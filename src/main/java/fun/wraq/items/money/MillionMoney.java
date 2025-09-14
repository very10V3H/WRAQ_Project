package fun.wraq.items.money;

import fun.wraq.common.Compute;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.series.WraqItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class MillionMoney extends WraqItem {
    public MillionMoney(Properties properties, List<Component> components) {
        super(properties, false, false, components);
    }

    @Override
    public void useOnServerSide(Player player) {
        InventoryOperation.removeItem(player, this, 1);
        Compute.VBIncomeAndMSGSend(player, 1000000);
    }
}
