package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ManaNewSkillBase2_0 extends SkillV2BaseSkill {

    public ManaNewSkillBase2_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        DelayOperationWithAnimation.beforeReleaseSkill(player);
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.manaNewSkillBase2_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                Vec3 targetPos = WraqPower.getDefaultTargetPos(player);
                MySound.soundToNearPlayer(player.level(), targetPos, SoundEvents.EVOKER_CAST_SPELL);
                WraqPower.getDefaultTargetMobList(player).forEach(mob -> {
                    Damage.causeRateApDamageToMonster(player, mob, 2 + skillLevel * 0.15, true);
                    Compute.addSlowDownEffect(mob, 40, 2);
                });
                ParticleProvider.dustParticle(player, targetPos, 6, 120,
                        CustomStyle.styleOfMana.getColor().getValue());
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("对准星区域周围的敌人造成",
                getRateDescription(2, 0.15, level), CustomStyle.styleOfMana, "伤害"));
        components.add(Te.s("并附带", "减速效果", CustomStyle.styleOfStone));
        return components;
    }
}
