package fun.wraq.events.mob.instance;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class NoTeamInstance {
    public List<ServerBossEvent> bossInfoList = new ArrayList<>();
    public final Vec3 pos;
    public final double range;
    public List<Mob> mobList = new ArrayList<>();
    public Mob lastMob;
    public int summonTick = 0;
    public int delayTick;
    public boolean inChallenge;
    public final Vec3 armorStandPos;
    public final MutableComponent name;
    public boolean ready;

    public NoTeamInstance(final Vec3 pos, final double range, final int delayTick,
                          final Vec3 armorStandPos, final MutableComponent name) {
        this.pos = pos;
        this.range = range;
        this.delayTick = delayTick;
        this.inChallenge = false;
        this.armorStandPos = armorStandPos;
        this.name = name;
        this.ready = false;
    }

    public void detectAndSummon(Level level) {
        List<Player> playerList = getPlayerList(level);
        int tick = level.getServer().getTickCount();
        boolean allMobIsNull = true;
        for (Mob mob : mobList) {
            if (mob != null) {
                allMobIsNull = false;
                break;
            }
        }
        boolean allMobIsNotAlive = true;
        for (Mob mob : mobList) {
            if (mob != null && mob.isAlive()) {
                allMobIsNotAlive = false;
                break;
            }
        }
        if (!inChallenge && tick > summonTick && (allMobIsNull || allMobIsNotAlive)
                && !playerList.isEmpty() && ready) {
            mobList.clear();
            bossInfoList.clear();
            summonModule(level);
            inChallenge = true;
        }
        if (playerList.isEmpty() || playerList.stream().allMatch(LivingEntity::isDeadOrDying)) {
            reset(tick, true);
        }
        if (inChallenge && !allMobIsNull) {
            boolean allMobIsDead = true;
            for (Mob mob : mobList) {
                if (mob != null && !mob.isDeadOrDying()) {
                    allMobIsDead = false;
                    break;
                }
            }
            if (allMobIsDead) rewardNearPlayer(level);

            if (allMobIsNotAlive) {
                reset(tick, false);
            } else {
                if (playerList.size() >= 4) summonTick -= (playerList.size() / 4 - 1);
                tickModule();
                for (Mob mob : mobList) {
                    if (mob != null && mob.isAlive()) {
                        if (mob.position().distanceTo(pos) > 30) {
                            mob.moveTo(pos);
                        }
                        lastMob = mob;
                    }
                }
            }
        }
    }

    public abstract void tickModule();

    public abstract void summonModule(Level level);

    public Item getSummonAndRewardNeedItem() {
        return ModItems.notePaper.get();
    }

    public void rewardNearPlayer(Level level) {
        List<Player> playerList = getPlayerList(level);
        playerList.forEach(player -> {
            if (!this.mobList.isEmpty() && this.mobList.get(0) != null) {
                Mob mob = this.mobList.get(0);
                int needLevel = (int) (MobSpawn.MobBaseAttributes.xpLevel.get(MobSpawn.getMobOriginName(mob)) * 0.8);
                if (!allowReward(player) && allowRewardCondition() != null) {
                    Compute.sendFormatMSG(player, Component.literal("副本").withStyle(ChatFormatting.RED),
                            allowRewardCondition());
                    return;
                }
                if (player.experienceLevel < needLevel) {
                    Compute.sendFormatMSG(player, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("你没有达到获取奖励所需的等级: ").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("Lv." + needLevel).withStyle(Utils.levelStyleList.get(needLevel / 25))).
                                    append(Component.literal(" 因此你无法获得奖励").withStyle(ChatFormatting.WHITE)));
                } else {
                    if (playerHasItem(player)) {
                        rewardModule(player);
                        InventoryOperation.removeItem(player.getInventory(), getSummonAndRewardNeedItem(), 1);
                    } else {
                        Compute.sendFormatMSG(player, Component.literal("副本").withStyle(ChatFormatting.RED),
                                Component.literal("你的背包中没有 ").withStyle(ChatFormatting.WHITE).
                                        append(getSummonAndRewardNeedItem().getDefaultInstance().getDisplayName()).
                                        append(Component.literal(" 因此你无法获得奖励").withStyle(ChatFormatting.WHITE)));
                    }
                }
            }
        });
    }

    public boolean playerHasItem(Player player) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.is(getSummonAndRewardNeedItem())) return true;
        }
        return false;
    }

    public void summonLeftSecondsArmorStand(Level level) {
        if (!getPlayerList(level).isEmpty() && !inChallenge) {
            List<ArmorStand> armorStandList = level.getEntitiesOfClass(ArmorStand.class, AABB.ofSize(armorStandPos,
                    8, 8, 8));
            armorStandList.forEach(armorStand -> armorStand.remove(Entity.RemovalReason.KILLED));
            int tick = Tick.get();
            if (tick > summonTick) {
                summonArmorStand(level, new Vec3(0, -0.25, 0), Te.s("手持",
                        getSummonAndRewardNeedItem().getDefaultInstance().getDisplayName(), "右键以召唤", ChatFormatting.AQUA));
            } else {
                summonArmorStand(level, new Vec3(0, -0.25, 0), Component.literal("剩余:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(String.valueOf((summonTick - tick) / 20)).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("秒").withStyle(ChatFormatting.WHITE)));
            }
            summonArmorStand(level, new Vec3(0, 0, 0), Te.s("领主级怪物", ChatFormatting.RED, ":", name));
        } else {
            List<ArmorStand> armorStandList = level.getEntitiesOfClass(ArmorStand.class, AABB.ofSize(armorStandPos, range * 2, range * 2, range * 2));
            armorStandList.forEach(armorStand -> armorStand.remove(Entity.RemovalReason.KILLED));
        }
    }

    public void summonArmorStand(Level level, Vec3 offset, Component name) {
        ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, level);
        armorStand.setNoGravity(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(name);
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.noPhysics = true;
        armorStand.setBoundingBox(AABB.ofSize(new Vec3(0, 0, 0), 0.1, 0.1, 0.1));
        armorStand.moveTo(armorStandPos.add(offset).add(0.5, 0, 0.5));
        level.addFreshEntity(armorStand);
    }

    public abstract void rewardModule(Player player);

    public List<Player> getPlayerList(Level level) {
        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(pos, range * 2, range * 2, range * 2));
        playerList.removeIf(player -> player.position().distanceTo(pos) > range);
        if (!mobList.isEmpty()) {
            for (Mob mob : mobList) {
                playerList.addAll(level.getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(),
                        range * 2, range * 2, range * 2))
                        .stream().filter(player -> player.distanceTo(mob) < range).toList());
            }
        }
        return playerList;
    }

    public static void givePlayerNotePaper(Player player) throws IOException {
        ItemStack itemStack = new ItemStack(ModItems.notePaper.get(), 64);
        InventoryCheck.addOwnerTagToItemStack(player, itemStack);
        InventoryOperation.itemStackGive(player, itemStack);
    }

    public void bossInfoSet(Level level) {
        for (int i = 0; i < bossInfoList.size(); i++) {
            ServerBossEvent serverBossEvent = bossInfoList.get(i);
            if (!mobList.isEmpty()) {
                Mob mob = mobList.get(i);
                if (serverBossEvent != null) {
                    List<Player> playerList = getPlayerList(level);
                    for (Player player : playerList) {
                        if (!serverBossEvent.getPlayers().contains((ServerPlayer) player))
                            serverBossEvent.addPlayer((ServerPlayer) player);
                    }
                    serverBossEvent.setProgress(mob.getHealth() / mob.getMaxHealth());
                    List<ServerPlayer> removingPlayer = new ArrayList<>();
                    for (ServerPlayer player : serverBossEvent.getPlayers()) {
                        if (player.distanceTo(mob) > range) removingPlayer.add(player);
                    }
                    for (ServerPlayer serverPlayer : removingPlayer) {
                        serverBossEvent.removePlayer(serverPlayer);
                    }
                }
            }
        }
    }

    public abstract boolean allowReward(Player player);

    public abstract Component allowRewardCondition();

    public void reset(int tick, boolean removeMob) {
        summonTick = tick + delayTick;
        inChallenge = false;
        bossInfoList.forEach(ServerBossEvent::removeAllPlayers);
        bossInfoList.clear();
        mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
        mobList.clear();
        ready = false;
    }
}
