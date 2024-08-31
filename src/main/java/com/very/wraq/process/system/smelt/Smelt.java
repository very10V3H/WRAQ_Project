package com.very.wraq.process.system.smelt;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.common.Compute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Objects;

public class Smelt {

    public static CompoundTag clientData = new CompoundTag();

    private static final String smeltTagKey = "smeltTagKey";
    private static CompoundTag getPlayerSmeltTag(Player player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(smeltTagKey)) data.put(smeltTagKey, new CompoundTag());
        return data.getCompound(smeltTagKey);
    }

    private static final String maxSmeltSlotKey = "maxSmeltSlotKey";
    private static int getMaxSmeltSlot(Player player) {
        return getPlayerSmeltTag(player).getInt(maxSmeltSlotKey);
    }

    private static final String smeltSlotKeyPrefix = "smeltSlotKey_";
    private static String getSmeltSlotInfo(Player player, int slotIndex) {
        int maxSlot = getMaxSmeltSlot(player);
        if (slotIndex > maxSlot) return null;
        else return getPlayerSmeltTag(player).getString(smeltTagKey + slotIndex);
    }

    private static String getSmeltSlotInfoForClientSide(int slotIndex) {
        int maxSlot = clientData.getInt(maxSmeltSlotKey);
        if (slotIndex > maxSlot) return null;
        else return clientData.getString(smeltTagKey + slotIndex);
    }

    private static final String smeltSlotEmpty = "smeltSlotEmpty";
    private static boolean putSmeltSlotInfoToEmptySlot(Player player, String smeltSlotInfo) {
        int maxSlot = getMaxSmeltSlot(player);
        for (int i = 1 ; i <= maxSlot ; i ++) {
            if (Objects.equals(getSmeltSlotInfo(player, i), smeltSlotInfo)) {
                getPlayerSmeltTag(player).putString(smeltSlotKeyPrefix + i, smeltSlotInfo);
                return true;
            }
        }
        return false;
    }

    public static boolean putSmeltSlotInfoToEmptySlot(Player player, ItemStack itemStack, Calendar finishTime) {
        return putSmeltSlotInfoToEmptySlot(player, Compute.getItemStackString(itemStack) + Compute.CalendarToString(finishTime));
    }

    // smeltSlotInfo 形如: itemStack$calendar 包含了产物与完成时间
    private static ItemStack getSmeltItem(String smeltSlotInfo) throws CommandSyntaxException {
        if (!smeltSlotInfo.contains("$")) {
            return ItemStack.EMPTY;
        }
        String itemStackString = smeltSlotInfo.split("\\$")[0];
        return Compute.getItemFromString(itemStackString);
    }

    public static ItemStack getSmeltItem(Player player, int slotIndex) throws CommandSyntaxException {
        if (slotIndex > getMaxSmeltSlot(player)) {
            return ItemStack.EMPTY;
        }
        String info = getSmeltSlotInfo(player, slotIndex);
        if (info == null) return ItemStack.EMPTY;
        return getSmeltItem(info);
    }

    public static ItemStack getSmeltItemForClientSide(int slotIndex) throws CommandSyntaxException {
        if (slotIndex > clientData.getInt(maxSmeltSlotKey)) {
            return ItemStack.EMPTY;
        }
        String info = getSmeltSlotInfoForClientSide(slotIndex);
        if (info == null) return ItemStack.EMPTY;
        return getSmeltItem(info);
    }

    private static Calendar getSmeltFinishTime(String smeltSlotInfo) throws ParseException {
        if (!smeltSlotInfo.contains("$")) {
            return null;
        }
        String calendarString = smeltSlotInfo.split("\\$")[1];
        return Compute.StringToCalendar(calendarString);
    }

    public static Calendar getSmeltFinishTime(Player player, int slotIndex) throws ParseException {
        if (slotIndex > getMaxSmeltSlot(player)) {
            return null;
        }
        String info = getSmeltSlotInfo(player, slotIndex);
        if (info == null) return null;
        return getSmeltFinishTime(info);
    }

    public static Calendar getSmeltFinishTimeForClientSide(int slotIndex) throws ParseException {
        if (slotIndex > clientData.getInt(maxSmeltSlotKey)) {
            return null;
        }
        String info = getSmeltSlotInfoForClientSide(slotIndex);
        if (info == null) return null;
        return getSmeltFinishTime(info);
    }

    public static void checkFinishAndGiveItem(Player player, int slotIndex) throws ParseException, CommandSyntaxException {
        if (slotIndex > getMaxSmeltSlot(player)) return;
        Calendar finishTime = getSmeltFinishTime(player, slotIndex);
        Calendar current = Calendar.getInstance();
        if (current.after(finishTime)) {
            Compute.itemStackGive(player, getSmeltItem(player, slotIndex));
        }
    }

    public static void checkFinishAndGiveItemForAllSlot(Player player) throws ParseException, CommandSyntaxException {
        for (int i = 1 ; i <= getMaxSmeltSlot(player) ; i ++) {
            checkFinishAndGiveItem(player, i);
        }
    }
}
