package com.very.wraq.netWorking.misc.TeamPackets;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Struct.PlayerTeam;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class TeamDeleteC2SPacket {
    public TeamDeleteC2SPacket()
    {

    }
    public TeamDeleteC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer teamLeader = context.getSender();
        context.enqueueWork( ()->{

            PlayerTeam playerTeam = Utils.playerTeamMap.get(teamLeader);

            if (Compute.thisTeamIsChallenging(Utils.playerTeamMap.get(teamLeader))) {
                teamLeader.sendSystemMessage(Component.literal("请等待副本挑战结束后再尝试解散队伍。"));
            }
            else {
                Utils.TeamInvitePlayerMap.keySet().forEach(player -> {
                    List<PlayerTeam> playerTeamList = Utils.TeamInvitePlayerMap.get(player);
                    playerTeamList.remove(playerTeam);
                });

                Utils.PlayerRequestTeamMap.keySet().forEach(player -> {
                    List<PlayerTeam> playerTeamList = Utils.PlayerRequestTeamMap.get(player);
                    playerTeamList.remove(playerTeam);
                });


                playerTeam.getPlayerList().forEach(player1 -> {
                    if (player1 != teamLeader) {
                        Utils.playerTeamMap.remove(player1);
                        player1.sendSystemMessage(Component.literal("您所在的队伍已解散。"));
                        ModNetworking.sendToClient(new ScreenSetS2CPacket(0),(ServerPlayer) player1);
                    }
                });

                Utils.playerTeamMap.remove(teamLeader);

                teamLeader.sendSystemMessage(Component.literal("队伍已解散。"));

                teamLeader.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> ModNetworking.sendToClient(
                        new TeamInfoResetS2CPacket(),serverPlayer));
            }

        });
        return true;
    }
}
