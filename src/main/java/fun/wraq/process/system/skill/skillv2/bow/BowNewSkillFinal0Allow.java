package fun.wraq.process.system.skill.skillv2.bow;

import fun.wraq.common.fast.Te;
import fun.wraq.customized.uniform.bow.BowCurios4;
import fun.wraq.process.system.skill.skillv2.SkillV2FinalSkill;
import fun.wraq.process.system.skill.skillv2.SkillV2AllowInterruptNormalAttack;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class BowNewSkillFinal0Allow extends SkillV2FinalSkill implements SkillV2AllowInterruptNormalAttack {

    public BowNewSkillFinal0Allow(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        WraqQuiver.batchAddExShoot(player,
                getRate(skillLevel) * (1 + getEnhanceRate(player)),
                getArrowCount(skillLevel) + BowCurios4.getExArrowCount(player));
    }

    private double getRate(int level) {
        if (level < 3) {
            return 0.7;
        } else if (level < 5) {
            return 0.8;
        } else if (level < 8) {
            return 0.9;
        } else {
            return 1;
        }
    }

    private int getArrowCount(int level) {
        return 5 + level / 2;
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("向前方射出", getArrowCount(level) + "支", CustomStyle.styleOfFlexible, "箭矢"));
        components.add(Te.s("这些箭矢具有",
                String.format("%.0f%%", getRate(level) * 100), CustomStyle.styleOfFlexible, "伤害"));
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 10;
    }
}
