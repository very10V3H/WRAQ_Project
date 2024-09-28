package fun.wraq.series.end.eventController.NetherRecall1;

import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RecallRuby extends Item {

    public RecallRuby(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·材料").withStyle(CustomStyle.styleOfEnd));
        components.add(Component.literal(" "));
        components.add(Component.literal("来自下界的红水晶。").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))));
        components.add(Component.literal("拥有着极其特殊的下界能量。").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))));
        components.add(Component.literal(" "));
        components.add(Component.literal("Recall-Nether").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }
}
