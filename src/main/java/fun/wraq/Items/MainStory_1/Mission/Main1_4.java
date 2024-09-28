package fun.wraq.Items.MainStory_1.Mission;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Main1_4 extends Item {
    public Main1_4(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("门开着，似乎守山人已经知道你要到来。"));
        components.add(Component.literal("看见你手上的物资，守山人面露微笑。"));
        components.add(Component.literal("'年轻人，是想要进山吗？'守山人一边接过你递给他的物资，一边问。"));
        components.add(Component.literal("'是的，我想考察一下这里的地质。'守山人收下了你的物资。"));
        components.add(Component.literal("'就这样进去考察可不行。'说完，守山人进屋翻找了一会物品。"));
        components.add(Component.literal("'带上它们。'守山人拿出了一套装甲与一把长剑。").withStyle(ChatFormatting.STRIKETHROUGH));
        components.add(Component.literal("还没等你发问，守山人边说'拿着，肯定会用上的。'"));
        components.add(Component.literal("'时间不早了，我该休息了，你要在这留一宿吗？'守山人似乎不太想继续交谈"));
        components.add(Component.literal("'谢谢您，我去山下准备一下！'你满是疑问地道别。"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("MainStoryI-IV").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
