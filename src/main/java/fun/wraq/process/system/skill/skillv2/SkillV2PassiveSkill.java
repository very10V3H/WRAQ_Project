package fun.wraq.process.system.skill.skillv2;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public abstract class SkillV2PassiveSkill extends SkillV2 {

    protected SkillV2PassiveSkill(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(cooldownTick, manaCost, professionType, skillType, serial);
    }

    // 被动技能不能主动释放
    @Override
    protected boolean canRelease(Player player) {
        return false;
    }

    @Override
    protected void releaseOperation(Player player) {

    }

    @Override
    protected List<Component> getReleaseConditionDescription() {
        return List.of();
    }

    // 被动技能等级不应超过玩家等级/20 + 1
    @Override
    protected boolean canUpgrade(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        return skillLevel < player.experienceLevel / 20 + 1;
    }
}
