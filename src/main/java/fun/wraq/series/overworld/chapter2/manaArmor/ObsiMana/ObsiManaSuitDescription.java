package fun.wraq.series.overworld.chapter2.manaArmor.ObsiMana;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.render.toolTip.CustomStyle;
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
public class ObsiManaSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        ComponentUtils.descriptionOfAddition(components);
        if (Screen.hasShiftDown()) SuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        components.add(Component.literal("ObsiMana-I").withStyle(CustomStyle.styleOfMana).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.suffixOfChapterII(components);

    }

    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = CustomStyle.styleOfMana;
        Item[] items = {
                ModItems.ObsiManaArmorHelmet.get(),
                ModItems.ObsiManaArmorChest.get(),
                ModItems.ObsiManaArmorLeggings.get(),
                ModItems.ObsiManaArmorBoots.get(),
        };
        ArrayList<Item> OffHands = new ArrayList<>() {{
            add(ModItems.EvokerBook0.get());
            add(ModItems.EvokerBook1.get());
            add(ModItems.EvokerBook2.get());
            add(ModItems.EvokerBook3.get());
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

        String CrestString = StringUtils.Crest.Mana.Crest;
        String CrestName = "[唤魔纹章]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString)) {
            components.add(Component.literal(CrestName).withStyle(MainStyle));
            Count++;
        } else components.add(Component.literal(CrestName).withStyle(ChatFormatting.GRAY));

        Compute.SuitDoubleDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("魔力涌动").withStyle(ChatFormatting.LIGHT_PURPLE));
        ComponentUtils.descriptionNum(components, "每秒回复", Compute.AttributeDescription.ManaRecover("5"), "");
        Compute.SuitQuadraDescription(components, Count);
        Compute.DescriptionPassive(components, Component.literal("黑曜能量涌动").withStyle(ChatFormatting.LIGHT_PURPLE));
        ComponentUtils.descriptionNum(components, "提升", Compute.AttributeDescription.AttackDamage("15%"), "");
        ComponentUtils.descriptionNum(components, "提升", ComponentUtils.AttributeDescription.manaDamage("25%"), "");
        ComponentUtils.descriptionNum(components, "获得", Compute.AttributeDescription.CoolDown("20%"), "");

    }
}