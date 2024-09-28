package fun.wraq.networking.misc.TeamPackets;

import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.PlayerTeam;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.TeamInviteRequestS2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class TeamInviteC2SPacket {

    private final String name;

    public TeamInviteC2SPacket(String name) {
        this.name = name;
    }

    public TeamInviteC2SPacket(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.name);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer teamLeader = context.getSender();
        context.enqueueWork(() -> {

            PlayerTeam playerTeam = Utils.playerTeamMap.get(teamLeader);
            ServerPlayer player = teamLeader.getServer().getPlayerList().getPlayerByName(name);
            if (player != null) {
                if (!Utils.TeamInvitePlayerMap.containsKey(player) || Utils.TeamInvitePlayerMap.containsKey(player)
                        && !Utils.TeamInvitePlayerMap.get(player).contains(playerTeam)) {
                    ModNetworking.sendToClient(new TeamInviteRequestS2CPacket(teamLeader.getName().getString(), teamLeader.getDisplayName()), player);
                    teamLeader.sendSystemMessage(Component.literal("邀请已发送！"));
                    player.sendSystemMessage(Component.literal("您收到一个新的队伍邀请。"));
                    if (!Utils.TeamInvitePlayerMap.containsKey(player))
                        Utils.TeamInvitePlayerMap.put(player, new ArrayList<>() {{
                            add(playerTeam);
                        }});
                    else Utils.TeamInvitePlayerMap.get(player).add(playerTeam);
                } else teamLeader.sendSystemMessage(Component.literal("请勿重复邀请。"));
            }
        });
        return true;
    }
}
