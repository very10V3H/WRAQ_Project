package fun.wraq.series.overworld.chapter2.sea;

import fun.wraq.common.Compute;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SeaSoul extends Item {
    public SeaSoul(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Low(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("海洋的灵魂残片").withStyle(CustomStyle.styleOfSea));
        components.add(Component.literal("可在灌注台合成:").withStyle(ChatFormatting.GRAY).
                append(Component.literal("海洋意志").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Sea").withStyle(CustomStyle.styleOfSeaDark).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
