package fun.wraq.series.overworld.cold.sc4;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ColdIronArmor extends WraqArmor {

    public final int tier;

    public ColdIronArmor(ArmorMaterial armorMaterial, Type type, Properties properties, int tier) {
        super(armorMaterial, type, properties);
        this.tier = tier;
        if (type.equals(Type.HELMET)) {
            Utils.percentHealthRecover.put(this, new double[]{0.015, 0.02, 0.025}[tier]);
            Utils.healthRecover.put(this, new double[]{200, 250, 300}[tier]);
            Utils.defence.put(this, new double[]{75d, 85d, 100d}[tier]);
            Utils.manaDefence.put(this, new double[]{40d, 45d, 50d}[tier]);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, new double[]{150d, 170d, 200d}[tier]);
            Utils.manaDefence.put(this, new double[]{80d, 90d, 100d}[tier]);
            Utils.maxHealth.put(this, new double[]{30000, 35000, 40000}[tier]);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, new double[]{60000, 70000, 80000}[tier]);
            Utils.defence.put(this, new double[]{75d, 85d, 100d}[tier]);
            Utils.manaDefence.put(this, new double[]{40d, 45d, 50d}[tier]);
        }
        Utils.movementSpeedCommon.put(this, -0.25);
        Utils.levelRequire.put(this, 230);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    public static double[] getCritDamageReductionRate = new double[]{0.2, 0.25, 0.4, 0.55};

    public static double getPlayerCritDamageReductionRate(Player player) {
        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ColdIronArmor armor) {
            return getCritDamageReductionRate[armor.tier];
        }
        return 0;
    }

    public static double[] getWithstandDamageReductionRate = new double[]{0.1, 0.15, 0.25, 0.4};

    public static double getWithstandDamageReductionRate(Player player) {
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ColdIronArmor armor) {
            return getWithstandDamageReductionRate[armor.tier];
        }
        return 0;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("寒铁坚甲", getMainStyle()));
        if (getType().equals(Type.HELMET)) {
            components.add(Te.s(" 减少" + String.format("%.0f%%", getCritDamageReductionRate[tier] * 100),
                    "受到的", ComponentUtils.AttributeDescription.critDamage("")));
        }
        if (getType().equals(Type.CHESTPLATE)) {
            components.add(Te.s(" 减少" + String.format("%.0f%%", getWithstandDamageReductionRate[tier] * 100),
                    "受到的", "任意伤害", CustomStyle.styleOfMoon));
        }
        if (getType().equals(Type.LEGGINGS)) {
            if (tier >= 2) {
                components.add(Te.s(" 免疫", "减速效果", CustomStyle.styleOfSnow,
                        "与", "禁锢效果", CustomStyle.styleOfMine));
            } else {
                components.add(Te.s(" 免疫", "减速效果", CustomStyle.styleOfSnow));
            }
            components.add(Te.s(" 免疫不包括装备带来的移动速度影响"));
        }
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSuperCold();
    }
}
