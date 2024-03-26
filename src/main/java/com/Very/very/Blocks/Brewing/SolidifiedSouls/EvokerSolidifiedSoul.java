package com.Very.very.Blocks.Brewing.SolidifiedSouls;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EvokerSolidifiedSoul extends Item {

    public EvokerSolidifiedSoul(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·材料-酿造").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBrew));
        components.add(Component.literal("经过固化的唤魔之源。"));
        components.add(Component.literal("经过固化剂的固化，魔源的能量被略微稳定。"));
        components.add(Component.literal("Items-Brewing").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBrew));
        super.appendHoverText(itemStack, level, components, flag);
    }
}
