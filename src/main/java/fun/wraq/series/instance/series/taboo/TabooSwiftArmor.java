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

public class TabooSwiftArmor extends WraqArmor {

    public TabooSwiftArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 2560d);
        Utils.attackDamage.put(this, 550d);
        Utils.defence.put(this, 4d);
        Utils.manaDefence.put(this, 3d);
        Utils.swiftnessUp.put(this, 3d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("禁忌秘法-狩猎").withStyle(style));
        components.add(Component.literal(" 当箭矢命中目标时，若拥有高于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("10%")).
                append(Component.literal("则消耗").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("10%")).
                append(Component.literal("，来使你的箭矢附带").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("4倍").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("物理伤害").withStyle(CustomStyle.styleOfPower)));
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
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.TabooSwiftHelmet.get());
    }

    public static double ExDamage(Player player) {
        if (!IsOn(player)) return 0;
        if (Mana.getPlayerCurrentManaNum(player) / Mana.getPlayerMaxManaNum(player) > 0.1) {
            Mana.addOrCostPlayerMana(player, (-Mana.getPlayerMaxManaNum(player) * 0.1));
            return Compute.getXpStrengthADDamage(player, 4);
        }
        return 0;
    }

}