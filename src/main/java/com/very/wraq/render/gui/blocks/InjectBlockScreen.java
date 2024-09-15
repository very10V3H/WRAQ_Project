package com.very.wraq.render.gui.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import com.very.wraq.blocks.blocks.inject.InjectC2SPacket;
import com.very.wraq.blocks.blocks.inject.InjectRecipe;
import com.very.wraq.blocks.entity.InjectBlockEntity;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.InjectingRecipe;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.Limit.ScreenCloseC2SPacket;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class InjectBlockScreen extends AbstractContainerScreen<InjectBlockMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Utils.MOD_ID, "textures/gui/inject_block.png");

    private final InjectBlockMenu menu;

    public InjectBlockScreen(InjectBlockMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.menu = menu;
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(Button.builder(Component.translatable("灌注"), (p_280814_) -> {
            ModNetworking.sendToServer(new InjectC2SPacket(menu.blockEntity.getBlockPos()));
        }).pos(this.width / 2 - 7, this.height / 2 - 58).size(32, 16).build());
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float PartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());
        }
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {

        Minecraft mc = Minecraft.getInstance();

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        InjectBlockEntity injectBlockEntity = menu.blockEntity;
        ItemStack injectedItem = injectBlockEntity.getDownSlotItemStack();
        if (!injectedItem.is(Items.AIR)) {
            if (InjectRecipe.injectingRecipeMap.isEmpty()) InjectRecipe.setInjectingRecipeMap();

            if (InjectRecipe.injectingRecipeMap.containsKey(injectedItem.getItem())) {
                InjectingRecipe recipe = InjectRecipe.injectingRecipeMap.get(injectedItem.getItem());

                ItemStack neededMaterial = recipe.getForgingNeededMaterial().getDefaultInstance();
                neededMaterial.setCount(recipe.getMaterialCount());

                int X1 = 122;
                int Y1 = 25;
                int Y2 = 53;

                guiGraphics.drawCenteredString(mc.font, Component.literal("需要").withStyle(CustomStyle.styleOfInject),
                        x + 131, y + 8, 0);

                guiGraphics.drawCenteredString(mc.font, Component.literal("得到").withStyle(CustomStyle.styleOfInject),
                        x + 159, y + 36, 0);

                ItemStack getItem = recipe.getForgingGetItem().getDefaultInstance();
                getItem.getOrCreateTagElement(Utils.MOD_ID).merge(injectedItem.getOrCreateTagElement(Utils.MOD_ID));
                getItem.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                Compute.forgingHoverName(getItem);

                guiGraphics.renderFakeItem(neededMaterial, x + X1, y + Y1);
                guiGraphics.renderFakeItem(injectedItem, x + X1, y + Y2);
                guiGraphics.renderFakeItem(getItem, x + X1 + 28, y + Y2);

                if (recipe.getMaterialCount() > 1) {
                    guiGraphics.drawCenteredString(mc.font, Component.literal("x" + String.valueOf(recipe.getMaterialCount())).withStyle(ChatFormatting.WHITE),
                            x + X1 + 20, y + Y1 + 9, 0);
                }

                if (recipe.getOriginalMaterialNeedCount() > 1) {
                    guiGraphics.drawCenteredString(mc.font, Component.literal("x" + String.valueOf(recipe.getOriginalMaterialNeedCount())).withStyle(ChatFormatting.WHITE),
                            x + X1 + 20, y + Y2 + 9, 0);
                }

                if (mouseX > x + X1 && mouseX < x + X1 + 16 && mouseY > y + Y1 && mouseY < y + Y1 + 16) {
                    guiGraphics.renderTooltip(mc.font, neededMaterial, mouseX, mouseY);
                }

                if (mouseX > x + X1 && mouseX < x + X1 + 16 && mouseY > y + Y2 && mouseY < y + Y2 + 16) {
                    guiGraphics.renderTooltip(mc.font, injectedItem, mouseX, mouseY);
                }

                if (mouseX > x + X1 + 28 && mouseX < x + X1 + 28 + 16 && mouseY > y + Y2 && mouseY < y + Y2 + 16) {
                    guiGraphics.renderTooltip(mc.font, getItem, mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        ModNetworking.sendToServer(new ScreenCloseC2SPacket());
    }

}
