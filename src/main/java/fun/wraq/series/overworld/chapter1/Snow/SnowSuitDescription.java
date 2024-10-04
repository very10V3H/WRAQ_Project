package fun.wraq.series.overworld.chapter1.Snow;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.StringUtils;
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
public class SnowSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        if (Screen.hasShiftDown()) SuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
    }

    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = CustomStyle.styleOfSnow;
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
        for (int i = 0; i < items.length; i++) {
            Count += Compute.SuitItemVision(player, items[i], equipmentSlot[i], components, MainStyle);
        }

        String CrestString = StringUtils.Crest.Snow.Crest;
        String CrestName = "[冰川纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString)) {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count++;
        } else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("冰川增幅").withStyle(MainStyle));
        components.add(Component.literal("攻击或箭矢攻击将会").withStyle(ChatFormatting.WHITE).
                append(Component.literal("缓速").withStyle(MainStyle)).
                append(Component.literal("目标").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.25s").withStyle(MainStyle)));
        Compute.SuitQuadraDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("寒铁").withStyle(MainStyle));
        components.add(Component.literal("受击将会").withStyle(ChatFormatting.WHITE).
                append(Component.literal("缓速").withStyle(MainStyle)).
                append(Component.literal("目标").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.25s").withStyle(MainStyle)));
        components.add(Component.literal("同时降低目标").withStyle(ChatFormatting.WHITE).
                append(Component.literal("25%").withStyle(MainStyle)).
                append(Component.literal("造成伤害，持续3s").withStyle(ChatFormatting.WHITE)));

    }
}
