package fun.wraq.series.overworld.chapter1.volcano;

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

import java.util.List;

public class VolcanoSuitDescription {
    public static void VolcanoArmorCommonDescription(List<Component> components) {
        Style style = Style.EMPTY.applyFormat(ChatFormatting.YELLOW);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        Compute.solePassiveDescription(components, Component.literal("").withStyle(style));
        int level = Math.min(100, Minecraft.getInstance().player.experienceLevel);
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("等级 * 1")).
                append(Component.literal("(" + level + ")").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("等级 * 2")).
                append(Component.literal("(" + level * 2 + ")").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 在100级时达到最大收益").withStyle(ChatFormatting.GRAY));
        if (Screen.hasShiftDown()) VolcanoSuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.WHITE);
        components.add(Component.literal("VolcanoArmor-I").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.suffixOfChapterI(components);
    }

    public static void VolcanoSuitDescription(List<Component> components) {
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
        for (int i = 0; i < items.length; i++) {
            Count += Compute.SuitItemVision(player, items[i], equipmentSlot[i], components, MainStyle);
        }

        String CrestString = StringUtils.Crest.Volcano.Crest;
        String CrestName = "[火山纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString)) {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count++;
        } else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("熔岩涌动").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExAttackDamage("15%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("25%")));
        Compute.SuitQuadraDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("地层震荡").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("35%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaPenetration("35")));
    }
}