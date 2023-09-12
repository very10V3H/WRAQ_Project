package com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Runes_Plain;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Piece extends Item {
    public Piece(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Low(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("用于合成完整符文"));
        components.add(Component.literal(" "));
        components.add(Component.literal("RunesPiece").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
