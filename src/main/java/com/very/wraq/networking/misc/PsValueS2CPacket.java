package com.very.wraq.networking.misc;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PsValueS2CPacket {
    private final int PsValue;

    public PsValueS2CPacket(int psValue) {
        this.PsValue = psValue;
    }

    public PsValueS2CPacket(FriendlyByteBuf buf) {
        this.PsValue = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.PsValue);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.currentPsValue = this.PsValue;
        });
        return true;
    }
}
