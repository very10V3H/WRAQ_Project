package com.very.wraq.process.system.lottery;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotteryScreenUtils {

    /* on serverSide */
    public static Map<String, Integer> thisTimeLotCount = new HashMap<>();
    public static Map<String, Integer> rewardSendTick = new HashMap<>();

    public static void tick(Player player) {

    }

    /* on clientSide */
    public static List<ItemStack> rewards = new ArrayList<>();

}
