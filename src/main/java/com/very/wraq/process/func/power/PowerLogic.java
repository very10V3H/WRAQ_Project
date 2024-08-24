package com.very.wraq.process.func.power;

import com.very.wraq.common.MySound;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.EarthPower.EarthPowerS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.EffectParticle.DamageDecreaseParticleS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.element.ElementValue;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.Castle.CastleManaArmor;
import com.very.wraq.series.newrunes.chapter6.CastleNewRune;
import com.very.wraq.series.overworld.chapter1.forest.ForestPower;
import com.very.wraq.series.overworld.chapter1.forest.ForestPowerEffectMob;
import com.very.wraq.series.overworld.chapter1.plain.PlainPower;
import com.very.wraq.series.overworld.chapter1.Snow.SnowPower;
import com.very.wraq.series.overworld.chapter1.volcano.VolcanoPower;
import com.very.wraq.series.overworld.chapter1.waterSystem.LakePower;
import com.very.wraq.series.overworld.chapter1.waterSystem.LakePowerEffect;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.StableAttributesModifier;
import com.very.wraq.common.attributeValues.EffectOnMob;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.very.wraq.core.ManaAttackModule.ManaSkill12Attack;
import static com.very.wraq.core.ManaAttackModule.ManaSkill13Attack;
import static com.very.wraq.common.Compute.*;

public class PowerLogic {

    public static Map<Player, Integer> playerLastTimeReleasePower = new HashMap<>();

    public static Map<Player, Map<Item, Integer>> playerPowerCoolDownRecord = new HashMap<>();
    public static Map<Player, Integer> playerLastTimeReleasePowerCoolDownTime = new HashMap<>();
    public static Map<Player, Double> playerLastTimeReleasePowerManaCost = new HashMap<>();

    public static void ReleaseLastTime(Player player) {
        int type = playerLastTimeReleasePower.getOrDefault(player, 0);
        ReleaseByType(player, type);
    }

    public static void ReleaseByType(Player player, int type) {
        switch (type) {
            case 0 -> WitherBonePower(player, null);
            case 1 -> PiglinPower(player, null);
            case 2 -> WitherBoneMealPower(player, null);
            case 3 -> MagmaPower(player, null);
            case 4 -> PlainPower(player, null, 3);
            case 5 -> ForestPower(player, null, 3);
            case 6 -> LakePower(player, null, 3);
            case 7 -> VolcanoPower(player, null, 3);
            case 8 -> SnowPower(player, null, 3);
            case 9 -> EarthPower(player, true);
        }
    }


