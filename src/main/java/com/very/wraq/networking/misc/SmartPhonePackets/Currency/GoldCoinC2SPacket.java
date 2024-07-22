package com.very.wraq.networking.misc.SmartPhonePackets.Currency;

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

import java.io.IOException;
import java.util.function.Supplier;

public class GoldCoinC2SPacket {
    private int flag = -1;

    public GoldCoinC2SPacket(int flag) {
        this.flag = flag;
    }

    public GoldCoinC2SPacket(FriendlyByteBuf buf) {
        this.flag = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.flag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            CompoundTag data = player.getPersistentData();
            Inventory inventory = player.getInventory();
            switch (flag) {

                // 将背包内所有金币存入账户
                case 0 -> {
                    int GoldCoinNum = Compute.itemStackCount(inventory, ModItems.goldCoin.get());
                    if (GoldCoinNum > 0) {
                        Compute.itemStackRemoveIgnoreVB(inventory, ModItems.goldCoin.get(), GoldCoinNum);
                        Compute.VBIncomeAndMSGSend(player, GoldCoinNum * 144);
                    } else {
                        Compute.formatMSGSend(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("背包内似乎没有金币用于兑换。").withStyle(ChatFormatting.WHITE));
                    }
                }

                // 从账户中支取1枚金币
                case 1 -> {
                    int vbValue = 144;
                    int count = 1;
                    if (data.contains("VB") && data.getDouble("VB") >= vbValue * count) {
                        Compute.VBExpenseAndMSGSend(player, vbValue * count);
                        ItemStack itemStack = ModItems.goldCoin.get().getDefaultInstance();
                        itemStack.setCount(count);
                        Compute.itemStackGive(player, itemStack);
                    } else {
                        Compute.formatMSGSend(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.WHITE));
                    }
                }

                // 从账户中支取10枚金币
                case 2 -> {
                    int vbValue = 144;
                    int count = 10;
                    if (data.contains("VB") && data.getDouble("VB") >= vbValue * count) {
                        Compute.VBExpenseAndMSGSend(player, vbValue * count);
                        ItemStack itemStack = ModItems.goldCoin.get().getDefaultInstance();
                        itemStack.setCount(count);
                        Compute.itemStackGive(player, itemStack);
                    } else {
                        Compute.formatMSGSend(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.WHITE));
                    }
                }

                // 使用64枚铜币兑换1枚金币
                case 3 -> {
                    try {
                        Compute.itemTrade(player, new ItemStack(ModItems.copperCoin.get(), 144), new ItemStack(ModItems.goldCoin.get()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 使用8枚银币兑换一枚金币
                case 4 -> {
                    try {
                        Compute.itemTrade(player, new ItemStack(ModItems.silverCoin.get(), 12), new ItemStack(ModItems.goldCoin.get()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return true;
    }
}
