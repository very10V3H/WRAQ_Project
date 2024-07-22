package com.very.wraq.networking.misc.AnimationPackets;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RollingS2CPacket {
    private final double Rate;

    public RollingS2CPacket(double rate) {
        this.Rate = rate;
    }

    public RollingS2CPacket(FriendlyByteBuf buf) {
        this.Rate = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.Rate);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.RollingFlag = true;
            ClientUtils.RollingRate = Rate;
        });
        return true;
    }
}
