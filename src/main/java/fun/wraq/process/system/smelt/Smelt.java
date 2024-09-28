package fun.wraq.process.system.smelt;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.smelt.SmeltDataS2CPacket;
import fun.wraq.process.system.smelt.SmeltRecipe;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Smelt {

    public static CompoundTag clientData = new CompoundTag();

    private static final String smeltTagKey = "smeltTagKey";
    public static CompoundTag getPlayerSmeltTag(Player player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(smeltTagKey)) {
            data.put(smeltTagKey, new CompoundTag());
            data.getCompound(smeltTagKey).putInt(maxSmeltSlotKey, 5);
        }
        return data.getCompound(smeltTagKey);
    }

    private static final String maxSmeltSlotKey = "maxSmeltSlotKey";
    private static int getMaxSmeltSlot(Player player) {
        return getPlayerSmeltTag(player).getInt(maxSmeltSlotKey);
    }

    public static void setMaxSmeltSlot(Player player, int slot) {
        getPlayerSmeltTag(player).putInt(maxSmeltSlotKey, slot);
    }

    private static final String smeltSlotKeyPrefix = "smeltSlotKey_";
    private static String getSlotInfo(Player player, int slotIndex) {
        int maxSlot = getMaxSmeltSlot(player);
        if (slotIndex > maxSlot) return null;
        else {
            CompoundTag data = getPlayerSmeltTag(player);
            if (!data.contains(smeltSlotKeyPrefix + slotIndex)) {
                return smeltSlotEmpty;
            }
            else {
                return getPlayerSmeltTag(player).getString(smeltSlotKeyPrefix + slotIndex);
            }
        }
    }

    private static void setSlotInfo(Player player, int slotIndex, String info) {
        int maxSlot = getMaxSmeltSlot(player);
        if (slotIndex <= maxSlot) {
            getPlayerSmeltTag(player).putString(smeltSlotKeyPrefix + slotIndex, info);
        }
    }

    private static String getSlotInfoForClientSide(int slotIndex) {
        int maxSlot = clientData.getInt(maxSmeltSlotKey);
        if (slotIndex > maxSlot) return null;
        else {
            if (!clientData.contains(smeltSlotKeyPrefix + slotIndex)) {
                return smeltSlotEmpty;
            }
            else {
                return clientData.getString(smeltSlotKeyPrefix + slotIndex);
            }
        }
    }

    // smeltSlotInfo 形如: 1$calendar 包含了配方下标与完成时间
    private static final String smeltSlotEmpty = "smeltSlotEmpty";
    private static boolean putSlotInfoToEmptySlot(Player player, String smeltSlotInfo) {
        int maxSlot = getMaxSmeltSlot(player);
        for (int i = 1 ; i <= maxSlot ; i ++) {
            if (Objects.equals(getSlotInfo(player, i), smeltSlotEmpty)) {
                getPlayerSmeltTag(player).putString(smeltSlotKeyPrefix + i, smeltSlotInfo);
                return true;
            }
        }
        return false;
    }

    private static String buildSlotInfoString(int recipeIndex, Calendar finishTime) {
        return recipeIndex + "$" + Compute.CalendarToString(finishTime);
    }

    public static boolean putSlotInfoToEmptySlot(Player player, int recipeIndex, Calendar finishTime) {
        return putSlotInfoToEmptySlot(player, buildSlotInfoString(recipeIndex, finishTime));
    }

    private static SmeltRecipe getRecipeBySlotInfo(String smeltSlotInfo) {
        if (!smeltSlotInfo.contains("$")) {
            return null;
        }
        return SmeltRecipe.getRecipeByIndex(Integer.parseInt(smeltSlotInfo.split("\\$")[0]));
    }

    private static List<ItemStack> getProductListBySlotInfo(String smeltSlotInfo) throws CommandSyntaxException {
        if (!smeltSlotInfo.contains("$")) {
            return List.of();
        }
        String[] s = smeltSlotInfo.split("\\$");
        return SmeltRecipe.getRecipeByIndex(Integer.parseInt(s[0])).productList;
    }

    public static List<ItemStack> getProductListBySlotIndex(Player player, int slotIndex) throws CommandSyntaxException {
        if (slotIndex > getMaxSmeltSlot(player)) {
            return List.of();
        }
        String info = getSlotInfo(player, slotIndex);
        if (Objects.equals(info, smeltSlotEmpty) || info == null) {
            return List.of();
        }
        return getProductListBySlotInfo(info);
    }

    private static List<ItemAndRate> getExProductListBySlotInfo(String smeltSlotInfo) throws CommandSyntaxException {
        if (!smeltSlotInfo.contains("$")) {
            return List.of();
        }
        String[] s = smeltSlotInfo.split("\\$");
        return SmeltRecipe.getRecipeByIndex(Integer.parseInt(s[0])).exProductList;
    }

    public static List<ItemAndRate> getExProductListBySlotIndex(Player player, int slotIndex) throws CommandSyntaxException {
        if (slotIndex > getMaxSmeltSlot(player)) {
            return List.of();
        }
        String info = getSlotInfo(player, slotIndex);
        if (Objects.equals(info, smeltSlotEmpty) || info == null) {
            return List.of();
        }
        return getExProductListBySlotInfo(info);
    }

    public static List<ItemStack> getItemForClientSide(int slotIndex) throws CommandSyntaxException {
        if (slotIndex > clientData.getInt(maxSmeltSlotKey)) {
            return List.of();
        }
        String info = getSlotInfoForClientSide(slotIndex);
        if (info == null || info.equals(smeltSlotEmpty)) {
            return List.of();
        }
        return getProductListBySlotInfo(info);
    }

    private static int getRecipeIndexBySlotInfo(String smeltSlotInfo) {
        if (!smeltSlotInfo.contains("$")) {
            return -1;
        }
        String recipeIndexString = smeltSlotInfo.split("\\$")[0];
        return Integer.parseInt(recipeIndexString);
    }

    private static Calendar getFinishTimeBySlotInfo(String smeltSlotInfo) throws ParseException {
        if (!smeltSlotInfo.contains("$")) {
            return null;
        }
        String calendarString = smeltSlotInfo.split("\\$")[1];
        return Compute.StringToCalendar(calendarString);
    }

    public static Calendar getFinishTimeBySlotIndex(Player player, int slotIndex) throws ParseException {
        if (slotIndex > getMaxSmeltSlot(player)) {
            return null;
        }
        String info = getSlotInfo(player, slotIndex);
        if (info == null || info.equals(smeltSlotEmpty)) return null;
        return getFinishTimeBySlotInfo(info);
    }

    public static Calendar getFinishTimeForClientSide(int slotIndex) throws ParseException {
        if (slotIndex > clientData.getInt(maxSmeltSlotKey)) {
            return null;
        }
        String info = getSlotInfoForClientSide(slotIndex);
        if (info == null || info.equals(smeltSlotEmpty)) return null;
        return getFinishTimeBySlotInfo(info);
    }

    public static void checkFinishAndGiveItem(Player player, int slotIndex) throws ParseException, CommandSyntaxException {
        if (slotIndex > getMaxSmeltSlot(player)) return;
        Calendar finishTime = getFinishTimeBySlotIndex(player, slotIndex);
        Calendar current = Calendar.getInstance();
        if (current.after(finishTime)) {
            getProductListBySlotIndex(player, slotIndex).forEach(stack -> {
                InventoryOperation.itemStackGive(player, new ItemStack(stack.getItem(), stack.getCount()));
            });
            getExProductListBySlotIndex(player, slotIndex).forEach(itemAndRate -> {
                itemAndRate.giveByNewObject(player);
            });
            setSlotInfo(player, slotIndex, smeltSlotEmpty);
        } else {
            // 还未到时间

        }
    }

    public static void checkFinishAndGiveItemForAllSlot(Player player) throws ParseException, CommandSyntaxException {
        for (int i = 1 ; i <= getMaxSmeltSlot(player) ; i ++) {
            checkFinishAndGiveItem(player, i);
        }
    }

    public static List<SmeltRecipe> getRecipeByTag(CompoundTag data) {
        List<SmeltRecipe> recipes = new ArrayList<>();
        int maxSlot = data.getInt(maxSmeltSlotKey);
        for (int i = 1 ; i <= maxSlot ; i ++) {
            if (getRecipeBySlotInfo(data.getString(smeltSlotKeyPrefix + i)) != null) {
                recipes.add(SmeltRecipe.getRecipeByIndex(Integer.parseInt(data.getString(smeltSlotKeyPrefix + i).split("\\$")[0])));
            } else {
                recipes.add(null);
            }
        }
        return recipes;
    }

    public static List<Calendar> getProgressFinishTimeByTag(CompoundTag data) throws ParseException {
        List<Calendar> times = new ArrayList<>();
        int maxSlot = data.getInt(maxSmeltSlotKey);
        for (int i = 1 ; i <= maxSlot ; i ++) {
            if (getFinishTimeBySlotInfo(data.getString(smeltSlotKeyPrefix + i)) != null) {
                times.add(getFinishTimeBySlotInfo(data.getString(smeltSlotKeyPrefix + i)));
            } else {
                times.add(null);
            }
        }
        return times;
    }

    public static void fastenSmeltProgressBySlotIndex(Player player, int slotIndex, int minutes) throws ParseException {
        String smeltSlotInfo = getSlotInfo(player, slotIndex);
        if (smeltSlotInfo != null && !smeltSlotInfo.equals(smeltSlotEmpty)) {
            Calendar finishTime = getFinishTimeBySlotIndex(player, slotIndex);
            finishTime.add(Calendar.MINUTE, -minutes);
            setSlotInfo(player, slotIndex,
                    buildSlotInfoString(getRecipeIndexBySlotInfo(smeltSlotInfo), finishTime));
        }
    }

    public static void fastenAllSmeltProgress(Player player, int minutes) throws ParseException {
        for (int i = 1 ; i <= getMaxSmeltSlot(player) ; i ++) {
            fastenSmeltProgressBySlotIndex(player, i, minutes);
        }
        checkIfAnyProgressFinished(player);
    }

    public static void checkIfAnyProgressFinished(Player player) throws ParseException {
        int finishedCount = 0;
        for (int i = 1 ; i <= getMaxSmeltSlot(player) ; i ++) {
            String slotInfo = getSlotInfo(player, i);
            if (slotInfo != null && !slotInfo.equals(smeltSlotEmpty)) {
                Calendar finishTime = getFinishTimeBySlotInfo(slotInfo);
                Calendar calendar = Calendar.getInstance();
                if (calendar.after(finishTime)) finishedCount ++;
            }
        }
        if (finishedCount > 0) {
            sendFormatMSG(player, Te.m("你有")
                    .append(Te.m(String.valueOf(finishedCount), ChatFormatting.GOLD))
                    .append(Te.m("个已完成的炼造配方，前往炼造台查看")));
        }
    }

    public static void sendFormatMSG(Player player, Component component) {
        Compute.sendFormatMSG(player, Component.literal("炼造").withStyle(CustomStyle.styleOfGold), component);
    }

    public static void sendDataToClient(ServerPlayer serverPlayer) {
        ModNetworking.sendToClient(new SmeltDataS2CPacket(getPlayerSmeltTag(serverPlayer)), serverPlayer);
    }
}
