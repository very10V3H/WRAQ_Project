package fun.wraq.process.system.missions.netWorking;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.missions.mission2.MissionV2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
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
            Player player = context.getSender();
            MissionV2.sendDataToClient(player);
            int inProgressCount = MissionV2.getInProgressMissionCount(player);
            int notAcceptedCount = MissionV2.getNoAcceptedMissionCount(player);
            if (inProgressCount == 0) {
                if (notAcceptedCount == 0) {
                    ModNetworking.sendToClient(new MissionScreenOpenS2CPacket(3), player);
                } else {
                    ModNetworking.sendToClient(new MissionScreenOpenS2CPacket(1), player);
                }
            } else {
                ModNetworking.sendToClient(new MissionScreenOpenS2CPacket(2), player);
            }
        });
        return true;
    }

}

