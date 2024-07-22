package com.very.wraq.process.series.labourDay;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OldCoin extends Item {

    public OldCoin(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        LabourDaySuffix(components);
        components.add(Component.literal(" "));
        components.add(Component.literal(" 辛勤地耕耘，换得了等值之回报").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" 该物品的材质是维瑞阿契最早的材质之一").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    public static void LabourDaySuffix(List<Component> components) {
        components.add(Component.literal(" 活动").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" · ").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("劳动节").withStyle(ChatFormatting.RED)));
    }
}
