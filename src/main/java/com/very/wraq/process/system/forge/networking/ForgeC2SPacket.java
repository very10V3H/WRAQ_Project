package com.very.wraq.process.system.forge.networking;

import com.very.wraq.Items.Forging.ForgeDraw;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class ForgeC2SPacket {

    private final ItemStack itemStack;

    public ForgeC2SPacket(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ForgeC2SPacket(FriendlyByteBuf buf) {
        this.itemStack = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.itemStack);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            try {
                ForgeDraw.tryToForge(serverPlayer, itemStack.getItem());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
