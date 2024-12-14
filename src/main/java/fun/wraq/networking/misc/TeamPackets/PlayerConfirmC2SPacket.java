package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.PlayerTeam;
import fun.wraq.networking.ModNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerConfirmC2SPacket {

    private final String playerName;

    public PlayerConfirmC2SPacket(String playerName) {
        this.playerName = playerName;
    }

    public PlayerConfirmC2SPacket(FriendlyByteBuf buf) {
        this.playerName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.playerName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork(() -> {
            ServerPlayer teamLeader = player.getServer().getPlayerList().getPlayerByName(playerName);
            if (teamLeader == null) {
                player.sendSystemMessage(Te.s("队伍不存在"));
                return;
            }
            PlayerTeam playerTeam = Utils.playerTeamMap.get(teamLeader);
            Utils.TeamInvitePlayerMap.get(player).remove(playerTeam);
            if (Compute.thisTeamIsChallenging(playerTeam)) {
                player.sendSystemMessage(Component.literal("该队伍正在挑战副本，请等待副本挑战结束后再尝试加入。"));
                return;
            }
            if (playerTeam.teamPlayerNum() >= 8) {
                player.sendSystemMessage(Component.literal("队伍成员已满。"));
                return;
            }
            if (playerTeam.containPlayer(player)) {
                player.sendSystemMessage(Component.literal("你已经在该队伍了。"));
                return;
            }
            Utils.TeamInvitePlayerMap.remove(player);
            Utils.PlayerRequestTeamMap.remove(player);
            Utils.playerTeamMap.get(teamLeader).addPlayer(player);
            Utils.playerTeamMap.get(teamLeader).getPlayerList().forEach(player1 -> {
                Utils.playerTeamMap.put(player1, Utils.playerTeamMap.get(teamLeader));
            });
            teamLeader.sendSystemMessage(Component.literal("").append(player.getDisplayName()).
                    append(Component.literal("加入了您的队伍。")));
            player.sendSystemMessage(Component.literal("您加入了").withStyle(ChatFormatting.WHITE).
                    append(Utils.playerTeamMap.get(teamLeader).getTeamName()));
            player.server.getPlayerList().getPlayers().forEach(serverPlayer -> ModNetworking.sendToClient(
                    new TeamInfoResetS2CPacket(), serverPlayer));
            ModNetworking.sendToClient(new ScreenSetS2CPacket(1), player);
        });
        return true;
    }
}
