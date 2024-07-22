package com.very.wraq.series.overworld.chapter1.Mine;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ClientUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MineSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMine, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        SuitDescription(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMine, ChatFormatting.WHITE);
        components.add(Component.literal("MineArmor-I").withStyle(CustomStyle.styleOfMine).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);

    }

    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = CustomStyle.styleOfMine;
        Item[] items = {
                ModItems.MineArmorHelmet.get(),
                ModItems.MineArmorChest.get(),
                ModItems.MineArmorLeggings.get(),
                ModItems.MineArmorBoots.get(),
                ModItems.MineBracelet.get()
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

        String CrestString = StringUtils.Crest.Mine.Crest;
        String CrestName = "[矿山纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString)) {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count++;
        } else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("顽铁").withStyle(MainStyle));
        components.add(Component.literal("减少受到的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("50%")));
        Compute.SuitQuadraDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("沉重").withStyle(MainStyle));
        components.add(Component.literal("1.").withStyle(MainStyle).
                append(Component.literal("降低").withStyle(ChatFormatting.RED)).
                append(Compute.AttributeDescription.MovementSpeedDecrease("50%")));
        components.add(Component.literal("2.").withStyle(MainStyle).
                append(Component.literal("获得").withStyle(ChatFormatting.GREEN)).
                append(Compute.AttributeDescription.Defence("250")));
        components.add(Component.literal("3.").withStyle(MainStyle).
                append(Component.literal("获得").withStyle(ChatFormatting.GREEN)).
                append(Compute.AttributeDescription.ExAttackDamage("30%")));

    }
}
