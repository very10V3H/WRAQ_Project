package fun.wraq.process.system.lottery.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class LotteryRewardS2CPacket {

    private final List<ItemStack> rewards;

    public LotteryRewardS2CPacket(List<ItemStack> rewards) {
        this.rewards = rewards;
    }

    public LotteryRewardS2CPacket(FriendlyByteBuf buf) {
        this.rewards = buf.readList(FriendlyByteBuf::readItem);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(this.rewards, FriendlyByteBuf::writeItem);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

        });
        return true;
    }
}
