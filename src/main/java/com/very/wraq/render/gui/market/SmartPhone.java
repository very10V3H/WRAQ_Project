package com.very.wraq.render.gui.market;

import com.mojang.blaze3d.systems.RenderSystem;
import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.SmartPhonePackets.Currency.GoldCoinC2SPacket;
import com.very.wraq.networking.misc.SmartPhonePackets.Currency.SilverCoinC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmartPhone extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/smartphone.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;

    public SmartPhone(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.vmd0") : Component.translatable("menu.vmd1"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createPauseMenu();
        }
    }

    private void createPauseMenu() {
        this.addRenderableWidget(Button.builder(Component.translatable("将全部银币转换为VB"), (p_280814_) -> {
            ModNetworking.sendToServer(new SilverCoinC2SPacket(0));
        }).pos(this.width / 2 - 122, this.height / 2 - 20 - 68).size(98, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("用VB兑换10银币"), (p_280814_) -> {
            ModNetworking.sendToServer(new SilverCoinC2SPacket(1));
        }).pos(this.width / 2 - 122, this.height / 2 + 5 - 68).size(98, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("用VB兑换64银币"), (p_280814_) -> {
            ModNetworking.sendToServer(new SilverCoinC2SPacket(2));
        }).pos(this.width / 2 - 122, this.height / 2 + 30 - 68).size(98, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("买入"), (p_280814_) -> {
/*            ModNetworking.sendToServer(new SecurityBuyC2SPacket(0));
            ModNetworking.sendToServer(new RequestC2SPacket());*/
        }).pos(this.width / 2 - 122, this.height / 2 + 75 - 68).size(40, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("售出"), (p_280814_) -> {
/*            ModNetworking.sendToServer(new SecuritySellC2SPacket(0));
            ModNetworking.sendToServer(new RequestC2SPacket());*/
        }).pos(this.width / 2 - 70, this.height / 2 + 75 - 68).size(40, 20).build());
        //酒店
        this.addRenderableWidget(Button.builder(Component.translatable("买入"), (p_280814_) -> {
/*            ModNetworking.sendToServer(new SecurityBuyC2SPacket(1));
            ModNetworking.sendToServer(new RequestC2SPacket());*/
        }).pos(this.width / 2 - 122, this.height / 2 + 110 - 68).size(40, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("售出"), (p_280814_) -> {
/*            ModNetworking.sendToServer(new SecuritySellC2SPacket(1));
            ModNetworking.sendToServer(new RequestC2SPacket());*/
        }).pos(this.width / 2 - 70, this.height / 2 + 110 - 68).size(40, 20).build());
        //矿业
        this.addRenderableWidget(Button.builder(Component.translatable("将全部金币转换为VB"), (p_280814_) -> {
            ModNetworking.sendToServer(new GoldCoinC2SPacket(0));
        }).pos(this.width / 2 + 22, this.height / 2 - 20 - 68).size(98, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("用VB兑换1金币"), (p_280814_) -> {
            ModNetworking.sendToServer(new GoldCoinC2SPacket(1));
        }).pos(this.width / 2 + 22, this.height / 2 + 5 - 68).size(98, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("用VB兑换10金币"), (p_280814_) -> {
            ModNetworking.sendToServer(new GoldCoinC2SPacket(2));
        }).pos(this.width / 2 + 22, this.height / 2 + 30 - 68).size(98, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("买入"), (p_280814_) -> {
/*            ModNetworking.sendToServer(new SecurityBuyC2SPacket(2));
            ModNetworking.sendToServer(new RequestC2SPacket());*/
        }).pos(this.width / 2 + 22, this.height / 2 + 75 - 68).size(40, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("售出"), (p_280814_) -> {
/*            ModNetworking.sendToServer(new SecuritySellC2SPacket(2));
            ModNetworking.sendToServer(new RequestC2SPacket());*/
        }).pos(this.width / 2 + 74, this.height / 2 + 75 - 68).size(40, 20).build());
        //渔业
        this.addRenderableWidget(Button.builder(Component.translatable("买入"), (p_280814_) -> {
/*            ModNetworking.sendToServer(new SecurityBuyC2SPacket(3));
            ModNetworking.sendToServer(new RequestC2SPacket());*/
        }).pos(this.width / 2 + 22, this.height / 2 + 110 - 68).size(40, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("售出"), (p_280814_) -> {
/*            ModNetworking.sendToServer(new SecuritySellC2SPacket(3));
            ModNetworking.sendToServer(new RequestC2SPacket());*/
        }).pos(this.width / 2 + 74, this.height / 2 + 110 - 68).size(40, 20).build());
        //建设、
        this.addRenderableWidget(Button.builder(Component.translatable("打开市场"), (p_280814_) -> {
            Minecraft.getInstance().setScreen(new MarketScreen(true, 0, -1));
        }).pos(this.width / 2 - 140, this.height / 2 + 135 - 68).size(40, 20).build());
    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float p_96313_) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int textureWidth = 300;
        int textureHeight = 200;
        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("当前VB余额:"), this.width / 2 + 75, this.height / 2 + 86, 16755200);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.2f", ClientUtils.VBNUM)), this.width / 2 + 125, this.height / 2 + 86, 5636095);

