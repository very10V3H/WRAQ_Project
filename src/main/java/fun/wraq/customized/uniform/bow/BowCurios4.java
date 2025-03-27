package fun.wraq.customized.uniform.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BowCurios4 extends WraqBowUniformCurios {

    public BowCurios4(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfFlexible;
        ComponentUtils.descriptionPassive(components, Te.s("气贯长虹", style));
        components.add(Te.s(" 箭术 - 速射", style, "的", "箭矢数量 + 7", style));
        components.add(Component.literal(" 不仅是敏捷，力量、智慧对在恶劣环境中的猎手同样重要。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("落拓不羁").withStyle(hoverMainStyle());
    }

    public static int getExArrowCount(Player player) {
        if (Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(UniformItems.BOW_CURIOS_4.get())) {
            return 7;
        }
        return 0;
    }
}
