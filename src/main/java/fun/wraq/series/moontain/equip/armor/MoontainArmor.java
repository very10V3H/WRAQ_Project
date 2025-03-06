package fun.wraq.series.moontain.equip.armor;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.moontain.equip.MoontainEquip;
import fun.wraq.series.moontain.equip.weapon.MoontainUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoontainArmor extends WraqArmor implements ExBaseAttributeValueEquip, Decomposable, MoontainEquip {

    public MoontainArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
        if (type.equals(Type.HELMET)) {
            Utils.percentHealthRecover.put(this, 0.011);
            Utils.healthRecover.put(this, 130d);
            Utils.defence.put(this, 40d);
            Utils.manaDefence.put(this, 20d);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, 85d);
            Utils.manaDefence.put(this, 20d);
            Utils.maxHealth.put(this, 18000d);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 36000d);
            Utils.defence.put(this, 40d);
            Utils.manaDefence.put(this, 20d);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.14);
            Utils.maxHealth.put(this, 18000d);
        }
        Utils.levelRequire.put(this, 210);
    }

    @Override
    public Style getQuoteStyle() {
        return CustomStyle.styleOfMoontain;
    }

    @Override
    public Style getExValueStyle() {
        return Style.EMPTY.applyFormat(ChatFormatting.GREEN);
    }

    @Override
    public Map<Map<Item, Double>, TagAndEachTierValue> getTagAndRateMap() {
        if (type.equals(Type.HELMET)) {
            return Map.of(Utils.healthRecover, new TagAndEachTierValue(MoontainUtils.MOONTAIN_HEALTH_RECOVER_TAG_KEY, 10));
        }
        if (type.equals(Type.CHESTPLATE)) {
            return Map.of(Utils.defence, new TagAndEachTierValue(MoontainUtils.MOONTAIN_DEFENCE_TAG_KEY, 5));
        }
        if (type.equals(Type.LEGGINGS)) {
            return Map.of(Utils.maxHealth, new TagAndEachTierValue(MoontainUtils.MOONTAIN_MAX_HEALTH_TAG_KEY, 2000d));
        }
        if (type.equals(Type.BOOTS)) {
            return Map.of(Utils.movementSpeedCommon, new TagAndEachTierValue(MoontainUtils.MOONTAIN_MOVEMENT_SPEED_TAG_KEY, 0.01));
        }
        return Map.of();
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoontain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("筑造", getMainStyle()));
        components.add(Te.s(" 筑造阶数: ", getMainStyle(),
                String.valueOf(ExBaseAttributeValueEquip.getForgeTier(stack, MoontainUtils.getTraditionalAttributeMap(stack)))));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoontain();
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(MoontainItems.HEART.get(), 4);
    }
}
