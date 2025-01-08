package fun.wraq.events.mob.instance.instances.element;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MushroomInstance extends NoTeamInstance {

    private static MushroomInstance instance;

    public static String mobName = "菌菇灵统领";
    public Mob boss;
    public Cow cow;
    public static Style style = CustomStyle.MUSHROOM_STYLE;
    public static final double MAX_HEALTH = 1 * Math.pow(10, 8);
    public static final int XP_LEVEL = 235;

    public static MushroomInstance getInstance() {
        if (instance == null) {
            instance = new MushroomInstance(new Vec3(2011, 129, -1788), 60, 60, new Vec3(2011, 129, -1788),
                    Component.literal(mobName).withStyle(style));
        }
        return instance;
    }

    public MushroomInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, XP_LEVEL);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        if (boss == null || boss.tickCount == 0) return;

        int tick = boss.tickCount;
        if (tick % 20 == 0) {
            commonAttack();
        }

        if (tick % 40 == 10) {
            skill1();
        }

        if (tick % 200 == 70) {
            skill2();
        }
    }

    @Override
    public void reset(int tick, boolean removeMob) {
        if (cow != null)  {
            cow.remove(Entity.RemovalReason.KILLED);
        }
        super.reset(tick, removeMob);
    }

    @Override
    public void summonModule(Level level) {
        cow = new Cow(EntityType.MOOSHROOM, level);
        cow.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.45);
        cow.moveTo(pos);
        level.addFreshEntity(cow);

        Piglin mob = new Piglin(EntityType.PIGLIN_BRUTE, level);
        MobSpawn.setMobCustomName(mob, Component.literal(mobName).withStyle(style), XP_LEVEL);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, 3750, 340, 340, 0.4,
                5, 0.55, 160, 25, MAX_HEALTH, 0.45);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", CustomStyle.styleOfRed.getColor().getValue());
            tag.put("display", tag1);
            mob.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        mob.setItemSlot(EquipmentSlot.HEAD, Compute.getSkullByName("MHF_MushroomCow"));
        mob.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.GOLDEN_AXE));

        mob.moveTo(pos);
        level.addFreshEntity(mob);
        mobList.add(mob);
        boss = mob;

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(mob.getDisplayName(),
                BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);

        mob.startRiding(cow);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> {
            boolean reward = itemAndRate.sendWithMSG(player, 1);
            if (itemAndRate.getRate() < 0.01 && reward) {
                sendFormatMSG(player, Te.s(player.getDisplayName(), "击败", mobName, style,
                        "获得了", itemAndRate.getItemStack().getDisplayName()));
            }
        });

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);

        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), XP_LEVEL);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moontainBoss);
    }

    @Override
    public Component allowRewardCondition() {
        return Component.literal("需要至少").withStyle(ChatFormatting.WHITE).
                append(Component.literal("锻造").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("过").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1件").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("暗黑城堡武器").withStyle(CustomStyle.styleOfCastleCrystal)).
                append(Component.literal("，方能获取奖励。").withStyle(ChatFormatting.WHITE));
    }

    @Override
    public int getRewardNeedItemCount() {
        return 2;
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(MushroomItems.RED_MUSHROOM.get(), 1),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1),
                new ItemAndRate(MushroomItems.NETHER_MUSHROOM.get(), 0.01)
        );
    }

    public static void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("菌菇", style), content);
    }

    public void commonAttack() {
        double commonAttackDamage = 5000;
        Level level = boss.level();
        getAllPlayers(level).forEach(player -> {
            ParticleProvider.createLineEffectParticle(level, (int) player.distanceTo(boss) * 5,
                    boss.getEyePosition(), player.getEyePosition(), style);
            Damage.causeAttackDamageToPlayer(boss, player, commonAttackDamage);
            Damage.causeManaDamageToPlayer(boss, player, commonAttackDamage, 0, 200);
        });
    }

    public void skill1() {
        Level level = boss.level();
        List<Player> playerList = getAllPlayers(level).stream().toList();
        if (playerList.isEmpty()) return;
        Player randomPlayer = playerList.get(RandomUtils.nextInt(0, playerList.size()));

        // 造成伤害
        PersistentRangeEffect.addEffect(boss, randomPlayer.position(), 5, (effect -> {
            Compute.getNearEntity(boss.level(), effect.center(), Player.class, 5)
                    .stream().filter(e -> e instanceof Player)
                    .map(e -> (Player) randomPlayer).forEach(eachPlayer -> {
                        Damage.causeManaDamageToPlayer(boss, eachPlayer, 5000, 0, 200);
                    });
        }), 5, 40);

        // 制造粒子
        ParticleProvider.createSpaceEffectParticle(level, randomPlayer.position(), 5, 100,
                CustomStyle.MUSHROOM_STYLE, 40);
    }

    public void skill2() {
        Level level = boss.level();
        getAllPlayers(level).forEach(player -> {
            ParticleProvider.createBreakBlockParticle(player, Blocks.BROWN_MUSHROOM);
            SpecialEffectOnPlayer.addImprisonEffect(player, 40);
        });
    }

    public static double getAdjustDamageRate(Mob mob) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            return -0.5;
        }
        return 0;
    }
}
