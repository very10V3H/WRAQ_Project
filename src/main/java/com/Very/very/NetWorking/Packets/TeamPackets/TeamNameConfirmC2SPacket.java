package com.Very.very.NetWorking.Packets.TeamPackets;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class TeamNameConfirmC2SPacket {

    private final String teamName;
    public TeamNameConfirmC2SPacket(String teamName)
    {
        this.teamName = teamName;
    }
    public TeamNameConfirmC2SPacket(FriendlyByteBuf buf)
    {
        this.teamName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUtf(this.teamName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork( ()->{
            Utils.playerTeamMap.get(player).setTeamName(this.teamName);
            player.sendSystemMessage(Component.literal("队伍名已更改。"));

            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> ModNetworking.sendToClient(
                    new TeamInfoResetS2CPacket(),serverPlayer));
        });
        return true;
    }
}
