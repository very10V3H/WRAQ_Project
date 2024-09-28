package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.PlayerTeam;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.networking.misc.TeamPackets.TeamInfoResetS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TeamConfirmC2SPacket {

    private final String playerName;

    public TeamConfirmC2SPacket(String playerName) {
        this.playerName = playerName;
    }

    public TeamConfirmC2SPacket(FriendlyByteBuf buf) {
        this.playerName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.playerName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer teamLeader = context.getSender();
        context.enqueueWork(() -> {

            ServerPlayer player = teamLeader.getServer().getPlayerList().getPlayerByName(playerName);
            if (player != null) {
                PlayerTeam playerTeam = Utils.playerTeamMap.get(teamLeader);
                if (!Compute.thisTeamIsChallenging(playerTeam)) {
                    if (!Utils.playerTeamMap.containsKey(player)) {
                        if (playerTeam.teamPlayerNum() < 8) {
                            if (!playerTeam.containPlayer(player)) {
                                Utils.TeamInvitePlayerMap.remove(player);
                                Utils.PlayerRequestTeamMap.remove(player);

                                Utils.playerTeamMap.get(teamLeader).addPlayer(player);

                                Utils.playerTeamMap.get(teamLeader).getPlayerList().forEach(player1 -> {
                                    Utils.playerTeamMap.put(player1, Utils.playerTeamMap.get(teamLeader));
                                });

                                Utils.playerTeamMap.put(player, Utils.playerTeamMap.get(teamLeader));

                                teamLeader.sendSystemMessage(Component.literal("").append(player.getDisplayName()).
                                        append(Component.literal("已加入您的队伍。")));
                                player.sendSystemMessage(Component.literal("您已加入").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal("" + Utils.playerTeamMap.get(teamLeader).getTeamName())));

                                player.server.getPlayerList().getPlayers().forEach(serverPlayer -> ModNetworking.sendToClient(
                                        new TeamInfoResetS2CPacket(), serverPlayer));

                                ModNetworking.sendToClient(new fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket(1), player);

                            } else teamLeader.sendSystemMessage(Component.literal("玩家已在队伍内。"));
                        } else teamLeader.sendSystemMessage(Component.literal("队伍成员已满。"));
                    } else {
                        if (!playerTeam.containPlayer(player))
                            teamLeader.sendSystemMessage(Component.literal("该玩家已在其他队伍内。"));
                    }
                    ModNetworking.sendToClient(new ScreenSetS2CPacket(2), teamLeader);
                } else {
                    teamLeader.sendSystemMessage(Component.literal("您的队伍正在挑战副本，请等待副本挑战结束后再同意入队申请。"));
                }
            }

        });
        return true;
    }
}
