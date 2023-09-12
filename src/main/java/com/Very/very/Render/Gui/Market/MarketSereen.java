package com.Very.very.Render.Gui.Market;

import com.Very.very.Files.MarketItemInfo;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.SmartPhonePackets.*;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MarketSereen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/market.png");
    ResourceLocation POTION_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/item/purified_water.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int pageNum = 0;
    public MarketSereen(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.vmd2") : Component.translatable("menu.vmd3"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createPauseMenu();
        }
    }

    private void createPauseMenu() {
        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if(pageNum > 0) pageNum--;
        }).pos(this.width / 2 - 39, this.height / 2 - 20 + 97).size(20,20).build());
/*        this.addRenderableWidget(new Button(this.width / 2 - 39, this.height / 4 - 20 + 165, 20, 20, Component.translatable("←"), (p_96335_) -> {
            if(pageNum > 0) pageNum--;
        }));*/
        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if(pageNum < 100) pageNum++;
        }).pos(this.width / 2 + 20, this.height / 2 - 20 + 97).size(20,20).build());
/*        this.addRenderableWidget(new Button(this.width / 2 + 20, this.height / 4 - 20 + 165, 20, 20, Component.translatable("→"), (p_96335_) -> {
            if(pageNum < 100) pageNum++;
        }));*/
        this.addRenderableWidget(Button.builder(Component.translatable("收取"), (p_280814_) -> {
            ModNetworking.sendToServer(new RequestGetC2SPacket());
        }).pos(this.width / 2 - 75, this.height / 2 - 20 + 97).size(20,20).build());
/*        this.addRenderableWidget(new Button(this.width / 2 - 75, this.height / 4 - 20 + 165, 20, 20, Component.translatable("收取"), (p_96335_) -> {
            ModNetworking.sendToServer(new RequestGetC2SPacket());
        }));*/
        for(int i = 0; i < 5 ; i++){
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                if(ClientUtils.MarketInfoArray[pageNum * 5 + finalI] != null)
                {
                    ModNetworking.sendToServer(new BuyCheckC2SPacket(ClientUtils.MarketInfoArray[pageNum * 5 + finalI].getPlayer(),
                            ClientUtils.MarketInfoArray[pageNum * 5 + finalI].getItemStack(),
                            ClientUtils.MarketInfoArray[pageNum * 5 + finalI].getPrice()));
                }
            }).pos(this.width / 2 + 100, this.height / 2 - 84 + 32 * (i)).size(40,20).build());
