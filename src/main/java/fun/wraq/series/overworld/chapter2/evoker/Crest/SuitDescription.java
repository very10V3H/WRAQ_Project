package fun.wraq.series.overworld.chapter2.evoker.Crest;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
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
public class SuitDescription {
    public static void CrestSuitDescription(List<Component> components) {
        ComponentUtils.suitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = CustomStyle.styleOfMana;
        ArrayList<Item> ManaArmor = new ArrayList<>() {{
            add(ModItems.LifeManaArmorHelmet.get());
            add(ModItems.ObsiManaArmorHelmet.get());
            add(ModItems.LifeManaArmorChest.get());
            add(ModItems.ObsiManaArmorChest.get());
            add(ModItems.LifeManaArmorLeggings.get());
            add(ModItems.ObsiManaArmorLeggings.get());
            add(ModItems.LifeManaArmorBoots.get());
            add(ModItems.ObsiManaArmorBoots.get());
        }};
        ArrayList<String> ArmorName = new ArrayList<>() {{
            add("[生机/黑曜魔力头盔]");
            add("[生机/黑曜魔力胸甲]");
            add("[生机/黑曜魔力护腿]");
            add("[生机/黑曜魔力靴子]");
        }};
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
        boolean flag = true;
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0) flag = true;
            if (player.getItemBySlot(equipmentSlot[i / 2]).is(ManaArmor.get(i))) {
                components.add(Component.literal(ManaArmor.get(i).getDefaultInstance().getDisplayName().getString()).withStyle(MainStyle));
                flag = false;
            }
            if (i % 2 == 1 && flag) {
                components.add(Component.literal(ArmorName.get(i / 2)).withStyle(ChatFormatting.GRAY));
            }
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

    }
}
