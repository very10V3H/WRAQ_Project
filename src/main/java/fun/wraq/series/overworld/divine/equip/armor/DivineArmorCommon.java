package fun.wraq.series.overworld.divine.equip.armor;

import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.onkill.OnKillEffectEquip;
import fun.wraq.common.impl.skillv2.EnhanceSkillRateEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.equip.weapon.DivineWeaponCommon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface DivineArmorCommon extends EnhanceSkillRateEquip, OnKillEffectEquip, ForgeItem {
    Style style = CustomStyle.DIVINE_STYLE;
    static List<Component> getDescription(ItemStack stack, double maxRate, int maxCount) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("圣光恩赐", style));
        Component countName = ComponentUtils.getRightAngleQuote("圣光恩赐", style);
        components.add(Te.s(" 击杀怪物将会受", countName, style));
        components.add(Te.s(" ", countName, style, "将存储在这件物品内"));
        components.add(Te.s(" ", countName, style, "至多为你提供:"));
        Component skill;
        ArmorItem item = (ArmorItem) stack.getItem();
        switch (item.getType()) {
            case CHESTPLATE -> skill = Te.s("终极技能", CustomStyle.styleOfRed);
            case HELMET -> skill = Te.s("基础技能 - 1", ChatFormatting.AQUA);
            case LEGGINGS -> skill = Te.s("基础技能 - 2", ChatFormatting.AQUA);
            case BOOTS -> skill = Te.s("基础技能 - 3", ChatFormatting.AQUA);
            default -> skill = Te.s("");
        }
        components.add(Te.s(" ", skill, "的",
                String.format("%.0f%%", maxRate * 100) + "增幅", style));
        int count = DivineWeaponCommon.getDivineCount(stack);
        components.add(Te.s(" 圣光充盈度:"
                + String.format("%.0f%%",
                Math.min(count, maxCount) * 100.0 / maxCount), style, "(" + count + ")", ChatFormatting.GRAY));
        components.add(Te.s(" ".repeat(4),
                ComponentUtils.getProgressBar(20, count, maxCount, style)));
        components.add(Te.s(" 在收集", maxCount + "层", style, countName, "后达最大值"));
        components.add(Te.s(" ", countName, "每日将会清空", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        return components;
    }

    static double getCommonEnhanceRate(ItemStack stack, double maxRate, int maxCount) {
        return DivineWeaponCommon.getDivineCount(stack) * 1.0 / maxCount * maxRate;
    }
}
