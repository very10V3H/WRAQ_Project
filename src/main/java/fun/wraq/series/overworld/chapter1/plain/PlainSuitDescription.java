package fun.wraq.series.overworld.chapter1.plain;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
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
public class PlainSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        Style style = Style.EMPTY.applyFormat(ChatFormatting.GREEN);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        Compute.solePassiveDescription(components, Component.literal("").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth("等级 * 10")).
                append(Component.literal("(" + Math.min(100, Minecraft.getInstance().player.experienceLevel) * 10 + ")").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 在100级时达到最大收益").withStyle(ChatFormatting.GRAY));
        if (Screen.hasShiftDown()) SuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal("PlainArmor-I").withStyle(style).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.suffixOfChapterI(components);

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
        for (int i = 0; i < items.length; i++) {
            Count += Compute.SuitItemVision(player, items[i], equipmentSlot[i], components, MainStyle);
        }

        String CrestString = StringUtils.Crest.Plain.Crest;
        String CrestName = "[平原纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString)) {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count++;
        } else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("野火不灭:").withStyle(ChatFormatting.GREEN));
        ComponentUtils.descriptionNum(components, "每秒回复", Compute.AttributeDescription.Health("0.5%+1"), "");
        Compute.SuitQuadraDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("平原生机:").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth("200")));
    }
}
