package fun.wraq.networking.misc.AnimationPackets;

import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.AnimationPackets.AnimationTickResetS2CPacket;
import fun.wraq.networking.misc.AnimationPackets.UseAnimationS2CPacket;
import fun.wraq.process.func.SpecialEffectOnPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class UseAnimationRequestC2SPacket {
    private final int count;

    public UseAnimationRequestC2SPacket(int count) {
        this.count = count;
    }

    public UseAnimationRequestC2SPacket(FriendlyByteBuf buf) {
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (SpecialEffectOnPlayer.inVertigo(serverPlayer)) {
                ModNetworking.sendToClient(new AnimationTickResetS2CPacket(), serverPlayer);
            } else {
                List<ServerPlayer> playerList = serverPlayer.getServer().getPlayerList().getPlayers();
                playerList.forEach(serverPlayer1 -> {
                    ModNetworking.sendToClient(new UseAnimationS2CPacket(serverPlayer.getId(), count), serverPlayer1);
                });
            }
        });
        return true;
    }
}