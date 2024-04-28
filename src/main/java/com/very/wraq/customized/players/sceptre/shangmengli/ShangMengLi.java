package com.very.wraq.customized.players.sceptre.shangmengli;

import com.very.wraq.coreAttackModule.ManaAttackModule;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import com.very.wraq.valueAndTools.registry.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShangMengLi {
    public static Map<Item,Integer> ItemCoolDownTime = new HashMap<>();
    public static void CoolDownRecord(Player player, Item item, int tick) {
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore1) {
            ItemCoolDownTime.put(item,tick);
        }
    }
    public static void DoubleManaAttack(Player player) {
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player)) {
            if (Utils.ShangMengLiCountsLastTick < player.getServer().getTickCount()) Utils.ShangMengLiCounts = 0;
            if (Utils.ShangMengLiDoubleManaAttackDelay > 0) Utils.ShangMengLiDoubleManaAttackDelay --;
            if (Utils.ShangMengLiDoubleManaAttackDelay == 0) {
                Utils.ShangMengLiDoubleManaAttackDelay --;
                Shoot(player);
            }
            if (Utils.ShangMengLiManaSkillDelay == player.getServer().getTickCount()) {
                List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),15,15,15));
                mobList.forEach(mob -> {
                    if (mob.distanceTo(player) < 6) {
                        Compute.Damage.ManaDamageToMonster_RateApDamage(player,mob,4,false);
                        Compute.PlayerManaAddOrCost(player, (int) (Compute.PlayerAttributes.PlayerMaxMana(player) * 0.5));ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 2, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1.75, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1.5, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1.25, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                    }
                });
                ParticleProvider.GatherParticle(player.position(), (ServerLevel) player.level(), 1, 6, 120, ModParticles.LONG_LAKE.get(), 0.25);
                ParticleProvider.GatherParticle(player.position(), (ServerLevel) player.level(), 1.5, 6, 120, ModParticles.LONG_LAKE.get(), 0.25);
                Compute.SoundToAll(player, SoundEvents.WATER_AMBIENT);
            }
            if (Utils.ShangMengLiActiveTick > 0) {
                if (Utils.ShangMengLiActiveTick % 2 == 0) {
                    ServerPlayer serverPlayer = (ServerPlayer) player;
                    CompoundTag data = serverPlayer.getPersistentData();
                    List<Mob> mobList = serverPlayer.level().getEntitiesOfClass(Mob.class, AABB.ofSize(serverPlayer.position(),15,15,15));
                    mobList.forEach(mob -> {
                        if (Compute.ManaSkillLevelGet(data, 11) > 0) {
                            Compute.Damage.ManaDamageToMonster_RateApDamage(serverPlayer,mob,10,false);
                        }
                        else {
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SEA.get(), serverPlayer, serverPlayer.level(),
                                    Compute.PlayerAttributes.PlayerManaDamage(serverPlayer),
                                    Compute.PlayerAttributes.PlayerManaPenetration(serverPlayer),
                                    Compute.PlayerAttributes.PlayerManaPenetration0(serverPlayer), StringUtils.ParticleTypes.Sea);
                            ManaAttackModule.BasicAttack(serverPlayer,mob,Compute.PlayerAttributes.PlayerManaDamage(serverPlayer),
                                    Compute.PlayerAttributes.PlayerManaPenetration(serverPlayer),
                                    Compute.PlayerAttributes.PlayerManaPenetration0(serverPlayer),serverPlayer.level(),newArrow);
                        }
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 2, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1.75, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1.5, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1.25, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.FALLING_WATER, 0);
                    });
                    ParticleProvider.DisperseParticle(serverPlayer.position(), (ServerLevel) serverPlayer.level(), 1, 1, 120, ModParticles.LONG_LAKE.get(), 1);
                    ParticleProvider.DisperseParticle(serverPlayer.position(), (ServerLevel) serverPlayer.level(), 1.5, 1, 120, ModParticles.LONG_LAKE.get(), 1);
                    Compute.SoundToAll(serverPlayer, SoundEvents.WATER_AMBIENT);
                }
                Utils.ShangMengLiActiveTick --;
            }
        }
    }
    public static double FirstMana(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShangMengLi_Sceptre.get())) {
            if (Utils.ShangMengLiManaCount) {
                return Compute.PlayerAttributes.PlayerManaDamage(player) * 2;
            }
        }
        return 0;
    }
    public static double SecondMana(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShangMengLi_Sceptre.get())) {
            if (!Utils.ShangMengLiManaCount) {
                if (Utils.ShangMengLiCounts < 5) Utils.ShangMengLiCounts ++;
                Utils.ShangMengLiCountsLastTick = player.getServer().getTickCount() + 100;
                Compute.EffectLastTimeSend(player,ModItems.ShangMengLi_Sceptre.get().getDefaultInstance(),100,Utils.ShangMengLiCounts);
                return Compute.PlayerAttributes.PlayerManaDamage(player) * 2;
            }
        }
        return 0;
    }
    public static void ShangMengLiCore1Count(Player player) {
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore1) {
            CompoundTag data = player.getPersistentData();
            if (Compute.ManaSkillLevelGet(data,10) == 10) {
                Utils.ShangMengLiCore1Count += 10;
                if (Utils.ShangMengLiCore1Count == 100) {
                    Shoot(player);
                    Utils.ShangMengLiCore1Temperature ++;
                    Utils.ShangMengLiCore1Count = 0;
                }
                if (Utils.ShangMengLiCore1Temperature == 3) {
                    Utils.ShangMengLiCore1TemperatureShoot = true;
                }
                if (Utils.ShangMengLiCore1TemperatureShoot) {
                    Utils.ShangMengLiCore1Temperature --;
                    Shoot(player);
                    if (Utils.ShangMengLiCore1Temperature == 0)
                        Utils.ShangMengLiCore1TemperatureShoot = false;
                }
                Compute.EffectLastTimeSend(player,ModItems.ShangMengLiCurios11.get().getDefaultInstance(),8888,Utils.ShangMengLiCore1Count,true);
                Compute.EffectLastTimeSend(player,ModItems.ShangMengLiCurios1.get().getDefaultInstance(),8888,Utils.ShangMengLiCore1Temperature,true);
            }
            if (Compute.ManaSkillLevelGet(data,11) == 10) {
                Utils.PowerTag.keySet().forEach(item -> {
                    if (ItemCoolDownTime.containsKey(item)) {
                        float rate = player.getCooldowns().getCooldownPercent(item,1);
                        int CurrentTick = (int) (ItemCoolDownTime.get(item) * rate);
                        player.getCooldowns().removeCooldown(item);
                        player.getCooldowns().addCooldown(item,Math.max(0,CurrentTick - 5));
                        ItemCoolDownTime.put(item,CurrentTick - 5);
                    }
                });
            }
        }
    }
    public static void Shoot(Player player) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SEA.get(), player, level,
                Compute.PlayerAttributes.PlayerManaDamage(player), Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Sea);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.DRIPPING_WATER);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.DRIPPING_WATER);
        Compute.SoundToAll(player, ModSounds.Mana.get());
    }
    public static double ShangMengLiCurios1(Player player) {
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore1Temperature > 0) {
            return Utils.ShangMengLiCore1Temperature * 0.33;
        }
        return 0;
    }
}