/*
        guiGraphics.drawCenteredString(fontRenderer,Component.literal("维瑞库尤酒店 当前股数:"),this.width / 2 - 90,this.height / 2 + 65 - 68,16755200);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.2f",ClientUtils.Security0Num)),this.width / 2 - 30,this.height / 2 + 65 - 68,5636095);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("维瑞库尤矿业 当前股数:"),this.width / 2 - 90,this.height / 2 + 100 - 68,16755200);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.2f",ClientUtils.Security1Num)),this.width / 2 - 30,this.height / 2 + 100 - 68,5636095);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("维瑞库尤渔业 当前股数:"),this.width / 2 + 52,this.height / 2 + 65 - 68,16755200);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.2f",ClientUtils.Security2Num)),this.width / 2 + 112,this.height / 2 + 65 - 68,5636095);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("维瑞库尤建设 当前股数:"),this.width / 2 + 52,this.height / 2 + 100 - 68,16755200);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.2f",ClientUtils.Security3Num)),this.width / 2 + 112,this.height / 2 + 100 - 68,5636095);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("当前股市收益:"),this.width / 2 - 60,this.height / 2 + 86,16755200);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.2f",ClientUtils.SecurityGet)),this.width / 2 - 9,this.height / 2 + 86,5636095);
*/

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("当前股市收益:"), this.width / 2 - 60, this.height / 2 + 86, 16755200);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("系统维护中"), this.width / 2 - 9, this.height / 2 + 86, 5636095);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("维瑞库尤酒店 当前股数:"), this.width / 2 - 90, this.height / 2 + 65 - 68, 16755200);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("系统维护中"), this.width / 2 - 30, this.height / 2 + 65 - 68, 5636095);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("维瑞库尤矿业 当前股数:"), this.width / 2 - 90, this.height / 2 + 100 - 68, 16755200);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("系统维护中"), this.width / 2 - 30, this.height / 2 + 100 - 68, 5636095);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("维瑞库尤渔业 当前股数:"), this.width / 2 + 52, this.height / 2 + 65 - 68, 16755200);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("系统维护中"), this.width / 2 + 112, this.height / 2 + 65 - 68, 5636095);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("维瑞库尤建设 当前股数:"), this.width / 2 + 52, this.height / 2 + 100 - 68, 16755200);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("系统维护中"), this.width / 2 + 112, this.height / 2 + 100 - 68, 5636095);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("当前股市收益:"), this.width / 2 - 60, this.height / 2 + 86, 16755200);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("系统维护中"), this.width / 2 - 9, this.height / 2 + 86, 5636095);


        /*
        guiGraphics.renderItem(Moditems.KazeSword3.get().getDefaultInstance(),this.width / 2 - 60,this.height / 2 + 86);
        guiGraphics.renderTooltip(fontRenderer,Moditems.KazeSword3.get().getDefaultInstance(),this.width / 2 - 60,this.height / 2 + 86);
*/
        super.render(p_96310_, x, y, p_96313_);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
