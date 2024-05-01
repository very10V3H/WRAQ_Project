package com.very.wraq.netWorking.bowAndSceptreActive;

import com.very.wraq.process.element.equipAndCurios.waterElement.WaterElementSceptre;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WaterElementSceptreC2SPacket {
    public WaterElementSceptreC2SPacket()
    {

    }
    public WaterElementSceptreC2SPacket(FriendlyByteBuf buf)
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
            WaterElementSceptre.Active(serverPlayer);
        });
        return true;
    }
}
