package com.very.wraq.customized.players.sceptre.liulixian_;

import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Random;

public class LiuLiXianCurios1F {
    public static int Count1 = 0;
    public static int Count2 = 0;
    public static int LastTimeCount1EffectAddIs = 0;
    public static int LastTimeCount2EffectAddIs = 0;
    public static int[] Count1StatusTick = {0,0};
    public static int[] Count2StatusTick = {0,0};
    public static int Skill3Tick = 0;
    public static int Skill4Tick = 0;
    public static boolean Skill5Flag = false;
    public static Mob Skill7Mob;
    public static int Skill7Tick = 0;
    public static int Skill8Tick = 0;
    public static int UltimateSkillTick = 0;
    public static int Skill1ShootDelay = 0;
    public static Player Player;
    public static Boolean isOn;
    public static void CountAdd(Player player, Mob mob) {
        int TickCount = player.getServer().getTickCount();
        Count1 ++;
        if (Count1 == 9) {
            Count1 = 0;
            Count2 ++;
            Toss(player,TickCount,mob);
        }
        if (Count2 == 9) {
            Count2 = 0;
            UltimateSkillTick = TickCount + 540;
            Compute.EffectLastTimeSend(player,ModItems.LiuLiXianCurios1.get().getDefaultInstance(),540);
        }
        Compute.EffectLastTimeSend(player,ModItems.LiuLiCount1.get().getDefaultInstance(),8888,Count1,true);
        Compute.EffectLastTimeSend(player,ModItems.LiuLiCount2.get().getDefaultInstance(),8888,Count2,true);
        Count12EffectAdd(TickCount);
    }
    public static void Count12EffectAdd(int TickCount) {
        for (int i = 0 ; i < 2 ; i ++) {
            if (Count1 == (i + 1) * 3 && LastTimeCount1EffectAddIs != (i + 1) * 3) {
                LastTimeCount1EffectAddIs = (i + 1) * 3;
                Count1StatusTick[i] = TickCount + 100;
            }
            if (Count2 == (i + 1) * 3 && LastTimeCount2EffectAddIs != (i + 1) * 3) {
                LastTimeCount2EffectAddIs = (i + 1) * 3;
                Count2StatusTick[i] = TickCount + 100;
            }
        }
    }
    public static void Toss(Player player, int TickCount, Mob mob) {
        Random random = new Random();
        switch (random.nextInt(1,9)) {
            case 1 -> Skill1(player);
            case 2 -> Skill2(player);
            case 3 -> Skill3(player,TickCount);
            case 4 -> Skill4(player,TickCount);
            case 5 -> Skill5(player);
            case 6 -> Skill6(player);
            case 7 -> Skill7(player,mob,TickCount);
            case 8 -> Skill8(player,mob,TickCount);
        }
    }
    public static void Skill1(Player player) {
        Compute.EffectLastTimeSend(player, ModItems.LiuLiSkill1.get().getDefaultInstance(),6);
        Skill1ShootDelay = 6;
    }

