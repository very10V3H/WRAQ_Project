package com.very.wraq.networking.misc;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EndRune3TypeS2CPacket {
    private final int Type;

    public EndRune3TypeS2CPacket(int type) {
        this.Type = type;
    }

    public EndRune3TypeS2CPacket(FriendlyByteBuf buf) {
        this.Type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.EndRune3Type = this.Type;
        });
        return true;
    }
}
