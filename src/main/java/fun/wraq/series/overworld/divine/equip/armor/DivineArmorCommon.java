package fun.wraq.series.overworld.divine.equip.armor;

import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.onkill.OnKillEffectEquip;
import fun.wraq.common.impl.skillv2.EnhanceSkillRateEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface DivineArmorCommon extends EnhanceSkillRateEquip, OnKillEffectEquip, ForgeItem {
    Style style = CustomStyle.DIVINE_STYLE;
    static List<Component> getDescription(ItemStack stack, double maxRate) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("圣光辐照", style));
        Component countName = ComponentUtils.getRightAngleQuote("圣光辐照", style);
        components.add(Te.s(" 击杀一名怪物，获得一层", countName));
        Component skill;
        ArmorItem item = (ArmorItem) stack.getItem();
        switch (item.getType()) {
            case CHESTPLATE -> skill = Te.s("终极技能", CustomStyle.styleOfRed);
            case HELMET -> skill = Te.s("基础技能 - 1", ChatFormatting.AQUA);
            case LEGGINGS -> skill = Te.s("基础技能 - 2", ChatFormatting.AQUA);
            case BOOTS -> skill = Te.s("基础技能 - 3", ChatFormatting.AQUA);
            default -> skill = Te.s("");
        }
        components.add(Te.s(" ", countName, "会为", skill, "至多提供",
                String.format("%.0f%%", maxRate * 100) + "增幅", style));
        components.add(Te.s(" ", countName, "至多叠加至", "1000层", style));
        return components;
    }

    static double getCommonEnhanceRate(Player player, double maxRate) {
        return DivineUtils.getHolyLightCount(player) / 1000.0 * maxRate;
    }
}
