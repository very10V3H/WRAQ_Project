package fun.wraq.customized.uniform.mana;

import fun.wraq.common.util.Utils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class WraqManaUniformCurios extends WraqUniformCurios {

    public static final List<Item> MANA_CURIOS = new ArrayList<>();

    public WraqManaUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelManaDamage.put(this, 4d);
        Utils.xpLevelManaPenetration0.put(this, 0.1d);
        Utils.manaRecover.put(this, 20d);
        Utils.maxMana.put(this, 80d);
        Utils.coolDownDecrease.put(this, 0.1);
        MANA_CURIOS.add(this);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMana;
    }

}
