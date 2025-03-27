package fun.wraq.customized.uniform.attack;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AttackCurios4 extends WraqAttackUniformCurios {

    public AttackCurios4(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Te.s("嗜杀成性", hoverMainStyle()));
        components.add(Te.s(" 剑锋攻击", style, "造成的伤害", "+75%", style));
        components.add(Te.s(" 剑锋攻击:", style, "对3格以外的目标的普通攻击。"));
        components.add(Component.literal(" 残暴的君主，终将被民众推翻。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("暴戾恣睢").withStyle(hoverMainStyle());
    }

    public static double getAttackDamageRate(Player player, Mob mob) {
        if (Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(UniformItems.ATTACK_CURIOS_4.get())) {
            if (mob.distanceTo(player) > 3) {
                return 0.75;
            }
        }
        return 0;
    }
}
