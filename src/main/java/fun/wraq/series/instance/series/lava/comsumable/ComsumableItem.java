package fun.wraq.series.instance.series.lava.comsumable;

import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract  class ComsumableItem extends WraqItem implements ActiveItem {

    public ComsumableItem(Properties properties, List<Component> description) {
        super(properties, true, description);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 消耗品 ", CustomStyle.styleOfPower));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}
