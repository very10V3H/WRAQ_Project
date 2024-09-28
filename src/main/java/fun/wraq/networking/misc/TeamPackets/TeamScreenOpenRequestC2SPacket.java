package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.TeamScreenOpenS2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TeamScreenOpenRequestC2SPacket {
    public TeamScreenOpenRequestC2SPacket() {

    }

    public TeamScreenOpenRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork(() -> {
            ModNetworking.sendToClient(new TeamScreenOpenS2CPacket(), serverPlayer);
        });
        return true;
    }
}
