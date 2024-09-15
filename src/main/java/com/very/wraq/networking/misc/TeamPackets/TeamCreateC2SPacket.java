package com.very.wraq.networking.misc.TeamPackets;

import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.PlayerTeam;
import com.very.wraq.networking.ModNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TeamCreateC2SPacket {
    public TeamCreateC2SPacket() {

    }

    public TeamCreateC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        Player player = context.getSender();
        context.enqueueWork(() -> {

            List<Player> playerList = new ArrayList<>() {{
                add(player);
            }};

            PlayerTeam playerTeam = new PlayerTeam(playerList, player.getName().getString() + "的队伍");

            Utils.playerTeamMap.put(player, playerTeam);

            player.sendSystemMessage(Component.literal("队伍已创建"));

            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> ModNetworking.sendToClient(
                    new TeamInfoResetS2CPacket(), serverPlayer));
        });
        return true;
    }
}
