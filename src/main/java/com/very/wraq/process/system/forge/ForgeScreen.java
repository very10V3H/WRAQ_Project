package com.very.wraq.process.system.forge;

import com.mojang.blaze3d.systems.RenderSystem;
import com.very.wraq.blocks.blocks.ForgeRecipe;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.process.system.forge.networking.ForgeC2SPacket;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
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

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ForgeScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/forge.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;

    public ForgeScreen() {
        super(Component.translatable("menu.forge"));
        this.showPauseMenu = true;
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
        }).pos(X - 39 + 2, Y - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            int size = ForgeEquipUtils.getPlayerInZoneItemList(mc.player).size();
            if (page < size / 3) page++;
        }).pos(X + 20 + 2, Y - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X + 136, Y - 98).size(12, 12).build());

        for (int i = 0; i < 3; i++) {
            int xOffset = -102 + 95 * i;
            int yOffset = -36;
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("\uD83D\uDEE0尝试锻造\uD83D\uDEE0"), (p_280814_) -> {
                ModNetworking.sendToServer(new ForgeC2SPacket(ForgeEquipUtils.getPlayerInZoneItemList(mc.player).get(page * 3 + finalI)));
            }).pos(X + xOffset - 24, Y + yOffset + 50).size(64, 16).build());
        }
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

        int size = ForgeEquipUtils.getPlayerInZoneItemList(mc.player).size();

        for (int i = 0; i < 3; i++) {
            if (page * 3 + i < size) {
                ItemStack itemStack = ForgeEquipUtils.getPlayerInZoneItemList(mc.player).get(page * 3 + i);
                int xOffset = -102 + 95 * i;
                int yOffset = -36;
                guiGraphics.renderItem(itemStack, this.width / 2 + xOffset, this.height / 2 + yOffset);
                if (x > this.width / 2 + xOffset && x < this.width / 2 + xOffset + 16
                        && y > this.height / 2 + yOffset && y < this.height / 2 + 16 + yOffset) {
                    guiGraphics.renderTooltip(font, itemStack, x, y);
                }

                guiGraphics.drawCenteredString(fontRenderer, itemStack.getDisplayName(),
                        this.width / 2 + xOffset + 8, this.height / 2 + yOffset - 20, 0);

                guiGraphics.drawCenteredString(fontRenderer, Component.literal("「材料清单」").withStyle(ChatFormatting.AQUA),
                        this.width / 2 + xOffset + 8, this.height / 2 + yOffset + 30, 0);

                if (x > this.width / 2 + xOffset - 16 && x < this.width / 2 + xOffset + 32
                        && y > this.height / 2 + yOffset + 30 - 8 && y < this.height / 2 + 16 + yOffset + 30) {
                    if (ForgeRecipe.forgeDrawRecipe.containsKey(itemStack.getItem())) {
                        List<ItemStack> materialList = ForgeRecipe.forgeDrawRecipe.get(itemStack.getItem());
                        List<Component> components = new ArrayList<>() {{
                            add(Component.literal("「材料清单」").withStyle(ChatFormatting.AQUA));
                            materialList.forEach(material -> {
                                int playerInventoryHasNum = Compute.itemStackCount(mc.player, material.getItem());
                                if (playerInventoryHasNum >= material.getCount()) {
                                    add(Component.literal("").append(material.getDisplayName()).
                                            append(Component.literal(" (")).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("" + material.getCount()).withStyle(ChatFormatting.AQUA)).
                                            append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                                            append(Component.literal("" + material.getCount()).withStyle(CustomStyle.styleOfMoon)).
                                            append(Component.literal(")").withStyle(ChatFormatting.WHITE)).
                                            append(Component.literal(" √").withStyle(ChatFormatting.GREEN)));
                                } else {
                                    add(Component.literal("").append(material.getDisplayName()).
                                            append(Component.literal(" (")).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("" + playerInventoryHasNum).withStyle(ChatFormatting.AQUA)).
                                            append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                                            append(Component.literal("" + material.getCount()).withStyle(CustomStyle.styleOfMoon)).
                                            append(Component.literal(")").withStyle(ChatFormatting.WHITE)).
                                            append(Component.literal(" -").withStyle(ChatFormatting.WHITE)));
                                }
                            });
                        }};
                        guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
                    }
                }
            }
        }

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1)).withStyle(ChatFormatting.WHITE),
                this.width / 2 + 2, this.height / 2 - 22 + 105, 0);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("共" + (size / 3 + 1) + "页 " + (size) + "件物品").withStyle(ChatFormatting.BLACK),
                this.width / 2 + 80, this.height / 2 - 22 + 105, 0);

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
