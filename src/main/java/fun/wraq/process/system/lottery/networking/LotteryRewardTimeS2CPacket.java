package fun.wraq.process.system.lottery.networking;

import fun.wraq.process.system.lottery.NewLotteries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class LotteryRewardTimeS2CPacket {

    private final Map<String, Integer> rewardTimes;

    public LotteryRewardTimeS2CPacket(Map<String, Integer> rewardTimes) {
        this.rewardTimes = rewardTimes;
    }

    public LotteryRewardTimeS2CPacket(FriendlyByteBuf buf) {
        this.rewardTimes = buf.readMap(FriendlyByteBuf::readUtf, FriendlyByteBuf::readInt);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(this.rewardTimes, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeInt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            NewLotteries.clientRewardTimes.clear();
            NewLotteries.clientRewardTimes = rewardTimes;
        });
        return true;
    }
}
