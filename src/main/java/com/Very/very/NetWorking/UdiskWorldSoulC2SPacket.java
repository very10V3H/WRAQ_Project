package com.Very.very.NetWorking;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class UdiskWorldSoulC2SPacket {
    public UdiskWorldSoulC2SPacket()
    {

    }
    public UdiskWorldSoulC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer serverPlayer = context.getSender();
            Inventory inventory = serverPlayer.getInventory();
            while (Compute.ItemStackCheck(inventory, ModItems.WorldSoul1.get(), 64)) {
                try {
                    Compute.ItemStackRemove(inventory,ModItems.WorldSoul1.get(),64);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Compute.ItemStackGive(serverPlayer,new ItemStack(ModItems.WorldSoul2.get(),1));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return true;
    }
}
