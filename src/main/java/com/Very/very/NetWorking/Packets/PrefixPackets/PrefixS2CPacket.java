package com.Very.very.NetWorking.Packets.PrefixPackets;

import com.Very.very.Items.Prefix.PrefixInfo;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PrefixS2CPacket {
    private final String playerName;
    private final String prefixName;
    private final int level;
    public PrefixS2CPacket(String playerName, String prefixName, int level)
    {
        this.playerName = playerName;
        this.prefixName = prefixName;
        this.level = level;
    }
    public PrefixS2CPacket(FriendlyByteBuf buf)
    {
        this.playerName = buf.readUtf();
        this.prefixName = buf.readUtf();
        this.level = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUtf(this.playerName);
        buf.writeUtf(this.prefixName);
        buf.writeInt(this.level);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            PrefixInfo prefixInfo = new PrefixInfo(this.prefixName,this.level);
            ClientUtils.clientPrefixInfoMap.put(this.playerName,prefixInfo);
        });
        return true;
    }
}
