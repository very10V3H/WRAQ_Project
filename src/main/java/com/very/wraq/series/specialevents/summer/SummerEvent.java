package com.very.wraq.series.specialevents.summer;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.process.system.tower.Tower;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.specialevents.SpecialEventItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class SummerEvent {

    public static String dailySummerVoucherGetTimesKey = "dailySummerVoucherGetTimes";
    public static String dailyWorldSoul5GetTimesKey = "dailyWorldSoul5GetTimes";
    public static String dailyExpGetTimesKey = "dailyExpGetTimes";
    public static String dailySwimmingSecondsKey = "dailySwimmingSeconds";
    public static String totalSwimmingSecondsKey = "totalSwimmingSeconds";

    public static void tick(Player player) throws SQLException {
        if (player.isSwimming() && player.tickCount % 20 == 17) {
            incrementIntData(player, dailySwimmingSecondsKey);
            incrementIntData(player, totalSwimmingSecondsKey);
            int dailySwimmingSeconds = getIntData(player, dailySwimmingSecondsKey);
            if (dailySwimmingSeconds % 15 >= 3) {
                if (dailySwimmingSeconds % 3 == 0) Compute.soundToPlayer(player, SoundEvents.FISH_SWIM);
                Compute.sendActionBarMSG(player, Component.literal("正在摸鱼" + ".".repeat((dailySwimmingSeconds % 15) / 3)).withStyle(CustomStyle.styleOfWater));
            }
            reward(player);
        }
    }

    public static void reward(Player player) throws SQLException {
        int dailySwimmingSeconds = getIntData(player, dailySwimmingSecondsKey);
        if (dailySwimmingSeconds % 15 == 0) {
            Compute.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
            Component timeFormat;
            if (dailySwimmingSeconds >= 600) {
                timeFormat = Component.literal(String.format("%.2f", dailySwimmingSeconds / 60.0) + "min").withStyle(CustomStyle.styleOfWater);
                if (dailySwimmingSeconds % 600 == 0) {
                    Compute.formatBroad(player.level(), Component.literal("暑期活动").withStyle(CustomStyle.styleOfPower),
                            Component.literal("").withStyle(ChatFormatting.WHITE).
                                    append(player.getDisplayName()).
                                    append(Component.literal(" 今日").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal("摸鱼时间").withStyle(CustomStyle.styleOfWater)).
                                    append(Component.literal("达到了: ").withStyle(ChatFormatting.WHITE)).
                                    append(timeFormat));
                }
            } else {
                timeFormat = Component.literal(dailySwimmingSeconds + "s").withStyle(CustomStyle.styleOfWater);
            }
            Compute.sendActionBarMSG(player, Component.literal("今日摸鱼: ").withStyle(CustomStyle.styleOfWater).append(timeFormat));
            Random random = new Random();
            double index = random.nextDouble();
            boolean quitJudge = false;
            if (index < 0.01) {
                int summerVoucherGetTimesKey = getIntData(player, dailySummerVoucherGetTimesKey);
                if (summerVoucherGetTimesKey < 8) {
                    quitJudge = true;
                    incrementIntData(player, dailySummerVoucherGetTimesKey);
                    Compute.itemStackGive(player, new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get()));
                    sendFormatMSG(player, Component.literal("今日还能获得").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.valueOf(8 - summerVoucherGetTimesKey) + "次").withStyle(CustomStyle.styleOfWater)).
                            append(SpecialEventItems.SUMMER_VOUCHER.get().getDefaultInstance().getDisplayName()));
                } else index += 0.01;
            }

            if (!quitJudge && index < 0.02) {
                int dailyWorldSoul5GetTimes = getIntData(player, dailyWorldSoul5GetTimesKey);
                if (dailyWorldSoul5GetTimes < 8) {
                    quitJudge = true;
                    incrementIntData(player, dailyWorldSoul5GetTimesKey);
                    Tower.givePlayerStar(player, 5, "summer event");
                    sendFormatMSG(player, Component.literal("今日还能获得").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.valueOf(8 - dailyWorldSoul5GetTimes) + "次").withStyle(CustomStyle.styleOfWater)).
                            append(ModItems.worldSoul5.get().getDefaultInstance().getDisplayName()));
                } else index += 0.01;
            }

            if (!quitJudge && index < 0.45) {
                int dailyExpGetTimes = getIntData(player, dailyExpGetTimesKey);
                if (dailyExpGetTimes < 20) {
                    quitJudge = true;
                    incrementIntData(player, dailyExpGetTimesKey);
                    Compute.ExpPercentGetIgnoreLimitAndMSGSend(player, 0.05, 0, player.experienceLevel);
                    sendFormatMSG(player, Component.literal("今日还能获得").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.valueOf(20 - dailyExpGetTimes) + "次").withStyle(CustomStyle.styleOfWater)).
                            append(Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE)));
                }
            }

            if (!quitJudge) {
                Compute.itemStackGive(player, new ItemStack(ModItems.SeaSoul.get(), 3));
            }
        }
    }

    public static void sendFormatMSG(Player player, Component component) {
        Compute.formatMSGSend(player, Component.literal("暑期活动").withStyle(CustomStyle.styleOfPower), component);
    }

    public static int getIntData(Player player, String key) {
        CompoundTag data = player.getPersistentData();
        return data.getInt(key);
    }

    public static void incrementIntData(Player player, String key) {
        CompoundTag data = player.getPersistentData();
        data.putInt(key, data.getInt(key) + 1);
    }

    public static void resetDailyData(Player player) {
        CompoundTag data = player.getPersistentData();
        data.putInt(dailySummerVoucherGetTimesKey, 0);
        data.putInt(dailyWorldSoul5GetTimesKey, 0);
        data.putInt(dailyExpGetTimesKey, 0);
        data.putInt(dailySwimmingSecondsKey, 0);
    }

    public static void removeLastTimeEventUseKey(Player player) {
        CompoundTag data = player.getPersistentData();
        List<String> list = List.of(dailySummerVoucherGetTimesKey, dailyWorldSoul5GetTimesKey,
                dailyExpGetTimesKey, dailySwimmingSecondsKey);
        list.forEach(data::remove);
    }
}