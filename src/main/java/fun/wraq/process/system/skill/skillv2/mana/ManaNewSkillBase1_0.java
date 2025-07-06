package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.customized.uniform.mana.ManaCurios5;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.skill.skillv2.SkillV2AllowInterruptNormalAttack;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.process.system.skill.skillv2.SkillV2ElementEffect;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManaNewSkillBase1_0 extends SkillV2BaseSkill implements SkillV2ElementEffect, SkillV2AllowInterruptNormalAttack {

    public ManaNewSkillBase1_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        double damage = ManaNewSkill.modifyDamage(player,
                2.5 + skillLevel * 0.25) * (1 + getEnhanceRate(player));
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.manaNewSkillBase1_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                MySound.soundToNearPlayer(player.level(), player.getEyePosition(), SoundEvents.EVOKER_CAST_SPELL);
                double maxDistance = 20;
                maxDistance *= (1 + ManaCurios5.getExSkillRangeRate(player));
                double range = 3;
                range *= (1 + ManaCurios5.getExSkillRangeRate(player));
                Vec3 pickLocation = player.pick(maxDistance, 0, false).getLocation();
                Vec3 eyePosition = player.getEyePosition();
                ParticleProvider.createLineSpaceDustParticle(player.level(),
                        (int) (pickLocation.distanceTo(eyePosition) * maxDistance), eyePosition, pickLocation,
                        range, Element.getManaSkillParticleStyle(player));
                Set<Mob> mobs = Compute.getPlayerRayMobList(player, 1, range, maxDistance);
                mobs.forEach(mob -> {
                    Damage.causeRateApDamageWithElement(player, mob,
                            damage * (1 + ManaCurios5.getExBaseDamageRate(player, mob))
                                    * (mobs.size() == 1 ? 3 : 1), true);
                    ManaNewSkillPassive0.addCount(player, mob, 2);
                });
                Element.giveResonanceElement(player);
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("对前方矩形范围的敌人造成",
                getRateDescription(2.5, 0.25, level), CustomStyle.styleOfMana, "伤害"));
        components.add(Te.s("并额外施加2层", " 渗", CustomStyle.styleOfMana));
        components.add(Te.s("若范围内仅有一名敌人，则造成",
                getRateDescription(7.5, 0.75, level), CustomStyle.styleOfMana, "伤害"));
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 10;
    }
}
