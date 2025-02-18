package fun.wraq.series.overworld.sun;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TabooPaper extends WraqCurios {

    public TabooPaper(Properties properties) {
        super(properties);
        Utils.manaPenetration.put(this, 0.04);
        Utils.levelRequire.put(this, 150);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("禁忌秘法", hoverMainStyle()));
        components.add(Te.s(" 使", "法术 - 撕裂", CustomStyle.styleOfMana, ":"));
        components.add(Te.s( " 1.", hoverMainStyle(), "每次伤害 ", "+20%", CustomStyle.styleOfMana));
        components.add(Te.s(" 2.", hoverMainStyle(), "持续时间 ", "+1s", ChatFormatting.AQUA));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfDemon;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSunIsland();
    }

    public static boolean enhanceManaSkillV2_2(Player player) {
        return Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(SunIslandItems.TABOO_PAPER_CURIO.get());
    }
}
