package fun.wraq.customized.uniform.mana.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onshoot.OnPowerReleaseCurios;
import fun.wraq.common.impl.onshoot.OnReleaseSkillCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManaCurioTaboo extends WraqManaUniformCurios implements OnPowerReleaseCurios, OnReleaseSkillCurios {

    public ManaCurioTaboo(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Component countName = ComponentUtils.getRightAngleQuote("禁忌", CustomStyle.styleOfDemon);
        ComponentUtils.descriptionPassive(components, Te.s("禁忌位面 - Unknown", hoverMainStyle()));
        components.add(Te.s(" 释放任意", "法术技能", hoverMainStyle(), "时，获得一层", countName));
        components.add(Te.s(" 当你拥有", "4", hoverMainStyle(), "层", countName, "后，你释放的下一个技能:"));
        components.add(Te.s(" 1.", hoverMainStyle(), "基础冷却时间 - 50%", ChatFormatting.AQUA));
        components.add(Te.s(" 2.", hoverMainStyle(), "无法力消耗", hoverMainStyle()));
        components.add(Te.s(" 3.", hoverMainStyle(), "伤害提升100%", CustomStyle.styleOfPower));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("禁忌秘术").withStyle(hoverMainStyle());
    }

    public static Map<String, Integer> passiveCountMap = new HashMap<>();

    @Override
    public void onPowerRelease(Player player) {
        passiveCountMap.compute(Name.get(player), (k, v) -> v == null ? 1 : v + 1);
        Compute.sendEffectLastTime(player, "item/mana_curios_0",
                passiveCountMap.get(Name.get(player)), true);
    }

    public static boolean isActive(Player player) {
        if (Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(UniformItems.MANA_CURIO_TABOO.get())) {
            return passiveCountMap.getOrDefault(Name.get(player), 0) >= 4;
        }
        return false;
    }

    @Override
    public void onReleaseSkill(Player player) {
        if (passiveCountMap.getOrDefault(Name.get(player), 0) > 4) {
            passiveCountMap.put(Name.get(player), 1);
            Compute.sendEffectLastTime(player, "item/mana_curios_0", 1, true);
        }
    }
}
