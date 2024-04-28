package com.very.wraq.netWorking.customized;

import com.very.wraq.customized.players.bow.Yxwg.YxwgBow;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class YxwgC2SPacket {
    public YxwgC2SPacket()
    {

    }
    public YxwgC2SPacket(FriendlyByteBuf buf)
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
            YxwgBow.Active(serverPlayer);
        });
        return true;
    }
}
