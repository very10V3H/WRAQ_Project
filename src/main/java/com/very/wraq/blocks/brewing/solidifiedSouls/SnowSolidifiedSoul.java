package com.very.wraq.blocks.brewing.solidifiedSouls;

import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SnowSolidifiedSoul extends Item {

    public SnowSolidifiedSoul(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·材料-酿造").withStyle(CustomStyle.styleOfBrew));
        components.add(Component.literal("经过固化的冰川根源。"));
        components.add(Component.literal("经过固化剂的固化，根源的能量被略微稳定。"));
        components.add(Component.literal("Items-Brewing").withStyle(CustomStyle.styleOfBrew));
        super.appendHoverText(itemStack, level, components, flag);
    }
}
