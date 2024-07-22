package com.very.wraq.series.instance.Devil;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevilAttackArmor extends WraqArmor {

    public DevilAttackArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 3072d);
        Utils.attackDamage.put(this, 450d);
        Utils.defence.put(this, 600d);
        Utils.manaDefence.put(this, 400d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("封魔者之怒").withStyle(style));
        components.add(Component.literal(" 受到来自怪物的伤害时，为你提供等同于怪物").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("50%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")).
                append(Component.literal("加成，持续2s").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static Map<Player, Double> DevilAttackArmorPassiveNumMap = new HashMap<>();
    public static Map<Player, Integer> DevilAttackArmorPassiveTickMap = new HashMap<>();

    public static void DevilAttackArmorPassive(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.DevilAttackChest.get())) {
            int TickCount = player.getServer().getTickCount();
            DevilAttackArmorPassiveNumMap.put(player, MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob, MobSpawn.MobBaseAttributes.attackDamage) * 0.5);
            DevilAttackArmorPassiveTickMap.put(player, TickCount + 40);
            Compute.effectLastTimeSend(player, ModItems.DevilBlood.get().getDefaultInstance(), 40);
        }
    }

    public static double DevilAttackArmorPassiveExDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (DevilAttackArmorPassiveTickMap.containsKey(player) && DevilAttackArmorPassiveTickMap.get(player) > TickCount)
            return DevilAttackArmorPassiveNumMap.get(player);
        return 0;
    }
}
