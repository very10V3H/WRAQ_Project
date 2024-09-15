package com.very.wraq.networking.misc.TeamPackets;

import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.PlayerTeam;
import com.very.wraq.networking.ModNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class PlayerRequestC2SPacket {

    private final String playerName;

    public PlayerRequestC2SPacket(String playerName) {
        this.playerName = playerName;
    }

    public PlayerRequestC2SPacket(FriendlyByteBuf buf) {
        this.playerName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.playerName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork(() -> {

            ServerPlayer teamLeader = serverPlayer.getServer().getPlayerList().getPlayerByName(playerName);
            if (teamLeader != null) {
                PlayerTeam playerTeam = Utils.playerTeamMap.get(teamLeader);

                if (!Utils.PlayerRequestTeamMap.containsKey(serverPlayer)
                        || Utils.PlayerRequestTeamMap.containsKey(serverPlayer)
                        && !Utils.PlayerRequestTeamMap.get(serverPlayer).contains(playerTeam)) {
                    ModNetworking.sendToClient(new PlayerRequestS2CPacket(serverPlayer.getName().getString(), serverPlayer.getDisplayName()), teamLeader);
                    serverPlayer.sendSystemMessage(Component.literal("申请已发送！"));
                    teamLeader.sendSystemMessage(Component.literal("您新收到一个待处理的入队申请。"));
                    if (!Utils.PlayerRequestTeamMap.containsKey(serverPlayer))
                        Utils.PlayerRequestTeamMap.put(serverPlayer, new ArrayList<>() {{
                            add(playerTeam);
                        }});
                    else Utils.PlayerRequestTeamMap.get(serverPlayer).add(playerTeam);
                } else serverPlayer.sendSystemMessage(Component.literal("您已经提交过申请了。"));


            }

        });
        return true;
    }
}
