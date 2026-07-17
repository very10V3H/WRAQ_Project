/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.system.stock.networking.StockPortfolioSyncS2CPacket;
import fun.wraq.process.system.vp.VpDataHandler;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class StockTradingSystem {

    public static final double BUY_FEE_RATE = 0.0005; // 0.05%

    public static final int[] LEVERAGE_OPTIONS = {3, 10, 100};

    public static String buy(ServerPlayer player, StockIndex index, int leverage, int quantity, String currencyType) {
        if (StockMarketData.marketPaused) {
            return "§c股市已暂停交易，请稍后再试。";
        }
        StockPriceData priceData = StockMarketData.getPrice(index);
        if (priceData == null || !priceData.isAvailable()) {
            return "§c该指数当前无价格数据。";
        }
        double currentPrice = priceData.getCurrentPrice();
        if (currentPrice <= 0) {
            return "§c价格数据异常，无法交易。";
        }
        if (quantity <= 0) {
            return "§c数量必须大于0。";
        }
        boolean validLeverage = false;
        for (int l : LEVERAGE_OPTIONS) {
            if (l == leverage) { validLeverage = true; break; }
        }
        if (!validLeverage) {
            return "§c无效的杠杆倍率。可选: 3x, 10x, 100x";
        }

        double totalCost = currentPrice * quantity;
        double fee = totalCost * BUY_FEE_RATE;
        double totalDeduct = totalCost + fee;

        if ("VB".equals(currencyType)) {
            double balance = Compute.getCurrentVB(player);
            if (balance < totalDeduct) {
                return "§cVB余额不足！需要 §e" + String.format("%.2f", totalDeduct)
                        + "§c，当前余额 §e" + String.format("%.2f", balance);
            }
            Compute.VBExpenseAndMSGSend(player, totalDeduct);
        } else if ("VP".equals(currencyType)) {
            double balance = VpDataHandler.getPlayerVp(player);
            if (balance < totalDeduct) {
                return "§cVP余额不足！需要 §e" + String.format("%.2f", totalDeduct)
                        + "§c，当前余额 §e" + String.format("%.2f", balance);
            }
            VpDataHandler.payVp(player, totalDeduct);
        } else {
            return "§c无效的货币类型。";
        }

        StockPosition position = new StockPosition(index, leverage, currentPrice, quantity,
                currencyType, System.currentTimeMillis(), totalCost);
        StockPlayerData.addPosition(player, position);

        String msg = "§a[股市] 买入成功！§f" + index.getDisplayName()
                + " §e" + leverage + "x§f 杠杆 | 数量: §e" + quantity
                + "§f | 单价: §e" + String.format("%.2f", currentPrice)
                + "§f | 投资: §e" + String.format("%.2f", totalCost)
                + "§f | 手续费: §e" + String.format("%.2f", fee);
        return msg;
    }

    public static String sell(ServerPlayer player, int positionIndex) {
        List<StockPosition> positions = StockPlayerData.getPositions(player);
        if (positionIndex < 0 || positionIndex >= positions.size()) {
            return "§c无效的持仓索引。";
        }
        StockPosition position = positions.get(positionIndex);
        if (position == null) return "§c持仓数据异常。";

        if (StockMarketData.marketPaused) {
            return "§c股市已暂停交易，无法卖出。";
        }

        long now = System.currentTimeMillis();
        long cooldownMs = StockMarketData.POLL_INTERVAL_MS * 2;
        long heldMs = now - position.getBuyTimestamp();
        if (heldMs < cooldownMs) {
            long remainingSec = (cooldownMs - heldMs) / 1000;
            return "§c买入后需等待 §e" + remainingSec + "§c 秒才能卖出（2倍API刷新间隔）。";
        }

        StockPriceData priceData = StockMarketData.getPrice(position.getIndex());
        if (priceData == null || !priceData.isAvailable()) {
            return "§c该指数当前无价格数据，无法卖出。";
        }
        double currentPrice = priceData.getCurrentPrice();
        if (currentPrice <= 0) {
            return "§c价格数据异常，无法卖出。";
        }

        double priceChange = (currentPrice - position.getBuyPrice()) / position.getBuyPrice();
        double profit = priceChange * position.getInvestment() * position.getLeverage();
        double returnAmount = position.getInvestment() + profit;
        // Clamp: cannot lose more than invested
        if (returnAmount < 0) returnAmount = 0;

        String currencyType = position.getCurrencyType();
        if ("VB".equals(currencyType)) {
            Compute.VBIncomeAndMSGSend(player, returnAmount);
        } else if ("VP".equals(currencyType)) {
            VpDataHandler.rechargeVp(player, returnAmount);
        }

        StockPlayerData.removePosition(player, positionIndex);

        String profitStr = profit >= 0 ? "§a+" + String.format("%.2f", profit) : "§c" + String.format("%.2f", profit);
        String msg = "§a[股市] 卖出成功！§f" + position.getIndex().getDisplayName()
                + " | 盈亏: " + profitStr
                + "§f | 返还: §e" + String.format("%.2f", returnAmount)
                + " §f(" + position.getCurrencyType() + ")";
        return msg;
    }

    public static void checkAutoLiquidation(ServerPlayer player) {
        List<StockPosition> positions = StockPlayerData.getPositions(player);
        List<Integer> toRemove = new ArrayList<>();
        for (int i = 0; i < positions.size(); i++) {
            StockPosition pos = positions.get(i);
            StockPriceData priceData = StockMarketData.getPrice(pos.getIndex());
            if (priceData == null || !priceData.isAvailable()) continue;
            double currentPrice = priceData.getCurrentPrice();
            if (currentPrice <= 0) continue;
            double priceChange = (currentPrice - pos.getBuyPrice()) / pos.getBuyPrice();
            double loss = -priceChange * pos.getInvestment() * pos.getLeverage();
            // Liquidate when loss >= investment
            if (loss >= pos.getInvestment()) {
                toRemove.add(i);
                player.sendSystemMessage(Te.s("§c[股市] §f" + pos.getIndex().getDisplayName()
                        + " §c" + pos.getLeverage() + "x 合约已自动平仓！")
                        .withStyle(CustomStyle.styleOfHealth));
            }
        }
        // Remove in reverse order
        for (int j = toRemove.size() - 1; j >= 0; j--) {
            StockPlayerData.removePosition(player, toRemove.get(j));
        }
        if (!toRemove.isEmpty()) {
            StockPortfolioSyncS2CPacket.sendTo(player);
        }
    }

    public static void handleServerTick() {
        // Auto-liquidation check runs on the tick handler in ServerTick
        // This method is called periodically to handle any scheduled operations
    }
}
