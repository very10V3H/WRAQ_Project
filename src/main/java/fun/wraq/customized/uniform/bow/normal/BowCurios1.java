package fun.wraq.customized.uniform.bow.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onshoot.OnShootArrowCurios;
import fun.wraq.customized.UniformItems;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BowCurios1 extends WraqBowUniformCurios implements OnShootArrowCurios {

    public BowCurios1(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Te.s("精湛弓术", hoverMainStyle()));
        components.add(Te.s(" 普通箭矢攻击", hoverMainStyle(), "将额外释放一枚具有"));
        components.add(Te.s(" 100%基础伤害", CustomStyle.styleOfPower, "的", "箭矢", hoverMainStyle()));
        components.add(Te.s(" 不仅是敏捷，力量、智慧对在恶劣环境中的猎手同样重要。", hoverMainStyle()));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Te.s("优胜劣汰", hoverMainStyle());
    }

    @Override
    public void onShoot(Player player) {
        if (!WraqCurios.hasCurio(player, UniformItems.BOW_ENHANCED_CURIOS_0.get())) {
            WraqQuiver.batchAddExShoot(player, 1, 1);
        }
    }
}
