package fun.wraq.process.system.market;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.util.MarketItemData;
import fun.wraq.common.util.MarketProfitData;
import fun.wraq.common.util.Utils;
import fun.wraq.files.MarketItemInfo;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Map;

public class MarketInfo {
    public static void marketItemInfoRead(ServerLevel overworld) throws CommandSyntaxException {
        List<String> marketItemList = MarketItemData.getInstance(overworld).getMarketInfoStringList();
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
    }

    public static void marketItemInfoWrite(ServerLevel overworld) {
        MarketItemData marketItemData = MarketItemData.getInstance(overworld);
        List<String> marketItemList = marketItemData.getMarketInfoStringList();
        marketItemList.clear();
        Utils.marketItemInfos.forEach(marketItemInfo -> {
            marketItemList.add(marketItemInfo.getPlayer() + "#" + Compute.getItemStackString(marketItemInfo.getItemStack()) + "*" + marketItemInfo.getPrice());
        });
        marketItemData.setDirty();
    }

    public static void marketProfitInfoRead(ServerLevel overworld) {
        MarketProfitData marketProfitData = MarketProfitData.getInstance(overworld);
        Map<String, Double> marketProfitInfos = marketProfitData.getMarketProfitInfo();
        Utils.marketPlayerInfos.putAll(marketProfitInfos);
    }

    public static void marketProfitInfoWrite(ServerLevel overworld) {
        MarketProfitData marketProfitData = MarketProfitData.getInstance(overworld);
        Map<String, Double> marketProfitInfos = marketProfitData.getMarketProfitInfo();
        marketProfitInfos.clear();
        marketProfitInfos.putAll(Utils.marketPlayerInfos);
        marketProfitData.setDirty();
    }
}
