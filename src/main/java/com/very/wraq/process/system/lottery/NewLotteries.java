package com.very.wraq.process.system.lottery;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.files.dataBases.DataBase;
import com.very.wraq.process.func.item.InventoryOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewLotteries extends Item {

    public static Map<Item, Integer> getRewardSerial = new HashMap<>();

    public static void setGetRewardSerial() {
        getRewardSerial.put(ModItems.SwordLottery.get(), 2);
        getRewardSerial.put(ModItems.BowLottery.get(), 2);
        getRewardSerial.put(ModItems.SceptreLottery.get(), 2);
    }

    public static Map<Item, Integer> getGetRewardSerial() {
        if (getRewardSerial.isEmpty()) setGetRewardSerial();
        return getRewardSerial;
    }

    public static Map<Item, Integer> guaranteeTimes = new HashMap<>();

    public static void setGuaranteeTimes() {
        guaranteeTimes.put(ModItems.SwordLottery.get(), 90);
        guaranteeTimes.put(ModItems.BowLottery.get(), 90);
        guaranteeTimes.put(ModItems.SceptreLottery.get(), 90);
    }

    public static Map<Item, Double> guaranteeRange = new HashMap<>();

    public static void setGuaranteeRange() {
        guaranteeRange.put(ModItems.SwordLottery.get(), 0.01);
        guaranteeRange.put(ModItems.BowLottery.get(), 0.01);
        guaranteeRange.put(ModItems.SceptreLottery.get(), 0.01);
    }

    public record Loot(ItemStack itemStack, double rate) {
    }

    private final List<Loot> loots;

    public NewLotteries(Properties p_41383_, List<Loot> loots) {
        super(p_41383_);
        this.loots = loots;

        // 调整未满100%概率
        double frontRate = 0;
        for (int i = 0; i < loots.size() - 1; i++) frontRate += loots.get(i).rate;
        loots.set(loots.size() - 1, new Loot(loots.get(loots.size() - 1).itemStack, 1 - frontRate));
    }

    private int lootSerialNum(double range) {
        SecureRandom rand = new SecureRandom();
        double fullRate = rand.nextDouble(range);
        for (int i = 0; i < loots.size(); i++) {
            Loot loot = loots.get(i);
            fullRate -= loot.rate;
            if (fullRate <= 0) return i;
        }
        return loots.size() - 1;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(""));
        components.add(Component.literal(" 可能获得的奖励有:").withStyle(ChatFormatting.GOLD));
        Item item = itemStack.getItem();
        if (getRewardSerial.isEmpty()) setGetRewardSerial();
        if (guaranteeTimes.isEmpty()) setGuaranteeTimes();
        if (guaranteeRange.isEmpty()) setGuaranteeRange();
        for (int i = 0; i < loots.size(); i++) {
            Loot loot = loots.get(i);
            ChatFormatting style = ChatFormatting.AQUA;
            if (getRewardSerial.containsKey(item) && i < getRewardSerial.get(item)) style = ChatFormatting.RED;
            components.add(Component.literal(" " + (i + 1) + ".").withStyle(style).
                    append(loot.itemStack.getDisplayName()).
                    append(Component.literal(" *" + loot.itemStack.getCount()).withStyle(style)).
                    append(Component.literal(" 「" + String.format("%.2f%%", loot.rate * 100) + "」").withStyle(style)));
        }
        if (guaranteeTimes.containsKey(item)) {
            components.add(Component.literal(""));
            components.add(Component.literal(" 第" + guaranteeTimes.get(item) + "次必定抽取极品奖励").withStyle(ChatFormatting.GOLD));
        }
        super.appendHoverText(itemStack, p_41422_, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) {
            ItemStack mainHandStack = player.getItemInHand(interactionHand);
            Item lottery = mainHandStack.getItem();

            if (getRewardSerial.isEmpty()) setGetRewardSerial();
            if (guaranteeTimes.isEmpty()) setGuaranteeTimes();
            if (guaranteeRange.isEmpty()) setGuaranteeRange();

            boolean recordFlag = guaranteeTimes.containsKey(lottery);

            // 记录总开箱数

            try {
                if (recordFlag) addPlayerOpenLotteryTimes(player, lottery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            int times = 0;
            try {
                if (recordFlag) {
                    times = addPlayerRewardTimes(player, mainHandStack.getItem());
                    Compute.sendFormatMSG(player, Component.literal("礼盒").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("这是第").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("" + times).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                    append(Component.literal("次抽取该礼盒").withStyle(ChatFormatting.WHITE)));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Compute.playerItemUseWithRecord(player);

            double range = 1;

            // 概率递增与保底
            if (getRewardSerial.containsKey(lottery)) {
                range -= times * 0.005;
                if (times + 1 == guaranteeTimes.get(lottery)) range = guaranteeRange.get(lottery);
            }

            int serialNum = lootSerialNum(range);

            // 中签次数清零
            if (recordFlag && getRewardSerial.containsKey(lottery)) {
                if (serialNum < getRewardSerial.get(lottery)) {
                    setPlayerRewardTimes(player, lottery, 0);
                    try {
                        addPlayerLotteryWinTimes(player, lottery);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            // 提供奖励
            Loot loot = loots.get(serialNum);
            ItemStack itemStack = loot.itemStack;
            if (loot.rate <= 0.05) {
                Compute.formatBroad(level, Component.literal("礼盒").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 通过 ").withStyle(ChatFormatting.WHITE)).
                                append(this.getDefaultInstance().getDisplayName()).
                                append(Component.literal(" 获得了 ").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()).
                                append(Component.literal(" *" + itemStack.getCount()).withStyle(ChatFormatting.AQUA)).
                                append(Component.literal(guaranteeRange.containsKey(lottery) ? " (" + times + ")" : "").withStyle(ChatFormatting.GRAY)));
            }

            ItemStack reward = new ItemStack(itemStack.getItem(), itemStack.getCount());
            if (InventoryCheck.boundingList.contains(reward.getItem()))
                InventoryCheck.addOwnerTagToItemStack(player, reward); // 为部分物品添加绑定tag
            InventoryOperation.itemStackGive(player, reward);
        }
        return super.use(level, player, interactionHand);
    }

    public static Map<String, Map<String, Integer>> playerLotteryData = new HashMap<>();

    public static Map<String, Integer> getPlayerLotteryData(String name) {
        if (!playerLotteryData.containsKey(name)) playerLotteryData.put(name, new HashMap<>());
        return playerLotteryData.get(name);
    }

    public static int addPlayerRewardTimes(Player player, Item item) throws SQLException {
        String playerName = player.getName().getString();
        if (!playerLotteryData.containsKey(playerName)) playerLotteryData.put(playerName, new HashMap<>());
        Map<String, Integer> rewards = playerLotteryData.get(playerName);
        int times = getPlayerRewardTimes(player, item);
        rewards.put(item.toString(), times + 1);
        return times + 1;
    }

    public static void setPlayerRewardTimes(Player player, Item item, int times) {
        String playerName = player.getName().getString();
        if (!playerLotteryData.containsKey(playerName)) playerLotteryData.put(playerName, new HashMap<>());
        Map<String, Integer> rewards = playerLotteryData.get(playerName);
        rewards.put(item.toString(), times);
    }

    public static int getPlayerRewardTimes(Player player, Item item) throws SQLException {
        String playerName = player.getName().getString();
        if (playerLotteryData.containsKey(playerName)) {
            Map<String, Integer> map = playerLotteryData.get(playerName);
            if (map.containsKey(item.toString())) return map.get(item.toString());
        }
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        String times = DataBase.get(statement, player, item.toString());
        int result = 0;
        if (times != null) {
            if (!playerLotteryData.containsKey(playerName)) playerLotteryData.put(playerName, new HashMap<>());
            Map<String, Integer> map = playerLotteryData.get(playerName);
            map.put(item.toString(), Integer.valueOf(times));
            result = Integer.parseInt(times);
        }
        statement.close();

        return result;
    }

    public static int addPlayerOpenLotteryTimes(Player player, Item item) throws SQLException {
        String playerName = player.getName().getString();
        String openTimes = "_openTimes";
        String key = item.toString() + openTimes;
        if (!playerLotteryData.containsKey(playerName)) playerLotteryData.put(playerName, new HashMap<>());
        Map<String, Integer> rewards = playerLotteryData.get(playerName);
        int times = getPlayerOpenLotteryTimes(player, item);
        rewards.put(key, times + 1);
        return times + 1;
    }

    public static int getPlayerOpenLotteryTimes(Player player, Item item) throws SQLException {
        String playerName = player.getName().getString();
        String openTimes = "_openTimes";
        String key = item.toString() + openTimes;
        if (playerLotteryData.containsKey(playerName)) {
            Map<String, Integer> map = playerLotteryData.get(playerName);
            if (map.containsKey(key)) return map.get(key);
        }
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        String times = DataBase.get(statement, player, key);
        int result = 0;
        if (times != null) {
            if (!playerLotteryData.containsKey(playerName)) playerLotteryData.put(playerName, new HashMap<>());
            Map<String, Integer> map = playerLotteryData.get(playerName);
            map.put(key, Integer.valueOf(times));
            result = Integer.parseInt(times);
        }
        statement.close();

        return result;
    }

    public static int addPlayerLotteryWinTimes(Player player, Item item) throws SQLException {
        String playerName = player.getName().getString();
        String winTimes = "_winTimes";
        String key = item.toString() + winTimes;
        if (!playerLotteryData.containsKey(playerName)) playerLotteryData.put(playerName, new HashMap<>());
        Map<String, Integer> rewards = playerLotteryData.get(playerName);
        int times = getPlayerLotteryWinTimes(player, item);
        rewards.put(key, times + 1);
        return times + 1;
    }

    public static int getPlayerLotteryWinTimes(Player player, Item item) throws SQLException {
        String playerName = player.getName().getString();
        String winTimes = "_winTimes";
        String key = item.toString() + winTimes;
        if (playerLotteryData.containsKey(playerName)) {
            Map<String, Integer> map = playerLotteryData.get(playerName);
            if (map.containsKey(key)) return map.get(key);
        }
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        String times = DataBase.get(statement, player, key);
        int result = 0;
        if (times != null) {
            if (!playerLotteryData.containsKey(playerName)) playerLotteryData.put(playerName, new HashMap<>());
            Map<String, Integer> map = playerLotteryData.get(playerName);
            map.put(key, Integer.valueOf(times));
            result = Integer.parseInt(times);
        }
        statement.close();

        return result;
    }

    public static void writeToDataBase() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        playerLotteryData.forEach((playerName, map) -> {
            map.forEach((s, integer) -> {
                try {
                    DataBase.put(statement, playerName, s, String.valueOf(integer));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        statement.close();

    }

    public static void writeToDataBase(Statement statement) throws SQLException {
        playerLotteryData.forEach((playerName, map) -> {
            map.forEach((s, integer) -> {
                try {
                    DataBase.put(statement, playerName, s, String.valueOf(integer));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    public static void synchronizedWrite(Statement statement) throws SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    writeToDataBase(statement);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
