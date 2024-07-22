package com.very.wraq.networking.misc.TeamPackets;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientLevelTypeS2CPacket {
    private final int type;

    public ClientLevelTypeS2CPacket(int type) {
        this.type = type;
    }

    public ClientLevelTypeS2CPacket(FriendlyByteBuf buf) {
        this.type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.clientLevelType = this.type;
        });
        return true;
    }
}
