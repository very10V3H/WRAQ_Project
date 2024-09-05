package com.very.wraq.process.system.smelt;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.common.Compute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
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
        else {
            CompoundTag data = getPlayerSmeltTag(player);
            if (!data.contains(smeltTagKey + slotIndex)) {
                return smeltSlotEmpty;
            }
            else {
                return getPlayerSmeltTag(player).getString(smeltTagKey + slotIndex);
            }
        }
    }

    private static void setSmeltSlotInfo(Player player, int slotIndex, String info) {
        int maxSlot = getMaxSmeltSlot(player);
        if (slotIndex <= maxSlot) {
            getPlayerSmeltTag(player).putString(smeltTagKey + slotIndex, info);
        }
    }

    private static String getSmeltSlotInfoForClientSide(int slotIndex) {
        int maxSlot = clientData.getInt(maxSmeltSlotKey);
        if (slotIndex > maxSlot) return null;
        else {
            if (!clientData.contains(smeltTagKey + slotIndex)) {
                return smeltSlotEmpty;
            }
            else {
                return clientData.getString(smeltTagKey + slotIndex);
            }
        }
    }

    // smeltSlotInfo 形如: 1$calendar 包含了配方下标与完成时间
    private static final String smeltSlotEmpty = "smeltSlotEmpty";
    private static boolean putSmeltSlotInfoToEmptySlot(Player player, String smeltSlotInfo) {
        int maxSlot = getMaxSmeltSlot(player);
        for (int i = 1 ; i <= maxSlot ; i ++) {
            if (Objects.equals(getSmeltSlotInfo(player, i), smeltSlotEmpty)) {
                getPlayerSmeltTag(player).putString(smeltSlotKeyPrefix + i, smeltSlotInfo);
                return true;
            }
        }
        return false;
    }

    public static boolean putSmeltSlotInfoToEmptySlot(Player player, int recipeIndex, Calendar finishTime) {
        return putSmeltSlotInfoToEmptySlot(player, recipeIndex + "$" + Compute.CalendarToString(finishTime));
    }

    private static List<ItemStack> getProductList(String smeltSlotInfo) throws CommandSyntaxException {
        if (!smeltSlotInfo.contains("$")) {
            return List.of();
        }
        String[] s = smeltSlotInfo.split("\\$");
        return SmeltRecipe.getRecipeByIndex(Integer.parseInt(s[0])).productList;
    }

    public static List<ItemStack> getProductList(Player player, int slotIndex) throws CommandSyntaxException {
        if (slotIndex > getMaxSmeltSlot(player)) {
            return List.of();
        }
        String info = getSmeltSlotInfo(player, slotIndex);
        if (Objects.equals(info, smeltSlotEmpty) || info == null) {
            return List.of();
        }
        return getProductList(info);
    }

    public static List<ItemStack> getSmeltItemForClientSide(int slotIndex) throws CommandSyntaxException {
        if (slotIndex > clientData.getInt(maxSmeltSlotKey)) {
            return List.of();
        }
        String info = getSmeltSlotInfoForClientSide(slotIndex);
        if (info == null || info.equals(smeltSlotEmpty)) {
            return List.of();
        }
        return getProductList(info);
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
        if (info == null || info.equals(smeltSlotEmpty)) return null;
        return getSmeltFinishTime(info);
    }

    public static Calendar getSmeltFinishTimeForClientSide(int slotIndex) throws ParseException {
        if (slotIndex > clientData.getInt(maxSmeltSlotKey)) {
            return null;
        }
        String info = getSmeltSlotInfoForClientSide(slotIndex);
        if (info == null || info.equals(smeltSlotEmpty)) return null;
        return getSmeltFinishTime(info);
    }

    public static void checkFinishAndGiveItem(Player player, int slotIndex) throws ParseException, CommandSyntaxException {
        if (slotIndex > getMaxSmeltSlot(player)) return;
        Calendar finishTime = getSmeltFinishTime(player, slotIndex);
        Calendar current = Calendar.getInstance();
        if (current.after(finishTime)) {
            getProductList(player, slotIndex).forEach(stack -> {
                Compute.itemStackGive(player, stack);
            });
            setSmeltSlotInfo(player, slotIndex, smeltSlotEmpty);
        }
    }

    public static void checkFinishAndGiveItemForAllSlot(Player player) throws ParseException, CommandSyntaxException {
        for (int i = 1 ; i <= getMaxSmeltSlot(player) ; i ++) {
            checkFinishAndGiveItem(player, i);
        }
    }
}
