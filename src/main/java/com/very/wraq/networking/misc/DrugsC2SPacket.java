package com.very.wraq.networking.misc;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrugsC2SPacket {
    public DrugsC2SPacket() {

    }

    public DrugsC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Inventory inventory = player.getInventory();
            CompoundTag data = player.getPersistentData();
            if (Compute.ItemCheck(player, ModItems.drug0.get().getDefaultInstance()) != -1) {
                ItemStack itemStack = inventory.getItem(Compute.ItemCheck(player, ModItems.drug0.get().getDefaultInstance()));
                if (player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                    if (!data.contains("IgnoreUse") || (!data.getBoolean("IgnoreUse")))
                        player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("药品").withStyle(ChatFormatting.RED)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)).append(Component.literal("药品正在冷却。").withStyle(ChatFormatting.WHITE)));
                } else {
                    itemStack.setCount(itemStack.getCount() - 1);
                    inventory.setItem(Compute.ItemCheck(player, ModItems.drug0.get().getDefaultInstance()), itemStack);
                    player.heal(10);
                    player.getCooldowns().addCooldown(itemStack.getItem(), 200);
                }
            } else if (!data.contains("IgnoreUse") || (!data.getBoolean("IgnoreUse")))
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("药品").withStyle(ChatFormatting.RED)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)).append(Component.literal("背包里没有可用药品。").withStyle(ChatFormatting.WHITE)));

        });
        return true;
    }
}
