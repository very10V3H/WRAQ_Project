package fun.wraq.series.end.eventController.NetherRecall1;

import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IntensifiedRuby extends Item {
    public IntensifiedRuby(Properties p_41383_) {
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
                append(Component.literal("下界红水晶。").withStyle(CustomStyle.styleOfNether)));
        components.add(Component.literal("似乎将维度扭曲。").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" "));
        components.add(Component.literal("Intensified-Nether").withStyle(CustomStyle.styleOfVolcano).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}