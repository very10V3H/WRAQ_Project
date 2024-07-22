package com.very.wraq.networking.unSorted;

import com.very.wraq.render.hud.ClientSwiftData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwiftSyncS2CPacket {
    private final double MaxSwift;
    private final double CurrentSwift;

    public SwiftSyncS2CPacket(double MaxSwift, double CurrentSwift) {
        this.MaxSwift = MaxSwift;
        this.CurrentSwift = CurrentSwift;
    }

    public SwiftSyncS2CPacket(FriendlyByteBuf buf) {
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
            ClientSwiftData.setMaxSwift(MaxSwift);
            ClientSwiftData.setCurrentSwift(CurrentSwift);
        });
        return true;
    }
}
