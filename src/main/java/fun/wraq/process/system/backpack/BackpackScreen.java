package fun.wraq.process.system.backpack;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.backpack.networking.BackpackPageC2SPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 * AI-Generated, 2026-07-12
 * 背包 GUI（AE2 风格深色半透明主题），支持分页。
 * 每页固定 4 行，翻页栏位于背包区域与玩家背包之间。
 */
public class BackpackScreen extends AbstractContainerScreen<BackpackMenu> {

    private static final int SLOT_SIZE = 18;
    private static final int COL_STRIDE = 19;
    private static final int ROW_STRIDE = 19;
    private static final int COLS = 9;
    private static final int PAGE_ROWS = 4;          // 每页固定 4 行

    // ── 布局常量（与 BackpackMenu slot 坐标同步） ──
    private static final int BP_SLOT_X = 10;
    private static final int BP_SLOT_Y = 20;
    private static final int PLAYER_INV_GAP = 14;
    private static final int HOTBAR_GAP = 4;
    private static final int LEFT_MARGIN = 10;
    private static final int RIGHT_MARGIN = 7;
    private static final int TOP_MARGIN = 17;
    private static final int BOTTOM_MARGIN = 7;
    private static final int PAGE_BAR_HEIGHT = 16;   // 翻页栏高度

    // ── 颜色 ──
    private static final int COLOR_BG = 0xCC0E0E1A;
    private static final int COLOR_BP_BG = 0xCC1A1A2E;
    private static final int COLOR_BP_BORDER = 0xFF2D2D5E;
    private static final int COLOR_PLAYER_BG = 0xCC21213E;
    private static final int COLOR_GRID = 0xFF4B6EAF;
    private static final int COLOR_BORDER_ACCENT = 0xFF7B9FEF;
    private static final int COLOR_TITLE = 0xFF8AB4F8;
    private static final int COLOR_INFO = 0xFFA0A0C0;
    private static final int COLOR_BTN_HOVER = 0xFFFFFFFF;
    private static final int COLOR_BTN_DISABLED = 0xFF505070;

    // ── 分页 ──
    private final int pageOffset;
    private final int totalPages;
    private final int rowsOnThisPage;

    // 按钮点击区域（init() 中计算坐标）
    private int prevBtnX, prevBtnY;
    private final int prevBtnW, prevBtnH;
    private int nextBtnX, nextBtnY;
    private final int nextBtnW, nextBtnH;

    public BackpackScreen(BackpackMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.pageOffset = menu.getPageOffset();
        int totalSlots = menu.getSlotCount();
        this.totalPages = Math.max(1, (totalSlots + 35) / 36);
        int startIndex = pageOffset * 36;
        int slotsAvailable = Math.max(0, totalSlots - startIndex);
        this.rowsOnThisPage = Math.min(PAGE_ROWS, (slotsAvailable + 8) / 9);

        this.imageWidth = LEFT_MARGIN + (COLS - 1) * COL_STRIDE + SLOT_SIZE + RIGHT_MARGIN;
        this.imageHeight = TOP_MARGIN
                + (BP_SLOT_Y - TOP_MARGIN) + PAGE_ROWS * ROW_STRIDE
                + PLAYER_INV_GAP
                + PAGE_BAR_HEIGHT
                + PLAYER_INV_GAP
                + 3 * ROW_STRIDE + HOTBAR_GAP + ROW_STRIDE
                + BOTTOM_MARGIN;

        // leftPos/topPos 此时为 0，按钮坐标在 init() 中计算
        this.prevBtnW = 14;
        this.prevBtnH = 14;
        this.nextBtnW = 14;
        this.nextBtnH = 14;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (imageWidth - this.font.width(title)) / 2;
        this.titleLabelY = 6;

        // 翻页按钮坐标（位于背包区域与玩家背包之间）
        int bpAbsY = topPos + BP_SLOT_Y;
        int barCenterY = bpAbsY + PAGE_ROWS * ROW_STRIDE + PLAYER_INV_GAP + PAGE_BAR_HEIGHT / 2;
        this.prevBtnX = leftPos + (imageWidth - prevBtnW) / 2 - 40;
        this.prevBtnY = barCenterY - prevBtnH / 2;
        this.nextBtnX = leftPos + (imageWidth - nextBtnW) / 2 + 40;
        this.nextBtnY = barCenterY - nextBtnH / 2;
    }

