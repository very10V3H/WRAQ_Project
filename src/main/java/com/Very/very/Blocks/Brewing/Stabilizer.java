package com.Very.very.Blocks.Brewing;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Stabilizer extends Item {

    public Stabilizer(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·材料-酿造").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBrew));
        components.add(Component.literal("酿造稳定剂。"));
        components.add(Component.literal("使药水中的酿造材料在体内的反应速率稳定，延长药水时效。"));
        components.add(Component.literal("Items-Brewing").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBrew));
        super.appendHoverText(itemStack, level, components, flag);
    }
}
