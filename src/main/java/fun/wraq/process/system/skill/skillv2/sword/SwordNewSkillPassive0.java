package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.fast.Te;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2PassiveSkill;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SwordNewSkillPassive0 extends SkillV2PassiveSkill {

    public SwordNewSkillPassive0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("普攻对非主要目标造成",
                getRateDescription(0.3, 0.05, level), CustomStyle.styleOfPower, "伤害。"));
        return components;
    }

    public static double exTargetsDamageEnhance(Player player) {
        SkillV2 skillV2 = SkillV2.getPlayerCurrentSkillByType(player, 0);
        if (skillV2 instanceof SwordNewSkillPassive0) {
            int skillLevel = SkillV2.getPlayerSkillLevelBySkillV2(player, skillV2);
            return skillLevel * 0.1;
        }
        return 0;
    }
}
