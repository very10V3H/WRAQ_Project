package fun.wraq.process.system.entrustment.mob;

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
    private final int weeklyFinishedTime;
    private final int totalFinishedTime;
    private final int averageTick;
    private final int nextAllowAcceptTick;
    public MobKillEntrustmentInfoS2CPacket(Component mobName, int targetCount, int currentCount, int startServerTick, 
                                           int reputationExpiredMin, int reputation,
                                           int dailyFinishedTime, int weeklyFinishedTime, int totalFinishedTime,
                                           int averageTick, int nextAllowAcceptTick) {
        this.mobName = mobName;
        this.targetCount = targetCount;
        this.currentCount = currentCount;
        this.startServerTick = startServerTick;
        this.reputationExpiredMin = reputationExpiredMin;
        this.reputation = reputation;
        this.dailyFinishedTime = dailyFinishedTime;
        this.weeklyFinishedTime = weeklyFinishedTime;
        this.totalFinishedTime = totalFinishedTime;
        this.averageTick = averageTick;
        this.nextAllowAcceptTick = nextAllowAcceptTick;
    }

    public MobKillEntrustmentInfoS2CPacket(FriendlyByteBuf buf) {
        this.mobName = buf.readComponent();
        this.targetCount = buf.readInt();
        this.currentCount = buf.readInt();
        this.startServerTick = buf.readInt();
        this.reputationExpiredMin = buf.readInt();
        this.reputation = buf.readInt();
        this.dailyFinishedTime = buf.readInt();
        this.weeklyFinishedTime = buf.readInt();
        this.totalFinishedTime = buf.readInt();
        this.averageTick = buf.readInt();
        this.nextAllowAcceptTick = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeComponent(mobName);
        buf.writeInt(targetCount);
        buf.writeInt(currentCount);
        buf.writeInt(startServerTick);
        buf.writeInt(reputationExpiredMin);
        buf.writeInt(reputation);
        buf.writeInt(dailyFinishedTime);
        buf.writeInt(weeklyFinishedTime);
        buf.writeInt(totalFinishedTime);
        buf.writeInt(averageTick);
        buf.writeInt(nextAllowAcceptTick);
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
            MobKillEntrustmentHud.weeklyFinishedTimes = weeklyFinishedTime;
            MobKillEntrustmentHud.totalFinishedTimes = totalFinishedTime;
            MobKillEntrustmentHud.averageTick = averageTick;
            MobKillEntrustmentHud.nextAllowAcceptTick = nextAllowAcceptTick;
        });
        return true;
    }
}
