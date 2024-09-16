package com.very.wraq.process.system.smelt;

import com.mojang.blaze3d.systems.RenderSystem;
import com.very.wraq.common.Compute;
import com.very.wraq.common.fast.Te;
import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.process.system.vp.VpStore;
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

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SmeltProgressScreen extends Screen {

    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/smelt_gui.png");
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;

    public SmeltProgressScreen() {
        super(Component.translatable("menu.reputation_store0"));
    }

    protected void init() {
        this.createMenu();
    }

    private final List<SmeltRecipe> list = Smelt.getRecipeByTag(Smelt.clientData);

    private void createMenu() {

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) page--;
        }).pos(this.width / 2 - 39 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < (list.size() - 1) / 10) page++;
        }).pos(this.width / 2 + 20 - 4, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12, 12).build());

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("收取"), (p_280814_) -> {
                ModNetworking.sendToServer(new SmeltHarvestC2SPacket(finalI + page * 10));
            }).pos(this.width / 2 - 35, this.height / 2 - 83 + 32 * i).size(32, 16).build());
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("收取"), (p_280814_) -> {
                ModNetworking.sendToServer(new SmeltHarvestC2SPacket(finalI + 5 + page * 10));
            }).pos(this.width / 2 - 35 + 140, this.height / 2 - 83 + 32 * i).size(32, 16).build());
        }
    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics graphics, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int yOffset = 0;
        int xOffset = -16;
        int tick = ClientUtils.clientPlayerTick;
        List<Calendar> timeList = null;
        try {
            timeList = Smelt.getProgressFinishTimeByTag(Smelt.clientData);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 5; i++) {
            if (page * 10 + i < list.size() && page * 10 + i < timeList.size()) {
                displaySingleRecipe(list, timeList, graphics, page * 10 + i, tick, xOffset, x, y);
            }
            if (page * 10 + 5 + i < list.size() && page * 10 + 5 + i < timeList.size()) {
                displaySingleRecipe(list, timeList, graphics, page * 10 + 5 + i, tick, xOffset + 140, x, y);
            }
        }

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1)).withStyle(ChatFormatting.WHITE)
                , this.width / 2, this.height / 2 - 20 + 105, 0);

        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);
        super.render(graphics, x, y, v);
    }

    private void displaySingleRecipe(List<SmeltRecipe> list, List<Calendar> timeList, GuiGraphics guiGraphics, int index, int tick ,int xOffset, int x, int y) {
        SmeltRecipe smeltRecipe = list.get(index);
        Calendar finishedTime = timeList.get(index);
        index %= 5;
        if (smeltRecipe == null || finishedTime == null) {

            return;
        }
        int currentProductDisplayIndex = tick / 30 % smeltRecipe.productList.size();
        ItemStack currentDisplayProduct = smeltRecipe.productList.get(currentProductDisplayIndex);

        // 物品图标与数量
        Item item = currentDisplayProduct.getItem();
        guiGraphics.renderItem(currentDisplayProduct, this.width / 2 - 117 + xOffset, this.height / 2 - 83 + 32 * index);
        if (VpStore.getCountMap().getOrDefault(item, 1) > 1) {
            guiGraphics.drawCenteredString(font, Component.literal("" + currentDisplayProduct.getCount())
                            .withStyle(ChatFormatting.WHITE),
                    this.width / 2 - 117 + xOffset + 20, this.height / 2 - 83 + 32 * index, 0);
        }

        // 配方名
        guiGraphics.drawCenteredString(font, smeltRecipe.name, this.width / 2 - 56 + xOffset, this.height / 2 - 86 + 32 * index, 0);

        // 需要材料 -> 剩余时间
        Component leftTime = Te.m("剩余时间:" + Compute.getDifferenceFormatText(finishedTime, Calendar.getInstance()), ChatFormatting.AQUA);
        guiGraphics.drawCenteredString(font, leftTime, this.width / 2 - 56 + xOffset, this.height / 2 - 71 + 32 * index, 0);

        // 当指针移动至图表处时显示的ToolTip
        if (x > this.width / 2 - 117 + xOffset && x < this.width / 2 - 117 + 16 + xOffset
                && y > this.height / 2 - 83 + 32 * index && y < this.height / 2 - 83 + 32 * index + 16) {
            guiGraphics.renderTooltip(font, currentDisplayProduct, x, y);
        }

        // 鼠标移动至需要材料时显示的ToolTip
        if (x > this.width / 2 - 117 + xOffset + 16 && x < this.width / 2 - 117 + 90 + xOffset
                && y > this.height / 2 - 83 + 32 * index - 4 && y < this.height / 2 - 83 + 32 * index + 16 + 4) {
            guiGraphics.renderComponentTooltip(fontRenderer, smeltRecipe.getDescription(), x, y);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
