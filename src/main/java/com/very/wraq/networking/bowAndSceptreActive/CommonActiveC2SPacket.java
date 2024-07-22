package com.very.wraq.networking.bowAndSceptreActive;

import com.very.wraq.common.Compute;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CommonActiveC2SPacket {

    private final ItemStack itemStack;

    public CommonActiveC2SPacket(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public CommonActiveC2SPacket(FriendlyByteBuf buf) {
        this.itemStack = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(itemStack);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork(() -> {
            Compute.use(serverPlayer, itemStack.getItem());
        });
        return true;
    }
}
