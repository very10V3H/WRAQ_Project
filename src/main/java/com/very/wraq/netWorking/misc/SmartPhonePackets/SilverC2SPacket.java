package com.very.wraq.netWorking.misc.SmartPhonePackets;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.registry.ModItems;
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

public class SilverC2SPacket {
    private int flag = -1;
    public SilverC2SPacket(int flag)
    {
        this.flag = flag;
    }
    public SilverC2SPacket(FriendlyByteBuf buf)
    {
        this.flag = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(this.flag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer player = context.getSender();
            CompoundTag data = player.getPersistentData();
            Inventory inventory = player.getInventory();
            switch (flag){
                case 0 ->{
                    int SilverCoinNum = Compute.ItemStackCount(inventory, ModItems.SilverCoin.get());
                    if(SilverCoinNum > 0){
                        try {
                            Compute.ItemStackRemoveIgnoreVB(inventory, ModItems.SilverCoin.get(),SilverCoinNum);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Compute.VBgetAndMSGSend(player,SilverCoinNum);
                    }
                    else{
                        Compute.FormatMSGSend(player,Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("背包内似乎没有银币用于兑换。").withStyle(ChatFormatting.WHITE));
                    }
                }
                case 1 -> {
                    if(data.contains("VB") && data.getDouble("VB") >= 10){
                        Compute.VBputAndMSGSend(player,10);
                        ItemStack itemStack = ModItems.SilverCoin.get().getDefaultInstance();
                        itemStack.setCount(10);
                        try {
                            Compute.ItemStackGive(player,itemStack);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        Compute.FormatMSGSend(player,Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.WHITE));
                    }
                }
                case 2 -> {
                    if(data.contains("VB") && data.getDouble("VB") >= 64){
                        Compute.VBputAndMSGSend(player,64);
                        ItemStack itemStack = ModItems.SilverCoin.get().getDefaultInstance();
                        itemStack.setCount(64);
                        try {
                            Compute.ItemStackGive(player,itemStack);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        Compute.FormatMSGSend(player,Component.literal("VB").withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.WHITE));
                    }
                }
            }
        });
        return true;
    }
}
