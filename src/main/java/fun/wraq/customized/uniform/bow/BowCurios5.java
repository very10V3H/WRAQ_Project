package fun.wraq.customized.uniform.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BowCurios5 extends WraqBowUniformCurios {

    public BowCurios5(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfFlexible;
        ComponentUtils.descriptionPassive(components, Te.s("义薄云天", style));
        components.add(Te.s(" 箭矢", style, "将", "不会下坠，", CustomStyle.styleOfStone, "并基于与目标的距离;"));
        components.add(Te.s(" 提供至多", "+75%箭矢基础伤害", style));
        components.add(Te.s(" 在距离目标32格时有最大加成", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Component.literal(" 不仅是敏捷，力量、智慧对在恶劣环境中的猎手同样重要。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("放浪形骸").withStyle(hoverMainStyle());
    }

    public static boolean isOn(Player player) {
        return Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(UniformItems.BOW_CURIOS_5.get());
    }

    public static double getExArrowDamageRate(Player player, Mob mob) {
        if (isOn(player)) {
            return Math.min(0.75, player.distanceTo(mob) / 32 * 0.75);
        }
        return 0;
    }
}
