package com.very.wraq.networking.unSorted;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TimeS2CPacket {

    private final String Date;

    public TimeS2CPacket(String date) {
        this.Date = date;
    }

    public TimeS2CPacket(FriendlyByteBuf buf) {
        this.Date = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.Date);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.serverTime = this.Date;
        });
        return true;
    }
}
