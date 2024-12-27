package fun.wraq.process.system.entrustment.MobEntrustmentInfo;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MobKillEntrustmentInfoS2CPacket {

    private final Component mobName;
    private final int targetCount;
    private final int currentCount;
    private final int startServerTick;
    private final int reputationExpiredMin;
    private final int reputation;
    private final int dailyFinishedTime;
    private final int totalFinishedTime;
    public MobKillEntrustmentInfoS2CPacket(Component mobName, int targetCount, int currentCount, int startServerTick, 
                                           int reputationExpiredMin, int reputation,
                                           int dailyFinishedTime, int totalFinishedTime) {
        this.mobName = mobName;
        this.targetCount = targetCount;
        this.currentCount = currentCount;
        this.startServerTick = startServerTick;
        this.reputationExpiredMin = reputationExpiredMin;
        this.reputation = reputation;
        this.dailyFinishedTime = dailyFinishedTime;
        this.totalFinishedTime = totalFinishedTime;
    }

    public MobKillEntrustmentInfoS2CPacket(FriendlyByteBuf buf) {
        this.mobName = buf.readComponent();
        this.targetCount = buf.readInt();
        this.currentCount = buf.readInt();
        this.startServerTick = buf.readInt();
        this.reputationExpiredMin = buf.readInt();
        this.reputation = buf.readInt();
        this.dailyFinishedTime = buf.readInt();
        this.totalFinishedTime = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeComponent(mobName);
        buf.writeInt(targetCount);
        buf.writeInt(currentCount);
        buf.writeInt(startServerTick);
        buf.writeInt(reputationExpiredMin);
        buf.writeInt(reputation);
        buf.writeInt(dailyFinishedTime);
        buf.writeInt(totalFinishedTime);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MobKillEntrustmentHud.mobName = mobName;
            MobKillEntrustmentHud.targetCount = targetCount;
            MobKillEntrustmentHud.currentCount = currentCount;
            MobKillEntrustmentHud.startServerTick = startServerTick;
            MobKillEntrustmentHud.reputationExpiredMin = reputationExpiredMin;
            MobKillEntrustmentHud.reputation = reputation;
            MobKillEntrustmentHud.dailyFinishedTimes = dailyFinishedTime;
            MobKillEntrustmentHud.totalFinishedTimes = totalFinishedTime;
        });
        return true;
    }
}
