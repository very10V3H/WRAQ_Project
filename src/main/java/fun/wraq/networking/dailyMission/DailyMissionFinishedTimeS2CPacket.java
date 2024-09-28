package fun.wraq.networking.dailyMission;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DailyMissionFinishedTimeS2CPacket {

    private final String Date;

    public DailyMissionFinishedTimeS2CPacket(String date) {
        this.Date = date;
    }

    public DailyMissionFinishedTimeS2CPacket(FriendlyByteBuf buf) {
        this.Date = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.Date);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.lastDailyMissionFinishedTime = this.Date;
        });
        return true;
    }
}
