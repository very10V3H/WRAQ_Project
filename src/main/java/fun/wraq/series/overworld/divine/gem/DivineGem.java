package fun.wraq.series.overworld.divine.gem;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemEnhanceSkillRate;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class DivineGem extends WraqPassiveGem implements GemEnhanceSkillRate {

    private final int SKILL_TYPE;
    private final double ENHANCE_RATE;
    public DivineGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle,
                     Component oneLineDescription, Component suffix, int skillType, double enhanceRate) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
        SKILL_TYPE = skillType;
        ENHANCE_RATE = enhanceRate;
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("圣光恩赐", CustomStyle.DIVINE_STYLE));
        components.add(Te.s(" 为", SkillV2.getTypeName(SKILL_TYPE), "提供",
                String.format("%.0f%%", ENHANCE_RATE * 100) + "增幅", CustomStyle.DIVINE_STYLE));
        components.add(Te.s(" 这个效果可以叠加", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public int getType(Player player) {
        return SKILL_TYPE;
    }

    @Override
    public double getEnhanceRate(Player player) {
        return ENHANCE_RATE;
    }
}
