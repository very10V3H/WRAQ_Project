package fun.wraq.series;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface TickItem {
    void handleTick(Player player, ItemStack stack);
}
