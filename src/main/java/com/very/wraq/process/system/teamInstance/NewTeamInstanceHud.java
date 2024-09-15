package com.very.wraq.process.system.teamInstance;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;
import java.util.List;

public class NewTeamInstanceHud {

    public static List<Component> clientPreparedPlayerNames = new ArrayList<>();
    public static List<Component> clientUnpreparedPlayerNames = new ArrayList<>();
    public static List<Component> clientJoinedPlayerNames = new ArrayList<>();

    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    public static final IGuiOverlay NEW_TEAM_INSTANCE_HUD = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height / 2;
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        boolean display = false;
        Component instanceName = null;
        int minNum = 0;
        int maxNum = 0;
        for (NewTeamInstance overworldInstance : NewTeamInstanceEvent.getOverworldInstances()) {
            if (mc.level.dimension().equals(Level.OVERWORLD) && mc.player.position().distanceTo(overworldInstance.prepareCenterPos) < overworldInstance.prepareDetectRange) {
                display = true;
                instanceName = overworldInstance.description;
                minNum = overworldInstance.minPlayerNum;
                maxNum = overworldInstance.maxPlayerNum;
            }
        }

        if (display && mc.screen == null) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("副本 - ").withStyle(ChatFormatting.RED).
                    append(instanceName));
            if (!clientJoinedPlayerNames.isEmpty()) {
                components.add(Component.literal(" 已就绪").withStyle(ChatFormatting.RED).
                        append(Component.literal("的玩家:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("(").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(String.valueOf(clientJoinedPlayerNames.size())).withStyle(ChatFormatting.AQUA)).
                        append(Component.literal("/" + maxNum + ")").withStyle(ChatFormatting.WHITE)));
                components.addAll(clientJoinedPlayerNames);
            } else {
                if (!clientUnpreparedPlayerNames.isEmpty()) {
                    components.add(Component.literal(" 未准备").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("的玩家:").withStyle(ChatFormatting.WHITE)));
                    components.addAll(clientUnpreparedPlayerNames);
                    components.add(Component.literal(" "));
                }
                components.add(Component.literal(" 已准备").withStyle(ChatFormatting.AQUA).
                        append(Component.literal("的玩家:").withStyle(ChatFormatting.WHITE)));
                components.addAll(clientPreparedPlayerNames);
            }
            guiGraphics.renderComponentTooltip(fontRenderer, components, 0, (int) (y * 1.5));
        }
    });

}
