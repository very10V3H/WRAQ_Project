package fun.wraq.series.events.spring2025;

import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.world.entity.player.Player;

public class Spring2025 {
    public static void dailyRedEnvelopeReward(Player player) {
        if (player.experienceLevel >= 75) {
            InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.RED_ENVELOPE.get(), 2);
        }
    }
}
