package com.Very.very.NetWorking.Packets.TeamPackets;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TeamInviteListS2CPacket {

    private final String playerName;
    private final Component playerDisplayName;
    public TeamInviteListS2CPacket(String playerName, Component playerDisplayName)
    {
        this.playerName = playerName;
        this.playerDisplayName = playerDisplayName;
    }
    public TeamInviteListS2CPacket(FriendlyByteBuf buf)
    {
        this.playerName = buf.readUtf();
        this.playerDisplayName = buf.readComponent();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUtf(this.playerName);
        buf.writeComponent(this.playerDisplayName);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{

            ClientUtils.TeamInviteRequestList.put(playerName,playerDisplayName);
            
        });
        return true;
    }
}
