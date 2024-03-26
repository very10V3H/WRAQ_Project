package com.Very.very.NetWorking.BowAndSceptre;

import com.Very.very.Customize.Players.Black_Feisa_.BlackFeisa;
import com.Very.very.Customize.Players.Black_Feisa_.BlackFeisaSceptre;
import com.Very.very.Series.InstanceSeries.Castle.CastleBow;
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
