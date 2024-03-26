package com.Very.very.NetWorking.Customized;

import com.Very.very.Customize.Players.Qi_Fu.QiFuBow;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class QiFuSceptreC2SPacket {

    public QiFuSceptreC2SPacket()
    {

    }
    public QiFuSceptreC2SPacket(FriendlyByteBuf buf)
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
            QiFuBow.Active(serverPlayer);
        });
        return true;
    }
}
