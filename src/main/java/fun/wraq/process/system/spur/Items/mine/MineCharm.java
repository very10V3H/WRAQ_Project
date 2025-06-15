package fun.wraq.process.system.spur.Items.mine;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MineCharm extends WraqCurios {

    public MineCharm(Properties properties, int serial) {
        super(properties);
        int[] levelRequires = {40, 60, 80, 100, 120, 140, 160};
        double[] defencePenetration0 = {1, 2, 3, 4, 6, 9, 13};
        double[] manaPenetration0 = {1, 2, 3, 4, 6, 9, 13};
        Utils.levelRequire.put(this, levelRequires[serial]);
        Utils.defencePenetration.put(this, defencePenetration0[serial] * 0.01);
        Utils.defencePenetration0.put(this, defencePenetration0[serial]);
        Utils.manaPenetration.put(this, manaPenetration0[serial] * 0.01);
        Utils.manaPenetration0.put(this, manaPenetration0[serial]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMine;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSpurSuffix();
    }
}
