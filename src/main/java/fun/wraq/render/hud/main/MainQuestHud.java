package fun.wraq.render.hud.main;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.KeyBoradInput;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.process.system.missions.mainline.MainQuestHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

/**
 * AI-Generated, 2026-10-18
 * 极简主线 HUD：左上角显示当前主线阶段、目标与「按 L 打开任务列表」。
 * <p>
 * 数据来自 {@code MissionV2.clientMissionData}（服务端每 20 tick 同步一次）；
 * 快捷键文案取自 {@link KeyBoradInput#Mission}，改键后自动跟随。
 */
public final class MainQuestHud {

    public static final Minecraft mc = Minecraft.getInstance();
    private static final int X = 6;
    private static final int Y = 6;
    private static final int PADDING = 7;
    private static final int LINE_HEIGHT = 13;
    /** 三行：标题、目标、快捷键提示。 */
    public static final int HUD_HEIGHT = PADDING + LINE_HEIGHT * 3;

    private MainQuestHud() {
    }

    public static final IGuiOverlay MAIN_QUEST_HUD = (gui, guiGraphics, partialTick, width, height) -> {
        if (!shouldShow()) {
            return;
        }
        Component title = MainQuestHelper.clientTitle();
        Component objective = MainQuestHelper.clientObjective();
        Component hint = Te.s("按 ", KeyBoradInput.Mission.getTranslatedKeyMessage(),
                " 打开任务列表", ChatFormatting.GRAY);
        Font font = mc.font;
        int textWidth = Math.max(font.width(title),
                Math.max(objective == null ? 0 : font.width(objective), font.width(hint)));
        guiGraphics.fill(X, Y, X + textWidth + PADDING * 2, Y + HUD_HEIGHT, 0x90000000);
        guiGraphics.drawString(font, title, X + PADDING, Y + 4, 0xFFFFFFFF);
        if (objective != null) {
            guiGraphics.drawString(font, objective, X + PADDING, Y + 4 + LINE_HEIGHT, 0xFFE0E0E0);
        }
        guiGraphics.drawString(font, hint, X + PADDING, Y + 4 + LINE_HEIGHT * 2, 0xFFA0A0A0);
    };

    /** 供其它左上角 HUD 计算纵向位置。 */
    public static boolean shouldShow() {
        return mc.player != null && mc.screen == null && !ClientUtils.isInBattle
                && !mc.options.hideGui && MainQuestHelper.clientTitle() != null;
    }
}
