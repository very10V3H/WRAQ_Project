package com.very.wraq.process.func.guide.networking;

import com.very.wraq.process.func.guide.Guide;
import com.very.wraq.process.func.plan.DailySupply;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GuideStageS2CPacket {

    private final int status;

    public GuideStageS2CPacket(int status) {
        this.status = status;
    }

    public GuideStageS2CPacket(FriendlyByteBuf buf) {
        this.status = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(status);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Guide.clientStage = status;
        });
        return true;
    }
}
