package fun.wraq.Items.MainStory_1.Mission;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Main1_3 extends Item {
    public Main1_3(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("建议：").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("备好猪牛羊生肉各16个后再前往登山，并准备好食物。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("其中，你可以通过左键动物在不伤害动物的同时获取动物的生肉。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("生肉可以找到酒店的厨师帮忙加工成熟制食物。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("如果你找不到路，可以跟着路灯，或者你觉得任何是人类活动的痕迹探索。"));
        components.add(Component.literal("——————————————————————————————————").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("在准备了些许时刻后，你沿着小路登山。"));
        components.add(Component.literal("在快到达山顶时，看到了那座云杉木屋。"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("MainStoryI-III").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
