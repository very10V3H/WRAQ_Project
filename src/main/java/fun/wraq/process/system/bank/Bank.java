package fun.wraq.process.system.bank;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

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

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("银行", CustomStyle.styleOfGold), content);
    }
}
