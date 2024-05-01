package com.very.wraq.netWorking.unSorted;

import com.very.wraq.files.FileHandler;
import com.very.wraq.render.gui.villagerTrade.TradeList;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;
import vazkii.patchouli.api.PatchouliAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class TradeBuyRequestC2SPacket {
    private final String name;
    private final int index;
    public TradeBuyRequestC2SPacket(String name, int index)
    {
        this.name = name;
        this.index = index;
    }
    public TradeBuyRequestC2SPacket(FriendlyByteBuf buf)
    {
        this.name = buf.readUtf();
        this.index = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeUtf(this.name);
        buf.writeInt(this.index);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer serverPlayer = context.getSender();
            if (TradeList.tradeContent.isEmpty() || TradeList.tradeRecipeMap.isEmpty()) TradeList.setTradeContent();
            ItemStack targetItemStack = TradeList.tradeContent.get(this.name).get(index);
            ItemStack giveItemStack = new ItemStack(targetItemStack.getItem(),targetItemStack.getCount());
            List<ItemStack> requireItemList = TradeList.tradeRecipeMap.get(targetItemStack);
            List<ItemStack> itemList = new ArrayList<>();
            requireItemList.forEach(itemStack -> {
                itemList.add(new ItemStack(itemStack.getItem(),itemStack.getCount()));
            });

            for (int i = 0 ; i < itemList.size() ; i ++) {
                for (int j = 0 ; j < itemList.size() ; j ++) {
                    if (i != j && itemList.get(j).is(itemList.get(i).getItem())) {
                        itemList.get(i).setCount(itemList.get(i).getCount() + itemList.get(j).getCount());
                        itemList.get(j).setCount(0);
                    }
                }
            }
            itemList.removeIf(itemStack -> itemStack.getCount() == 0);
            AtomicBoolean playerHasItem = new AtomicBoolean(true);
            itemList.forEach(itemStack -> {
                if (playerHasItem.get()) playerHasItem.set(Compute.ItemStackCheck(serverPlayer.getInventory(), itemStack.getItem(), itemStack.getCount()));
            });
            if (playerHasItem.get()) {
                itemList.forEach(itemStack -> {
                    try {
                        Compute.ItemStackRemove(serverPlayer.getInventory(),itemStack.getItem(),itemStack.getCount());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                try {
                    FileHandler.WRAQLogWrite(serverPlayer, StringUtils.LogsType.Trade,giveItemStack.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (giveItemStack.is(Items.POTION)) giveItemStack.getOrCreateTag().putString("Potion","minecraft:long_night_vision");

                if (giveItemStack.is(PatchouliAPI.get().getBookStack(new ResourceLocation(Utils.MOD_ID,"guide")).getItem()))
                    giveItemStack = PatchouliAPI.get().getBookStack(new ResourceLocation(Utils.MOD_ID,"guide"));

                try {
                    Compute.ItemStackGive(serverPlayer,giveItemStack);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                Compute.FormatMSGSend(serverPlayer, Component.literal("交易").withStyle(ChatFormatting.GREEN),
                        Component.literal("似乎没有足够的物品用于购买。").withStyle(ChatFormatting.WHITE));
            }

        });
        return true;
    }
}
