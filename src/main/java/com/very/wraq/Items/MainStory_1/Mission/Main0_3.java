package com.very.wraq.Items.MainStory_1.Mission;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Main0_3 extends Item {
    public Main0_3(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("本节将介绍维瑞阿契的").append(Component.literal("世界探索").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("除了主线任务外，在维瑞阿契的世界各处，"));
        components.add(Component.literal("有许多需要你帮助的人。"));
        components.add(Component.literal("找到，并帮助他们将会获取一些有趣或实用的道具。"));
        components.add(Component.literal("此外，为保留Minecraft原生元素，"));
        components.add(Component.literal("在维瑞阿契，你将可以以安全的方式攻击动物，"));
        components.add(Component.literal("来获取动物身上的资源。"));
        components.add(Component.literal("维瑞阿契的钓鱼系统也进行了附加，"));
        components.add(Component.literal("你将可以在维瑞阿契的钓鱼系统中获得除Minecraft"));
        components.add(Component.literal("原生钓鱼系统外的附加内容。"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Prologue-III").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
