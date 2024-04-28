package com.very.wraq.render.gui.illustrate;

import com.very.wraq.valueAndTools.Utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if(page > 0) page--;
        }).pos(this.width / 2 - 39 + 5, this.height / 2 - 20 + 97).size(20,20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if(page < 100) page++;
        }).pos(this.width / 2 + 20 + 5, this.height / 2 - 20 + 97).size(20,20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12,12).build());

        this.addRenderableWidget(Button.builder(Component.translatable("武器"), (p_280814_) -> {
            this.type = 0;
            this.page = 0;
        }).pos(this.width / 2 - 120, this.height / 2 - 20 + 100).size(28,16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("防具"), (p_280814_) -> {
            this.type = 1;
            this.page = 0;
        }).pos(this.width / 2 - 120 + 40, this.height / 2 - 20 + 100).size(28,16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("饰品"), (p_280814_) -> {
            this.type = 2;
            this.page = 0;
        }).pos(this.width / 2 + 58, this.height / 2 - 20 + 100).size(28,16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("定制"), (p_280814_) -> {
            this.type = 3;
            this.page = 0;
        }).pos(this.width / 2 + 58 + 40, this.height / 2 - 20 + 100).size(28,16).build());
    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {

        GuiGraphics guiGraphics = new GuiGraphics(mc,mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0,GUI_TEXTURE);

        int XOffset = -28;
        switch (type) {
            case 0 -> {
                for (int i = 0; i < 5; i ++) {
                    for (int j = 0 ; j < 9 ; j ++) {
                        if (page * 45 + i * 9 + j < Utils.WeaponList.size()) {
                            ItemStack itemStack = Utils.WeaponList.get(page * 45 + i * 9 + j).getDefaultInstance();
                            itemStack.getOrCreateTagElement(Utils.MOD_ID).putBoolean(Display.DisplayString,true);
                            itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                            guiGraphics.renderItem(itemStack,
                                    this.width / 2 - 100 + j * 30 + XOffset,this.height / 2 - 73 + 32 * i);
                            if (x > this.width / 2 - 100 + j * 30 + XOffset && x < this.width / 2 - 100 + 16 + j * 30 + XOffset
                                    && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                                guiGraphics.renderTooltip(font,itemStack,x,y);
                            }
                        }
                    }
                }
            } // Weapon
            case 1 -> {
                for (int i = 0; i < 5; i ++) {
                    for (int j = 0 ; j < 9 ; j ++) {
                        if (page * 45 + i * 9 + j < Utils.ArmorList.size()) {
                            ItemStack itemStack = Utils.ArmorList.get(page * 45 + i * 9 + j).getDefaultInstance();
                            itemStack.getOrCreateTagElement(Utils.MOD_ID).putBoolean(Display.DisplayString,true);
                            itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                            guiGraphics.renderItem(itemStack,
                                    this.width / 2 - 100 + j * 30 + XOffset,this.height / 2 - 73 + 32 * i);
                            if (x > this.width / 2 - 100 + j * 30 + XOffset && x < this.width / 2 - 100 + 16 + j * 30 + XOffset
                                    && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                                guiGraphics.renderTooltip(font,itemStack,x,y);
                            }
                        }
                    }
                }
            } // Armor
            case 2 -> {
                for (int i = 0; i < 5; i ++) {
                    for (int j = 0 ; j < 9 ; j ++) {
                        if (page * 45 + i * 9 + j < Utils.CuriosList.size()) {
                            ItemStack itemStack = Utils.CuriosList.get(page * 45 + i * 9 + j).getDefaultInstance();
                            itemStack.getOrCreateTagElement(Utils.MOD_ID).putBoolean(Display.DisplayString,true);
                            itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                            guiGraphics.renderItem(itemStack,
                                    this.width / 2 - 100 + j * 30 + XOffset,this.height / 2 - 73 + 32 * i);
                            if (x > this.width / 2 - 100 + j * 30 + XOffset && x < this.width / 2 - 100 + 16 + j * 30 + XOffset
                                    && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                                guiGraphics.renderTooltip(font,itemStack,x,y);
                            }
                        }
                    }
                }
            } // Curios
            case 3 -> {
                for (int i = 0; i < 5; i ++) {
                    for (int j = 0 ; j < 9 ; j ++) {
                        if (page * 45 + i * 9 + j < Utils.CustomizedList.size()) {
                            ItemStack itemStack = Utils.CustomizedList.get(page * 45 + i * 9 + j).getDefaultInstance();
                            itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                            guiGraphics.renderItem(itemStack,
                                    this.width / 2 - 100 + j * 30 + XOffset,this.height / 2 - 73 + 32 * i);
                            if (x > this.width / 2 - 100 + j * 30 + XOffset && x < this.width / 2 - 100 + 16 + j * 30 + XOffset
                                    && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                                guiGraphics.renderTooltip(font,itemStack,x,y);
                            }
                        }
                    }
                }
            } // Customized

        }
        guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (page + 1)).withStyle(ChatFormatting.WHITE),this.width / 2 + 5 ,this.height / 2 - 22 + 105,0);

        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);

        super.render(p_96310_, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
