package fun.wraq.customized.uniform.attack;

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

public class AttackCurios3 extends WraqAttackUniformCurios {

    public AttackCurios3(Properties p_41383_) {
        super(p_41383_);
        Utils.attackSpeedEnhance.put(this, 0.2);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Te.s("只手遮天", hoverMainStyle()));
        components.add(Te.s(" 攻击范围", CustomStyle.styleOfMoon, "+50%", hoverMainStyle()));
        components.add(Component.literal(" 残暴的君主，终将被民众推翻。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("烈手损莩").withStyle(hoverMainStyle());
    }

    public static double getAttackRangeEnhanceRate(Player player) {
        if (Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(UniformItems.ATTACK_CURIOS_3.get())) {
            return 0.5;
        }
        return 0;
    }
}
