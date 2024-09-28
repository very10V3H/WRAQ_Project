package fun.wraq.process.system.missions.netWorking;

import fun.wraq.process.system.missions.MissionClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MissionStatusS2CPacket {

    private final String status;

    public MissionStatusS2CPacket(String status) {
        this.status = status;
    }

    public MissionStatusS2CPacket(FriendlyByteBuf buf) {
        this.status = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(status);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MissionClient.missionStatus = status;
        });
        return true;
    }

}

