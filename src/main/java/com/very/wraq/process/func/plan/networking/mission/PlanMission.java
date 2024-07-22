package com.very.wraq.process.func.plan.networking.mission;

import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlanMission {
    public static Map<String, ItemStack> planMissionContentMap = new HashMap<>();
    public static Map<String, Integer> planMissionContentCountMap = new HashMap<>();
    public static Map<String, String> planMissionStartTimeMap = new HashMap<>();

    public static Map<String, Integer> planMissionPunishLevelMap = new HashMap<>();
    public static Map<String, String> planMissionAllowRequestTimeMap = new HashMap<>();
}
