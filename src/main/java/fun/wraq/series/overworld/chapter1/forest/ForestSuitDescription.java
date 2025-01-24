package fun.wraq.series.overworld.chapter1.forest;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
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
public class ForestSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        Style style = CustomStyle.styleOfForest;
        Compute.solePassiveDescription(components, Component.literal("").withStyle(style));
        int level = Math.min(100, Minecraft.getInstance().player.experienceLevel);
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.defence("等级 * 0.3")).
                append(Component.literal("(" + BasicAttributeDescription.getDecimal(level * 0.3, 1) + ")")
                        .withStyle(ChatFormatting.GRAY)));
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
        ChatFormatting MainStyle = ChatFormatting.DARK_GREEN;
        Item[] items = {
                ModItems.ForestArmorHelmet.get(),
                ModItems.ForestArmorChest.get(),
                ModItems.ForestArmorLeggings.get(),
                ModItems.ForestArmorBoots.get()
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

        String crestName = "[森林纹章]";
        if (Compute.CuriosAttribute.getClientCuriosSet(player)
                .stream().anyMatch(item -> item instanceof ForestCrest)) {
            components.add(Component.literal(crestName).withStyle(MainStyle));
            count++;
        } else components.add(Component.literal(crestName).withStyle(ChatFormatting.GRAY));

        ComponentUtils.suitDoubleDescription(components, count);
        Compute.DescriptionPassive(components, Component.literal("繁茂生长").withStyle(ChatFormatting.DARK_GREEN));
        ComponentUtils.descriptionNum(components, "提升", ComponentUtils.AttributeDescription.defence("25%"), "");
        ComponentUtils.suitQuadraDescription(components, count);
        Compute.DescriptionPassive(components, Component.literal("森林生机").withStyle(ChatFormatting.DARK_GREEN));
        ComponentUtils.descriptionNum(components, "提升", ComponentUtils.AttributeDescription.healAmplification("50%"), "");
        ComponentUtils.descriptionNum(components, "在白天，提供", ComponentUtils.AttributeDescription.healthRecover("5"), "");
    }
}
