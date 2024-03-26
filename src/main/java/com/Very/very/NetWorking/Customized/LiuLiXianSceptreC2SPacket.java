package com.Very.very.NetWorking.Customized;

import com.Very.very.Customize.Players.liulixian_.liulixian_;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LiuLiXianSceptreC2SPacket {

    public LiuLiXianSceptreC2SPacket()
    {

    }
    public LiuLiXianSceptreC2SPacket(FriendlyByteBuf buf)
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
            liulixian_.LiuLiXianSwitch(serverPlayer);
        });
        return true;
    }
}
