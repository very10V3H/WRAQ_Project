package com.very.wraq.netWorking.misc.AnimationPackets;

import com.very.wraq.netWorking.ModNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class AttackAnimationRequestC2SPacket {
    private final int count;
    public AttackAnimationRequestC2SPacket(int count)
    {
        this.count = count;
    }
    public AttackAnimationRequestC2SPacket(FriendlyByteBuf buf)
    {
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer serverPlayer = context.getSender();
            List<ServerPlayer> playerList = serverPlayer.getServer().getPlayerList().getPlayers();
            playerList.forEach(serverPlayer1 -> {
                ModNetworking.sendToClient(new AttackAnimationS2CPacket(serverPlayer.getId(),count),serverPlayer1);
            });
        });
        return true;
    }
}
