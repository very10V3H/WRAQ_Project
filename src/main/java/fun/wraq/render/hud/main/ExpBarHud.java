/**
 * AI-Generated, 2026-10-25
 */
package fun.wraq.render.hud.main;

import fun.wraq.process.system.xp.MyExpSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.Locale;

/**
 * 经验条 HUD：在经验条上渲染「当前经验 / 升级所需」。
 *
 * <p>VMD 的经验是自定义的（{@code MyExpSystem}）：</p>
 * <ul>
 *     <li>升级所需经验 = {@link MyExpSystem#getCurrentXpLevelUpNeedXpPoint(int)}（按等级指数增长）；</li>
 *     <li>当前经验 = 升级所需 × {@code experienceProgress}（服务端每 tick 把
 *         {@code Xp / 升级所需} 写进 {@code experienceProgress} 并同步到客户端）；</li>
 *     <li>数字用千分位显示，例如 {@code 12,345,678/24,800,000,000}；满级显示 {@code MAX}。</li>
 * </ul>
 *
 * <p>因为原版会把等级数字画在经验条正中间（会和经验文字重叠），这里接管整个经验条：
 * 取消原版 {@code EXPERIENCE_BAR} overlay，按原版样式重画底条与绿色进度，
 * 等级数字上移到条的上方，经验文字居中画在条上。</p>
 *
 * <p>若整合包仍装有 AsteorBar，则完全不接管（避免和它自带的经验条重复）。</p>
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ExpBarHud {

    /** 原版经验条贴图（icons.png 的 0,64 / 0,69 区域） */
    private static final ResourceLocation GUI_ICONS =
            new ResourceLocation("minecraft", "textures/gui/icons.png");
    private static final int BAR_WIDTH = 182;
    private static final int BAR_HEIGHT = 5;
    /** 经验文字缩放（5px 高的条上放 8px 字，缩到 0.7） */
    private static final float TEXT_SCALE = 0.7F;

    private static final int LEVEL_COLOR = 0x80FF20;
    private static final int TEXT_COLOR = 0xFFFFFFFF;

    public static final IGuiOverlay XP_BAR_HUD = ((gui, guiGraphics, partialTick, width, height) -> {
        if (ModList.get().isLoaded("asteorbar")) return;
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.gameMode == null || !mc.gameMode.hasExperience()) return;

        Font font = mc.font;
        int x = width / 2 - 91;
        int y = height - 29;

        // 经验条底图 + 绿色进度（与原版一致）
        guiGraphics.blit(GUI_ICONS, x, y, 0, 64, BAR_WIDTH, BAR_HEIGHT);
        float progress = Mth.clamp(player.experienceProgress, 0.0F, 1.0F);
        int fill = (int) (progress * 183.0F);
        if (fill > 0) {
            guiGraphics.blit(GUI_ICONS, x, y, 0, 69, Math.min(fill, BAR_WIDTH), BAR_HEIGHT);
        }

        // 等级数字上移，避免与条上的经验文字重叠
        if (player.experienceLevel > 0) {
            guiGraphics.drawCenteredString(font, String.valueOf(player.experienceLevel),
                    width / 2, y - 11, LEVEL_COLOR);
        }

        // 当前经验 / 升级所需，居中画在经验条上
        String text = expText(player, progress);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(width / 2.0, y, 0.0);
        guiGraphics.pose().scale(TEXT_SCALE, TEXT_SCALE, 1.0F);
        guiGraphics.drawCenteredString(font, text, 0, 0, TEXT_COLOR);
        guiGraphics.pose().popPose();
    });

    private static String expText(LocalPlayer player, float progress) {
        if (player.experienceLevel >= MyExpSystem.levelUpperLimit) {
            return "MAX";
        }
        double need = MyExpSystem.getCurrentXpLevelUpNeedXpPoint(player.experienceLevel);
        double current = need * progress;
        return format(current) + "/" + format(need);
    }

    /** 千分位整数显示。 */
    private static String format(double value) {
        return String.format(Locale.ROOT, "%,.0f", value);
    }

    /**
     * 接管原版经验条：只有在本模组会绘制经验条时才取消原版 overlay。
     */
    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
        if (ModList.get().isLoaded("asteorbar")) return;
        NamedGuiOverlay overlay = event.getOverlay();
        if (overlay != VanillaGuiOverlay.EXPERIENCE_BAR.type()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.gameMode == null || !mc.gameMode.hasExperience()) return;
        event.setCanceled(true);
    }
}
