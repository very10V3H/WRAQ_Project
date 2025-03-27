package fun.wraq.process.system.spur.Items;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LogCharm extends WraqCurios {

    public LogCharm(Properties properties, int serial) {
        super(properties);
        int[] levelRequires = {40, 60, 80, 100, 120, 140, 160};
        double[] defence = {2, 3, 4, 6, 8, 12, 15};
        double[] manaDefence = {2, 3, 4, 6, 8, 12, 15};
        Utils.levelRequire.put(this, levelRequires[serial]);
        Utils.defence.put(this, defence[serial]);
        Utils.percentDefenceEnhance.put(this, defence[serial] * 0.01);
        Utils.manaDefence.put(this, manaDefence[serial]);
        Utils.percentManaDefenceEnhance.put(this, manaDefence[serial] * 0.01);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfHusk;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSpurSuffix();
    }
}
