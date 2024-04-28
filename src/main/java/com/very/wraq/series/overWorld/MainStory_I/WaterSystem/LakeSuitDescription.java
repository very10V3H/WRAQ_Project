package com.very.wraq.series.overWorld.MainStory_I.WaterSystem;

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

public class LakeSuitDescription {
    public static void LakeArmorCommonDescription (List<Component> components) {
        LakeSuitDescription(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("LakeArmor-I").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
    }
    public static void LakeSuitDescription (List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        ChatFormatting MainStyle = ChatFormatting.BLUE;
        Item[] items = {
                ModItems.LakeArmorHelmet.get(),
                ModItems.LakeArmorChest.get(),
                ModItems.LakeArmorLeggings.get(),
                ModItems.LakeArmorBoots.get(),
                ModItems.LakeBracelet.get()
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

        String CrestString = StringUtils.Crest.Lake.Crest;
        String CrestName = "[湖泊纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString))  {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count ++;
        }
        else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("冷却剂").withStyle(ChatFormatting.BLUE));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("20%")));
        Compute.SuitQuadraDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("循环").withStyle(ChatFormatting.BLUE));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MovementSpeed("35%")));
    }
}
