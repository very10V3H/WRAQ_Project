package com.very.wraq.netWorking.misc.SmartPhonePackets;

import com.very.wraq.files.MarketItemInfo;
import com.very.wraq.files.MarketPlayerInfo;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Supplier;

public class BuyCheckC2SPacket {
    private final String playerName;
    private final String itemStackName;
    private final double price;
    public BuyCheckC2SPacket(String playerName, String itemStackName, double price)
    {
        this.playerName = playerName;
        this.itemStackName = itemStackName;
        this.price = price;
    }
    public BuyCheckC2SPacket(FriendlyByteBuf buf)
    {
        this.playerName = buf.readUtf();
        this.itemStackName = buf.readUtf();
        this.price = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUtf(this.playerName);
        buf.writeUtf(this.itemStackName);
        buf.writeDouble(this.price);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer serverPlayer = context.getSender();
            MarketItemInfo marketItemInfo = new MarketItemInfo(playerName,itemStackName,price);
            Iterator<MarketItemInfo> iterator = Utils.MarketInfo.iterator();
            boolean HasFlag = false;
            boolean MoneyFlag = false;
            MarketItemInfo RemoveInfo = null;
            while(iterator.hasNext()){
                MarketItemInfo tmp = iterator.next();
                if(tmp.getPlayer().equals(marketItemInfo.getPlayer())
                        && tmp.getItemStack().equals(marketItemInfo.getItemStack())
                        && tmp.getPrice() == marketItemInfo.getPrice()) {
                    if(serverPlayer.getPersistentData().getDouble("VB") >= tmp.getPrice()) {
                        MarketPlayerInfo marketPlayerInfo = new MarketPlayerInfo(tmp.getPlayer(), tmp.getPrice());
                        Utils.MarketPlayerInfo.add(marketPlayerInfo);
                        HasFlag = true;
                        RemoveInfo = tmp;
                        break;
                    }
                    else{
                        MoneyFlag = true;
                        break;
                    }
                }
            }
            if(HasFlag) {
                Compute.VBputAndMSGSend(serverPlayer,(float) marketItemInfo.getPrice());
                if(Utils.itemStackMap.containsKey(marketItemInfo.getItemStackName())){
                    ItemStack itemStack = Utils.itemStackMap.get(marketItemInfo.getItemStackName());
                    itemStack.setCount(marketItemInfo.getItemStackCount());
                    if (serverPlayer.getServer().getPlayerList().getPlayerByName(marketItemInfo.getPlayer()) != null) {
                        ServerPlayer seller = serverPlayer.getServer().getPlayerList().getPlayerByName(marketItemInfo.getPlayer());
                        Compute.FormatMSGSend(seller, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(serverPlayer.getDisplayName()).
                                        append(Component.literal("购买了你上架的 ").withStyle(ChatFormatting.WHITE)).
                                        append(itemStack.getDisplayName()));
                    }
                    try {
                        Compute.ItemStackGive(serverPlayer,itemStack);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(Utils.PotionMap.containsKey(marketItemInfo.getItemStackName())){
                    ItemStack itemStack = Items.POTION.getDefaultInstance();
                    itemStack.getOrCreateTag().putString("Potion",marketItemInfo.getItemStackName());
                    itemStack.setCount(marketItemInfo.getItemStackCount());
                    if (serverPlayer.getServer().getPlayerList().getPlayerByName(marketItemInfo.getPlayer()) != null) {
                        ServerPlayer seller = serverPlayer.getServer().getPlayerList().getPlayerByName(marketItemInfo.getPlayer());
                        Compute.FormatMSGSend(seller, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(serverPlayer.getDisplayName()).
                                        append(Component.literal("购买了你上架的 ").withStyle(ChatFormatting.WHITE)).
                                        append(itemStack.getDisplayName()));
                    }
                    try {
                        Compute.ItemStackGive(serverPlayer,itemStack);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                Utils.MarketInfo.remove(RemoveInfo);
                Compute.FormatMSGSend(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                        Component.literal("购买成功！").withStyle(ChatFormatting.WHITE));
            }
            else {
                if(MoneyFlag){
                    Compute.FormatMSGSend(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                            Component.literal("余额不足").withStyle(ChatFormatting.WHITE));
                }
                else{
                    Compute.FormatMSGSend(serverPlayer, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                            Component.literal("购买失败！商品可能已经被购买。").withStyle(ChatFormatting.WHITE));

                }
            }
            Iterator<MarketItemInfo> iterator0 = Utils.MarketInfo.iterator();
            ModNetworking.sendToClient(new MarketDataClearS2CPacket(),serverPlayer);
            while(iterator0.hasNext()){
                MarketItemInfo marketItemInfo0 = iterator0.next();
                ModNetworking.sendToClient(new MarketDataS2CPacket(marketItemInfo0.getPlayer(),marketItemInfo0.getItemStack(),marketItemInfo0.getPrice()),serverPlayer);
            }
        });
        return true;
    }
}
