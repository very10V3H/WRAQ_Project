package com.Very.very.NetWorking.Packets.SmartPhonePackets;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SecurityBuyC2SPacket {
    private int flag = -1;
    public SecurityBuyC2SPacket(int flag)
    {
        this.flag = flag;
    }
    public SecurityBuyC2SPacket(FriendlyByteBuf buf)
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
            switch (flag){
                case 0 ->{
                    if(data.getFloat("VB") >= Utils.security0){
                        Compute.VBputAndMSGSend(player,Utils.security0);
                        if(data.contains("securityGet")) data.putFloat("securityGet",data.getFloat("securityGet")-Utils.security0);
                        else data.putFloat("securityGet",-Utils.security0);
                        if(data.contains("security0")) data.putFloat("security0",data.getFloat("security0")+1);
                        else data.putFloat("security0",1);
                        Compute.FormatBroad(player.level(), Component.literal("股票").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                                Component.literal(player.getName().getString()+"买入了价值"+String.format("%.2f",Utils.security0)+"的维瑞库尤酒店股票。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED).
                                        append(Component.literal("Ta当前的股票收益为:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f",data.getFloat("securityGet"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                    }
                    else{
                        Compute.FormatMSGSend(player,Component.literal("VB").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    }
                }
                case 1 -> {
                    if(data.getFloat("VB") >= Utils.security1){
                        Compute.VBputAndMSGSend(player,Utils.security1);
                        if(data.contains("securityGet")) data.putFloat("securityGet",data.getFloat("securityGet")-Utils.security1);
                        else data.putFloat("securityGet",-Utils.security1);
                        if(data.contains("security1")) data.putFloat("security1",data.getFloat("security1")+1);
                        else data.putFloat("security1",1);
                        Compute.FormatBroad(player.level(), Component.literal("股票").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                                Component.literal(player.getName().getString()+"买入了价值"+String.format("%.2f",Utils.security1)+"的维瑞库尤矿业股票。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED).
                                        append(Component.literal("Ta当前的股票收益为:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f",data.getFloat("securityGet"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                    }
                    else{
                        Compute.FormatMSGSend(player,Component.literal("VB").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    }
                }
                case 2 -> {
                    if(data.getFloat("VB") >= Utils.security2){
                        Compute.VBputAndMSGSend(player,Utils.security2);
                        if(data.contains("securityGet")) data.putFloat("securityGet",data.getFloat("securityGet")-Utils.security2);
                        else data.putFloat("securityGet",-Utils.security2);
                        if(data.contains("security2")) data.putFloat("security2",data.getFloat("security2")+1);
                        else data.putFloat("security2",1);
                        Compute.FormatBroad(player.level(), Component.literal("股票").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                                Component.literal(player.getName().getString()+"买入了价值"+String.format("%.2f",Utils.security2)+"的维瑞库尤渔业股票。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED).
                                        append(Component.literal("Ta当前的股票收益为:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f",data.getFloat("securityGet"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                    }
                    else{
                        Compute.FormatMSGSend(player,Component.literal("VB").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                                Component.literal("VB不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    }
                }
                case 3 -> {
                    if(data.getFloat("VB") >= Utils.security3){
                        Compute.VBputAndMSGSend(player,Utils.security3);
                        if(data.contains("securityGet")) data.putFloat("securityGet",data.getFloat("securityGet")-Utils.security3);
                        else data.putFloat("securityGet",-Utils.security3);
                        if(data.contains("security3")) data.putFloat("security3",data.getFloat("security3")+1);
                        else data.putFloat("security3",1);
                        Compute.FormatBroad(player.level(), Component.literal("股票").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                                Component.literal(player.getName().getString()+"买入了价值"+String.format("%.2f",Utils.security3)+"的维瑞库尤建设股票。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED).
                                        append(Component.literal("Ta当前的股票收益为:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f",data.getFloat("securityGet"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
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
