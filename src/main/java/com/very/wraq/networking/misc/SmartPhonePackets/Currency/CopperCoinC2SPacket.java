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

public class CopperCoinC2SPacket {
    private int flag = -1;

    public CopperCoinC2SPacket(int flag) {
        this.flag = flag;
    }

    public CopperCoinC2SPacket(FriendlyByteBuf buf) {
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

                // 所有将铜币存入账户
                case 0 -> {
                    int SilverCoinNum = Compute.itemStackCount(inventory, ModItems.copperCoin.get());
                    if (SilverCoinNum > 0) {
                        Compute.itemStackRemoveIgnoreVB(inventory, ModItems.copperCoin.get(), SilverCoinNum);
                        Compute.VBIncomeAndMSGSend(player, SilverCoinNum);
                    } else {
                        Compute.formatMSGSend(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("背包内似乎没有铜币用于兑换。").withStyle(ChatFormatting.WHITE));
                    }
                }

                // 从账户获取10铜币
                case 1 -> {
                    int vbValue = 1;
                    int count = 10;
                    if (data.contains("VB") && data.getDouble("VB") >= count * vbValue) {
                        Compute.VBExpenseAndMSGSend(player, count * vbValue);
                        ItemStack itemStack = ModItems.copperCoin.get().getDefaultInstance();
                        itemStack.setCount(count);
                        Compute.itemStackGive(player, itemStack);
                    } else {
                        Compute.formatMSGSend(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.WHITE));
                    }
                }

                // 从账户获取64枚银币
                case 2 -> {
                    int vbValue = 1;
                    int count = 64;
                    if (data.contains("VB") && data.getDouble("VB") >= vbValue * count) {
                        Compute.VBExpenseAndMSGSend(player, vbValue * count);
                        ItemStack itemStack = ModItems.copperCoin.get().getDefaultInstance();
                        itemStack.setCount(count);
                        Compute.itemStackGive(player, itemStack);
                    } else {
                        Compute.formatMSGSend(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.WHITE));
                    }
                }

                // 从背包中使用1枚银币兑换16枚铜币
                case 3 -> {
                    try {
                        Compute.itemTrade(player, new ItemStack(ModItems.silverCoin.get(), 1), new ItemStack(ModItems.copperCoin.get(), 12));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 从背包中使用1枚金币兑换256枚铜币
                case 4 -> {
                    try {
                        Compute.itemTrade(player, new ItemStack(ModItems.goldCoin.get(), 1), new ItemStack(ModItems.copperCoin.get(), 144));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return true;
    }
}
