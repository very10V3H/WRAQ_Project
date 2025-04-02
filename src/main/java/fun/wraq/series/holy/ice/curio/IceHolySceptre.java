package fun.wraq.series.holy.ice.curio;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IceHolySceptre extends IceHolyCurio {

    public final int tier;
    public static final double[] rate = new double[]{0.02, 0.04, 0.06, 0.1};

    public IceHolySceptre(int tier) {
        super(tier);
        this.tier = tier;
        Utils.commonDamageEnhance.put(this, rate[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s(" 基于", Element.Description.IceElementValue("")));
        components.add(Te.s(" 为你提供至多",
                String.format("%.0f%%", rate[tier] * 100) + "技能伤害倍率", CustomStyle.styleOfPower));
        components.add(Te.s(" 当拥有", Element.Description.IceElementValue("1000%"), "时达最大值."));
        return components;
    }

    public static double getExSkillDamageRate(Player player) {
        List<IceHolySceptre> list = Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof IceHolySceptre)
                .map(stack -> (IceHolySceptre) stack.getItem())
                .toList();
        double elementStrength = Math.min(10, ElementValue.getPlayerIceElementValue(player));
        if (!list.isEmpty()) {
            IceHolySceptre holySceptre = list.stream().findAny().orElse(null);
            return rate[holySceptre.tier] * elementStrength / 10;
        }
        return 0;
    }
}
