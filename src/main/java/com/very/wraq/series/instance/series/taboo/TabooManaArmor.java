package com.very.wraq.series.instance.series.taboo;

import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabooManaArmor extends WraqArmor {

    public TabooManaArmor(ItemMaterial Material, Type type, Properties itemProperties) {
        super(Material, type, itemProperties);
        Utils.maxHealth.put(this, 2560d);
        Utils.manaDamage.put(this, 1500d);
        Utils.defence.put(this, 400d);
        Utils.manaDefence.put(this, 280d);
        Utils.manaRecover.put(this, 30d);
        Utils.coolDownDecrease.put(this, 0.2);
        if (this.type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.45);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("禁忌秘法-涌动").withStyle(style));
        components.add(Component.literal(" 根据你最近5s内消耗的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("")).
                append(Component.literal("来提供等量").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaPenetration("")));
        components.add(Component.literal(" 若你拥有至少一点:").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术专精-力凝魔核").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("为你提供等同于层数 * 3%").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("最大")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaPenetration("")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix2Sakura();
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
        list.add(new NearCostMana(TickCount + 100, value));
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
        if (Compute.ManaSkillLevelGet(player.getPersistentData(), 10) > 0)
            return (PlayerAttributes.maxManaUp(player) + 100) * Compute.ManaSkillLevelGet(player.getPersistentData(), 10) * 0.03;
        return 0;
    }

}
