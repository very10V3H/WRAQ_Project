package fun.wraq.series.overworld.chapter1.Mine;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.render.toolTip.CustomStyle;
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
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMine, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        SuitDescription(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMine, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterI(components);
    }

    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = CustomStyle.styleOfMine;
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
        for (int i = 0; i < items.length; i++) {
            Count += Compute.SuitItemVision(player, items[i], equipmentSlot[i], components, MainStyle);
        }

        String CrestString = StringUtils.Crest.Mine.Crest;
        String CrestName = "[矿山纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString)) {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count++;
        } else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        ComponentUtils.suitDoubleDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("顽铁").withStyle(MainStyle));
        components.add(Component.literal("减少受到的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.critDamage("50%")));
        ComponentUtils.suitQuadraDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("沉重").withStyle(MainStyle));
        components.add(Component.literal("1.").withStyle(MainStyle).
                append(Component.literal("降低").withStyle(ChatFormatting.RED)).
                append(ComponentUtils.AttributeDescription.movementSpeedDecrease("50%")));
        components.add(Component.literal("2.").withStyle(MainStyle).
                append(Component.literal("获得").withStyle(ChatFormatting.GREEN)).
                append(ComponentUtils.AttributeDescription.defence("60")));
        components.add(Component.literal("3.").withStyle(MainStyle).
                append(Component.literal("获得").withStyle(ChatFormatting.GREEN)).
                append(ComponentUtils.AttributeDescription.exAttackDamage("30%")));

    }
}
