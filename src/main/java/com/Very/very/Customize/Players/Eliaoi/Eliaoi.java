package com.Very.very.Customize.Players.Eliaoi;

import com.Very.very.CoreAttackModule.ManaAttackModule;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.Projectile.Mana.ManaArrow;
import com.Very.very.Projectile.Mana.NewArrowMagma;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.ModEntityType;
import com.Very.very.ValueAndTools.registry.ModItems;
import com.Very.very.ValueAndTools.registry.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Eliaoi {
    public static Player Player;
    public static boolean Curios = false;
    public static int EliaoiIsInAttack = 0;
    public static int EliaoiManaExpand = 0;
    public static int EliaoiLaserTick = 0;
    public static Map<Mob,Integer> EliaoiMobMap = new HashMap<>();
    public static Map<Mob,Integer> EliaoiMobCountMap = new HashMap<>();
    public static Map<Player,Integer> EliaoiPlayerTickMap = new HashMap<>();

    public static void EliaoiMobCount(Player player, Mob mob) {
        if (EliaoiMobMap.containsKey(mob) && Player != null && Player.equals(player) && Curios) {
            int TickCount = player.getServer().getTickCount();
            EliaoiMobCountMap.put(mob,EliaoiMobCountMap.getOrDefault(mob,0) + 1);
            if (EliaoiMobCountMap.get(mob) == 7) {
                EliaoiMobCountMap.put(mob,0);
                List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class,AABB.ofSize(mob.position(),15,15,15));
                mobList.forEach(mob1 -> {
                    if (mob1.distanceTo(mob) < 6) {
                        Compute.Damage.ManaDamageToMonster_RateApDamage(player,mob1,10,false);
                    }
                });
                List<Player> playerList = mob.level().getEntitiesOfClass(net.minecraft.world.entity.player.Player.class,AABB.ofSize(mob.position(),30,30,30));
                playerList.removeIf(player1 -> player1.distanceTo(player) > 10);
                playerList.forEach(player1 -> {
                    EliaoiPlayerTickMap.put(player1,TickCount + 100);
                    Compute.PlayerShieldProvider(player1,40,Compute.PlayerAttributes.PlayerManaDamage(player) * 0.25);
                    Compute.EffectLastTimeSend(player1,ModItems.EliaoiCurios.get().getDefaultInstance(),100);
                });
                ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_RED_SPELL.get(), 2);
                ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_RED_SPELL.get(), 2);
            }
        }
    }
    public static double EliaoiEffect(Player player, Mob mob, boolean add) {
        int TickCount = player.getServer().getTickCount();
        if (add && player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.EliaoiBook.get()) && Curios) {
            EliaoiMobMap.put(mob,TickCount + 40);
            Compute.Damage.LastXpStrengthDamageToMob(player,mob,1,40,10,false);
            mob.setSecondsOnFire(2);
            Compute.AddSlowDownEffect(mob,40,3);
        }
        if (EliaoiMobMap.containsKey(mob) && EliaoiMobMap.get(mob) < TickCount) return 1;
        return 0;
    }

    public static void EliaoiAttack(Player player) {
        if (Eliaoi.Player != null && Eliaoi.Player.equals(player)) {
            if (EliaoiIsInAttack > 0) {
                if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.EliaoiBook.get())) {
                    if (EliaoiManaExpand < 20) EliaoiManaExpand ++;
                    ParticleCreate(player,EliaoiManaExpand);
                    /*if (player.tickCount % 10 == 0 && !player.isShiftKeyDown()) Shoot(player);*/
                    if (EliaoiManaExpand == 20) Compute.Laser(player,ModParticles.RED_SPELL.get(), Compute.PlayerAttributes.PlayerManaDamage(player), 5);
                    if (EliaoiManaExpand == 20 && player.tickCount % 20 == 0) {
                        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,
                                AABB.ofSize(player.position(), 20, 20, 20));
                        mobList.forEach(mob -> {
                            Vec3 PosVec = mob.position().subtract(player.position());
                            if (PosVec.length() <= 6) {
                                if (!Compute.MonsterCantBeMove(mob)) mob.setDeltaMovement(PosVec.normalize().scale(Math.min(2, 6 / PosVec.length())));
                                Compute.Damage.ManaDamageToMonster_RateApDamage(player,mob,10,false);
                            }
                        });
                    }
                }
                EliaoiIsInAttack --;
            }
            if (EliaoiIsInAttack == 0) EliaoiManaExpand = 0;
        }
    }

    public static void ParticleCreate(Player player,double rate) {
        Level level = player.level();
        ParticleProvider.VerticleCircleParticle((ServerPlayer) player,0,1.3,80, ModParticles.RED_SPELL.get());
        if (rate == 20) {
            Compute.BallParticle((ServerPlayer) player, player.position(),6, ModParticles.RED_SPELL.get(), 50);
        }
        if (rate < 20) {
            double r = 1.5 * 20 / (20 - rate);
            Vec3 Dot1 = new Vec3(0,0,r).add(player.position());
            Vec3 Dot2 = new Vec3(Math.sqrt(3) * r / 2, 0, - 0.5 * r).add(player.position());
            Vec3 Dot3 = new Vec3(- Math.sqrt(3) * r / 2, 0 , - 0.5 * r).add(player.position());
            ParticleProvider.LineParticle(level,20,Dot1,Dot2,ModParticles.RED_SPELL.get());
            ParticleProvider.LineParticle(level,20,Dot2,Dot3,ModParticles.RED_SPELL.get());
            ParticleProvider.LineParticle(level,20,Dot3,Dot1,ModParticles.RED_SPELL.get());

            Vec3 Dot4 = new Vec3(0,0 ,- r).add(player.position());
            Vec3 Dot5 = new Vec3(- Math.sqrt(3) * r / 2, 0, 0.5 * r).add(player.position());
            Vec3 Dot6 = new Vec3(Math.sqrt(3) * r / 2, 0, 0.5 * r).add(player.position());
            ParticleProvider.LineParticle(level,20,Dot4,Dot5,ModParticles.RED_SPELL.get());
            ParticleProvider.LineParticle(level,20,Dot5,Dot6,ModParticles.RED_SPELL.get());
            ParticleProvider.LineParticle(level,20,Dot6,Dot4,ModParticles.RED_SPELL.get());
        }

        double r = 1.5 * 20 / (rate);
        Vec3  Dot1 = new Vec3(0,0,r).add(player.position());
        Vec3 Dot2 = new Vec3(Math.sqrt(3) * r / 2, 0, - 0.5 * r).add(player.position());
        Vec3  Dot3 = new Vec3(- Math.sqrt(3) * r / 2, 0 , - 0.5 * r).add(player.position());
        ParticleProvider.LineParticle(level,20,Dot1,Dot2,ModParticles.RED_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot2,Dot3,ModParticles.RED_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot3,Dot1,ModParticles.RED_SPELL.get());

        Vec3  Dot4 = new Vec3(0,0 ,- r).add(player.position());
        Vec3  Dot5 = new Vec3(- Math.sqrt(3) * r / 2, 0, 0.5 * r).add(player.position());
        Vec3  Dot6 = new Vec3(Math.sqrt(3) * r / 2, 0, 0.5 * r).add(player.position());
        ParticleProvider.LineParticle(level,20,Dot4,Dot5,ModParticles.RED_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot5,Dot6,ModParticles.RED_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot6,Dot4,ModParticles.RED_SPELL.get());
    }
    public static void Shoot(Player player) {
        Level level = player.level();
        NewArrowMagma newArrow = new NewArrowMagma(player, level,
                Compute.PlayerAttributes.PlayerManaDamage(player),
                Compute.PlayerAttributes.PlayerManaPenetration(player),
                Compute.PlayerExpUp(player),
                Compute.PlayerAttributes.PlayerManaPenetration0(player));
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.LONG_RED_SPELL.get());

        Compute.SoundToAll(player, ModSounds.Mana.get());
    }
    public static void Laser(Player player) {
        if (Eliaoi.EliaoiManaExpand == 20) {
            int TickCount = player.getServer().getTickCount();
            if (Eliaoi.EliaoiLaserTick < TickCount) {
                Eliaoi.EliaoiLaserTick = TickCount + 20;
                Level level = player.level();
                Vec3 TargetPos = player.pick(25,0,false).getLocation();
                Vec3 StartPos = player.pick(0.5,0,false).getLocation();
                Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
                double Distance = TargetPos.distanceTo(StartPos);
                ParticleProvider.LineParticle(level,(int) Distance * 5, StartPos, TargetPos, ModParticles.LONG_RED_SPELL.get());
                List<Mob> mobList = new ArrayList<>();
                for (double i = 0 ; i < Distance ; i += 0.5) {
                    List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)),0.5,0.5,0.5));
                    for (Mob mob : mobList1) {
                        if (!mobList.contains(mob)) mobList.add(mob);
                    }
                }
                mobList.forEach(mob -> {
                    ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player, level,
                            Compute.PlayerAttributes.PlayerManaDamage(player),
                            Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),2);
                    ManaAttackModule.BasicAttack(player,mob,Compute.PlayerAttributes.PlayerManaDamage(player) * 10,
                            Compute.PlayerAttributes.PlayerManaPenetration(player),
                            Compute.PlayerAttributes.PlayerManaPenetration0(player),
                            level,newArrow);
                });
                Compute.CoolDownTimeSend(player,ModItems.EliaoiBook.get().getDefaultInstance(),20);
            }
        }
    }
}
