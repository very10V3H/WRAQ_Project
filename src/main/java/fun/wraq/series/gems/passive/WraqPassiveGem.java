package fun.wraq.series.gems.passive;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.series.gems.WraqGem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class WraqPassiveGem extends WraqGem {

    public WraqPassiveGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle,
                          Component oneLineDescription, Component suffix) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
    }

    public abstract List<Component> getAdditionDescription();

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = hoverStyle;
        components.add(oneLineDescription);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        if (!attributeMapValues.isEmpty()) {
            ComponentUtils.descriptionOfBasic(components);
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        }
        ComponentUtils.descriptionOfAddition(components);
        components.addAll(getAdditionDescription());
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(suffix);
    }
}