    /* ========== 背景绘制 ========== */

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int bpAbsX = leftPos + BP_SLOT_X;
        int bpAbsY = topPos + BP_SLOT_Y;
        int areaWidth = (COLS - 1) * COL_STRIDE + SLOT_SIZE;

        fillRect(graphics, leftPos, topPos, imageWidth, imageHeight, COLOR_BG);

        // 背包区域（始终以 PAGE_ROWS 高度绘制）
        int bpW = areaWidth + 2;
        int bpH = PAGE_ROWS * ROW_STRIDE + 2;
        fillRect(graphics, bpAbsX - 1, bpAbsY - 1, bpW, bpH, COLOR_BP_BORDER);
        fillRect(graphics, bpAbsX, bpAbsY, bpW - 2, bpH - 2, COLOR_BP_BG);

        // 翻页栏（背包区域与玩家背包之间）
        int pageBarY = bpAbsY + PAGE_ROWS * ROW_STRIDE + PLAYER_INV_GAP;
        fillRect(graphics, bpAbsX - 1, pageBarY, bpW, PAGE_BAR_HEIGHT, COLOR_PLAYER_BG);
        fillRect(graphics, bpAbsX - 1, pageBarY - 1, bpW, 1, COLOR_BP_BORDER);

        // 玩家背包 + 快捷栏区域
        int piAbsY = pageBarY + PAGE_BAR_HEIGHT + PLAYER_INV_GAP;
        int piH = 3 * ROW_STRIDE + HOTBAR_GAP + ROW_STRIDE;
        fillRect(graphics, bpAbsX - 1, piAbsY - 1, bpW, piH + 2, COLOR_BP_BORDER);
        fillRect(graphics, bpAbsX, piAbsY, bpW - 2, piH, COLOR_PLAYER_BG);

        // 标题
        graphics.drawString(font, title, leftPos + titleLabelX, topPos + 6, COLOR_TITLE, false);

        // 堆叠上限信息
        int tier = menu.getSlotLimitTier();
        if (tier > 0) {
            String info = "堆叠上限: " + (64 * (1 << tier));
            graphics.drawString(font, info,
                    leftPos + imageWidth - font.width(info) - 7, topPos + 6, COLOR_INFO, false);
        }

        // ── 翻页控件 ──
        boolean canPrev = pageOffset > 0;
        boolean canNext = pageOffset < totalPages - 1;
        int prevColor = canPrev ? (isHoveringPrev(mouseX, mouseY) ? COLOR_BTN_HOVER : COLOR_INFO) : COLOR_BTN_DISABLED;
        int nextColor = canNext ? (isHoveringNext(mouseX, mouseY) ? COLOR_BTN_HOVER : COLOR_INFO) : COLOR_BTN_DISABLED;

        graphics.drawString(font, "◀", prevBtnX, prevBtnY, prevColor, false);
        graphics.drawString(font, "▶", nextBtnX, nextBtnY, nextColor, false);

