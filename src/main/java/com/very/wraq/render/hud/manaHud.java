package com.very.wraq.render.hud;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

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
    private static final ResourceLocation MANA_PANEL = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manapanel.png");
    private static final ResourceLocation MANA_FILL = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manafill.png");

    private static final ResourceLocation Swiftness_Empty = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/swiftness_empty.png");
    private static final ResourceLocation Swiftness_Full = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/swiftness_full.png");
    private static final ResourceLocation Swiftness_Bar = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/swiftness_bar.png");
    private static final ResourceLocation Swiftness_Bardot = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/swiftness_bardot.png");
    private static final ResourceLocation Cold_Bardot = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/cold_bardot.png");
    private static final ResourceLocation Mission_Paper = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/mission_paper.png");

    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, width, height) -> {
        if (!mc.player.isCreative()) {
            int x = width / 2;
            int y = height;
            GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

            if (!mc.player.isShiftKeyDown()) {
                guiGraphics.blit(MANA_PANEL, x + 75, y - 47, 0, 0, 64, 64, 64, 64);
                double MaxMana = ClientManaData.getMaxMana();
                double CurrentMana = ClientManaData.getCurrentMana();
                int Rate = (int) Math.floor(20 * (CurrentMana / MaxMana));
                for (int i = 0; i <= 20; i++) {
                    if (Rate >= i) {
                        guiGraphics.blit(MANA_FILL, x + 75, y - 37 - i, 0, 0, 64, 64, //94
                                64, 64);
                    } else {
                        break;
                    }
                }
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", CurrentMana)).withStyle(CustomStyle.styleOfMana), x + 107, y - 38, 0);

                guiGraphics.blit(Swiftness_Bar, x + 135, y - 30, 0, 0, 16, 32, 16, 32);

                double MaxSwift = ClientSwiftData.getMaxSwift();
                double CurrentSwift = ClientSwiftData.getCurrentSwift();
                int RateSwift = (int) ((CurrentSwift / MaxSwift) * 12);
                if (MaxSwift != 0) {
                    for (int i = 0; i < 3; i++) {
                        if (CurrentSwift >= (33 * (i + 1)))
                            guiGraphics.blit(Swiftness_Full, x + 120, y - 17 - i * 8, 0, 0, 20, 20, 20, 20);
                        else guiGraphics.blit(Swiftness_Empty, x + 120, y - 17 - i * 8, 0, 0, 20, 20, 20, 20);
                    }
                    for (int i = 0; i < 24; i++) {
                        if (RateSwift > (i / 2)) {
                            guiGraphics.blit(Swiftness_Bardot, x + 135, y - 10 - i, 0, 0, 16, 16, 16, 16);
                        } else {
                            break;
                        }
                    }
                }

                double MaxCold = ClientColdNum.getMaxCold();
                double CurrentCold = ClientColdNum.getCurrentCold();
                int ColdRate = (int) ((CurrentCold / MaxCold) * 12);
                if (MaxCold != 0 && CurrentCold != 0) {
                    guiGraphics.blit(Swiftness_Bar, x + 140, y - 30, 0, 0, 16, 32, 16, 32);
                    for (int i = 0; i < 24; i++) {
                        if (ColdRate > (i / 2)) {
                            guiGraphics.blit(Cold_Bardot, x + 140, y - 10 - i, 0, 0, 16, 16, 16, 16);
                        } else {
                            break;
                        }
                    }
                }
            }
/*
            RenderSystem.setShaderTexture(0,MANA_BOTTLE);
            guiGraphics.blit(MANA_BOTTLE,x+90,y-52,0,0,12,12,12,12);
*/
            /*        fontRenderer.drawShadow(poseStack,"test",208,208,27850);*/
            /*        GuiComponent.drawCenteredString(poseStack,fontRenderer,Component.literal("test"),x-50,y-52,27856);*/
/*
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.valueOf(ClientManaData.getValue())),x+107,y-49,16733695);
*/
            float maxHealth = mc.player.getMaxHealth();
            float currentHealth = mc.player.getHealth();

            int healthRate = (int) ((currentHealth / maxHealth) * 20);
            for (int i = 0; i < 10; i++) {
                guiGraphics.blit(EMPTY_HEALTH, x - 94 + (i * 8), y - 42, 0, 0, 16, 16, 16, 16);
            }
            for (int i = 0; i < healthRate / 2; i++) {
                guiGraphics.blit(FULL_HEALTH, x - 94 + (i * 8), y - 42, 0, 0, 16, 16, 16, 16);
            }
            if (healthRate % 2 != 0) {
                guiGraphics.blit(HALF_HEALTH, x - 94 + (healthRate / 2 * 8), y - 42, 0, 0, 16, 16, 16, 16);
            }

            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", currentHealth)), x - 103, y - 39, 5635925);


/*            if (ClientUtils.MissionIndex < 100) {

                if (ClientUtils.MissionList.size() > 0) {

                    if (ClientUtils.MissionList.size() > 1) {
                        if (ClientUtils.MissionChange) {
                            if (ClientUtils.ListIndex < ClientUtils.MissionList.size() - 1) ClientUtils.ListIndex ++;
                            else ClientUtils.ListIndex = 0;
                            ClientUtils.MissionChange = false;
                        }
                    }
                    else {
                        ClientUtils.ListIndex = 0;
                    }

                    Mission mission = ClientUtils.MissionList.get(ClientUtils.ListIndex);
                    guiGraphics.drawCenteredString(fontRenderer,Component.literal(mission.getTitle()).
                                    withStyle(ChatFormatting.BLACK).withStyle(ChatFormatting.BOLD),
                            x + 177 + ClientUtils.MissionIndex,y - 150,0);
                    for (int i = 0; i < mission.getContent().length() / 7 + 1; i ++) {
                        if (i * 7 + 7 > mission.getContent().length()) {
                            guiGraphics.drawCenteredString(fontRenderer,Component.literal(mission.getContent().substring(i * 7 - 1)).
                                            withStyle(ChatFormatting.BLACK).withStyle(ChatFormatting.ITALIC),
                                    x + 177 + ClientUtils.MissionIndex,y - 134 + i * 16,0);
                        }
                        else {
                            guiGraphics.drawCenteredString(fontRenderer,Component.literal(mission.getContent().substring(i * 7,i * 7 + 7)).
                                            withStyle(ChatFormatting.BLACK).withStyle(ChatFormatting.ITALIC),
                                    x + 177 + ClientUtils.MissionIndex,y - 134 + i * 16,0);
                        }
                    }
                    guiGraphics.drawCenteredString(fontRenderer,Component.literal("[" + (int)mission.getDes().x + "," + (int)mission.getDes().y + "," + (int)mission.getDes().z + "]").
                                    withStyle(ChatFormatting.BLACK).withStyle(ChatFormatting.ITALIC),
                            x + 177 + ClientUtils.MissionIndex,y - 134 + (mission.getContent().length() / 7 + 1) * 16,0);

                }
                else {
                    guiGraphics.drawCenteredString(fontRenderer,Component.literal("暂无任务。").
                                    withStyle(ChatFormatting.BLACK).withStyle(ChatFormatting.ITALIC),
                            x + 177 + ClientUtils.MissionIndex,y - 134,0);
                    guiGraphics.drawCenteredString(fontRenderer,Component.literal("按下大小写切换").
                                    withStyle(ChatFormatting.BLACK).withStyle(ChatFormatting.ITALIC),
                            x + 177 + ClientUtils.MissionIndex,y - 134 + 16,0);
                    guiGraphics.drawCenteredString(fontRenderer,Component.literal("关闭纸条。").
                                    withStyle(ChatFormatting.BLACK).withStyle(ChatFormatting.ITALIC),
                            x + 177 + ClientUtils.MissionIndex,y - 134 + 16 * 2,0);
                }
                guiGraphics.blit(Mission_Paper,x + 112 + ClientUtils.MissionIndex,y - 168,0, 0,128,128,128,128);

            }*/
        }
    });
}
