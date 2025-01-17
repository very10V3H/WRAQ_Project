package fun.wraq.process.system.skill.skillv2;

import fun.wraq.process.func.DelayOperationWithAnimation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public abstract class SkillV2FinalSkill extends SkillV2 {

    protected SkillV2FinalSkill(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
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

    // 终极技能的技能等级不应超过基础技能等级的最小值
    @Override
    protected boolean canUpgrade(Player player) {
        List<SkillV2> skillV2List = getSkillV2ListByProfession(professionType);
        int minLevel = Math.min(getMaxLevel(player,skillV2List.stream().filter(skillV2 -> skillV2.skillType == 1).toList()),
                Math.min(getMaxLevel(player,skillV2List.stream().filter(skillV2 -> skillV2.skillType == 2).toList()),
                        getMaxLevel(player,skillV2List.stream().filter(skillV2 -> skillV2.skillType == 3).toList())));
        return getPlayerSkillLevelBySkillV2(player, this) < minLevel;
    }

    private int getMaxLevel(Player player, List<SkillV2> list) {
        int maxLevel = 0;
        for (SkillV2 skillV2 : list) {
            int skillLevel = getPlayerSkillLevelBySkillV2(player, skillV2);
            if (skillLevel > maxLevel) {
                maxLevel = skillLevel;
            }
        }
        return maxLevel;
    }
}
