package com.very.wraq.series.nether.Equip.Armor;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class NetherSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        Compute.DescriptionOfAddition(components);
        if (Screen.hasShiftDown()) SuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfNether, ChatFormatting.WHITE);
        components.add(Component.literal("NetherArmor").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
    }

    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = CustomStyle.styleOfNether;
        Item[] items = {
                ModItems.NetherArmorHelmet.get(),
                ModItems.NetherArmorChest.get(),
                ModItems.NetherArmorLeggings.get(),
                ModItems.NetherArmorBoots.get(),
                ModItems.NetherPower.get()
        };
        EquipmentSlot[] equipmentSlot = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET,
                EquipmentSlot.OFFHAND
        };
        for (int i = 0; i < items.length; i++) {
            Count += Compute.SuitItemVision(player, items[i], equipmentSlot[i], components, MainStyle);
        }

        Compute.DescriptionPassive(components, Component.literal("迸骸成末").withStyle(CustomStyle.styleOfNether));
        components.add(Component.literal("近战攻击与箭矢时基于目标的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("提供至多50%伤害提升").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("当目标的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("高于500时达到最大效能。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" "));
        ComponentUtils.SuitEffectRateDescription(components, Count);
    }
}
