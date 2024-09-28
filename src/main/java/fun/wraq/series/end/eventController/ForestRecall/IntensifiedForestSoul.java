package fun.wraq.series.end.eventController.ForestRecall;

import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IntensifiedForestSoul extends Item {
    public IntensifiedForestSoul(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·材料").withStyle(CustomStyle.styleOfVolcano));
        components.add(Component.literal(" "));
        components.add(Component.literal("经过").withStyle(ChatFormatting.WHITE).
                append(Component.literal("火山核心").withStyle(ChatFormatting.YELLOW)).
                append(Component.literal("激化").withStyle(CustomStyle.styleOfVolcano)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("森林根源。").withStyle(CustomStyle.styleOfForest)));
        components.add(Component.literal("火山的能量散发着生机的光辉。").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" "));
        components.add(Component.literal("Intensified-Forest").withStyle(CustomStyle.styleOfVolcano).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
