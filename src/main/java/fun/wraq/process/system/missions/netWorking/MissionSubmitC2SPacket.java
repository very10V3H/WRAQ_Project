package fun.wraq.process.system.missions.netWorking;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.missions.Mission;
import fun.wraq.process.system.missions.netWorking.MissionStatusS2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class MissionSubmitC2SPacket {

    private final int serialNum;

    public MissionSubmitC2SPacket(int serialNum) {
        this.serialNum = serialNum;
    }

    public MissionSubmitC2SPacket(FriendlyByteBuf buf) {
        this.serialNum = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(serialNum);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = supplier.get().getSender();
            if (player == null) return;

            try {
                Mission.playerTryToSubmit(player, serialNum);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ModNetworking.sendToClient(new MissionStatusS2CPacket(
                    Mission.getPlayerMissionStatusString(player)), player);

        });
        return true;
    }

}

