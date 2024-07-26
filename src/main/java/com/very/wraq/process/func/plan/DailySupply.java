package com.very.wraq.process.func.plan;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.process.func.plan.networking.DailySupplyS2CPacket;
import com.very.wraq.process.func.plan.networking.PlanDateAndTierS2CPacket;
import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DailySupply {

    public static Map<String, Integer> tryCooldown = new HashMap<>();

    public static boolean canReward(Player player) throws ParseException {
        String name = player.getName().getString();
        if (PlanPlayer.map.containsKey(name)) {
            PlanPlayer planPlayer = PlanPlayer.map.get(name);
            Calendar cal = Calendar.getInstance();
            if (planPlayer.lastRewardTime == null) return true;
            Calendar lastRewardTime = Compute.StringToCalendar(planPlayer.lastRewardTime);
            return lastRewardTime.get(Calendar.DATE) != cal.get(Calendar.DATE);
        } else {
            PlanPlayer planPlayer = new PlanPlayer(name, 0, null, null, 0);
            PlanPlayer.map.put(name, planPlayer);
            return true;
        }
    }

    public static void tryToReward(Player player) throws SQLException, ParseException, IOException {
        String name = player.getName().getString();
        int tick = player.getServer().getTickCount();
        if (tryCooldown.containsKey(name)) {
            if (tryCooldown.get(name) > tick) {
                Compute.sendFormatMSG(player, Component.literal("每日补给").withStyle(ChatFormatting.AQUA),
                        Component.literal("不要点太快喔").withStyle(ChatFormatting.WHITE));
                return;
            }
        }
        tryCooldown.put(name, tick + 40);
        if (canReward(player)) {
            PlanPlayer planPlayer = PlanPlayer.map.get(name);
            planPlayer.getDailyRewardTimes++;
            planPlayer.lastRewardTime = Compute.CalendarToString(Calendar.getInstance());
            Int2ObjectMap<ItemStack> map = getRewardItemMap();
            Compute.sendFormatMSG(player, Component.literal("每日补给").withStyle(ChatFormatting.AQUA),
                    Component.literal("成功领取了每日补给").withStyle(ChatFormatting.WHITE));
            ItemStack mapItemStack = map.getOrDefault(PlanPlayer.getPlayerTier(player), new ItemStack(Items.AIR));
            ItemStack giveItemStack = new ItemStack(mapItemStack.getItem(), mapItemStack.getCount());
            Compute.itemStackGive(player, giveItemStack);
            sendStatusToClient(player);
        } else {
            Compute.sendFormatMSG(player, Component.literal("每日补给").withStyle(ChatFormatting.AQUA),
                    Component.literal("似乎已经领取过今天的补给了呢").withStyle(ChatFormatting.WHITE));
        }
    }

    public static Int2ObjectMap<ItemStack> rewardItemMap = new Int2ObjectOpenHashMap<>();

    public static Int2ObjectMap<ItemStack> getRewardItemMap() {
        if (rewardItemMap.isEmpty()) {
            rewardItemMap.put(0, new ItemStack(ModItems.notePaper.get(), 64));
            rewardItemMap.put(1, new ItemStack(ModItems.supplyBoxTier1.get()));
            rewardItemMap.put(2, new ItemStack(ModItems.supplyBoxTier2.get()));
            rewardItemMap.put(3, new ItemStack(ModItems.supplyBoxTier3.get()));
        }
        return rewardItemMap;
    }

    public static int clientSupplyStatus = -1;

    public static void sendStatusToClient(Player player) throws ParseException {
        if (canReward(player)) {
            ModNetworking.sendToClient(new DailySupplyS2CPacket(PlanPlayer.getPlayerTier(player)), (ServerPlayer) player);
        } else ModNetworking.sendToClient(new DailySupplyS2CPacket(-1), (ServerPlayer) player);
        int tier = PlanPlayer.getPlayerTier(player);
        String name = player.getName().getString();
        if (tier != 0 && PlanPlayer.map.containsKey(name)) {
            Calendar calendar = Calendar.getInstance();
            Calendar overDate = Compute.StringToCalendar(PlanPlayer.map.get(name).overDate);
            int date = (int) Compute.calenderDateDifference(overDate, calendar);
            ModNetworking.sendToClient(new PlanDateAndTierS2CPacket(date, tier), (ServerPlayer) player);
        }
    }
}
