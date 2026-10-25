package fun.wraq.render.gui.illustrate;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.render.gui.illustrate.mobinfo.MobInfoGui;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

/**
 * AI-Generated, 2025-10-18
 * <p>
 * 物品图鉴：两列五排展示物品（系列物品聚合为一条并显示系列名）。
 * 每条目提供「系列」与「来源」按钮：
 * 「系列」子窗口浏览同系列其他物品；「来源」子窗口从代码中检索并展示制作材料、灌注配方与获取途径。
 * 子窗口内的物品保留原有 tooltip 特性，且点击任意位置即可收起子窗口。
 */
@OnlyIn(Dist.CLIENT)
public class Illustrate extends Screen {
    public static final String DISPLAY_FLAG = "illustrate_display_flag";
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font FONT = mc.font;

    private static final int PANEL_WIDTH = 424;
    private static final int PANEL_HEIGHT = 226;
    private static final int PANEL_TOP = 26;
    private static final int CARD_WIDTH = 196;
    private static final int CARD_HEIGHT = 32;
    private static final int CARD_GAP_X = 8;
    private static final int CARD_GAP_Y = 4;
    private static final int SERIES_BUTTON_X = 106;
    private static final int SERIES_BUTTON_WIDTH = 30;
    private static final int SOURCE_BUTTON_X = 158;
    private static final int SOURCE_BUTTON_WIDTH = 32;
    private static final int CARD_BUTTON_Y = 18;
    private static final int CARD_BUTTON_HEIGHT = 12;
    private static final int SUB_COLUMNS = 8;
    private static final int SUB_SPACING = 30;
    private static final int SUB_ROWS_MAX = 6;

    private static final int PANEL_BG_TOP = 0xE8F6FAFD;
    private static final int PANEL_BG_BOTTOM = 0xF7FFFFFF;
    private static final int PANEL_BORDER = 0xFF2E9BD6;
    private static final int TEXT = 0xFF1E2D44;
    private static final int DIM = 0xFF5A6B80;
    private static final int CARD = 0xE8FFFFFF;
    private static final int CARD_HOVER = 0xF4FFFFFF;
    private static final int CARD_BORDER = 0xFFB9D4E8;
    private static final int SLOT_FILL = 0xFF8C8C8C;
    private static final int SLOT_BORDER = 0xFF5A5A5A;
    private static final int SLOT_HIGHLIGHT = 0xFFAAAAAA;
    private static final int BUTTON_BG = 0xFFEAF4FB;
    private static final int BUTTON_BORDER = 0xFF7FB6D9;
    private static final int BUTTON_TEXT = 0xFF1E5A82;
    private static final int BUTTON_HOVER = 0xFF2E9BD6;

    private final boolean showPauseMenu;
    private int page = 0;
    private int type = 0;
    private int cachedType = -1;
    private List<IllustrateData.Entry> entries = List.of();
    private SubPanel subPanel;
    private int sourceScroll = 0;

    private record SubPanel(Component title, List<ItemStack> stacks,
                            List<ItemSourceInfo.Line> lines) {
    }

