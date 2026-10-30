package fun.wraq.process.system.achievement;

import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.achievement.networking.AchievementClaimC2SPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * AI-Generated, 2026-10-30
 * 成就界面：参考图鉴的两列卡片布局，支持分类、分页、手动领取奖励。
 * 获取方式在成就达成前隐藏。
 */
public class AchievementScreen extends Screen {

    private static final int PANEL_WIDTH = 424;
    private static final int PANEL_HEIGHT = 226;
    private static final int CARD_WIDTH = 196;
    private static final int CARD_HEIGHT = 32;
    private static final int CARD_GAP_X = 8;
    private static final int CARD_GAP_Y = 4;
    private static final int PAGE_SIZE = 10;

    private static final int BG_TOP = 0xE8F6FAFD;
    private static final int BG_BOTTOM = 0xF7FFFFFF;
    private static final int BORDER = 0xFF2E9BD6;
    private static final int TEXT = 0xFF1E2D44;
    private static final int DIM = 0xFF5A6B80;
    private static final int CARD = 0xE8FFFFFF;
    private static final int CARD_HOVER = 0xF4FFFFFF;
    private static final int CARD_BORDER = 0xFFB9D4E8;
    private static final int CLAIM_BG = 0xFFEAF4FB;
    private static final int CLAIM_BORDER = 0xFF7FB6D9;
    private static final int CLAIM_TEXT = 0xFF1E5A82;

    private final int category;
    private int page;
    private final List<Achievement> filtered = new ArrayList<>();

    public AchievementScreen(int category, int page) {
        super(Component.literal("成就"));
        this.category = category;
        this.page = Math.max(0, page);
    }

