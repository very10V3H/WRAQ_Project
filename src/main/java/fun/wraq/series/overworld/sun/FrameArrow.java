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

public class FrameArrow extends WraqCurios {

    private final int tier;
    public FrameArrow(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.critDamage.put(this, 0.04);
        Utils.levelRequire.put(this, 150);
    }

    private final double[] effectRate = new double[]{1, 1.5, 2, 2.5};

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("红莲弓矢", hoverMainStyle()));
        components.add(Te.s("使", "弓术技能 - 烈矢", CustomStyle.styleOfFlexible));
        components.add(Te.s(" - 1.", CustomStyle.styleOfWorld, "伤害", "+100%", CustomStyle.styleOfPower));
        components.add(Te.s(" - 2.", CustomStyle.styleOfWorld, "冷却时间", "-1s", CustomStyle.styleOfWorld));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfRed;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSunIsland();
    }

    public static boolean enhanceBowSkillV2_2(Player player) {
        return Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(SunIslandItems.FRAME_ARROW_0.get());
    }
}
