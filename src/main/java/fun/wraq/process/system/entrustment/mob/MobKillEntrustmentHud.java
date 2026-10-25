package fun.wraq.process.system.entrustment.mob;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.render.hud.main.MainQuestHud;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * AI-Generated, 2026-10-18
 * 委托任务迷你 HUD：替代原「按 Tab 显示的 tooltip」。
 * <p>
 * 位置在左上角、主线 HUD 下方；三行：
 * <ul>
 *     <li>进行中：击杀目标 / 进度与用时 / 声望与提交评级；</li>
 *     <li>空闲：委托声望 / 今日与本周次数 / 惩罚或衰减提示。</li>
 * </ul>
 * 数据仍由 {@link MobKillEntrustmentInfoS2CPacket} 写入这些静态字段。
 * <p>
 * 显示规则（不再受 Tab 附加界面开关控制）：
 * <ol>
 *     <li>战斗状态下立即隐藏；</li>
 *     <li>玩家上线后显示 1min；</li>
 *     <li>玩家正在进行委托时一直显示；</li>
 *     <li>玩家完成委托后再保留 1min。</li>
 * </ol>
 */
public class MobKillEntrustmentHud {
    public static Component mobName;
    public static int targetCount;
    public static int currentCount;
    public static int startServerTick;
    public static int reputationExpiredMin;
    public static int reputation;
    public static int dailyFinishedTimes;
    public static int weeklyFinishedTimes;
    public static int totalFinishedTimes;
    public static int averageTick;
    public static int nextAllowAcceptTick;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private static final int X = 6;
    private static final int PADDING = 7;
    private static final int LINE_HEIGHT = 13;
    /** 三行内容。 */
    public static final int HUD_HEIGHT = PADDING + LINE_HEIGHT * 3;
    /** 上线 / 完成委托后强制显示的剩余 tick，由 {@link #clientTick()} 递减。 */
    public static int displayLeftTick = 0;
    /** 上线与完成委托后的保留显示时长。 */
    public static final int SHOW_ONCE_TICK = Tick.min(1);

    public static final IGuiOverlay ENTRUSTMENT_HUD = (gui, guiGraphics, partialTick, width, height) -> {
        if (!shouldShow()) {
            return;
        }
        int y = MainQuestHud.shouldShow() ? 6 + MainQuestHud.height() + 4 : 6;
        List<Component> lines = buildLines();
        int textWidth = 0;
        for (Component line : lines) {
            textWidth = Math.max(textWidth, fontRenderer.width(line));
        }
        guiGraphics.fill(X, y, X + textWidth + PADDING * 2, y + HUD_HEIGHT, 0x90000000);
        for (int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(fontRenderer, lines.get(i),
                    X + PADDING, y + 4 + i * LINE_HEIGHT, 0xFFE0E0E0);
        }
    };

    /**
     * 客户端每 tick 调用一次（见 {@code ClientPlayerTickEvent}），递减强制显示倒计时。
     * 仅在 HUD 真正可见时消耗，战斗 / 打开界面期间暂停，
     * 保证上线与完成委托各能完整看到 1min。
     */
    public static void clientTick() {
        if (displayLeftTick > 0 && !isHudSuppressed()) {
            --displayLeftTick;
        }
    }

    /**
     * 让 HUD 在接下来 1min 内保持显示。
     * 上线同步与完成委托时由服务端通过 {@link MobKillEntrustmentInfoS2CPacket} 触发。
     */
    public static void showForOneMinute() {
        displayLeftTick = Math.max(displayLeftTick, SHOW_ONCE_TICK);
    }

    /** 委托是否正在进行中（服务端下发了有效的击杀目标）。 */
    public static boolean isEntrustmentActive() {
        return mobName != null && !mobName.getString().isEmpty();
    }

    private static boolean shouldShow() {
        // 2.上线保留1min；3.委托进行中；4.完成委托后保留1min
        return !isHudSuppressed() && (isEntrustmentActive() || displayLeftTick > 0);
    }

