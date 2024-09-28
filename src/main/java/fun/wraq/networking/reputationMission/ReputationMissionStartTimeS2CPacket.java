package fun.wraq.networking.reputationMission;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReputationMissionStartTimeS2CPacket {

    private final String Date;

    public ReputationMissionStartTimeS2CPacket(String date) {
        this.Date = date;
    }

    public ReputationMissionStartTimeS2CPacket(FriendlyByteBuf buf) {
        this.Date = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.Date);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.ReputationMissionStartTime = this.Date;
        });
        return true;
    }
}
