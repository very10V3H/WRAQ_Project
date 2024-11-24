package fun.wraq.render.gui.villagerTrade;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SmartPhonePackets.Currency.*;
import fun.wraq.networking.misc.SmartPhonePackets.MarketScreenC2SPacket;
import fun.wraq.networking.unSorted.TradeBuyRequestC2SPacket;
import fun.wraq.process.system.randomStore.RandomStore;
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
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class TradeScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/trade.png");
    ResourceLocation COIN_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/coin.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private String villagerName;

    public TradeScreen(boolean p_96308_, String villagerName) {
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
            if (page > 0) page--;
        }).pos(this.width / 2 - 39 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (fun.wraq.render.gui.villagerTrade.TradeList.tradeRecipeMap.isEmpty() || fun.wraq.render.gui.villagerTrade.TradeList.tradeContent.isEmpty()) fun.wraq.render.gui.villagerTrade.TradeList.setTradeContent();

            List<ItemStack> targetItemList = new ArrayList<>();
            if (fun.wraq.render.gui.villagerTrade.TradeList.tradeContent.containsKey(villagerName))
                targetItemList = fun.wraq.render.gui.villagerTrade.TradeList.tradeContent.get(villagerName);
            int size = targetItemList.size();
            if (page < (size - 1) / 5) page++;
        }).pos(this.width / 2 + 20 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12, 12).build());

        for (int i = 0; i < 7; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("兑换"), (p_280814_) -> {
                switch (finalI) {
                    case 0 -> ModNetworking.sendToServer(new SilverCoinC2SPacket(4)); // 1金换8银
                    case 1 -> ModNetworking.sendToServer(new CopperCoinC2SPacket(4)); // 1金换64铜
                    case 2 -> ModNetworking.sendToServer(new GoldCoinC2SPacket(4)); // 8银换1金
                    case 3 -> ModNetworking.sendToServer(new CopperCoinC2SPacket(3)); // 1银换8铜
                    case 4 -> ModNetworking.sendToServer(new GoldCoinC2SPacket(3)); // 64铜换1金
                    case 5 -> ModNetworking.sendToServer(new SilverCoinC2SPacket(3)); // 8铜换1银
                    case 6 -> ModNetworking.sendToServer(new GemPieceC2SPacket()); // 8铜换1银
                }
            }).pos(this.width / 2 + 256, this.height / 2 - 78 + 24 * i).size(24, 16).build());
        }

        this.addRenderableWidget(Button.builder(Component.translatable("VB兑1金币"), (p_280814_) -> {
            ModNetworking.sendToServer(new GoldCoinC2SPacket(1));
        }).pos(this.width / 2 - 146, this.height / 2 - 116).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x10"), (p_280814_) -> {
            ModNetworking.sendToServer(new GoldCoinC2SPacket(2));
        }).pos(this.width / 2 - 146 + 50, this.height / 2 - 116).size(24, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("VB兑10银币"), (p_280814_) -> {
            ModNetworking.sendToServer(new SilverCoinC2SPacket(1));
        }).pos(this.width / 2 - 146 + 50 + 26, this.height / 2 - 116).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x64"), (p_280814_) -> {
            ModNetworking.sendToServer(new SilverCoinC2SPacket(2));
        }).pos(this.width / 2 - 146 + 50 + 26 + 50, this.height / 2 - 116).size(24, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("VB兑10铜币"), (p_280814_) -> {
            ModNetworking.sendToServer(new CopperCoinC2SPacket(1));
        }).pos(this.width / 2 - 146 + 50 + 26 + 50 + 26, this.height / 2 - 116).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x64"), (p_280814_) -> {
            ModNetworking.sendToServer(new CopperCoinC2SPacket(2));
        }).pos(this.width / 2 - 146 + 50 + 26 + 50 + 26 + 50, this.height / 2 - 116).size(24, 16).build());

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                ModNetworking.sendToServer(new TradeBuyRequestC2SPacket(villagerName, page * 5 + finalI));
            }).pos(this.width / 2 - 35 + 144, this.height / 2 - 75 + 32 * i).size(32, 16).build());
        }

        this.addRenderableWidget(Button.builder(Component.translatable("玩家市场").withStyle(ChatFormatting.GOLD), (p_280814_) -> {
            ModNetworking.sendToServer(new MarketScreenC2SPacket(0));
        }).pos(this.width / 2 + 150, this.height / 2 - 98).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("兑换VB").withStyle(ChatFormatting.GOLD), (p_280814_) -> {
            ModNetworking.sendToServer(new AllCurrencyC2SPacket(true));
        }).pos(this.width / 2 + 200, this.height / 2 - 98).size(48, 16).build());
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
        int textureWidth = 300;
        int textureHeight = 200;
        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);

        int X = this.width / 2;
        int Y = this.height / 2;
        guiGraphics.blit(COIN_TEXTURE, X + 150, Y - 81, 0, 0, 200, 200, 200, 200);

        List<ItemStack> coinList = List.of(
                new ItemStack(ModItems.GOLD_COIN.get(), 1), new ItemStack(ModItems.silverCoin.get(), 12),
                new ItemStack(ModItems.GOLD_COIN.get()), new ItemStack(ModItems.copperCoin.get(), 144),
                new ItemStack(ModItems.silverCoin.get(), 12), new ItemStack(ModItems.GOLD_COIN.get()),
                new ItemStack(ModItems.silverCoin.get()), new ItemStack(ModItems.copperCoin.get(), 12),
                new ItemStack(ModItems.copperCoin.get(), 144), new ItemStack(ModItems.GOLD_COIN.get()),
                new ItemStack(ModItems.copperCoin.get(), 12), new ItemStack(ModItems.silverCoin.get()),
                new ItemStack(ModItems.gemPiece.get(), 64), new ItemStack(ModItems.completeGem.get()));

        for (int i = 0; i < coinList.size(); i++) {
            ItemStack coin = coinList.get(i);
            if (i % 2 == 0) {
                guiGraphics.renderItem(coin,
                        this.width / 2 + 158, this.height / 2 - 77 + i / 2 * 24);
                guiGraphics.drawCenteredString(font, Component.literal("" + coin.getCount()).withStyle(ChatFormatting.WHITE),
                        this.width / 2 + 158 + 20, this.height / 2 - 77 + 8 + i / 2 * 24, 0);
                if (x > this.width / 2 + 158 && x < this.width / 2 + 158 + 16
                        && y > this.height / 2 - 77 + i / 2 * 24 && y < this.height / 2 - 77 + i / 2 * 24 + 16) {
                    guiGraphics.renderTooltip(font, coin, x, y);
                }
            } else {
                guiGraphics.renderItem(coin,
                        this.width / 2 + 158 + 67, this.height / 2 - 77 + i / 2 * 24);
                guiGraphics.drawCenteredString(font, Component.literal("" + coin.getCount()).withStyle(ChatFormatting.WHITE),
                        this.width / 2 + 158 + 67 + 20, this.height / 2 - 77 + 8 + i / 2 * 24, 0);
                if (x > this.width / 2 + 158 + 67 && x < this.width / 2 + 158 + 67 + 16
                        && y > this.height / 2 - 77 + i / 2 * 24 && y < this.height / 2 - 77 + i / 2 * 24 + 16) {
                    guiGraphics.renderTooltip(font, coin, x, y);
                }
            }
        }

        int size = 0;
        if (!Objects.equals(villagerName, RandomStore.villagerName)) {
            if (fun.wraq.render.gui.villagerTrade.TradeList.tradeRecipeMap.isEmpty() || fun.wraq.render.gui.villagerTrade.TradeList.tradeContent.isEmpty()) fun.wraq.render.gui.villagerTrade.TradeList.setTradeContent();

            List<ItemStack> targetItemList = new ArrayList<>();
            if (fun.wraq.render.gui.villagerTrade.TradeList.tradeContent.containsKey(villagerName))
                targetItemList = fun.wraq.render.gui.villagerTrade.TradeList.tradeContent.get(villagerName);
            size = targetItemList.size();
            for (int i = 0; i < 5; i++) {
                if (page * 5 + i < targetItemList.size()) {
                    ItemStack targetItemStack = targetItemList.get(page * 5 + i);
                    targetItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                    guiGraphics.renderItem(targetItemStack,
                            this.width / 2 - 100 - 33 + 217, this.height / 2 - 73 + 32 * i);
                    guiGraphics.drawCenteredString(font, Component.literal("" + targetItemStack.getCount()).withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100 - 33 + 217 + 20, this.height / 2 - 73 + 32 * i + 8, 0);

                    if (x > this.width / 2 - 100 - 33 + 217 && x < this.width / 2 - 100 - 33 + 16 + 217
                            && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                        guiGraphics.renderTooltip(font, targetItemStack, x, y);
                    }
                    List<ItemStack> recipeList = TradeList.tradeRecipeMap.get(targetItemStack);
                    for (int j = 0; j < recipeList.size(); j++) {
                        ItemStack material = recipeList.get(j);
                        material.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                        guiGraphics.renderItem(material,
                                this.width / 2 - 100 - 33 + 150 - j * 30, this.height / 2 - 73 + 32 * i);
                        if (x > this.width / 2 - 100 - 33 + 150 - j * 30 && x < this.width / 2 - 100 - 33 + 16 + 150 - j * 30
                                && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                            guiGraphics.renderTooltip(font, material, x, y);
                        }
                        if (material.getCount() > 9)
                            guiGraphics.drawCenteredString(font, Component.literal("" + material.getCount()).withStyle(ChatFormatting.WHITE),
                                    this.width / 2 - 100 - 33 + 150 - j * 30 + 23, this.height / 2 - 73 + 32 * i + 8, 0);
                        else
                            guiGraphics.drawCenteredString(font, Component.literal("" + material.getCount()).withStyle(ChatFormatting.WHITE),
                                    this.width / 2 - 100 - 33 + 150 - j * 30 + 20, this.height / 2 - 73 + 32 * i + 8, 0);
                    }

                }
            }
        }

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1)).withStyle(ChatFormatting.WHITE), this.width / 2 + 5, this.height / 2 - 22 + 105, 0);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("共" + ((size - 1) / 5 + 1) + "页 " + (size) + "件物品").withStyle(ChatFormatting.WHITE),
                this.width / 2 + 80, this.height / 2 - 22 + 105, 0);
        super.render(p_96310_, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
