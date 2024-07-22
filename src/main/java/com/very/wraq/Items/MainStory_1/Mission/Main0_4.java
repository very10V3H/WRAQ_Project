package com.very.wraq.Items.MainStory_1.Mission;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Main0_4 extends Item {
    public Main0_4(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("本节将介绍维瑞阿契的").append(Component.literal("符石").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("符石是维瑞阿契中，被动地为你提供属性加成的物品。"));
        components.add(Component.literal("它的属性加成一般是“机制”类型的加成。"));
        components.add(Component.literal("意即符石的属性加成应在你全部探索阶段都能发挥一定作用。"));
        components.add(Component.literal("在维瑞阿契的世界各地，都有符石祭坛的存在。"));
        components.add(Component.literal("在获取相关材料，兑换符石后，在主手右键即可激活符石被动属性加成效果。"));
        components.add(Component.literal("同一符石祭坛处兑换的符石，同时至多存在一种效果。"));
        components.add(Component.literal("意即作者的本意是让你在探索过程中能够合理搭配各种类型符文，"));
        components.add(Component.literal("实现在不同情景下的应对。"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Prologue-IV").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
