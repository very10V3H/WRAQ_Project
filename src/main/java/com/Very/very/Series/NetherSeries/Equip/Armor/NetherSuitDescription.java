package com.Very.very.Series.NetherSeries.Equip.Armor;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
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
public class NetherSuitDescription {
    public static void ArmorCommonDescription(List<Component> components) {
        Compute.DescriptionOfAddtion(components);
        SuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfNether,ChatFormatting.WHITE);
        components.add(Component.literal("NetherArmor").withStyle(Utils.styleOfNether).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);

    }
    public static void SuitDescription(List<Component> components) {
        Compute.SuitDescription(components);

        int Count = 0;
        Player player = Minecraft.getInstance().player;
        Style MainStyle = Utils.styleOfNether;
        Item[] items = {
                ModItems.NetherArmorHelmet.get(),
                ModItems.NetherArmorChest.get(),
                ModItems.NetherArmorLeggings.get(),
                ModItems.NetherArmorBoots.get(),
                ModItems.NetherPower.get()
        };
        EquipmentSlot[] equipmentSlot = {
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET,
                EquipmentSlot.OFFHAND
        };
        for (int i = 0; i < items.length; i ++) {
            Count += Compute.SuitItemVision(player,items[i],equipmentSlot[i],components,MainStyle);
        }

        String CrestString = StringUtils.Crest.Nether;
        String CrestName = "[下界征服者徽记]";
        if (ClientUtils.CrestMap.containsKey(CrestString) && ClientUtils.CrestMap.get(CrestString))  {
            components.add(Component.literal(CrestName).withStyle(ChatFormatting.RESET).withStyle(MainStyle));
            Count ++;
        }
        else components.add(Component.literal(CrestName).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));

        Compute.DescriptionPassive(components,Component.literal("迸骸成末").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether));
        components.add(Component.literal("近战攻击与箭矢时基于目标的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("提供至多50%伤害提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("当目标的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("高于500时达到最大效能。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" "));
        Compute.SuitEffectRateDescription(components,Count);
    }
}
