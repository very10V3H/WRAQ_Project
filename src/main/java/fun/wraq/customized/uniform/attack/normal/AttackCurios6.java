package fun.wraq.customized.uniform.attack.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onhit.OnHitDamageInfluenceCurios;
import fun.wraq.common.impl.withstand.ModifyPlayerWithstandDamageInfluenceCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AttackCurios6 extends WraqAttackUniformCurios implements OnHitDamageInfluenceCurios,
        ModifyPlayerWithstandDamageInfluenceCurios {

    public AttackCurios6(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Te.s("孤立无援", hoverMainStyle()));
        components.add(Te.s(" 对于附近8格内没有其他敌人的目标:"));
        components.add(Te.s(" · ", hoverMainStyle(), "对其的普通攻击", "必定暴击.", hoverMainStyle()));
        components.add(Te.s(" · ", hoverMainStyle(), "对其造成的伤害获得",
                ComponentUtils.getCommonDamageEnhance("35%"), "."));
        components.add(Te.s(" · ", hoverMainStyle(), "免疫", CustomStyle.styleOfStone, "其25%伤害."));
        components.add(Component.literal(" 残暴的掠夺者，从未怜悯弱者.").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("残暴掠夺").withStyle(hoverMainStyle());
    }

    public static boolean isOn(Player player) {
        return Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(UniformItems.ATTACK_CURIOS_6.get());
    }

    public static boolean isSingleTarget(Mob mob) {
        return Compute.getNearMob(mob, 8).size() <= 1;
    }

    public static boolean isSurelyCrit(Player player, Mob mob) {
        return isOn(player) && isSingleTarget(mob);
    }

    @Override
    public double modifyHitDamageRate(Player player, Mob mob) {
        return isSingleTarget(mob) ? 0.35 : 0;
    }

    @Override
    public double modifyWithstandDamage(Player player, Mob mob) {
        return isSingleTarget(mob) ? -0.25 : 0;
    }
}