    public static int skill2Tick = 0;
    public static void Skill2(Player player) {
        Compute.EffectLastTimeSend(player, ModItems.LiuLiSkill2.get().getDefaultInstance(),180);
        skill2Tick = player.getServer().getTickCount() + 180;
    }
    public static double PenetrationUp(Player player) {
        if (!IsLiuLiXian(player) || skill2Tick < player.getServer().getTickCount()) return 1;
        return 1.5;
    }
    public static void Skill3(Player player, int TickCount) {
        Compute.EffectLastTimeSend(player, ModItems.LiuLiSkill3.get().getDefaultInstance(),180);
        Skill3Tick = TickCount + 180;
    }
    public static void Skill4(Player player, int TickCount) {
        Compute.EffectLastTimeSend(player, ModItems.LiuLiSkill4.get().getDefaultInstance(),180);
        Skill4Tick = TickCount + 180;
    }
    public static void Skill5(Player player) {
        Compute.EffectLastTimeSend(player, ModItems.LiuLiSkill5.get().getDefaultInstance(),8888,0,true);
        Skill5Flag = true;
    }
    public static void Skill6(Player player) {
        player.setHealth(player.getHealth() / 2);
    }
    public static void Skill7(Player player, Mob mob, int TickCount) {
        Skill7Mob = mob;
        Skill7Tick = TickCount + 180;
        Compute.EffectLastTimeSend(player, ModItems.LiuLiSkill7.get().getDefaultInstance(),180,0);
    }
    public static void Skill8(Player player, Mob mob, int TickCount) {
        Skill8Tick = TickCount + 180;
        Compute.EffectLastTimeSend(player, ModItems.LiuLiSkill8.get().getDefaultInstance(),180,0);
    }
    public static void Skill8Damage(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Skill8Tick > TickCount) {
            List<Player> playerList = player.level().getEntitiesOfClass(net.minecraft.world.entity.player.Player.class,
                    AABB.ofSize(player.position(),15,15,15));
            playerList.forEach(player1 -> {
                if (player1.distanceTo(player) < 6) {
                    Compute.PlayerHealthDecrease(player1,player1.getHealth() * 0.005,0.5);
                }
            });
        }
    }
    public static void Skill1Shoot(Player player) {
        if (Skill1ShootDelay > 0) {
            if (Skill1ShootDelay % 2 == 0) {
                Skill1ShootDelay --;
                Shoot(player);
            }
        }
    }
    public static void Shoot(Player player) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player, level,
                Compute.PlayerAttributes.PlayerManaDamage(player),
                Compute.PlayerAttributes.PlayerManaPenetration(player),
                Compute.PlayerAttributes.PlayerManaPenetration0(player), StringUtils.ParticleTypes.Sea);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        level.addFreshEntity(newArrow);
    }

    public static double LiuLiXianDamageCompute(Player player, double damage) {
        int TickCount = player.getServer().getTickCount();
        if (UltimateSkillTick > TickCount) {
            if (damage > player.getMaxHealth() * 0.1) Compute.FormatMSGSend(player,Component.literal("墨仙").withStyle(CustomStyle.styleOfSakura),
                    Component.literal("为你守护了" + String.format("%.0f", (damage - player.getMaxHealth() * 0.1))));
            return Math.min(player.getMaxHealth() * 0.1,damage);
        } else if (Count2StatusTick[0] > TickCount) {
            if (damage > player.getMaxHealth() * 0.2) Compute.FormatMSGSend(player,Component.literal("墨仙").withStyle(CustomStyle.styleOfSakura),
                    Component.literal("为你守护了" + String.format("%.0f", (damage - player.getMaxHealth() * 0.2))));
            return Math.min(player.getMaxHealth() * 0.2,damage);
        } else if (Count1StatusTick[0] > TickCount) {
            if (damage > player.getMaxHealth() * 0.3) Compute.FormatMSGSend(player,Component.literal("墨仙").withStyle(CustomStyle.styleOfSakura),
                    Component.literal("为你守护了" + String.format("%.0f", (damage - player.getMaxHealth() * 0.3))));
            return Math.min(player.getMaxHealth() * 0.3,damage);
        }
        return damage;
    }

    public static void LiuLiXianIgnoreDefenceDamage(Player player, Mob mob, double OriginalDamage) {
        int TickCount = player.getServer().getTickCount();
        if (UltimateSkillTick > TickCount) {
            Compute.Damage.DamageIgNoreDefenceToMonster(player,mob,OriginalDamage * 0.5);
        }
        else if (Count2StatusTick[1] > TickCount) Compute.Damage.DamageIgNoreDefenceToMonster(player,mob,OriginalDamage * 0.2);
        else if (Count1StatusTick[1] > TickCount) Compute.Damage.DamageIgNoreDefenceToMonster(player,mob,OriginalDamage * 0.1);
    }

    public static double LiuLiXianTotalDamageEnhance(Player player) {
        double rate = 1;
        int TickCount = player.getServer().getTickCount();
        if (Skill3Tick > TickCount) rate += 0.2;
        if (UltimateSkillTick > TickCount) rate += 1;
        return rate;
    }

    public static boolean IsLiuLiXian(Player player) {
        return Player != null && Player.equals(player) && isOn;
    }

    public static double Skill4ExDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Skill4Tick > TickCount) return Compute.XpStrengthAPDamage(player,1);
        return 0;
    }

    public static double Skill5BaseDamage(Player player) {
        if (Skill5Flag) {
            Skill5Flag = false;
            Compute.EffectLastTimeSend(player, ModItems.LiuLiSkill5.get().getDefaultInstance(),0);
            return 0;
        }
        return 1;
    }

    public static void Passive(Player player) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),25,25,25));
        mobList.forEach(mob -> {
            if (mob.distanceTo(player) < 10) {
                if (Compute.PlayerAttributes.PlayerAttackDamage(player) * 4 > Compute.PlayerAttributes.PlayerManaDamage(player)) {
                    Compute.Damage.AttackDamageToMonster_RateAdDamage(player,mob,10);
                }
                else Compute.Damage.ManaDamageToMonster_RateApDamage(player,mob,2.5,false);
                ParticleProvider.LineParticle(player.level(),20,player.position(),mob.position(), ModParticles.LONG_ENTROPY.get());
            }
        });
    }

    public static void Tick(Player player) {
        if (IsLiuLiXian(player)) {
            int TickCount = player.getServer().getTickCount();
            if (TickCount % 10 == 0) Passive(player);
            Skill1Shoot(player);
            if (UltimateSkillTick > TickCount && TickCount % 4 == 0)
                ParticleProvider.VerticleCircleParticle((ServerPlayer) player,0.2,1,20, ParticleTypes.CHERRY_LEAVES);
        }
    }

}
