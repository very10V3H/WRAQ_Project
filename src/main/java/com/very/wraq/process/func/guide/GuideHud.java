package com.very.wraq.process.func.guide;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;
import java.util.List;

public class GuideHud {
    public static boolean display = true;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    public static final IGuiOverlay GUIDE_HUD = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height / 2;
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        if (display && mc.screen == null) {
            if (Guide.clientStage == -1 || Guide.clientStage >= Guide.descriptionMap.size()) return;
            List<Component> components = new ArrayList<>(Guide.descriptionMap.get(Guide.clientStage));
            components.add(Component.literal("").withStyle(ChatFormatting.WHITE));
            components.add(Component.literal("按下[Caps Lock]打开/关闭此指引").withStyle(ChatFormatting.WHITE));
            components.add(Component.literal("你也可以前往按键绑定修改此开关按键").withStyle(ChatFormatting.WHITE));
            guiGraphics.renderComponentTooltip(fontRenderer, components, 0, (int) (y / 1.5));
        }
    });

}
