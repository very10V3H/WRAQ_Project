package com.very.wraq.series.overWorld.MainStory_I.Plain;

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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PlainSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        Compute.DescriptionDash(components, ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        SuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("PlainArmor-I").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);

    }
    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        ChatFormatting MainStyle = ChatFormatting.GREEN;
        Item[] items = {
                ModItems.PlainArmorHelmet.get(),
                ModItems.PlainArmorChest.get(),
                ModItems.PlainArmorLeggings.get(),
                ModItems.PlainArmorBoots.get(),
                ModItems.PlainBracelet.get()
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

        String CrestString = StringUtils.Crest.Plain.Crest;
        String CrestName = "[平原纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString))  {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count ++;
        }
        else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("野火不灭:").withStyle(ChatFormatting.GREEN));
        Compute.DescriptionNum(components,"每秒回复",Compute.AttributeDescription.Health("0.5%+1"),"");
        Compute.SuitQuadraDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("平原生机:").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("200")));
    }
}
