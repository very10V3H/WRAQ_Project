package com.Very.very.Items.gems;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class gemspiece extends Item {
    public gemspiece(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·材料").withStyle(ChatFormatting.DARK_PURPLE));
        components.add(Component.literal(" "));
        components.add(Component.literal("用于宝石与饰品的合成").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" "));
        components.add(Component.literal("Gems-Piece").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
