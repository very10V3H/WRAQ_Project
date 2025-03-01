package fun.wraq.series.overworld.sakura.bunker.crest;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RepeatableCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BunkerCrest extends WraqCurios implements RepeatableCurios {

    public BunkerCrest(Properties properties) {
        super(properties, 16);
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
        return CustomStyle.BUNKER_STYLE;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfBunker();
    }
}
