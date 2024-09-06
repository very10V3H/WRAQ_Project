package com.very.wraq.process.system.smelt;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SmeltRequestC2SPacket {

    private final int index;

    public SmeltRequestC2SPacket(int index) {
        this.index = index;
    }

    public SmeltRequestC2SPacket(FriendlyByteBuf buf) {
        this.index = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.index);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SmeltRecipe.createSmeltProcess(context.getSender(), index);
            Smelt.sendDataToClient(context.getSender());
        });
        return true;
    }
}
