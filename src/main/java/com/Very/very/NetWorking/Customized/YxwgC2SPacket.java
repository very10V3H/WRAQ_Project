package com.Very.very.NetWorking.Customized;

import com.Very.very.Customize.Players.Yxwg.YxwgBow;
import com.Very.very.Customize.Players.cgswd.CgswdSceptre;
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
