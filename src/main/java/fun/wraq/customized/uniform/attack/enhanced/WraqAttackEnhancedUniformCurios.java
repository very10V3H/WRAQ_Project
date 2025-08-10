package fun.wraq.customized.uniform.attack.enhanced;

import fun.wraq.common.util.Utils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.customized.uniform.UnCommonUniform;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

public abstract class WraqAttackEnhancedUniformCurios extends WraqUniformCurios implements UnCommonUniform {

    public WraqAttackEnhancedUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, 3d);
        Utils.xpLevelDefencePenetration0.put(this, 0.15d);
        Utils.critDamage.put(this, 0.12);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPower;
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
