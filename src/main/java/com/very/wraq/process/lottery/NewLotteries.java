package com.very.wraq.process.lottery;

import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.files.dataBases.DataBase;
import com.very.wraq.valueAndTools.Compute;
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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NewLotteries extends Item {

    public record Loot(ItemStack itemStack, double rate) {}
    private final List<Loot> loots;
    public NewLotteries(Properties p_41383_, List<Loot> loots) {
        super(p_41383_);
        this.loots = loots;

        // 调整未满100%概率
        double frontRate = 0;
        for (int i = 0 ; i < loots.size() - 1 ; i++) frontRate += loots.get(i).rate;
        loots.set(loots.size() - 1, new Loot(loots.get(loots.size() - 1).itemStack, 1 - frontRate));
    }

    private int lootSerialNum() {
        Random rand = new Random();
        double fullRate = rand.nextDouble();
        for (int i = 0 ; i < loots.size() ; i++) {
            Loot loot = loots.get(i);
            fullRate -= loot.rate;
            if (fullRate <= 0) return i;
        }
        return loots.size() - 1;
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(" 奖池:").withStyle(ChatFormatting.LIGHT_PURPLE));
        for (int i = 0 ; i < loots.size() ; i++) {
            Loot loot = loots.get(i);
            components.add(Component.literal(" " + (i + 1) + ".").withStyle(ChatFormatting.AQUA).
                    append(loot.itemStack.getDisplayName()).
                    append(Component.literal(" *" + loot.itemStack.getCount()).withStyle(ChatFormatting.AQUA)).
                    append(Component.literal(" 「" + String.format("%.2f%%", loot.rate * 100) + "」").withStyle(ChatFormatting.LIGHT_PURPLE)));
        }
        super.appendHoverText(itemStack, p_41422_, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) {
            ItemStack mainHandStack = player.getItemInHand(interactionHand);
            int times = 0;
            try {
                times = addPlayerRewardTimes(player, mainHandStack.getItem());
                Compute.FormatMSGSend(player, Component.literal("礼盒").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("这是第").withStyle(ChatFormatting.WHITE).
                                append(Component.literal("" + times).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                append(Component.literal("次抽取该礼盒").withStyle(ChatFormatting.WHITE)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                Compute.PlayerItemUseWithRecord(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int serialNum = lootSerialNum();
            Loot loot = loots.get(serialNum);
            ItemStack itemStack = loot.itemStack;
            if (loot.rate <= 0.05) {
                Compute.FormatBroad(level, Component.literal("礼盒").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 通过 ").withStyle(ChatFormatting.WHITE)).
                                append(this.getDefaultInstance().getDisplayName()).
                                append(Component.literal(" 获得了 ").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()).
                                append(Component.literal(" *" + itemStack.getCount()).withStyle(ChatFormatting.AQUA)));
            }

            ItemStack reward = new ItemStack(itemStack.getItem(), itemStack.getCount());
            InventoryCheck.addOwnerTagToItemStack(player, reward); // 为部分物品添加绑定tag

            try {
                Compute.ItemStackGive(player, reward);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public static Map<Player, Map<String, Integer>> playerLotteryData = new HashMap<>();

    public static int addPlayerRewardTimes(Player player, Item item) throws SQLException {
        if (!playerLotteryData.containsKey(player)) playerLotteryData.put(player, new HashMap<>());
        Map<String, Integer> rewards = playerLotteryData.get(player);
        int times = getPlayerRewardTimes(player, item);
        rewards.put(item.toString(), times + 1);
        return times + 1;
    }

    public static int getPlayerRewardTimes(Player player, Item item) throws SQLException {
        if (playerLotteryData.containsKey(player)) {
            Map<String, Integer> map = playerLotteryData.get(player);
            if (map.containsKey(item.toString())) return map.get(item.toString());
        }
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        String times = DataBase.get(statement, player, item.toString());
        int result = 0;
        if (times != null) {
            if (!playerLotteryData.containsKey(player)) playerLotteryData.put(player, new HashMap<>());
            Map<String, Integer> map = playerLotteryData.get(player);
            map.put(item.toString(), Integer.valueOf(times));
            result = Integer.parseInt(times);
        }
        statement.close();
        connection.close();
        return result;
    }

    public static void writeToDataBase() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        playerLotteryData.forEach((player, map) -> {
            map.forEach((s, integer) -> {
                try {
                    DataBase.put(statement, player.getName().getString(), s, String.valueOf(integer));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        statement.close();
        connection.close();
    }
}
