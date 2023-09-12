package com.Very.very.NetWorking.Packets.ToolTipPackets;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DailyMissionS2CPacket {
    private final int[] Kills;
    public DailyMissionS2CPacket(int[] Kills)
    {
        this.Kills = Kills;
    }
    public DailyMissionS2CPacket(FriendlyByteBuf buf)
    {
        this.Kills = buf.readVarIntArray();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeVarIntArray(this.Kills);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.DailyMission = Kills;
        });
        return true;
    }
}
