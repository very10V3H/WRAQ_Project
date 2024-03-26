package com.Very.very.Series.OverWorldSeries.MainStory_I.Snow;

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
public class SnowSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        Compute.DescriptionDash(components, ChatFormatting.WHITE, Utils.styleOfSnow,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        SuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfSnow,ChatFormatting.WHITE);
        components.add(Component.literal("SnowArmor-I").withStyle(Utils.styleOfSnow).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
    }
    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = Utils.styleOfSnow;
        Item[] items = {
                ModItems.SnowArmorHelmet.get(),
                ModItems.SnowArmorChest.get(),
                ModItems.SnowBossArmorChest.get(),
                ModItems.SnowArmorLeggings.get(),
                ModItems.SnowArmorBoots.get(),
                ModItems.SnowBracelet.get()
        };
        EquipmentSlot[] equipmentSlot = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET,
                EquipmentSlot.OFFHAND
        };
        for (int i = 0; i < items.length; i ++) {
            Count += Compute.SuitItemVision(player,items[i],equipmentSlot[i],components,MainStyle);
        }

        String CrestString = StringUtils.Crest.Snow.Crest;
        String CrestName = "[冰川纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString))  {
            components.add(Component.literal(CrestName).withStyle(ChatFormatting.RESET).withStyle(MainStyle));
            Count ++;
        }
        else components.add(Component.literal(CrestName).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("冰川增幅").withStyle(ChatFormatting.RESET).withStyle(MainStyle));
        components.add(Component.literal("攻击或箭矢攻击将会").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("缓速").withStyle(ChatFormatting.RESET).withStyle(MainStyle)).
                append(Component.literal("目标").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.25s").withStyle(ChatFormatting.RESET).withStyle(MainStyle)));
        Compute.SuitQuadraDesription(components,Count);
        Compute.DescriptionPassive(components,Component.literal("寒铁").withStyle(ChatFormatting.RESET).withStyle(MainStyle));
        components.add(Component.literal("受击将会").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("缓速").withStyle(ChatFormatting.RESET).withStyle(MainStyle)).
                append(Component.literal("目标").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.25s").withStyle(ChatFormatting.RESET).withStyle(MainStyle)));
        components.add(Component.literal("同时降低目标").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("25%").withStyle(ChatFormatting.RESET).withStyle(MainStyle)).
                append(Component.literal("造成伤害，持续3s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

    }
}
