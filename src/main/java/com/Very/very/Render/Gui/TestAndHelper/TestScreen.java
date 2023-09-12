package com.Very.very.Render.Gui.TestAndHelper;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TestScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/market.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    public TestScreen(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.vmd0") : Component.translatable("menu.vmd1"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createPauseMenu();
        }

    }

    private void createPauseMenu() {
/*        this.addRenderableWidget(new Button(this.width / 2 - 122, this.height / 4 - 20, 98, 20, Component.translatable("将全部银币转换为VB"), (button) -> {
            ModNetworking.sendToServer(new SilverC2SPacket(0));
            ModNetworking.sendToServer(new RequestC2SPacket());
        }));*/
    }
    public void tick() {
        super.tick();
        ClientUtils.MarketItemInfoGet();
    }

    public void render(PoseStack p_96310_, int x, int y, float p_96313_) {
/*        if (this.showPauseMenu) {
            this.renderBackground(p_96310_);
            drawCenteredString(p_96310_, this.font, Component.literal("手机").withStyle(ChatFormatting.BLACK), this.width / 2, 40, 16777215);
        } else {
            drawCenteredString(p_96310_, this.font, Component.literal("手机").withStyle(ChatFormatting.BLACK), this.width / 2, 10, 16777215);
        }*/
/*        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0,GUI_TEXTURE);
        this.blit(p_96310_, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, 300, 200);*/
/*        for(int i = 0; i < 5; i++){
            ClientUtils.MarketItemInfoGet();
            MarketItemInfo marketItemInfo = ClientUtils.MarketInfoArray[pageNum * 5 + i];
            if(marketItemInfo == null) break;
            if(Utils.itemStackCode.isEmpty()) Utils.itemStackCodeInit();
            int Num = Utils.itemStackCode.get(marketItemInfo.getItemStackName());
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1,1,1,1);
            RenderSystem.setShaderTexture(0,ClientUtils.resourceLocations[Num]);
            this.blit(p_96310_, this.width / 2 - 150 + 33, this.height / 2 - 100 + 17 + i * 32, 0, 0, 16, 16, 16, 16);
        }*/
/*        super.render(p_96310_, x, y, p_96313_);*/
    }

}
