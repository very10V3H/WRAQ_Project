package fun.wraq.render.hud.main;

import fun.wraq.common.fast.Te;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * AI-Generated, 2026-10-27
 * 左下角副本机制提示。玩家被 NoTeamInstance 中任意怪物击杀后，
 * 由服务端下发该副本的 getIntroduction()，仅在死亡界面显示。
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class InstanceMechanicHintHud {

    private static final Minecraft mc = Minecraft.getInstance();
    private static final int PADDING = 7;
    private static final int LINE_HEIGHT = 13;

    private static Component instanceName;
    private static List<Component> lines = List.of();

    private InstanceMechanicHintHud() {
    }

    public static void show(Component name, List<Component> introduction) {
        if (name == null || introduction == null || introduction.isEmpty()) {
            return;
        }
        instanceName = name;
        lines = List.copyOf(introduction);
    }

    /** 只在死亡界面绘制，避免重生后与聊天窗重叠。 */
    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post event) {
        if (!(event.getScreen() instanceof DeathScreen)) {
            clear();
            return;
        }
        if (isVisible()) {
            render(event.getGuiGraphics(), event.getScreen().width, event.getScreen().height);
        }
    }

    private static void clear() {
        instanceName = null;
        lines = List.of();
    }

    private static void render(GuiGraphics guiGraphics, int width, int height) {
        Component title = Te.s("怪物机制 - ", ChatFormatting.GOLD, instanceName);
        Font font = mc.font;
        int maxWidth = font.width(title);
        for (Component line : lines) {
            maxWidth = Math.max(maxWidth, font.width(line));
        }
        int panelWidth = Math.min(width - 12, maxWidth + PADDING * 2);
        int panelHeight = PADDING * 2 + LINE_HEIGHT * (1 + lines.size());
        int x = 6;
        int y = height - panelHeight - 6;
        guiGraphics.fill(x, y, x + panelWidth, y + panelHeight, 0x90000000);
        guiGraphics.drawString(font, title, x + PADDING, y + PADDING, 0xFFFFFFFF);
        for (int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(font, lines.get(i), x + PADDING,
                    y + PADDING + LINE_HEIGHT * (i + 1), 0xFFFFFFFF);
        }
    }

    private static boolean isVisible() {
        return instanceName != null && !lines.isEmpty() && mc.player != null;
    }
}