    public static void WitherBonePower(Player player, Item Tool) {
        playerLastTimeReleasePower.put(player, 0);
        PlayerPowerRelease(player);
        if (Tool != null) playerItemCoolDown(player, Tool, 10);
        Level level = player.level();
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) TargetPos = detectPlayerPickMob(player).position();
        List<Mob> monsterList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(TargetPos, 20, 20, 20));
        for (Mob mob : monsterList) {
            if (mob.getPosition(0).distanceTo(TargetPos) < 6) {
                Damage.ManaDamageToMonster_RateApDamage_ElementAddition(player, mob, 3, true,
                        Element.fire, ElementValue.ElementValueJudgeByType(player, Element.fire) + 1);
                PlayerPowerEffectToMob(player, mob);
                mob.getPersistentData().putInt("witherBonePower", 100);
                player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                        ModNetworking.sendToClient(new DamageDecreaseParticleS2CPacket(mob.getId(), 100), serverPlayer));

                Utils.witherBonePowerCCMonster.add(mob);
                player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                        ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 100), serverPlayer));

                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        }
        ParticleProvider.VerticleCircleParticle(TargetPos, (ServerLevel) level, 1, 6, 100, ParticleTypes.WITCH);
        ParticleProvider.VerticleCircleParticle(TargetPos, (ServerLevel) level, 1.5, 6, 100, ParticleTypes.WITCH);
        MySound.SoundToAll(player, ModSounds.Nether_Power.get());
    }

    public static void PiglinPower(Player player, Item Tool) {
        playerLastTimeReleasePower.put(player, 1);
        PlayerPowerRelease(player);
        Compute.PlayerPowerParticle(player);
        if (Tool != null) playerItemCoolDown(player, Tool, 10);
        Level level = player.level();
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) TargetPos = detectPlayerPickMob(player).position();
        List<Mob> monsterList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(TargetPos, 20, 20, 20));

        for (Mob mob : monsterList) {
            if (mob.getPosition(0).distanceTo(TargetPos) < 6) {
                Damage.ManaDamageToMonster_RateApDamage_ElementAddition(player, mob, monsterList.size() * 2, true,
                        Element.fire, ElementValue.ElementValueJudgeByType(player, Element.fire) + 1);
                PlayerPowerEffectToMob(player, mob);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        }

        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 20, 20, 20));

        double manaDamageUpValue = 0;
        if (StableAttributesModifier.getAttributeModifierList(player, StableAttributesModifier.playerAttackDamageModifier).
                stream().anyMatch(attributesModifier -> attributesModifier.tag().equals("piglinPowerAttackDamageUp"))
                && WraqCurios.isOn(CastleNewRune.class, player)) {
            manaDamageUpValue = (PlayerAttributes.manaDamage(player) - StableAttributesModifier.getAttributeModifierList(player, StableAttributesModifier.playerAttackDamageModifier).
                    stream().filter(attributesModifier -> attributesModifier.tag().equals("piglinPowerAttackDamageUp")).findFirst().get().value() * 0.4) * 0.03;
        } else manaDamageUpValue = 0.03 * PlayerAttributes.manaDamage(player);

        for (Player player1 : playerList) {
            if (player1.distanceTo(player) < 6) {

                Compute.sendEffectLastTime(player1, ModItems.PigLinPower.get(), 100);

                StableAttributesModifier.addAttributeModifier(player1, StableAttributesModifier.playerMovementSpeedModifier,
                        new StableAttributesModifier("piglinPowerMovementSpeedUp", 0.1 * playerList.size(), player.getServer().getTickCount() + 100));

                StableAttributesModifier.addAttributeModifier(player1, StableAttributesModifier.playerAttackDamageModifier,
                        new StableAttributesModifier("piglinPowerAttackDamageUp", manaDamageUpValue, player.getServer().getTickCount() + 100));

                player1.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                player1.removeEffect(MobEffects.WITHER);
                player1.removeEffect(MobEffects.BLINDNESS);

                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.4, 8, ParticleTypes.COMPOSTER, 0);
            }
        }
        ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36, CustomStyle.styleOfPower.getColor().getValue());

        ParticleProvider.VerticleCircleParticle(TargetPos, (ServerLevel) level, 1, 6, 100, ParticleTypes.WITCH);
        ParticleProvider.VerticleCircleParticle(TargetPos, (ServerLevel) level, 1.5, 6, 100, ParticleTypes.WITCH);

        MySound.SoundToAll(player, ModSounds.Nether_Power.get());
    }

    public static void WitherBoneMealPower(Player player, Item Tool) {
        if (!Compute.playerManaCost(player, Compute.PlayerMaxManaNum(player) * 0.33, true)) return;
        playerLastTimeReleasePowerManaCost.put(player, Compute.PlayerMaxManaNum(player));
        playerLastTimeReleasePower.put(player, 2);
        int TickCount = player.getServer().getTickCount();
        PlayerPowerRelease(player);
        Compute.PlayerPowerParticle(player);
        if (Tool != null) playerItemCoolDown(player, Tool, 10);
        Level level = player.level();
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) TargetPos = detectPlayerPickMob(player).position();
        List<Mob> monsterList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(TargetPos, 20, 20, 20));
        for (Mob mob : monsterList) {
            if (mob.getPosition(0).distanceTo(TargetPos) < 6) {
                Damage.ManaDamageToMonster_RateApDamage_ElementAddition(player, mob, 8, true,
                        Element.fire, ElementValue.ElementValueJudgeByType(player, Element.fire) + 1);
                PlayerPowerEffectToMob(player, mob);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
                Utils.NetherBoneMealPowerEffectMap.put(mob, TickCount + 100);
                AddManaDefenceDescreaseEffectParticle(mob, 100);
            }
        }

        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(TargetPos, 20, 20, 20));
        for (Player player1 : playerList) {
            if (player1.getPosition(0).distanceTo(TargetPos) < 6) {
                if (!player1.level().equals(player1.getServer().getLevel(Level.OVERWORLD)))
                    Damage.manaDamageToPlayer(player, player1, 10);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        }

        Compute.ManaStatusUpdate(player);

        ParticleProvider.VerticleCircleParticle(TargetPos, (ServerLevel) level, 1, 6, 100, ParticleTypes.WITCH);
        ParticleProvider.VerticleCircleParticle(TargetPos, (ServerLevel) level, 1.5, 6, 100, ParticleTypes.WITCH);

        MySound.SoundToAll(player, ModSounds.Nether_Power.get());
    }

    public static void MagmaPower(Player player, Item Tool) {
        playerLastTimeReleasePower.put(player, 3);
        CompoundTag data = player.getPersistentData();
        if (Tool != null) playerItemCoolDown(player, Tool, 3);
        data.putBoolean("MagmaPower", true);
        MySound.SoundToAll(player, ModSounds.Nether_Power.get());
    }

    public static void PlainPower(Player player, Item Tool, int level) {
        playerLastTimeReleasePower.put(player, 4);
        CompoundTag data = player.getPersistentData();
        PlayerPowerRelease(player);
        Level Dimension = player.level();
        double effect = PlainPower.effect[level];
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) TargetPos = detectPlayerPickMob(player).position();
        List<Mob> mobList = Dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        Vec3 finalTargetPos = TargetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                if (!MonsterCantBeMove(mob))
                    mob.setDeltaMovement(PosVec.normalize().scale(Math.min(2, 6 / PosVec.length())));
                Damage.ManaDamageToMonster_RateApDamage_ElementAddition(player, mob, effect, true,
                        Element.life, ElementValue.ElementValueJudgeByType(player, Element.life) + 1);
                PlayerPowerEffectToMob(player, mob);
            }
        });

        List<Player> playerList = Dimension.getEntitiesOfClass(Player.class,
                AABB.ofSize(player.position(), 20, 20, 20));

        playerList.forEach(player1 -> {
            if (player1.distanceTo(player) <= 6) {
                Compute.playerHeal(player1, data.getInt(StringUtils.Ability.Intelligent) * 20);

                StableAttributesModifier.addAttributeModifier(player1, StableAttributesModifier.playerMovementSpeedModifier,
                        new StableAttributesModifier("plainPowerMovementSpeed", 0.5, player.getServer().getTickCount() + 100));
                Compute.sendEffectLastTime(player1, ModItems.PlainPower.get(), 100);

                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
            }
        });
        ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36, CustomStyle.styleOfPlain.getColor().getValue());

        if (Tool != null)
            playerItemCoolDown(player, Tool, PlainPower.CoolDownTime[level] - ArmorCount.LifeManaE(player));

        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_PLAIN.get(), 1);
        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_PLAIN.get(), 1);

        MySound.SoundToAll(player, ModSounds.Wind.get());
    }

    public static void ForestPower(Player player, Item Tool, int level) {
        playerLastTimeReleasePower.put(player, 5);
        PlayerPowerRelease(player);
        Level Dimension = player.level();
        double effect = ForestPower.effect[level];
        Vec3 DesPos = player.pick(15, 0, true).getLocation();
        if (detectPlayerPickMob(player) != null) DesPos = detectPlayerPickMob(player).position();

        List<Mob> mobList = Dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(DesPos, 20, 20, 20));
        Vec3 finalDesPos = DesPos;
        mobList.forEach(mob -> {
            if (mob.position().subtract(finalDesPos).length() <= 8) {
                if (!MonsterCantBeMove(mob))
                    Utils.ForestPowerEffectMobList.add(new ForestPowerEffectMob(finalDesPos, 10, mob));
                Damage.ManaDamageToMonster_RateApDamage_ElementAddition(player, mob, effect, true,
                        Element.life, ElementValue.ElementValueJudgeByType(player, Element.life) + 1);
                PlayerPowerEffectToMob(player, mob);
                Compute.leafParticleCreate(mob, mob.level());
            }
        });

        List<Player> playerList = Dimension.getEntitiesOfClass(Player.class,
                AABB.ofSize(player.position(), 20, 20, 20));
        playerList.forEach(player1 -> {
            if (player1.distanceTo(player) <= 6) {
                Compute.playerHeal(player1, player.getPersistentData().getInt(StringUtils.Ability.Intelligent) * 20);

                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
            }
        });
        ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36, CustomStyle.styleOfForest.getColor().getValue());

        if (Tool != null)
            playerItemCoolDown(player, Tool, ForestPower.CoolDownTime[level] - ArmorCount.LifeManaE(player));

        ParticleProvider.GatherParticle(DesPos, (ServerLevel) player.level(), 1, 6, 120, ModParticles.LONG_FOREST.get(), 0.25);
        ParticleProvider.GatherParticle(DesPos, (ServerLevel) player.level(), 1.5, 6, 120, ModParticles.LONG_FOREST.get(), 0.25);
    }

    public static void LakePower(Player player, Item Tool, int tier) {
        playerLastTimeReleasePower.put(player, 6);
        int TickCount = player.getServer().getTickCount();
        PlayerPowerRelease(player);
        Level Dimension = player.level();
        double effect = LakePower.effect[tier];
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) TargetPos = detectPlayerPickMob(player).position();
        List<Mob> mobList = Dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(TargetPos, 20, 20, 20));

        List<Player> playerList = Dimension.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 20, 20, 20));
        playerList.removeIf(player1 -> player1.distanceTo(player) > 6);
        playerList.forEach(player1 -> {
            LakePower.playerDefendRateMap.put(player1, tier + 1);
            LakePower.playerDefendTickMap.put(player1, TickCount + 80);
            Compute.sendEffectLastTime(player, ModItems.LakePower.get().getDefaultInstance(), 80);
        });
        ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36, CustomStyle.styleOfLake.getColor().getValue());

        Vec3 finalTargetPos = TargetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                Compute.IgniteMob(player, mob, 0);
                EffectOnMob.addSlowDownEffect(mob, 40, 0.25);
                AddManaDefenceDescreaseEffectParticle(mob, 40);
                Utils.LakePowerEffectMobMap.put(mob, new LakePowerEffect(TickCount + 40, (tier + 1)));
                PlayerPowerEffectToMob(player, mob);
                Damage.ManaDamageToMonster_RateApDamage_ElementAddition(player, mob, effect, true,
                        Element.water, ElementValue.ElementValueJudgeByType(player, Element.water) + 1);
            }
        });


        if (Tool != null)
            playerItemCoolDown(player, Tool, LakePower.CoolDownTime[tier] - ArmorCount.ObsiManaE(player) * 0.75);

        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_LAKE.get(), 1);
        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_LAKE.get(), 1);
        MySound.SoundToAll(player, SoundEvents.WATER_AMBIENT);
    }

    public static void VolcanoPower(Player player, Item Tool, int level) {
        playerLastTimeReleasePower.put(player, 7);
        PlayerPowerRelease(player);
        Level Dimension = player.level();
        ServerLevel serverLevel = (ServerLevel) Dimension;
        double effect = VolcanoPower.effect[level];
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) TargetPos = detectPlayerPickMob(player).position();
        List<Mob> mobList = Dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        List<ServerPlayer> playerList = Dimension.getServer().getPlayerList().getPlayers();
        Vec3 finalTargetPos = TargetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                ParticleProvider.VerticleCircleParticle(mob.position(), serverLevel, 1, 2, 40, ModParticles.LONG_VOLCANO.get());
                ParticleProvider.VerticleCircleParticle(mob.position(), serverLevel, 1.5, 2, 40, ModParticles.LONG_VOLCANO.get());
                Compute.IgniteMob(player, mob, 40);
                List<Mob> mobList1 = Dimension.getEntitiesOfClass(Mob.class,
                        AABB.ofSize(mob.position(), 10, 10, 10));
                mobList1.forEach(mob1 -> {
                    if (mob1.position().subtract(mob.position()).length() <= 2) {
                        Damage.ManaDamageToMonster_RateApDamage_ElementAddition(player, mob1, effect, true,
                                Element.fire, ElementValue.ElementValueJudgeByType(player, Element.fire) + 1);
                        PlayerPowerEffectToMob(player, mob);
                        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                                new ClientboundLevelParticlesPacket(ParticleTypes.EXPLOSION_EMITTER, true,
                                        mob1.getX(), mob1.getY(), mob1.getZ(),
                                        0, 0, 0, 0, 0);
                        playerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                    }
                });
            }
        });

        if (Tool != null)
            playerItemCoolDown(player, Tool, VolcanoPower.CoolDownTime[level] - ArmorCount.ObsiManaE(player) * 0.75);

        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_VOLCANO.get(), 1);
        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_VOLCANO.get(), 1);

        MySound.SoundToAll(player, SoundEvents.DRAGON_FIREBALL_EXPLODE);
    }

    public static void SnowPower(Player player, Item Tool, int level) {
        playerLastTimeReleasePower.put(player, 8);
        PlayerPowerRelease(player);
        Level Dimension = player.level();
        double effect = SnowPower.effect[level];
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        CompoundTag data = player.getPersistentData();
        if (detectPlayerPickMob(player) != null) TargetPos = detectPlayerPickMob(player).position();

        List<Mob> mobList = Dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        Vec3 finalTargetPos = TargetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                Compute.IgniteMob(player, mob, 0);
                BlockPos blockPos = mob.blockPosition().above();
                if (Dimension.getBlockState(blockPos).is(Blocks.AIR)) {
                    Dimension.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                    Dimension.destroyBlock(blockPos, false);
                }
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 100, false, false, false));
                Damage.ManaDamageToMonster_RateApDamage_ElementAddition(player, mob, effect, true,
                        Element.ice, ElementValue.ElementValueJudgeByType(player, Element.ice) + 1);
                PlayerPowerEffectToMob(player, mob);
            }
        });

        List<Player> players = Dimension.getEntitiesOfClass(Player.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        players.forEach(player1 -> {
            if (player1.distanceTo(player) < 6) {
                playerShieldProvider(player1, 50, data.getInt(StringUtils.Ability.Intelligent) * 20);
                Compute.iceParticleCreate(player1);
                sendEffectLastTime(player, ModItems.SnowPower.get().getDefaultInstance(), 50);
            }
        });
        ParticleProvider.SpaceRangeParticle((ServerLevel) player.level(), player.getEyePosition(), 6, 36, ParticleTypes.SNOWFLAKE);

        if (Tool != null) playerItemCoolDown(player, Tool, SnowPower.CoolDownTime[level]);

        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_SNOW.get(), 1);
        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_SNOW.get(), 1);
        MySound.SoundToAll(player, ModSounds.Mana.get());
    }


    public static void EarthPower(Player player, boolean NotCost) {
        playerLastTimeReleasePower.put(player, 9);
        PlayerPowerRelease(player);
        if (!NotCost) Compute.playerItemCoolDown(player, ModItems.EarthPower.get(), 15);
        ModNetworking.sendToClient(new EarthPowerS2CPacket(), (ServerPlayer) player);
    }

    public static void PlayerReleasePowerType(Player player, int index) {

    }

    public static void PlayerPowerEffectToMob(Player player, Mob mob) {

    }

    public static void PlayerPowerRelease(Player player) {
        CompoundTag data = player.getPersistentData();

        ChargingModule(data, player);
        ManaSkill12Attack(data, player); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）
        ManaSkill13Attack(data, player); // 法术收集（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将基于目标周围实体数量提供至多1000%的范围伤害，并回复自身50%的法力值）
        if (Compute.ManaSkillLevelGet(player.getPersistentData(), 11) == 10) CastleManaArmor.NormalAttack(player);

    }
}