    @Override
    protected void init() {
        int x = this.width / 2;
        int topY = this.height / 2 - PANEL_HEIGHT / 2;
        int categoryStart = x - 196;
        for (int i = 0; i < AchievementCategory.values().length + 1; i++) {
            int target = i - 1;
            String label = i == 0 ? "全部" : AchievementCategory.values()[i - 1].displayName;
            this.addRenderableWidget(Button.builder(Component.literal(label), b -> {
                        Minecraft.getInstance().setScreen(new AchievementScreen(target, 0));
                    }).pos(categoryStart + i * 56, topY - 22).size(54, 18)
                    .build());
        }
        this.addRenderableWidget(Button.builder(Component.literal("←"), b -> {
            if (page > 0) {
                page--;
            }
        }).pos(x - 44, topY + PANEL_HEIGHT - 21).size(16, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("→"), b -> {
            if (page < maxPage()) {
                page++;
            }
        }).pos(x + 28, topY + PANEL_HEIGHT - 21).size(16, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("x"), b -> {
            this.minecraft.setScreen(null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(x + PANEL_WIDTH / 2 - 16, topY + 3).size(12, 12).build());
        filtered.clear();
        for (Achievement achievement : AchievementRegistry.all()) {
            if (category < 0 || achievement.category().ordinal() == category) {
                filtered.add(achievement);
            }
        }
        page = Math.min(page, maxPage());
    }

    private int maxPage() {
        return Math.max(0, (filtered.size() - 1) / PAGE_SIZE);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        int x = this.width / 2;
        int topY = this.height / 2 - PANEL_HEIGHT / 2;
        int left = x - PANEL_WIDTH / 2;
        graphics.fill(left, topY, left + PANEL_WIDTH, topY + PANEL_HEIGHT, BG_BOTTOM);
        graphics.fill(left, topY, left + PANEL_WIDTH, topY + 2, BORDER);
        graphics.fill(left, topY + PANEL_HEIGHT - 2, left + PANEL_WIDTH, topY + PANEL_HEIGHT, BORDER);
        graphics.fill(left, topY, left + 2, topY + PANEL_HEIGHT, BORDER);
        graphics.fill(left + PANEL_WIDTH - 2, topY, left + PANEL_WIDTH, topY + PANEL_HEIGHT, BORDER);
        graphics.fill(left + 2, topY + 2, left + PANEL_WIDTH - 2, topY + 16, BG_TOP);
        Font font = Minecraft.getInstance().font;
        graphics.drawCenteredString(font, Component.literal("「成就」").withStyle(CustomStyle.styleOfGold),
                x, topY + 4, TEXT);

        int unlocked = 0;
        int claimed = 0;
        for (Achievement achievement : AchievementRegistry.all()) {
            if (AchievementClientData.isUnlocked(achievement.id())) {
                unlocked++;
                if (AchievementClientData.isClaimed(achievement.id())) {
                    claimed++;
                }
            }
        }
        graphics.drawString(font, "已达成 " + unlocked + "/" + AchievementRegistry.all().size()
                        + "   已领取 " + claimed,
                left + 8, topY + 19, DIM, false);

        int startX = x - (CARD_WIDTH * 2 + CARD_GAP_X) / 2;
        int startY = topY + 32;
        for (int i = 0; i < PAGE_SIZE; i++) {
            int index = page * PAGE_SIZE + i;
            if (index >= filtered.size()) {
                break;
            }
            Achievement achievement = filtered.get(index);
            int col = i % 2;
            int row = i / 2;
            int cardX = startX + col * (CARD_WIDTH + CARD_GAP_X);
            int cardY = startY + row * (CARD_HEIGHT + CARD_GAP_Y);
            renderCard(graphics, font, achievement, cardX, cardY, mouseX, mouseY);
        }
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, font, mouseX, mouseY, startX, startY);
    }

    private void renderCard(GuiGraphics graphics, Font font, Achievement achievement,
                            int x, int y, int mouseX, int mouseY) {
        boolean hovered = mouseX >= x && mouseX < x + CARD_WIDTH
                && mouseY >= y && mouseY < y + CARD_HEIGHT;
        boolean unlocked = AchievementClientData.isUnlocked(achievement.id());
        boolean claimed = AchievementClientData.isClaimed(achievement.id());
        graphics.fill(x, y, x + CARD_WIDTH, y + CARD_HEIGHT, hovered ? CARD_HOVER : CARD);
        graphics.fill(x, y, x + CARD_WIDTH, y + 1, CARD_BORDER);
        graphics.fill(x, y + CARD_HEIGHT - 1, x + CARD_WIDTH, y + CARD_HEIGHT, CARD_BORDER);
        graphics.fill(x, y, x + 1, y + CARD_HEIGHT, CARD_BORDER);
        graphics.fill(x + CARD_WIDTH - 1, y, x + CARD_WIDTH, y + CARD_HEIGHT, CARD_BORDER);

        ChatFormatting color = achievement.category().color();
        graphics.drawString(font, achievement.name(), x + 5, y + 4, color.getColor() == null ? TEXT : color.getColor(), false);
        String desc = font.plainSubstrByWidth(achievement.description(), CARD_WIDTH - 12);
        graphics.drawString(font, desc, x + 5, y + 14, DIM, false);

        int current = AchievementClientData.progress(achievement.id());
        String state;
        if (claimed) {
            state = "已领取";
        } else if (unlocked) {
            state = "可领取";
        } else {
            state = current + "/" + achievement.target();
        }
        graphics.drawString(font, state, x + 5, y + 23, unlocked ? 0xFF2E7D32 : DIM, false);

        if (unlocked && !claimed) {
            int bx = x + CARD_WIDTH - 48;
            int by = y + 8;
            graphics.fill(bx, by, bx + 42, by + 16, CLAIM_BG);
            graphics.fill(bx, by, bx + 42, by + 1, CLAIM_BORDER);
            graphics.fill(bx, by + 15, bx + 42, by + 16, CLAIM_BORDER);
            graphics.fill(bx, by, bx + 1, by + 16, CLAIM_BORDER);
            graphics.fill(bx + 41, by, bx + 42, by + 16, CLAIM_BORDER);
            graphics.drawCenteredString(font, "领取", bx + 21, by + 4, CLAIM_TEXT);
        }
    }

    private void renderTooltip(GuiGraphics graphics, Font font, int mouseX, int mouseY,
                               int startX, int startY) {
        for (int i = 0; i < PAGE_SIZE; i++) {
            int index = page * PAGE_SIZE + i;
            if (index >= filtered.size()) {
                break;
            }
            Achievement achievement = filtered.get(index);
            int col = i % 2;
            int row = i / 2;
            int x = startX + col * (CARD_WIDTH + CARD_GAP_X);
            int y = startY + row * (CARD_HEIGHT + CARD_GAP_Y);
            if (mouseX < x || mouseX >= x + CARD_WIDTH || mouseY < y || mouseY >= y + CARD_HEIGHT) {
                continue;
            }
            List<Component> lines = new ArrayList<>();
            boolean unlocked = AchievementClientData.isUnlocked(achievement.id());
            lines.add(Te.s(achievement.name(), achievement.category().color()));
            lines.add(Te.s(achievement.description(), ChatFormatting.GRAY));
            if (unlocked) {
                lines.add(Te.s("获取方式: ", CustomStyle.styleOfGold, achievement.acquisition(), ChatFormatting.WHITE));
                lines.add(Te.s("奖励:", CustomStyle.styleOfGold));
                for (var reward : achievement.rewards()) {
                    ItemStack stack = reward.get();
                    lines.add(Te.s(" - ", stack.getHoverName(), " ×", stack.getCount(), ChatFormatting.WHITE));
                }
                if (AchievementClientData.isClaimed(achievement.id())) {
                    lines.add(Te.s("奖励已领取", ChatFormatting.GRAY));
                } else {
                    lines.add(Te.s("点击「领取」获得奖励", ChatFormatting.GREEN));
                }
            } else {
                lines.add(Te.s("达成后解锁获取方式", ChatFormatting.DARK_GRAY));
            }
            graphics.renderComponentTooltip(font, lines, mouseX, mouseY);
            return;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        int startX = this.width / 2 - (CARD_WIDTH * 2 + CARD_GAP_X) / 2;
        int startY = this.height / 2 - PANEL_HEIGHT / 2 + 32;
        for (int i = 0; i < PAGE_SIZE; i++) {
            int index = page * PAGE_SIZE + i;
            if (index >= filtered.size()) {
                break;
            }
            Achievement achievement = filtered.get(index);
            int col = i % 2;
            int row = i / 2;
            int x = startX + col * (CARD_WIDTH + CARD_GAP_X);
            int y = startY + row * (CARD_HEIGHT + CARD_GAP_Y);
            if (AchievementClientData.isUnlocked(achievement.id())
                    && !AchievementClientData.isClaimed(achievement.id())
                    && mouseX >= x + CARD_WIDTH - 48 && mouseX < x + CARD_WIDTH - 6
                    && mouseY >= y + 8 && mouseY < y + 24) {
                ModNetworking.sendToServer(new AchievementClaimC2SPacket(achievement.id()));
                return true;
            }
        }
        return false;
    }
}
