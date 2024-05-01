package com.very.wraq.netWorking.bowAndSceptreActive;

import com.very.wraq.process.element.equipAndCurios.waterElement.WaterElementBow;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WaterElementBowC2SPacket {
    public WaterElementBowC2SPacket()
    {

    }
    public WaterElementBowC2SPacket(FriendlyByteBuf buf)
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
            WaterElementBow.Active(serverPlayer);
        });
        return true;
    }
}
