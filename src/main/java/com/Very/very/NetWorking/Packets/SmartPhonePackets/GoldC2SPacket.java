package com.Very.very.NetWorking.Packets.SmartPhonePackets;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GoldC2SPacket {
    private int flag = -1;
    public GoldC2SPacket(int flag)
    {
        this.flag = flag;
    }
    public GoldC2SPacket(FriendlyByteBuf buf)
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
                    int GoldCoinNum = Compute.ItemStackCount(inventory,Moditems.GoldCoin.get());
                    if(GoldCoinNum > 0){
                        Compute.ItemStackRemove(inventory,Moditems.GoldCoin.get(),GoldCoinNum);
                        Compute.VBgetAndMSGSend(player,GoldCoinNum*64);
                    }
                    else{
                        Compute.FormatMSGSend(player, Component.literal("VB").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                                Component.literal("背包内似乎没有金币用于兑换。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    }
                }
                case 1 -> {
                    if(data.contains("VB") && data.getFloat("VB") >= 64){
                        Compute.VBputAndMSGSend(player,64);
                        ItemStack itemStack = Moditems.GoldCoin.get().getDefaultInstance();
                        itemStack.setCount(1);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        Compute.ItemStackGive(player,itemStack);
                    }
                    else{
                        Compute.FormatMSGSend(player,Component.literal("VB").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    }
                }
                case 2 -> {
                    if(data.contains("VB") && data.getFloat("VB") >= 640){
                        Compute.VBputAndMSGSend(player,640);
                        ItemStack itemStack = Moditems.GoldCoin.get().getDefaultInstance();
                        itemStack.setCount(10);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        Compute.ItemStackGive(player,itemStack);
                    }
                    else{
                        Compute.FormatMSGSend(player,Component.literal("VB").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    }
                }
            }
        });
        return true;
    }
}
