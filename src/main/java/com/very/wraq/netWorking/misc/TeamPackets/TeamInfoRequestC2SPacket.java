package com.very.wraq.netWorking.misc.TeamPackets;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.valueAndTools.Utils.Struct.PlayerTeam;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class TeamInfoRequestC2SPacket {
    public TeamInfoRequestC2SPacket()
    {

    }
    public TeamInfoRequestC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork( ()->{
            Utils.playerTeamMap.keySet().forEach(player -> {
                List<Player> playerList = Utils.playerTeamMap.get(player).getPlayerList();
                String[] playerName = new String[8];
                Component[] playerDisplayName = new Component[8];

                for (int i = 0; i < playerList.size(); i ++) {
                    playerName[i] = playerList.get(i).getName().getString();
                    playerDisplayName[i] = playerList.get(i).getDisplayName();
                }
                for (int i = playerList.size(); i < 8; i ++) {
                    playerName[i] = "";
                    playerDisplayName[i] = Component.literal("");
                }
                ModNetworking.sendToClient(new TeamInfoS2CPacket(Utils.playerTeamMap.get(player).getTeamName(),
                        playerName[0],playerName[1], playerName[2],playerName[3],
                        playerName[4],playerName[5], playerName[6],playerName[7],
                        playerDisplayName[0],playerDisplayName[1],playerDisplayName[2],playerDisplayName[3],
                        playerDisplayName[4],playerDisplayName[5],playerDisplayName[6],playerDisplayName[7],
                        playerList.size()),serverPlayer);

            });

            serverPlayer.getServer().getPlayerList().getPlayers().forEach(serverPlayer1 -> {
                ModNetworking.sendToClient(new PlayerInfoS2CPacket(serverPlayer1.getName().getString(),
                        serverPlayer1.getDisplayName()),serverPlayer);
            });

            Utils.TeamInvitePlayerMap.keySet().forEach(player -> {
                List<PlayerTeam> playerTeamList = Utils.TeamInvitePlayerMap.get(player);
                playerTeamList.forEach(playerTeam -> {
                    ModNetworking.sendToClient(new TeamInviteListS2CPacket(playerTeam.getTeamLeader().getName().getString(),
                            playerTeam.getTeamLeader().getDisplayName()),(ServerPlayer) player);
                });
            });

            Utils.PlayerRequestTeamMap.keySet().forEach(player -> {
                List<PlayerTeam> playerTeamList = Utils.PlayerRequestTeamMap.get(player);
                playerTeamList.forEach(playerTeam -> {
                    ModNetworking.sendToClient(new PlayerRequestListS2CPacket(player.getName().getString(),player.getDisplayName())
                            ,(ServerPlayer) playerTeam.getTeamLeader());
                });
            });

        });
        return true;
    }
}
