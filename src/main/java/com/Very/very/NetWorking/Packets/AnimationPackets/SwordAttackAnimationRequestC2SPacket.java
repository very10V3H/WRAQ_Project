package com.Very.very.NetWorking.Packets.AnimationPackets;

import com.Very.very.NetWorking.ModNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class SwordAttackAnimationRequestC2SPacket {
    private final int PlayerID;
    public SwordAttackAnimationRequestC2SPacket(int playerID)
    {
        this.PlayerID = playerID;
    }
    public SwordAttackAnimationRequestC2SPacket(FriendlyByteBuf buf)
    {
        this.PlayerID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.PlayerID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer serverPlayer = context.getSender();
            List<ServerPlayer> playerList = serverPlayer.getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer1 : playerList) {
                ModNetworking.sendToClient(new SwordAttackAnimationS2CPacket(PlayerID),serverPlayer1);
            }

        });
        return true;
    }
}
