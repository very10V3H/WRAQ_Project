package com.very.wraq.networking.unSorted;

import com.very.wraq.common.registry.MySound;
import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.process.func.guide.Guide;
import com.very.wraq.process.system.lottery.NewLotteries;
import com.very.wraq.render.gui.villagerTrade.TradeList;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class TradeBuyRequestC2SPacket {
    private final String name;
    private final int index;

    public TradeBuyRequestC2SPacket(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public TradeBuyRequestC2SPacket(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
        this.index = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.name);
        buf.writeInt(this.index);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (TradeList.tradeContent.isEmpty() || TradeList.tradeRecipeMap.isEmpty()) TradeList.setTradeContent();
            ItemStack targetItemStack = TradeList.tradeContent.get(this.name).get(index);
            ItemStack giveItemStack = new ItemStack(targetItemStack.getItem(), targetItemStack.getCount());
            List<ItemStack> requireItemList = TradeList.tradeRecipeMap.get(targetItemStack);
            List<ItemStack> itemList = new ArrayList<>();

            requireItemList.forEach(itemStack -> {
                itemList.add(new ItemStack(itemStack.getItem(), itemStack.getCount()));
            });

            for (int i = 0; i < itemList.size(); i++) {
                for (int j = 0; j < itemList.size(); j++) {
                    if (i != j && itemList.get(j).is(itemList.get(i).getItem())) {
                        itemList.get(i).setCount(itemList.get(i).getCount() + itemList.get(j).getCount());
                        itemList.get(j).setCount(0);
                    }
                }
            }

            AtomicBoolean playerHasItem = new AtomicBoolean(true);
            itemList.removeIf(itemStack -> itemStack.getCount() == 0);

            // 判断玩家背包中是否拥有足够的货币 若有则直接使用即可
            boolean playerHasEnoughMoney = itemList.stream().noneMatch(TradeBuyRequestC2SPacket::itemStackIsCurrency);

            if (!playerHasEnoughMoney) for (ItemStack itemStack : itemList) {
                if (itemStackIsCurrency(itemStack)) {
                    if (Compute.checkPlayerHasItem(serverPlayer.getInventory(), itemStack.getItem(), itemStack.getCount())) {
                        playerHasEnoughMoney = true;
                    }
                }
            }

            int requireVBCount = requireItemListVBCount(itemList);
            boolean needToTransform = false;
            // 若无足够的货币 检测背包中货币总和 + 当前账户vb数是否大于需求数
            if (!playerHasEnoughMoney) {
                int playerCurrentTotalVb = (int) (playerInventoryCurrencyVBCount(serverPlayer) + Compute.CurrentVB(serverPlayer));
                if (playerCurrentTotalVb >= requireVBCount) {
                    playerHasEnoughMoney = true;
                    needToTransform = true;
                    itemList.removeIf(TradeBuyRequestC2SPacket::itemStackIsCurrency);
                }
            }

            itemList.forEach(itemStack -> {
                if (playerHasItem.get())
                    playerHasItem.set(Compute.checkPlayerHasItem(serverPlayer.getInventory(), itemStack.getItem(), itemStack.getCount()));
            });

            if (playerHasEnoughMoney && playerHasItem.get()) {
                itemList.forEach(itemStack -> {
                    Compute.removeItem(serverPlayer.getInventory(), itemStack.getItem(), itemStack.getCount());
                });

                if (requireVBCount > 0 && needToTransform) {
                    int income = removeAllCurrencyInInventoryAndGiveVB(serverPlayer);
                    if (income > 0)
                        Compute.sendFormatMSG(serverPlayer, Component.literal("交易").withStyle(ChatFormatting.GREEN),
                                Component.literal("本次交易将背包中的所有货币转化为了").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(income + "vb").withStyle(ChatFormatting.GOLD)).
                                        append(Component.literal("，再从vb账户中支出了").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(requireVBCount + "vb").withStyle(ChatFormatting.GOLD)));
                    Compute.VBExpenseAndMSGSend(serverPlayer, requireVBCount);
                }


                if (giveItemStack.is(Items.POTION))
                    giveItemStack.getOrCreateTag().putString("Potion", "minecraft:long_night_vision");

                if (giveItemStack.is(PatchouliAPI.get().getBookStack(new ResourceLocation(Utils.MOD_ID, "guide")).getItem()))
                    giveItemStack = PatchouliAPI.get().getBookStack(new ResourceLocation(Utils.MOD_ID, "guide"));

                if (NewLotteries.getRewardSerial.isEmpty()) NewLotteries.setGetRewardSerial();
                if (NewLotteries.getRewardSerial.containsKey(giveItemStack.getItem()))
                    InventoryCheck.addOwnerTagToItemStack(serverPlayer, giveItemStack);
                if (giveItemStack.is(TradeList.netheriteBackPack)) Guide.trig(serverPlayer, 0);
                Compute.itemStackGive(serverPlayer, giveItemStack);
                MySound.soundToPlayer(serverPlayer, SoundEvents.ARROW_HIT_PLAYER);
            } else {
                Compute.sendFormatMSG(serverPlayer, Component.literal("交易").withStyle(ChatFormatting.GREEN),
                        Component.literal("似乎没有足够的物品用于购买。").withStyle(ChatFormatting.WHITE));
            }
        });
        return true;
    }

    public static int playerInventoryCurrencyVBCount(Player player) {
        return Compute.itemStackCount(player, ModItems.copperCoin.get()) +
                Compute.itemStackCount(player, ModItems.silverCoin.get()) * 12 +
                Compute.itemStackCount(player, ModItems.goldCoin.get()) * 144;
    }

    public static int requireItemListVBCount(List<ItemStack> requireItemList) {
        int count = 0;
        for (ItemStack itemStack : requireItemList) {
            if (itemStack.is(ModItems.copperCoin.get())) count += itemStack.getCount();
            if (itemStack.is(ModItems.silverCoin.get())) count += itemStack.getCount() * 12;
            if (itemStack.is(ModItems.goldCoin.get())) count += itemStack.getCount() * 144;
        }
        return count;
    }

    public static int removeAllCurrencyInInventoryAndGiveVB(Player player) {
        Inventory inventory = player.getInventory();
        int income = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack.is(ModItems.copperCoin.get())) {
                income += itemStack.getCount();
                itemStack.setCount(0);
            }
            if (itemStack.is(ModItems.silverCoin.get())) {
                income += itemStack.getCount() * 12;
                itemStack.setCount(0);
            }
            if (itemStack.is(ModItems.goldCoin.get())) {
                income += itemStack.getCount() * 144;
                itemStack.setCount(0);
            }
        }
        if (income > 0) Compute.VBIncomeAndMSGSend(player, income);
        return income;
    }

    public static boolean itemStackIsCurrency(ItemStack itemStack) {
        return itemStack.is(ModItems.copperCoin.get()) || itemStack.is(ModItems.silverCoin.get())
                || itemStack.is(ModItems.goldCoin.get());
    }
}
