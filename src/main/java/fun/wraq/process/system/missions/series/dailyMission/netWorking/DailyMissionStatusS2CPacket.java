package fun.wraq.process.system.missions.series.dailyMission.netWorking;

import fun.wraq.process.system.missions.Mission;
import fun.wraq.process.system.missions.series.dailyMission.DailyMission;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DailyMissionStatusS2CPacket {

    private final int[] killCount;
    private final int[] instanceCount;

    public DailyMissionStatusS2CPacket(int[] killCount, int[] instanceCount) {
        this.killCount = killCount;
        this.instanceCount = instanceCount;
    }

    public DailyMissionStatusS2CPacket(FriendlyByteBuf buf) {
        this.killCount = buf.readVarIntArray();
        this.instanceCount = buf.readVarIntArray();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(killCount);
        buf.writeVarIntArray(instanceCount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DailyMission.clientKillCount = killCount;
            DailyMission.clientInstanceCount = instanceCount;
            Mission.setDetailContent();
        });
        return true;
    }

}

