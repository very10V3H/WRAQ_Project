package fun.wraq.Items.MainStory_1.Mission;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Main1_2 extends Item {
    public Main1_2(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("你来到了维瑞库尤地区边境与维瑞阿契地区最近的酒店，打算以此为据点，开展无限期的考察。"));
        components.add(Component.literal("在这个特殊位置的酒店附近，似乎只能看见酒店工作人员与游客。"));
        components.add(Component.literal("'我想到西边那座山上，有什么需要注意的吗？'你向前台工作人员询问。"));
        components.add(Component.literal("'那座山？您是想上那座山吗？那座山属于未开发地区，不建议您前往。'工作人员答道。"));
        components.add(Component.literal("'我就是来开发的。'你紧接着说道。"));
        components.add(Component.literal("'呃，好的先生。这样，沿着山路，你能看见一座云杉木屋，那有位守山人。'"));
        components.add(Component.literal("'守山人是原酒店老板，因为年纪太大，上山静养了。'"));
        components.add(Component.literal("'他可能知道更多关于那座山的内容，您可以去问他。'"));
        components.add(Component.literal("'哦对了，您最好带上点肉类，山上通行不便，守山人很需要这类物资。'"));
        components.add(Component.literal("'感谢！'你谢过工作人员，准备去往").append(Component.literal("西边的山").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("MainStoryI-II").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
