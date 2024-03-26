package com.Very.very.Process.Power;

import com.Very.very.Customize.Players.Sora_vanilla.SoraVanilla2;
import com.Very.very.Customize.Players.very_new.VeryNew;
import com.Very.very.NetWorking.EarthPowerS2CPacket;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.ParticlePackets.DamageDecreaseParticleS2CPacket;
import com.Very.very.NetWorking.Packets.ParticlePackets.SlowDownParticleS2CPacket;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.ForestPower;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.ForestPowerEffectMob;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.PlainPower;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.SnowPower;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.VolcanoPower;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.LakePower;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.LakePowerEffect;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import com.Very.very.ValueAndTools.registry.ModSounds;
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

import static com.Very.very.ValueAndTools.Compute.*;

public class PowerLogic {

    public static Map<Player,Integer> playerLastTimeReleasePower = new HashMap<>();

    public static Map<Player,Map<Item,Integer>> playerPowerCoolDownRecord = new HashMap<>();
    public static Map<Player,Integer> playerLastTimeReleasePowerCoolDownTime = new HashMap<>();
    public static Map<Player,Integer> playerLastTimeReleasePowerManaCost = new HashMap<>();

    public static void ReleaseLastTime(Player player) {
        int type = playerLastTimeReleasePower.getOrDefault(player,0);
        ReleaseByType(player,type);
    }

    public static void ReleaseByType(Player player, int type) {
        switch (type) {
            case 0 -> WitherBonePower(player,null);
            case 1 -> PiglinPower(player,null);
            case 2 -> WitherBoneMealPower(player,null);
            case 3 -> MagmaPower(player,null);
            case 4 -> PlainPower(player,null,3);
            case 5 -> ForestPower(player,null,3);
            case 6 -> LakePower(player,null,3);
            case 7 -> VolcanoPower(player,null,3);
            case 8 -> SnowPower(player,null,3);
            case 9 -> EarthPower(player,true);
        }
    }


    public static void WitherBonePower(Player player, Item Tool) {
        playerLastTimeReleasePower.put(player,0);
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        if (data.contains("NetherRune") && data.getInt("NetherRune") == 2)
            data.putInt(StringUtils.NetherRune.NetherRune2, TickCount + 100);
        PlayerPowerRelease(player);
        if (Tool != null) PlayerItemCoolDown(player,Tool,10);
        Level level = player.level();
        Vec3 TargetPos = player.pick(15,0,false).getLocation();
        if (powerDetectPlayerPickMob(player) != null) TargetPos = powerDetectPlayerPickMob(player).position();
        List<Mob> monsterList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(TargetPos, 20, 20, 20));
        for (Mob mob : monsterList) {
            if (mob.getPosition(0).distanceTo(TargetPos) < 6) {
                Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 8,true);
                mob.getPersistentData().putInt("witherBonePower", 100);
                player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                        ModNetworking.sendToClient(new DamageDecreaseParticleS2CPacket(mob.getId(), 100), serverPlayer));

