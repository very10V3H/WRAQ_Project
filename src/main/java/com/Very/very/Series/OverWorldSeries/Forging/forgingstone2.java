package com.Very.very.Series.OverWorldSeries.Forging;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class forgingstone2 extends Item {
    public forgingstone2(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·材料").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal(" "));
        components.add(Component.literal("一颗").withStyle(ChatFormatting.GRAY).append(Component.literal("顶级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal("的强化石。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY))));
        components.add(Component.literal("将力量注入物品，使物品获得更好性能。").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Forging").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
