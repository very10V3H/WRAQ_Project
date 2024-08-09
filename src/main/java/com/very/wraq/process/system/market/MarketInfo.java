package com.very.wraq.process.system.market;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.files.MarketItemInfo;
import com.very.wraq.files.MarketPlayerInfo;
import com.very.wraq.files.dataBases.DataBase;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MarketInfo {
    public static void marketInfoRead() throws SQLException, CommandSyntaxException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        List<String> marketItemList = DataBase.getAllMarketItemInfo(statement);
        Utils.marketItemInfos.clear();
        for (String string : marketItemList) {
            String playerName = "";
            String itemStackName = "";
            String price = "";
            int index1 = 0; // #
            int index2 = 0; // *
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == '#') {
                    playerName = string.substring(0, i);
                    index1 = i + 1;
                    break;
                }
            }
            for (int i = string.length() - 1; i >= 0; i--) {
                if (string.charAt(i) == '*') {
                    itemStackName = string.substring(index1, i);
                    index2 = i + 1;
                    break;
                }
            }

            price = string.substring(index2, string.length() - 1);
            if (itemStackName.startsWith("{")) {
                ItemStack itemStack = Compute.getItemFromString(itemStackName);
                if (!playerName.isEmpty() && !price.isEmpty() && !itemStack.is(Items.AIR)) {
                    MarketItemInfo marketItemInfo = new MarketItemInfo(playerName, itemStack, Double.parseDouble(price));
                    Utils.marketItemInfos.add(marketItemInfo);
                }
            }
        }
        statement.close();
        connection.close();
    }

    public static void marketPlayerInfoRead() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        List<MarketPlayerInfo> marketPlayerInfos = DataBase.getAllMarketPlayerInfo(statement);
        for (MarketPlayerInfo marketPlayerInfo : marketPlayerInfos) {
            Utils.marketPlayerInfos.put(marketPlayerInfo.getPlayer(), marketPlayerInfo.getProfit());
        }
        statement.close();
        connection.close();
    }

    public static void playerInfoWrite() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        DataBase.putAllMarketPlayerInfo(statement);
        statement.close();
        connection.close();
    }

    public static void itemInfoWrite() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        /*DataBase.putAllMarketItemInfo(statement);*/
        statement.close();
        connection.close();
    }

}
