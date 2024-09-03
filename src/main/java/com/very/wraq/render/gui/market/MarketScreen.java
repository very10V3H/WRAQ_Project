package com.very.wraq.render.gui.market;

import com.mojang.blaze3d.systems.RenderSystem;
import com.very.wraq.files.MarketItemInfo;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.SmartPhonePackets.BuyCheckC2SPacket;
import com.very.wraq.networking.misc.SmartPhonePackets.MarketDataC2SPacket;
import com.very.wraq.networking.misc.SmartPhonePackets.OffShellC2SPacket;
import com.very.wraq.networking.misc.SmartPhonePackets.RequestGetC2SPacket;
import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MarketScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/market.png");

    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int pageNum = 0;
    private EditBox nameSearchBox;
    private int sortByPrice = -1; // -1 不排序 0 升序 1 降序
    private String lastTimeString = "";
    public static int tempPage = 0;
    public static int tempSortByPrice = -1;
    public static boolean openFlag = false;

    public MarketScreen(boolean p_96308_, int page, int sortByPrice) {
        super(p_96308_ ? Component.translatable("menu.vmd2") : Component.translatable("menu.vmd3"));
        this.showPauseMenu = p_96308_;
        this.pageNum = page;
        this.sortByPrice = sortByPrice;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createPauseMenu();
            this.nameSearchBox = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 124, 200, 20, Component.translatable("teamsearch.search"));
            this.addWidget(this.nameSearchBox);
        }
    }

    private void createPauseMenu() {
        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (pageNum > 0) pageNum--;
        }).pos(this.width / 2 - 39, this.height / 2 - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (pageNum < (ClientUtils.marketInfo.size() - 1) / 5) pageNum++;
        }).pos(this.width / 2 + 20, this.height / 2 - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("收取"), (p_280814_) -> {
            ModNetworking.sendToServer(new RequestGetC2SPacket());
            mc.setScreen(new MarketScreen(true, pageNum, sortByPrice));
            ModNetworking.sendToServer(new MarketDataC2SPacket());
        }).pos(this.width / 2 - 75, this.height / 2 - 20 + 97).size(32, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("刷新"), (p_280814_) -> {
            ModNetworking.sendToServer(new MarketDataC2SPacket());
        }).pos(this.width / 2 - 120, this.height / 2 - 20 + 97).size(32, 20).build());

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                if (ClientUtils.marketInfo.size() > pageNum * 5 + finalI) {
                    MarketItemInfo marketItemInfo = ClientUtils.marketInfo.get(pageNum * 5 + finalI);
                    if (marketItemInfo != null) {
                        ModNetworking.sendToServer(new BuyCheckC2SPacket(marketItemInfo.getPlayer(),
                                marketItemInfo.getItemStack(), marketItemInfo.getPrice()));
                    }
                }
                mc.setScreen(new MarketScreen(true, pageNum, sortByPrice));
                ModNetworking.sendToServer(new MarketDataC2SPacket());
            }).pos(this.width / 2 + 100, this.height / 2 - 84 + 32 * (i)).size(40, 20).build());
        }

        for (int i = 0; i < 5; i++) {
            if (ClientUtils.marketInfo.size() > pageNum * 5 + i) {
                MarketItemInfo marketItemInfo = ClientUtils.marketInfo.get(pageNum * 5 + i);
                if (marketItemInfo != null && (marketItemInfo.getPlayer().equals(mc.player.getName().getString()) || mc.player.isCreative())) {
                    this.addRenderableWidget(Button.builder(Component.translatable("下架"), (p_280814_) -> {
                        ModNetworking.sendToServer(new OffShellC2SPacket(marketItemInfo.getPlayer(),
                                marketItemInfo.getItemStack(), marketItemInfo.getPrice()));
                        mc.setScreen(new MarketScreen(true, pageNum, sortByPrice));
                        ModNetworking.sendToServer(new MarketDataC2SPacket());
                    }).pos(this.width / 2 + 50, this.height / 2 - 84 + 32 * (i)).size(40, 20).build());
                }
            }
        }


    }

    public void tick() {
        super.tick();
        String targetValue = this.nameSearchBox.getValue();
        if (!targetValue.isEmpty() && !targetValue.equals(lastTimeString)) {
            lastTimeString = targetValue;
            List<MarketItemInfo> matchedListPriority0 = new ArrayList<>();
            List<MarketItemInfo> matchedListPriority1 = new ArrayList<>();
            List<MarketItemInfo> otherList = new ArrayList<>();
            ClientUtils.marketInfo.forEach(marketItemInfo -> {
                ItemStack itemStack = marketItemInfo.getItemStack();
                if (itemStack.getDisplayName().getString().contains(targetValue)) {
                    matchedListPriority0.add(marketItemInfo);
                    return;
                } else {
                    List<Component> components = itemStack.getTooltipLines(mc.player, TooltipFlag.NORMAL);
                    for (Component component : components) {
                        if (component.getString().contains(targetValue)) {
                            matchedListPriority1.add(marketItemInfo);
                            return;
                        }
                    }
                }
                otherList.add(marketItemInfo);
            });
            List<MarketItemInfo> list = new ArrayList<>();
            list.addAll(matchedListPriority0);
            list.addAll(matchedListPriority1);
            list.addAll(otherList);
            ClientUtils.marketInfo.clear();
            ClientUtils.marketInfo = list;
        }
        if (sortByPrice != -1) {
            if (mc.player.tickCount % 20 == 0) {
                if (sortByPrice == 0) {
                    ClientUtils.marketInfo.sort(((o1, o2) -> {
                        return (int) (o1.getPrice() - o2.getPrice());
                    }));
                } else {
                    ClientUtils.marketInfo.sort(((o1, o2) -> {
                        return (int) (o2.getPrice() - o1.getPrice());
                    }));
                }
            }
        }
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, 300, 200);
        for (int i = 0; i < 5; i++) {
            if (pageNum * 5 + i >= ClientUtils.marketInfo.size()) break;
            MarketItemInfo marketItemInfo = ClientUtils.marketInfo.get(pageNum * 5 + i);
            if (marketItemInfo == null) break;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            ItemStack itemStack = marketItemInfo.getItemStack();
            if (itemStack != null) {
                guiGraphics.renderItem(itemStack, this.width / 2 - 150 + 33, this.height / 2 - 100 + 17 + i * 32);
                if (x > this.width / 2 - 150 + 33 && x < this.width / 2 - 150 + 33 + 16
                        && y > this.height / 2 - 100 + 17 + i * 32 && y < this.height / 2 - 100 + 17 + i * 32 + 16) {
                    guiGraphics.renderTooltip(font, itemStack, x, y);
                }
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("").append(itemStack.getDisplayName()).withStyle(ChatFormatting.WHITE), this.width / 2 - 150 + 100, this.height / 2 - 100 + 17 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("价格:" + marketItemInfo.getPrice()).withStyle(ChatFormatting.WHITE), this.width / 2 - 150 + 100, this.height / 2 - 100 + 27 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + marketItemInfo.getItemStackCount()).withStyle(ChatFormatting.WHITE), this.width / 2 - 150 + 55, this.height / 2 - 100 + 27 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("卖家:" + marketItemInfo.getPlayer()).withStyle(ChatFormatting.WHITE), this.width / 2 + 18, this.height / 2 - 100 + 22 + i * 32, 0);
            }
        }
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("当前VB余额:").withStyle(ChatFormatting.WHITE), this.width / 2 + 75, this.height / 2 + 86, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.2f", ClientUtils.VBNUM)).withStyle(ChatFormatting.WHITE), this.width / 2 + 125, this.height / 2 + 86, 0);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (pageNum + 1)).withStyle(ChatFormatting.WHITE), this.width / 2, this.height / 2 - 20 + 105, 0);
        this.nameSearchBox.render(guiGraphics, x, y, v);
        super.render(p_96310_, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