        String pageStr = "第 " + (pageOffset + 1) + "/" + totalPages + " 页";
        int pageTextX = leftPos + (imageWidth - font.width(pageStr)) / 2;
        graphics.drawString(font, pageStr, pageTextX, prevBtnY, COLOR_TITLE, false);
    }

    /* ========== 网格线覆盖 ========== */

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderGridOverlay(graphics);
        renderTooltip(graphics, mouseX, mouseY);
    }

    private void renderGridOverlay(GuiGraphics graphics) {
        int bpAbsX = leftPos + BP_SLOT_X;
        int bpAbsY = topPos + BP_SLOT_Y;
        int areaWidth = (COLS - 1) * COL_STRIDE + SLOT_SIZE;

        // ── 背包区域高亮边框（四边） ──
        int bpBot = bpAbsY + rowsOnThisPage * ROW_STRIDE;
        int bpRight = bpAbsX + areaWidth;
        fillRect(graphics, bpAbsX - 1, bpAbsY - 1, areaWidth + 2, 1, COLOR_BORDER_ACCENT);       // 上
        fillRect(graphics, bpAbsX - 1, bpAbsY - 1, 1, rowsOnThisPage * ROW_STRIDE + 2, COLOR_BORDER_ACCENT); // 左
        fillRect(graphics, bpAbsX - 1, bpBot - 1, areaWidth + 2, 1, COLOR_BORDER_ACCENT);              // 下
        fillRect(graphics, bpRight, bpAbsY - 1, 1, rowsOnThisPage * ROW_STRIDE + 2, COLOR_BORDER_ACCENT);  // 右
        drawSlotGrid(graphics, bpAbsX, bpAbsY, COLS, rowsOnThisPage);

        // ── 玩家背包 + 快捷栏区域 ──
        int pageBarY = bpAbsY + PAGE_ROWS * ROW_STRIDE + PLAYER_INV_GAP;
        int piAbsY = pageBarY + PAGE_BAR_HEIGHT + PLAYER_INV_GAP;
        int piH = 3 * ROW_STRIDE + HOTBAR_GAP + ROW_STRIDE;
        int piBot = piAbsY + piH;
        fillRect(graphics, bpAbsX - 1, piAbsY - 1, areaWidth + 2, 1, COLOR_BORDER_ACCENT);       // 上
        fillRect(graphics, bpAbsX - 1, piAbsY - 1, 1, piH + 2, COLOR_BORDER_ACCENT);              // 左
        fillRect(graphics, bpAbsX - 1, piBot, areaWidth + 2, 1, COLOR_BORDER_ACCENT);             // 下
        fillRect(graphics, bpRight, piAbsY - 1, 1, piH + 2, COLOR_BORDER_ACCENT);                 // 右
        drawSlotGrid(graphics, bpAbsX, piAbsY, COLS, 3);

        // ── 快捷栏上分隔线 ──
        int hotbarAbsY = piAbsY + 3 * ROW_STRIDE + HOTBAR_GAP;
        fillRect(graphics, bpAbsX - 1, hotbarAbsY - 5, areaWidth + 2, 1, COLOR_BORDER_ACCENT);
        drawSlotGrid(graphics, bpAbsX, hotbarAbsY - 2, COLS, 1);
    }

    private void drawSlotGrid(GuiGraphics graphics, int ox, int oy, int cols, int rows) {
        int totalW = (cols - 1) * COL_STRIDE + SLOT_SIZE;
        int totalH = rows * ROW_STRIDE;

        for (int r = 1; r < rows; r++) {
            int y = oy + r * ROW_STRIDE - 1;
            graphics.fill(ox, y, ox + totalW, y + 1, COLOR_GRID);
        }
        for (int c = 1; c < cols; c++) {
            int x = ox + c * COL_STRIDE - 1;
            graphics.fill(x, oy, x + 1, oy + totalH, COLOR_GRID);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
    }

    /* ========== 鼠标点击 ========== */

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (isHoveringPrev((int) mouseX, (int) mouseY) && pageOffset > 0) {
                ModNetworking.sendToServer(new BackpackPageC2SPacket(pageOffset - 1));
                return true;
            }
            if (isHoveringNext((int) mouseX, (int) mouseY) && pageOffset < totalPages - 1) {
                ModNetworking.sendToServer(new BackpackPageC2SPacket(pageOffset + 1));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isHoveringPrev(int mouseX, int mouseY) {
        return mouseX >= prevBtnX && mouseX <= prevBtnX + prevBtnW
                && mouseY >= prevBtnY && mouseY <= prevBtnY + prevBtnH;
    }

    private boolean isHoveringNext(int mouseX, int mouseY) {
        return mouseX >= nextBtnX && mouseX <= nextBtnX + nextBtnW
                && mouseY >= nextBtnY && mouseY <= nextBtnY + nextBtnH;
    }

    private static void fillRect(GuiGraphics graphics, int x, int y, int w, int h, int color) {
        graphics.fill(x, y, x + w, y + h, color);
    }
}
