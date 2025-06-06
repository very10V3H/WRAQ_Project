package fun.wraq.series.overworld.chapter2.manaArmor.LifeMana;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter2.evoker.ManaCrest;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class LifeManaSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        ComponentUtils.descriptionOfAddition(components);
        if (Screen.hasShiftDown()) SuitDescription(components);
        else {
            ComponentUtils.suitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        components.add(Component.literal("LifeMana-I").withStyle(CustomStyle.styleOfMana).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.suffixOfChapterII(components);

    }

    public static void SuitDescription(List<Component> components) {
        ComponentUtils.suitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = CustomStyle.styleOfMana;
        Item[] items = {
                ModItems.LIFE_MANA_HELMET.get(),
                ModItems.LIFE_MANA_CHEST.get(),
                ModItems.LIFE_MANA_LEGGINGS.get(),
                ModItems.LIFE_MANA_BOOTS.get(),
        };
        ArrayList<Item> OffHands = new ArrayList<>() {{
            add(ModItems.EVOKER_BOOK_0.get());
            add(ModItems.EVOKER_BOOK_1.get());
            add(ModItems.EVOKER_BOOK_2.get());
            add(ModItems.EVOKER_BOOK_3.get());
        }};
        EquipmentSlot[] equipmentSlot = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET,
                EquipmentSlot.OFFHAND
        };
        for (int i = 0; i < 4; i++) {
            Count += Compute.SuitItemVision(player, items[i], equipmentSlot[i], components, MainStyle);
        }

        Item OffHandItem = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        if (OffHands.contains(OffHandItem)) {
            components.add(Component.literal(OffHands.get(0).getDefaultInstance().getDisplayName().getString()).withStyle(MainStyle));
        } else
            components.add(Component.literal(OffHands.get(0).getDefaultInstance().getDisplayName().getString()).withStyle(ChatFormatting.GRAY));

        String CrestName = "[唤魔纹章]";
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof ManaCrest)) {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count++;
        } else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        ComponentUtils.suitDoubleDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("魔力涌动").withStyle(ChatFormatting.LIGHT_PURPLE));
        ComponentUtils.descriptionNum(components, "获得", ComponentUtils.AttributeDescription.manaRecover("5"), "");
        ComponentUtils.suitQuadraDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("生机涌动").withStyle(ChatFormatting.GREEN));
        ComponentUtils.descriptionNum(components, "每秒回复", ComponentUtils.AttributeDescription.health("1+1%"), "");
        ComponentUtils.descriptionNum(components, "提升", ComponentUtils.AttributeDescription.defence("25%"), "");
        ComponentUtils.descriptionNum(components, "获得", ComponentUtils.AttributeDescription.manaHealSteal("5"), "");
    }

}
