package fun.wraq.process.system.bank;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Bank {
    public static final String BANK_DATA_KEY = "BankData";
    public static CompoundTag getBankData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, BANK_DATA_KEY);
    }

    public static final String GB_VALUE_DATA_KEY = "GBValue";
    public static double getGBValue(Player player) {
        return getBankData(player).getDouble(GB_VALUE_DATA_KEY);
    }

    public static void setGBValue(Player player, double value) {
        getBankData(player).putDouble(GB_VALUE_DATA_KEY, value);
    }

    public static void incomeGB(Player player, double incomeValue) {
        double currentGB = getGBValue(player);
        double afterIncomeGB = currentGB + incomeValue;
        setGBValue(player, afterIncomeGB);
        sendMSG(player, Te.s(" + ", ChatFormatting.GREEN,
                String.format("%.1f", incomeValue) + " GB", CustomStyle.styleOfGold,
                " (", String.format("%.1f", afterIncomeGB), CustomStyle.styleOfGold, ")"));
    }

    public static void expenseGB(Player player, double expenseValue) {
        double currentGB = getGBValue(player);
        double afterIncomeGB = currentGB - expenseValue;
        setGBValue(player, afterIncomeGB);
        sendMSG(player, Te.s(" - ", ChatFormatting.RED,
                String.format("%.1f", expenseValue) + " GB", CustomStyle.styleOfGold,
                " (", String.format("%.1f", afterIncomeGB), CustomStyle.styleOfGold, ")"));
    }

    public static void onPlayerInteractWithVillager(Player player) {
        sendMSG(player, Te.s("请问您需要办理什么业务？", player));
        Compute.sendBlankLine(player, 3);
        player.sendSystemMessage(Te.s(" ".repeat(4),
                Te.c(Te.s("「债券分红」", CustomStyle.styleOfGold),
                        "/vmd bank tryToGetDividends",
                        Te.s("点击以尝试收取每日债券分红")),
                " ".repeat(4),
                Te.c(Te.s("「取出金豆」", CustomStyle.styleOfGold),
                        "vmd bank tryToGetGoldenBeans",
                        Te.s("点击以尝试取出金豆"))));
        Compute.sendBlankLine(player, 4);
        MySound.soundToNearPlayer(player, SoundEvents.VILLAGER_AMBIENT);
    }

    public static void tryToGetGoldenBeans(Player player) {
        sendMSG(player, Te.s("您的账户当前拥有", String.format("%.1f", getGBValue(player))));
        Compute.sendBlankLine(player, 3);
        player.sendSystemMessage(Te.s(" ".repeat(4), getGoldenBeansCommand(1),
                " ".repeat(4), getGoldenBeansCommand(5),
                " ".repeat(4), getGoldenBeansCommand(10)));
        Compute.sendBlankLine(player, 4);
        MySound.soundToNearPlayer(player, SoundEvents.VILLAGER_AMBIENT);
    }

    public static Component getGoldenBeansCommand(int num) {
        return Te.c(Te.s("「取" + num + "金豆」", CustomStyle.styleOfGold),
                "/vmd getGoldenBeans " + num,
                Te.s("点击以取出" + num + "金豆"));
    }

    public static void getGoldenBeans(Player player, int count) {
        double currentGBValue = getGBValue(player);
        if (currentGBValue < count) {
            sendMSG(player, Te.s("账户内的", "GB", CustomStyle.styleOfGold, "不足。。"));
            return;
        }
        sendMSG(player, Te.s("成功取出了", count + "个", CustomStyle.styleOfGold, ModItems.GOLDEN_BEANS));
        expenseGB(player, count);
        InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(ModItems.GOLDEN_BEANS.get(), count));
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("银行", CustomStyle.styleOfGold), content);
    }
}
