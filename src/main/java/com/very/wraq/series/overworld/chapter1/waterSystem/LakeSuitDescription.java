package com.very.wraq.series.overworld.chapter1.waterSystem;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ClientUtils;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.List;

public class LakeSuitDescription {
    public static void LakeArmorCommonDescription(List<Component> components) {
        Style style = CustomStyle.styleOfLake;
        Compute.solePassiveDescription(components, Component.literal("").withStyle(style));
        int level = Math.min(100, Minecraft.getInstance().player.experienceLevel);
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("等级 * 0.25")).
                append(Component.literal("(" + String.format("%.0f", level * 0.25) + ")").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 在100级时达到最大收益").withStyle(ChatFormatting.GRAY));
        if (Screen.hasShiftDown()) LakeSuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal("LakeArmor-I").withStyle(style).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
    }

    public static void LakeSuitDescription(List<Component> components) {
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
        for (int i = 0; i < items.length; i++) {
            Count += Compute.SuitItemVision(player, items[i], equipmentSlot[i], components, MainStyle);
        }

        String CrestString = StringUtils.Crest.Lake.Crest;
        String CrestName = "[湖泊纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString)) {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count++;
        } else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("冷却剂").withStyle(CustomStyle.styleOfWater));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("20")));
        Compute.SuitQuadraDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("循环").withStyle(CustomStyle.styleOfWater));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.movementSpeed("35%")));
    }
}
