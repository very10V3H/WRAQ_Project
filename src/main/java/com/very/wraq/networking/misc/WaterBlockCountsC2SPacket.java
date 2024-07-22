package com.very.wraq.networking.misc;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WaterBlockCountsC2SPacket {
    private final int counts;

    public WaterBlockCountsC2SPacket(int counts) {
        this.counts = counts;
    }

    public WaterBlockCountsC2SPacket(FriendlyByteBuf buf) {
        this.counts = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.counts);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            Utils.ShipSceptreWaterBlockNum.put(serverPlayer, counts);
        });
        return true;
    }
}
