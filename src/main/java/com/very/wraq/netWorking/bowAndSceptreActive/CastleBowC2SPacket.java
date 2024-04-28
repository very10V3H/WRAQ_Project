package com.very.wraq.netWorking.bowAndSceptreActive;

import com.very.wraq.series.instance.Castle.CastleBow;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CastleBowC2SPacket {
    public CastleBowC2SPacket()
    {

    }
    public CastleBowC2SPacket(FriendlyByteBuf buf)
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
            CastleBow.Active(serverPlayer);
        });
        return true;
    }
}
