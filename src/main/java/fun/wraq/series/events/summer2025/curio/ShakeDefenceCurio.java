package fun.wraq.series.events.summer2025.curio;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.summer2024.SummerEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShakeDefenceCurio extends WraqCurios implements InCuriosOrEquipSlotAttributesModify {

    private final int tier;
    public ShakeDefenceCurio(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.defence.put(this, new double[]{40, 100, 250, 500}[tier]);
        Utils.manaDefence.put(this, new double[]{40, 100, 250, 500}[tier]);
        Utils.percentDefenceEnhance.put(this, new double[]{0, 0, 0.03, 0.06}[tier]);
        Utils.percentManaDefenceEnhance.put(this, new double[]{0, 0, 0.03, 0.06}[tier]);
        Utils.levelRequire.put(this, new int[]{80, 150, 200, 225}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    public double getDefenceEnhanceRate() {
        return new double[]{0.04, 0.10, 0.17, 0.25}[tier];
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("清凉沙冰", hoverMainStyle()));
        components.add(Te.s(" 夏季时", CustomStyle.styleOfPower, "获得:"));
        components.add(Te.s(" · ", CustomStyle.styleOfPower,
                ComponentUtils.AttributeDescription
                        .defence(String.format("%.2f%%", getDefenceEnhanceRate() * 100))));
        components.add(Te.s(" · ", CustomStyle.styleOfPower,
                ComponentUtils.AttributeDescription
                        .manaDefence(String.format("%.2f%%", getDefenceEnhanceRate() * 100))));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSummer2025();
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        if (SummerEvent.isInSummer()) {
            return List.of(
                    new Attribute(Utils.percentDefenceEnhance, getDefenceEnhanceRate()),
                    new Attribute(Utils.percentManaDefenceEnhance, getDefenceEnhanceRate())
            );
        }
        return List.of();
    }
}
