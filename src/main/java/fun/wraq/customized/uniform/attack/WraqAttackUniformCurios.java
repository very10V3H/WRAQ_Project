package fun.wraq.customized.uniform.attack;

import fun.wraq.common.util.Utils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class WraqAttackUniformCurios extends WraqUniformCurios {

    public final static List<Item> ATTACK_CURIOS = new ArrayList<>();

    public WraqAttackUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, 2d);
        Utils.xpLevelDefencePenetration0.put(this, 0.1d);
        Utils.critDamage.put(this, 0.08);
        ATTACK_CURIOS.add(this);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPower;
    }

}
