package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ManaNewSkillBase1_0 extends SkillV2BaseSkill {

    public ManaNewSkillBase1_0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected List<Component> getUpgradeConditionDescription() {
        return List.of();
    }

    @Override
    protected void upgradeOperation(Player player) {

    }

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.manaNewSkillBase1_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                Vec3 pickLocation = player.pick(16, 0, false).getLocation();
                Vec3 eyePosition = player.getEyePosition();
                ParticleProvider.createLineSpaceDustParticle(player.level(),
                        (int) pickLocation.distanceTo(eyePosition) * 20, eyePosition, pickLocation,
                        3, CustomStyle.styleOfMana);
                Compute.getPlayerRayMobList(player, 1, 3, 16).forEach(mob -> {
                    Damage.causeManaDamageToMonster_RateApDamage(player, mob, 1 + skillLevel * 0.1, true);
                });
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("对前方矩形范围的敌人造成伤害"));
        return components;
    }
}
