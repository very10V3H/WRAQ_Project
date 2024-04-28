package com.very.wraq.netWorking.bowAndSceptreActive;

import com.very.wraq.process.element.equipAndCurios.fireElement.FireElementSceptre;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FireElementSceptreC2SPacket {
    public FireElementSceptreC2SPacket()
    {

    }
    public FireElementSceptreC2SPacket(FriendlyByteBuf buf)
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
            FireElementSceptre.Active(serverPlayer);
        });
        return true;
    }
}
