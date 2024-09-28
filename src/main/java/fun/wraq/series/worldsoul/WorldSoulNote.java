package fun.wraq.series.worldsoul;

import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WorldSoulNote extends Item {
    public WorldSoulNote(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" "));
        components.add(Component.literal("世界本源分崩离析，化为根源散落彼方。").withStyle(CustomStyle.styleOfWorld).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("  1.通过收集").withStyle(ChatFormatting.WHITE).
                append(Component.literal("根源").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("放入我旁边的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("世界本源解析器").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("，你将有概率得到").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld)));
        components.add(Component.literal("  2.").withStyle(ChatFormatting.WHITE).
                append(Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("解析成功概率").withStyle(ChatFormatting.YELLOW)).
                append(Component.literal("受到").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("当前场环境").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("的影响。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("  3.将").withStyle(ChatFormatting.WHITE).
                append(Component.literal("大量世界本源").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("再次放入").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("世界本源解析器").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("可以得到纯度更高的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("  4.你可以将解析出的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("带给我，作为交换，我会将我的一些").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("研究成果").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("给你。").withStyle(ChatFormatting.WHITE)));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
