package fun.wraq.customized.uniform.mana.enhanced;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ManaEnhancedCurio extends WraqManaEnhancedUniformCurios {

    public ManaEnhancedCurio(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Te.s("法术调幅", hoverMainStyle()));
        components.add(Te.s(" 获得", ComponentUtils.AttributeDescription.manaDamage("50%总")));
        Compute.DescriptionPassive(components, Te.s("解构凡躯", hoverMainStyle()));
        components.add(Te.s(" 造成的", "魔法伤害", hoverMainStyle(),
                "将额外造成", "50%真实伤害", CustomStyle.styleOfSea, "."));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Te.s("MUX", hoverMainStyle());
    }
}
