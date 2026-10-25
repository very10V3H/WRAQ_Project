package fun.wraq.render.hud.main;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.KeyBoradInput;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.process.system.missions.mainline.MainQuestHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.List;

/**
 * AI-Generated, 2026-10-18
 * 极简主线 HUD：左上角显示当前主线阶段、全部目标与「按 L 打开任务列表」。
 * <p>
 * 数据来自 {@code MissionV2.clientMissionData}（服务端每 20 tick 同步一次）；
 * 快捷键文案取自 {@link KeyBoradInput#Mission}，改键后自动跟随。
 * <p>
 * 战斗时默认隐藏；若当前阶段存在「击杀数 &gt; 1」的目标，且玩家正在与该目标战斗，
 * 则保持显示，方便查看击杀进度。
 */
public final class MainQuestHud {

    public static final Minecraft mc = Minecraft.getInstance();
    private static final int X = 6;
    private static final int Y = 6;
    private static final int PADDING = 7;
    private static final int LINE_HEIGHT = 13;
    /** 多目标阶段允许保持显示的最大搜索半径。 */
    private static final double TARGET_SEARCH_RADIUS = 32;
    private static int lastBattleCheckTick = Integer.MIN_VALUE;
    private static boolean lastBattleCheckResult;

    private MainQuestHud() {
    }

    public static final IGuiOverlay MAIN_QUEST_HUD = (gui, guiGraphics, partialTick, width, height) -> {
        if (!shouldShow()) {
            return;
        }
        Component title = MainQuestHelper.clientTitle();
        List<Component> objectives = MainQuestHelper.clientObjectiveLines();
        Component hint = Te.s("按 ", KeyBoradInput.Mission.getTranslatedKeyMessage(),
                " 打开任务列表", ChatFormatting.GRAY);
        Font font = mc.font;
        int textWidth = Math.max(font.width(title), font.width(hint));
        for (Component objective : objectives) {
            textWidth = Math.max(textWidth, font.width(objective));
        }
        int hudHeight = height();
        guiGraphics.fill(X, Y, X + textWidth + PADDING * 2, Y + hudHeight, 0x90000000);
        guiGraphics.drawString(font, title, X + PADDING, Y + 4, 0xFFFFFFFF);
        int line = 1;
        for (Component objective : objectives) {
            guiGraphics.drawString(font, objective, X + PADDING, Y + 4 + LINE_HEIGHT * line,
                    0xFFE0E0E0);
            line++;
        }
        guiGraphics.drawString(font, hint, X + PADDING, Y + 4 + LINE_HEIGHT * line, 0xFFA0A0A0);
    };

    /** 标题 + 全部目标 + 快捷键提示，行数随目标数量变化；供其它左上角 HUD 计算纵向位置。 */
    public static int height() {
        int objectiveCount = Math.max(1, MainQuestHelper.clientObjectiveLines().size());
        return PADDING + LINE_HEIGHT * (2 + objectiveCount);
    }

    /** 战斗时默认隐藏；正在与「击杀数 &gt; 1」的主线目标战斗时例外。 */
    public static boolean shouldShow() {
        if (mc.player == null || mc.screen != null || mc.options.hideGui
                || MainQuestHelper.clientTitle() == null) {
            return false;
        }
        return !ClientUtils.isInBattle || isFightingMultiKillTarget();
    }

    /**
     * 客户端侧的战斗判定：除 {@link ClientUtils#isInBattle} 外，还要求附近/最近攻击的
     * 怪物名与当前阶段「击杀数量 &gt; 1」的目标匹配，避免打其它怪时也强制显示。
     */
    private static boolean isFightingMultiKillTarget() {
        if (mc.player == null) {
            return false;
        }
        // 每 tick 最多扫描一次 32 格范围，避免 60fps 下反复分配实体列表
        int tick = mc.player.tickCount;
        if (tick == lastBattleCheckTick) {
            return lastBattleCheckResult;
        }
        lastBattleCheckTick = tick;
        lastBattleCheckResult = checkFightingMultiKillTarget();
        return lastBattleCheckResult;
    }

    private static boolean checkFightingMultiKillTarget() {
        List<String> targets = MainQuestHelper.clientMultiKillTargets();
        if (targets.isEmpty() || mc.player == null) {
            return false;
        }
        if (mc.player.getLastHurtMob() instanceof Mob lastHurtMob
                && targets.contains(originName(lastHurtMob))) {
            return true;
        }
        if (mc.player.getLastHurtByMob() instanceof Mob lastHurtByMob
                && targets.contains(originName(lastHurtByMob))) {
            return true;
        }
        for (Mob mob : mc.player.level().getEntitiesOfClass(Mob.class,
                mc.player.getBoundingBox().inflate(TARGET_SEARCH_RADIUS))) {
            if (mob.isAlive() && targets.contains(originName(mob))) {
                return true;
            }
        }
        return false;
    }

    /** 与 {@code MobSpawn.getMobOriginName} 相同：去掉名字前的 "Lv.xx " 前缀。 */
    private static String originName(Mob mob) {
        String name = mob.getName().getString();
        int space = name.indexOf(' ');
        return space >= 0 ? name.substring(space + 1) : name;
    }
}
