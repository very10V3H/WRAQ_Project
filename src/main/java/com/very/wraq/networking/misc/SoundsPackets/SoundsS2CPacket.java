package com.very.wraq.networking.misc.SoundsPackets;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SoundsS2CPacket {
    private final int flag;

    public SoundsS2CPacket(int flag) {
        this.flag = flag;
    }

    public SoundsS2CPacket(FriendlyByteBuf buf) {
        this.flag = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.flag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.Sounds = flag;
        });
        return true;
    }
}