    /** 被系统条件（界面、等级、战斗）遮蔽时不渲染，也不消耗保留显示的倒计时。 */
    private static boolean isHudSuppressed() {
        if (mc.player == null || mc.options.hideGui) {
            return true;
        }
        if (mc.screen != null && !(mc.screen instanceof ChatScreen)) {
            return true;
        }
        // 未达到接取等级时不显示，避免新手被无用信息干扰
        if (mc.player.experienceLevel < 75) {
            return true;
        }
        // 1.战斗时立即隐藏
        return isInBattle();
    }

    /**
     * 战斗判定沿用全局的战斗状态（{@link ClientUtils#isInBattle}，服务端每 20 tick 同步一次），
     * 并用 {@code hurtTime} 补充受击瞬间的即时反馈，保证战斗时立刻隐藏。
     */
    private static boolean isInBattle() {
        return ClientUtils.isInBattle || mc.player.hurtTime > 0;
    }

    private static List<Component> buildLines() {
        List<Component> lines = new ArrayList<>();
        if (isEntrustmentActive()) {
            int deltaTick = Math.max(0, ClientUtils.serverTick - startServerTick);
            lines.add(Te.s("「委托」", CustomStyle.styleOfWorld, " 击杀 ", mobName));
            Component progress = currentCount >= targetCount
                    ? Te.s(currentCount, "/", targetCount, ChatFormatting.GREEN)
                    : Te.s(currentCount, "/", targetCount, ChatFormatting.AQUA);
            lines.add(Te.s("进度 ", progress, " · 已进行 ",
                    MobKillEntrustment.getDeltaTimeFormatString(ClientUtils.serverTick, startServerTick),
                    ChatFormatting.AQUA));
            lines.add(Te.s("声望 ", reputation,
                    String.format(" (+%.0f%%)", MobKillEntrustment.getExRateOfReputation(reputation) * 100),
                    getTierStyle(reputation), " · 评级 ", rating(deltaTick)));
        } else {
            lines.add(Te.s("「委托」", CustomStyle.styleOfWorld, " 委托声望 ", reputation,
                    String.format(" (+%.0f%%)", MobKillEntrustment.getExRateOfReputation(reputation) * 100),
                    getTierStyle(reputation)));
            lines.add(Te.s("今日 ", dailyFinishedTimes, " 次 · 本周 ",
                    weeklyFinishedTimes, " 次 · 总 ", totalFinishedTimes, " 次", CustomStyle.styleOfMoon1));
            if (nextAllowAcceptTick > ClientUtils.serverTick) {
                lines.add(Te.s("惩罚剩余 ", MobKillEntrustment.getDeltaTimeFormatString(
                        nextAllowAcceptTick, ClientUtils.serverTick), ChatFormatting.RED));
            } else if (reputation > 0 && reputationExpiredMin > 0) {
                lines.add(Te.s("声望衰减剩余 ", reputationExpiredMin + "min",
                        ChatFormatting.GRAY, " · 平均用时 ",
                        MobKillEntrustment.getDeltaTimeFormatString(averageTick, 0), CustomStyle.styleOfSky));
            } else {
                lines.add(Te.s("平均用时 ",
                        MobKillEntrustment.getDeltaTimeFormatString(averageTick, 0), CustomStyle.styleOfSky,
                        " · 右键研院秘书接取委托", ChatFormatting.GRAY));
            }
        }
        return lines;
    }

    /** 按剩余时间给出当前可获得的最高评级。 */
    private static Component rating(int deltaTick) {
        List<MobKillEntrustment.TimeAndTier> tiers = PlanPlayer.clientPlanTier > 0
                ? MobKillEntrustment.planTimeAndTiers : MobKillEntrustment.timeAndTiers;
        for (MobKillEntrustment.TimeAndTier tier : tiers) {
            if (deltaTick <= Tick.min(tier.minutes())) {
                return tier.component();
            }
        }
        return PlanPlayer.clientPlanTier > 0
                ? Te.s("B+", ChatFormatting.LIGHT_PURPLE)
                : Te.s("已超时", ChatFormatting.GRAY);
    }

    public static ChatFormatting getTierStyle(int reputation) {
        if (reputation <= 5) {
            return ChatFormatting.GREEN;
        } else if (reputation < 10) {
            return ChatFormatting.YELLOW;
        } else return ChatFormatting.RED;
    }
}
