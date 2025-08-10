package fun.wraq.customized.uniform.mana.enhanced;

import fun.wraq.common.util.Utils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.customized.uniform.UnCommonUniform;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

public abstract class WraqManaEnhancedUniformCurios extends WraqUniformCurios implements UnCommonUniform {

    public WraqManaEnhancedUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelManaDamage.put(this, 6d);
        Utils.xpLevelManaPenetration0.put(this, 0.15d);
        Utils.manaRecover.put(this, 30d);
        Utils.maxMana.put(this, 1000d);
        Utils.coolDownDecrease.put(this, 0.15);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public double getFinalDamageEnhanceRate() {
        return 0.75;
    }

    @Override
    public ItemStack getProduct() {
        return null;
    }
}
