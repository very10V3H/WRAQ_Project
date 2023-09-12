package com.Very.very.Events.SecEvents;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Mod.EventBusSubscriber
public class SLEvent {
    @SubscribeEvent
    public static void SL(TickEvent.PlayerTickEvent event) throws ParseException {
        if(event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            ServerPlayer serverPlayer = (ServerPlayer) player;
            CompoundTag data = player.getPersistentData();
            int TmpNum = player.tickCount;
            if(TmpNum % 20 == 0) // 隐藏副本：湖泊
            {
                if(player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) &&
                        player.getX() > 65 && player.getX() < 67 &&
                        player.getZ() > 981 && player.getZ() < 983 && player.getY() == -24)
                {
                    Calendar cal = Calendar.getInstance();
                    if(data.contains("KillCountOfLakeDrowned") && data.getInt("KillCountOfLakeDrowned") > 1000){
                        if(data.contains("SLDATE"))
                        {
                            String DateString0 = data.getString("SLDATE");
                            SimpleDateFormat tmpDate0 = new SimpleDateFormat("yyyyMMddHHmmss");
                            Date date10 = tmpDate0.parse(DateString0);
                            Calendar cal0 = Calendar.getInstance();
                            cal0.setTime(date10); // cal0 = 字符串对应时间
                            cal0.add(Calendar.HOUR_OF_DAY,6);
                            if(cal0.after(cal)) // 晚于系统时间 意即还未冷却完毕
                            {
                                Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                        withStyle(ChatFormatting.BLUE),Component.literal("副本暂未冷却完毕。"));
                                Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                        withStyle(ChatFormatting.BLUE),Component.literal("将在"+cal0.get(Calendar.YEAR)+"年"+(cal0.get(Calendar.MONTH)+1)+
                                        "月"+cal0.get(Calendar.DAY_OF_MONTH)+"日"+cal0.get(Calendar.HOUR_OF_DAY)+"时"+cal0.get(Calendar.MINUTE)+"分"+cal0.get(Calendar.SECOND)+"可用。"));
                            }
                            else
                            {
                                if (Utils.SLController == 0) {
                                    Date date = cal.getTime();
                                    SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
                                    String DateString = tmpDate.format(date);
                                    data.putString("SLDATE",DateString);
                                    data.putInt("SLTIME",120);
                                    Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                            withStyle(ChatFormatting.BLUE),Component.literal("已激活！现在你有两分钟的时间可以击杀副本怪物！"));
                                    Compute.FormatBroad(player.level(),Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                            withStyle(ChatFormatting.BLUE),Component.literal(player.getName().getString()+"已激活湖泊隐藏副本！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                                    Utils.SLController = 40;
                                    player.moveTo(54,-51,1000);
                                }
                                else {
                                    Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                            withStyle(ChatFormatting.BLUE),Component.literal("副本已被激活，请等待其他玩家激活时间结束。"));
                                }
                            }
                        }
                        else
                        {
                            if (Utils.SLController == 0) {
                                Date date = cal.getTime();
                                SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
                                String DateString = tmpDate.format(date);
                                data.putString("SLDATE",DateString);
                                data.putInt("SLTIME",120);
                                Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                        withStyle(ChatFormatting.BLUE),Component.literal("已激活！现在你有两分钟的时间可以击杀副本怪物！"));
                                Compute.FormatBroad(player.level(),Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                        withStyle(ChatFormatting.BLUE),Component.literal(player.getName().getString()+"已激活湖泊隐藏副本！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                                Utils.SLController = 40;
                                player.moveTo(54,-51,1000);
                            }
                            else {
                                Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                        withStyle(ChatFormatting.BLUE),Component.literal("副本已被激活，请等待其他玩家激活时间结束。"));
                            }
                        }
                    }
                    else
                    {
                        Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                withStyle(ChatFormatting.BLUE),Component.literal("暂未达到副本激活需求。"));
                        if(data.contains("KillCountOfLakeDrowned") && data.getInt("KillCountOfLakeDrowned") < 1000){
                            Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                    withStyle(ChatFormatting.BLUE),Component.literal("湖泊守卫者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE).
                                    append(Component.literal("击杀数: ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("("+data.getInt("KillCountOfLakeDrowned")+"/"+1000+")"))));
                        }
                    }
                }
                if(data.contains("SLTIME"))
                {
                    if(data.getInt("SLTIME") > 0) data.putInt("SLTIME",data.getInt("SLTIME")-1);
                    if(data.getInt("SLTIME") == 100) Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                            withStyle(ChatFormatting.BLUE),Component.literal("还剩下100s！"));
                    if(data.getInt("SLTIME") == 80) Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                            withStyle(ChatFormatting.BLUE),Component.literal("还剩下80s！"));
                    if(data.getInt("SLTIME") == 60) Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                            withStyle(ChatFormatting.BLUE),Component.literal("还剩下60s！"));
                    if(data.getInt("SLTIME") == 40) Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                            withStyle(ChatFormatting.BLUE),Component.literal("还剩下40s！"));
                    if(data.getInt("SLTIME") == 20) Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                            withStyle(ChatFormatting.BLUE),Component.literal("还剩下20s！"));
                    if(data.getInt("SLTIME") == 0) {
                        player.teleportTo(66,-24,982);
                        data.putInt("SLTIME",-1);
                        Compute.FormatBroad(player.level(),Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                withStyle(ChatFormatting.BLUE),Component.literal(player.getName().getString()+"在湖泊隐藏副本激活期间，共击杀了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal(""+data.getInt("KillCountOfSLC")).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)).
                                append(Component.literal("只").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("被遗忘的探索者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
                        data.putInt("KillCountOfSLC",0);
                    }
                }
            }
        }
    }
}
