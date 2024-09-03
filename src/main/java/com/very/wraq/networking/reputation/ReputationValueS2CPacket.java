package com.very.wraq.networking.reputation;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReputationValueS2CPacket {

    private final int reputation;

    public ReputationValueS2CPacket(int reputation) {
        this.reputation = reputation;
    }

    public ReputationValueS2CPacket(FriendlyByteBuf buf) {
        this.reputation = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.reputation);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.ReputationNum = reputation;
        });
        return true;
    }
}
