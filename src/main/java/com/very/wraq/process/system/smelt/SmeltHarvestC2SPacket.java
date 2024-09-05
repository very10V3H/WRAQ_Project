package com.very.wraq.process.system.smelt;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.text.ParseException;
import java.util.function.Supplier;

public class SmeltHarvestC2SPacket {

    private final int index;

    public SmeltHarvestC2SPacket(int index) {
        this.index = index;
    }

    public SmeltHarvestC2SPacket(FriendlyByteBuf buf) {
        this.index = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.index);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            try {
                Smelt.checkFinishAndGiveItem(context.getSender(), index);
            } catch (ParseException | CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
