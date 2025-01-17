package fun.wraq.process.system.skill.skillv2;

import fun.wraq.process.func.DelayOperationWithAnimation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public abstract class SkillV2BaseSkill extends SkillV2 {

    protected SkillV2BaseSkill(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected boolean canRelease(Player player) {
        return !DelayOperationWithAnimation.playerCurrentOperationMap.containsKey(player);
    }

    @Override
    protected List<Component> getReleaseConditionDescription() {
        return List.of();
    }

    // 基础技能等级不应超过最大被动技能等级
    @Override
    protected boolean canUpgrade(Player player) {
        List<SkillV2> passiveSkillList = getSkillV2ListByProfession(professionType)
                .stream().filter(skillV2 -> skillV2.skillType == 0)
                .toList();
        int maxPassiveSkillLevel = 0;
        for (SkillV2 skillV2 : passiveSkillList) {
            int skillLevel = getPlayerSkillLevelBySkillV2(player, skillV2);
            if (skillLevel > maxPassiveSkillLevel) {
                maxPassiveSkillLevel = getPlayerSkillLevelBySkillV2(player, skillV2);
            }
        }
        return getPlayerSkillLevelBySkillV2(player, this) < maxPassiveSkillLevel;
    }
}
