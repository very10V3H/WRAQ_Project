package fun.wraq.series.overworld.sun;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BrokenBlade extends WraqCurios {

    private final int tier;
    public BrokenBlade(Properties properties, int tier) {
        super(properties);
        Utils.attackDamage.put(this, 70d);
        Utils.levelRequire.put(this, 150);
        this.tier = tier;
    }

    private final double[] effectRate = new double[]{2, 2.5, 3, 3.5};

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("剑气", hoverMainStyle()));
        components.add(Te.s(" 使", "剑术技能 - 注魔之刃", CustomStyle.styleOfPower));
        components.add(Te.s(" - 1.", CustomStyle.styleOfWorld, "伤害", "+100%", CustomStyle.styleOfPower));
        components.add(Te.s(" - 2.", CustomStyle.styleOfWorld, "持续时间", "+1s", CustomStyle.styleOfWorld));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfGold;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSunIsland();
    }

    public static boolean enhanceSwordSkillV2_4(Player player) {
        return Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(SunIslandItems.BROKEN_BLADE_0.get());
    }
}
