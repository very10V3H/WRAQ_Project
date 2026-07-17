package fun.wraq.events.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI-Generated, 2026-05-16
 * <p>
 * Enables mouse-wheel scrolling for item tooltips that exceed 75% of the screen height.
 * When a tooltip is over-height, the default rendering is cancelled and replaced
 * with a scissor-clipped render that occupies at most 75% of the screen vertically,
 * positioned so the visible window extends above the mouse when needed.
 */
@Mod.EventBusSubscriber(Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class ScrollableTooltipManager {

    private static final Map<String, Double> scrollOffsets = new HashMap<>();
    private static final int SCROLL_SPEED = 20;
    private static final int SCROLLBAR_WIDTH = 4;
    private static final double VISIBLE_RATIO = 0.9;

    private static boolean isTooltipOverflowing = false;
    private static int scissorMinX, scissorMinY, scissorMaxX, scissorMaxY;
    private static int tooltipTotalHeight;
    private static int visibleAreaTop;
    private static int visibleHeight;
    private static String currentTooltipKey;
    private static long lastTooltipTime;

    // Solid tooltip background colors
    private static final int BG_COLOR = 0xF0100010;
    private static final int BORDER_COLOR = 0x505000FF;

    @SubscribeEvent
    public static void onPreRenderTooltip(RenderTooltipEvent.Pre event) {
        List<ClientTooltipComponent> components = event.getComponents();
        if (components.isEmpty()) return;

        Font font = event.getFont();
        int screenHeight = event.getScreenHeight();

        int totalHeight = 0;
        for (ClientTooltipComponent comp : components) {
            totalHeight += comp.getHeight();
        }

        // Only activate when tooltip height exceeds 75% of screen height
        int threshold = (int) (screenHeight * VISIBLE_RATIO);
        if (totalHeight <= threshold) {
            clearState();
            return;
        }

        event.setCanceled(true);

        ItemStack stack = event.getItemStack();
        String key = getTooltipKey(stack);
        currentTooltipKey = key;
        lastTooltipTime = System.currentTimeMillis();

        int width = computeTooltipWidth(font, components);
        int mouseX = event.getX();
        int mouseY = event.getY();

        visibleHeight = threshold;

        // Position visible window: mouse in upper portion, top can extend above mouse
        int visibleTop = mouseY - visibleHeight / 4;
        visibleTop = Mth.clamp(visibleTop, 0, screenHeight - visibleHeight);

        visibleAreaTop = visibleTop;

        double maxScroll = Math.max(0, totalHeight - visibleHeight);
        double scrollOffset = Mth.clamp(scrollOffsets.getOrDefault(key, 0.0), 0, maxScroll);
        scrollOffsets.put(key, scrollOffset);

        tooltipTotalHeight = totalHeight;

        // Render tooltip starting at visibleTop, with content translated by scroll
        int tooltipX = mouseX;
        int tooltipBaseY = visibleTop;

        GuiGraphics graphics = event.getGraphics();
        graphics.pose().pushPose();
        graphics.pose().translate(0, -scrollOffset, 400);

        int scMinX = tooltipX - 4;
        int scMinY = Math.max(0, visibleTop - 5);
        int scMaxX = tooltipX + width + 4 + SCROLLBAR_WIDTH;
        int scMaxY = Math.min(screenHeight, visibleTop + visibleHeight + 5);
        graphics.enableScissor(scMinX, scMinY, scMaxX, scMaxY);

        scissorMinX = scMinX;
        scissorMinY = scMinY;
        scissorMaxX = scMaxX;
        scissorMaxY = scMaxY;
        isTooltipOverflowing = true;

        renderTooltipBackground(graphics, tooltipX, tooltipBaseY, width, totalHeight);

        // Pass 1: renderText — ClientTextTooltip (vanilla text)
        int currentY = tooltipBaseY;
        for (int i = 0; i < components.size(); i++) {
            ClientTooltipComponent comp = components.get(i);
            int compX = (i == 0) ? tooltipX + (width - comp.getWidth(font)) / 2 : tooltipX;
            comp.renderText(font, compX, currentY, graphics.pose().last().pose(), graphics.bufferSource());
            currentY += comp.getHeight() + (i == 0 ? 2 : 0);
        }
        // Pass 2: renderImage — custom components (NewTooltip, TraditionalTooltip)
        currentY = tooltipBaseY;
        for (int i = 0; i < components.size(); i++) {
            ClientTooltipComponent comp = components.get(i);
            int compX = (i == 0) ? tooltipX + (width - comp.getWidth(font)) / 2 : tooltipX;
            comp.renderImage(font, compX, currentY, graphics);
            currentY += comp.getHeight() + (i == 0 ? 2 : 0);
        }

        graphics.disableScissor();
        graphics.pose().popPose();

        renderScrollbar(graphics, tooltipX, tooltipBaseY, width, totalHeight,
                visibleHeight, scrollOffset, maxScroll);
    }

    @SubscribeEvent
    public static void onMouseScrolledScreen(ScreenEvent.MouseScrolled.Pre event) {
        if (!isTooltipOverflowing) return;

        double mouseX = event.getMouseX();
        double mouseY = event.getMouseY();

        if (isMouseOverTooltip(mouseX, mouseY)) {
            applyScroll(event.getScrollDelta());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMouseScrolledNoScreen(InputEvent.MouseScrollingEvent event) {
        if (!isTooltipOverflowing) return;

        double mouseX = event.getMouseX();
        double mouseY = event.getMouseY();

        if (isMouseOverTooltip(mouseX, mouseY)) {
            applyScroll(event.getScrollDelta());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (isTooltipOverflowing && System.currentTimeMillis() - lastTooltipTime > 500) {
            clearState();
        }
    }

    private static void applyScroll(double scrollDelta) {
        double current = scrollOffsets.getOrDefault(currentTooltipKey, 0.0);
        double maxScroll = Math.max(0, tooltipTotalHeight - visibleHeight);
        double newOffset = Mth.clamp(current - scrollDelta * SCROLL_SPEED, 0, maxScroll);
        scrollOffsets.put(currentTooltipKey, newOffset);
        lastTooltipTime = System.currentTimeMillis();
    }

    private static boolean isMouseOverTooltip(double mouseX, double mouseY) {
        return mouseX >= scissorMinX && mouseX <= scissorMaxX
                && mouseY >= scissorMinY && mouseY <= scissorMaxY;
    }

    private static void renderScrollbar(GuiGraphics graphics, int x, int y, int tooltipWidth,
                                        int totalHeight, int visibleHeight,
                                        double scrollOffset, double maxScroll) {
        if (maxScroll <= 0) return;

        int barRight = x + tooltipWidth + 8;
        int barLeft = barRight - SCROLLBAR_WIDTH;
        int barTop = y + 3;
        int barHeight = visibleHeight - 10;
        int barBottom = barTop + barHeight;

        double visibleRatio = (double) visibleHeight / totalHeight;
        int thumbHeight = Math.max(8, (int) (barHeight * visibleRatio));
        int thumbTravel = barHeight - thumbHeight;
        int thumbOffset = (int) ((scrollOffset / maxScroll) * thumbTravel);

        int thumbTop = barTop + thumbOffset;
        int thumbBottom = thumbTop + thumbHeight;

        graphics.fill(barLeft, barTop, barRight, barBottom, 0x33FFFFFF);
        graphics.fill(barLeft, thumbTop, barRight, thumbBottom, 0xAAFFFFFF);
    }

    private static int computeTooltipWidth(Font font, List<ClientTooltipComponent> components) {
        int width = 0;
        for (ClientTooltipComponent comp : components) {
            width = Math.max(width, comp.getWidth(font));
        }
        return width;
    }

    private static String getTooltipKey(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return "empty";
        CompoundTag tag = stack.getTag();
        return stack.getDescriptionId() + (tag != null ? tag.hashCode() : 0);
    }

    private static void clearState() {
        isTooltipOverflowing = false;
        currentTooltipKey = null;
    }

    private static void renderTooltipBackground(GuiGraphics graphics, int x, int y, int width, int height) {
        // Outer 1px border on all four sides
        graphics.fill(x - 3, y - 4, x + width + 3, y - 3, BORDER_COLOR);
        graphics.fill(x - 3, y + height + 3, x + width + 3, y + height + 4, BORDER_COLOR);
        graphics.fill(x - 4, y - 3, x - 3, y + height + 3, BORDER_COLOR);
        graphics.fill(x + width + 3, y - 3, x + width + 4, y + height + 3, BORDER_COLOR);
        // Inner background fill — solid, no gradient
        graphics.fill(x - 3, y - 3, x + width + 3, y + height + 3, BG_COLOR);
    }

}
