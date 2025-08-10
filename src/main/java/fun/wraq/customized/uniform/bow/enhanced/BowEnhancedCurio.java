package fun.wraq.customized.uniform.bow.enhanced;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onshoot.OnShootArrowCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BowEnhancedCurio extends WraqBowEnhancedUniformCurios implements OnShootArrowCurios {

    public BowEnhancedCurio(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Te.s("闪避突袭", hoverMainStyle()));
        components.add(Te.s(" 使用", "翻滚", hoverMainStyle(), "后，获得",
                ComponentUtils.AttributeDescription.swiftness("15%总")));
        components.add(Te.s(" 并使", "箭矢", hoverMainStyle(), "提升",
                "75%基础伤害", CustomStyle.styleOfPower, "."));
        components.add(Te.s(" 效果持续3s.", ChatFormatting.AQUA));
        Compute.DescriptionPassive(components, Te.s("精湛弓术", hoverMainStyle()));
        components.add(Te.s(" 普通箭矢攻击", hoverMainStyle(), "将额外释放一枚具有"));
        components.add(Te.s(" 125%基础伤害", CustomStyle.styleOfPower, "的", "箭矢", hoverMainStyle()));
        components.add(Te.s(" 同名的被动不会重复生效.", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Te.s("进化论", hoverMainStyle());
    }

    @Override
    public void onShoot(Player player) {
        WraqQuiver.batchAddExShoot(player, 1.25, 1);
    }
}
