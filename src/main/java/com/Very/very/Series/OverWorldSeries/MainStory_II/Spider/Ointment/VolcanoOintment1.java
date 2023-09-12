package com.Very.very.Series.OverWorldSeries.MainStory_II.Spider.Ointment;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VolcanoOintment1 extends Item {
    public VolcanoOintment1(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·涂料").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal(" "));
        VolcanoOintment0.VolcanoOintmentCommonDescription(components,1);
        components.add(Component.literal("优良").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                append(Component.literal("的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("火山能量涂料。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Ointment").withStyle(Utils.styleOfSpider).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

}
