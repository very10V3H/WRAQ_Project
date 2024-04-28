package com.very.wraq.series.instance.Taboo;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabooManaArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfBloodMana;
    private String type = "";

    public TabooManaArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type) {
        super(Material, Slots, itemProperties);
        Utils.MaxHealth.put(this, 2560d);
        Utils.ManaDamage.put(this, 1560d);
        Utils.Defence.put(this, 180d);
        Utils.ManaDefence.put(this, 180d);
        Utils.ManaRecover.put(this, 30d);
        Utils.CoolDownDecrease.put(this, 0.2);
        Utils.ArmorTag.put(this, 1d);
        Utils.ArmorList.add(this);
        switch (type) {
            case 0 -> this.type = "头盔";
            case 1 -> this.type = "胸甲";
            case 2 -> this.type = "护腿";
            case 3 -> this.type = "靴子";
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.ForgingHoverName(stack, Component.literal("紫晶铁").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal(type).withStyle(style)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("禁忌秘法-涌动").withStyle(style));
        components.add(Component.literal(" 根据你最近5s内消耗的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("")).
                append(Component.literal("来提供等量").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaPenetration("")));
        components.add(Component.literal(" 若你拥有至少一点:").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术专精-力凝魔核").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("为你提供等同于层数 * 10%").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("最大")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaPenetration("")));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public record NearCostMana(int tick, double value) {
    }

    public static Map<Player, List<NearCostMana>> nearCostListMap = new HashMap<>();

    public static boolean IsOn(Player player) {
        return player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.TabooManaBoots.get());
    }

    public static void storeCostToList(Player player, double value) {
        if (!IsOn(player)) return;
        int TickCount = player.getServer().getTickCount();
        if (!nearCostListMap.containsKey(player)) nearCostListMap.put(player, new ArrayList<>());
        List<NearCostMana> list = nearCostListMap.get(player);
        list.add(new NearCostMana(TickCount + 100,value));
    }

    public static double storeCostValue(Player player) {
        if (!IsOn(player)) return 0;
        int TickCount = player.getServer().getTickCount();
        if (!nearCostListMap.containsKey(player)) return 0;
        List<NearCostMana> list = nearCostListMap.get(player);
        list.removeIf(nearCostMana -> nearCostMana.tick < TickCount);
        int storedValue = 0;
        for (NearCostMana nearCostMana : list) {
            storedValue += nearCostMana.value;
        }
        return storedValue;
    }

    public static double PenetrationDirectEnhance(Player player) {
        if (!IsOn(player)) return 0;
        if (Compute.ManaSkillLevelGet(player.getPersistentData(),10) > 0)
            return (Compute.PlayerAttributes.PlayerMaxMana(player) + 100) * Compute.ManaSkillLevelGet(player.getPersistentData(),10) * 0.1;

        return 0;
    }

}
