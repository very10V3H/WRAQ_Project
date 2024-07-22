package com.very.wraq.networking.reputation;

import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReputationBuyRequestC2SPacket {

    private final int index;

    public ReputationBuyRequestC2SPacket(int index) {
        this.index = index;
    }

    public ReputationBuyRequestC2SPacket(FriendlyByteBuf buf) {
        this.index = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.index);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (Utils.ReputationStoreItemList.isEmpty()) Utils.setReputationStoreItemList();
            if (Utils.ReputationStorePrice.isEmpty()) Utils.setReputationStorePrice();
            if (index >= Utils.ReputationStoreItemList.size()) {
                Compute.formatMSGSend(serverPlayer, Component.literal("声望商店").withStyle(ChatFormatting.YELLOW),
                        Component.literal("暂无商品。").withStyle(ChatFormatting.WHITE));
                return;
            }
            Item item = Utils.ReputationStoreItemList.get(index);
            int Price = Utils.ReputationStorePrice.get(item);
            if (Compute.playerReputationAddOrCost(serverPlayer, -Price)) {
                ItemStack itemStack = item.getDefaultInstance();
                if (item.equals(ModItems.notePaper.get()))
                    InventoryCheck.addOwnerTagToItemStack(serverPlayer, itemStack);
                Compute.itemStackGive(serverPlayer, itemStack);
            }
        });
        return true;
    }
}
