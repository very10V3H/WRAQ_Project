package fun.wraq.render.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ShieldHud {
    private static final ResourceLocation SHIELD = new ResourceLocation(Utils.MOD_ID,
            "textures/shield/shield.png");
    private static final ResourceLocation SHIELD0 = new ResourceLocation(Utils.MOD_ID,
            "textures/shield/shield0.png");
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    public static final IGuiOverlay HUD_SHIELD = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        if (ClientShieldData.get() > 0 && ClientUtils.isInBattle) {
            GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, SHIELD0);
            guiGraphics.blit(SHIELD0, x - 102, y - 52, 0, 0, 12, 12, 12, 12);
            RenderSystem.setShaderTexture(0, SHIELD);
            for (int i = 0; i < 9; i++) {
                if (ClientShieldData.get() > i) {
                    guiGraphics.blit(SHIELD, x - 92 + (i * 9), y - 52, 12, 12, 12, 12, //94
                            12, 12);
                } else {
                    break;
                }
            }
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.valueOf(ClientShieldData.getValue())), x - 107, y - 49, 11184810);
        }
    });
}
