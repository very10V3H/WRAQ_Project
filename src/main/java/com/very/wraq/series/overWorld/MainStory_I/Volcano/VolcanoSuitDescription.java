package com.very.wraq.series.overWorld.MainStory_I.Volcano;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.ClientUtils;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.List;

public class VolcanoSuitDescription {
    public static void VolcanoArmorCommonDescription (List<Component> components) {
        Compute.DescriptionDash(components, ChatFormatting.WHITE,ChatFormatting.YELLOW,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        VolcanoSuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.YELLOW,ChatFormatting.WHITE);
        components.add(Component.literal("VolcanoArmor-I").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
    }
    public static void VolcanoSuitDescription (List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        ChatFormatting MainStyle = ChatFormatting.YELLOW;
        Item[] items = {
                ModItems.VolcanoArmorHelmet.get(),
                ModItems.VolcanoArmorChest.get(),
                ModItems.VolcanoArmorLeggings.get(),
                ModItems.VolcanoArmorBoots.get(),
                ModItems.VolcanoBracelet.get()
        };
        EquipmentSlot[] equipmentSlot = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET,
                EquipmentSlot.OFFHAND
        };
        for (int i = 0; i < items.length; i ++) {
            Count += Compute.SuitItemVision(player,items[i],equipmentSlot[i],components,MainStyle);
        }

        String CrestString = StringUtils.Crest.Volcano.Crest;
        String CrestName = "[火山纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString))  {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count ++;
        }
        else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("熔岩涌动").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExAttackDamage("15%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("25%")));
        Compute.SuitQuadraDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("地层震荡").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("35%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaPenetration("35")));
    }
}
