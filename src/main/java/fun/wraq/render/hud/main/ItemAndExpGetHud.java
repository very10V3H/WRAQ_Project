package fun.wraq.render.hud.main;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ItemAndExpGetHud {

    public static double getExp = 0;
    public static int lastFreshTick = 0;
    public static Queue<ItemStack> getItemStacks = new ArrayDeque<>();
    public static List<ItemStack> displayStacks = new ArrayList<>();
    public static int displayStartTick = 0;
    public static final IGuiOverlay ITEM_EXP_GET_HUD = ((gui, poseStack, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        Font fontRenderer = mc.font;
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        int xOffset = -315;
        int x = width / 2;
        int y = height;

        if (getExp != 0 && lastFreshTick + 30 >= ClientUtils.clientPlayerTick) {
            guiGraphics.drawString(fontRenderer, Te.s("经验值", ChatFormatting.LIGHT_PURPLE,
                            " + ", ChatFormatting.DARK_PURPLE, getExpString(getExp), CustomStyle.styleOfLucky),
                    x + xOffset + 5, y - 38, 0);
        }

        if (!displayStacks.isEmpty()) {
            guiGraphics.drawCenteredString(fontRenderer, Te.m("+", ChatFormatting.AQUA),
                    x * 16 + xOffset + 16, y - 10, 0);
        }
        for (int i = 0; i < displayStacks.size(); i++) {
            ItemStack stack = displayStacks.get(i);
            guiGraphics.renderItem(stack, x + i * 16 + xOffset, y - 18);
            guiGraphics.drawCenteredString(fontRenderer, Te.m(String.valueOf(stack.getCount())),
                    x + i * 16 + xOffset + 16, y - 10, 0);
        }
    });

    private static String getExpString(double expValue) {
        if (expValue < 10000) {
            return String.format("%.1f", getExp);
        }
        if (expValue < Math.pow(10, 8)) {
            return String.format("%.1fw", getExp / Math.pow(10, 4));
        }
        return String.format("%.1fe", getExp / Math.pow(10, 8));
    }

    public static void clientTick() {
        // 检测display表元素删除时间，若时间到达或者表为空，则删除，并放入新元素至display表，同时指定display表元素删除时间
        if (displayStacks.isEmpty()
                || displayStartTick + 30 < ClientUtils.clientPlayerTick
                || displayStartTick > ClientUtils.clientPlayerTick
                || displayStacks.size() < 4) {
            if (displayStartTick + 30 < ClientUtils.clientPlayerTick || displayStartTick > ClientUtils.clientPlayerTick) {
                displayStacks.clear();
            }
            for (int i = 0 ; i < Math.min(4 - displayStacks.size(), getItemStacks.size()) ; i ++) {
                displayStacks.add(getItemStacks.poll());
            }
            displayStartTick = ClientUtils.clientPlayerTick;
        }
    }
}
