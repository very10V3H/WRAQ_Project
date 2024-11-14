package fun.wraq.series.instance.series.sakura;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Boss2AttackRing extends WraqCurios {

    public Boss2AttackRing(Properties properties, int tier) {
        super(properties);
        Utils.attackDamage.put(this, new double[]{160, 200, 240, 280}[tier]);
        Utils.percentAttackDamageEnhance.put(this, new double[]{0.01, 0.015, 0.02, 0.025}[tier]);
        Utils.levelRequire.put(this, 150);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfGold;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSakura();
    }
}
