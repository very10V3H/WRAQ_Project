package fun.wraq.series.events.spring2025.curios;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Spring2025Scale extends WraqCurios {

    private final int tier;
    public Spring2025Scale(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAllTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s(" 使", "所有基础属性最终值", CustomStyle.styleOfFantasy,
                "提升", ChatFormatting.AQUA, (tier + 1) + "%", hoverMainStyle()));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfSpring;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSpring2025();
    }
}
