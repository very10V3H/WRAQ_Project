package fun.wraq.process.func.damage;

import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.particle.ParticleProvider;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SputteringDamage {

    public static List<SputteringDamage> sputteringDamages = new ArrayList<>();

    private final List<Mob> causedDamageMob = new ArrayList<>();
    private final List<Mob> nextCauseMob = new Vector<>();

    private int generation;
    private final Mob originMob;
    private final Player originPlayer;
    private final int originTick;
    private final double originDamage;
    private final int damageType;
    private final int maxGeneration;
    private final Style sputterStyle;

    public SputteringDamage(Mob originMob, Player originPlayer, int originTick, double originDamage,
                            int damageType, int maxGeneration, Style sputterStyle) {
        this.originMob = originMob;
        this.originPlayer = originPlayer;
        this.originTick = originTick;
        this.originDamage = originDamage;
        this.damageType = damageType;
        this.generation = 0;
        this.maxGeneration = maxGeneration;
        this.sputterStyle = sputterStyle;
        nextCauseMob.add(originMob);
    }

    public void sputter() {
        if (originPlayer != null) {
            int tick = Tick.get();
            if ((tick - this.originTick) % 5 == 0 && tick - this.originTick != 0) {
                if (this.generation == maxGeneration) {
                    nextCauseMob.forEach(mob -> {
                        if (!causedDamageMob.contains(mob) && !mob.equals(this.originMob)) {
                            if (this.damageType == 0)
                                Damage.causeAttackDamageToMonster_AdDamage_Direct(originPlayer, mob,
                                        this.originDamage / (2 * (this.generation + 1)), false);
                            if (this.damageType == 1)
                                Damage.causeManaDamageToMonster_ApDamage_Direct(originPlayer, mob,
                                        this.originDamage / (2 * (this.generation + 1)), false);
                            if (this.damageType == 2)
                                Damage.causeTrueDamageToMonster(originPlayer, mob,
                                        this.originDamage / (2 * (this.generation + 1)));
                        }
                        causedDamageMob.add(mob);
                    });
                    ++this.generation;
                }

                if (this.generation < maxGeneration) {
                    List<Mob> addMobs = new ArrayList<>();

                    nextCauseMob.forEach(mob -> {
                        if (causedDamageMob.contains(mob)) return;
                        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 30, 30, 30));
                        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 9 || causedDamageMob.contains(mob1)
                                || nextCauseMob.contains(mob1) || addMobs.contains(mob1));
                        mobList.forEach(mob1 -> {
                            ParticleProvider.createLineEffectParticle(mob.level(), (int) mob1.distanceTo(mob) * 5,
                                    mob.getEyePosition(), mob1.getEyePosition(), sputterStyle);
                        });
                        addMobs.addAll(mobList);
                        if (this.generation > 0 && !mob.equals(this.originMob)) {
                            if (this.damageType == 0)
                                Damage.causeAttackDamageToMonster_AdDamage_Direct(originPlayer, mob,
                                        this.originDamage / (2 * (this.generation + 1)), false);
                            if (this.damageType == 1)
                                Damage.causeManaDamageToMonster_ApDamage_Direct(originPlayer, mob,
                                        this.originDamage / (2 * (this.generation + 1)), false);
                            if (this.damageType == 2)
                                Damage.causeTrueDamageToMonster(originPlayer, mob,
                                        this.originDamage / (2 * (this.generation + 1)));
                        }
                        causedDamageMob.add(mob);
                    });

                    nextCauseMob.addAll(addMobs);
                    ++this.generation;
                }
            }
        }
    }

    public static void addSputteringDamageOnMob(Mob target, Player originPlayer, double originDamage,
                                                int damageType, int maxGeneration, Style style) {
        sputteringDamages.add(new SputteringDamage(target, originPlayer, Tick.get(),
                originDamage, damageType, maxGeneration, style));
    }

    public static void handleServerTick() {
        sputteringDamages.forEach(SputteringDamage::sputter);
        sputteringDamages.removeIf(sputteringDamage -> sputteringDamage.generation > sputteringDamage.maxGeneration);
    }
}
