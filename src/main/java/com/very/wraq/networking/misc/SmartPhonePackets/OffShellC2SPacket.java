package com.very.wraq.networking.misc.SmartPhonePackets;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.very.wraq.files.MarketItemInfo;
import com.very.wraq.networking.ModNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OffShellC2SPacket {

    private final String playerName;
    private final ItemStack itemStack;
    private final double price;

    public OffShellC2SPacket(String playerName, ItemStack itemStack, double price) {
        this.playerName = playerName;
        this.itemStack = itemStack;
        this.price = price;
    }

    public OffShellC2SPacket(FriendlyByteBuf buf) {
        this.playerName = buf.readUtf();
        this.itemStack = buf.readItem();
        this.price = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.playerName);
        buf.writeItem(this.itemStack);
        buf.writeDouble(this.price);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (!playerName.equals(serverPlayer.getName().getString()) && !serverPlayer.isCreative()) {
                Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("你不能下架不属于你的物品").withStyle(ChatFormatting.WHITE));
            }
            MarketItemInfo marketItemInfo = new MarketItemInfo(playerName, itemStack, price);
            MarketItemInfo removeItemInfo = null;
            for (MarketItemInfo itemInfo : Utils.marketItemInfos) {
                if (itemInfo.equals(marketItemInfo)) removeItemInfo = itemInfo;
            }
            if (removeItemInfo != null) {
                serverPlayer.addItem(removeItemInfo.getItemStack());
                Utils.marketItemInfos.remove(removeItemInfo);
                Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("你成功下架了一件物品").withStyle(ChatFormatting.WHITE));
            } else {
                Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("物品可能已被购买").withStyle(ChatFormatting.WHITE));
            }
            ModNetworking.sendToClient(new MarketDataS2CPacket(Utils.marketItemInfos), serverPlayer);
        });
        return true;
    }
}
