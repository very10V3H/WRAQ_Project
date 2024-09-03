package com.very.wraq.networking.misc.TeamPackets;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerRequestListS2CPacket {

    private final String playerName;
    private final Component playerDisplayName;

    public PlayerRequestListS2CPacket(String playerName, Component playerDisplayName) {
        this.playerName = playerName;
        this.playerDisplayName = playerDisplayName;
    }

    public PlayerRequestListS2CPacket(FriendlyByteBuf buf) {
        this.playerName = buf.readUtf();
        this.playerDisplayName = buf.readComponent();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.playerName);
        buf.writeComponent(this.playerDisplayName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.TeamPlayerRequestList.put(playerName, playerDisplayName);
        });
        return true;
    }
}
