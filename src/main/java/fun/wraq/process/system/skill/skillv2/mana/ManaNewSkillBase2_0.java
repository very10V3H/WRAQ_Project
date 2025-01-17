package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ManaNewSkillBase2_0 extends SkillV2BaseSkill {

    public ManaNewSkillBase2_0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
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
                DelayOperationWithAnimation.Animation.manaNewSkillBase2_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                WraqPower.getDefaultTargetMobList(player).forEach(mob -> {
                    Damage.causeManaDamageToMonster_RateApDamage(player, mob, 1 + skillLevel * 0.1, true);
                });
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("对准星区域一定范围内的敌人造成伤害，并减速"));
        return components;
    }
}
