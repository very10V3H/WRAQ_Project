package fun.wraq.series.instance.series.taboo;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TabooAttackArmor extends WraqArmor {

    public TabooAttackArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 8000d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("禁忌秘法-加护").withStyle(style));
        components.add(Component.literal(" 当受到来自怪物的伤害时，若拥有高于").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaValue("10%")));
        components.add(Component.literal(" ").
                append(Component.literal("则消耗").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaValue("10%")).
                append(Component.literal("来使即将到来的伤害减少80%").withStyle(ChatFormatting.WHITE)));
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

    public static boolean IsOn(Player player) {
        return player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.TabooAttackLeggings.get());
    }

    public static double Passive(Player player) {
        if (!IsOn(player)) return 1;
        if (Mana.getPlayerCurrentManaNum(player) / Mana.getPlayerMaxManaNum(player) > 0.1) {
            Mana.addOrCostPlayerMana(player, (-Mana.getPlayerMaxManaNum(player) * 0.1));
            return 0.2;
        }
        return 1;
    }


}
