package com.very.wraq.blocks.blocks;

import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InjectNote extends Item {
    public InjectNote(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" "));
        components.add(Component.literal("使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("能量注入装置").withStyle(CustomStyle.styleOfInject)).
                append(Component.literal("以增幅武器。").withStyle(ChatFormatting.WHITE)));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
