package fun.wraq.render.gui.dialog;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.struct.ClientDialogData;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.dialog.DialogSelectC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * AI-Generated, 2026-10-17
 * 对话界面：取代聊天窗里的「可点击命令文本」。
 * <p>
 * 布局采用「左立绘 + 右正文」的双栏结构：标题与正文都排在立绘右侧、从面板顶部开始，
 * 不再为立绘单独留一条贯穿面板的横幅（那样右侧会空出一大块）。
 * 没有可渲染实体时（entityId 缺失或实体不在本机）自动退化为单栏全宽布局。
 * <p>
 * 立绘复用 {@link InventoryScreen#renderEntityInInventoryFollowsMouse}，
 * 与生存模式物品栏里的玩家模型同款（跟随鼠标水平/垂直转头）。
 */
public class DialogScreen extends Screen {

    private static final int PANEL_WIDTH = 380;
    private static final int MODEL_COLUMN = 76;
    private static final int CONTENT_OFFSET = MODEL_COLUMN + 12;
    private static final int PADDING = 16;
    private static final int TITLE_BAND = 28;
    private static final int OPTION_HEIGHT = 20;
    private static final int OPTION_GAP = 4;
    private static final int LINE_COLOR = 0xE0E0E0;
    /** 立绘在面板内的目标像素高度。 */
    private static final int PORTRAIT_TARGET_PX = 72;

    private final ClientDialogData data;
    private final List<int[]> optionRects = new ArrayList<>();
    private final List<Integer> lineHeights = new ArrayList<>();

    private LivingEntity portrait;
    private int portraitScale;
    private int panelX;
    private int panelY;
    private int panelHeight;
    private int contentX;
    private int contentWidth;
    private int bodyY;
    private int dividerY;
    private int anchorX;
    private int anchorY;
    private int modelCenterY;

    public DialogScreen(ClientDialogData data) {
        super(Component.literal("Dialog"));
        this.data = data;
    }

    @Override
    protected void init() {
        this.optionRects.clear();
        this.lineHeights.clear();

        this.portrait = resolvePortrait();
        this.portraitScale = portrait == null ? 0
                : Math.max(6, Math.round(PORTRAIT_TARGET_PX / Math.max(0.6F, portrait.getBbHeight())));
        int portraitPx = portrait == null ? 0 : Math.round(portraitScale * portrait.getBbHeight());

        int offset = portrait == null ? PADDING : CONTENT_OFFSET;
        this.contentWidth = PANEL_WIDTH - offset - PADDING;

        int linesHeight = 4;
        for (Component line : data.lines()) {
            int wrapped = Math.max(1, this.font.split(line, contentWidth).size());
            int height = wrapped * this.font.lineHeight;
            this.lineHeights.add(height);
            linesHeight += height;
        }

        int optionCount = data.optionLabels().size();
        int optionsHeight = optionCount * (OPTION_HEIGHT + OPTION_GAP);
        int contentBottom = Math.max(TITLE_BAND + linesHeight, TITLE_BAND + portraitPx);
        int buttonsTop = contentBottom + 16;
        this.panelHeight = buttonsTop + optionsHeight + 6;
        this.panelX = (this.width - PANEL_WIDTH) / 2;
        this.panelY = (this.height - panelHeight) / 2;

        this.contentX = panelX + offset;
        this.bodyY = panelY + TITLE_BAND;
        this.dividerY = panelY + contentBottom + 8;
        this.anchorX = panelX + MODEL_COLUMN / 2;
        this.anchorY = panelY + TITLE_BAND + portraitPx;
        this.modelCenterY = anchorY - portraitPx / 2;

        int y = panelY + buttonsTop;
        for (int i = 0; i < optionCount; i++) {
            final String actionId = data.optionActionIds().get(i);
            this.addRenderableWidget(Button.builder(data.optionLabels().get(i), button ->
                            ModNetworking.sendToServer(new DialogSelectC2SPacket(
                                    data.npcId(), data.sessionId(), actionId)))
                    .pos(contentX, y)
                    .size(contentWidth, OPTION_HEIGHT)
                    .build());
            this.optionRects.add(new int[]{contentX, y, contentX + contentWidth, y + OPTION_HEIGHT, i});
            y += OPTION_HEIGHT + OPTION_GAP;
        }

        this.addRenderableWidget(Button.builder(Component.literal("×"), button -> this.onClose())
                .pos(panelX + PANEL_WIDTH - 22, panelY + 5)
                .size(14, 14)
                .build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);

        RenderSystem.enableBlend();
        graphics.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + panelHeight, 0xE0101018);
        graphics.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + 1, 0xFF5A5A78);
        graphics.fill(panelX, panelY + panelHeight - 1, panelX + PANEL_WIDTH, panelY + panelHeight, 0xFF5A5A78);
        graphics.fill(panelX, panelY, panelX + 1, panelY + panelHeight, 0xFF5A5A78);
        graphics.fill(panelX + PANEL_WIDTH - 1, panelY, panelX + PANEL_WIDTH, panelY + panelHeight, 0xFF5A5A78);
        RenderSystem.disableBlend();

        if (portrait != null) {
            // 立绘栏与正文列之间的细分隔线
            graphics.fill(panelX + MODEL_COLUMN + 4, panelY + 8, panelX + MODEL_COLUMN + 5,
                    anchorY + 2, 0xFF40405A);
        }

        graphics.drawCenteredString(this.font, data.speaker(), contentX + contentWidth / 2, panelY + 9, 0xFFFFFF);

        int y = bodyY;
        for (int i = 0; i < data.lines().size(); i++) {
            graphics.drawWordWrap(this.font, data.lines().get(i), contentX, y, contentWidth, LINE_COLOR);
            y += this.lineHeights.get(i);
        }

        graphics.fill(contentX, dividerY, panelX + PANEL_WIDTH - PADDING, dividerY + 1, 0xFF40405A);

        super.render(graphics, mouseX, mouseY, partialTick);

        // 立绘在 super.render 之后：按钮与文字已绘制完，模型不会盖住它们
        this.renderPortrait(graphics, mouseX, mouseY);

        for (int[] rect : optionRects) {
            if (mouseX >= rect[0] && mouseX <= rect[2] && mouseY >= rect[1] && mouseY <= rect[3]) {
                int index = rect[4];
                if (index < data.optionHovers().size()) {
                    graphics.renderTooltip(this.font, data.optionHovers().get(index), mouseX, mouseY);
                }
                break;
            }
        }
    }

    /** 按网络 id 从本机世界查回对话实体；查不到就退化为无立绘布局。 */
    private LivingEntity resolvePortrait() {
        if (data.entityId() < 0) {
            return null;
        }
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return null;
        }
        Entity entity = level.getEntity(data.entityId());
        return entity instanceof LivingEntity living ? living : null;
    }

    /**
     * 渲染立绘。锚点是模型脚底（模型自锚点向上生长），所以放在立绘栏底部。
     */
    private void renderPortrait(GuiGraphics graphics, int mouseX, int mouseY) {
        if (portrait == null || portraitScale <= 0) {
            return;
        }
        // 立绘上的名字标签必须压掉，否则会和正文抢位置。
        // 注意两个坑：
        // 1) 不能 setCustomName(null) —— 那会让 getName() 退回实体类型名，
        //    标签反而画出原始翻译键 entity.vmd.npc（历史 bug）；
        // 2) GUI 里的名字标签不受 isCustomNameVisible 控制（原版 shouldShowName 走
        //    Minecraft.renderNames() 分支），所以只关 Visible 也压不住。
        // 正确做法：临时塞一个空名字——标签照旧绘制，但绘制的是空文本。
        Component originalName = portrait.getCustomName();
        boolean nameVisible = portrait.isCustomNameVisible();
        portrait.setCustomName(Component.literal(""));
        portrait.setCustomNameVisible(false);
        try {
            // 1.20.1 的 FollowsMouse 签名是 (x, y, scale, 水平偏移, 垂直偏移, entity)：
            // 两个 float 各自 ÷40 后取 atan，分别驱动 yRot = 180+40·atan(dx/40) 与 xRot = -20·atan(dy/40)。
            // 方向推导（勿凭直觉改符号）：pose 为 scale(s,s,-s) + rotateZ(π)，故世界 +X → 屏幕 −X；
            // 实体正面 = (-sin(yaw),0,cos(yaw))，yaw = 180+Δ ⇒ 正面屏幕 X 分量 = −sinΔ，
            // 即 Δ>0（dx>0）时模型看向屏幕左。所以 dx 必须取「中轴 − 鼠标」：
            // 鼠标在模型左侧 ⇒ dx>0 ⇒ 向左看。
            // 参照点必须是模型自身中轴：取面板中心时，鼠标还在模型右侧就已经被判成往左。
            float dx = (anchorX - mouseX) * 0.35F;
            float dy = (modelCenterY - mouseY) * 0.5F;
            InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, anchorX, anchorY, portraitScale,
                    dx, dy, portrait);
        } finally {
            portrait.setCustomName(originalName);
            portrait.setCustomNameVisible(nameVisible);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
