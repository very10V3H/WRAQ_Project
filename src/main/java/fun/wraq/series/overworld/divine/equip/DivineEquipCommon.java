package fun.wraq.series.overworld.divine.equip;

import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.impl.onkill.OnKillEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface DivineEquipCommon extends OnKillEffectEquip, InCuriosOrEquipSlotAttributesModify {

    Style style = CustomStyle.DIVINE_STYLE;
    static List<Component> getCommonDescription(ItemStack stack, double upperLimitRate, int maxCount) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("圣光", style));
        components.add(Te.s(" 将除", "当前共鸣", ChatFormatting.AQUA, "以外的元素", "以", "20%", style, "的效率"));
        components.add(Te.s(" 转化为", "当前元素", ChatFormatting.AQUA, "的", "归一化元素强度", style));
        ComponentUtils.descriptionPassive(components, Te.s("圣光", style));
        components.add(Te.s(" 击杀怪物将会受", "圣光恩赐", style));
        components.add(Te.s(" 圣光恩赐", style, "将存储在这件物品内"));
        components.add(Te.s(" 恩赐", style, "至多为你提供:"));
        components.add(Te.s(" 1.", style,
                ComponentUtils.AttributeDescription.getElementStrength(String.format("%.0f%%", upperLimitRate * 100))));
        components.add(Te.s(" 2.", style,
                ComponentUtils.AttributeDescription.attackDamage(String.format("%.0f%%", upperLimitRate * 100))));
        components.add(Te.s(" 圣光充盈度:", style));
        components.add(Te.s(" ".repeat(4),
                ComponentUtils.getProgressBar(20, getDivineCount(stack), maxCount, style)));
        return components;
    }

    String DIVINE_COUNT_DATA_KEY = "DivineCount";
    static int getDivineCount(ItemStack stack) {
        return stack.getOrCreateTagElement(Utils.MOD_ID).getInt(DIVINE_COUNT_DATA_KEY);
    }

    static void setDivineCount(ItemStack stack, int divineCount) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putInt(DIVINE_COUNT_DATA_KEY, divineCount);
    }

    static void addDivineCount(ItemStack stack) {
        setDivineCount(stack, getDivineCount(stack) + 1);
    }

    static double getElementExceptOneElementValue(Player player, String exceptElement) {
        double value = 0;
        for (String s : Element.elementList) {
            if (!s.equals(exceptElement)) {
                value += ElementValue.getElementValueJudgeByType(player, s);
            }
        }
        return value;
    }
}
