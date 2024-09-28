package fun.wraq.process.system.randomStore.networking;

import fun.wraq.process.system.randomStore.RandomStore;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TradeListS2CPacket {

    private final ItemStack targetItem;
    private final ItemStack needItem;

    public TradeListS2CPacket(ItemStack targetItem, ItemStack needItem) {
        this.targetItem = targetItem;
        this.needItem = needItem;
    }

    public TradeListS2CPacket(FriendlyByteBuf buf) {
        this.targetItem = buf.readItem();
        this.needItem = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(targetItem);
        buf.writeItem(needItem);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (!RandomStore.clientTradeList.containsKey(targetItem))
                RandomStore.clientTradeList.put(targetItem, new ArrayList<>());
            List<ItemStack> needList = RandomStore.clientTradeList.get(targetItem);
            needList.add(needItem);
        });
        return true;
    }
}
