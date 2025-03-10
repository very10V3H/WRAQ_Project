package fun.wraq.customized.uniform.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.UniformItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BowCurios3 extends WraqBowUniformCurios {

    public BowCurios3(Properties p_41383_) {
        super(p_41383_);
        Utils.attackSpeedEnhance.put(this, 0.2);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfFlexible;
        ComponentUtils.descriptionPassive(components, Te.s("穿云御风", style));
        components.add(Te.s(" 箭矢速度", CustomStyle.styleOfFlexible, "+50%", CustomStyle.styleOfKaze));
        components.add(Component.literal(" 不仅是敏捷，力量、智慧对在恶劣环境中的猎手同样重要。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("猎物感知").withStyle(hoverMainStyle());
    }

    public static float getArrowExFlySpeed(Player player) {
        if (Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(UniformItems.BOW_CURIOS_3.get())) {
            return 1.5f;
        }
        return 0;
    }
}
