package com.very.wraq.networking.unSorted;

import com.very.wraq.render.hud.ClientColdNum;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ColdSyncS2CPacket {
    private final double MaxSwift;
    private final double CurrentSwift;

    public ColdSyncS2CPacket(double MaxSwift, double CurrentSwift) {
        this.MaxSwift = MaxSwift;
        this.CurrentSwift = CurrentSwift;
    }

    public ColdSyncS2CPacket(FriendlyByteBuf buf) {
        this.MaxSwift = buf.readDouble();
        this.CurrentSwift = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.MaxSwift);
        buf.writeDouble(this.CurrentSwift);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientColdNum.setMaxCold(MaxSwift);
            ClientColdNum.setCurrentCold(CurrentSwift);
        });
        return true;
    }
}