/*            this.addRenderableWidget(new Button(this.width / 2 + 100, this.height / 2 - 84 + 32 * (i) , 40, 20, Component.translatable("购买"), (p_96335_) -> {
                if(ClientUtils.MarketInfoArray[pageNum * 5 + finalI] != null)
                {
                    ModNetworking.sendToServer(new BuyCheckC2SPacket(ClientUtils.MarketInfoArray[pageNum * 5 + finalI].getPlayer(),
                            ClientUtils.MarketInfoArray[pageNum * 5 + finalI].getItemStack(),
                            ClientUtils.MarketInfoArray[pageNum * 5 + finalI].getPrice()));
                }
            }));*/
        }
    }
    public void tick() {
        super.tick();
        ClientUtils.MarketItemInfoGet();
        ModNetworking.sendToServer(new RequestMarketC2SPacket());
        ModNetworking.sendToServer(new RequestC2SPacket());
    }

    public void render(GuiGraphics p_96310_, int x, int y, float p_96313_) {
/*        if (this.showPauseMenu) {
            this.renderBackground(p_96310_);
            drawCenteredString(p_96310_, this.font, Component.literal("手机").withStyle(ChatFormatting.BLACK), this.width / 2, 40, 16777215);
        } else {
            drawCenteredString(p_96310_, this.font, Component.literal("手机").withStyle(ChatFormatting.BLACK), this.width / 2, 10, 16777215);
        }*/
        GuiGraphics guiGraphics = new GuiGraphics(mc,mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0,GUI_TEXTURE);
        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, 300, 200);
        for(int i = 0; i < 5; i++){
            ClientUtils.MarketItemInfoGet();
            MarketItemInfo marketItemInfo = ClientUtils.MarketInfoArray[pageNum * 5 + i];
            if(marketItemInfo == null) break;
/*            if(Utils.itemStackCode.isEmpty()) Utils.itemStackCodeInit();*/
            if(Utils.PotionMap.isEmpty()) Utils.PotionMapInit();
            if(Utils.PotionMap.containsKey(marketItemInfo.getItemStackName())){
                ItemStack itemStack = Items.POTION.getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                itemStack.getOrCreateTag().putString("Potion",marketItemInfo.getItemStackName());
                itemStack.setCount(marketItemInfo.getItemStackCount());
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1,1,1,1);
/*                RenderSystem.setShaderTexture(0,POTION_TEXTURE);*/
/*
                guiGraphics.blit(POTION_TEXTURE, this.width / 2 - 150 + 33, this.height / 2 - 100 + 17 + i * 32, 0, 0, 16, 16, 16, 16);
*/
                guiGraphics.renderItem(itemStack,this.width / 2 - 150 + 33,this.height / 2 - 100 + 17 + i * 32);
                String PotionName = marketItemInfo.getItemStackName().substring(4);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("").append(Component.translatable("item.minecraft.potion.effect."+PotionName)),this.width / 2 - 150 + 100,this.height / 2 - 100 + 17 + i * 32,16755200);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("价格:"+marketItemInfo.getPrice()),this.width / 2 - 150 + 100,this.height / 2 - 100 + 27 + i * 32,16755200);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal(""+marketItemInfo.getItemStackCount()),this.width / 2 - 150 + 50,this.height / 2 - 100 + 27 + i * 32,16777215);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("卖家:"+marketItemInfo.getPlayer()),this.width / 2 + 25,this.height / 2 - 100 + 22 + i * 32,16755200);
            }
            else
            {
/*                int Num = Utils.itemStackCode.get(marketItemInfo.getItemStackName());*/
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1,1,1,1);
/*                RenderSystem.setShaderTexture(0,ClientUtils.resourceLocations[Num]);*/
                if (Utils.itemStackMap.isEmpty()) Utils.itemStackMapInit();
                ItemStack itemStack = Utils.itemStackMap.get(marketItemInfo.getItemStackName());
                if (itemStack != null) {
                    itemStack.setCount(marketItemInfo.getItemStackCount());
                    guiGraphics.renderItem(itemStack,this.width / 2 - 150 + 33,this.height / 2 - 100 + 17 + i * 32);
/*
                guiGraphics.blit(ClientUtils.resourceLocations[Num], this.width / 2 - 150 + 33, this.height / 2 - 100 + 17 + i * 32, 0, 0, 16, 16, 16, 16);
*/
                    guiGraphics.drawCenteredString(fontRenderer,Component.literal("").append(Component.translatable("item.vmd."+marketItemInfo.getItemStackName())),this.width / 2 - 150 + 100,this.height / 2 - 100 + 17 + i * 32,16755200);
                    guiGraphics.drawCenteredString(fontRenderer,Component.literal("价格:"+marketItemInfo.getPrice()),this.width / 2 - 150 + 100,this.height / 2 - 100 + 27 + i * 32,16755200);
                    guiGraphics.drawCenteredString(fontRenderer,Component.literal(""+marketItemInfo.getItemStackCount()),this.width / 2 - 150 + 50,this.height / 2 - 100 + 27 + i * 32,16777215);
                    guiGraphics.drawCenteredString(fontRenderer,Component.literal("卖家:"+marketItemInfo.getPlayer()),this.width / 2 + 25,this.height / 2 - 100 + 22 + i * 32,16755200);

                }
            }
        }
        guiGraphics.drawCenteredString(fontRenderer,Component.literal("当前VB余额:"),this.width / 2 + 75,this.height / 2 + 86,16755200);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.2f",ClientUtils.VBNUM)),this.width / 2 + 125,this.height / 2 + 86,5636095);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal(""+pageNum),this.width / 2 ,this.height / 2 - 20 + 105,16755200);
        super.render(p_96310_, x, y, p_96313_);
    }
}
