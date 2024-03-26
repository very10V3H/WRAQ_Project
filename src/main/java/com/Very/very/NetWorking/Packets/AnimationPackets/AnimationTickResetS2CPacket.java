package com.Very.very.NetWorking.Packets.AnimationPackets;

import com.Very.very.Events.ClientEvents.ClientAttackEvent;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AnimationTickResetS2CPacket {
    public AnimationTickResetS2CPacket()
    {

    }
    public AnimationTickResetS2CPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{

            ClientUtils.AnimationTickReset();
        });
        return true;
    }
}
