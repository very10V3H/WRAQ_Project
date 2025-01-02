package fun.wraq.process.system.market;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.MarketItemData;
import fun.wraq.common.util.MarketProfitData;
import fun.wraq.common.util.Utils;
import fun.wraq.files.MarketItemInfo;
import fun.wraq.files.MarketProfitInfo;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class MarketInfo {
    public static void marketItemInfoRead(ServerLevel overworld) throws CommandSyntaxException {
        List<String> marketItemList = MarketItemData.getInstance(overworld).getMarketInfoStringList();
        Utils.marketItemInfos.clear();
        for (String string : marketItemList) {
            String playerName = "";
            String itemStackName = "";
            String price = "";
            String type = "";
            int index1 = 0; // #
            int index2 = 0; // *
            int index3 = 0; // ^
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == '#') {
                    playerName = string.substring(0, i);
                    index1 = i + 1;
                    break;
                }
            }
            for (int i = string.length() - 1; i >= 0; i--) {
                if (string.charAt(i) == '^') {
                    type = string.substring(i + 1);
                    index3 = i + 1;
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
            price = string.substring(index2, index3 - 1);

            if (itemStackName.startsWith("{")) {
                ItemStack itemStack = Compute.getItemFromString(itemStackName);
                if (!playerName.isEmpty() && !price.isEmpty() && !itemStack.is(Items.AIR)) {
                    MarketItemInfo marketItemInfo = new MarketItemInfo(playerName, itemStack,
                            Integer.parseInt(price), Integer.parseInt(type));
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
            marketItemList.add(marketItemInfo.playerName + "#" + Compute.getItemStackString(marketItemInfo.itemStack)
                    + "*" + marketItemInfo.price + "^" + marketItemInfo.type);
        });
        marketItemData.setDirty();
    }

    public static void marketProfitInfoRead(ServerLevel overworld) {
        MarketProfitData marketProfitData = MarketProfitData.getInstance(overworld);
        List<String> marketProfitInfos = marketProfitData.getMarketProfitInfo();
        Utils.marketProfitInfos
                .addAll(marketProfitInfos
                        .stream().map(MarketProfitInfo::getMarketProfitInfoFromString)
                        .toList());
    }

    public static void marketProfitInfoWrite(ServerLevel overworld) {
        MarketProfitData marketProfitData = MarketProfitData.getInstance(overworld);
        List<String> marketProfitInfos = marketProfitData.getMarketProfitInfo();
        marketProfitInfos.clear();
        marketProfitInfos.addAll(Utils.marketProfitInfos.stream().map(MarketProfitInfo::toString).toList());
        marketProfitData.setDirty();
    }

    public static String getType(int type) {
        if (type == 0) {
            return "VB";
        } else if (type == 1) {
            return "GB";
        }
        return "VB";
    }

    public static Component getTypeDescription(int type) {
        if (type == 0) {
            return Te.s("VB", CustomStyle.styleOfGold);
        } else if (type == 1) {
            return Te.s(ModItems.GOLDEN_BEANS);
        }
        return Te.s("VB", CustomStyle.styleOfGold);
    }
}
