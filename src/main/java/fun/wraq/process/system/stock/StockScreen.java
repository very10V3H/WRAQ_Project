/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.stock.networking.ClientStockData;
import fun.wraq.process.system.stock.networking.StockBuyC2SPacket;
import fun.wraq.process.system.stock.networking.StockKLineRequestC2SPacket;
import fun.wraq.process.system.stock.networking.StockSellC2SPacket;
import fun.wraq.process.system.vp.VpDataHandler;
import fun.wraq.render.gui.WraqScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class StockScreen extends WraqScreen {

    private static final int CHART_X = 145;
    private static final int CHART_Y = 50;
    private static final int CHART_W = 200;
    private static final int CHART_H = 130;

    private int selectedIndex = 0;
    private int selectedLeverage = 3;
    private int quantity = 1;
    private int currencyType = 0; // 0=VB, 1=VP
    private int klineScale = 1;   // 0=分时, 1=日K, 2=周K
    private int lastPositionCount = -1;
    private int lastKlineIndex = -1;
    private int lastKlineScale = -1;
    private int klineRequestCooldown = 0;

    public StockScreen() {
        super(Component.literal("股市交易系统"));
    }

    @Override
    protected void init() {
        super.init();
        createMenu();
    }

    private void createMenu() {
        this.clearWidgets();

        int leftX = 5;
        int rightX = 155;
        int tableStartY = 36;
        int rowHeight = 14;

        StockIndex[] indices = StockIndex.values();

        // Index selection buttons (left panel)
        for (int i = 0; i < indices.length; i++) {
            final int idx = i;
            StockIndex index = indices[i];
            int y = tableStartY + i * rowHeight;
            String name = (i == selectedIndex ? "§6" : "") + index.getDisplayName();
            this.addRenderableWidget(Button.builder(
                    Component.literal(name),
                    btn -> selectIndex(idx)
            ).pos(leftX, y).size(92, 13).build());
            this.addRenderableWidget(Button.builder(
                    Component.literal("买入"),
                    btn -> selectIndex(idx)
            ).pos(leftX + 94, y).size(36, 13).build());
        }

        // K-line timeframe buttons (above chart)
        String[] scaleLabels = {"分时", "日K", "周K"};
        for (int i = 0; i < 3; i++) {
            final int sc = i;
            String label = scaleLabels[i] + (klineScale == i ? " ✓" : "");
            this.addRenderableWidget(Button.builder(
                    Component.literal(label),
                    btn -> { klineScale = sc; requestKLineData(); createMenu(); }
            ).pos(CHART_X + 5 + i * 45, CHART_Y - 14).size(40, 13).build());
        }

        // Leverage buttons
        int[] leverages = {3, 10, 100};
        for (int i = 0; i < leverages.length; i++) {
            final int lev = leverages[i];
            boolean active = selectedLeverage == lev;
            String label = lev + "x" + (active ? " ✓" : "");
            this.addRenderableWidget(Button.builder(
                    Component.literal(label),
                    btn -> { selectedLeverage = lev; createMenu(); }
            ).pos(CHART_X + 5 + i * 34, CHART_Y + CHART_H + 4).size(30, 13).build());
        }

        // Quantity controls
        this.addRenderableWidget(Button.builder(
                Component.literal("-"),
                btn -> { if (quantity > 1) { quantity--; createMenu(); } }
        ).pos(CHART_X + 5, CHART_Y + CHART_H + 20).size(16, 13).build());
        this.addRenderableWidget(Button.builder(
                Component.literal("+"),
                btn -> { if (quantity < 10000) { quantity++; createMenu(); } }
        ).pos(CHART_X + 55, CHART_Y + CHART_H + 20).size(16, 13).build());

        // Currency toggle
        this.addRenderableWidget(Button.builder(
                Component.literal("VB" + (currencyType == 0 ? " ✓" : "")),
                btn -> { currencyType = 0; createMenu(); }
        ).pos(CHART_X + 80, CHART_Y + CHART_H + 4).size(30, 13).build());
        this.addRenderableWidget(Button.builder(
                Component.literal("VP" + (currencyType == 1 ? " ✓" : "")),
                btn -> { currencyType = 1; createMenu(); }
        ).pos(CHART_X + 114, CHART_Y + CHART_H + 4).size(30, 13).build());

        // Buy button
        this.addRenderableWidget(Button.builder(
                Component.literal("确认买入"),
                btn -> ModNetworking.sendToServer(new StockBuyC2SPacket(
                        selectedIndex, selectedLeverage, quantity, currencyType))
        ).pos(CHART_X + 80, CHART_Y + CHART_H + 20).size(65, 14).build());

        // Sell buttons for each position
        int portfolioStartY = CHART_Y + CHART_H + 40;
        for (int i = 0; i < ClientStockData.positions.size(); i++) {
            final int posIdx = i;
            int y = portfolioStartY + i * 14;
            if (y > this.height - 10) break;
            this.addRenderableWidget(Button.builder(
                    Component.literal("卖出#" + (i + 1)),
                    btn -> ModNetworking.sendToServer(new StockSellC2SPacket(posIdx))
            ).pos(5, y).size(38, 13).build());
        }
    }

    private void selectIndex(int idx) {
        if (selectedIndex != idx) {
            selectedIndex = idx;
            requestKLineData();
        } else {
            selectedIndex = idx;
        }
        createMenu();
    }

    private void requestKLineData() {
        if (klineRequestCooldown > 0) return;
        klineRequestCooldown = 40; // 2 second cooldown
        ModNetworking.sendToServer(new StockKLineRequestC2SPacket(selectedIndex, klineScale));
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g);

        StockIndex[] indices = StockIndex.values();

        // Title
        g.drawCenteredString(font, Component.literal("§6§l股市交易系统"), this.width / 2, 6, 0xFFFFFF);

        // Market status
        String statusText = ClientStockData.marketPaused ? "§c§l暂停交易" : "§a§l交易中";
        g.drawString(font, statusText, this.width - 60, 6, 0xFFFFFF);

        // Balances
        String balanceText = "§eVB: §f" + String.format("%.2f", ClientUtils.VBNUM)
                + "  §bVP: §f" + String.format("%.2f", VpDataHandler.clientVpValue);
        g.drawString(font, balanceText, 5, 22, 0xFFFFFF);

        // Left panel: index table with price data
        int tableStartY = 36;
        int rowHeight = 14;
        for (int i = 0; i < indices.length; i++) {
            StockIndex index = indices[i];
            ClientStockData.PriceEntry entry = ClientStockData.priceCache.get(index);
            int y = tableStartY + i * rowHeight;
            boolean selected = (i == selectedIndex);

            String priceStr = "---";
            String changeStr = "§7无数据";
            if (entry != null && entry.available) {
                priceStr = String.format("%.2f", entry.price);
                double change = entry.changePercent;
                String sign = change >= 0 ? "+" : "";
                changeStr = (change >= 0 ? "§a" : "§c") + sign + String.format("%.2f%%", change);
            }
            int color = selected ? 0xFFAA00 : 0xAAAAAA;
            // Render price below the button (button takes rowHeight=13px)
            g.drawString(font, priceStr + " " + changeStr, 5, y + rowHeight, color);
        }

        // Chart area
        drawChart(g);

        // Buy info below chart
        StockIndex sel = indices[selectedIndex];
        ClientStockData.PriceEntry selEntry = ClientStockData.priceCache.get(sel);
        if (selEntry != null && selEntry.available && selEntry.price > 0) {
            double totalCost = selEntry.price * quantity;
            double fee = totalCost * StockTradingSystem.BUY_FEE_RATE;
            g.drawString(font, "§f数量: §e" + quantity, CHART_X + 23, CHART_Y + CHART_H + 22, 0xFFFFFF);
            g.drawString(font, "§f投资: §e" + String.format("%.2f", totalCost), CHART_X + 5, CHART_Y + CHART_H + 38, 0xFFFFFF);
            g.drawString(font, "§f手续费: §7" + String.format("%.2f", fee), CHART_X + 80, CHART_Y + CHART_H + 38, 0xFFFFFF);
        }

        // Portfolio section
        int portfolioStartY = CHART_Y + CHART_H + 40;
        g.drawString(font, "§6§l持仓列表", 5, portfolioStartY - 12, 0xFFFFFF);
        if (ClientStockData.positions.isEmpty()) {
            g.drawString(font, "§7暂无持仓", 5, portfolioStartY, 0xFFFFFF);
        } else {
            for (int i = 0; i < ClientStockData.positions.size(); i++) {
                StockPosition pos = ClientStockData.positions.get(i);
                int y = portfolioStartY + i * 14;
                if (y > this.height - 10) break;
                ClientStockData.PriceEntry pe = ClientStockData.priceCache.get(pos.getIndex());
                double curPrice = (pe != null && pe.available) ? pe.price : pos.getBuyPrice();
                double profit = (curPrice - pos.getBuyPrice()) / pos.getBuyPrice()
                        * pos.getInvestment() * pos.getLeverage();
                String profitStr = profit >= 0 ? "§a+" + String.format("%.2f", profit)
                        : "§c" + String.format("%.2f", profit);
                long heldMs = System.currentTimeMillis() - pos.getBuyTimestamp();
                long remainingSec = (StockMarketData.POLL_INTERVAL_MS * 2 - heldMs) / 1000;
                String cooldownStr = remainingSec > 0 ? " §7(冷却" + remainingSec + "s)" : " §a可卖出";
                String posText = "#" + (i + 1) + " " + pos.getIndex().getDisplayName()
                        + " " + pos.getLeverage() + "x " + profitStr + cooldownStr;
                g.drawString(font, posText, 45, y, 0xFFFFFF);
            }
        }

        super.render(g, mouseX, mouseY, partialTick);
    }

    /** Draw the K-line / line chart */
    private void drawChart(GuiGraphics g) {
        // Chart border
        g.fill(CHART_X, CHART_Y, CHART_X + CHART_W, CHART_Y + CHART_H, 0x44000000);
        g.renderOutline(CHART_X, CHART_Y, CHART_W, CHART_H, 0xFF555555);

        StockIndex[] indices = StockIndex.values();
        StockIndex sel = indices.length > selectedIndex ? indices[selectedIndex] : indices[0];

        // Title in chart header
        String chartTitle = sel.getDisplayName();
        String[] scaleNames = {"分时", "日K", "周K"};
        if (klineScale >= 0 && klineScale < 3) {
            chartTitle += " · " + scaleNames[klineScale];
        }
        g.drawString(font, chartTitle, CHART_X + 5, CHART_Y + 2, 0xFFCC66);

        List<StockKLineData> bars = getCurrentKLineData();
        if (bars == null || bars.isEmpty()) {
            g.drawCenteredString(font, Component.literal("§7加载中..."),
                    CHART_X + CHART_W / 2, CHART_Y + CHART_H / 2 - 5, 0xFFFFFF);
            return;
        }

        // Compute price range
        double minPrice = Double.MAX_VALUE;
        double maxPrice = Double.MIN_VALUE;
        for (StockKLineData bar : bars) {
            double low = bar.getLow() > 0 ? bar.getLow() : bar.getClose();
            double high = bar.getHigh() > 0 ? bar.getHigh() : bar.getClose();
            if (low < minPrice) minPrice = low;
            if (high > maxPrice) maxPrice = high;
        }
        if (minPrice == Double.MAX_VALUE || maxPrice == Double.MIN_VALUE) return;

        // Add padding to price range
        double priceRange = maxPrice - minPrice;
        if (priceRange == 0) priceRange = 1;
        minPrice -= priceRange * 0.05;
        maxPrice += priceRange * 0.05;
        priceRange = maxPrice - minPrice;

        int plotLeft = CHART_X + 5;
        int plotRight = CHART_X + CHART_W - 30;
        int plotTop = CHART_Y + 12;
        int plotBottom = CHART_Y + CHART_H - 14;
        int plotW = plotRight - plotLeft;
        int plotH = plotBottom - plotTop;

        // Draw horizontal grid lines (price levels)
        int gridCount = 5;
        for (int i = 0; i <= gridCount; i++) {
            int lineY = plotTop + (i * plotH / gridCount);
            double price = maxPrice - (i * priceRange / gridCount);
            g.fill(plotLeft, lineY, plotRight, lineY + 1, 0x22FFFFFF);
            g.drawString(font, String.format("%.2f", price), plotRight + 2, lineY - 4, 0xAAAAAA);
        }

        if (klineScale == 0) {
            // 分时: draw line chart
            drawLineChart(g, bars, plotLeft, plotTop, plotW, plotH, minPrice, priceRange);
        } else {
            // 日K/周K: draw candlesticks
            drawCandlesticks(g, bars, plotLeft, plotTop, plotW, plotH, minPrice, priceRange);
        }

        // Draw time labels on bottom
        int labelCount = Math.min(5, bars.size());
        if (labelCount > 0 && !bars.isEmpty()) {
            for (int i = 0; i < labelCount; i++) {
                int idx = i * (bars.size() - 1) / (labelCount - 1);
                if (idx >= bars.size()) idx = bars.size() - 1;
                int labelX = plotLeft + (int) ((double) idx / (bars.size() - 1) * plotW);
                if (i == 0) labelX = plotLeft;
                if (i == labelCount - 1) labelX = plotRight - 40;
                String timeStr = bars.get(idx).getTime();
                if (timeStr.length() > 5) timeStr = timeStr.substring(timeStr.length() - 5);
                g.drawString(font, timeStr, labelX, plotBottom + 2, 0x888888);
            }
        }
    }

    private void drawLineChart(GuiGraphics g, List<StockKLineData> bars,
                               int px, int py, int pw, int ph, double minPrice, double priceRange) {
        if (bars.size() < 2) return;
        double prevClose = bars.get(0).getClose();

        for (int i = 1; i < bars.size(); i++) {
            int x1 = px + (int) ((double) (i - 1) / (bars.size() - 1) * pw);
            int x2 = px + (int) ((double) i / (bars.size() - 1) * pw);
            int y1 = py + ph - (int) ((bars.get(i - 1).getClose() - minPrice) / priceRange * ph);
            int y2 = py + ph - (int) ((bars.get(i).getClose() - minPrice) / priceRange * ph);
            // Clamp Y
            y1 = Math.max(py, Math.min(py + ph, y1));
            y2 = Math.max(py, Math.min(py + ph, y2));
            // Line color: green if above prev close, red if below
            double curClose = bars.get(i).getClose();
            int lineColor = curClose >= prevClose ? 0xFF00AA00 : 0xFFCC0000;
            // Draw thick line with 2px height
            drawLine(g, x1, y1, x2, y2, lineColor);
            drawLine(g, x1, y1 + 1, x2, y2 + 1, lineColor);
            prevClose = bars.get(0).getClose(); // compare to first bar's close (base price)
        }
    }

    private void drawCandlesticks(GuiGraphics g, List<StockKLineData> bars,
                                  int px, int py, int pw, int ph, double minPrice, double priceRange) {
        if (bars.isEmpty()) return;
        int n = bars.size();
        int maxBarWidth = Math.max(2, pw / n - 1);
        int barWidth = Math.min(8, maxBarWidth);
        int gap = Math.max(1, (pw - barWidth * n) / n);

        for (int i = 0; i < n; i++) {
            StockKLineData bar = bars.get(i);
            int cx = px + i * (barWidth + gap) + barWidth / 2;
            int openY = py + ph - (int) ((bar.getOpen() - minPrice) / priceRange * ph);
            int closeY = py + ph - (int) ((bar.getClose() - minPrice) / priceRange * ph);
            int highY = py + ph - (int) ((bar.getHigh() - minPrice) / priceRange * ph);
            int lowY = py + ph - (int) ((bar.getLow() - minPrice) / priceRange * ph);

            openY = Math.max(py, Math.min(py + ph, openY));
            closeY = Math.max(py, Math.min(py + ph, closeY));
            highY = Math.max(py, Math.min(py + ph, highY));
            lowY = Math.max(py, Math.min(py + ph, lowY));

            boolean isUp = bar.getClose() >= bar.getOpen();
            int bodyColor = isUp ? 0xFFCC0000 : 0xFF00AA00; // Red=up, Green=down (Chinese convention)
            int wickColor = isUp ? 0xFFCC0000 : 0xFF00AA00;

            // Draw wick (high-low line)
            g.fill(cx, Math.min(highY, lowY), cx + 1, Math.max(highY, lowY), wickColor);

            // Draw body (open-close rect)
            int bodyTop = Math.min(openY, closeY);
            int bodyHeight = Math.max(1, Math.abs(closeY - openY));
            int bodyLeft = cx - barWidth / 2;
            g.fill(bodyLeft, bodyTop, bodyLeft + barWidth, bodyTop + bodyHeight, bodyColor);
        }
    }

    /** Simple line drawing using horizontal/vertical fills */
    private void drawLine(GuiGraphics g, int x1, int y1, int x2, int y2, int color) {
        int dx = Math.abs(x2 - x1);
        int dy = -Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx + dy;
        while (true) {
            g.fill(x1, y1, x1 + 1, y1 + 1, color);
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 >= dy) { err += dy; x1 += sx; }
            if (e2 <= dx) { err += dx; y1 += sy; }
        }
    }

    private List<StockKLineData> getCurrentKLineData() {
        if (klineScale >= 0 && klineScale < 3) {
            List<StockKLineData> cached = ClientStockData.klineCache[klineScale];
            if (cached != null && !cached.isEmpty()) return cached;
        }
        return List.of();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        // Rebuild widgets when positions change
        int currentCount = ClientStockData.positions.size();
        if (currentCount != lastPositionCount) {
            lastPositionCount = currentCount;
            createMenu();
        }
        // Request K-line data on first open or when index changes
        if (klineRequestCooldown > 0) {
            klineRequestCooldown--;
        }
        if (lastKlineIndex != selectedIndex || lastKlineScale != klineScale) {
            lastKlineIndex = selectedIndex;
            lastKlineScale = klineScale;
            requestKLineData();
        }
    }
}
