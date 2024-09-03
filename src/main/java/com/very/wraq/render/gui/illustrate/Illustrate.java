package com.very.wraq.render.gui.illustrate;

import com.mojang.blaze3d.systems.RenderSystem;
import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class Illustrate extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/illustrate.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private int type = 0;

    public Illustrate(boolean p_96308_, int type) {
        super(p_96308_ ? Component.translatable("menu.illustrate") : Component.translatable("menu.illustrate0"));
        this.showPauseMenu = p_96308_;
        this.type = type;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
        }
    }

    private void createMenu() {

        int X = this.width / 2;
        int Y = this.height / 2;
        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) page--;
        }).pos(X - 39 + 5, Y - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < 100) page++;
        }).pos(X + 20 + 5, Y - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X + 136, Y - 98).size(12, 12).build());

        this.addRenderableWidget(Button.builder(Component.translatable("武器"), (p_280814_) -> {
            this.type = 0;
            this.page = 0;
        }).pos(X + 150, Y - 98).size(28, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("防具"), (p_280814_) -> {
            this.type = 1;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20).size(28, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("饰品"), (p_280814_) -> {
            this.type = 2;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 2).size(28, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("宝石"), (p_280814_) -> {
            this.type = 6;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 3).size(28, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("制式"), (p_280814_) -> {
            this.type = 3;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 4).size(28, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("酿造"), (p_280814_) -> {
            this.type = 4;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 5).size(28, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("材料"), (p_280814_) -> {
            this.type = 5;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 6).size(28, 16).build());
    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int xOffset = -28;
        switch (type) {
            case 0 -> sameModule(Utils.weaponList, guiGraphics, x, y, xOffset);
            case 1 -> sameModule(Utils.armorList, guiGraphics, x, y, xOffset);
            case 2 -> sameModule(Utils.curiosList, guiGraphics, x, y, xOffset);
            case 3 -> sameModule(Utils.customizedList, guiGraphics, x, y, xOffset);
            case 4 -> sameModule(Display.getBrewingList(), guiGraphics, x, y, xOffset);
            case 5 -> sameModule(Display.materialList, guiGraphics, x, y, xOffset);
            case 6 -> sameModule(Display.gemList, guiGraphics, x, y, xOffset);
        }
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1)).withStyle(ChatFormatting.WHITE), this.width / 2 + 5, this.height / 2 - 22 + 105, 0);

        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);
        super.render(p_96310_, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void sameModule(List<Item> list, GuiGraphics guiGraphics, int x, int y, int xOffset) {
        if (list.isEmpty()) Display.setMaterialList();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (page * 45 + i * 9 + j < list.size()) {
                    ItemStack itemStack = list.get(page * 45 + i * 9 + j).getDefaultInstance();
                    itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                    guiGraphics.renderItem(itemStack,
                            this.width / 2 - 100 + j * 30 + xOffset, this.height / 2 - 73 + 32 * i);
                    if (x > this.width / 2 - 100 + j * 30 + xOffset && x < this.width / 2 - 100 + 16 + j * 30 + xOffset
                            && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                        guiGraphics.renderTooltip(font, itemStack, x, y);
                    }
                }
            }
        }
    }
}
