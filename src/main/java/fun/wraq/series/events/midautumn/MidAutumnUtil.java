package fun.wraq.series.events.midautumn;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.data.PersistentDataUtil;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MidAutumnUtil {
    public static Component getSuffix() {
        return Te.s("「中秋 - 月荧」", CustomStyle.styleOfMoon);
    }

    private static final String DATA_TOP_KEY = "mid_autumn_data";

    private static CompoundTag getData(Player player) {
        return PersistentDataUtil.getPlayerSpecificKeyCompoundTagData(player, DATA_TOP_KEY);
    }

    public static boolean isInActivityDate() {
        Calendar calendar = Calendar.getInstance();
        boolean is9_30 = calendar.get(Calendar.MONTH) == Calendar.SEPTEMBER
                && calendar.get(Calendar.DAY_OF_MONTH) == 30;
        boolean is10_1_8 = calendar.get(Calendar.MONTH) == Calendar.OCTOBER
                && calendar.get(Calendar.DAY_OF_MONTH) <= 8;
        return calendar.get(Calendar.YEAR) == 2025 && (is9_30 || is10_1_8);
    }

    private static final Style style = CustomStyle.styleOfMoon;

    private static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("中秋", CustomStyle.styleOfMoon), content);
    }

    private static final String SIGN_DAY = "sign_day_new";

    public static void onLoginReward(Player player) {
        if (!isInActivityDate()) {
            return;
        }
        if (player.experienceLevel < 150) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dataDay = getData(player).getInt(SIGN_DAY);
        if (dataDay == day) {
            return;
        }
        getData(player).putInt(SIGN_DAY, day);
        getRewards(month, day).forEach(itemStack -> {
            InventoryCheck.addOwnerTagToItemStack(player, itemStack);
            InventoryOperation.giveItemStackWithMSG(player, itemStack);
        });
        sendMSG(player, Te.s("中秋活动期间登录，每日可获取限定奖励!"));
        for (int i = 1; i <= 8; i++) {
            MutableComponent component = Te.s(" ".repeat(10), "10." + i, style, ": ");
            getRewards(Calendar.OCTOBER, i).forEach(itemStack -> {
                component.append(Te.s(itemStack,
                        " * " + itemStack.getCount(), CustomStyle.styleOfMoon, " "));
            });
            player.sendSystemMessage(component);
        }
    }

    private static List<ItemStack> getRewards(int month, int day) {
        Item supremeMooncake = SpecialEventItems.SUPREME_MOONCAKE.get();
        Item mooncake = SpecialEventItems.MOONCAKE.get();
        Item supplyBox1 = ModItems.SUPPLY_BOX_TIER_1.get();
        Item supplyBox2 = ModItems.SUPPLY_BOX_TIER_2.get();
        Item supplyBox3 = ModItems.SUPPLY_BOX_TIER_3.get();
        int october = Calendar.OCTOBER;
        if (month == 9 && day == 30) {
            return List.of(
                    new ItemStack(supremeMooncake, 2),
                    new ItemStack(mooncake, 15),
                    new ItemStack(SpecialEventItems.MOON_FEATHER_0.get()),
                    new ItemStack(supplyBox2)
            );
        } else if (month == october && day == 1) {
            return List.of(
                    new ItemStack(supremeMooncake, 4),
                    new ItemStack(mooncake, 10),
                    new ItemStack(supplyBox3)
            );
        } else if (month == october && day == 2) {
            return List.of(
                    new ItemStack(supremeMooncake, 2),
                    new ItemStack(mooncake, 15),
                    new ItemStack(SpecialEventItems.MID_AUTUMN_LETTER_CURIO_0.get())
            );
        } else if (month == october && day == 3) {
            return List.of(
                    new ItemStack(supremeMooncake, 4),
                    new ItemStack(mooncake, 10),
                    new ItemStack(supplyBox1)
            );
        } else if (month == october && day == 4) {
            return List.of(
                    new ItemStack(supremeMooncake, 2),
                    new ItemStack(mooncake, 15),
                    new ItemStack(supplyBox3)
            );
        } else if (month == october && day == 5) {
            return List.of(
                    new ItemStack(supremeMooncake, 4),
                    new ItemStack(mooncake, 10),
                    new ItemStack(supplyBox1)
            );
        } else if (month == october && day == 6) {
            return List.of(
                    new ItemStack(supremeMooncake, 10),
                    new ItemStack(SpecialEventItems.MID_AUTUMN_PREFIX.get()),
                    new ItemStack(SpecialEventItems.MID_AUTUMN_GEM.get()),
                    new ItemStack(SpecialEventItems.MANA_MOONCAKE.get())
            );
        } else if (month == october && day == 7) {
            return List.of(
                    new ItemStack(supremeMooncake, 2),
                    new ItemStack(mooncake, 15),
                    new ItemStack(supplyBox2)
            );
        } else if (month == october && day == 8) {
            return List.of(
                    new ItemStack(supremeMooncake, 4),
                    new ItemStack(mooncake, 10),
                    new ItemStack(supplyBox1)
            );
        }
        return List.of();
    }

    public static void onKillMob(Player player) {
        if (!isInActivityDate()) {
            return;
        }
        int dailyKillCount = Compute.getPlayerDailyKillCount(player);
        if (dailyKillCount <= 1000 && dailyKillCount % 50 == 0) {
            InventoryOperation.giveBoundingItemStack(player, new ItemStack(SpecialEventItems.OSMANTHUS.get()));
        }
        if (Utils.overworldIsNight) {
            Random random = new Random();
            if (random.nextDouble() < 0.005) {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(SpecialEventItems.OSMANTHUS.get()));
            }
        }
    }
}
