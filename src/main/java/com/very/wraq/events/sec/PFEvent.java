package com.very.wraq.events.sec;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Mod.EventBusSubscriber
public class PFEvent {
    @SubscribeEvent
    public static void PF(TickEvent.PlayerTickEvent event) throws ParseException, IOException {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            CompoundTag data = player.getPersistentData();
            int TmpNum = player.tickCount;
            if (TmpNum % 20 == 0) // y
            {
                if (player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) &&
                        player.getX() > 213 && player.getX() < 215 &&
                        player.getZ() > 1073 && player.getZ() < 1075 && player.getY() == 102) {
                    Calendar cal = Calendar.getInstance();
                    if (data.contains("KillCountOfPlainZombie") && data.getInt("KillCountOfPlainZombie") >= 500
                            && data.contains("KillCountOfForestSkeleton") && data.contains("KillCountOfForestZombie")
                            && data.getInt("KillCountOfForestSkeleton") + data.getInt("KillCountOfForestZombie") >= 500) {
                        if (data.contains("PFDATE")) {
                            String DateString0 = data.getString("PFDATE");
                            SimpleDateFormat tmpDate0 = new SimpleDateFormat("yyyyMMddHHmmss");
                            Date date10 = tmpDate0.parse(DateString0);
                            Calendar cal0 = Calendar.getInstance();
                            cal0.setTime(date10); // cal0 = 字符串对应时间
                            cal0.add(Calendar.HOUR_OF_DAY, 6);
                            if (cal0.after(cal)) // 晚于系统时间 意即还未冷却完毕
                            {
                                Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                        withStyle(CustomStyle.styleOfHealth), Component.literal("副本暂未冷却完毕。"));
                                Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                        withStyle(CustomStyle.styleOfHealth), Component.literal("将在" + cal0.get(Calendar.YEAR) + "年" + (cal0.get(Calendar.MONTH) + 1) +
                                        "月" + cal0.get(Calendar.DAY_OF_MONTH) + "日" + cal0.get(Calendar.HOUR_OF_DAY) + "时" + cal0.get(Calendar.MINUTE) + "分" + cal0.get(Calendar.SECOND) + "可用。"));
                            } else {
                                if (Utils.PFController == 0) {
                                    Date date = cal.getTime();
                                    SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
                                    String DateString = tmpDate.format(date);
                                    data.putString("PFDATE", DateString);
                                    data.putInt("PFTIME", 120);
                                    Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                            withStyle(CustomStyle.styleOfHealth), Component.literal("已激活！现在你有两分钟的时间可以击杀副本怪物！"));
                                    Compute.formatBroad(player.level(), Component.literal("隐藏副本").
                                            withStyle(CustomStyle.styleOfHealth), Component.literal(player.getName().getString() + "已激活平原/森林隐藏副本！").withStyle(ChatFormatting.WHITE));
                                    Utils.PFController = 120;
                                    player.teleportTo(214.5, 81.5, 1080.5);
                                } else {
                                    Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                            withStyle(CustomStyle.styleOfHealth), Component.literal("副本已被激活，请等待其他玩家激活时间结束。"));
                                }
                            }
                        } else {
                            if (Utils.PFController == 0) {
                                Date date = cal.getTime();
                                SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
                                String DateString = tmpDate.format(date);
                                data.putString("PFDATE", DateString);
                                data.putInt("PFTIME", 120);
                                Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                        withStyle(CustomStyle.styleOfHealth), Component.literal("已激活！现在你有两分钟的时间可以击杀副本怪物！"));
                                Compute.formatBroad(player.level(), Component.literal("隐藏副本").
                                        withStyle(CustomStyle.styleOfHealth), Component.literal(player.getName().getString() + "已激活平原/森林隐藏副本！").withStyle(ChatFormatting.WHITE));
                                Utils.PFController = 120;
                                player.teleportTo(214.5, 81.5, 1080.5);
                            } else {
                                Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                        withStyle(CustomStyle.styleOfHealth), Component.literal("副本已被激活，请等待其他玩家激活时间结束。"));
                            }
                        }
                    } else {
                        Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                withStyle(CustomStyle.styleOfHealth), Component.literal("暂未达到副本激活需求。"));
                        if (data.contains("KillCountOfPlainZombie") && data.getInt("KillCountOfPlainZombie") < 500) {
                            Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                    withStyle(CustomStyle.styleOfHealth), Component.literal("平原僵尸").withStyle(ChatFormatting.GREEN).
                                    append(Component.literal("击杀数: ").withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("(" + data.getInt("KillCountOfPlainZombie") + "/" + 500 + ")"))));
                        }
                        if ((data.contains("KillCountOfForestSkeleton") && data.contains("KillCountOfForestZombie")
                                && data.getInt("KillCountOfForestZombie") + data.getInt("KillCountOfForestSkeleton") < 500)
                                || !data.contains("KillCountOfForestSkeleton") || !data.contains("KillCountOfForestZombie")) {
                            Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                    withStyle(CustomStyle.styleOfHealth), Component.literal("森林骷髅/僵尸").withStyle(ChatFormatting.DARK_GREEN).
                                    append(Component.literal("击杀数: ").withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("(" + (data.getInt("KillCountOfForestZombie") + data.getInt("KillCountOfForestSkeleton")) + "/" + 500 + ")"))));
                        }
                    }
                }
                if (data.contains("PFTIME")) {
                    if (data.getInt("PFTIME") > 0) data.putInt("PFTIME", data.getInt("PFTIME") - 1);
                    if (data.getInt("PFTIME") == 100) Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                            withStyle(CustomStyle.styleOfHealth), Component.literal("还剩下100s！"));
                    if (data.getInt("PFTIME") == 80) Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                            withStyle(CustomStyle.styleOfHealth), Component.literal("还剩下80s！"));
                    if (data.getInt("PFTIME") == 60) Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                            withStyle(CustomStyle.styleOfHealth), Component.literal("还剩下60s！"));
                    if (data.getInt("PFTIME") == 40) Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                            withStyle(CustomStyle.styleOfHealth), Component.literal("还剩下40s！"));
                    if (data.getInt("PFTIME") == 20) Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                            withStyle(CustomStyle.styleOfHealth), Component.literal("还剩下20s！"));
                    if (data.getInt("PFTIME") == 0) {
                        player.teleportTo(214, 102, 1074);
                        data.putInt("PFTIME", -1);

                        Compute.formatBroad(player.level(), Component.literal("隐藏副本").
                                withStyle(CustomStyle.styleOfHealth), Component.literal(player.getName().getString() + "在平原/森林隐藏副本激活期间，共击杀了").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("" + Utils.PFSecKillCount).withStyle(CustomStyle.styleOfHealth)).
                                append(Component.literal("只").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("隐秘生机残骸").withStyle(CustomStyle.styleOfHealth)));

                        ItemStack plainSoulBag = ModItems.PlainSoulBag.get().getDefaultInstance();
                        ItemStack forestSoulBag = ModItems.ForestSoulBag.get().getDefaultInstance();
                        int playerExpLevel = player.experienceLevel;
                        plainSoulBag.getOrCreateTagElement(Utils.MOD_ID).putInt(StringUtils.PlainSoulCount, (Utils.PFSecKillCount / 35 + 1) * playerExpLevel);
                        forestSoulBag.getOrCreateTagElement(Utils.MOD_ID).putInt(StringUtils.ForestSoulCount, (Utils.PFSecKillCount / 35 + 1) * playerExpLevel);
                        Compute.itemStackGive(player, forestSoulBag);
                        Compute.itemStackGive(player, plainSoulBag);
                        Utils.PFSecKillCount = 0;
                    }
                }
            }
        }
    }
}
