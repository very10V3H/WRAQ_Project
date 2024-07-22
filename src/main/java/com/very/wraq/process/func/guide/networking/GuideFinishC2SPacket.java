package com.very.wraq.process.func.guide.networking;

import com.very.wraq.process.func.guide.Guide;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GuideFinishC2SPacket {

    private final int stage;

    public GuideFinishC2SPacket(int stage) {
        this.stage = stage;
    }

    public GuideFinishC2SPacket(FriendlyByteBuf buf) {
        this.stage = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(stage);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = supplier.get().getSender();
            Guide.trig(player, stage);
        });
        return true;
    }
}
