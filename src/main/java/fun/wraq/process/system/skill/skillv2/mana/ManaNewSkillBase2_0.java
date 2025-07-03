package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.customized.uniform.mana.ManaCurios5;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.PersistentRangeEffectOperation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.process.system.skill.skillv2.SkillV2ElementEffect;
import fun.wraq.process.system.skill.skillv2.SkillV2AllowInterruptNormalAttack;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.cold.sc5.dragon.weapon.SuperColdDragonWeaponCommon;
import fun.wraq.series.overworld.sun.TabooPaper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ManaNewSkillBase2_0 extends SkillV2BaseSkill implements SkillV2ElementEffect, SkillV2AllowInterruptNormalAttack {

    public ManaNewSkillBase2_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        boolean enhanced = TabooPaper.enhanceManaSkillV2_2(player);
        double damage = ManaNewSkill.modifyDamage(player,
                (0.5 + 0.05 * skillLevel + (enhanced ? 0.2 : 0)) * (1 + getEnhanceRate(player)));
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.manaNewSkillBase2_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                Vec3 targetPos = WraqPower
                        .getDefaultTargetPos(player, 15 * (1 + ManaCurios5.getExSkillRangeRate(player)));
                double radius = 6;
                double exRange = 0;
                exRange += SuperColdDragonWeaponCommon.getSkillExRange(player);
                radius += exRange;
                radius *= (1 + ManaCurios5.getExSkillRangeRate(player));
                double finalRadius = radius;
                PersistentRangeEffect.addEffect(player, targetPos, radius, new PersistentRangeEffectOperation() {
                    @Override
                    public void operation(PersistentRangeEffect effect) {
                        Compute.getNearEntity(effect.level, effect.center, Mob.class, finalRadius)
                                .stream().map(mob -> (Mob) mob)
                                .forEach(mob -> {
                                    Damage.causeRateApDamageWithElement(player, mob,
                                            damage * (1 + ManaCurios5.getExBaseDamageRate(player, mob)), true);
                                    Compute.addSlowDownEffect(mob, Tick.s(1), 2);
                                    SuperColdDragonWeaponCommon.addImprisonEffectToMob(player, mob);
                                });
                        ParticleProvider.dustParticle(player, effect.center,
                                finalRadius, 120, Element.getManaSkillParticleStyle(player).getColor().getValue());
                        Element.giveResonanceElement(player);
                    }
                }, 10, Tick.s(3 + (enhanced ? 1 : 0)));
                ParticleProvider.createLastVerticalCircleParticles(player,
                        targetPos.add(0, 0.5, 0), radius, 200,
                        ModParticles.EVOKER.get(), Tick.s(3 + (enhanced ? 1 : 0)));
                ParticleProvider.createLineDustParticleFromRightHand(player, targetPos,
                        Element.getManaSkillParticleStyle(player));
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("撕裂", CustomStyle.styleOfMana, "准星附近的空间", "3s", CustomStyle.styleOfMana));
        components.add(Te.s("在空间内的敌人", "每0.5s", CustomStyle.styleOfMana, "受到",
                getRateDescription(0.5, 0.05,level), CustomStyle.styleOfMana, "伤害"));
        components.add(Te.s("并对空间内的敌人施加", "减速效果", CustomStyle.styleOfStone));
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 15;
    }
}
