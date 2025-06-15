package fun.wraq.series.overworld.sakura.bunker.armor;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BunkerArmor extends WraqArmor {

    public final int tier;
    public BunkerArmor(ArmorMaterial armorMaterial, Type type, Properties properties, int tier) {
        super(armorMaterial, type, properties);
        this.tier = tier;
        if (type.equals(Type.HELMET)) {
            Utils.percentHealthRecover.put(this, new double[]{0.015, 0.02}[tier]);
            Utils.healthRecover.put(this, new double[]{200, 250}[tier]);
            Utils.defence.put(this, new double[]{75d, 85d}[tier]);
            Utils.manaDefence.put(this, new double[]{40d, 45d}[tier]);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, new double[]{150d, 170d}[tier]);
            Utils.manaDefence.put(this, new double[]{80d, 90d}[tier]);
            Utils.maxHealth.put(this, new double[]{30000, 35000}[tier]);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, new double[]{60000, 70000}[tier]);
            Utils.defence.put(this, new double[]{75d, 85d}[tier]);
            Utils.manaDefence.put(this, new double[]{40d, 45d}[tier]);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.16);
            Utils.maxHealth.put(this, new double[]{30000, 35000}[tier]);
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.BUNKER_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("聚变能量", getMainStyle()));
        components.add(Te.s(" 产热 + ", (tier + 1), getMainStyle()));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfBunker();
    }
}
