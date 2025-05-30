package fun.wraq.series.events.dragonboat;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.files.MarketItemInfo;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.util.Calendar;

public class DragonBoatFes {
    // 5.30 ~ 6.6
    public static boolean isInActivate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == 2025
                && ((calendar.get(Calendar.MONTH) == Calendar.MAY && calendar.get(Calendar.DAY_OF_MONTH) >= 30)
                || (calendar.get(Calendar.MONTH) == Calendar.JUNE && calendar.get(Calendar.DAY_OF_MONTH) <= 6));
    }

    public static void handleOnMobSpawn(Mob mob) {
        if (!isInActivate()) {
            return;
        }
        if (mob instanceof Zombie || mob instanceof Skeleton) {
            int mobXpLevel = MobSpawn.getMobXpLevel(mob);
            double rate = 0.01 * (1 + mobXpLevel / 100d);
            if (RandomUtils.nextDouble(0, 1) < rate) {
                mob.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(SpecialEventItems.ZONG_LEAF.get()));
                mob.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(SpecialEventItems.ZONG_LEAF.get()));
            }
            if (RandomUtils.nextDouble(0, 1) < rate * 0.01) {
                mob.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(SpecialEventItems.GOLDEN_ZONG_LEAF.get()));
                mob.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(SpecialEventItems.GOLDEN_ZONG_LEAF.get()));
            }
        }
    }

    public static void onKillMob(Mob mob, Player player) {
        if (!isInActivate()) {
            return;
        }
        if (mob instanceof Zombie || mob instanceof Skeleton) {
            if (mob.getItemInHand(InteractionHand.OFF_HAND).is(SpecialEventItems.ZONG_LEAF.get())) {
                ItemAndRate.send(player, new ItemStack(SpecialEventItems.ZONG_LEAF.get()));
            }
            if (mob.getItemInHand(InteractionHand.OFF_HAND).is(SpecialEventItems.GOLDEN_ZONG_LEAF.get())) {
                ItemAndRate.send(player, new ItemStack(SpecialEventItems.GOLDEN_ZONG_LEAF.get()));
            }
        }
    }

    public static void onLogin(Player player) {
        if (!isInActivate()) {
            return;
        }
        sendMSG(player, Te.s("端午活动进行中!!!(5.30 - 6.6 = 7days)", CustomStyle.styleOfWorld));
        player.sendSystemMessage(Te.s(" ".repeat(16), "1 -> 击杀带有", SpecialEventItems.ZONG_LEAF.get(),
                "或", SpecialEventItems.GOLDEN_ZONG_LEAF.get(), "的僵尸(包含尸壳/溺尸)以及骷髅，获得",
                SpecialEventItems.ZONG_LEAF.get(), "与", SpecialEventItems.GOLDEN_ZONG_LEAF.get(), "!"));
        player.sendSystemMessage(Te.s(" ".repeat(16), "2 -> 前往", "玉林小饭店烹饪", CustomStyle.MUSHROOM_STYLE,
                SpecialEventItems.SWEET_ZONG_ZI.get(), "/", SpecialEventItems.GOLDEN_ZONG_ZI.get(),
                "，找到", "粽子大王", CustomStyle.DRAGON_BOAT_FES_STYLE, "兑换活动限定物品!"));
        player.sendSystemMessage(Te.s(" ".repeat(16), "3 -> 活动期间，",
                "传送中枢", CustomStyle.styleOfEnd, "可无消耗使用!"));
        player.sendSystemMessage(Te.s(" ".repeat(16), "4 -> 活动期间，", "可以使用vp直接购买因子."));
    }

    public static int lastHour = 0;
    public static void handleServerTick() {
        if (!isInActivate()) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        if (lastHour != calendar.get(Calendar.HOUR_OF_DAY) &&
                (calendar.get(Calendar.HOUR_OF_DAY) == 11 || calendar.get(Calendar.HOUR_OF_DAY) == 17)) {
            lastHour = calendar.get(Calendar.HOUR_OF_DAY);
            for (int i = 0 ; i < 5 ; i ++) {
                ItemStack itemStack = new ItemStack(SpecialEventItems.ZONG_LEAF.get(), 4);
                Utils.marketItemInfos.add(new MarketItemInfo("very_H", itemStack, 10000, 0));
            }
            for (int i = 0 ; i < 2 ; i ++) {
                ItemStack itemStack = new ItemStack(SpecialEventItems.GOLDEN_ZONG_LEAF.get());
                Utils.marketItemInfos.add(new MarketItemInfo("very_H", itemStack, 20, 1));
            }
            Compute.formatBroad(Te.s("端午", CustomStyle.DRAGON_BOAT_FES_STYLE),
                    Te.s("铁头在全球市场上架了一些", SpecialEventItems.ZONG_LEAF.get(),
                            "与", SpecialEventItems.GOLDEN_ZONG_LEAF.get(), "去看看吧!"));
        }
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("端午", CustomStyle.DRAGON_BOAT_FES_STYLE), content);
    }
}
