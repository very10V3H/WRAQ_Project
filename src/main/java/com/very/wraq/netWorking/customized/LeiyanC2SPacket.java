package com.very.wraq.netWorking.customized;

import com.very.wraq.customized.players.bow.Lei_yan233.LeiyanBow;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LeiyanC2SPacket {
    public LeiyanC2SPacket()
    {

    }
    public LeiyanC2SPacket(FriendlyByteBuf buf)
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
            LeiyanBow.SwitchMode(serverPlayer);
        });
        return true;
    }
}
