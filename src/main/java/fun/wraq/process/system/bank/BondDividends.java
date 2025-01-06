package fun.wraq.process.system.bank;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class BondDividends {

    public static final String DAILY_BOND_DIVIDENDS_KEY = "DailyBondReward";
    public static boolean allowGetDividends(Player player) {
        return !Bank.getBankData(player).getBoolean(DAILY_BOND_DIVIDENDS_KEY);
    }

    public static void setAllowGetDividends(Player player, boolean allow) {
        Bank.getBankData(player).putBoolean(DAILY_BOND_DIVIDENDS_KEY, !allow);
    }

    public static void tryToGetDividends(Player player) {
        if (!allowGetDividends(player)) {
            sendMSG(player, Te.s("今天已经收取过分红了! 明天再来试试吧。"));
            return;
        }
        Item bondItem = ModItems.BOND.get();
        int count = InventoryOperation.itemStackCount(player, bondItem);
        sendMSG(player, Te.s("当前背包中共有", count + "张", ChatFormatting.AQUA, bondItem));
        sendMSG(player, Te.c(
                Te.s("「领取", String.format("%.1f", count * 0.1) + "GB", CustomStyle.styleOfGold, "」")
                , "/vmd bank getDividends"
                , Te.s("点击以领取每日", "债券分红", CustomStyle.styleOfWorld)));
    }

    public static void getDividends(Player player) {
        Item bondItem = ModItems.BOND.get();
        int count = InventoryOperation.itemStackCount(player, bondItem);
        setAllowGetDividends(player, false);
        Bank.incomeGB(player, count * 0.1);
        sendMSG(player, Te.s("成功领取了每日", "债券分红", CustomStyle.styleOfWorld));
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("债券", CustomStyle.styleOfWorld), content);
    }
}
