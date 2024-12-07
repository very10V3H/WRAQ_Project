package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.PlayerTeam;
import fun.wraq.networking.ModNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TeamInfoRequestC2SPacket {
    public TeamInfoRequestC2SPacket() {

    }

    public TeamInfoRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork(() -> {
            module(serverPlayer);
        });
        return true;
    }

    public static void module(ServerPlayer serverPlayer) {
        Utils.playerTeamMap.keySet().forEach(player -> {
            List<Player> playerList = Utils.playerTeamMap.get(player).getPlayerList();
            ModNetworking.sendToClient(new TeamInfoS2CPacket(Utils.playerTeamMap.get(player).getTeamName(),
                    playerList.stream().map(eachPlayer -> eachPlayer.getName().getString()).toList(),
                    playerList.stream().map(Player::getDisplayName).toList()
                    ,playerList.size()), serverPlayer);

        });

        serverPlayer.getServer().getPlayerList().getPlayers().forEach(serverPlayer1 -> {
            ModNetworking.sendToClient(new PlayerInfoS2CPacket(serverPlayer1.getName().getString(),
                    serverPlayer1.getDisplayName()), serverPlayer);
        });

        if (Utils.TeamInvitePlayerMap.containsKey(serverPlayer)) {
            List<PlayerTeam> playerTeamList = Utils.TeamInvitePlayerMap.get(serverPlayer);
            Map<String, Component> invitedList = new HashMap<>();
            playerTeamList.forEach(playerTeam -> {
                invitedList.put(playerTeam.getTeamLeader().getName().getString(),
                        playerTeam.getTeamLeader().getDisplayName());
            });
            ModNetworking.sendToClient(new TeamInviteListS2CPacket(invitedList), serverPlayer);
        }

        if (Utils.PlayerRequestTeamMap.containsKey(serverPlayer)) {
            List<PlayerTeam> playerTeamList = Utils.PlayerRequestTeamMap.get(serverPlayer);
            playerTeamList.forEach(playerTeam -> {
                ModNetworking.sendToClient(new PlayerRequestListS2CPacket(serverPlayer.getName().getString(),
                                serverPlayer.getDisplayName())
                        , (ServerPlayer) playerTeam.getTeamLeader());
            });
        }
    }
}
