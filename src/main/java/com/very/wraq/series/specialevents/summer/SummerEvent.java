package com.very.wraq.series.specialevents.summer;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.process.system.tower.Tower;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.specialevents.SpecialEventItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
        if (player.isSwimming() && player.getDeltaMovement().length() > 0.002 && player.tickCount % 20 == 17) {
            incrementIntData(player, dailySwimmingSecondsKey);
            incrementIntData(player, totalSwimmingSecondsKey);
            int dailySwimmingSeconds = getIntData(player, dailySwimmingSecondsKey);
            if (dailySwimmingSeconds % 15 >= 3) {
                Compute.sendActionBarMSG(player, Component.literal("正在摸鱼" + ".".repeat((dailySwimmingSeconds % 15) / 3)).withStyle(CustomStyle.styleOfWater));
            }
            reward(player);
        }
    }

    public static void reward(Player player) throws SQLException {
        int dailySwimmingSeconds = getIntData(player, dailySwimmingSecondsKey);
        if (dailySwimmingSeconds % 15 == 0) {
            Component timeFormat;
            if (dailySwimmingSeconds >= 600) {
                timeFormat = Component.literal(String.format("%.2f", dailySwimmingSeconds / 60.0) + "min").withStyle(CustomStyle.styleOfWater);
            } else {
                timeFormat = Component.literal(dailySwimmingSeconds + "s").withStyle(CustomStyle.styleOfWater);
            }
            Compute.sendActionBarMSG(player, Component.literal("今日摸鱼: ").withStyle(CustomStyle.styleOfWater).append(timeFormat));
            Random random = new Random();
            double index = random.nextDouble();
            boolean quitJudge = false;
            if (index < 0.01) {
                if (getIntData(player, dailySummerVoucherGetTimesKey) < 8) {
                    quitJudge = true;
                    incrementIntData(player, dailySummerVoucherGetTimesKey);
                    Compute.itemStackGive(player, new ItemStack(SpecialEventItems.SUMMER_VOUCHER.get()));
                } else index += 0.01;
            }

            if (!quitJudge && index < 0.02) {
                if (getIntData(player, dailyWorldSoul5GetTimesKey) < 8) {
                    quitJudge = true;
                    incrementIntData(player, dailyWorldSoul5GetTimesKey);
                    Tower.givePlayerStar(player, 5, "summer event");
                } else index += 0.01;
            }

            if (!quitJudge && index < 0.45) {
                if (getIntData(player, dailyExpGetTimesKey) < 8) {
                    quitJudge = true;
                    incrementIntData(player, dailyExpGetTimesKey);
                    Compute.ExpPercentGetIgnoreLimitAndMSGSend(player, 0.05, 0, player.experienceLevel);
                }
            }

            if (!quitJudge) {
                Compute.itemStackGive(player, new ItemStack(ModItems.SeaSoul.get(), 3));
            }
        }
    }

    public static void formatMSGSend(Player player, Component component) {
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

    public static void removeLastTimeEventUseKey(Player player) {
        CompoundTag data = player.getPersistentData();
        List<String> list = List.of(dailySummerVoucherGetTimesKey, dailyWorldSoul5GetTimesKey,
                dailyExpGetTimesKey, dailySwimmingSecondsKey);
        list.forEach(data::remove);
    }
}
