package com.Very.very.NetWorking.Packets.SkillPackets;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BufferChangeS2CPacket {
    public BufferChangeS2CPacket()
    {

    }
    public BufferChangeS2CPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ClientUtils.SkillDataBuffer = false;
            ClientUtils.AbilityDataBuffer = false;
        });
        return true;
    }
}
