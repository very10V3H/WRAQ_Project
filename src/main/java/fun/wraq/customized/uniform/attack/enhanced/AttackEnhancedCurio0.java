package fun.wraq.customized.uniform.attack.enhanced;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AttackEnhancedCurio0 extends WraqAttackEnhancedUniformCurios {

    public AttackEnhancedCurio0(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Te.s("暴政", hoverMainStyle()));
        components.add(Te.s(" 获得", ComponentUtils.AttributeDescription.critDamage("12%总")));
        Compute.DescriptionPassive(components, Te.s("横行", hoverMainStyle()));
        Component countName = ComponentUtils.getRightAngleQuote("暴怒", hoverMainStyle());
        components.add(Te.s(" 暴击", hoverMainStyle(), "将会获得一层", countName, "."));
        components.add(Te.s(" 持续5s，至多叠加至", "7层", hoverMainStyle()));
        components.add(Te.s(" 每层", countName, "为你提供:"));
        components.add(Te.s(" 1.", ComponentUtils.AttributeDescription.critDamage("5%")));
        components.add(Te.s(" 2.", ComponentUtils.AttributeDescription.attackDamage("5%总")));
        components.add(Te.s(" 只有近战攻击的暴击能够触发横行", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        components.add(Te.s(" 同名的被动不会重复生效.", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Te.s("暴虐专政", hoverMainStyle());
    }
}
