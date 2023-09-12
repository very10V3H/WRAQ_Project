package com.Very.very.Render.Hud;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import com.mojang.blaze3d.systems.RenderSystem;

public class manaHud {
    private static final ResourceLocation FILLED_MANA = new ResourceLocation(Utils.MOD_ID,
            "textures/mana/filled_mana.png");
    private static final ResourceLocation EMPTY_MANA = new ResourceLocation(Utils.MOD_ID,
            "textures/mana/empty_mana.png");
    private static final ResourceLocation MANA_BOTTLE = new ResourceLocation(Utils.MOD_ID,
            "textures/item/evokersoul.png");
    private static final ResourceLocation FULL_HEALTH = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/fullhealth.png");
    private static final ResourceLocation HALF_HEALTH = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/halfhealth.png");
    private static final ResourceLocation EMPTY_HEALTH = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/emptyheal.png");
    private static final ResourceLocation FOOD = new ResourceLocation(Utils.MOD_ID,
            "textures/item/food.png");

    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, width, height) -> {
        if (!mc.player.isCreative()) {
            int x = width / 2;
            int y = height;
            GuiGraphics guiGraphics = new GuiGraphics(mc,mc.renderBuffers().bufferSource());
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            /*        RenderSystem.setShaderTexture(0, EMPTY_MANA);*/
            for(int i = 0; i < 9; i++) {
                guiGraphics.blit(EMPTY_MANA,x + 8  + (i * 9), y - 52,0,0,12,12, //54
                        12,12);
            }

            /*        RenderSystem.setShaderTexture(0, FILLED_MANA);*/
            for(int i = 0; i < 9; i++) {
                if(ClientManaData.get() > i) {
                    guiGraphics.blit(FILLED_MANA,x + 8 + (i * 9),y - 52,0,0,12,12, //94
                            12,12);
                } else {
                    break;
                }
            }
            RenderSystem.setShaderTexture(0,MANA_BOTTLE);
            guiGraphics.blit(MANA_BOTTLE,x+90,y-52,0,0,12,12,12,12);
            /*        fontRenderer.drawShadow(poseStack,"test",208,208,27850);*/
            /*        GuiComponent.drawCenteredString(poseStack,fontRenderer,Component.literal("test"),x-50,y-52,27856);*/
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.valueOf(ClientManaData.getValue())),x+107,y-49,16733695);
            float maxHealth = mc.player.getMaxHealth();
            float currentHealth = mc.player.getHealth();

            int healthRate = (int) ((currentHealth / maxHealth) * 20);
            for (int i = 0; i < 10; i++) {
                guiGraphics.blit(EMPTY_HEALTH,x - 94  + (i * 8), y - 42, 0,0,16,16,16,16);
            }
            for (int i = 0; i < healthRate/2; i++) {
                guiGraphics.blit(FULL_HEALTH,x - 94  + (i * 8), y - 42, 0,0,16,16,16,16);
            }
            if (healthRate % 2 != 0) {
                guiGraphics.blit(HALF_HEALTH,x - 94  + (healthRate/2 * 8), y - 42, 0,0,16,16,16,16);
            }
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",currentHealth)),x-103,y-39,5635925);

        }

    });
}
