package com.very.wraq.events.mob.instance.instances;

import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.fast.Tick;
import com.very.wraq.common.registry.ModEntityType;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.instance.NoTeamInstance;
import com.very.wraq.events.mob.instance.NoTeamInstanceModule;
import com.very.wraq.process.func.damage.Damage;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.system.missions.series.dailyMission.DailyMission;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.gems.GemItems;
import com.very.wraq.series.newrunes.NewRuneItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MoonInstance extends NoTeamInstance {

    private static MoonInstance instance;

    public static String mobName = "阿尔忒弥斯";

    public static MoonInstance getInstance() {
        if (instance == null) {
            instance = new MoonInstance(new Vec3(1761, 130, -463), 50, 200, new Vec3(1761, 130, -463),
                    Component.literal(mobName).withStyle(CustomStyle.styleOfMoon));
        }
        return instance;
    }

    public MoonInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        Mob attackMob = mobList.get(0);
        Mob manaMob = mobList.get(1);
        if (attackMob == null || manaMob == null) return;
        int tickCount = attackMob.getServer().getTickCount();
        List<Player> players = getPlayerList(attackMob.level());
        if (tickCount % 280 == 0) {
            Skill1(attackMob, manaMob, players);
        }
        if (tickCount % 280 == 140) {
            Skill2(attackMob, manaMob, players);
        }
        Skill3(attackMob, manaMob, players);
        // AI
        fall(attackMob, manaMob, players, 120, pos);
    }

    @Override
    public void summonModule(Level level) {
        Stray attackMob = new Stray(EntityType.STRAY, level);
        Style style = CustomStyle.styleOfMoon;

        MobSpawn.setMobCustomName(attackMob, Component.literal("阿尔忒弥斯 - 明镜").withStyle(style), 160);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(attackMob), 120);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(attackMob, 2000, 25, 25, 0.4, 4, 0.25, 13, 20, 700 * Math.pow(10, 4), 0.3);

        attackMob.setHealth(attackMob.getMaxHealth());
        attackMob.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorMoonAttack.get().getDefaultInstance());
        attackMob.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorMoonChest.get().getDefaultInstance());
        attackMob.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorMoonLeggings.get().getDefaultInstance());
        attackMob.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorMoonBoots.get().getDefaultInstance());
        attackMob.setItemSlot(EquipmentSlot.OFFHAND, ModItems.MoonShield.get().getDefaultInstance());
        attackMob.setItemSlot(EquipmentSlot.MAINHAND, ModItems.MoonKnife.get().getDefaultInstance());

        attackMob.moveTo(1747.5, 127.5, -491.5);
        level.addFreshEntity(attackMob);
        mobList.add(attackMob);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(attackMob.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getPlayerList(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);

        Stray manaMob = new Stray(EntityType.STRAY, level);
        MobSpawn.setMobCustomName(manaMob, Component.literal("阿尔忒弥斯 - 天镜").withStyle(style), 160);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(manaMob), 120);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(manaMob, 2000, 2500, 2500, 0.4, 4, 0.4, 13, 20, 700 * Math.pow(10, 4), 0.3);

        manaMob.setHealth(manaMob.getMaxHealth());
        manaMob.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorMoonMana.get().getDefaultInstance());
        manaMob.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorMoonChest.get().getDefaultInstance());
        manaMob.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorMoonLeggings.get().getDefaultInstance());
        manaMob.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorMoonBoots.get().getDefaultInstance());
        manaMob.setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());

        manaMob.moveTo(new Vec3(1773.5, 122.5, -498.5));
        level.addFreshEntity(manaMob);
        mobList.add(manaMob);

        ServerBossEvent serverBossEvent1 = (ServerBossEvent) (new ServerBossEvent(manaMob.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getPlayerList(level).forEach(player -> {
            serverBossEvent1.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent1);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> {
            try {
                itemAndRate.send(player, 1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        DailyMission.addCount(player, DailyMission.moonInstanceCountMap);

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), 160);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moon);
    }

    @Override
    public Component allowRewardCondition() {
        return Component.literal("需要至少").withStyle(ChatFormatting.WHITE).
                append(Component.literal("锻造").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("过").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1件").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("魔王武器").withStyle(CustomStyle.styleOfDemon)).
                append(Component.literal("，方能获取奖励。").withStyle(ChatFormatting.WHITE));
    }

    public static List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.MoonLoot.get(), 1),
                new ItemAndRate(GemItems.moonAttackGem.get(), 0.01),
                new ItemAndRate(GemItems.moonManaGem.get(), 0.01),
                new ItemAndRate(ModItems.WorldSoul2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1),
                new ItemAndRate(NewRuneItems.moonNewRune.get(), 0.015));
    }

    public static void Skill1(Mob AttackMob, Mob ManaMob, List<Player> playerList) {
        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal("潮汐之源").withStyle(CustomStyle.styleOfMoon));
        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                new ClientboundSetSubtitleTextPacket(Component.literal(""));
        playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket));
        playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket));
        if (AttackMob.isAlive()) {
            Compute.RepelPlayer(AttackMob, AttackMob.position(), 6, 3, 3);
            ParticleProvider.DisperseParticle(AttackMob.position(), (ServerLevel) AttackMob.level(), 1, 1, 120, ModParticles.LONG_LIGHTNINGISLAND.get(), 1);
            ParticleProvider.DisperseParticle(AttackMob.position(), (ServerLevel) AttackMob.level(), 1.5, 1, 120, ModParticles.LONG_LIGHTNINGISLAND.get(), 1);
        }
        if (ManaMob.isAlive()) {
            for (int i = 0; i < 16; i++) {
                ManaArrow manaArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), ManaMob, ManaMob.level());
                manaArrow.shootFromRotation(ManaMob, ManaMob.getXRot(), ManaMob.getYRot() + 360 * i / 16.0f, 1, 1.5f, 1);
                ManaMob.level().addFreshEntity(manaArrow);
            }
            ParticleProvider.DisperseParticle(ManaMob.position(), (ServerLevel) ManaMob.level(), 1, 1, 120, ModParticles.LONG_LIGHTNINGISLAND.get(), 1);
            ParticleProvider.DisperseParticle(ManaMob.position(), (ServerLevel) ManaMob.level(), 1.5, 1, 120, ModParticles.LONG_LIGHTNINGISLAND.get(), 1);
        }
    }

    public static void Skill2(Mob AttackMob, Mob ManaMob, List<Player> playerList) {
        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal("阴晴圆缺").withStyle(CustomStyle.styleOfMoon));
        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                new ClientboundSetSubtitleTextPacket(Component.literal(""));
        playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket));
        playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket));
        if (playerList.size() == 0) return;
        Player HealthHighPlayer = playerList.get(0);
        Player HealthLowPlayer = playerList.get(0);
        double HighNum = 0;
        double LowNum = 1;
        for (Player player : playerList) {
            double rate = player.getHealth() / player.getMaxHealth();
            if (rate > HighNum) {
                HighNum = rate;
                HealthHighPlayer = player;
            }
            if (rate < LowNum) {
                LowNum = rate;
                HealthLowPlayer = player;
            }
        }
        if (AttackMob.isAlive()) {
            Damage.manaDamageToPlayer(AttackMob, HealthLowPlayer, HealthLowPlayer.getMaxHealth() * 0.5);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthLowPlayer, 1, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthLowPlayer, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthLowPlayer, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthLowPlayer, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthLowPlayer, 0, 0.4, 8, ParticleTypes.WITCH, 0);
        }
        if (ManaMob.isAlive()) {
            Damage.manaDamageToPlayer(ManaMob, HealthHighPlayer, HealthHighPlayer.getMaxHealth() * 0.5);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthHighPlayer, 1, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthHighPlayer, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthHighPlayer, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthHighPlayer, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthHighPlayer, 0, 0.4, 8, ParticleTypes.WITCH, 0);
        }
    }

    public static void Skill3(Mob AttackMob, Mob ManaMob, List<Player> playerList) {

        if (AttackMob.isAlive() && ManaMob.isAlive()) {
            double attackMobRate = AttackMob.getHealth() / AttackMob.getMaxHealth();
            double manaMobRate = ManaMob.getHealth() / ManaMob.getMaxHealth();
            if (Math.abs(attackMobRate - manaMobRate) > 0.5) {
                ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                        new ClientboundSetTitleTextPacket(Component.literal("月食").withStyle(CustomStyle.styleOfMoon));
                ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                        new ClientboundSetSubtitleTextPacket(Component.literal(""));
                playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket));
                playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket));
                AttackMob.heal(AttackMob.getMaxHealth());
                ManaMob.heal(ManaMob.getMaxHealth());
                ParticleProvider.EntityEffectVerticleCircleParticle(AttackMob, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(AttackMob, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(AttackMob, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(AttackMob, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(AttackMob, 0, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(ManaMob, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(ManaMob, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(ManaMob, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(ManaMob, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(ManaMob, 0, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                playerList.forEach(player -> {
                    Damage.manaDamageToPlayer(ManaMob, player, player.getMaxHealth() * 0.25);
                    Damage.manaDamageToPlayer(AttackMob, player, player.getMaxHealth() * 0.25);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player, 0, 0.4, 8, ParticleTypes.WITCH, 0);
                });
            }
        }
    }

    public static void fall(Mob attackMob, Mob manaMob, List<Player> playerList, double yLimit, Vec3 pos) {
        if (attackMob.isAlive() && attackMob.getY() < yLimit) attackMob.moveTo(pos);
        if (manaMob.isAlive() && manaMob.getY() < yLimit) manaMob.moveTo(pos);
        playerList.forEach(player -> {
            if (player.position().distanceTo(pos) < 150 && player.getY() < yLimit) {
                Damage.manaDamageToPlayer(manaMob, player, player.getMaxHealth() * 0.25);
                Damage.manaDamageToPlayer(attackMob, player, player.getMaxHealth() * 0.25);
                ((ServerPlayer) player).teleportTo(player.getServer().getLevel(Level.OVERWORLD), pos.x, pos.y, pos.z, 179, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        });

        if (playerList.isEmpty()) return;
        Level level = playerList.get(0).level();
        List<ItemEntity> list = level.getEntitiesOfClass(ItemEntity.class, AABB.ofSize(pos, 200, 50, 200));
        list.forEach(itemEntity -> {
            if (itemEntity.getY() < yLimit) {
                itemEntity.moveTo(pos);
            }
        });
    }

    public static WeakHashMap<Player, Integer> attackImmuneMSGMap = new WeakHashMap<>();

    public static boolean isMoonAttackImmune(Player player, Mob mob) {
        if (player.isCreative()) return false;
        if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorMoonAttack.get())) {
            if (player.distanceTo(mob) > 6 && attackImmuneMSGMap.getOrDefault(player, 0) < Tick.get()) {
                attackImmuneMSGMap.put(player, Tick.get() + 100);
                Compute.sendFormatMSG(player, Component.literal("尘月宫").withStyle(CustomStyle.styleOfMoon),
                        Component.literal("似乎这个距离对阿尔忒弥斯的伤害将会减半。").withStyle(CustomStyle.styleOfMoon1));
                return true;
            }
        }
        return false;
    }

    public static WeakHashMap<Player, Integer> manaImmuneMSGMap = new WeakHashMap<>();

    public static boolean isMoonManaImmune(Player player, Mob mob) {
        if (player.isCreative()) return false;
        if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorMoonMana.get())) {
            if (player.distanceTo(mob) < 6 && manaImmuneMSGMap.getOrDefault(player, 0) < Tick.get()) {
                manaImmuneMSGMap.put(player, Tick.get() + 100);
                Compute.sendFormatMSG(player, Component.literal("尘月宫").withStyle(CustomStyle.styleOfMoon),
                        Component.literal("似乎这个距离对阿尔忒弥斯的伤害将会减半。").withStyle(CustomStyle.styleOfMoon1));

                return true;
            }
        }
        return false;
    }
}
