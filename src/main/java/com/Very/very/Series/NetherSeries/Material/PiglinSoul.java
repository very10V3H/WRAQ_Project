package com.Very.very.Series.NetherSeries.Material;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PiglinSoul extends Item{
    public PiglinSoul(Item.Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.MaterialLevelDescription.Normal(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("猪灵的收藏品。").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#363633"))));
        components.add(Component.literal("不知为何，热爱着这样一件物品。").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a363633"))));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Nether").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
