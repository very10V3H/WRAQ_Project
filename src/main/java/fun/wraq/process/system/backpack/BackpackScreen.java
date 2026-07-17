package fun.wraq.process.system.backpack;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 * AI-Generated, 2026-07-12
 * 背包 GUI（AE2 风格深色半透明主题）。
 * <p>
 * 网格线位于格子之间（slot 18px + 1px 网格线间隙），行列均不重叠。
 * 行间距 19px，列间距 19px。
 */
public class BackpackScreen extends AbstractContainerScreen<BackpackMenu> {

    private static final int SLOT_SIZE = 18;
    private static final int COL_STRIDE = 19;        // 列步进：slot 18px + 网格线 1px
    private static final int ROW_STRIDE = 19;         // 行步进：slot 18px + 网格线 1px
    private static final int COLS = 9;

    // ── 布局常量（与 BackpackMenu slot 坐标同步） ──
    private static final int BP_SLOT_X = 10;
    private static final int BP_SLOT_Y = 20;
    private static final int PLAYER_INV_GAP = 14;
    private static final int HOTBAR_GAP = 4;
    private static final int LEFT_MARGIN = 10;
    private static final int RIGHT_MARGIN = 7;
    private static final int TOP_MARGIN = 17;
    private static final int BOTTOM_MARGIN = 7;

    // ── 颜色 ──
    private static final int COLOR_BG = 0xCC0E0E1A;
    private static final int COLOR_BP_BG = 0xCC1A1A2E;
    private static final int COLOR_BP_BORDER = 0xFF2D2D5E;
    private static final int COLOR_PLAYER_BG = 0xCC21213E;
    private static final int COLOR_GRID = 0xFF4B6EAF;
    private static final int COLOR_BORDER_ACCENT = 0xFF7B9FEF;
    private static final int COLOR_TITLE = 0xFF8AB4F8;
    private static final int COLOR_INFO = 0xFFA0A0C0;

    public BackpackScreen(BackpackMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        int bpRows = (menu.getSlotCount() + 8) / 9;
        this.imageWidth = LEFT_MARGIN + (COLS - 1) * COL_STRIDE + SLOT_SIZE + RIGHT_MARGIN;
        this.imageHeight = TOP_MARGIN
                + (BP_SLOT_Y - TOP_MARGIN) + bpRows * ROW_STRIDE
                + PLAYER_INV_GAP
                + 3 * ROW_STRIDE + HOTBAR_GAP + ROW_STRIDE
                + BOTTOM_MARGIN;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (imageWidth - this.font.width(title)) / 2;
        this.titleLabelY = 6;
    }

    /* ========== 背景绘制（在 slot 之前） ========== */

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int bpRows = (menu.getSlotCount() + 8) / 9;

        int bpAbsX = leftPos + BP_SLOT_X;
        int bpAbsY = topPos + BP_SLOT_Y;
        int areaWidth = (COLS - 1) * COL_STRIDE + SLOT_SIZE;  // slot 0..8 总宽

        fillRect(graphics, leftPos, topPos, imageWidth, imageHeight, COLOR_BG);

        // 背包区域
        int bpW = areaWidth + 2;
        int bpH = bpRows * ROW_STRIDE + 2;
        fillRect(graphics, bpAbsX - 1, bpAbsY - 1, bpW, bpH, COLOR_BP_BORDER);
        fillRect(graphics, bpAbsX, bpAbsY, bpW - 2, bpH - 2, COLOR_BP_BG);

        // 玩家背包 + 快捷栏区域
        int piAbsY = bpAbsY + bpRows * ROW_STRIDE + PLAYER_INV_GAP;
        int piH = 3 * ROW_STRIDE + HOTBAR_GAP + ROW_STRIDE;
        fillRect(graphics, bpAbsX - 1, piAbsY - 1, bpW, piH + 2, COLOR_BP_BORDER);
        fillRect(graphics, bpAbsX, piAbsY, bpW - 2, piH, COLOR_PLAYER_BG);

        // 标题
        graphics.drawString(font, title, leftPos + titleLabelX, topPos + 6, COLOR_TITLE, false);

