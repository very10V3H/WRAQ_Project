package com.very.wraq.series.overworld.chapter2.sky;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ClientUtils;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ModItems;
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
public class SkySuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        Compute.DescriptionOfAddition(components);
        if (Screen.hasShiftDown()) SuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfSky, ChatFormatting.WHITE);
        components.add(Component.literal("SkyArmor-I").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);

    }

    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = CustomStyle.styleOfSky;
        Item[] items = {
                ModItems.SkyArmorHelmet.get(),
                ModItems.SkyArmorChest.get(),
                ModItems.SkyArmorLeggings.get(),
                ModItems.SkyArmorBoots.get(),
                ModItems.SkyBracelet.get()
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

        String CrestString = StringUtils.Crest.Sky.Crest;
        String CrestName = "[天空纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString)) {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count++;
        } else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        Compute.DescriptionPassive(components, Component.literal("天空的加护").withStyle(CustomStyle.styleOfSky));
        components.add(Component.literal("1.").
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("高于80%时，至多提升")).
                append(Compute.AttributeDescription.AttackDamage("100%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("40%")));
        components.add(Component.literal("2.").
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("介于40%与80%之间时，至多提升")).
                append(Compute.AttributeDescription.AttackDamage("40%")));
        components.add(Component.literal("3.").
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("低于40%时，每十秒至多获得")).
                append(Compute.AttributeDescription.AttackDamage("10%")).
                append(Component.literal("护盾。")).
                append(Component.literal("持续10s")).withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" "));
        ComponentUtils.SkySuitEffectRateDescription(components, Count);

    }
}
