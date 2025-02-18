package fun.wraq.events.mob.instance;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.AdjustStackBeforeGive;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.missions.mission2.MissionV2Helper;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.purple.PurpleIronCommon;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

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
    public final int level;
    public Set<Player> players = new CopyOnWriteArraySet<>();
    public int spawnTick = 0;
    public int clearTick = 0;

    public NoTeamInstance(final Vec3 pos, final double range, final int delayTick,
                          final Vec3 armorStandPos, final MutableComponent name, int level) {
        this.pos = pos;
        this.range = range;
        this.delayTick = delayTick;
        this.inChallenge = false;
        this.armorStandPos = armorStandPos;
        this.name = name;
        this.ready = false;
        this.level = level;
    }

    public boolean anyMobAlive() {
        return mobList.stream().anyMatch(mob -> mob != null && mob.isAlive());
    }

    public void detectAndSummonThenHandleTick(Level level) {
        int tick = Tick.get();
        boolean allMobIsNull = true;
        for (Mob mob : mobList) {
            if (mob != null) {
                allMobIsNull = false;
                break;
            }
        }
        if (!inChallenge && tick > summonTick && (allMobIsNull || !anyMobAlive())
                && !getNearPlayers(level).isEmpty() && ready) {
            mobList.clear();
            bossInfoList.clear();
            inChallenge = true;
            spawnTick = Tick.get();
            players.addAll(getNearPlayers(level));
            summonModule(level);
        }
        if (getNearPlayers (level).isEmpty()
                && (players.isEmpty() || players.stream().allMatch(LivingEntity::isDeadOrDying))) {
            reset(tick, true);
        }
        if (inChallenge && spawnTick != Tick.get()) {
            players.removeIf(Objects::isNull);
            tickModule();
            if (clearTick == Tick.get()) {
                rewardPlayers();
                reset(tick, false);
                return;
            }
            if (!anyMobAlive() && clearTick < Tick.get()) {
                clearTick = Tick.get() + 60;
            } else {
                for (Mob mob : mobList) {
                    if (mob != null && mob.isAlive()) {
                        if (Compute.getHorizonDistance(mob.position(), pos) > range) {
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

    public int getRewardNeedItemCount() {
        return 1;
    }

    public void rewardPlayers() {
        players.forEach(player -> {
            if (player != null && !this.mobList.isEmpty() && this.mobList.get(0) != null) {
                int needLevel = (int) (level * 0.8);
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
                    if (InventoryOperation.checkItemRemoveIfHas(player,
                            List.of(new ItemStack(getSummonAndRewardNeedItem(), getRewardNeedItemCount())))) {
                        List<ItemAndRate> rewardList = getRewardList();
                        rewardList.forEach(itemAndRate -> {
                            ItemStack copyStack = itemAndRate.getItemStack().copy();
                            Item copyItem = copyStack.getItem();
                            AdjustStackBeforeGive adjustStackBeforeGive = stack -> {
                                ForgeEquipUtils.setForgeQualityOnEquip(stack, RandomUtils.nextInt(2, 6));
                            };
                            boolean isEquip
                                    = Utils.mainHandTag.containsKey(copyItem) || Utils.armorTag.containsKey(copyItem);
                            ItemStack rewardStack = itemAndRate.sendWithMSG(player, 1,
                                    isEquip ? adjustStackBeforeGive : null);
                            boolean reward = !rewardStack.is(Items.AIR);
                            if (itemAndRate.getRate() <= 0.01 && reward) {
                                if (isEquip) {
                                    Compute.forgingHoverName(rewardStack);
                                }
                                Compute.sendFormatMSG(player, Te.s("领主级怪物", CustomStyle.styleOfRed),
                                        Te.s(player.getDisplayName(), "击败", name,
                                        "获得了", rewardStack));
                                if (copyItem instanceof PurpleIronCommon) {
                                    NoTeamInstanceModule.putPlayerAllowReward(player,
                                            NoTeamInstanceModule.AllowRewardKey.iceKnight, true);
                                }
                            }
                        });
                        MobSpawn.incrementPlayerKillCount(player, name.getString());
                        Compute.givePercentExpToPlayer(player, 0.1, PlayerAttributes.expUp(player), this.level);
                        exReward(player);
                    } else {
                        Compute.sendFormatMSG(player, Component.literal("副本").withStyle(ChatFormatting.RED),
                                Component.literal("你的背包中没有 ").withStyle(ChatFormatting.WHITE).
                                        append(getSummonAndRewardNeedItem().getDefaultInstance().getDisplayName()).
                                        append(Te.s(" * " + getRewardNeedItemCount(), ChatFormatting.AQUA)).
                                        append(Component.literal(" 因此你无法获得奖励").withStyle(ChatFormatting.WHITE)));
                    }
                    MissionV2Helper.onChallengeFinished(player, name.getString());
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
        if (!getNearPlayers(level).isEmpty() && !inChallenge) {
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

    public void exReward(Player player) {

    }

    public List<Player> getNearPlayers(Level level) {
        List<Player> playerList = level.getEntitiesOfClass(Player.class,
                AABB.ofSize(pos, range * 2, 320, range * 2));
        playerList.removeIf(player -> Compute.getHorizonDistance(player.position(), pos) > range);
        Set<Player> players = new HashSet<>(playerList);
        if (!mobList.isEmpty()) {
            for (Mob mob : mobList) {
                players.addAll(level.getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(),
                                range * 2, range * 2, range * 2))
                        .stream().filter(player -> player.distanceTo(mob) < range).toList());
            }
        }
        return players.stream().toList();
    }

    public static void givePlayerNotePaper(Player player) throws IOException {
        ItemStack itemStack = new ItemStack(ModItems.notePaper.get(), 64);
        InventoryCheck.addOwnerTagToItemStack(player, itemStack);
        InventoryOperation.giveItemStack(player, itemStack);
    }

    public void bossInfoSet(Level level) {
        for (int i = 0; i < bossInfoList.size(); i++) {
            ServerBossEvent serverBossEvent = bossInfoList.get(i);
            if (!mobList.isEmpty()) {
                Mob mob = mobList.get(i);
                if (serverBossEvent != null) {
                    List<Player> playerList = getNearPlayers(level);
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
        players.clear();
        spawnTick = 0;
    }

    public abstract List<ItemAndRate> getRewardList();

    public double onMobWithStandDamageRate(Player player, Mob mob) {
        if (!players.contains(player)) {
            return 0;
        }
        return 1;
    }

    public abstract String getKillCountDataKey();

    public int getMaxPlayerNum() {
        return 4;
    }
}
