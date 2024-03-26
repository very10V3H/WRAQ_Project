package com.Very.very.NetWorking.BowAndSceptre;

import com.Very.very.Series.InstanceSeries.Castle.CastleBow;
import com.Very.very.Series.InstanceSeries.Castle.CastleSceptre;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CastleSceptreC2SPacket {
    public CastleSceptreC2SPacket()
    {

    }
    public CastleSceptreC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork( ()->{
            CastleSceptre.Active(serverPlayer);
        });
        return true;
    }
}
