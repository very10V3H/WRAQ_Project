package com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LakeMana extends Item {
    public LakeMana(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Epic(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("经过提取与封装的").withStyle(ChatFormatting.DARK_PURPLE).append(Component.literal("湖泊意志").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
        components.add(Component.literal("使用炼金术士酿造的魔力稳定水晶封印，使").withStyle(ChatFormatting.DARK_PURPLE).append(Component.literal("湖泊意志").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)).append(Component.literal("能够转化为魔力供人所用。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-evoker").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
