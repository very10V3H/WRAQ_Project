package com.very.wraq.netWorking.customized;

import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisa;
import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisaSceptre;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlackFeisaC2SPacket {
    public BlackFeisaC2SPacket()
    {

    }
    public BlackFeisaC2SPacket(FriendlyByteBuf buf)
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
            BlackFeisa.BlackFeisaActive(serverPlayer);
            BlackFeisaSceptre.ActiveStatusChange();
        });
        return true;
    }
}
