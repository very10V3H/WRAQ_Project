package com.Very.very.Series.OverWorldSeries.MainStory_I.Forest;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.registry.ModItems;
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
public class ForestSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        Compute.DescriptionDash(components, ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        SuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("ForestArmor-I").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
    }
    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        ChatFormatting MainStyle = ChatFormatting.DARK_GREEN;
        Item[] items = {
                ModItems.ForestArmorHelmet.get(),
                ModItems.ForestArmorChest.get(),
                ModItems.ForestArmorLeggings.get(),
                ModItems.ForestArmorBoots.get(),
                ModItems.ForestBracelet.get()
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

        String CrestString = StringUtils.Crest.Forest.Crest;
        String CrestName = "[森林纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString))  {
            components.add(Component.literal(CrestName).withStyle(ChatFormatting.RESET).withStyle(MainStyle));
            Count ++;
        }
        else components.add(Component.literal(CrestName).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("繁茂生长").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN));
        Compute.DescriptionNum(components,"提升",Compute.AttributeDescription.Defence("25%"),"");
        Compute.SuitQuadraDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("森林生机").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN));
        Compute.DescriptionNum(components,"提升",Compute.AttributeDescription.HealAmplification("50%"),"");
        Compute.DescriptionNum(components,"在白天，提供",Compute.AttributeDescription.HealthRecover("5"),"");
    }
}
