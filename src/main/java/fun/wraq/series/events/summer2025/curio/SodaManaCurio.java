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

public class SodaManaCurio extends WraqCurios implements InCuriosOrEquipSlotAttributesModify {

    private final int tier;
    public SodaManaCurio(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.manaDamage.put(this, new double[]{400, 800, 1600, 3200}[tier]);
        Utils.percentManaDamageEnhance.put(this, new double[]{0, 0, 0.015, 0.03}[tier]);
        Utils.levelRequire.put(this, new int[]{80, 150, 200, 225}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    public double getAttackEnhanceRate() {
        return new double[]{0.02, 0.05, 0.09, 0.15}[tier];
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("冰爽汽水", hoverMainStyle()));
        components.add(Te.s(" 夏季时", CustomStyle.styleOfPower, "获得:"));
        components.add(Te.s(" · ", CustomStyle.styleOfPower,
                ComponentUtils.AttributeDescription
                        .manaDamage(String.format("%.0f%%", getAttackEnhanceRate() * 100))));
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
                    new Attribute(Utils.percentManaDamageEnhance, getAttackEnhanceRate())
            );
        }
        return List.of();
    }
}
