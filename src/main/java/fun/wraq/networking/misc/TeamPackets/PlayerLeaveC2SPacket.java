package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.TeamInfoResetS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerLeaveC2SPacket {
    public PlayerLeaveC2SPacket() {

    }

    public PlayerLeaveC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork(() -> {

            ServerPlayer removePlayer = player;
            Player teamLeader = Utils.playerTeamMap.get(removePlayer).getTeamLeader();
            String teamName = Utils.playerTeamMap.get(removePlayer).getTeamName();

            if (Compute.thisTeamIsChallenging(Utils.playerTeamMap.get(removePlayer))) {
                removePlayer.sendSystemMessage(Component.literal("请等待副本挑战完成后再尝试退出。"));
            } else {
                Utils.playerTeamMap.get(removePlayer).removePlayer(removePlayer);

                Utils.playerTeamMap.remove(removePlayer);

                Utils.playerTeamMap.get(teamLeader).getPlayerList().forEach(player1 -> {
                    Utils.playerTeamMap.put(player1, Utils.playerTeamMap.get(teamLeader));
                    player1.sendSystemMessage(Component.literal("").
                            append(removePlayer.getDisplayName()).
                            append(Component.literal("离开了队伍。")));
                });

                removePlayer.sendSystemMessage(Component.literal("您已退出").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("" + teamName)));

                player.server.getPlayerList().getPlayers().forEach(serverPlayer -> ModNetworking.sendToClient(
                        new TeamInfoResetS2CPacket(), serverPlayer));
            }
        });
        return true;
    }
}
