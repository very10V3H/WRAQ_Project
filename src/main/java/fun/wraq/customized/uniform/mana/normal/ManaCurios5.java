package fun.wraq.customized.uniform.mana.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ManaCurios5 extends WraqManaUniformCurios {

    public ManaCurios5(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Te.s("法力无边", hoverMainStyle()));
        components.add(Te.s(" 使法术技能的", "最大范围/最大距离 + 50%", style));
        components.add(Te.s(" 基于与目标的距离，提供至多", "75%基础伤害增幅", style));
        components.add(Te.s(" 在距离目标32格时有最大加成", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s(" 法术的研究者，也是亚瑟王的挚友和导师——梅林，给予新生法师的礼物。", style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("未卜先知").withStyle(hoverMainStyle());
    }
    
    public static boolean isOn(Player player) {
        return Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(UniformItems.MANA_CURIOS_5.get());
    }
    
    public static double getExSkillRangeRate(Player player) {
        return isOn(player) ? 0.5 : 0;
    }
    
    public static double getExBaseDamageRate(Player player, Mob mob) {
        if (isOn(player)) {
            return Math.min(0.75, player.distanceTo(mob) / 32 * 0.75);
        }
        return 0;
    }
}
