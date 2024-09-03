package com.very.wraq.networking.misc.SmartPhonePackets;

import com.mojang.logging.LogUtils;
import com.very.wraq.files.MarketItemInfo;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.Iterator;
import java.util.function.Supplier;

public class BuyCheckC2SPacket {

    private final String playerName;
    private final ItemStack itemStack;
    private final double price;

    public BuyCheckC2SPacket(String playerName, ItemStack itemStack, double price) {
        this.playerName = playerName;
        this.itemStack = itemStack;
        this.price = price;
    }

    public BuyCheckC2SPacket(FriendlyByteBuf buf) {
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
            MarketItemInfo marketItemInfo = new MarketItemInfo(playerName, itemStack, price);
            Iterator<MarketItemInfo> iterator = Utils.marketItemInfos.iterator();
            boolean HasFlag = false;
            boolean MoneyFlag = false;
            MarketItemInfo RemoveInfo = null;
            while (iterator.hasNext()) {
                MarketItemInfo tmp = iterator.next();
                if (tmp.getPlayer().equals(marketItemInfo.getPlayer())
                        && Compute.getItemStackString(itemStack).equals(Compute.getItemStackString(tmp.getItemStack()))
                        && tmp.getItemStackCount() == marketItemInfo.getItemStackCount()
                        && tmp.getPrice() == marketItemInfo.getPrice()) {
                    if (serverPlayer.getPersistentData().getDouble("VB") >= tmp.getPrice()) {
                        Utils.marketPlayerInfos.put(tmp.getPlayer(), Utils.marketPlayerInfos.getOrDefault(tmp.getPlayer(), 0d) + tmp.getPrice());
                        HasFlag = true;
                        RemoveInfo = tmp;
                    } else {
                        MoneyFlag = true;
                    }
                    break;
                }
            }
            if (HasFlag) {
                Compute.VBExpenseAndMSGSend(serverPlayer, (float) marketItemInfo.getPrice());
                if (MarketItemInfo.itemCanBeSold(marketItemInfo.getItemStack())) {
                    ItemStack itemStack = marketItemInfo.getItemStack();
                    if (serverPlayer.getServer().getPlayerList().getPlayerByName(marketItemInfo.getPlayer()) != null) {
                        ServerPlayer seller = serverPlayer.getServer().getPlayerList().getPlayerByName(marketItemInfo.getPlayer());
                        Compute.sendFormatMSG(seller, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(serverPlayer.getDisplayName()).
                                        append(Component.literal("购买了你上架的 ").withStyle(ChatFormatting.WHITE)).
                                        append(itemStack.getDisplayName()));
                    }

                    LogUtils.getLogger().info("市场 {} 以 {} 购买了 {} 出售的 {} ", serverPlayer.getName().getString(), price, marketItemInfo.getPlayer(), itemStack);

                    Compute.itemStackGive(serverPlayer, itemStack);
                }

                Utils.marketItemInfos.remove(RemoveInfo);
                Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("购买成功！").withStyle(ChatFormatting.WHITE));
            } else {
                if (MoneyFlag) {
                    Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                            Component.literal("余额不足").withStyle(ChatFormatting.WHITE));
                } else {
                    Compute.sendFormatMSG(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                            Component.literal("购买失败！商品可能已经被购买。").withStyle(ChatFormatting.WHITE));
                }
            }

            ModNetworking.sendToClient(new MarketDataS2CPacket(Utils.marketItemInfos), serverPlayer);
        });
        return true;
    }
}
