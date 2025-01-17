package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SwordNewSkillBase3_0 extends SkillV2BaseSkill {

    public SwordNewSkillBase3_0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
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
        Compute.sendForwardMotionPacketToPlayer(player, 1);
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.swordNewSkillBase3_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                Compute.getNearEntity(player, Mob.class, 6)
                        .stream().map(entity -> (Mob) entity)
                        .forEach(mob -> {
                            Damage.causeRateAdDamageToMonsterWithCritJudge(player, mob, 1 + skillLevel * 0.1);
                        });
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("向前突进，并对周围目标造成伤害"));
        return components;
    }
}
