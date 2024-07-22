package com.very.wraq.Items.MainStory_1.Mission;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Main1_1 extends Item {
    public Main1_1(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("你是一名年轻的地质学教授，一天闲来无事，对着地图发呆。"));
        components.add(Component.literal("发现地图上有一块区域似乎在你的知识盲区。"));
        components.add(Component.literal("平日的大量的文献阅读也没有得到关于这个地区的知识。"));
        components.add(Component.literal("你上网搜索那个地区的资料，发现这个地区有着格外壮丽的景观。"));
        components.add(Component.literal("尤其，有着一座死火山，以及一座海拔极高的雪山。"));
        components.add(Component.literal("此外，它们之间的距离十分接近。但被一条河谷分割。"));
        components.add(Component.literal("你进一步搜索火山、雪山，发现没有任何详细资料。"));
        components.add(Component.literal("作为一名优秀、年轻的学者，你想你必须实地考察这未知区域。"));
        components.add(Component.literal("于是，今天，你来到了").append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append(Component.literal("地区，一个缺少各种资料的未知区域。")));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("MainStoryI-I").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
