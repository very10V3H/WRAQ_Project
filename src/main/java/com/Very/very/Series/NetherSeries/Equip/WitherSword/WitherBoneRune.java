package com.Very.very.Series.NetherSeries.Equip.WitherSword;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
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

public class WitherBoneRune extends Item {
    public WitherBoneRune(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.MaterialLevelDescription.Normal(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("经下界能量浇筑的凋零残骨。").withStyle(Utils.styleOfNether));
        components.add(Component.literal("凋零与下界能量在这个物体中互相交融，散发出阵阵能量光。").withStyle(Utils.styleOfNether));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Nether").withStyle(Utils.styleOfNether).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
