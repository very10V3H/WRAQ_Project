package fun.wraq.process.system.missions.netWorking;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.missions.Mission;
import fun.wraq.process.system.missions.netWorking.MissionStatusS2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MissionScreenOpenC2SPacket {

    private final int type;

    public MissionScreenOpenC2SPacket(int status) {
        this.type = status;
    }

    public MissionScreenOpenC2SPacket(FriendlyByteBuf buf) {
        this.type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ModNetworking.sendToClient(new MissionStatusS2CPacket(Mission.getPlayerMissionStatusString(context.getSender())), context.getSender());
            ModNetworking.sendToClient(new MissionScreenOpenS2CPacket(type), context.getSender());
        });
        return true;
    }

}

