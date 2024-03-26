package com.Very.very.Render.Gui.VillagerTrade;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Reputation.ReputationBuyRequestC2SPacket;
import com.Very.very.NetWorking.TradeBuyRequestC2SPacket;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class Trade extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/trade.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private String villagerName;



    public Trade(boolean p_96308_, String villagerName) {
        super(p_96308_ ? Component.translatable("menu.trade") : Component.translatable("menu.trade0"));
        this.showPauseMenu = p_96308_;
        this.villagerName = villagerName;
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

        for (int i = 0 ; i < 5 ; i ++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                ModNetworking.sendToServer(new TradeBuyRequestC2SPacket(villagerName,page * 5 + finalI));
            }).pos(this.width / 2 - 35 + 144, this.height / 2 - 75 + 32 * i).size(32,16).build());

        }
    }
    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {

        GuiGraphics guiGraphics = new GuiGraphics(mc,mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0,GUI_TEXTURE);

        if (TradeList.tradeRecipeMap.isEmpty() || TradeList.tradeContent.isEmpty()) TradeList.setTradeContent();

        List<ItemStack> targetItemList = new ArrayList<>();
        if (TradeList.tradeContent.containsKey(villagerName))
           targetItemList = TradeList.tradeContent.get(villagerName);

        for (int i = 0; i < 5; i ++) {
            if (page * 5 + i < targetItemList.size()) {
                ItemStack targetItemStack = targetItemList.get(page * 5 + i);
                targetItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                guiGraphics.renderItem(targetItemStack,
                        this.width / 2 - 100 - 33 + 217,this.height / 2 - 73 + 32 * i);
                guiGraphics.drawCenteredString(font,Component.literal("" + targetItemStack.getCount()).withStyle(ChatFormatting.WHITE),
                        this.width / 2 - 100 - 33 + 217 + 20 ,this.height / 2 - 73 + 32 * i + 8,0);

                if (x > this.width / 2 - 100 - 33 + 217 && x < this.width / 2 - 100 - 33 + 16 + 217
                        && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                    guiGraphics.renderTooltip(font,targetItemStack,x,y);
                }
                List<ItemStack> recipeList = TradeList.tradeRecipeMap.get(targetItemStack);
                for (int j = 0; j < recipeList.size(); j ++) {
                    ItemStack material = recipeList.get(j);
                    material.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                    guiGraphics.renderItem(material,
                            this.width / 2 - 100 - 33 + 150 - j * 30,this.height / 2 - 73 + 32 * i);
                    if (x > this.width / 2 - 100 - 33 + 150 - j * 30 && x < this.width / 2 - 100 - 33 + 16 + 150 - j * 30
                            && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                        guiGraphics.renderTooltip(font,material,x,y);
                    }
                    if (material.getCount() > 9) guiGraphics.drawCenteredString(font,Component.literal("" + material.getCount()).withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100 - 33 + 150 - j * 30 + 23 ,this.height / 2 - 73 + 32 * i + 8,0);
                    else guiGraphics.drawCenteredString(font,Component.literal("" + material.getCount()).withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100 - 33 + 150 - j * 30 + 20 ,this.height / 2 - 73 + 32 * i + 8,0);
                }

            }
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
