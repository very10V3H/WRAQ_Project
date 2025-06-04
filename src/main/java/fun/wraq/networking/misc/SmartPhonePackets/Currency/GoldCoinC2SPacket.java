package fun.wraq.networking.misc.SmartPhonePackets.Currency;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
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
                    int GoldCoinNum = InventoryOperation.itemStackCount(inventory, ModItems.GOLD_COIN.get());
                    if (GoldCoinNum > 0) {
                        InventoryOperation.itemStackRemoveIgnoreVB(inventory, ModItems.GOLD_COIN.get(), GoldCoinNum);
                        Compute.VBIncomeAndMSGSend(player, GoldCoinNum * 144);
                    } else {
                        Compute.sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("背包内似乎没有金币用于兑换。").withStyle(ChatFormatting.WHITE));
                    }
                }

                // 从账户中支取1枚金币
                case 1 -> {
                    int vbValue = 144;
                    int count = 1;
                    if (data.contains("VB") && data.getDouble("VB") >= vbValue * count) {
                        Compute.VBExpenseAndMSGSend(player, vbValue * count);
                        ItemStack itemStack = ModItems.GOLD_COIN.get().getDefaultInstance();
                        itemStack.setCount(count);
                        InventoryOperation.giveItemStack(player, itemStack);
                    } else {
                        Compute.sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.WHITE));
                    }
                }

                // 从账户中支取10枚金币
                case 2 -> {
                    int vbValue = 144;
                    int count = 10;
                    if (data.contains("VB") && data.getDouble("VB") >= vbValue * count) {
                        Compute.VBExpenseAndMSGSend(player, vbValue * count);
                        ItemStack itemStack = ModItems.GOLD_COIN.get().getDefaultInstance();
                        itemStack.setCount(count);
                        InventoryOperation.giveItemStack(player, itemStack);
                    } else {
                        Compute.sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.WHITE));
                    }
                }

                // 使用64枚铜币兑换1枚金币
                case 3 -> {
                    try {
                        InventoryOperation.itemTrade(player, new ItemStack(ModItems.COPPER_COIN.get(), 144), new ItemStack(ModItems.GOLD_COIN.get()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 使用8枚银币兑换一枚金币
                case 4 -> {
                    try {
                        InventoryOperation.itemTrade(player, new ItemStack(ModItems.SILVER_COIN.get(), 12), new ItemStack(ModItems.GOLD_COIN.get()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return true;
    }
}
