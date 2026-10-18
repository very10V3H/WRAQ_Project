/** AI-Generated, 2026-10-18 */
package fun.wraq.render.gui.skills;

import com.mojang.blaze3d.vertex.PoseStack;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.MasteryPackets.MasteryRequestC2SPacket;
import fun.wraq.networking.misc.MasteryPackets.MasterySaveC2SPacket;
import fun.wraq.process.system.mastery.MasteryAllocation;
import fun.wraq.process.system.mastery.MasteryClientData;
import fun.wraq.process.system.mastery.MasteryTree;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * 精通界面（AI-Generated, 2026-10-18）。
 *
 * <p>五列竖排布局：每列对应精密 / 主宰 / 巫术 / 坚决 / 启迪，每列 6 排，
 * 第 6 排为基石。点击圆圈打开该排的横向加点面板，左键 +1、右键 -1、滚轮 ±1，
 * 点击「确定」保存。若调整导致下一排前置不足，会弹出确认框；继续则清空该树后续排的点数。</p>
 *
 * <p>界面完全用图形绘制（无贴图）：图标以圆圈 + 路径首字代替。点数数据来自
 * {@link MasteryClientData}，保存走 {@link MasterySaveC2SPacket}，服务端再回传最终数据。</p>
 */
public class MasteryScreen extends Screen {

    private static final int TREE_COUNT = MasteryAllocation.TREE_COUNT;
    private static final int ROW_COUNT = MasteryAllocation.ROW_COUNT;

    private static final int PANEL_PAD = 22;

    /** 预生成的圆形图集：8列 x 4行，每格 128px，带 blur/linear 过滤 */
    private static final ResourceLocation CIRCLE_TEXTURE =
            new ResourceLocation(Utils.MOD_ID, "textures/gui/mastery/circles.png");
    private static final int ATLAS_CELL = 128;
    private static final int ATLAS_WIDTH = 1024;
    private static final int ATLAS_HEIGHT = 512;
    private static final int CELL_FILL = 0;          // (0,0)
    private static final int CELL_GLOW_WHITE = 1;    // (1,0)
    private static final int CELL_RING_WHITE = 2;    // (2,0)
    private static final int CELL_RING_LOCKED = 3;   // (3,0)
    private static final int CELL_RING_UNLOCKED = 4; // (4,0)
    // 每棵树：glow = (tree,1)、ring = (tree,2)、diamond = (tree,3)

    private static final int TEXT = 0xFFC9D4E6;
    private static final int DIM = 0xFF7C879C;
    private static final int FAINT = 0xFF4A5468;
    private static final int GOLD = 0xFFE6C46A;
    private static final int PANEL_BORDER = 0xFF4FC3F7;
    private static final int COLUMN_BORDER = 0x40222E44;

    private final Screen parent;

    private int panelX;
    private int panelY;
    private int panelW;
    private int panelH;
    private int panelPad;
    private int columnW;
    private int columnGap;
    private int footerH;
    private int emblemRadius;
    private int nodeRadius;
    private int keystoneRadius;
    private float labelScale;
    private float nodeTextScale;
    private boolean compact;
    private int rowsY;
    private int rowStep;

    private int[][] draft = MasteryAllocation.empty();
    private int[][] baseline = MasteryAllocation.empty();
    private final int[][] pendingRefund = new int[TREE_COUNT][ROW_COUNT];
    private int seenRevision = -1;

    private boolean warningOpen;
    private int warningCleared;

    private boolean keystoneOpen;
    private int keystoneTree = -1;
    private int[][] keystoneBackup;

    private String notice;
    private long noticeUntil;

    public MasteryScreen(Screen parent) {
        super(Component.literal("精通"));
        this.parent = parent;
    }

    public MasteryScreen() {
        this(null);
    }

    @Override
    protected void init() {
        ModNetworking.sendToServer(new MasteryRequestC2SPacket());
        updateLayout();
        draft = MasteryAllocation.copy(MasteryClientData.allocation);
        baseline = MasteryAllocation.copy(MasteryClientData.allocation);
        clearPendingRefund();
        seenRevision = MasteryClientData.revision;
    }

    @Override
    public void tick() {
        super.tick();
        syncDraftFromCache();
    }

    private void syncDraftFromCache() {
        if (seenRevision == MasteryClientData.revision) {
            return;
        }
        seenRevision = MasteryClientData.revision;
        if (!isDirty()) {
            draft = MasteryAllocation.copy(MasteryClientData.allocation);
            baseline = MasteryAllocation.copy(MasteryClientData.allocation);
            clearPendingRefund();
        }
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        updateLayout();
    }

    /**
     * 依据当前窗口尺寸重算响应式排版。窗口拉伸时也会重新计算。
     */
    private void updateLayout() {
        compact = this.height < 420 || this.width < 480;
        panelPad = compact ? 14 : PANEL_PAD;
        columnGap = compact ? 8 : 10;
        panelW = Math.min(this.width - 16, 820);
        panelH = Math.min(this.height - 16, 510);
        panelX = (this.width - panelW) / 2;
        panelY = (this.height - panelH) / 2;
        columnW = (panelW - panelPad * 2 - columnGap * (TREE_COUNT - 1)) / TREE_COUNT;

        emblemRadius = compact ? 12 : 17;

        if (compact) {
            footerH = 36;
            rowsY = panelY + 102;
            int lastRowY = panelY + panelH - footerH - 20;
            rowStep = Math.max(18, (lastRowY - rowsY) / (ROW_COUNT - 1));
            nodeRadius = Math.max(6, Math.min(14, (int) Math.round(rowStep * 0.32)));
            keystoneRadius = Math.min(18, nodeRadius + 4);
            labelScale = 0.56f;
            nodeTextScale = 0.75f;
        } else {
            footerH = 46;
            rowsY = panelY + 126;
            int lastRowY = panelY + panelH - footerH - 28;
            rowStep = Math.max(42, (lastRowY - rowsY) / (ROW_COUNT - 1));
            nodeRadius = 14;
            keystoneRadius = 18;
            labelScale = 0.66f;
            nodeTextScale = 1.0f;
        }
        // 窄窗口时按列宽再收一档，避免三节点横向重叠
        nodeRadius = Math.min(nodeRadius, Math.max(5, (columnW - 12) / 6));
        keystoneRadius = Math.min(keystoneRadius, nodeRadius + 4);
    }

