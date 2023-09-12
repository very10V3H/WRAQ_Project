package com.Very.very.Items.Money;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SilverCoin extends Item {
    public SilverCoin(Item.Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·货币").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" "));
        components.add(Component.literal("一枚通用货币").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" "));
        components.add(Component.literal("Currency-SilverCoin").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
