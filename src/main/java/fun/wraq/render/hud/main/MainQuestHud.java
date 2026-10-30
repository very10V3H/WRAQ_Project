package fun.wraq.render.hud.main;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.KeyBoradInput;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.process.system.missions.mainline.MainQuestHelper;
import fun.wraq.process.system.missions.mission2.MissionV2;
import fun.wraq.process.system.missions.mission2.MissionV2Helper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * AI-Generated, 2026-10-18
 * 极简任务 HUD：左上角显示当前主线阶段、全部目标、已解锁支线任务与「按 L 打开任务列表」。
 * <p>
 * 数据来自 {@code MissionV2.clientMissionData}（服务端每 20 tick 同步一次）；
 * 快捷键文案取自 {@link KeyBoradInput#Mission}，改键后自动跟随。
 * <p>
 * 主线标题使用 aqua，支线标题与目标使用绿色；战斗时隐藏任务指引。
 */
public final class MainQuestHud {

    public static final Minecraft mc = Minecraft.getInstance();
    private static final int X = 6;
    private static final int Y = 6;
    private static final int PADDING = 7;
    private static final int LINE_HEIGHT = 13;

    private MainQuestHud() {
    }

    public static final IGuiOverlay MAIN_QUEST_HUD = (gui, guiGraphics, partialTick, width, height) -> {
        if (!shouldShow()) {
            return;
        }
        Component mainTitle = MainQuestHelper.clientTitle();
        List<Component> mainObjectives = MainQuestHelper.clientObjectiveLines();
        List<MissionV2> sideQuests = clientSideQuests();
        Component hint = Te.s("按 ", KeyBoradInput.Mission.getTranslatedKeyMessage(),
                " 打开任务列表", ChatFormatting.GRAY);
        Font font = mc.font;

        int textWidth = font.width(hint);
        if (mainTitle != null) {
            textWidth = Math.max(textWidth, font.width(mainTitle));
        }
        for (Component objective : mainObjectives) {
            textWidth = Math.max(textWidth, font.width(objective));
        }
        for (MissionV2 mission : sideQuests) {
            textWidth = Math.max(textWidth, font.width(sideTitle(mission)));
            textWidth = Math.max(textWidth, font.width(sideObjective(mission)));
        }

        int hudHeight = height();
        guiGraphics.fill(X, Y, X + textWidth + PADDING * 2, Y + hudHeight, 0x90000000);
        int line = 0;
        boolean hasSeparator = mainTitle != null && !sideQuests.isEmpty();
        if (mainTitle != null) {
            guiGraphics.drawString(font, mainTitle.copy().withStyle(ChatFormatting.AQUA),
                    X + PADDING, Y + 4, 0xFFFFFFFF);
            line++;
            for (Component objective : mainObjectives) {
                guiGraphics.drawString(font, objective, X + PADDING, Y + 4 + LINE_HEIGHT * line,
                        0xFFE0E0E0);
                line++;
            }
        }
        if (hasSeparator) {
            // 主线和支线之间用白色横线分隔。
            int separatorY = Y + 4 + LINE_HEIGHT * line + LINE_HEIGHT / 2;
            guiGraphics.fill(X + PADDING, separatorY,
                    X + PADDING + textWidth, separatorY + 1, 0xFFFFFFFF);
            line++;
        }
        for (MissionV2 mission : sideQuests) {
            guiGraphics.drawString(font, sideTitle(mission), X + PADDING,
                    Y + 4 + LINE_HEIGHT * line, 0xFFFFFFFF);
            line++;
            guiGraphics.drawString(font, sideObjective(mission), X + PADDING,
                    Y + 4 + LINE_HEIGHT * line, 0xFFFFFFFF);
            line++;
        }
        guiGraphics.drawString(font, hint, X + PADDING, Y + 4 + LINE_HEIGHT * line, 0xFFA0A0A0);
    };

    /** 主线标题/目标 + 已解锁支线 + 快捷键提示，行数随内容变化；供其它左上角 HUD 计算纵向位置。 */
    public static int height() {
        int mainLines = 0;
        if (MainQuestHelper.clientTitle() != null) {
            mainLines = 1 + Math.max(1, MainQuestHelper.clientObjectiveLines().size());
        }
        int sideLines = clientSideQuests().size() * 2;
        int separatorLines = MainQuestHelper.clientTitle() != null && !clientSideQuests().isEmpty() ? 1 : 0;
        return PADDING + LINE_HEIGHT * (mainLines + sideLines + separatorLines + 1);
    }

    /** 战斗中隐藏任务指引。 */
    public static boolean shouldShow() {
        if (mc.player == null || mc.screen != null || mc.options.hideGui) {
            return false;
        }
        if (MainQuestHelper.clientTitle() == null && clientSideQuests().isEmpty()) {
            return false;
        }
        return !ClientUtils.isInBattle;
    }

    /**
     * 客户端侧已解锁支线：状态为「可接取」或「进行中」。
     * 状态来自任务同步包，尚未解锁的空状态不会显示。
     */
    private static List<MissionV2> clientSideQuests() {
        CompoundTag data = MissionV2.clientMissionData;
        if (data == null || !data.contains(MissionV2Helper.MISSION_V2_STATUS_KEY)) {
            return List.of();
        }
        CompoundTag statusData = data.getCompound(MissionV2Helper.MISSION_V2_STATUS_KEY);
        List<MissionV2> result = new ArrayList<>();
        for (MissionV2 mission : MissionV2.values()) {
            if (mission.getCategory() != MissionV2.Category.SIDE) {
                continue;
            }
            String status = statusData.getString(mission.name());
            if (status.equals(MissionV2.Status.NOT_ACCEPTED)
                    || status.equals(MissionV2.Status.IN_PROGRESS)) {
                result.add(mission);
            }
        }
        return result;
    }

    private static Component sideTitle(MissionV2 mission) {
        return Component.literal("「支线」").withStyle(ChatFormatting.GREEN)
                .append(mission.title.copy().withStyle(ChatFormatting.GREEN));
    }

    private static Component sideObjective(MissionV2 mission) {
        MutableComponent line = Component.literal("  ")
                .append(mission.description.copy().withStyle(ChatFormatting.GREEN));
        if (clientStatus(mission).equals(MissionV2.Status.NOT_ACCEPTED)) {
            line.append(Component.literal("（可在任务列表接取）").withStyle(ChatFormatting.GRAY));
        }
        return line;
    }

    private static String clientStatus(MissionV2 mission) {
        CompoundTag data = MissionV2.clientMissionData;
        if (data == null || !data.contains(MissionV2Helper.MISSION_V2_STATUS_KEY)) {
            return "";
        }
        return data.getCompound(MissionV2Helper.MISSION_V2_STATUS_KEY).getString(mission.name());
    }
}
