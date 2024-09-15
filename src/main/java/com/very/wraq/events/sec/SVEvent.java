package com.very.wraq.events.sec;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.item.InventoryOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
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
public class SVEvent {
    @SubscribeEvent
    public static void SV(TickEvent.PlayerTickEvent event) throws ParseException, IOException {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            ServerPlayer serverPlayer = (ServerPlayer) player;
            CompoundTag data = player.getPersistentData();
            int TmpNum = player.tickCount;
            if (TmpNum % 20 == 0) // 隐藏副本：火山
            {
                if (player.level().equals(player.getServer().getLevel(Level.OVERWORLD)) &&
                        player.getX() > 4 && player.getX() < 6 &&
                        player.getZ() > 989 && player.getZ() < 991 && player.getY() == -54) {
                    Calendar cal = Calendar.getInstance();
                    if (data.contains("KillCountOfVolcanoBlazw") && data.getInt("KillCountOfVolcanoBlazw") > 500) {
                        if (data.contains("SVDATE")) {
                            String DateString0 = data.getString("SVDATE");
                            SimpleDateFormat tmpDate0 = new SimpleDateFormat("yyyyMMddHHmmss");
                            Date date10 = tmpDate0.parse(DateString0);
                            Calendar cal0 = Calendar.getInstance();
                            cal0.setTime(date10); // cal0 = 字符串对应时间
                            cal0.add(Calendar.HOUR_OF_DAY, 6);
                            if (cal0.after(cal)) // 晚于系统时间 意即还未冷却完毕
                            {
                                Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                        withStyle(ChatFormatting.YELLOW), Component.literal("副本暂未冷却完毕。"));
                                Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                        withStyle(ChatFormatting.YELLOW), Component.literal("将在" + cal0.get(Calendar.YEAR) + "年" + (cal0.get(Calendar.MONTH) + 1) +
                                        "月" + cal0.get(Calendar.DAY_OF_MONTH) + "日" + cal0.get(Calendar.HOUR_OF_DAY) + "时" + cal0.get(Calendar.MINUTE) + "分" + cal0.get(Calendar.SECOND) + "可用。"));
                            } else {
                                if (Utils.SVController == 0) {
                                    Date date = cal.getTime();
                                    SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
                                    String DateString = tmpDate.format(date);
                                    data.putString("SVDATE", DateString);
                                    data.putInt("SVTIME", 120);
                                    Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                            withStyle(ChatFormatting.YELLOW), Component.literal("已激活！现在你有两分钟的时间可以击杀副本怪物！"));
                                    Compute.formatBroad(player.level(), Component.literal("隐藏副本").
                                            withStyle(ChatFormatting.YELLOW), Component.literal(player.getName().getString() + "已激活火山隐藏副本！").withStyle(ChatFormatting.WHITE));
                                    Utils.SVController = 30;
                                    player.moveTo(-18, -43, 889);
                                } else {
                                    Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                            withStyle(ChatFormatting.YELLOW), Component.literal("副本已被激活，请等待其他玩家激活时间结束。"));
                                }
                            }
                        } else {
                            if (Utils.SVController == 0) {
                                Date date = cal.getTime();
                                SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
                                String DateString = tmpDate.format(date);
                                data.putString("SVDATE", DateString);
                                data.putInt("SVTIME", 120);
                                Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                        withStyle(ChatFormatting.YELLOW), Component.literal("已激活！现在你有两分钟的时间可以击杀副本怪物！"));
                                Compute.formatBroad(player.level(), Component.literal("隐藏副本").
                                        withStyle(ChatFormatting.YELLOW), Component.literal(player.getName().getString() + "已激活火山隐藏副本！").withStyle(ChatFormatting.WHITE));
                                Utils.SVController = 30;
                                player.moveTo(-18, -43, 889);
                            } else {
                                Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                        withStyle(ChatFormatting.YELLOW), Component.literal("副本已被激活，请等待其他玩家激活时间结束。"));
                            }
                        }
                    } else {
                        Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                withStyle(ChatFormatting.YELLOW), Component.literal("暂未达到副本激活需求。"));
                        if (data.contains("KillCountOfVolcanoBlazw") && data.getInt("KillCountOfVolcanoBlazw") < 500) {
                            Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                                    withStyle(ChatFormatting.YELLOW), Component.literal("火山烈焰").withStyle(ChatFormatting.YELLOW).
                                    append(Component.literal("击杀数: ").withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("(" + data.getInt("KillCountOfVolcanoBlazw") + "/" + 500 + ")"))));
                        }
                    }
                }
                if (data.contains("SVTIME")) {
                    if (data.getInt("SVTIME") > 0) data.putInt("SVTIME", data.getInt("SVTIME") - 1);
                    if (data.getInt("SVTIME") == 100) Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                            withStyle(ChatFormatting.YELLOW), Component.literal("还剩下100s！"));
                    if (data.getInt("SVTIME") == 80) Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                            withStyle(ChatFormatting.YELLOW), Component.literal("还剩下80s！"));
                    if (data.getInt("SVTIME") == 60) Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                            withStyle(ChatFormatting.YELLOW), Component.literal("还剩下60s！"));
                    if (data.getInt("SVTIME") == 40) Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                            withStyle(ChatFormatting.YELLOW), Component.literal("还剩下40s！"));
                    if (data.getInt("SVTIME") == 20) Compute.sendFormatMSG(player, Component.literal("隐藏副本").
                            withStyle(ChatFormatting.YELLOW), Component.literal("还剩下20s！"));
                    if (data.getInt("SVTIME") == 0) {
                        player.teleportTo(5, -54, 990);
                        data.putInt("SVTIME", -1);
                        Compute.formatBroad(player.level(), Component.literal("隐藏副本").
                                withStyle(ChatFormatting.YELLOW), Component.literal(player.getName().getString() + "在火山隐藏副本激活期间，共击杀了").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("" + Utils.VolcanoSecKillCount).withStyle(ChatFormatting.YELLOW)).
                                append(Component.literal("只").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("隐秘熔岩能源").withStyle(ChatFormatting.YELLOW)));

                        ItemStack volcanoSoulBag = ModItems.VolcanoSoulBag.get().getDefaultInstance();
                        int playerExpLevel = player.experienceLevel;
                        volcanoSoulBag.getOrCreateTagElement(Utils.MOD_ID).putInt(StringUtils.VolcanoSoulCount, (Utils.VolcanoSecKillCount / 35 + 1) * playerExpLevel);
                        InventoryOperation.itemStackGive(player, volcanoSoulBag);
                        Utils.VolcanoSecKillCount = 0;
                    }
                }
            }
        }
    }
}
