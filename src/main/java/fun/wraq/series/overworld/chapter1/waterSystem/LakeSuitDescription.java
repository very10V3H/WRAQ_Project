package fun.wraq.series.overworld.chapter1.waterSystem;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter1.waterSystem.crest.LakeCrest;
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
                append(ComponentUtils.AttributeDescription.releaseSpeed("等级 * 0.25")).
                append(Component.literal("(" + String.format("%.0f", level * 0.25) + ")").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 在100级时达到最大收益").withStyle(ChatFormatting.GRAY));
        if (Screen.hasShiftDown()) LakeSuitDescription(components);
        else {
            ComponentUtils.suitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterI(components);
    }

    public static void LakeSuitDescription(List<Component> components) {
        ComponentUtils.suitDescription(components);

        int count = 0;
        Player player = Minecraft.getInstance().player;
        ChatFormatting mainStyle = ChatFormatting.BLUE;
        Item[] items = {
                ModItems.LakeArmorHelmet.get(),
                ModItems.LakeArmorChest.get(),
                ModItems.LakeArmorLeggings.get(),
                ModItems.LakeArmorBoots.get()
        };
        EquipmentSlot[] equipmentSlot = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        };
        for (int i = 0; i < items.length; i++) {
            count += Compute.SuitItemVision(player, items[i], equipmentSlot[i], components, mainStyle);
        }

        String crestName = "[湖泊纹章]";
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream().anyMatch(stack -> stack.getItem() instanceof LakeCrest)) {
            components.add(Component.literal(crestName).withStyle(mainStyle));
            count++;
        } else components.add(Component.literal(crestName).withStyle(ChatFormatting.GRAY));

        ComponentUtils.suitDoubleDescription(components, count);
        Compute.DescriptionPassive(components, Component.literal("冷却剂").withStyle(CustomStyle.styleOfWater));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.releaseSpeed("10")));
        ComponentUtils.suitQuadraDescription(components, count);
        Compute.DescriptionPassive(components, Component.literal("循环").withStyle(CustomStyle.styleOfWater));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.movementSpeed("35%")));
    }
}
