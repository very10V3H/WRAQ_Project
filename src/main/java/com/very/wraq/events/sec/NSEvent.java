package com.very.wraq.events.sec;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
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
public class NSEvent {
    @SubscribeEvent
    public static void NS(TickEvent.PlayerTickEvent event) throws ParseException {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            ServerPlayer serverPlayer = (ServerPlayer) player;
            CompoundTag data = player.getPersistentData();
            int TmpNum = player.tickCount;
            if (TmpNum % 20 == 0) // 下界副本
            {
                if (player.level().equals(player.getServer().getLevel(Level.NETHER)) &&
                        player.getX() > 27 && player.getX() < 30 &&
                        player.getZ() > 266 && player.getZ() < 269 && player.getY() == 32) {
                    Calendar cal = Calendar.getInstance();
                    if (data.contains("KillCountOfWitherSkeleton") && data.getInt("KillCountOfWitherSkeleton") > 333
                            && data.contains("KillCountOfZombiePigLin") && data.getInt("KillCountOfZombiePigLin") > 333
                            && data.contains("KillCountOfNetherSkeleton") && data.getInt("KillCountOfNetherSkeleton") > 333
                            && data.contains("KillCountOfNetherMagma") && data.getInt("KillCountOfNetherMagma") > 333) {
                        if (data.contains("NSDATE")) {
                            String DateString0 = data.getString("NSDATE");
                            SimpleDateFormat tmpDate0 = new SimpleDateFormat("yyyyMMddHHmmss");
                            Date date10 = tmpDate0.parse(DateString0);
                            Calendar cal0 = Calendar.getInstance();
                            cal0.setTime(date10); // cal0 = 字符串对应时间
                            cal0.add(Calendar.HOUR_OF_DAY, 12);
                            if (cal0.after(cal)) // 晚于系统时间 意即还未冷却完毕
                            {
                                Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                        withStyle(CustomStyle.styleOfNether), Component.literal("副本暂未冷却完毕。"));
                                Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                        withStyle(CustomStyle.styleOfNether), Component.literal("将在" + cal0.get(Calendar.YEAR) + "年" + (cal0.get(Calendar.MONTH) + 1) +
                                        "月" + cal0.get(Calendar.DAY_OF_MONTH) + "日" + cal0.get(Calendar.HOUR_OF_DAY) + "时" + cal0.get(Calendar.MINUTE) + "分" + cal0.get(Calendar.SECOND) + "可用。"));
                            } else {
                                if (Utils.NSController == -1) {
                                    if (!Utils.NSPlayerController.contains(player))
                                        Utils.NSPlayerController.add(player);
                                    if (data.contains("NSConfirm") && !data.getBoolean("NSConfirm")) {
                                        Compute.sendFormatMSG(player, Component.literal("隐藏副本").withStyle(CustomStyle.styleOfNether),
                                                Component.literal("有" + Utils.NSPlayerController.size() + "名玩家正在准备激活该副本:").withStyle(ChatFormatting.WHITE));
                                        String PlayerList = "";
                                        for (Player player1 : Utils.NSPlayerController) {
                                            PlayerList += player1.getName().getString() + ",";
                                        }
                                        PlayerList = PlayerList.substring(0, PlayerList.length() - 1);
                                        Compute.sendFormatMSG(player, Component.literal("隐藏副本").withStyle(CustomStyle.styleOfNether),
                                                Component.literal("Ta们是:" + PlayerList).withStyle(ChatFormatting.WHITE));
                                        Compute.sendFormatMSG(player, Component.literal("隐藏副本").withStyle(CustomStyle.styleOfNether),
                                                Component.literal("输入/vmd confirm确认激活副本").withStyle(ChatFormatting.WHITE));

                                    }
                                    /*                                    Date date = cal.getTime();
                                    SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
                                    String DateString = tmpDate.format(date);
                                    data.putString("NSDATE",DateString);
                                    data.putInt("NSTIME",120);
                                    Compute.FormatMSGSend(player,Component.literal("隐藏副本").
                                            withStyle(ChatFormatting.YELLOW),Component.literal("已激活！现在你有两分钟的时间可以击杀副本怪物！"));
                                    Compute.FormatBroad(player.level,Component.literal("隐藏副本").
                                            withStyle(ChatFormatting.YELLOW),Component.literal(player.getName().getString()+"已激活火山隐藏副本！").withStyle(ChatFormatting.WHITE));
                                    Utils.NSController = 120;
                                    player.moveTo(-18,-43,889);*/
                                } else {
                                    Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                            withStyle(CustomStyle.styleOfNether), Component.literal("副本已被激活，请等待其他玩家激活时间结束。").withStyle(ChatFormatting.WHITE));
                                }
                            }
                        } else {
                            if (Utils.NSController == -1) {
                                if (!Utils.NSPlayerController.contains(player)) Utils.NSPlayerController.add(player);
                                if (data.contains("NSConfirm") && !data.getBoolean("NSConfirm")) {
                                    Compute.sendFormatMSG(player, Component.literal("隐藏副本").withStyle(CustomStyle.styleOfNether),
                                            Component.literal("有" + Utils.NSPlayerController.size() + "名玩家正在准备激活该副本:").withStyle(ChatFormatting.WHITE));
                                    String PlayerList = "";
                                    for (Player player1 : Utils.NSPlayerController) {
                                        PlayerList += player1.getName().getString() + ",";
                                    }
                                    if (PlayerList.length() != 0) {
                                        PlayerList = PlayerList.substring(0, PlayerList.length() - 1);
                                    }
                                    Compute.sendFormatMSG(player, Component.literal("隐藏副本").withStyle(CustomStyle.styleOfNether),
                                            Component.literal("Ta们是:" + PlayerList).withStyle(ChatFormatting.WHITE));
                                    Compute.sendFormatMSG(player, Component.literal("隐藏副本").withStyle(CustomStyle.styleOfNether),
                                            Component.literal("输入/vmd confirm确认激活副本").withStyle(ChatFormatting.WHITE));

                                }/*                                Date date = cal.getTime();
                                SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
                                String DateString = tmpDate.format(date);
                                data.putString("NSDATE",DateString);
                                data.putInt("NSTIME",120);
                                Compute.FormatMSGSend(player,Component.literal("隐藏副本").
                                        withStyle(ChatFormatting.YELLOW),Component.literal("已激活！现在你有两分钟的时间可以击杀副本怪物！"));
                                Compute.FormatBroad(player.level,Component.literal("隐藏副本").
                                        withStyle(ChatFormatting.YELLOW),Component.literal(player.getName().getString()+"已激活火山隐藏副本！").withStyle(ChatFormatting.WHITE));
                                Utils.NSController = 120;
                                player.moveTo(-18,-43,889);*/
                            } else {
                                Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                        withStyle(CustomStyle.styleOfNether), Component.literal("副本已被激活，请等待其他玩家激活时间结束。").withStyle(ChatFormatting.WHITE));
                            }
                        }
                    } else {
                        Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                withStyle(CustomStyle.styleOfNether), Component.literal("暂未达到副本激活需求。"));
                        if (data.contains("KillCountOfWitherSkeleton") && data.getInt("KillCountOfWitherSkeleton") < 333) {
                            Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                    withStyle(CustomStyle.styleOfNether), Component.literal("下界凋零骷髅").withStyle(CustomStyle.styleOfNether).
                                    append(Component.literal("击杀数: ").withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("(" + data.getInt("KillCountOfWitherSkeleton") + "/" + 333 + ")"))));
                        }
                        if (data.contains("KillCountOfZombiePigLin") && data.getInt("KillCountOfZombiePigLin") < 333) {
                            Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                    withStyle(CustomStyle.styleOfNether), Component.literal("下界猪灵").withStyle(CustomStyle.styleOfNether).
                                    append(Component.literal("击杀数: ").withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("(" + data.getInt("KillCountOfZombiePigLin") + "/" + 333 + ")"))));
                        }
                        if (data.contains("KillCountOfNetherSkeleton") && data.getInt("KillCountOfNetherSkeleton") < 333) {
                            Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                    withStyle(CustomStyle.styleOfNether), Component.literal("下界遗骸").withStyle(CustomStyle.styleOfNether).
                                    append(Component.literal("击杀数: ").withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("(" + data.getInt("KillCountOfNetherSkeleton") + "/" + 333 + ")"))));
                        }
                        if (data.contains("KillCountOfNetherMagma") && data.getInt("KillCountOfNetherMagma") < 333) {
                            Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                    withStyle(CustomStyle.styleOfNether), Component.literal("下界熔岩能量聚合体").withStyle(CustomStyle.styleOfNether).
                                    append(Component.literal("击杀数: ").withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("(" + data.getInt("KillCountOfNetherMagma") + "/" + 333 + ")"))));
                        }
                    }
                }
            }
        }
    }
}
