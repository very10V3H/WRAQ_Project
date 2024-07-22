package com.very.wraq.process.system.tower;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TowerStatusS2CPacket {

    private final String status;

    public TowerStatusS2CPacket(String status) {
        this.status = status;
    }

    public TowerStatusS2CPacket(FriendlyByteBuf buf) {
        this.status = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(status);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Tower.clientTowerStatus = status;
        });
        return true;
    }

}

