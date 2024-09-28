package fun.wraq.networking.misc.AnimationPackets;

import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.AnimationPackets.PickAxeAttackAnimationS2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class PickAxeAttackAnimationRequestC2SPacket {
    private final int PlayerID;

    public PickAxeAttackAnimationRequestC2SPacket(int playerID) {
        this.PlayerID = playerID;
    }

    public PickAxeAttackAnimationRequestC2SPacket(FriendlyByteBuf buf) {
        this.PlayerID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.PlayerID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            List<ServerPlayer> playerList = serverPlayer.getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer1 : playerList) {
                ModNetworking.sendToClient(new PickAxeAttackAnimationS2CPacket(PlayerID), serverPlayer1);
            }

        });
        return true;
    }
}