    private int closeX() {
        return panelX + panelW - (compact ? 24 : 26);
    }

    private int closeY() {
        return panelY + (compact ? 8 : 10);
    }

    // ------------------------------------------------------------------
    // rendering
    // ------------------------------------------------------------------

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fillGradient(0, 0, this.width, this.height, 0xFF05080F, 0xFF0A101B);
        drawPanel(graphics, mouseX, mouseY);

        List<Component> tooltip = drawColumns(graphics,
                warningOpen || keystoneOpen ? Integer.MIN_VALUE : mouseX,
                warningOpen || keystoneOpen ? Integer.MIN_VALUE : mouseY);

        drawFooter(graphics);
        drawFooterButtons(graphics, mouseX, mouseY);

        if (keystoneOpen) {
            drawKeystoneModal(graphics, mouseX, mouseY);
        }
        if (warningOpen) {
            drawWarning(graphics, mouseX, mouseY);
        }
        drawNotice(graphics);

        if (!MasteryClientData.synced) {
            drawCenteredScaled(graphics, "正在同步精通数据...", this.width / 2,
                    panelY + panelH / 2, 1.0f, GOLD);
        }
        if (tooltip != null) {
            graphics.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void drawPanel(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(panelX - 3, panelY - 3, panelX + panelW + 3, panelY + panelH + 3, 0x50000000);
        graphics.fillGradient(panelX, panelY, panelX + panelW, panelY + panelH, 0xFF0C1320, 0xFF070B12);
        border(graphics, panelX, panelY, panelX + panelW, panelY + panelH, PANEL_BORDER);

        scaledString(graphics, "精通", panelX + panelPad, panelY + (compact ? 8 : 10),
                compact ? 1.3f : 1.7f, GOLD, false);
        if (!compact) {
            scaledString(graphics, "MASTERY", panelX + panelPad + 58, panelY + 16, 0.9f, 0xFF5C6880, false);
            scaledString(graphics, "选择路径 · 自由分配 · 随时重新配置", panelX + panelPad,
                    panelY + 31, 0.72f, DIM, false);
        }

        int pillW = compact ? 150 : 190;
        int pillH = compact ? 24 : 30;
        int pillX = panelX + panelW - panelPad - pillW - (compact ? 24 : 28);
        int pillY = panelY + (compact ? 8 : 10);
        graphics.fill(pillX, pillY, pillX + pillW, pillY + pillH, 0xD0141C2C);
        border(graphics, pillX, pillY, pillX + pillW, pillY + pillH, 0xFF3A4864);
        if (compact) {
            scaledString(graphics, "可用精通点", pillX + 10, pillY + 4, 0.55f, DIM, false);
            scaledString(graphics, String.valueOf(draftAvailable()), pillX + 10,
                    pillY + 11, 1.0f, GOLD, false);
            scaledStringRight(graphics,
                    "总点数 " + MasteryClientData.total + " · 已用 " + draftUsed(),
                    pillX + pillW - 10, pillY + 7, 0.55f, DIM);
        } else {
            scaledString(graphics, "可用精通点", pillX + 12, pillY + 5, 0.68f, DIM, false);
            scaledString(graphics, String.valueOf(draftAvailable()), pillX + 12,
                    pillY + 14, 1.25f, GOLD, false);
            scaledStringRight(graphics, "总点数 " + MasteryClientData.total, pillX + pillW - 12,
                    pillY + 5, 0.62f, DIM);
            scaledStringRight(graphics, "已用 " + draftUsed(), pillX + pillW - 12,
                    pillY + 15, 0.62f, DIM);
        }

        int closeX = closeX();
        int closeY = closeY();
        boolean closeHovered = inRect(mouseX, mouseY, closeX, closeY, 18, 18);
        graphics.fill(closeX, closeY, closeX + 18, closeY + 18,
                closeHovered ? 0xFF3A1A1A : 0x80141B28);
        border(graphics, closeX, closeY, closeX + 18, closeY + 18,
                closeHovered ? 0xFFE25A55 : 0xFF3A4864);
        drawCentered(graphics, "×", closeX + 9, closeY + 5,
                closeHovered ? 0xFFFFDDDD : DIM);
    }

    private List<Component> drawColumns(GuiGraphics graphics, int mouseX, int mouseY) {
        List<Component> tooltip = null;
        int cardTop = panelY + (compact ? 34 : 44);
        int cardBottom = panelY + panelH - footerH + 2;

        for (int treeIndex = 0; treeIndex < TREE_COUNT; treeIndex++) {
            MasteryTree tree = MasteryTree.byIndex(treeIndex);
            int[] points = draft[treeIndex];
            int colX = columnX(treeIndex);
            int centerX = colX + columnW / 2;
            int emblemY = panelY + (compact ? 50 : 64);

            graphics.fill(colX, cardTop, colX + columnW, cardBottom, 0x70101724);
            border(graphics, colX, cardTop, colX + columnW, cardBottom, COLUMN_BORDER);
            graphics.fill(colX, cardTop, colX + columnW, cardTop + 3, tree.getColor());

            // 路径徽记
            if (inCircle(mouseX, mouseY, centerX, emblemY, emblemRadius) && !warningOpen) {
                tooltip = treeTooltip(treeIndex);
            }
            blitCell(graphics, treeIndex, 1, centerX, emblemY, emblemRadius * 2 + 6);
            blitCell(graphics, CELL_FILL, 0, centerX, emblemY, emblemRadius * 2);
            blitCell(graphics, treeIndex, 2, centerX, emblemY, emblemRadius * 2);
            drawCentered(graphics, tree.getSigil(), centerX, emblemY - 4, tree.getColor());
            if (compact) {
                drawCenteredScaled(graphics, tree.getDisplayName(), centerX, emblemY + 15, 0.8f,
                        tree.getColor());
                drawCenteredScaled(graphics,
                        MasteryAllocation.treeTotal(points) + " / " + MasteryAllocation.TREE_CAP,
                        centerX, emblemY + 27, 0.58f, DIM);
            } else {
                drawCentered(graphics, tree.getDisplayName(), centerX, emblemY + 22, tree.getColor());
                drawCenteredScaled(graphics,
                        MasteryAllocation.treeTotal(points) + " / " + MasteryAllocation.TREE_CAP,
                        centerX, emblemY + 34, 0.7f, DIM);
            }

            // 连接线（空间不足时自动隐藏）
            for (int row = 0; row < ROW_COUNT - 1; row++) {
                boolean full = MasteryAllocation.rowTotal(points, row) == MasteryAllocation.rowCap(row);
                int lineTop = nodeRowY(row) + radiusOf(row) + (compact ? 11 : 15);
                int lineBottom = nodeRowY(row + 1) - radiusOf(row + 1) - (compact ? 3 : 4);
                if (lineBottom > lineTop) {
                    graphics.fill(centerX - 1, lineTop, centerX + 1, lineBottom,
                            full ? withAlpha(tree.getColor(), 0x99) : 0xFF1B2333);
                }
            }

            // 每排节点
            for (int row = 0; row < ROW_COUNT; row++) {
                boolean unlocked = MasteryAllocation.rowUnlocked(points, row);
                int nodeY = nodeRowY(row);
                int count = MasteryAllocation.rowNodeCount(row);
                int radius = radiusOf(row);
                if (row == ROW_COUNT - 1) {
                    blitCell(graphics, treeIndex, 1, centerX, nodeY, keystoneRadius * 2 + 8);
                    blitCell(graphics, treeIndex, 3, centerX, nodeY, keystoneRadius * 2 + 6);
                    // 基石排主界面只显示当前选中的那一枚，点击后打开完整选择界面
                    int total = MasteryAllocation.rowTotal(points, ROW_COUNT - 1);
                    boolean hovered = inCircle(mouseX, mouseY, centerX, nodeY, keystoneRadius + 4)
                            && !warningOpen && !keystoneOpen;
                    drawNode(graphics, centerX, nodeY, keystoneRadius, total, tree, unlocked, hovered);
                    if (hovered) {
                        tooltip = keystoneTooltip(treeIndex);
                    }
                } else {
                    for (int node = 0; node < count; node++) {
                        int nodeX = nodeCenterX(treeIndex, row, node);
                        int allocated = points[MasteryAllocation.index(row, node)];
                        boolean hovered = inCircle(mouseX, mouseY, nodeX, nodeY, radius + 4)
                                && !warningOpen && !keystoneOpen;
                        drawNode(graphics, nodeX, nodeY, radius, allocated, tree, unlocked, hovered);
                        if (hovered) {
                            tooltip = nodeTooltip(treeIndex, row, node);
                        }
                    }
                }
                int sum = MasteryAllocation.rowTotal(points, row);
                int cap = MasteryAllocation.rowCap(row);
                boolean invalid = !unlocked && sum > 0;
                int labelColor = invalid ? 0xFFE25A55
                        : (sum == cap ? tree.getColor() : (sum > 0 ? 0xFFB9C4D8 : FAINT));
                drawCenteredScaled(graphics, sum + " / " + cap, centerX,
                        nodeY + radius + (compact ? 3 : 5), labelScale, labelColor);
            }
        }
        return tooltip;
    }

    private void drawNode(GuiGraphics graphics, int centerX, int centerY, int radius, int allocated,
                          MasteryTree tree, boolean unlocked, boolean hovered) {
        boolean filled = allocated > 0;
        int ringSize = radius * 2;
        if (filled) {
            blitCell(graphics, tree.ordinal(), 1, centerX, centerY, radius * 2 + 4);
        }
        if (hovered) {
            blitCell(graphics, CELL_GLOW_WHITE, 0, centerX, centerY, radius * 2 + 6);
        }
        blitCell(graphics, CELL_FILL, 0, centerX, centerY, ringSize);
        if (filled) {
            blitCell(graphics, tree.ordinal(), 2, centerX, centerY, ringSize);
        } else {
            blitCell(graphics, unlocked ? CELL_RING_UNLOCKED : CELL_RING_LOCKED, 0,
                    centerX, centerY, ringSize);
        }
        if (hovered) {
            blitCell(graphics, CELL_RING_WHITE, 0, centerX, centerY, radius * 2 + 4);
        }
        if (filled) {
            drawCenteredScaled(graphics, String.valueOf(allocated), centerX, centerY - 3,
                    nodeTextScale, 0xFFF2F6FF);
        } else if (unlocked) {
            blitCell(graphics, tree.ordinal(), 1, centerX, centerY, 8);
        } else {
            drawCentered(graphics, "·", centerX, centerY - 4, FAINT);
        }
    }

    private void drawFooter(GuiGraphics graphics) {
        drawCenteredScaled(graphics, "左键 +1 · 右键 -1 · 滚轮 ±1",
                this.width / 2, panelY + panelH - (compact ? 19 : 22),
                compact ? 0.55f : 0.65f, DIM);
    }

    private void drawFooterButtons(GuiGraphics graphics, int mouseX, int mouseY) {
        boolean dirty = isDirty();
        drawButton(graphics, resetButton(), "重置", 0xFF3A4864, false, mouseX, mouseY);
        drawButton(graphics, saveButton(), "保存", dirty ? GOLD : 0xFF3A4864, dirty, mouseX, mouseY);
    }

    private void drawKeystoneModal(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(0, 0, this.width, this.height, 0xAA000000);
        MasteryTree tree = MasteryTree.byIndex(keystoneTree);
        int x = keystoneCardX();
        int y = keystoneCardY();
        int w = keystoneCardW();
        int h = keystoneCardH();
        graphics.fillGradient(x, y, x + w, y + h, 0xFF101827, 0xFF0A0F18);
        border(graphics, x, y, x + w, y + h, 0xFF3A4864);
        graphics.fill(x, y, x + w, y + 3, tree.getColor());

        scaledString(graphics, "选择基石", x + (compact ? 14 : 18), y + (compact ? 9 : 12),
                compact ? 1.0f : 1.15f, tree.getColor(), false);
        int[] points = draft[keystoneTree];
        int total = MasteryAllocation.rowTotal(points, ROW_COUNT - 1);
        scaledStringRight(graphics, "本排 " + total + " / " + MasteryAllocation.rowCap(ROW_COUNT - 1),
                x + w - (compact ? 14 : 18), y + (compact ? 10 : 14), compact ? 0.68f : 0.76f,
                total == MasteryAllocation.rowCap(ROW_COUNT - 1) ? tree.getColor() : TEXT);
        scaledStringRight(graphics, "可用精通点 " + draftAvailable(),
                x + w - (compact ? 14 : 18), y + (compact ? 23 : 28), compact ? 0.58f : 0.64f, DIM);
        scaledString(graphics, "左键选择基石 · 右键清空 · 滚轮切换",
                x + (compact ? 14 : 18), y + (compact ? 32 : 40), compact ? 0.56f : 0.64f, DIM, false);
        scaledString(graphics, "确定后仍需在主界面点「保存」",
                x + (compact ? 14 : 18), y + (compact ? 43 : 52), compact ? 0.52f : 0.58f, FAINT, false);

        int count = keystoneOptionCount();
        for (int i = 0; i < count; i++) {
            int ox = keystoneOptionCenterX(i);
            int oy = keystoneOptionCenterY(i);
            int allocated = points[MasteryAllocation.index(ROW_COUNT - 1, i)];
            boolean hovered = inCircle(mouseX, mouseY, ox, oy, keystoneOptionRadius() + 4);
            drawNode(graphics, ox, oy, keystoneOptionRadius(), allocated, tree, true, hovered);
            if (allocated > 0) {
                blitCell(graphics, CELL_RING_WHITE, 0, ox, oy, keystoneOptionRadius() * 2 + 4);
            }
            drawCenteredScaled(graphics, MasteryAllocation.nodeName(ROW_COUNT - 1, i), ox,
                    oy + keystoneOptionRadius() + (compact ? 4 : 6), compact ? 0.6f : 0.68f,
                    allocated > 0 ? 0xFFFFFFFF : DIM);
        }

        int[] clear = keystoneClearButton();
        drawButton(graphics, clear, "清空", 0xFF3A4864, false, mouseX, mouseY);
        int[] cancel = keystoneCancelButton();
        drawButton(graphics, cancel, "取消", 0xFF3A4864, false, mouseX, mouseY);
        int[] confirm = keystoneConfirmButton();
        drawButton(graphics, confirm, "确定", tree.getColor(), true, mouseX, mouseY);
    }

    private void drawWarning(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(0, 0, this.width, this.height, 0xAA000000);
        int w = warningW();
        int h = warningH();
        int x = warningX();
        int y = warningY();
        graphics.fillGradient(x, y, x + w, y + h, 0xFF1A1420, 0xFF0C0A10);
        border(graphics, x, y, x + w, y + h, 0xFF8A3A3A);
        graphics.fill(x, y, x + w, y + 3, 0xFFE25A55);

        drawCentered(graphics, "保存确认", x + w / 2, y + (compact ? 12 : 16), 0xFFFFD9D9);
        drawCenteredScaled(graphics, "当前调整会使后续排不满足前置条件。",
                x + w / 2, y + (compact ? 32 : 42), compact ? 0.72f : 0.82f, TEXT);
        drawCenteredScaled(graphics, "保存时将清空 " + warningCleared + " 点精通。",
                x + w / 2, y + (compact ? 48 : 57), compact ? 0.72f : 0.82f, 0xFFE25A55);
        drawCenteredScaled(graphics, "是否继续保存？",
                x + w / 2, y + (compact ? 64 : 74), compact ? 0.72f : 0.82f, DIM);

        int[] cancelButton = warningCancelButton();
        drawButton(graphics, cancelButton, "取消", 0xFF3A4864, false, mouseX, mouseY);
        int[] continueButton = warningContinueButton();
        drawButton(graphics, continueButton, "保存", 0xFFE25A55, true, mouseX, mouseY);
    }

    private void drawButton(GuiGraphics graphics, int[] rect, String label, int accent,
                            boolean primary, int mouseX, int mouseY) {
        boolean hovered = inRect(mouseX, mouseY, rect[0], rect[1], rect[2], rect[3]);
        graphics.fill(rect[0], rect[1], rect[0] + rect[2], rect[1] + rect[3],
                hovered ? 0xFF20293A : (primary ? 0xFF151D2B : 0xFF10161F));
        border(graphics, rect[0], rect[1], rect[0] + rect[2], rect[1] + rect[3],
                hovered ? accent : (primary ? withAlpha(accent, 0x88) : 0xFF2C3850));
        drawCentered(graphics, label, rect[0] + rect[2] / 2, rect[1] + rect[3] / 2 - 4,
                hovered ? 0xFFFFFFFF : (primary ? TEXT : DIM));
    }

    private void drawNotice(GuiGraphics graphics) {
        if (notice == null) {
            return;
        }
        if (System.currentTimeMillis() > noticeUntil) {
            notice = null;
            return;
        }
        int w = this.font.width(notice) + 24;
        int x = this.width / 2 - w / 2;
        int y = panelY + (compact ? 34 : 56);
        graphics.fill(x, y, x + w, y + 18, 0xE01A2030);
        border(graphics, x, y, x + w, y + 18, 0xFF7A5A20);
        drawCentered(graphics, notice, x + w / 2, y + 5, GOLD);
    }

    // ------------------------------------------------------------------
    // tooltips
    // ------------------------------------------------------------------

    private List<Component> treeTooltip(int treeIndex) {
        MasteryTree tree = MasteryTree.byIndex(treeIndex);
        List<Component> lines = new ArrayList<>();
        lines.add(Component.literal(tree.getDisplayName())
                .withStyle(Style.EMPTY.withColor(tree.getColor()))
                .append(Component.literal("   精通路径").withStyle(ChatFormatting.DARK_GRAY)));
        lines.add(Component.literal(tree.getDescription())
                .withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        lines.add(Component.empty());
        lines.add(Component.literal("已投入 "
                + MasteryAllocation.treeTotal(draft[treeIndex])
                + " / " + MasteryAllocation.TREE_CAP + " 点").withStyle(ChatFormatting.WHITE));
        lines.add(Component.literal("点满一排后，才能给下一排加点").withStyle(ChatFormatting.DARK_GRAY));
        return lines;
    }

    private List<Component> nodeTooltip(int treeIndex, int row, int node) {
        MasteryTree tree = MasteryTree.byIndex(treeIndex);
        int[] points = draft[treeIndex];
        int allocated = points[MasteryAllocation.index(row, node)];
        int sum = MasteryAllocation.rowTotal(points, row);
        int cap = MasteryAllocation.rowCap(row);
        boolean unlocked = MasteryAllocation.rowUnlocked(points, row);

        List<Component> lines = new ArrayList<>();
        lines.add(Component.literal(tree.getDisplayName() + " · " + MasteryAllocation.rowName(row)
                        + " · " + MasteryAllocation.nodeName(row, node))
                .withStyle(Style.EMPTY.withColor(tree.getColor())));
        lines.add(Component.literal("已投入 " + allocated + " 点").withStyle(ChatFormatting.WHITE));
        lines.add(Component.empty());
        lines.add(Component.literal("本排进度 " + sum + " / " + cap)
                .withStyle(sum == cap ? ChatFormatting.GREEN : ChatFormatting.GRAY));
        if (!unlocked) {
            lines.add(Component.literal("尚未解锁：需先点满"
                            + MasteryAllocation.rowName(row - 1) + "（"
                            + MasteryAllocation.rowCap(row - 1) + " 点）")
                    .withStyle(ChatFormatting.RED));
            lines.add(Component.literal("左键可在同排选项间转移点数，右键减少").withStyle(ChatFormatting.GOLD));
            if (allocated > 0) {
                lines.add(Component.literal("保存时会清空本排及后续排").withStyle(ChatFormatting.RED));
            }
        } else {
            lines.add(Component.literal("左键 +1 · 右键 -1 · 滚轮 ±1").withStyle(ChatFormatting.GOLD));
            lines.add(Component.literal("非最后一排：左键会从同排其他选项转移点数")
                    .withStyle(ChatFormatting.DARK_GRAY));
            lines.add(Component.literal("最后一排：优先消耗剩余精通点").withStyle(ChatFormatting.DARK_GRAY));
        }
        lines.add(Component.literal("效果：按路径总点数换算（详见身份卡）").withStyle(ChatFormatting.DARK_GRAY));
        return lines;
    }

    private int selectedKeystoneIndex(int[] points) {
        int count = MasteryAllocation.rowNodeCount(ROW_COUNT - 1);
        for (int i = 0; i < count; i++) {
            if (points[MasteryAllocation.index(ROW_COUNT - 1, i)] > 0) {
                return i;
            }
        }
        return 0;
    }

    private List<Component> keystoneTooltip(int treeIndex) {
        MasteryTree tree = MasteryTree.byIndex(treeIndex);
        int[] points = draft[treeIndex];
        int total = MasteryAllocation.rowTotal(points, ROW_COUNT - 1);
        int selected = selectedKeystoneIndex(points);
        boolean unlocked = MasteryAllocation.rowUnlocked(points, ROW_COUNT - 1);
        List<Component> lines = new ArrayList<>();
        lines.add(Component.literal("基石 · " + tree.getDisplayName())
                .withStyle(Style.EMPTY.withColor(tree.getColor())));
        lines.add(Component.literal(total > 0
                        ? "当前：" + MasteryAllocation.nodeName(ROW_COUNT - 1, selected)
                        : "尚未选择基石")
                .withStyle(ChatFormatting.WHITE));
        lines.add(Component.literal("本排进度 " + total + " / "
                        + MasteryAllocation.rowCap(ROW_COUNT - 1))
                .withStyle(total == MasteryAllocation.rowCap(ROW_COUNT - 1)
                        ? ChatFormatting.GREEN : ChatFormatting.GRAY));
        lines.add(Component.empty());
        if (!unlocked) {
            lines.add(Component.literal("尚未解锁：需先点满"
                            + MasteryAllocation.rowName(ROW_COUNT - 2) + "（"
                            + MasteryAllocation.rowCap(ROW_COUNT - 2) + " 点）")
                    .withStyle(ChatFormatting.RED));
        }
        lines.add(Component.literal("左键打开基石选择界面").withStyle(ChatFormatting.GOLD));
        lines.add(Component.literal("后续可扩展到多个基石选项").withStyle(ChatFormatting.DARK_GRAY));
        return lines;
    }

    // ------------------------------------------------------------------
    // input
    // ------------------------------------------------------------------

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (warningOpen) {
            if (inRect(mouseX, mouseY, warningContinueButton())) {
                warningOpen = false;
                draft = MasteryAllocation.sanitize(draft, MasteryClientData.total);
                commitDraft();
                return true;
            }
            if (inRect(mouseX, mouseY, warningCancelButton())) {
                warningOpen = false;
            }
            return true;
        }

        if (keystoneOpen) {
            if (inRect(mouseX, mouseY, keystoneClearButton())) {
                clearKeystone();
                return true;
            }
            if (inRect(mouseX, mouseY, keystoneCancelButton())) {
                closeKeystone(false);
                return true;
            }
            if (inRect(mouseX, mouseY, keystoneConfirmButton())) {
                closeKeystone(true);
                return true;
            }
            int option = keystoneOptionAt(mouseX, mouseY);
            if (option >= 0) {
                if (button == 0) {
                    selectKeystone(option);
                } else if (button == 1) {
                    clearKeystone();
                }
            }
            return true;
        }

        if (inRect(mouseX, mouseY, closeX(), closeY(), 18, 18)) {
            this.onClose();
            return true;
        }
        if (inRect(mouseX, mouseY, resetButton())) {
            resetDraft();
            return true;
        }
        if (inRect(mouseX, mouseY, saveButton())) {
            saveDraft();
            return true;
        }

        if (!MasteryClientData.synced) {
            showNotice("正在同步精通数据，请稍候");
            return true;
        }

        int[] hit = nodeAt(mouseX, mouseY);
        if (hit != null) {
            if (hit[1] == ROW_COUNT - 1) {
                openKeystoneModal(hit[0]);
            } else if (button == 0) {
                increasePoint(hit[0], hit[1], hit[2]);
            } else if (button == 1) {
                decreasePoint(hit[0], hit[1], hit[2]);
            }
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (warningOpen) {
            return true;
        }
        if (keystoneOpen) {
            int option = keystoneOptionAt(mouseX, mouseY);
            if (option >= 0 && keystoneOptionCount() > 1) {
                int count = keystoneOptionCount();
                int next = (option + (delta > 0 ? 1 : -1) + count) % count;
                selectKeystone(next);
            }
            return true;
        }
        int[] hit = nodeAt(mouseX, mouseY);
        if (hit != null) {
            if (hit[1] == ROW_COUNT - 1) {
                return true;
            }
            if (!MasteryClientData.synced) {
                showNotice("正在同步精通数据，请稍候");
            } else if (delta > 0) {
                increasePoint(hit[0], hit[1], hit[2]);
            } else if (delta < 0) {
                decreasePoint(hit[0], hit[1], hit[2]);
            }
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            if (warningOpen) {
                warningOpen = false;
                return true;
            }
            if (keystoneOpen) {
                closeKeystone(false);
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        if (parent != null && parent != this) {
            this.minecraft.setScreen(parent);
        } else {
            super.onClose();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // ------------------------------------------------------------------
    // editing
    // ------------------------------------------------------------------

    private void increasePoint(int treeIndex, int row, int node) {
        if (!MasteryClientData.synced || !MasteryAllocation.isValidNode(row, node)) {
            return;
        }
        int[] points = draft[treeIndex];
        int target = MasteryAllocation.index(row, node);
        int cap = MasteryAllocation.rowCap(row);
        int rowTotal = MasteryAllocation.rowTotal(points, row);
        int free = draftAvailable();
        boolean unlocked = MasteryAllocation.rowUnlocked(points, row);
        boolean frontier = MasteryAllocation.pointsBelow(points, row) == 0;
        int donor = findDonor(points, row, node);

        // 1. 优先补回本排刚刚减少的点数（避免“减少后再转移”重复计算）
        if (pendingRefund[treeIndex][row] > 0 && rowTotal < cap) {
            pendingRefund[treeIndex][row]--;
            points[target]++;
            return;
        }
        // 2. 非最后一排，或本排已满/没有剩余点数：同排转移
        if (donor >= 0 && (!frontier || rowTotal >= cap || free <= 0)) {
            points[MasteryAllocation.index(row, donor)]--;
            points[target]++;
            showNotice("已从「" + MasteryAllocation.nodeName(row, donor) + "」转移 1 点");
            return;
        }
        // 3. 最后一排且还有剩余点数：从池里加点
        if (frontier && rowTotal < cap && free > 0) {
            points[target]++;
            // 从公共池加点后，其他排“退回待补”的点数已不再独占，清空避免重复计算
            clearPendingRefund();
            return;
        }
        if (!unlocked) {
            showNotice("需先点满" + MasteryAllocation.rowName(row - 1) + "才能给这一排加点");
        } else if (rowTotal >= cap) {
            showNotice("本排已满，且没有可转移的点数");
        } else if (free <= 0) {
            showNotice("精通点不足，且没有可转移的点数");
        } else {
            showNotice("本排没有可转移的点数");
        }
    }

    private int findDonor(int[] points, int row, int target) {
        int count = MasteryAllocation.rowNodeCount(row);
        for (int i = 0; i < count; i++) {
            if (i != target && points[MasteryAllocation.index(row, i)] > 0) {
                return i;
            }
        }
        return -1;
    }

    private void decreasePoint(int treeIndex, int row, int node) {
        if (!MasteryClientData.synced || !MasteryAllocation.isValidNode(row, node)) {
            return;
        }
        int[] points = draft[treeIndex];
        int index = MasteryAllocation.index(row, node);
        if (points[index] <= 0) {
            return;
        }
        points[index]--;
        pendingRefund[treeIndex][row]++;
        if (!MasteryAllocation.rowUnlocked(points, row)
                && MasteryAllocation.pointsBelow(points, row) > 0) {
            showNotice("后续排将不满足前置，保存时会清空");
        }
    }

    private void clearPendingRefund() {
        for (int tree = 0; tree < TREE_COUNT; tree++) {
            for (int row = 0; row < ROW_COUNT; row++) {
                pendingRefund[tree][row] = 0;
            }
        }
    }

    private void openKeystoneModal(int treeIndex) {
        if (!MasteryClientData.synced) {
            showNotice("正在同步精通数据，请稍候");
            return;
        }
        keystoneOpen = true;
        keystoneTree = treeIndex;
        keystoneBackup = MasteryAllocation.copy(draft);
    }

    private void closeKeystone(boolean apply) {
        if (!apply && keystoneBackup != null) {
            draft = MasteryAllocation.copy(keystoneBackup);
        }
        if (keystoneTree >= 0) {
            pendingRefund[keystoneTree][ROW_COUNT - 1] = 0;
        }
        keystoneOpen = false;
        keystoneTree = -1;
        keystoneBackup = null;
    }

    private void selectKeystone(int option) {
        if (keystoneTree < 0
                || !MasteryAllocation.isValidNode(ROW_COUNT - 1, option)) {
            return;
        }
        int[] points = draft[keystoneTree];
        if (!MasteryAllocation.rowUnlocked(points, ROW_COUNT - 1)) {
            showNotice("需先点满" + MasteryAllocation.rowName(ROW_COUNT - 2) + "才能选择基石");
            return;
        }
        int oldTotal = MasteryAllocation.rowTotal(points, ROW_COUNT - 1);
        int available = draftAvailable() + oldTotal;
        int newPoints = Math.min(MasteryAllocation.rowCap(ROW_COUNT - 1), available);
        for (int i = 0; i < MasteryAllocation.MAX_NODES_PER_ROW; i++) {
            points[MasteryAllocation.index(ROW_COUNT - 1, i)] = 0;
        }
        points[MasteryAllocation.index(ROW_COUNT - 1, option)] = newPoints;
        pendingRefund[keystoneTree][ROW_COUNT - 1] = 0;
        if (newPoints <= 0) {
            showNotice("精通点不足");
        } else if (newPoints < MasteryAllocation.rowCap(ROW_COUNT - 1)) {
            showNotice("可用精通点不足，已投入 " + newPoints + " 点");
        }
    }

    private void clearKeystone() {
        if (keystoneTree < 0) {
            return;
        }
        int[] points = draft[keystoneTree];
        for (int i = 0; i < MasteryAllocation.MAX_NODES_PER_ROW; i++) {
            points[MasteryAllocation.index(ROW_COUNT - 1, i)] = 0;
        }
        pendingRefund[keystoneTree][ROW_COUNT - 1] = 0;
        showNotice("已清空基石");
    }

    private int draftUsed() {
        return MasteryAllocation.used(draft);
    }

    private int draftAvailable() {
        return Math.max(0, MasteryClientData.total - draftUsed());
    }

    private boolean isDirty() {
        for (int tree = 0; tree < MasteryAllocation.TREE_COUNT; tree++) {
            for (int i = 0; i < MasteryAllocation.NODES_PER_TREE; i++) {
                if (draft[tree][i] != baseline[tree][i]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void resetDraft() {
        if (!isDirty()) {
            showNotice("当前没有未保存的调整");
            return;
        }
        draft = MasteryAllocation.copy(baseline);
        clearPendingRefund();
        showNotice("已重置为调整前的点数");
    }

    private void saveDraft() {
        if (!MasteryClientData.synced) {
            showNotice("正在同步精通数据，请稍候");
            return;
        }
        if (!isDirty()) {
            showNotice("没有需要保存的调整");
            return;
        }
        int[][] clean = MasteryAllocation.sanitize(draft, MasteryClientData.total);
        int removed = MasteryAllocation.used(draft) - MasteryAllocation.used(clean);
        if (removed > 0) {
            warningCleared = removed;
            warningOpen = true;
            return;
        }
        draft = clean;
        commitDraft();
    }

    private void commitDraft() {
        ModNetworking.sendToServer(new MasterySaveC2SPacket(draft));
        baseline = MasteryAllocation.copy(draft);
        clearPendingRefund();
        showNotice("已保存");
    }

    private void showNotice(String text) {
        this.notice = text;
        this.noticeUntil = System.currentTimeMillis() + 2200;
    }

    // ------------------------------------------------------------------
    // geometry
    // ------------------------------------------------------------------

    private int columnX(int treeIndex) {
        return panelX + panelPad + treeIndex * (columnW + columnGap);
    }

    private int rowY(int row) {
        return rowsY + row * rowStep;
    }

    /** 基石的显示位置：比正常网格略微下移，避免压住第五排的进度数字 */
    private int nodeRowY(int row) {
        return row == ROW_COUNT - 1 ? rowY(row) + 6 : rowY(row);
    }

    private int radiusOf(int row) {
        return row == ROW_COUNT - 1 ? keystoneRadius : nodeRadius;
    }

    private double nodeSpacing(int count) {
        if (count <= 1) {
            return 0;
        }
        double max = count == 2 ? 44 : 30;
        double available = columnW - 2.0 * nodeRadius - 10;
        return Math.min(max, available / (count - 1));
    }

    private int nodeCenterX(int treeIndex, int row, int node) {
        int count = MasteryAllocation.rowNodeCount(row);
        double center = columnX(treeIndex) + columnW / 2.0;
        return (int) Math.round(center + (node - (count - 1) / 2.0) * nodeSpacing(count));
    }

    private int[] nodeAt(double mouseX, double mouseY) {
        for (int treeIndex = 0; treeIndex < TREE_COUNT; treeIndex++) {
            for (int row = 0; row < ROW_COUNT; row++) {
                if (row == ROW_COUNT - 1) {
                    int centerX = columnX(treeIndex) + columnW / 2;
                    if (inCircle(mouseX, mouseY, centerX, nodeRowY(row), keystoneRadius + 4)) {
                        return new int[]{treeIndex, row, selectedKeystoneIndex(draft[treeIndex])};
                    }
                    continue;
                }
                int count = MasteryAllocation.rowNodeCount(row);
                int radius = radiusOf(row) + 4;
                for (int node = 0; node < count; node++) {
                    if (inCircle(mouseX, mouseY, nodeCenterX(treeIndex, row, node),
                            nodeRowY(row), radius)) {
                        return new int[]{treeIndex, row, node};
                    }
                }
            }
        }
        return null;
    }

    private int[] resetButton() {
        int w = compact ? 56 : 66;
        int h = compact ? 18 : 20;
        return new int[]{panelX + panelPad, panelY + panelH - (compact ? 24 : 28), w, h};
    }

    private int[] saveButton() {
        int w = compact ? 56 : 66;
        int h = compact ? 18 : 20;
        return new int[]{panelX + panelW - panelPad - w,
                panelY + panelH - (compact ? 24 : 28), w, h};
    }

    private int keystoneOptionCount() {
        return MasteryAllocation.rowNodeCount(ROW_COUNT - 1);
    }

    private int keystoneCols() {
        return Math.min(3, keystoneOptionCount());
    }

    private int keystoneRows() {
        int cols = keystoneCols();
        return (keystoneOptionCount() + cols - 1) / cols;
    }

    private int keystoneOptionRadius() {
        return compact ? 15 : 19;
    }

    private int keystoneStepX() {
        return compact ? 92 : 112;
    }

    private int keystoneStepY() {
        return compact ? 74 : 88;
    }

    private int keystoneHeaderH() {
        return compact ? 62 : 74;
    }

    private int keystoneCardW() {
        int min = compact ? 330 : 400;
        return Math.min(panelW - 20, Math.max(min, keystoneCols() * keystoneStepX() + 40));
    }

    private int keystoneCardH() {
        return keystoneHeaderH() + keystoneOptionRadius() * 2
                + (keystoneRows() - 1) * keystoneStepY() + (compact ? 42 : 48);
    }

    private int keystoneCardX() {
        return (this.width - keystoneCardW()) / 2;
    }

    private int keystoneCardY() {
        return (this.height - keystoneCardH()) / 2;
    }

    private int keystoneOptionCenterX(int index) {
        int cols = keystoneCols();
        int col = index % cols;
        return keystoneCardX() + keystoneCardW() / 2
                + (int) Math.round((col - (cols - 1) / 2.0) * keystoneStepX());
    }

    private int keystoneOptionCenterY(int index) {
        int cols = keystoneCols();
        int row = index / cols;
        return keystoneCardY() + keystoneHeaderH() + keystoneOptionRadius()
                + row * keystoneStepY();
    }

    private int keystoneOptionAt(double mouseX, double mouseY) {
        for (int i = 0; i < keystoneOptionCount(); i++) {
            if (inCircle(mouseX, mouseY, keystoneOptionCenterX(i), keystoneOptionCenterY(i),
                    keystoneOptionRadius() + 4)) {
                return i;
            }
        }
        return -1;
    }

    private int[] keystoneClearButton() {
        int w = compact ? 50 : 58;
        int h = compact ? 20 : 22;
        return new int[]{keystoneCardX() + (compact ? 14 : 18),
                keystoneCardY() + keystoneCardH() - (compact ? 26 : 30), w, h};
    }

    private int[] keystoneConfirmButton() {
        int w = compact ? 56 : 64;
        int h = compact ? 20 : 22;
        int x = keystoneCardX() + keystoneCardW() - (compact ? 14 : 18) - w;
        int y = keystoneCardY() + keystoneCardH() - (compact ? 26 : 30);
        return new int[]{x, y, w, h};
    }

    private int[] keystoneCancelButton() {
        int w = compact ? 50 : 58;
        int h = compact ? 20 : 22;
        int confirmX = keystoneCardX() + keystoneCardW() - (compact ? 14 : 18)
                - (compact ? 56 : 64);
        int y = keystoneCardY() + keystoneCardH() - (compact ? 26 : 30);
        return new int[]{confirmX - 8 - w, y, w, h};
    }

    private int warningW() {
        return Math.min(410, this.width - 24);
    }

    private int warningH() {
        return compact ? 140 : 156;
    }

    private int warningX() {
        return (this.width - warningW()) / 2;
    }

    private int warningY() {
        return (this.height - warningH()) / 2;
    }

    private int[] warningContinueButton() {
        int w = compact ? 64 : 78;
        int h = compact ? 20 : 22;
        int cancelW = compact ? 52 : 62;
        int gap = compact ? 8 : 10;
        int total = cancelW + gap + w;
        int x = warningX() + (warningW() - total) / 2 + cancelW + gap;
        int y = warningY() + warningH() - (compact ? 26 : 34);
        return new int[]{x, y, w, h};
    }

    private int[] warningCancelButton() {
        int w = compact ? 52 : 62;
        int h = compact ? 20 : 22;
        int confirmW = compact ? 64 : 78;
        int gap = compact ? 8 : 10;
        int total = w + gap + confirmW;
        int x = warningX() + (warningW() - total) / 2;
        int y = warningY() + warningH() - (compact ? 26 : 34);
        return new int[]{x, y, w, h};
    }

    // ------------------------------------------------------------------
    // drawing helpers
    // ------------------------------------------------------------------

    private static boolean inRect(double mouseX, double mouseY, int x, int y, int w, int h) {
        return mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
    }

    private static boolean inRect(double mouseX, double mouseY, int[] rect) {
        return inRect(mouseX, mouseY, rect[0], rect[1], rect[2], rect[3]);
    }

    private static boolean inCircle(double mouseX, double mouseY, double centerX, double centerY,
                                    double radius) {
        double dx = mouseX - centerX;
        double dy = mouseY - centerY;
        return dx * dx + dy * dy <= radius * radius;
    }

    private static int withAlpha(int color, int alpha) {
        return (alpha << 24) | (color & 0xFFFFFF);
    }

    private void drawCentered(GuiGraphics graphics, String text, int centerX, int y, int color) {
        graphics.drawString(this.font, text, centerX - this.font.width(text) / 2, y, color, false);
    }

    private void drawCenteredScaled(GuiGraphics graphics, String text, int centerX, int y,
                                    float scale, int color) {
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(centerX, y, 0);
        pose.scale(scale, scale, 1.0f);
        graphics.drawString(this.font, text, -this.font.width(text) / 2, 0, color, false);
        pose.popPose();
    }

    private void scaledCentered(GuiGraphics graphics, String text, int centerX, int y,
                                float scale, int color) {
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(centerX, y, 0);
        pose.scale(scale, scale, 1.0f);
        graphics.drawString(this.font, text, -this.font.width(text) / 2, 0, color, false);
        pose.popPose();
    }

    private void scaledString(GuiGraphics graphics, String text, int x, int y, float scale,
                              int color, boolean centered) {
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(x, y, 0);
        pose.scale(scale, scale, 1.0f);
        graphics.drawString(this.font, text, centered ? -this.font.width(text) / 2 : 0, 0,
                color, false);
        pose.popPose();
    }

    private void scaledStringRight(GuiGraphics graphics, String text, int rightX, int y,
                                   float scale, int color) {
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(rightX, y, 0);
        pose.scale(scale, scale, 1.0f);
        graphics.drawString(this.font, text, -this.font.width(text), 0, color, false);
        pose.popPose();
    }

    private static void border(GuiGraphics graphics, int x1, int y1, int x2, int y2, int color) {
        graphics.fill(x1, y1, x2, y1 + 1, color);
        graphics.fill(x1, y2 - 1, x2, y2, color);
        graphics.fill(x1, y1, x1 + 1, y2, color);
        graphics.fill(x2 - 1, y1, x2, y2, color);
    }

    /**
     * 从圆环图集中取一格绘制到中心点；图集带 blur，缩放后是平滑的。
     */
    private static void blitCell(GuiGraphics graphics, int column, int row, int centerX, int centerY,
                                 int size) {
        if (size <= 0) {
            return;
        }
        int x = centerX - size / 2;
        int y = centerY - size / 2;
        graphics.blit(CIRCLE_TEXTURE, x, y, size, size,
                (float) (column * ATLAS_CELL), (float) (row * ATLAS_CELL),
                ATLAS_CELL, ATLAS_CELL, ATLAS_WIDTH, ATLAS_HEIGHT);
    }
}
