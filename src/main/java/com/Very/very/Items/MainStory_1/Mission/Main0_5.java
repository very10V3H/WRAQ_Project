package com.Very.very.Items.MainStory_1.Mission;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Main0_5 extends Item {
    public Main0_5(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("本节将介绍维瑞阿契的").append(Component.literal("饰品").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("在打开我给你的一封信后，你会找到一本地质学者的笔记。"));
        components.add(Component.literal("它能够在你探索过程中帮你记录你所探索到的地质。"));
        components.add(Component.literal("值得一提的是，能够被记录的地方一般均有信标。"));
        components.add(Component.literal("在信标周围右键笔记，你就可以获得笔记复印件。"));
        components.add(Component.literal("笔记复印件与宝石残片将是获取饰品的重要途径。"));
        components.add(Component.literal("此外，更多的饰品获取途径还请在探索过程中发现。"));
        components.add(Component.literal("饰品一般放置在副手位置，宝石残片在击杀任何维瑞阿契怪物后均有小概率获取。"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Prologue-V").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
