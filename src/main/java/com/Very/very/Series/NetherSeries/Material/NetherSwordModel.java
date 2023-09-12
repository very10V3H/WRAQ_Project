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

public class NetherSwordModel extends Item {
    public NetherSwordModel(Item.Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.MaterialLevelDescription.Rare(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("下界长剑剑坯。").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))));
        components.add(Component.literal("用于下界长剑武器的制作。").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Nether").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }
}