        int tier = menu.getSlotLimitTier();
        if (tier > 0) {
            String info = "堆叠上限: " + (64 * (1 << tier));
            graphics.drawString(font, info,
                    leftPos + imageWidth - font.width(info) - 7, topPos + 6, COLOR_INFO, false);
        }
    }

    /* ========== 网格线覆盖（在 slot 之上绘制） ========== */

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderGridOverlay(graphics);
        renderTooltip(graphics, mouseX, mouseY);
    }

    private void renderGridOverlay(GuiGraphics graphics) {
        int bpRows = (menu.getSlotCount() + 8) / 9;

        int bpAbsX = leftPos + BP_SLOT_X;
        int bpAbsY = topPos + BP_SLOT_Y;
        int areaWidth = (COLS - 1) * COL_STRIDE + SLOT_SIZE;

        // ── 背包区域高亮边框（四边） ──
        int bpBot = bpAbsY + bpRows * ROW_STRIDE;
        int bpRight = bpAbsX + areaWidth;
        fillRect(graphics, bpAbsX - 1, bpAbsY - 1, areaWidth + 2, 1, COLOR_BORDER_ACCENT);       // 上
        fillRect(graphics, bpAbsX - 1, bpAbsY - 1, 1, bpRows * ROW_STRIDE + 2, COLOR_BORDER_ACCENT); // 左
        fillRect(graphics, bpAbsX - 1, bpBot, areaWidth + 2, 1, COLOR_BORDER_ACCENT);              // 下
        fillRect(graphics, bpRight, bpAbsY - 1, 1, bpRows * ROW_STRIDE + 2, COLOR_BORDER_ACCENT);  // 右
        drawSlotGrid(graphics, bpAbsX, bpAbsY, COLS, bpRows);

        // ── 玩家背包 + 快捷栏区域 ──
        int piAbsY = bpAbsY + bpRows * ROW_STRIDE + PLAYER_INV_GAP;
        int piH = 3 * ROW_STRIDE + HOTBAR_GAP + ROW_STRIDE;
        int piBot = piAbsY + piH;
        fillRect(graphics, bpAbsX - 1, piAbsY - 1, areaWidth + 2, 1, COLOR_BORDER_ACCENT);       // 上
        fillRect(graphics, bpAbsX - 1, piAbsY - 1, 1, piH + 2, COLOR_BORDER_ACCENT);              // 左
        fillRect(graphics, bpAbsX - 1, piBot, areaWidth + 2, 1, COLOR_BORDER_ACCENT);             // 下
        fillRect(graphics, bpRight, piAbsY - 1, 1, piH + 2, COLOR_BORDER_ACCENT);                 // 右
        drawSlotGrid(graphics, bpAbsX, piAbsY, COLS, 3);

        // ── 快捷栏上分隔线 ──
        int hotbarAbsY = piAbsY + 3 * ROW_STRIDE + HOTBAR_GAP;
        fillRect(graphics, bpAbsX - 1, hotbarAbsY - 1, areaWidth + 2, 1, COLOR_BORDER_ACCENT);
        drawSlotGrid(graphics, bpAbsX, hotbarAbsY, COLS, 1);
    }

    /** 绘制格子分隔线，水平线间距 ROW_STRIDE，垂直线间距 COL_STRIDE */
    private void drawSlotGrid(GuiGraphics graphics, int ox, int oy, int cols, int rows) {
        int totalW = (cols - 1) * COL_STRIDE + SLOT_SIZE;
        int totalH = rows * ROW_STRIDE;

        // 水平线（每个 slot 的底边）
        for (int r = 1; r < rows; r++) {
            int y = oy + r * ROW_STRIDE - 1;  // slot 底边 = oy + r*19 - 1
            graphics.fill(ox, y, ox + totalW, y + 1, COLOR_GRID);
        }
        // 垂直线（每个 slot 的右边）
        for (int c = 1; c < cols; c++) {
            int x = ox + c * COL_STRIDE - 1;  // slot 右边 = ox + c*19 - 1
            graphics.fill(x, oy, x + 1, oy + totalH, COLOR_GRID);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
    }

    private static void fillRect(GuiGraphics graphics, int x, int y, int w, int h, int color) {
        graphics.fill(x, y, x + w, y + h, color);
    }
}
