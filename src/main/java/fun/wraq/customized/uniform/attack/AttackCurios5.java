package fun.wraq.customized.uniform.attack;

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

public class AttackCurios5 extends WraqAttackUniformCurios {

    public AttackCurios5(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Te.s("穷兵黩武", hoverMainStyle()));
        components.add(Te.s(" 你的", ComponentUtils.AttributeDescription.health(""),
                "不会超过", "80%", CustomStyle.styleOfHealth, "。"));
        components.add(Te.s(" 每降低", ComponentUtils.AttributeDescription.health("1%"),
                "获得", ComponentUtils.AttributeDescription.critDamage("1%"),
                "与", ComponentUtils.AttributeDescription.attackDamage("0.5%")));
        components.add(Component.literal(" 残暴的君主，终将被民众推翻。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("刚愎自用").withStyle(hoverMainStyle());
    }

    public static boolean isOn(Player player) {
        return Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(UniformItems.ATTACK_CURIOS_5.get());
    }

    public static boolean onHealHealthRecover(Player player, double healNum) {
        if (isOn(player)) {
            return (player.getHealth() + healNum) / player.getMaxHealth() > 0.8;
        }
        return false;
    }

    public static double getExCritDamage(Player player) {
        if (isOn(player)) {
            return (1 - player.getHealth() / player.getMaxHealth());
        }
        return 0;
    }

    public static double getExPercentAttackDamage(Player player) {
        if (isOn(player)) {
            return (1 - player.getHealth() / player.getMaxHealth()) * 0.5;
        }
        return 0;
    }
}
