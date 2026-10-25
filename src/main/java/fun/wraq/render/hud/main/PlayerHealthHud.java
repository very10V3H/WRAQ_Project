/**
 * AI-Generated, 2026-10-17
 */
package fun.wraq.render.hud.main;

import fun.wraq.common.util.NumberUtils;
import fun.wraq.render.hud.ClientShieldData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

/**
 * 玩家自身血条 HUD（替代 AsteorBar 的玩家血条）。
 *
 * <p>VMD 玩家的最大生命值通常在数万以上，原版心形血条会按 {@code 最大生命 / 2} 铺满屏幕，
 * 因此在移除 AsteorBar 后改由 VMD 自己画一条比例血条：</p>
 * <ul>
 *     <li>位置沿用原版心形血条的位置（快捷栏左上方），圆角样式；</li>
 *     <li>颜色随血量比例平滑渐变（阈值处为该区间对应的原版原色）：</li>
 *     <li> 0% ~ 20%：下界红 {@code #A2001B} → 腥月红；</li>
 *     <li> 20% ~ 40%：腥月红 {@code #D63FA8} → 旭升岛黄；</li>
 *     <li> 40% ~ 75%：旭升岛黄 {@code #FFDC4A} → Wind 绿；</li>
 *     <li> ≥ 75%：Wind 绿 {@code #A1E475}；</li>
 *     <li>数字使用 {@link NumberUtils#abbreviate(double)} 缩写，如 {@code 2.5w/2.5w}；</li>
 *     <li>白色段为护盾（VMD 护盾 + 原版吸收伤害），紧接生命值右侧，比例为 护盾/最大生命值；
 *         当 生命值+护盾 超过最大生命值时，以 生命值+护盾 为分母整体压缩，保证不超出血条长度；</li>
 *     <li>同时取消原版 {@code PLAYER_HEALTH} overlay，避免心形血条铺满屏幕。</li>
 * </ul>
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerHealthHud {

    /** 与原版饱食度条同长（10 个图标 × 8px + 1px 间隔 = 81px） */
    private static final int BAR_WIDTH = 81;
    private static final int BAR_HEIGHT = 7;
    private static final int CORNER_RADIUS = 3;
    /** 数字缩放（血条变矮后数字同步缩小） */
    private static final float TEXT_SCALE = 0.7F;

    private static final int BORDER_COLOR = 0xFF000000;
    private static final int EMPTY_COLOR = 0xFF202020;
    /** 护盾段颜色（白色，紧接生命值右侧） */
    private static final int SHIELD_COLOR = 0xFFFFFFFF;
    private static final int TEXT_COLOR = 0xFFFFFFFF;

    /** 下界红 */
    private static final int COLOR_NETHER = 0xFFA2001B;
    /** 腥月红（CustomStyle.styleOfBloodMana） */
    private static final int COLOR_BLOOD_MOON = 0xFFD63FA8;
    /** 旭升岛黄（CustomStyle.styleOfSunIsland） */
    private static final int COLOR_SUN_ISLAND = 0xFFFFDC4A;
    /** Wind 绿（CustomStyle.styleOfWind） */
    private static final int COLOR_WIND = 0xFFA1E475;

    public static final IGuiOverlay PLAYER_HEALTH_HUD = ((gui, guiGraphics, partialTick, width, height) -> {
        // 整合包仍装有 AsteorBar 时交给它渲染，避免双份血条
        if (ModList.get().isLoaded("asteorbar")) return;
        LocalPlayer player = Minecraft.getInstance().player;
        // 与原版一致：创造/旁观/死亡时不显示血量
        if (player == null || !player.isAlive() || player.isCreative() || player.isSpectator()) return;

        Font font = Minecraft.getInstance().font;
        // 与原版血条/饱食度条同一左端位置，长度 81px 与原版饱食度条一致
        int x = width / 2 - 91;
        int y = height - 39;

        float maxHealth = Math.max(1.0F, player.getMaxHealth());
        float health = Mth.clamp(player.getHealth(), 0.0F, maxHealth);
        float ratio = health / maxHealth;

        // 护盾 = VMD 护盾 + 原版吸收伤害。
        // 生命值 + 护盾超过最大生命值时，改用「生命值 + 护盾」作分母，
        // 保证健康段 + 白色护盾段不会超出血条长度（参考英雄联盟的血条压缩）。
        float shield = Math.max(0.0F, player.getAbsorptionAmount())
                + Math.max(0.0F, ClientShieldData.getValue());
        float denominator = Math.max(maxHealth, health + shield);
        int healthWidth = denominator <= 0.0F ? 0
                : Math.min(BAR_WIDTH, Math.round(BAR_WIDTH * (health / denominator)));
        int shieldWidth = denominator <= 0.0F ? 0
                : Math.min(BAR_WIDTH - healthWidth, Math.round(BAR_WIDTH * (shield / denominator)));

        // 外边框 + 空槽（圆角，外框与内部同半径、错开 1px，保证描边均匀 1px）
        roundedRect(guiGraphics, x - 1, y - 1, x + BAR_WIDTH + 1, y + BAR_HEIGHT + 1,
                CORNER_RADIUS, BORDER_COLOR, true, true);
        roundedRect(guiGraphics, x, y, x + BAR_WIDTH, y + BAR_HEIGHT,
                CORNER_RADIUS, EMPTY_COLOR, true, true);

        // 当前血量（左端圆角；后面接护盾时右端直边，满血无护盾时右端圆角）
        if (healthWidth > 0) {
            boolean fullBar = shieldWidth <= 0 && healthWidth >= BAR_WIDTH;
            roundedRect(guiGraphics, x, y, x + healthWidth, y + BAR_HEIGHT,
                    CORNER_RADIUS, healthColor(ratio), true, fullBar);
        }

        // 白色护盾段（紧接生命值右侧）
        if (shieldWidth > 0) {
            int left = x + healthWidth;
            boolean reachesEnd = healthWidth + shieldWidth >= BAR_WIDTH;
            roundedRect(guiGraphics, left, y, left + shieldWidth, y + BAR_HEIGHT,
                    reachesEnd ? CORNER_RADIUS : 0, SHIELD_COLOR, false, reachesEnd);
        }

        // 数值（按 TEXT_SCALE 缩放，居中于血条）
        String text = NumberUtils.abbreviate(health) + "/" + NumberUtils.abbreviate(maxHealth);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(TEXT_SCALE, TEXT_SCALE, 1.0F);
        guiGraphics.drawCenteredString(font, text,
                Math.round((x + BAR_WIDTH / 2.0F) / TEXT_SCALE),
                Math.round((y + 0.7F) / TEXT_SCALE), TEXT_COLOR);
        guiGraphics.pose().popPose();
    });

    /**
     * 处理原版 GUI overlay：
     * <ul>
     *     <li>未安装 AsteorBar：取消原版心形血条（VMD 血量数值过大，heart 数量会铺满屏幕），交由
     *         {@link #PLAYER_HEALTH_HUD} 绘制；</li>
     *     <li>仍安装 AsteorBar：保持原有行为，取消原版护甲条/坐骑血量条，避免与其自绘的条重复。</li>
     * </ul>
     */
    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
        NamedGuiOverlay overlay = event.getOverlay();
        if (ModList.get().isLoaded("asteorbar")) {
            if (overlay == VanillaGuiOverlay.ARMOR_LEVEL.type()
                    || overlay == VanillaGuiOverlay.MOUNT_HEALTH.type()) {
                event.setCanceled(true);
            }
            return;
        }
        if (overlay == VanillaGuiOverlay.PLAYER_HEALTH.type()) {
            event.setCanceled(true);
        }
    }

    /**
     * 血量比例 → 血条颜色（阈值之间线性混色）。
     *
     * <pre>
     * 0%    ~ 20%  : 下界红 → 腥月红
     * 20%   ~ 40%  : 腥月红 → 旭升岛黄
     * 40%   ~ 75%  : 旭升岛黄 → Wind绿
     * 75%   ~ 100% : Wind绿
     * </pre>
     */
    private static int healthColor(float ratio) {
        float r = Mth.clamp(ratio, 0.0F, 1.0F);
        if (r >= 0.75F) {
            return COLOR_WIND;
        }
        if (r >= 0.40F) {
            return lerpColor(COLOR_SUN_ISLAND, COLOR_WIND, (r - 0.40F) / (0.75F - 0.40F));
        }
        if (r >= 0.20F) {
            return lerpColor(COLOR_BLOOD_MOON, COLOR_SUN_ISLAND, (r - 0.20F) / (0.40F - 0.20F));
        }
        return lerpColor(COLOR_NETHER, COLOR_BLOOD_MOON, r / 0.20F);
    }

    /**
     * 两个不透明颜色按 {@code t} 线性混色。
     */
    private static int lerpColor(int from, int to, float t) {
        float f = Mth.clamp(t, 0.0F, 1.0F);
        int a = (int) (((from >>> 24) & 0xFF) * (1 - f) + ((to >>> 24) & 0xFF) * f);
        int red = (int) (((from >>> 16) & 0xFF) * (1 - f) + ((to >>> 16) & 0xFF) * f);
        int green = (int) (((from >>> 8) & 0xFF) * (1 - f) + ((to >>> 8) & 0xFF) * f);
        int blue = (int) ((from & 0xFF) * (1 - f) + (to & 0xFF) * f);
        return (a << 24) | (red << 16) | (green << 8) | blue;
    }

    /**
     * 用逐行 {@code fill} 近似圆角矩形（GUI 只能画矩形）。
     * {@code roundLeft/roundRight} 指定左右两端是否圆角。
     */
    private static void roundedRect(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2,
                                    int radius, int color, boolean roundLeft, boolean roundRight) {
        if (x2 <= x1 || y2 <= y1) return;
        int r = Math.max(0, Math.min(radius, Math.min((x2 - x1) / 2, (y2 - y1) / 2)));
        if (r <= 0) {
            guiGraphics.fill(x1, y1, x2, y2, color);
            return;
        }
        int barHeight = y2 - y1;
        for (int row = 0; row < barHeight; row++) {
            int edgeDistance = Math.min(row, barHeight - 1 - row);
            int inset = edgeDistance < r ? cornerInset(r, edgeDistance) : 0;
            int leftInset = roundLeft ? inset : 0;
            int rightInset = roundRight ? inset : 0;
            guiGraphics.fill(x1 + leftInset, y1 + row, x2 - rightInset, y1 + row + 1, color);
        }
    }

    /**
     * 距离圆角边缘 {@code distance} 行处的水平内缩量。
     */
    private static int cornerInset(int radius, int distance) {
        double dy = radius - 0.5 - distance;
        double dx = Math.sqrt(Math.max(0.0, (double) radius * radius - dy * dy));
        return Math.max(0, (int) Math.round(radius - dx));
    }
}
