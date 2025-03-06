package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.process.system.skill.skillv2.SkillV2ElementEffect;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ManaNewSkillBase1_0 extends SkillV2BaseSkill implements SkillV2ElementEffect {

    public ManaNewSkillBase1_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        DelayOperationWithAnimation.beforeReleaseSkill(player);
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        double damage = ManaNewSkill.modifyDamage(player,
                2.5 + skillLevel * 0.25) * (1 + getEnhanceRate(player));
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.manaNewSkillBase1_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                MySound.soundToNearPlayer(player.level(), player.getEyePosition(), SoundEvents.EVOKER_CAST_SPELL);
                Vec3 pickLocation = player.pick(20, 0, false).getLocation();
                Vec3 eyePosition = player.getEyePosition();
                ParticleProvider.createLineSpaceDustParticle(player.level(),
                        (int) pickLocation.distanceTo(eyePosition) * 20, eyePosition, pickLocation,
                        3, Element.getManaSkillParticleStyle(player));
                Compute.getPlayerRayMobList(player, 1, 3, 20).forEach(mob -> {
                    Damage.causeRateApDamageWithElement(player, mob, damage, true);
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
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 10;
    }
}