                Utils.witherBonePowerCCMonster.add(mob);
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2, false, false));
                player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                        ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 100), serverPlayer));

                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        }
        List<Player> playerList = level.getEntitiesOfClass(Player.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        ParticleProvider.VerticleCircleParticle(TargetPos,(ServerLevel) level,1,6,100,ParticleTypes.WITCH);
        ParticleProvider.VerticleCircleParticle(TargetPos,(ServerLevel) level,1.5,6,100,ParticleTypes.WITCH);
        Compute.SoundToAll(player, ModSounds.Nether_Power.get());
    }
    public static void PiglinPower(Player player, Item Tool) {
        playerLastTimeReleasePower.put(player,1);
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        PlayerPowerRelease(player);
        Compute.PlayerPowerParticle(player);
        if (Tool != null) PlayerItemCoolDown(player,Tool,10);
        if (data.contains("NetherRune") && data.getInt("NetherRune") == 2)
            data.putInt(StringUtils.NetherRune.NetherRune2, TickCount + 100);
        Level level = player.level();
        Vec3 TargetPos = player.pick(15,0,false).getLocation();
        if (powerDetectPlayerPickMob(player) != null) TargetPos = powerDetectPlayerPickMob(player).position();
        List<Mob> monsterList = level.getEntitiesOfClass(Mob.class,AABB.ofSize(TargetPos, 20, 20, 20));
        for (Mob mob : monsterList) {
            if (mob.getPosition(0).distanceTo(TargetPos) < 6) {
                Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, monsterList.size() * 2,true);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        }
        List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(TargetPos, 20, 20, 20));
        for (Player player1 : playerList) {
            if (player1.position().distanceTo(TargetPos) < 6) {
                Utils.PiglinPowerLastTick.put(player1, TickCount + 200);
                Utils.PiglinPowerNum.put(player1, playerList.size());
                Utils.PiglinPowerAp.put(player1, Compute.PlayerAttributes.PlayerManaDamage(player));
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
        ParticleProvider.VerticleCircleParticle(TargetPos,(ServerLevel) level,1,6,100,ParticleTypes.WITCH);
        ParticleProvider.VerticleCircleParticle(TargetPos,(ServerLevel) level,1.5,6,100,ParticleTypes.WITCH);

        Compute.SoundToAll(player, ModSounds.Nether_Power.get());
    }
    public static void WitherBoneMealPower(Player player, Item Tool) {
        playerLastTimeReleasePowerManaCost.put(player,Compute.PlayerMaxManaNum(player));
        playerLastTimeReleasePower.put(player,2);
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        PlayerPowerRelease(player);
        Compute.PlayerPowerParticle(player);
        if (Tool != null) PlayerItemCoolDown(player,Tool,10);
        if (data.contains("NetherRune") && data.getInt("NetherRune") == 2)
            data.putInt(StringUtils.NetherRune.NetherRune2, TickCount + 100);
        Level level = player.level();
        Vec3 TargetPos = player.pick(15,0,false).getLocation();
        if (powerDetectPlayerPickMob(player) != null) TargetPos = powerDetectPlayerPickMob(player).position();
        List<Mob> monsterList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(TargetPos, 20, 20, 20));
        for (Mob mob : monsterList) {
            if (mob.getPosition(0).distanceTo(TargetPos) < 6) {
                Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 20,true);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
                Utils.NetherBoneMealPowerEffectMap.put(mob, TickCount + 100);
                AddManaDefenceDescreaseEffectParticle(mob, 100);
            }
        }
        List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(TargetPos, 20, 20, 20));
        for (Player player1 : playerList) {
            if (player1.getPosition(0).distanceTo(TargetPos) < 6) {
                if (!player1.level().equals(player1.getServer().getLevel(Level.OVERWORLD)))
                    Compute.Damage.ManaDamageToPlayer(player, player1, 10);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        }
        player.getPersistentData().putInt("MANA", 0);
        Compute.ManaStatusUpdate(player);

        ParticleProvider.VerticleCircleParticle(TargetPos,(ServerLevel) level,1,6,100,ParticleTypes.WITCH);
        ParticleProvider.VerticleCircleParticle(TargetPos,(ServerLevel) level,1.5,6,100,ParticleTypes.WITCH);

        Compute.SoundToAll(player, ModSounds.Nether_Power.get());
    }
    public static void MagmaPower(Player player, Item Tool) {
        playerLastTimeReleasePower.put(player,3);
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        if (Tool != null) PlayerItemCoolDown(player,Tool,3);
        if (data.contains("NetherRune") && data.getInt("NetherRune") == 2)
            data.putInt(StringUtils.NetherRune.NetherRune2, TickCount + 100);
        data.putBoolean("MagmaPower", true);
        Compute.SoundToAll(player, ModSounds.Nether_Power.get());
    }
    public static void PlainPower(Player player, Item Tool, int level) {
        playerLastTimeReleasePower.put(player,4);
        CompoundTag data = player.getPersistentData();
        PlayerPowerRelease(player);
        Level Dimension = player.level();
        double AP = Compute.PlayerAttributes.PlayerManaDamage(player);
        int Effect = PlainPower.Effect[level];
        Vec3 TargetPos = player.pick(15,0,false).getLocation();
        if (powerDetectPlayerPickMob(player) != null) TargetPos = powerDetectPlayerPickMob(player).position();
        List<Mob> mobList = Dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        Vec3 finalTargetPos = TargetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                double Rate = 6;
                if (!MonsterCantBeMove(mob)) mob.setDeltaMovement(PosVec.normalize().scale(Math.min(2, 6 / PosVec.length())));
                Damage.ManaDamageToMonster_RateApDamage(player, mob, Effect,true);
            }
        });
        List<Player> playerList = Dimension.getEntitiesOfClass(Player.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        Vec3 finalTargetPos1 = TargetPos;
        playerList.forEach(player1 -> {
            if (player1.position().subtract(finalTargetPos1).length() <= 6) {
                Compute.PlayerHeal(player1, data.getInt(StringUtils.Ability.Intelligent) * 30);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
            }
        });
        if (Tool != null) PlayerItemCoolDown(player, Tool, PlainPower.CoolDownTime[level] - ArmorCount.LifeManaE(player));

        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_PLAIN.get(), 1);
        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_PLAIN.get(), 1);

        Compute.SoundToAll(player, ModSounds.Wind.get());
    }
    public static void ForestPower(Player player, Item Tool, int level) {
        playerLastTimeReleasePower.put(player,5);
        PlayerPowerRelease(player);
        Level Dimension = player.level();
        ServerLevel serverLevel = (ServerLevel) Dimension;
        double AP = Compute.PlayerAttributes.PlayerManaDamage(player);
        int Effect = ForestPower.Effect[level];
        Vec3 DesPos = player.pick(15, 0, true).getLocation();
        if (powerDetectPlayerPickMob(player) != null) DesPos = powerDetectPlayerPickMob(player).position();

        List<Mob> mobList = Dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(DesPos, 20, 20, 20));
        Vec3 finalDesPos = DesPos;
        mobList.forEach(mob -> {
            if (mob.position().subtract(finalDesPos).length() <= 8) {
                if (!MonsterCantBeMove(mob)) Utils.ForestPowerEffectMobList.add(new ForestPowerEffectMob(finalDesPos, 20, mob));
                AddSlowDownEffect(mob, 40, 2);
                Damage.ManaDamageToMonster_RateApDamage(player, mob, Effect,true);
            }
        });

        if (Tool != null) PlayerItemCoolDown(player, Tool, ForestPower.CoolDownTime[level] - ArmorCount.LifeManaE(player));

        ParticleProvider.GatherParticle(DesPos, (ServerLevel) player.level(), 1, 6, 120, ModParticles.LONG_FOREST.get(), 0.25);
        ParticleProvider.GatherParticle(DesPos, (ServerLevel) player.level(), 1.5, 6, 120, ModParticles.LONG_FOREST.get(), 0.25);

        Compute.SoundToAll(player, SoundEvents.GRASS_BREAK);
    }
    public static void LakePower(Player player, Item Tool, int level) {
        playerLastTimeReleasePower.put(player,6);
        int TickCount = player.getServer().getTickCount();
        PlayerPowerRelease(player);
        Level Dimension = player.level();
        double AP = Compute.PlayerAttributes.PlayerManaDamage(player);
        int Effect = LakePower.Effect[level];
        Vec3 TargetPos = player.pick(15,0,false).getLocation();
        if (powerDetectPlayerPickMob(player) != null) TargetPos = powerDetectPlayerPickMob(player).position();
        List<Mob> mobList = Dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        List<Player> playerList = Dimension.getEntitiesOfClass(Player.class, AABB.ofSize(TargetPos,20,20,20));

        Vec3 finalTargetPos1 = TargetPos;
        playerList.removeIf(player1 -> player1.position().distanceTo(finalTargetPos1) > 6);
        playerList.forEach(player1 -> {
            LakePower.playerDefendRateMap.put(player1,level);
            LakePower.playerDefendTickMap.put(player1,TickCount + 80);
            Compute.EffectLastTimeSend(player,ModItems.LakePower.get().getDefaultInstance(),80);
        });
        Vec3 finalTargetPos = TargetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                mob.setRemainingFireTicks(0);
                AddSlowDownEffect(mob, 40, 3);
                AddManaDefenceDescreaseEffectParticle(mob, 40);
                Utils.LakePowerEffectMobMap.put(mob, new LakePowerEffect(TickCount + 40, Effect));
            }
        });



        if (Tool != null) PlayerItemCoolDown(player, Tool, LakePower.CoolDownTime[level] - ArmorCount.ObsiManaE(player) * 0.75);

        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_LAKE.get(), 1);
        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_LAKE.get(), 1);
        Compute.SoundToAll(player, SoundEvents.WATER_AMBIENT);
    }
    public static void VolcanoPower(Player player, Item Tool, int level) {
        playerLastTimeReleasePower.put(player,7);
        PlayerPowerRelease(player);
        VeryNew.VolcanoPowerModifier(player);
        Level Dimension = player.level();
        ServerLevel serverLevel = (ServerLevel) Dimension;
        double AP = Compute.PlayerAttributes.PlayerManaDamage(player);
        int Effect = VolcanoPower.Effect[level];
        Vec3 TargetPos = player.pick(15,0,false).getLocation();
        if (powerDetectPlayerPickMob(player) != null) TargetPos = powerDetectPlayerPickMob(player).position();
        List<Mob> mobList = Dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        List<ServerPlayer> playerList = Dimension.getServer().getPlayerList().getPlayers();
        Vec3 finalTargetPos = TargetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                ParticleProvider.VerticleCircleParticle(mob.position(), serverLevel, 1, 2, 40, ModParticles.LONG_VOLCANO.get());
                ParticleProvider.VerticleCircleParticle(mob.position(), serverLevel, 1.5, 2, 40, ModParticles.LONG_VOLCANO.get());
                mob.setRemainingFireTicks(40);
                List<Mob> mobList1 = Dimension.getEntitiesOfClass(Mob.class,
                        AABB.ofSize(mob.position(), 10, 10, 10));
                mobList1.forEach(mob1 -> {
                    if (mob1.position().subtract(mob.position()).length() <= 2) {
                        Damage.ManaDamageToMonster_RateApDamage(player, mob1, Effect,true);
                        AddSlowDownEffect(mob, 40, 3);
                        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                                new ClientboundLevelParticlesPacket(ParticleTypes.EXPLOSION_EMITTER, true,
                                        mob1.getX(), mob1.getY(), mob1.getZ(),
                                        0, 0, 0, 0, 0);
                        playerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                    }
                });
            }
        });

        if (Tool != null) PlayerItemCoolDown(player, Tool, VolcanoPower.CoolDownTime[level] - ArmorCount.ObsiManaE(player) * 0.75);

        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_VOLCANO.get(), 1);
        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_VOLCANO.get(), 1);

        Compute.SoundToAll(player, SoundEvents.DRAGON_FIREBALL_EXPLODE);
    }
    public static void SnowPower(Player player, Item Tool, int level) {
        playerLastTimeReleasePower.put(player,8);
        PlayerPowerRelease(player);
        Level Dimension = player.level();
        double AP = Compute.PlayerAttributes.PlayerManaDamage(player);
        int Effect = SnowPower.Effect[level];
        Vec3 TargetPos = player.pick(15,0,false).getLocation();
        CompoundTag data = player.getPersistentData();

        if (powerDetectPlayerPickMob(player) != null) TargetPos = powerDetectPlayerPickMob(player).position();
        List<Mob> mobList = Dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        List<Player> players = Dimension.getEntitiesOfClass(Player.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        List<ServerPlayer> playerList = Dimension.getServer().getPlayerList().getPlayers();
        Vec3 finalTargetPos = TargetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                mob.setRemainingFireTicks(0);
                BlockPos blockPos = mob.blockPosition().above();
                if (Dimension.getBlockState(blockPos).is(Blocks.AIR)) {
                    Dimension.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                    Dimension.destroyBlock(blockPos, false);
                }
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 100, false, false, false));
                Damage.ManaDamageToMonster_RateApDamage(player, mob, Effect * 2,true);
            }
        });
        Vec3 finalTargetPos1 = TargetPos;
        players.forEach(player1 -> {
            if (player1.position().distanceTo(finalTargetPos1) < 6) {
                PlayerShieldProvider(player1,80,data.getInt(StringUtils.Ability.Intelligent) * 30);
                Compute.IceParticleCreate(player1,Dimension);
                EffectLastTimeSend(player, ModItems.SnowPower.get().getDefaultInstance(),80);
            }
        });
        if (Tool != null) PlayerItemCoolDown(player, Tool, SnowPower.CoolDownTime[level]);

        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_SNOW.get(), 1);
        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_SNOW.get(), 1);
        Compute.SoundToAll(player, ModSounds.Mana.get());
    }

    public static void EarthPower(Player player, boolean NotCost) {
        playerLastTimeReleasePower.put(player,9);
        PlayerPowerRelease(player);
        if (!NotCost) Compute.PlayerItemCoolDown(player,ModItems.EarthPower.get(), 15);
        ModNetworking.sendToClient(new EarthPowerS2CPacket(),(ServerPlayer) player);
    }

    public static void PlayerReleasePowerType(Player player, int index) {
        SoraVanilla2.ReleaseRecord(player,index);
    }
}
