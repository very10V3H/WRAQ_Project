package fun.wraq.customized.uniform.mana.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.mixture.WraqMixture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManaCurios4 extends WraqManaUniformCurios {

    public ManaCurios4(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Te.s("诡谲多变", hoverMainStyle()));
        Component countName = ComponentUtils.getRightAngleQuote("谲", style);
        components.add(Te.s(" 对怪物造成", "任意伤害", CustomStyle.styleOfStone, "时获得一层", countName));
        components.add(Te.s(" ", countName, "持续5s，最多叠加至100层，至多提供:"));
        components.add(Te.s(" 1.", hoverMainStyle(),
                ComponentUtils.AttributeDescription.exManaDamage("40% + 800")));
        components.add(Te.s(" 2.", hoverMainStyle(),
                ComponentUtils.AttributeDescription.manaPenetration("15% + 60")));
        components.add(Te.s(" 满层时，普攻将额外释放一枚", "法球", style));
        components.add(Te.s(" 法术的研究者，也是亚瑟王的挚友和导师——梅林，给予新生法师的礼物。", style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("智冠群伦").withStyle(hoverMainStyle());
    }

    public static boolean isOn(Player player) {
        return Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(UniformItems.MANA_CURIOS_4.get());
    }

    public static boolean isExpired(Player player) {
        return countExpiredMap.getOrDefault(Name.get(player), 0) < Tick.get();
    }

    public static Map<String, Integer> countMap = new HashMap<>();
    public static Map<String, Integer> countExpiredMap = new HashMap<>();

    public static void onCauseDamage(Player player) {
        if (isOn(player)) {
            String name = Name.get(player);
            if (countExpiredMap.getOrDefault(name, 0) < Tick.get()) {
                countMap.put(name, 0);
            }
            countMap.compute(name, (k, v) -> v == null ? 1 : Math.min(100, v + 1));
            countExpiredMap.put(name, Tick.get() + Tick.s(5));
            Compute.sendEffectLastTime(player, UniformItems.MANA_CURIOS_4.get(), 
                    Tick.s(5), countMap.get(name), false);
        }
    }

    public static double getExManaDamageRate(Player player) {
        if (isOn(player) && !isExpired(player)) {
            return countMap.getOrDefault(Name.get(player), 0) * 1.0 / 100 * 0.4;
        }
        return 0;
    }

    public static double getExManaDamage(Player player) {
        if (isOn(player) && !isExpired(player)) {
            return countMap.getOrDefault(Name.get(player), 0) * 8;
        }
        return 0;
    }

    public static double getExManaPenetrationRate(Player player) {
        if (isOn(player) && !isExpired(player)) {
            return countMap.getOrDefault(Name.get(player), 0) * 1.0 / 100 * 0.15;
        }
        return 0;
    }

    public static double getExManaPenetration0(Player player) {
        if (isOn(player) && !isExpired(player)) {
            return countMap.getOrDefault(Name.get(player), 0) * 0.6;
        }
        return 0;
    }

    public static void onShoot(Player player) {
        if (isOn(player)) {
            if (countMap.getOrDefault(Name.get(player), 0) >= 100) {
                WraqMixture.addExShoot(player, 1);
            }
        }
    }
}
