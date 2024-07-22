package com.very.wraq.process.system.forge.networking;

import com.very.wraq.render.gui.blocks.ForgingBlockScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DecomposeRecipeLossS2CPacket {

    public DecomposeRecipeLossS2CPacket() {

    }

    public DecomposeRecipeLossS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ForgingBlockScreen.lossRecipe = 40;
        });
        return true;
    }
}
