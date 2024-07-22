package com.very.wraq.process.system.vp.networking;

import com.very.wraq.process.system.vp.VpDataHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VpValueS2CPacket {

    private final double value;

    public VpValueS2CPacket(double value) {
        this.value = value;
    }

    public VpValueS2CPacket(FriendlyByteBuf buf) {
        this.value = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.value);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            VpDataHandler.clientVpValue = value;
        });
        return true;
    }
}
