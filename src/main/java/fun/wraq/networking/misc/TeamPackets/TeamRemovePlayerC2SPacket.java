package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.networking.misc.TeamPackets.TeamInfoResetS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TeamRemovePlayerC2SPacket {

    private final String name;

    public TeamRemovePlayerC2SPacket(String name) {
        this.name = name;
    }

    public TeamRemovePlayerC2SPacket(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.name);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer teamLeader = context.getSender();
        context.enqueueWork(() -> {

            ServerPlayer removePlayer = teamLeader.getServer().getPlayerList().getPlayerByName(name);
            if (removePlayer != null) {
                if (Compute.thisTeamIsChallenging(Utils.playerTeamMap.get(teamLeader))) {
                    removePlayer.sendSystemMessage(Component.literal("请等待副本挑战完成后再尝试请出玩家。"));
                } else {
                    String teamName = Utils.playerTeamMap.get(removePlayer).getTeamName();
                    Utils.playerTeamMap.get(teamLeader).removePlayer(removePlayer);
                    Utils.playerTeamMap.remove(removePlayer);

                    Utils.playerTeamMap.get(teamLeader).getPlayerList().forEach(player -> {
                        Utils.playerTeamMap.put(player, Utils.playerTeamMap.get(teamLeader));
                        player.sendSystemMessage(Component.literal("").
                                append(removePlayer.getDisplayName()).
                                append(Component.literal("离开了队伍。")));
                    });

                    teamLeader.sendSystemMessage(Component.literal("").append(removePlayer.getDisplayName()).
                            append(Component.literal("离开了你的队伍。").withStyle(ChatFormatting.WHITE)));
                    removePlayer.sendSystemMessage(Component.literal("您已退出").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("" + teamName)));

                    removePlayer.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> ModNetworking.sendToClient(
                            new TeamInfoResetS2CPacket(), serverPlayer));

                    ModNetworking.sendToClient(new ScreenSetS2CPacket(0), removePlayer);
                }
            }
        });
        return true;
    }
}
