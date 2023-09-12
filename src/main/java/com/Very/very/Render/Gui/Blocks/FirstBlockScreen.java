package com.Very.very.Render.Gui.Blocks;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.Limit.ScreenCloseC2SPacket;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FirstBlockScreen extends AbstractContainerScreen<FirstBlockMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Utils.MOD_ID,"textures/gui/gem_infusing_station_gui3.png");
    public FirstBlockScreen(FirstBlockMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float PartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());
        }
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void onClose() {
        super.onClose();
        ModNetworking.sendToServer(new ScreenCloseC2SPacket());
    }
}
