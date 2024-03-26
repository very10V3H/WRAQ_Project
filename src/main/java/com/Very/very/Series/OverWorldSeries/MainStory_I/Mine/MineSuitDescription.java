package com.Very.very.Series.OverWorldSeries.MainStory_I.Mine;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
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
        Compute.DescriptionDash(components, ChatFormatting.WHITE, Utils.styleOfMine,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        SuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMine,ChatFormatting.WHITE);
        components.add(Component.literal("MineArmor-I").withStyle(Utils.styleOfMine).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);

    }
    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = Utils.styleOfMine;
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
        for (int i = 0; i < items.length; i ++) {
            Count += Compute.SuitItemVision(player,items[i],equipmentSlot[i],components,MainStyle);
        }

        String CrestString = StringUtils.Crest.Plain.Crest;
        String CrestName = "[矿洞纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString))  {
            components.add(Component.literal(CrestName).withStyle(ChatFormatting.RESET).withStyle(MainStyle));
            Count ++;
        }
        else components.add(Component.literal(CrestName).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("顽铁").withStyle(ChatFormatting.RESET).withStyle(MainStyle));
        components.add(Component.literal("减少受到的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("50%")));
        Compute.SuitQuadraDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("沉重").withStyle(ChatFormatting.RESET).withStyle(MainStyle));
        components.add(Component.literal("1.").withStyle(ChatFormatting.RESET).withStyle(MainStyle).
                append(Component.literal("降低").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)).
                append(Compute.AttributeDescription.MovementSpeedDecrease("50%")));
        components.add(Component.literal("2.").withStyle(ChatFormatting.RESET).withStyle(MainStyle).
                append(Component.literal("获得").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).
                append(Compute.AttributeDescription.Defence("250")));
        components.add(Component.literal("3.").withStyle(ChatFormatting.RESET).withStyle(MainStyle).
                append(Component.literal("获得").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).
                append(Compute.AttributeDescription.ExAttackDamage("30%")));

    }
}
