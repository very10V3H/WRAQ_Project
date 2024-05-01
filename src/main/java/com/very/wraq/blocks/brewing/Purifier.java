package com.very.wraq.blocks.brewing;

import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Purifier extends Item {

    public Purifier(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·材料-酿造").withStyle(CustomStyle.styleOfBrew));
        components.add(Component.literal("用于在酿造中净化水质。"));
        components.add(Component.literal("置于液体中，产生胶体，沉淀杂质。"));
        components.add(Component.literal("Items-Brewing").withStyle(CustomStyle.styleOfBrew));
        super.appendHoverText(itemStack, level, components, flag);
    }
}
