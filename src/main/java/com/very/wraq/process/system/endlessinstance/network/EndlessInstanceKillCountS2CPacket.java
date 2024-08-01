package com.very.wraq.process.system.endlessinstance.network;

import com.very.wraq.process.system.endlessinstance.DailyEndlessInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EndlessInstanceKillCountS2CPacket {
    private final int count;

    public EndlessInstanceKillCountS2CPacket(int count) {
        this.count = count;
    }

    public EndlessInstanceKillCountS2CPacket(FriendlyByteBuf buf) {
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DailyEndlessInstance.clientKillCount = count;
            DailyEndlessInstance.clientLastTick = 100;
        });
        return true;
    }
}
