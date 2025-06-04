package fun.wraq.series.overworld.chapter1.plain;

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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PlainSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        Style style = Style.EMPTY.applyFormat(ChatFormatting.GREEN);
        Compute.solePassiveDescription(components, Component.literal("").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth("等级 * 10")).
                append(Component.literal("(" + Math.min(100, Minecraft.getInstance().player.experienceLevel) * 10 + ")").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 在100级时达到最大收益").withStyle(ChatFormatting.GRAY));
        if (Screen.hasShiftDown()) SuitDescription(components);
        else {
            ComponentUtils.suitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
    }

    public static void SuitDescription(List<Component> components) {
        ComponentUtils.suitDescription(components);

        int count = 0;
        Player player = Minecraft.getInstance().player;
        ChatFormatting mainStyle = ChatFormatting.GREEN;
        Item[] items = {
                ModItems.PLAIN_HELMET.get(),
                ModItems.PLAIN_CHEST.get(),
                ModItems.PLAIN_LEGGINGS.get(),
                ModItems.PLAIN_BOOTS.get(),
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

        String crestName = "[平原纹章]";
        if (Compute.CuriosAttribute.getClientCuriosSet(player)
                .stream().anyMatch(item -> item instanceof PlainCrest)) {
            components.add(Component.literal(crestName).withStyle(mainStyle));
            count++;
        } else components.add(Component.literal(crestName).withStyle(ChatFormatting.GRAY));

        ComponentUtils.suitDoubleDescription(components, count);
        Compute.DescriptionPassive(components, Component.literal("野火不灭:").withStyle(ChatFormatting.GREEN));
        ComponentUtils.descriptionNum(components, "每秒回复", ComponentUtils.AttributeDescription.health("0.5%+1"), "");
        ComponentUtils.suitQuadraDescription(components, count);
        Compute.DescriptionPassive(components, Component.literal("平原生机:").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth("800")));
    }
}
