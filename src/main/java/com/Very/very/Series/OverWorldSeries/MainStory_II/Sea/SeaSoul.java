package com.Very.very.Series.OverWorldSeries.MainStory_II.Sea;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SeaSoul extends Item {
    public SeaSoul(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Low(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("海洋的灵魂残片").withStyle(Utils.styleOfSea));
        components.add(Component.literal("可在锻造台与6个阳光能量聚合物合成:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                append(Component.literal("海洋意志").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Sea").withStyle(Utils.styleOfSeaDark).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
