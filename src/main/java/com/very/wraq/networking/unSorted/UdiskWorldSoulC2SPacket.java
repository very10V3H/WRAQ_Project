package com.very.wraq.networking.unSorted;

import com.very.wraq.common.registry.ModItems;
import com.very.wraq.process.func.item.InventoryOperation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UdiskWorldSoulC2SPacket {
    public UdiskWorldSoulC2SPacket() {

    }

    public UdiskWorldSoulC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            Inventory inventory = serverPlayer.getInventory();
            while (InventoryOperation.checkPlayerHasItem(inventory, ModItems.WorldSoul1.get(), 64)) {
                InventoryOperation.removeItem(inventory, ModItems.WorldSoul1.get(), 64);
                InventoryOperation.itemStackGive(serverPlayer, new ItemStack(ModItems.WorldSoul2.get(), 1));
            }
        });
        return true;
    }
}
