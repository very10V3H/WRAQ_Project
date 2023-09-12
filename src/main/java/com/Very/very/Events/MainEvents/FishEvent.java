package com.Very.very.Events.MainEvents;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;
@Mod.EventBusSubscriber
public class FishEvent {
    @SubscribeEvent
    public static void Fish(ItemFishedEvent event)
    {
        ItemStack Fished = event.getDrops().get(0);
        event.getDrops().set(0,Fished);
        Player player = event.getEntity();
        Random r = new Random();
        int XpLevel = player.experienceLevel;
        CompoundTag data = player.getPersistentData();
        if(!data.contains("FishCount")) data.putInt("FishCount",1);
        else data.putInt("FishCount",data.getInt("FishCount")+1);
        int RodLevel = data.getInt("FishCount");
        ItemStack GoldCoin = Moditems.GoldCoin.get().getDefaultInstance();
        ItemStack SilverCoin = Moditems.SilverCoin.get().getDefaultInstance();
        ItemStack SeaSoul = Moditems.SeaSoul.get().getDefaultInstance();
        if(RodLevel > 2000){
            int rate = 7;
            if(r.nextInt(100) < rate) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,GoldCoin);
            }
            if(r.nextInt(100) < rate*2) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                player.addItem(SilverCoin);
            }
            if(r.nextInt(100) < rate) {
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(SeaSoul.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SeaSoul);
            }
            Compute.ExpPercentGetAndMSGSend(player,0.05,0,50);
        }
        else if(RodLevel > 1000){
            int rate = 6;
            if(r.nextInt(100) < rate) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));

                Compute.ItemStackGive(player,GoldCoin);
            }
            if(r.nextInt(100) < rate*2) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SilverCoin);
            }
            if(r.nextInt(100) < rate) {
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(SeaSoul.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SeaSoul);
            }
            Compute.ExpPercentGetAndMSGSend(player,0.05,0,40);
        }
        else if(RodLevel > 500){
            int rate = 5;
            if(r.nextInt(100) < rate) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,GoldCoin);
            }
            if(r.nextInt(100) < rate*2) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SilverCoin);
            }
            if(r.nextInt(100) < rate) {
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(SeaSoul.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SeaSoul);
            }
            Compute.ExpPercentGetAndMSGSend(player,0.05,0,30);
        }
        else if(RodLevel > 200){
            int rate = 4;
            if(r.nextInt(100) < rate) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,GoldCoin);
            }
            if(r.nextInt(100) < rate*2) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SilverCoin);
            }
            if(r.nextInt(100) < rate) {
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(SeaSoul.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SeaSoul);
            }
            Compute.ExpPercentGetAndMSGSend(player,0.05,0,20);
        }
        else if(RodLevel > 100){
            int rate = 3;
            if(r.nextInt(100) < rate) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));

                Compute.ItemStackGive(player,GoldCoin);
            }
            if(r.nextInt(100) < rate*2) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SilverCoin);
            }
            if(r.nextInt(100) < rate) {
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(SeaSoul.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SeaSoul);
            }
            Compute.ExpPercentGetAndMSGSend(player,0.05,0,15);
        }
        else if(RodLevel > 50){
            int rate = 2;
            if(r.nextInt(100) < rate) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));

                Compute.ItemStackGive(player,GoldCoin);
            }
            if(r.nextInt(100) < rate*2) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SilverCoin);
            }
            if(r.nextInt(100) < rate) {
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(SeaSoul.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SeaSoul);
            }
            Compute.ExpPercentGetAndMSGSend(player,0.05,0,10);
        }
        else if(RodLevel > 20){
            int rate = 1;
            if(r.nextInt(100) < rate) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(GoldCoin.getDisplayName()).append(Component.literal("*1")));

                Compute.ItemStackGive(player,GoldCoin);
            }
            if(r.nextInt(100) < rate*2) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SilverCoin);
            }
            if(r.nextInt(100) < rate) {
                Compute.FormatBroad(player.level(),Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal(player.getName().getString()+"通过钓鱼额外获得了:").append(SeaSoul.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SeaSoul);
            }
            Compute.ExpPercentGetAndMSGSend(player,0.05,0,5);
        }
        else{
            Compute.ExpPercentGetAndMSGSend(player,0.05,0,3);
            if(r.nextInt(100) < 1) {
                Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("你通过钓鱼额外获得了:").append(SilverCoin.getDisplayName()).append(Component.literal("*1")));
                Compute.ItemStackGive(player,SilverCoin);
            }
        }
        Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                Component.literal("当前钓鱼熟练度为: "+RodLevel));
        if(RodLevel > 1000){
            Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" "+(2000-RodLevel)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)).
                            append(Component.literal("熟练度。")));
        }
        else if(RodLevel > 500){
            Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" "+(1000-RodLevel)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)).
                            append(Component.literal("熟练度。")));        }
        else if(RodLevel > 200){
            Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" "+(500-RodLevel)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)).
                            append(Component.literal("熟练度。")));        }
        else if(RodLevel > 100){
            Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" "+(200-RodLevel)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)).
                            append(Component.literal("熟练度。")));        }
        else if(RodLevel > 50){
            Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" "+(100-RodLevel)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)).
                            append(Component.literal("熟练度。")));        }
        else if(RodLevel > 20){
            Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" "+(50-RodLevel)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)).
                            append(Component.literal("熟练度。")));        }
        else{
            Compute.FormatMSGSend(player,Component.literal("钓鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                    Component.literal("距离下一个钓鱼等阶，还需:").
                            append(Component.literal(" "+(20-RodLevel)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)).
                            append(Component.literal("熟练度。")));        }
    }
}
