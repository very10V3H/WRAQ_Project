package fun.wraq.series.overworld.chapter1.volcano;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
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
                append(ComponentUtils.AttributeDescription.attackDamage("等级 * 1")).
                append(Component.literal("(" + level + ")").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("等级 * 2")).
                append(Component.literal("(" + level * 2 + ")").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 在100级时达到最大收益").withStyle(ChatFormatting.GRAY));
        if (Screen.hasShiftDown()) VolcanoSuitDescription(components);
        else {
            ComponentUtils.suitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterI(components);
    }

    public static void VolcanoSuitDescription(List<Component> components) {
        ComponentUtils.suitDescription(components);

        int count = 0;
        Player player = Minecraft.getInstance().player;
        ChatFormatting MainStyle = ChatFormatting.YELLOW;
        Item[] items = {
                ModItems.VolcanoArmorHelmet.get(),
                ModItems.VolcanoArmorChest.get(),
                ModItems.VolcanoArmorLeggings.get(),
                ModItems.VolcanoArmorBoots.get()
        };
        EquipmentSlot[] equipmentSlot = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        };
        for (int i = 0; i < items.length; i++) {
            count += Compute.SuitItemVision(player, items[i], equipmentSlot[i], components, MainStyle);
        }

        String crestName = "[火山纹章]";
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream().anyMatch(stack -> stack.getItem() instanceof VolcanoCrest)) {
            components.add(Component.literal(crestName).withStyle(MainStyle));
            count++;
        } else components.add(Component.literal(crestName).withStyle(ChatFormatting.GRAY));

        ComponentUtils.suitDoubleDescription(components, count);
        Compute.DescriptionPassive(components, Component.literal("熔岩涌动").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.exAttackDamage("15%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("25%")));
        ComponentUtils.suitQuadraDescription(components, count);
        Compute.DescriptionPassive(components, Component.literal("地层震荡").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.critDamage("35%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaPenetration("35")));
    }
}
