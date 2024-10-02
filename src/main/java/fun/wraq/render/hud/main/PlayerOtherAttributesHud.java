package fun.wraq.render.hud.main;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static fun.wraq.render.hud.main.HudUtils.*;

public class PlayerOtherAttributesHud {
    public static final IGuiOverlay OTHER_PLAYER_HUD = ((gui, poseStack, partialTick, width, height) -> {

        Minecraft mc = Minecraft.getInstance();
        Font fontRenderer = mc.font;

        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        // playerAttributes
        if (ClientUtils.mobAttribute == null && ClientUtils.playerAttribute != null && !ClientUtils.playerAttributeList.isEmpty()) {
            int xOffset = 0;
            int yOffset = 10;
            int standardX = 0;
            int standardY = 10;
            boolean isPlayer = (ClientUtils.playerAttribute.getId() == ClientUtils.playerId);
            if (isPlayer) {
                guiGraphics.drawString(fontRenderer, ClientUtils.playerAttribute.getDisplayName(), standardX, standardY - 8, 0);

                RenderSystem.setShaderTexture(0, ATTACK);
                guiGraphics.blit(ATTACK, 0 + xOffset, 0 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(0))), 20 + xOffset, 3 + yOffset, 5636095);

                RenderSystem.setShaderTexture(0, DEFENCE_PENETRATION);
                guiGraphics.blit(DEFENCE_PENETRATION, 0 + xOffset, 10 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(1) * 100)), 20 + xOffset, 13 + yOffset, 11184810);

                RenderSystem.setShaderTexture(0, CRIT_RATE);
                guiGraphics.blit(CRIT_RATE, 0 + xOffset, 20 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(2) * 100)), 20 + xOffset, 23 + yOffset, 16733695);

                RenderSystem.setShaderTexture(0, CRIT_DAMAGE);
                guiGraphics.blit(CRIT_DAMAGE, 0 + xOffset, 30 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(3) * 100)), 20 + xOffset, 33 + yOffset, 5592575);

                RenderSystem.setShaderTexture(0, MANA_DAMAGE);
                guiGraphics.blit(MANA_DAMAGE, 40 + xOffset, 0 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(4))), 60 + xOffset, 3 + yOffset, 16733695);

                RenderSystem.setShaderTexture(0, MANA_DEFENCE_PENETRATION);
                guiGraphics.blit(MANA_DEFENCE_PENETRATION, 40 + xOffset, 10 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(5) * 100)), 60 + xOffset, 13 + yOffset, 5592575);

                RenderSystem.setShaderTexture(0, MANA_REPLY);
                guiGraphics.blit(MANA_REPLY, 40 + xOffset, 20 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(6) + 5)), 60 + xOffset, 23 + yOffset, 16733695);

                RenderSystem.setShaderTexture(0, COOL_DOWN);
                guiGraphics.blit(COOL_DOWN, 40 + xOffset, 30 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(7) * 100)), 60 + xOffset, 33 + yOffset, 5636095);

                RenderSystem.setShaderTexture(0, HEALTH_STEAL);
                guiGraphics.blit(HEALTH_STEAL, 80 + xOffset, 0 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(8) * 100)), 100 + xOffset, 3 + yOffset, 16733525);

                RenderSystem.setShaderTexture(0, DEFENCE);
                guiGraphics.blit(DEFENCE, 80 + xOffset, 10 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(9))), 100 + xOffset, 13 + yOffset, 11184810);

                RenderSystem.setShaderTexture(0, MANA_DEFENCE);
                guiGraphics.blit(MANA_DEFENCE, 80 + xOffset, 20 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(10))), 100 + xOffset, 23 + yOffset, 5592575);

                RenderSystem.setShaderTexture(0, SPEED);
                guiGraphics.blit(SPEED, 80 + xOffset, 30 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(11) * 100)), 100 + xOffset, 33 + yOffset, 5635925);

                guiGraphics.blit(DEFENCE_PENETRATION0, 120 + xOffset, 0 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(12))).withStyle(ChatFormatting.GRAY), 140 + xOffset, 3 + yOffset, 0);

                guiGraphics.blit(MANA_PENETRATION0, 120 + xOffset, 10 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(13))).withStyle(ChatFormatting.BLUE), 140 + xOffset, 13 + yOffset, 0);

                guiGraphics.blit(ATTACK_RANGE_UP, 120 + xOffset, 20 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.2f", ClientUtils.playerAttributeList.get(14))).withStyle(CustomStyle.styleOfSea), 140 + xOffset, 23 + yOffset, 0);

                guiGraphics.blit(EXP_UP, 120 + xOffset, 30 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(15) * 100)).withStyle(ChatFormatting.LIGHT_PURPLE), 140 + xOffset, 33 + yOffset, 0);
            }
        }

    });
}
