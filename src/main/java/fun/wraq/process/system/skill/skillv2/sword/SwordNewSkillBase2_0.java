package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SwordNewSkillBase2_0 extends SkillV2BaseSkill {

    public SwordNewSkillBase2_0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
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
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.skill, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {

            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("对自身周围目标造成减速"));
        return components;
    }
}
