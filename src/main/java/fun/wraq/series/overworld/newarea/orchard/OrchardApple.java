package fun.wraq.series.overworld.newarea.orchard;

import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.series.WraqItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrchardApple extends WraqItem implements ActiveItem {

    public OrchardApple(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {

        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public void active(Player player) {

    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
