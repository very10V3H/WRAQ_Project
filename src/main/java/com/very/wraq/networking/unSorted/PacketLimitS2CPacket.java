package com.very.wraq.networking.unSorted;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketLimitS2CPacket {
    private final int nps;

    public PacketLimitS2CPacket(int nps) {
        this.nps = nps;
    }

    public PacketLimitS2CPacket(FriendlyByteBuf buf) {
        this.nps = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.nps);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.clientPacketLimit = nps;
        });
        return true;
    }
}
