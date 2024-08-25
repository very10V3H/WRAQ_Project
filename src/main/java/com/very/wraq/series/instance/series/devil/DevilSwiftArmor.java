package com.very.wraq.series.instance.series.devil;

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

public class DevilSwiftArmor extends WraqArmor {

    public DevilSwiftArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 1728d);
        Utils.attackDamage.put(this, 450d);
        Utils.defence.put(this, 360d);
        Utils.manaDefence.put(this, 240d);
        Utils.swiftnessUp.put(this, 2d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.45);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("唤魔者之息").withStyle(style));
        components.add(Component.literal(" 箭矢命中目标时，为你提供等同于目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("50%")).
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

    public static Map<Player, Double> DevilSwiftArmorPassiveNumMap = new HashMap<>();
    public static Map<Player, Integer> DevilSwiftArmorPassiveTickMap = new HashMap<>();

    public static void DevilSwiftArmorPassive(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.DevilSwiftBoots.get())) {
            int TickCount = player.getServer().getTickCount();
            DevilSwiftArmorPassiveNumMap.put(player, MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob, MobSpawn.MobBaseAttributes.defence) * 0.5);
            DevilSwiftArmorPassiveTickMap.put(player, TickCount + 40);
            Compute.sendEffectLastTime(player, ModItems.DevilBlood.get().getDefaultInstance(), 40);
        }
    }

    public static double DevilSwiftArmorPassiveExDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (DevilSwiftArmorPassiveTickMap.containsKey(player) && DevilSwiftArmorPassiveTickMap.get(player) > TickCount)
            return DevilSwiftArmorPassiveNumMap.get(player);
        return 0;
    }
}
