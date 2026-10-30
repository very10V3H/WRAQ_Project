package fun.wraq.events.client;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.plan.DailySupply;
import fun.wraq.process.system.mastery.MasteryAllocation;
import fun.wraq.process.system.mastery.MasteryClientData;
import fun.wraq.process.system.mastery.MasteryTree;
import fun.wraq.render.gui.skills.IdCardActions;
import fun.wraq.render.gui.skills.IdCardGui;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * AI-Generated, 2026-10-25
 * 原版物品栏 / Curios 界面的身份卡整合：
 * <ul>
 *     <li>隐藏原版合成配方书按钮；</li>
 *     <li>打开物品栏时自动切到 Curios，让饰品栏一起出现；</li>
 *     <li>在上方/下方横放 8 个功能按钮，在右侧放置精通/每日补给，</li>
 *     <li>右侧保留精通五路径点数概览、精通入口、每日补给物品渲染与悬停查看。</li>
 * </ul>
 */
@Mod.EventBusSubscriber(modid = Utils.MOD_ID, value = Dist.CLIENT,
        bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class InventoryUiTweaks {

    private static final int RECIPE_BUTTON_X = 104;
    /** 原版 height/2 - 22，对 166 高的容器等于 top + 61。 */
    private static final int RECIPE_BUTTON_Y = 61;
    private static final int RECIPE_BUTTON_WIDTH = 20;
    private static final int RECIPE_BUTTON_HEIGHT = 18;

    private static final int PANEL_WIDTH = 100;
    private static final int PANEL_HEIGHT = 166;
    private static final int BAR_WIDTH = 196;
    private static final int BAR_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 38;
    private static final int BUTTON_HEIGHT = 16;
    private static final int BUTTON_STEP = 38;

    /** 新旧身份卡底色的中间值，避免过深或过白。 */
    private static final int CARD_BG = 0xF2A7E6FF;
    private static final int CARD_HEADER = 0xFFBBECFF;
    private static final int CARD_LINE = 0xFF6DB7F6;
    private static final int CARD_LAVENDER = 0xFFC0B8F3;
    private static final int CARD_TEXT = 0xFF143F66;

    private static Screen lastScreen;

    private InventoryUiTweaks() {
    }

    /**
     * 自动打开 Curios：从游戏/其他界面进入原版物品栏时，模拟点击 CuriosButton。
     * 从 Curios 返回原版物品栏时跳过，否则「关闭饰品栏」会被立刻重新打开。
     */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            lastScreen = null;
            return;
        }
        Screen current = mc.screen;
        if (current == lastScreen) {
            return;
        }
        Screen previous = lastScreen;
        lastScreen = current;
        if (current instanceof InventoryScreen && !isCuriosScreen(previous)) {
            clickCuriosButton(current);
        }
    }

    private static boolean isCuriosScreen(Screen screen) {
        return screen != null
                && screen.getClass().getName().startsWith("top.theillusivec4.curios.client.gui.CuriosScreen");
    }

    /**
     * 不直接 import Curios 客户端包（compileOnly 只有 API），按类名找到 CuriosButton，
     * 调用它原本的点击逻辑；按钮自身的回调会发 CPacketOpenCurios 并处理配方书/手持物。
     */
    private static void clickCuriosButton(Screen screen) {
        for (GuiEventListener listener : screen.children()) {
            if (listener instanceof ImageButton button
                    && button.getClass().getName().equals("top.theillusivec4.curios.client.gui.CuriosButton")
                    && button.visible && button.active) {
                button.mouseClicked(button.getX() + 1, button.getY() + 1, 0);
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof AbstractContainerScreen<?> screen)) {
            return;
        }
        if (!isTargetScreen(screen)) {
            return;
        }
        int recipeX = screen.getGuiLeft() + RECIPE_BUTTON_X;
        int recipeY = screen.getGuiTop() + RECIPE_BUTTON_Y;
        for (GuiEventListener listener : screen.children()) {
            if (listener instanceof ImageButton button
                    && button.getWidth() == RECIPE_BUTTON_WIDTH
                    && button.getHeight() == RECIPE_BUTTON_HEIGHT
                    && button.getX() == recipeX
                    && button.getY() == recipeY) {
                button.visible = false;
                button.active = false;
            }
        }
        addIdCardUi(screen, event);
    }

    /**
     * 布局：
     * <pre>
     *             [研发采购][全球市场][VP商店][声望商店]     ← 上方横排
     *  ┌────────────────────┐   ┌──────────────┐
     *  │ 原版/Curios 物品栏  │   │ 身份卡       │
     *  │                    │   │ 精通五路径概览│
     *  │                    │   │ [精通]       │
     *  │                    │   │ 每日补给 [物] │
     *  │                    │   │ [领取补给]   │
     *  └────────────────────┘   └──────────────┘
     *             [图鉴][本源回廊][任务列表][组队副本]        ← 下方横排
     * </pre>
     */
    private static void addIdCardUi(AbstractContainerScreen<?> screen, ScreenEvent.Init.Post event) {
        int panelY = Math.max(4, Math.min(screen.getGuiTop(), screen.height - PANEL_HEIGHT - 4));
        int panelX = findPanelX(screen);
        int barX = (screen.width - BAR_WIDTH) / 2;
        int barTopY = panelY - 24;
        int barBottomY = panelY + PANEL_HEIGHT + 4;

        boolean fullLayoutFits = panelX >= 0
                && barX >= 4 && barX + BAR_WIDTH <= screen.width - 4
                && barTopY >= 4
                && barBottomY + BAR_HEIGHT <= screen.height - 4;
        if (!fullLayoutFits) {
            addFallbackButton(screen, event);
            return;
        }

        event.addListener(new IdCardPanelWidget(panelX, panelY, PANEL_WIDTH, PANEL_HEIGHT));
        event.addListener(new IdCardBarWidget(barX, barTopY, BAR_WIDTH, BAR_HEIGHT));
        event.addListener(new IdCardBarWidget(barX, barBottomY, BAR_WIDTH, BAR_HEIGHT));

        // 上方横排
        addButton(event, barX + 4, barTopY + 2, BUTTON_WIDTH, "研发采购",
                CustomStyle.styleOfWorld, b -> IdCardActions.openResearch());
        addButton(event, barX + 4 + BUTTON_STEP, barTopY + 2, BUTTON_WIDTH, "全球市场",
                ChatFormatting.GOLD, b -> IdCardActions.openMarket());
        addButton(event, barX + 4 + BUTTON_STEP * 2, barTopY + 2, BUTTON_WIDTH, "VP商店",
                ChatFormatting.AQUA, b -> IdCardActions.openVpStore());
        addButton(event, barX + 4 + BUTTON_STEP * 3, barTopY + 2, BUTTON_WIDTH, "声望商店",
                ChatFormatting.YELLOW, b -> IdCardActions.openReputation());

        // 右侧：精通 + 每日补给
        addButton(event, panelX + 4, panelY + 102, 92, "精通",
                CustomStyle.styleOfWorld, b -> IdCardActions.openMastery(screen));
        addButton(event, panelX + 24, panelY + 132, 72, "领取补给",
                ChatFormatting.GOLD, b -> IdCardActions.claimSupply());

        // 下方横排
        addButton(event, barX + 4, barBottomY + 2, BUTTON_WIDTH, "图鉴",
                CustomStyle.styleOfSunIsland, b -> IdCardActions.openIllustrate());
        addButton(event, barX + 4 + BUTTON_STEP, barBottomY + 2, BUTTON_WIDTH, "本源回廊",
                CustomStyle.styleOfWorld, b -> IdCardActions.openTower());
        addButton(event, barX + 4 + BUTTON_STEP * 2, barBottomY + 2, BUTTON_WIDTH, "任务列表",
                CustomStyle.styleOfKaze, b -> IdCardActions.openMissions());
        addButton(event, barX + 4 + BUTTON_STEP * 3, barBottomY + 2, BUTTON_WIDTH, "组队副本",
                ChatFormatting.AQUA, b -> IdCardActions.openTeam());
        addButton(event, barX + 4 + BUTTON_STEP * 4, barBottomY + 2, BUTTON_WIDTH, "成就",
                CustomStyle.styleOfGold, b -> IdCardActions.openAchievements());

        // tooltip 最后加入，保证鼠标停在补给物品上时不会被按钮覆盖。
        event.addListener(new DailySupplyTooltipWidget(panelX + 4, panelY + 132));
    }

    private static int findPanelX(AbstractContainerScreen<?> screen) {
        int rightX = screen.getGuiLeft() + screen.getXSize() + 4;
        // 右侧只剩 1px 也要保留；否则 640 宽、431 宽 Curios 下会误回退。
        if (rightX + PANEL_WIDTH <= screen.width) {
            return rightX;
        }
        int leftX = screen.getGuiLeft() - 4 - PANEL_WIDTH;
        if (leftX >= 4) {
            return leftX;
        }
        return -1;
    }

    private static void addButton(ScreenEvent.Init.Post event, int x, int y, int width,
                                  String label, Object style, Button.OnPress onPress) {
        event.addListener(Button.builder(Te.s(label, style), onPress)
                .pos(x, y).size(width, BUTTON_HEIGHT).build());
    }

    /** 极高 GUI Scale 下放不下完整布局：保留一个「身份卡」入口，打开旧界面。 */
    private static void addFallbackButton(AbstractContainerScreen<?> screen, ScreenEvent.Init.Post event) {
        event.addListener(Button.builder(Te.s("身份卡", Style.EMPTY.withColor(CARD_TEXT)),
                        b -> Minecraft.getInstance().setScreen(new IdCardGui(true)))
                .pos(Math.max(4, screen.width - 52), 4)
                .size(48, 16).build());
    }

    private static ItemStack dailySupplyItem() {
        return DailySupply.getRewardItemMap()
                .getOrDefault(DailySupply.clientSupplyStatus, ItemStack.EMPTY);
    }

    /** 右侧身份卡面板：精通五路径概览 + 每日补给物品。 */
    private static final class IdCardPanelWidget extends AbstractWidget {
        private static final int ROW_START = 18;
        private static final int ROW_STEP = 16;

        private IdCardPanelWidget(int x, int y, int width, int height) {
            super(x, y, width, height, Component.empty());
            this.active = false;
        }

        @Override
        protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            int x = getX();
            int y = getY();
            int right = x + getWidth();
            int bottom = y + getHeight();
            graphics.fill(x, y, right, bottom, CARD_BG);
            graphics.fill(x, y, right, y + 2, 0xFFFFFFFF);
            graphics.fill(x, bottom - 2, right, bottom, 0xFFFFFFFF);
            graphics.fill(x, y, x + 2, bottom, 0xFFFFFFFF);
            graphics.fill(right - 2, y, right, bottom, 0xFFFFFFFF);
            graphics.fill(x + 2, y + 2, right - 2, y + 14, CARD_HEADER);

            var font = Minecraft.getInstance().font;
            // 标题使用无样式文本，避免 CustomStyle.styleOfWorld 自带的亮色覆盖 CARD_TEXT。
            graphics.drawString(font, Component.literal("身份卡"),
                    x + 5, y + 4, CARD_TEXT, false);
            graphics.drawString(font, MasteryClientData.used() + "/" + MasteryClientData.total,
                    x + 68, y + 4, CARD_TEXT, false);

            for (int i = 0; i < MasteryTree.values().length; i++) {
                MasteryTree tree = MasteryTree.values()[i];
                int rowY = y + ROW_START + i * ROW_STEP;
                int color = tree.getNameColor();
                drawTriangle(graphics, x + 4, rowY + 2, color);
                graphics.drawString(font, tree.getDisplayName(), x + 12, rowY + 4, color, false);
                int points = MasteryAllocation.treeTotal(MasteryClientData.allocation[tree.ordinal()]);
                graphics.fill(x + 72, rowY + 1, x + 92, rowY + 14, CARD_LAVENDER);
                graphics.drawCenteredString(font, Component.literal(String.valueOf(points)),
                        x + 82, rowY + 4, 0xFF2F2F4F);
            }

            graphics.drawString(font, Component.literal("每日补给"),
                    x + 4, y + 120, CARD_TEXT, false);
            ItemStack supply = dailySupplyItem();
            if (!supply.isEmpty()) {
                graphics.renderItem(supply, x + 4, y + 132);
                graphics.renderItemDecorations(font, supply, x + 4, y + 132);
            }
        }

        private static void drawTriangle(GuiGraphics graphics, int x, int y, int color) {
            for (int i = 0; i < 5; i++) {
                int half = i <= 2 ? i : 4 - i;
                graphics.fill(x + i, y + 3 - half, x + i + 1, y + 4 + half, color);
            }
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        }
    }

    /** 上下两条横排的底板。 */
    private static final class IdCardBarWidget extends AbstractWidget {
        private IdCardBarWidget(int x, int y, int width, int height) {
            super(x, y, width, height, Component.empty());
            this.active = false;
        }

        @Override
        protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            int x = getX();
            int y = getY();
            int right = x + getWidth();
            int bottom = y + getHeight();
            graphics.fill(x, y, right, bottom, CARD_BG);
            graphics.fill(x, y, right, y + 2, CARD_LINE);
            graphics.fill(x, bottom - 2, right, bottom, CARD_LINE);
            graphics.fill(x, y, x + 2, bottom, CARD_LINE);
            graphics.fill(right - 2, y, right, bottom, CARD_LINE);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        }
    }

    /** 最后渲染的每日补给 tooltip，避免被领取按钮覆盖。 */
    private static final class DailySupplyTooltipWidget extends AbstractWidget {
        private DailySupplyTooltipWidget(int x, int y) {
            super(x, y, 16, 16, Component.empty());
            this.active = false;
        }

        @Override
        protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            if (mouseX < getX() || mouseX >= getX() + getWidth()
                    || mouseY < getY() || mouseY >= getY() + getHeight()) {
                return;
            }
            ItemStack supply = dailySupplyItem();
            if (!supply.isEmpty()) {
                graphics.renderTooltip(Minecraft.getInstance().font, supply, mouseX, mouseY);
            }
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        }
    }

    /**
     * 只处理原版物品栏与 Curios 的两个界面；不直接 import Curios 客户端类，
     * 避免 compileOnly API 依赖缺少 client 实现时编译失败。
     */
    private static boolean isTargetScreen(AbstractContainerScreen<?> screen) {
        if (screen instanceof InventoryScreen) {
            return true;
        }
        String name = screen.getClass().getName();
        return name.equals("top.theillusivec4.curios.client.gui.CuriosScreen")
                || name.equals("top.theillusivec4.curios.client.gui.CuriosScreenV2");
    }
}