    public Illustrate(boolean p_96308_, int type) {
        super(p_96308_ ? Component.translatable("menu.illustrate") : Component.translatable("menu.illustrate0"));
        this.showPauseMenu = p_96308_;
        this.type = type;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
        }
    }

    private void createMenu() {
        int x = this.width / 2;
        int y = this.height / 2;
        int leftX = x - PANEL_WIDTH / 2 - 36;
        int rightX = x + PANEL_WIDTH / 2 + 8;
        int topY = y - PANEL_HEIGHT / 2;
        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) {
                page--;
            }
        }).pos(x - 44, topY + PANEL_HEIGHT - 21).size(16, 16).build());
        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < maxPage()) {
                page++;
            }
        }).pos(x + 28, topY + PANEL_HEIGHT - 21).size(16, 16).build());
        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(x + PANEL_WIDTH / 2 - 16, topY + 3).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.literal("怪物及其掉落"), (p_280814_) -> {
            this.minecraft.setScreen(new MobInfoGui());
        }).pos(rightX - 84, topY - 22).size(84, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("武器"), (p_280814_) -> {
            this.type = 0;
            this.page = 0;
        }).pos(rightX, topY + 13).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("防具"), (p_280814_) -> {
            this.type = 1;
            this.page = 0;
        }).pos(rightX, topY + 13 + 20).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("饰品"), (p_280814_) -> {
            this.type = 2;
            this.page = 0;
        }).pos(rightX, topY + 13 + 20 * 2).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("符石"), (p_280814_) -> {
            this.type = 7;
            this.page = 0;
        }).pos(rightX, topY + 13 + 20 * 3).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("宝石"), (p_280814_) -> {
            this.type = 6;
            this.page = 0;
        }).pos(rightX, topY + 13 + 20 * 4).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("制式"), (p_280814_) -> {
            this.type = 3;
            this.page = 0;
        }).pos(rightX, topY + 13 + 20 * 5).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("酿造"), (p_280814_) -> {
            this.type = 4;
            this.page = 0;
        }).pos(rightX, topY + 13 + 20 * 6).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("材料"), (p_280814_) -> {
            this.type = 5;
            this.page = 0;
        }).pos(rightX, topY + 13 + 20 * 7).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("镐子"), (p_280814_) -> {
            this.type = 17;
            this.page = 0;
        }).pos(rightX, topY + 13 + 20 * 8).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("近战", CustomStyle.styleOfPower), (p_280814_) -> {
            this.type = 8;
            this.page = 0;
        }).pos(leftX, topY + 13).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("弓", CustomStyle.styleOfFlexible), (p_280814_) -> {
            this.type = 9;
            this.page = 0;
        }).pos(leftX, topY + 13 + 20).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("法杖", CustomStyle.styleOfMana), (p_280814_) -> {
            this.type = 10;
            this.page = 0;
        }).pos(leftX, topY + 13 + 20 * 2).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("法术", CustomStyle.styleOfMana), (p_280814_) -> {
            this.type = 16;
            this.page = 0;
        }).pos(leftX, topY + 13 + 20 * 3).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("副手", CustomStyle.styleOfGold), (p_280814_) -> {
            this.type = 11;
            this.page = 0;
        }).pos(leftX, topY + 13 + 20 * 4).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("头盔", CustomStyle.styleOfStone), (p_280814_) -> {
            this.type = 12;
            this.page = 0;
        }).pos(leftX, topY + 13 + 20 * 5).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("胸甲", CustomStyle.styleOfStone), (p_280814_) -> {
            this.type = 13;
            this.page = 0;
        }).pos(leftX, topY + 13 + 20 * 6).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("护腿", CustomStyle.styleOfStone), (p_280814_) -> {
            this.type = 14;
            this.page = 0;
        }).pos(leftX, topY + 13 + 20 * 7).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("靴子", CustomStyle.styleOfStone), (p_280814_) -> {
            this.type = 15;
            this.page = 0;
        }).pos(leftX, topY + 13 + 20 * 8).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("纪念", CustomStyle.styleOfGold), (p_280814_) -> {
            this.type = 18;
            this.page = 0;
        }).pos(leftX, topY + 13 + 20 * 9).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("新品", CustomStyle.styleOfRed), (p_280814_) -> {
            this.type = 19;
            this.page = 0;
        }).pos(rightX, topY + 13 + 20 * 9).size(28, 16).build());
    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 子窗口打开时不再绘制主图鉴页面（模态），从根源上避免主界面文字/按钮压住子窗口
        if (subPanel != null) {
            guiGraphics.flush();
            this.renderSubPanel(guiGraphics, mouseX, mouseY);
            return;
        }

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelX = centerX - PANEL_WIDTH / 2;
        int panelY = centerY - PANEL_HEIGHT / 2;
        this.renderPanelBackground(guiGraphics, panelX, panelY, PANEL_WIDTH, PANEL_HEIGHT);

        List<IllustrateData.Entry> entryList = currentEntries();
        drawCenteredNoShadow(guiGraphics, Component.literal(categoryName()),
                centerX, panelY + 8, TEXT);

        ItemStack hoveredStack = null;
        if (entryList.isEmpty()) {
            drawCenteredNoShadow(guiGraphics, Component.literal("暂无物品"),
                    centerX, centerY, DIM);
        } else {
            for (int index = 0; index < IllustrateData.PER_PAGE; index++) {
                int entryIndex = page * IllustrateData.PER_PAGE + index;
                if (entryIndex >= entryList.size()) {
                    break;
                }
                int column = index % IllustrateData.COLUMNS;
                int row = index / IllustrateData.COLUMNS;
                ItemStack stack = this.renderEntry(guiGraphics, entryList.get(entryIndex),
                        entryX(panelX, column), entryY(panelY, row), mouseX, mouseY);
                if (stack != null) {
                    hoveredStack = stack;
                }
            }
        }

        drawCenteredNoShadow(guiGraphics,
                Component.literal((page + 1) + " / " + (maxPage() + 1)),
                centerX, panelY + PANEL_HEIGHT - 16, DIM);
        guiGraphics.drawString(FONT,
                Component.literal("共" + entryList.size() + "项"),
                panelX + 12, panelY + PANEL_HEIGHT - 14, DIM, false);

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        if (hoveredStack != null) {
            // 先提交已有批次，保证 tooltip 绘制在最上层
            guiGraphics.flush();
            guiGraphics.renderTooltip(FONT, hoveredStack, mouseX, mouseY);
        }
    }

    private void renderPanelBackground(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        guiGraphics.fillGradient(x, y, x + width, y + height, PANEL_BG_TOP, PANEL_BG_BOTTOM);
        border(guiGraphics, x, y, x + width, y + height, PANEL_BORDER);
    }

    private ItemStack renderEntry(GuiGraphics guiGraphics, IllustrateData.Entry entry,
                                  int x, int y, int mouseX, int mouseY) {
        boolean hover = isHover(mouseX, mouseY, x, y, CARD_WIDTH, CARD_HEIGHT);
        boolean seriesHover = entry.series()
                && isHover(mouseX, mouseY, x + SERIES_BUTTON_X, y + CARD_BUTTON_Y,
                SERIES_BUTTON_WIDTH, CARD_BUTTON_HEIGHT);
        boolean sourceHover = entry.hasSource()
                && isHover(mouseX, mouseY, x + SOURCE_BUTTON_X, y + CARD_BUTTON_Y,
                SOURCE_BUTTON_WIDTH, CARD_BUTTON_HEIGHT);
        guiGraphics.fill(x, y, x + CARD_WIDTH, y + CARD_HEIGHT, hover ? CARD_HOVER : CARD);
        border(guiGraphics, x, y, x + CARD_WIDTH, y + CARD_HEIGHT, CARD_BORDER);
        renderItemSlot(guiGraphics, x + 4, y + 6);
        guiGraphics.renderItem(entry.icon(), x + 6, y + 8);

        Component displayName = entry.displayName();
        int maxNameWidth = CARD_WIDTH - 32;
        if (FONT.width(displayName) <= maxNameWidth) {
            guiGraphics.drawString(FONT, displayName, x + 28, y + 4, TEXT, false);
        } else {
            List<FormattedCharSequence> nameLines = FONT.split(displayName, maxNameWidth);
            FormattedCharSequence firstLine = nameLines.get(0);
            guiGraphics.drawString(FONT, firstLine, x + 28, y + 4, TEXT, false);
            guiGraphics.drawString(FONT, "…", x + 28 + FONT.width(firstLine), y + 4, TEXT, false);
        }

        if (entry.series()) {
            renderSmallButton(guiGraphics, "系列", x + SERIES_BUTTON_X, y + CARD_BUTTON_Y,
                    SERIES_BUTTON_WIDTH, CARD_BUTTON_HEIGHT, seriesHover);
        }
        if (entry.hasSource()) {
            renderSmallButton(guiGraphics, "来源", x + SOURCE_BUTTON_X, y + CARD_BUTTON_Y,
                    SOURCE_BUTTON_WIDTH, CARD_BUTTON_HEIGHT, sourceHover);
        }
        return hover && !seriesHover && !sourceHover ? entry.icon() : null;
    }

    private void renderSmallButton(GuiGraphics guiGraphics, String label, int x, int y,
                                   int width, int height, boolean hover) {
        guiGraphics.fill(x, y, x + width, y + height, hover ? BUTTON_HOVER : BUTTON_BG);
        border(guiGraphics, x, y, x + width, y + height, BUTTON_BORDER);
        drawCenteredNoShadow(guiGraphics, label, x + width / 2,
                y + 2, hover ? 0xFFFFFFFF : BUTTON_TEXT);
    }

    private static void renderItemSlot(GuiGraphics guiGraphics, int x, int y) {
        // 参考旧图鉴的灰色物品槽：浅色面板上给物品图标提供稳定的底
        guiGraphics.fill(x, y, x + 20, y + 20, SLOT_FILL);
        border(guiGraphics, x, y, x + 20, y + 20, SLOT_BORDER);
        guiGraphics.fill(x + 1, y + 1, x + 19, y + 2, SLOT_HIGHLIGHT);
    }

    private static void border(GuiGraphics guiGraphics,
                               int x1, int y1, int x2, int y2, int color) {
        guiGraphics.fill(x1, y1, x2, y1 + 1, color);
        guiGraphics.fill(x1, y2 - 1, x2, y2, color);
        guiGraphics.fill(x1, y1, x1 + 1, y2, color);
        guiGraphics.fill(x2 - 1, y1, x2, y2, color);
    }

    private void renderSubPanel(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // 先把主界面（文字 / 按钮 / 物品）的所有批次提交到屏幕，
        // 再绘制子窗口背景，否则主界面的文字层会压住子窗口背景。
        guiGraphics.flush();

        if (subPanel.lines() != null) {
            this.renderSourcePanel(guiGraphics, mouseX, mouseY);
            return;
        }

        List<ItemStack> stacks = subPanel.stacks();
        int rows = Math.min(SUB_ROWS_MAX, Math.max(1, (stacks.size() + SUB_COLUMNS - 1) / SUB_COLUMNS));
        int visible = Math.min(stacks.size(), rows * SUB_COLUMNS);
        int width = SUB_COLUMNS * SUB_SPACING + 20;
        int height = 26 + rows * 26 + 16;
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int x = centerX - width / 2;
        int y = centerY - height / 2;

        // 子窗口只绘制自己的浅色面板，不额外填充/压暗边框外背景
        guiGraphics.fillGradient(x, y, x + width, y + height, PANEL_BG_TOP, PANEL_BG_BOTTOM);
        border(guiGraphics, x, y, x + width, y + height, PANEL_BORDER);
        // 背景批次提交后再绘制文字与物品
        guiGraphics.flush();

        drawCenteredNoShadow(guiGraphics, subPanel.title(), centerX, y + 8, PANEL_BORDER);
        drawCenteredNoShadow(guiGraphics, Component.literal("点击任意位置关闭"),
                centerX, y + height - 13, DIM);

        ItemStack hoveredStack = null;
        for (int index = 0; index < visible; index++) {
            int column = index % SUB_COLUMNS;
            int row = index / SUB_COLUMNS;
            int itemX = x + 10 + column * SUB_SPACING;
            int itemY = y + 24 + row * 26;
            ItemStack stack = stacks.get(index);
            renderItemSlot(guiGraphics, itemX - 2, itemY - 2);
            guiGraphics.renderItem(stack, itemX, itemY);
            if (isHover(mouseX, mouseY, itemX, itemY, 16, 16)) {
                hoveredStack = stack;
            }
        }
        if (hoveredStack != null) {
            guiGraphics.flush();
            guiGraphics.renderTooltip(FONT, hoveredStack, mouseX, mouseY);
        }
    }

    private record SourceRow(ItemStack icon, Component text, List<FormattedCharSequence> wrapped) {
    }

    private record SourceLayout(List<SourceRow> rows, int contentHeight, int x, int y,
                                int width, int height, int viewportTop, int viewportBottom) {
        int viewportHeight() {
            return viewportBottom - viewportTop;
        }

        int maxScroll() {
            return Math.max(0, contentHeight - viewportHeight());
        }
    }

    private List<SourceRow> layoutSourceRows(List<ItemSourceInfo.Line> lines, int width) {
        List<SourceRow> rows = new ArrayList<>();
        for (ItemSourceInfo.Line line : lines) {
            if (line.icon() != null) {
                String full = line.text().getString();
                String shown = FONT.plainSubstrByWidth(full, width - 30);
                Component text = line.text();
                if (shown.length() < full.length()) {
                    text = Component.literal(shown + "…").withStyle(text.getStyle());
                }
                rows.add(new SourceRow(line.icon(), text, null));
            } else {
                rows.add(new SourceRow(null, line.text(), FONT.split(line.text(), width)));
            }
        }
        return rows;
    }

    private SourceLayout sourceLayout() {
        int width = 340;
        List<SourceRow> rows = layoutSourceRows(subPanel.lines(), width - 20);
        int contentHeight = 0;
        for (SourceRow row : rows) {
            contentHeight += row.icon() != null ? 20 : row.wrapped().size() * 11;
        }
        int maxHeight = Math.min(320, Math.max(120, this.height - 40));
        int height = Math.min(26 + contentHeight + 18, maxHeight);
        int x = this.width / 2 - width / 2;
        int y = this.height / 2 - height / 2;
        return new SourceLayout(rows, contentHeight, x, y, width, height, y + 24, y + height - 16);
    }

    private void renderSourcePanel(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        SourceLayout layout = sourceLayout();
        sourceScroll = net.minecraft.util.Mth.clamp(sourceScroll, 0, layout.maxScroll());
        int x = layout.x();
        int y = layout.y();
        int width = layout.width();
        int height = layout.height();
        int centerX = this.width / 2;

        // 子窗口只绘制自己的浅色面板，不额外填充/压暗边框外背景
        guiGraphics.fillGradient(x, y, x + width, y + height, PANEL_BG_TOP, PANEL_BG_BOTTOM);
        border(guiGraphics, x, y, x + width, y + height, PANEL_BORDER);
        guiGraphics.flush();

        drawCenteredNoShadow(guiGraphics, subPanel.title(), centerX, y + 8, PANEL_BORDER);
        String hint = layout.maxScroll() > 0 ? "滚轮浏览 · 点击任意位置关闭" : "点击任意位置关闭";
        drawCenteredNoShadow(guiGraphics, Component.literal(hint), centerX, y + height - 12, DIM);

        guiGraphics.enableScissor(x + 1, layout.viewportTop(), x + width - 1, layout.viewportBottom());
        int textY = layout.viewportTop() - sourceScroll;
        ItemStack hovered = null;
        for (SourceRow row : layout.rows()) {
            if (row.icon() != null) {
                boolean visible = textY + 20 > layout.viewportTop() && textY < layout.viewportBottom();
                if (visible) {
                    renderItemSlot(guiGraphics, x + 6, textY - 1);
                    guiGraphics.renderItem(row.icon(), x + 8, textY + 1);
                    guiGraphics.drawString(FONT, row.text(), x + 28, textY + 5, TEXT, false);
                    if (isHover(mouseX, mouseY, x + 8, textY + 1, 16, 16)
                            && isHover(mouseX, mouseY, x, layout.viewportTop(), width,
                            layout.viewportHeight())) {
                        hovered = row.icon();
                    }
                }
                textY += 20;
            } else {
                boolean visible = textY + row.wrapped().size() * 11 > layout.viewportTop()
                        && textY < layout.viewportBottom();
                for (FormattedCharSequence sequence : row.wrapped()) {
                    if (visible) {
                        guiGraphics.drawString(FONT, sequence, x + 10, textY, TEXT, false);
                    }
                    textY += 11;
                }
            }
        }
        guiGraphics.disableScissor();

        if (layout.maxScroll() > 0) {
            int trackTop = layout.viewportTop();
            int trackHeight = layout.viewportHeight();
            int thumbHeight = Math.max(16, trackHeight * trackHeight / Math.max(1, layout.contentHeight()));
            int thumbTop = trackTop + (trackHeight - thumbHeight) * sourceScroll / layout.maxScroll();
            guiGraphics.fill(x + width - 5, trackTop, x + width - 3, trackTop + trackHeight, 0x33000000);
            guiGraphics.fill(x + width - 5, thumbTop, x + width - 3, thumbTop + thumbHeight, PANEL_BORDER);
        }
        if (hovered != null) {
            guiGraphics.flush();
            guiGraphics.renderTooltip(FONT, hovered, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (subPanel != null) {
            subPanel = null;
            return true;
        }
        if (button == 0) {
            List<IllustrateData.Entry> entryList = currentEntries();
            int panelX = this.width / 2 - PANEL_WIDTH / 2;
            int panelY = this.height / 2 - PANEL_HEIGHT / 2;
            for (int index = 0; index < IllustrateData.PER_PAGE; index++) {
                int entryIndex = page * IllustrateData.PER_PAGE + index;
                if (entryIndex >= entryList.size()) {
                    break;
                }
                IllustrateData.Entry entry = entryList.get(entryIndex);
                int x = entryX(panelX, index % IllustrateData.COLUMNS);
                int y = entryY(panelY, index / IllustrateData.COLUMNS);
                if (entry.series() && isHover(mouseX, mouseY, x + SERIES_BUTTON_X, y + CARD_BUTTON_Y,
                        SERIES_BUTTON_WIDTH, CARD_BUTTON_HEIGHT)) {
                    openSubPanel(Component.literal("系列 · ").append(entry.displayName())
                            .append(Component.literal("（" + entry.seriesItems().size() + "）")),
                            entry.seriesItems());
                    return true;
                }
                if (entry.hasSource() && isHover(mouseX, mouseY, x + SOURCE_BUTTON_X, y + CARD_BUTTON_Y,
                        SOURCE_BUTTON_WIDTH, CARD_BUTTON_HEIGHT)) {
                    openSourcePanel(entry);
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void openSubPanel(Component title, List<ItemStack> stacks) {
        this.subPanel = new SubPanel(title, stacks, null);
    }

    /** 打开「来源」窗口：从代码中检索制作配方、灌注配方与获取途径。 */
    private void openSourcePanel(IllustrateData.Entry entry) {
        this.sourceScroll = 0;
        this.subPanel = new SubPanel(Component.literal("来源 · ").append(entry.displayName()),
                List.of(), ItemSourceInfo.build(entry.members()));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (subPanel != null && subPanel.lines() != null) {
            SourceLayout layout = sourceLayout();
            if (layout.maxScroll() > 0) {
                sourceScroll = net.minecraft.util.Mth.clamp(
                        sourceScroll - (int) Math.round(delta * 14), 0, layout.maxScroll());
            }
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    private static void drawCenteredNoShadow(GuiGraphics guiGraphics, String text,
                                             int centerX, int y, int color) {
        guiGraphics.drawString(FONT, text, centerX - FONT.width(text) / 2, y, color, false);
    }

    private static void drawCenteredNoShadow(GuiGraphics guiGraphics, Component text,
                                             int centerX, int y, int color) {
        guiGraphics.drawString(FONT, text, centerX - FONT.width(text) / 2, y, color, false);
    }

    private static boolean isHover(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    private static int entryX(int panelX, int column) {
        return panelX + 12 + column * (CARD_WIDTH + CARD_GAP_X);
    }

    private static int entryY(int panelY, int row) {
        return panelY + PANEL_TOP + row * (CARD_HEIGHT + CARD_GAP_Y);
    }

    private List<IllustrateData.Entry> currentEntries() {
        if (cachedType != type) {
            cachedType = type;
            entries = IllustrateData.buildEntries(getItems());
            page = Math.min(page, maxPage());
        }
        return entries;
    }

    private int maxPage() {
        return Math.max(0, (entries.size() - 1) / IllustrateData.PER_PAGE);
    }

    private String categoryName() {
        return switch (type) {
            case 0 -> "武器";
            case 1 -> "防具";
            case 2 -> "饰品";
            case 3 -> "制式";
            case 4 -> "酿造";
            case 5 -> "材料";
            case 6 -> "宝石";
            case 7 -> "符石";
            case 8 -> "近战";
            case 9 -> "弓";
            case 10 -> "法杖";
            case 11 -> "副手";
            case 12 -> "头盔";
            case 13 -> "胸甲";
            case 14 -> "护腿";
            case 15 -> "靴子";
            case 16 -> "法术";
            case 17 -> "镐子";
            case 18 -> "纪念";
            case 19 -> "新品";
            default -> "图鉴";
        };
    }

    private List<Item> getItems() {
        List<Item> list;
        switch (type) {
            case 0 -> list = Utils.weaponList;
            case 1 -> list = Utils.armorList;
            case 2 -> list = Utils.curiosList;
            case 3 -> list = Utils.customizedList;
            case 4 -> list = Display.getBrewingList();
            case 5 -> list = Display.materialList;
            case 6 -> list = Display.gemList;
            case 7 -> list = Display.runeList;
            case 8 -> list = Display.swordList;
            case 9 -> list = Display.bowList;
            case 10 -> list = Display.sceptreList;
            case 11 -> list = Display.offHandList;
            case 12 -> list = Display.helmetList;
            case 13 -> list = Display.chestList;
            case 14 -> list = Display.leggingsList;
            case 15 -> list = Display.bootsList;
            case 16 -> list = Display.powerList;
            case 17 -> list = Display.pickAxeList;
            case 18 -> list = Display.getSouvenirsList();
            case 19 -> list = Display.getNewItemList();
            default -> list = List.of();
        }
        return list;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
