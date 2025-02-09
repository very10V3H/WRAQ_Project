package fun.wraq.process.system.missions.netWorking;

import fun.wraq.process.system.missions.mission2.MissionV2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MissionSubmitC2SPacket {

    private final int ordinal;

    public MissionSubmitC2SPacket(int ordinal) {
        this.ordinal = ordinal;
    }

    public MissionSubmitC2SPacket(FriendlyByteBuf buf) {
        this.ordinal = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(ordinal);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = supplier.get().getSender();
            if (player == null) return;
            MissionV2.onPlayerTryToSubmit(player, ordinal);
            MissionV2.sendDataToClient(player);
        });
        return true;
    }

}

