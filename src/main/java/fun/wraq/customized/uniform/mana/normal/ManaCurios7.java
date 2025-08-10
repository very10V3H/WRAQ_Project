package fun.wraq.customized.uniform.mana.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.damage.ModifySkillDamageRateCurios;
import fun.wraq.common.impl.onhit.OnHitEffectCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManaCurios7 extends WraqManaUniformCurios implements OnHitEffectCurios,
        ModifySkillDamageRateCurios {

    public ManaCurios7(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Te.s("绝对专注", hoverMainStyle()));
        Component countName = ComponentUtils.getRightAngleQuote("专注", hoverMainStyle());
        components.add(Te.s(" 对一个目标造成伤害后，获得1层", countName, "."));
        components.add(Te.s(" 在造成伤害后的0.5s内将不会再次获得"));
        components.add(Te.s(" 每层", countName, "提供", "1%技能伤害增幅", CustomStyle.DIVINE_STYLE, "."));
        components.add(Te.s(" 对另一名目标造成伤害后，将重置已有层数."));
        components.add(Component.literal(" 绝对的战斗专注，是法师永存于战场的根基.").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("鬼道").withStyle(hoverMainStyle());
    }

    public static Map<String, Integer> countMap = new HashMap<>();

    public static Map<String, Integer> nextAllowGetCountTickMap = new HashMap<>();

    public static Map<String, Mob> targetMap = new HashMap<>();

    @Override
    public double modifySkillDamageRate(Player player) {
        return countMap.getOrDefault(Name.get(player), 0) * 0.01;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        if (targetMap.containsKey(Name.get(player)) && !targetMap.get(Name.get(player)).equals(mob)) {
            countMap.put(Name.get(player), 0);
            Compute.sendEffectLastTime(player, this,
                    countMap.getOrDefault(Name.get(player), 0), true);
        }
        if (Tick.get() >= nextAllowGetCountTickMap.getOrDefault(Name.get(player), 0)) {
            targetMap.put(Name.get(player), mob);
            nextAllowGetCountTickMap.put(Name.get(player), Tick.get() + 10);
            countMap.compute(Name.get(player), (k, v) -> v == null ? 1 : v + 1);
            Compute.sendEffectLastTime(player, this,
                    countMap.getOrDefault(Name.get(player), 0), true);
        }
    }
}
