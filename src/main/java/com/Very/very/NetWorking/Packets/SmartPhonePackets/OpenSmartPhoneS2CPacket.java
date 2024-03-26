package com.Very.very.NetWorking.Packets.SmartPhonePackets;

import com.Very.very.Render.Gui.TestAndHelper.OpenGui;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenSmartPhoneS2CPacket {
    public OpenSmartPhoneS2CPacket()
    {

    }
    public OpenSmartPhoneS2CPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            DistExecutor.safeCallWhenOn(Dist.CLIENT,() -> OpenGui::new);
        });
        return true;
    }
}
