package fun.wraq.process.func;

import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class MobEffectAndDamageMethods {

    public record DelayParticleAttack(Mob mob, Vec3 startPos, Vec3 endPos, double range, int releaseTick, double damage,
                                      int damageType, ParticleOptions particleOptions,
                                      ParticleOptions particleOptions1) {
    }
    // damageType : 0 - AttackDamage 1 - ManaDamage 2 - DamageIgnoreDefence

    public static List<DelayParticleAttack> delayParticleAttackList = new ArrayList<>();

    public static void Tick(Level level) {
        DelayLineParticleAttackTick(level);
        DelayCircleParticleAttackTick(level);
    }

    public static void DelayLineParticleAttack(Mob mob, Vec3 startPos, Vec3 endPos, double range, int releaseTick, double damage, int damageType, ParticleOptions particleOptions, ParticleOptions particleOptions1) {
        DelayParticleAttack delayParticleAttack = new DelayParticleAttack(mob, startPos, endPos, range, Tick.get() + releaseTick, damage, damageType, particleOptions, particleOptions1);
        delayParticleAttackList.add(delayParticleAttack);
    }

    public static void DelayLineParticleAttackTick(Level level) {
        int tickCount = Tick.get();
        delayParticleAttackList.forEach(d -> {
            if (d.releaseTick == tickCount) {
                double distance = d.endPos.distanceTo(d.startPos);
                List<Player> playerList = new ArrayList<>();
                Vec3 appendVec3 = d.endPos.subtract(d.startPos).normalize();

                for (double i = 1; i <= distance; i += 0.5) {
                    Vec3 judgeDot = d.startPos.add(appendVec3.scale(i));
                    List<Player> players = level.getEntitiesOfClass(Player.class, AABB.ofSize(judgeDot, d.range, d.range, d.range));
                    players.forEach(player -> {
                        if (!playerList.contains(player)) playerList.add(player);
                    });
                } // get players near judge particle

                playerList.forEach(player -> {
                    switch (d.damageType) {
                        case 0 -> Damage.causeAttackDamageToPlayer(d.mob, player, d.damage);

                        case 1 -> Damage.causeManaDamageToPlayer(d.mob, player, d.damage);

                        case 2 -> Damage.causeTrueDamageToPlayer(d.mob, player, d.damage);

                    }
                    BaseEffectProvide(d.mob, player);
                });
                ParticleProvider.createLineParticle(level, (int) (d.startPos.subtract(d.endPos).length() * 2), d.startPos.add(0, 1, 0), d.endPos.add(0, 1, 0), d.particleOptions1); // big size particle ? or else particle to display range of attack
                AdditionEffectProvide(d.mob);
            } else {
                ParticleProvider.createLineParticle(level, (int) (d.startPos.subtract(d.endPos).length() * 2), d.startPos.add(0, 1, 0), d.endPos.add(0, 1, 0), d.particleOptions);
            }
        });
        delayParticleAttackList.removeIf(d -> d.releaseTick <= tickCount);
    }

    public static List<DelayParticleAttack> delayCircleAttackList = new ArrayList<>();

    public static void DelayCircleParticleAttack(Mob mob, Vec3 startPos, double range, int releaseTick, double damage, int damageType, ParticleOptions particleOptions, ParticleOptions particleOptions1) {
        DelayParticleAttack delayParticleAttack = new DelayParticleAttack(mob, startPos, startPos, range, Tick.get() + releaseTick, damage, damageType, particleOptions, particleOptions1);
        delayCircleAttackList.add(delayParticleAttack);
    }

    public static void DelayCircleParticleAttackTick(Level level) {
        int TickCount = Tick.get();
        delayCircleAttackList.forEach(d -> {
            if (d.releaseTick == TickCount) {
                List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(d.startPos, 50, 50, 50));
                playerList.removeIf(player -> player.position().distanceTo(d.startPos) > d.range);
                playerList.forEach(player -> {
                    switch (d.damageType) {
                        case 0 -> Damage.causeAttackDamageToPlayer(d.mob, player, d.damage);

                        case 1 -> Damage.causeManaDamageToPlayer(d.mob, player, d.damage);

                        case 2 -> Damage.causeTrueDamageToPlayer(d.mob, player, d.damage);
                    }
                    BaseEffectProvide(d.mob, player);
                });
                ParticleProvider.DisperseParticle(d.startPos, (ServerLevel) level, 1, 1, 120, d.particleOptions1, (int) d.range / 2);
                AdditionEffectProvide(d.mob);
            } else {
                ParticleProvider.VerticleCircleParticle(d.startPos, (ServerLevel) level, 1, d.range, (int) (2 * Math.PI * d.range) * 6, d.particleOptions);
            }
        });
        delayCircleAttackList.removeIf(d -> d.releaseTick <= TickCount);
    }

    public record PlayerDamageDecrease(Mob mob, double rate, int lastTick) {
    }

    public static Map<String, List<PlayerDamageDecrease>> playerDamageDecreaseMap = new HashMap<>();

    public static void PlayerDamageDecreaseProvide(Mob mob, Player player, double rate, int lastTick, ItemStack displayItem) {
        if (!playerDamageDecreaseMap.containsKey(Name.get(player))) {
            playerDamageDecreaseMap.put(Name.get(player), new ArrayList<>());
        }
        List<PlayerDamageDecrease> list = playerDamageDecreaseMap.get(Name.get(player));
        list.add(new PlayerDamageDecrease(mob, rate, Tick.get() + lastTick));
    }

    public static double PlayerDamageDecreaseRate(Player player, Mob mob) {
        if (!playerDamageDecreaseMap.containsKey(Name.get(player))) return 0;
        List<PlayerDamageDecrease> list = playerDamageDecreaseMap.get(Name.get(player));
        list.removeIf(p -> p.lastTick <= Tick.get());
        double rate = 1;
        for (PlayerDamageDecrease playerDamageDecrease : list) {
            rate *= (1 - playerDamageDecrease.rate);
        }
        return 1 - rate;
    }

    public static void BaseEffectProvide(Mob mob, Player player) {

    }

    public static void AdditionEffectProvide(Mob mob) {

    }
}
