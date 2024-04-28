package com.very.wraq.customized.players.sceptre.FengXiaoju;

import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Struct.CalculateDamage;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class FengXiaoJu {
    public static void FengXiaoJuPassive(Player player, Mob mob, double Damage) {
        if (Utils.Fengxiaoju != null && Utils.Fengxiaoju.equals(player) && Utils.FengxiaojuCurios) {
            int TickCount = player.getServer().getTickCount();
            Level level = player.level();
            if (!Utils.FengxiaojuPassiveMap.containsKey(mob)) Utils.FengxiaojuPassiveMap.put(mob,new ArrayList<>());
            List<CalculateDamage> damageList = Utils.FengxiaojuPassiveMap.get(mob);
            damageList.add(new CalculateDamage(TickCount,Damage));
            damageList.removeIf(calculateDamage -> (TickCount - calculateDamage.tick()) > 60);
            double DamageCalculate = 0;
            for (CalculateDamage calculateDamage : damageList) {
                DamageCalculate += calculateDamage.damage();
            }
            if (DamageCalculate > mob.getMaxHealth() * 0.35) {
                if (!Utils.FengxiaojuPassiveEffecetMap.containsKey(mob)) {
                    Utils.FengxiaojuPassiveEffecetMap.put(mob,true);
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
                    lightningBolt.setVisualOnly(true);
                    lightningBolt.setCause((ServerPlayer) player);
                    lightningBolt.moveTo(mob.position());
                    level.addFreshEntity(lightningBolt);
                    Compute.Damage.ManaDamageToMonster_ApDamage(player, mob,mob.getMaxHealth() * 0.2);
                    ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_LIGHTNINGISLAND.get(), 1);
                    ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_LIGHTNINGISLAND.get(), 1);
                    List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(),15,15,15));
                    mobList.forEach(mob1 -> {
                        if (mob1.distanceTo(mob) < 6) {
                            Compute.Damage.ManaDamageToMonster_ApDamage(player,mob1,50);
                        }
                    });
                }
            }
        }
    }
    public static void FengxiaojuPassiveNormalAttack(Player player) {
        if (Utils.Fengxiaoju != null && Utils.Fengxiaoju.equals(player) && Utils.FengxiaojuCurios) {
            int TickCount = player.getServer().getTickCount();
            Utils.FengxiaojuPassiveTick = TickCount + 160;
            if (Utils.FengxiaojuPassiveTick < TickCount) Utils.FengxiaojuPassiveCount = 0;
            if (Utils.FengxiaojuPassiveCount < 5) Utils.FengxiaojuPassiveCount ++;
            Compute.EffectLastTimeSend(player, ModItems.FengxiaojuCurios1.get().getDefaultInstance(),160,Utils.FengxiaojuPassiveCount);
        }
    }
}
