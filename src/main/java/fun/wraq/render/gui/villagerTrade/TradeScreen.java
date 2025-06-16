package fun.wraq.render.gui.villagerTrade;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.equip.WraqMainHandEquip;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SmartPhonePackets.Currency.*;
import fun.wraq.networking.unSorted.TradeBuyRequestC2SPacket;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.randomStore.RandomStore;
import fun.wraq.render.gui.trade.weekly.WeeklyStore;
import fun.wraq.render.gui.trade.weekly.WeeklyStorePlayerData;
import fun.wraq.render.gui.trade.weekly.WeeklyStoreRecipe;
import fun.wraq.render.toolTip.CustomStyle;
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
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class TradeScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/new_trade_screen.png");
    ResourceLocation COIN_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/coin.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private String villagerName;
    private boolean isWeeklyStore = false;

    public TradeScreen(boolean p_96308_, String villagerName) {
        super(p_96308_ ? Component.translatable("menu.trade") : Component.translatable("menu.trade0"));
        this.showPauseMenu = p_96308_;
        this.villagerName = villagerName;
        if (villagerName.equals(TradeListNew.WEEKLY_STORE_VILLAGER_NAME)) {
            isWeeklyStore = true;
        }
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
            List<ItemStack> targetItemList = new ArrayList<>();
            if (fun.wraq.render.gui.villagerTrade.TradeList.tradeContent.containsKey(villagerName))
                targetItemList = fun.wraq.render.gui.villagerTrade.TradeList.tradeContent.get(villagerName);
            int size = targetItemList.size();
            if (page < (size - 1) / 10) page++;
        }).pos(this.width / 2 + 14, this.height / 2 - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12, 12).build());
        if (!isWeeklyStore) {
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
            this.addRenderableWidget(Button.builder(Component.translatable("兑换VB").withStyle(ChatFormatting.GOLD), (p_280814_) -> {
                ModNetworking.sendToServer(new AllCurrencyC2SPacket(true));
            }).pos(this.width / 2 - 146 + 50 + 26 + 50 + 26 + 50 + 26, this.height / 2 - 116).size(48, 16).build());
        }
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                ModNetworking.sendToServer(new TradeBuyRequestC2SPacket(villagerName, page * 10 + finalI));
            }).pos(this.width / 2 - 61, this.height / 2 - 75 + 32 * i).size(32, 16).build());
            this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                ModNetworking.sendToServer(new TradeBuyRequestC2SPacket(villagerName, page * 10 + finalI + 5));
            }).pos(this.width / 2 - 61 + 144, this.height / 2 - 75 + 32 * i).size(32, 16).build());
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
        int textureWidth = 300;
        int textureHeight = 200;
        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);

        int X = this.width / 2;
        int Y = this.height / 2;
        if (!isWeeklyStore) {
            guiGraphics.blit(COIN_TEXTURE, X + 150, Y - 81, 0, 0,
                    200, 200, 200, 200);
        }

        if (!isWeeklyStore) {
            List<ItemStack> coinList = List.of(
                    new ItemStack(ModItems.GOLD_COIN.get(), 1), new ItemStack(ModItems.SILVER_COIN.get(), 12),
                    new ItemStack(ModItems.GOLD_COIN.get()), new ItemStack(ModItems.COPPER_COIN.get(), 144),
                    new ItemStack(ModItems.SILVER_COIN.get(), 12), new ItemStack(ModItems.GOLD_COIN.get()),
                    new ItemStack(ModItems.SILVER_COIN.get()), new ItemStack(ModItems.COPPER_COIN.get(), 12),
                    new ItemStack(ModItems.COPPER_COIN.get(), 144), new ItemStack(ModItems.GOLD_COIN.get()),
                    new ItemStack(ModItems.COPPER_COIN.get(), 12), new ItemStack(ModItems.SILVER_COIN.get()),
                    new ItemStack(ModItems.GEM_PIECE.get(), 64), new ItemStack(ModItems.COMPLETE_GEM.get()));
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
        }
        int size = 0;
        if (!Objects.equals(villagerName, RandomStore.villagerName)) {
            List<ItemStack> targetItemList = new ArrayList<>();
            if (TradeList.tradeContent.containsKey(villagerName)) {
                targetItemList = TradeList.tradeContent.get(villagerName);
            }
            size = targetItemList.size();
            for (int i = 0; i < 10; i++) {
                if (page * 10 + i < targetItemList.size()) {
                    ItemStack targetItemStack = targetItemList.get(page * 10 + i);
                    if (i < 5) {
                        renderTradeRecipe(targetItemStack, i, x, y, guiGraphics, -98);
                    } else {
                        renderTradeRecipe(targetItemStack, i % 5, x, y, guiGraphics, 47);
                    }
                }
            }
        }
        if (!isWeeklyStore) {
            guiGraphics.drawCenteredString(fontRenderer, Te.s("当前VB余额:"),
                    this.width / 2 - 110, this.height / 2 + 83, 0);
            guiGraphics.drawCenteredString(fontRenderer, Te.s(String.format("%.2f", ClientUtils.VBNUM)),
                    this.width / 2 - 60, this.height / 2 + 83, 0);
        }
        guiGraphics.drawCenteredString(fontRenderer, Te.s("" + (page + 1)),
                this.width / 2, this.height / 2 + 83, 0);
        guiGraphics.drawCenteredString(fontRenderer,
                Te.s("共" + ((size - 1) / 10 + 1) + "页 " + (size) + "件物品"),
                this.width / 2 + 80, this.height / 2 + 83, 0);
        if (villagerName.equals(TradeListNew.WEEKLY_STORE_VILLAGER_NAME)) {
            guiGraphics.drawCenteredString(fontRenderer,
                    Te.s("研发采购 - 第",
                            WeeklyStorePlayerData.clientIssueCount, ChatFormatting.RED, "期" + " 每周五更新(测试阶段)"),
                    this.width / 2, this.height / 2 - 96, 0);
        }
        super.render(p_96310_, x, y, v);
    }

    private void renderTradeRecipe(ItemStack targetItemStack, int i, int x, int y, GuiGraphics guiGraphics, int xOffset) {
        targetItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        guiGraphics.renderItem(targetItemStack,
                this.width / 2 - 100 - 33 + 206 + xOffset,
                this.height / 2 - 75 + 32 * i);
        int count = 0;
        if (getMinecraft().player != null) {
            count = InventoryOperation.itemStackCount(getMinecraft().player, targetItemStack.getItem());
            if (count > 0) {
                guiGraphics.drawCenteredString(font, Te.s(count, CustomStyle.styleOfWorld),
                        this.width / 2 - 100 - 33 + 206 + xOffset,
                        this.height / 2 - 75 + 32 * i - 4, 0);
            }
        }
        guiGraphics.drawCenteredString(font, Component.literal("" + targetItemStack.getCount()).withStyle(ChatFormatting.WHITE),
                this.width / 2 - 100 - 33 + 206 + 20 + xOffset, this.height / 2 - 75 + 32 * i + 14, 0);
        if (x > this.width / 2 - 100 - 33 + 206 + xOffset && x < this.width / 2 - 100 - 33 + 16 + 206 + xOffset
                && y > this.height / 2 - 75 + 32 * i && y < this.height / 2 - 75 + 32 * i + 16) {
            Item item = targetItemStack.getItem();
            if (item instanceof WraqArmor || item instanceof WraqMainHandEquip) {
                ForgeEquipUtils.setForgeQualityOnEquip(targetItemStack, 4);
            }
            guiGraphics.renderTooltip(font, targetItemStack, x, y);
        }
        List<ItemStack> recipeList = TradeList.tradeRecipeMap.get(targetItemStack);
        if (recipeList.size() <= 3) {
            for (int j = 0; j < recipeList.size(); j++) {
                ItemStack material = recipeList.get(j);
                material.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                guiGraphics.renderItem(material,
                        this.width / 2 - 100 - 33 + 150 - j * 28 + xOffset,
                        this.height / 2 - 75 + 32 * i);
                count = 0;
                if (getMinecraft().player != null) {
                    count = InventoryOperation.itemStackCount(getMinecraft().player, material.getItem());
                    if (count > 0) {
                        guiGraphics.drawCenteredString(font, Te.s(count, CustomStyle.styleOfWorld),
                                this.width / 2 - 100 - 33 + 150 - j * 28 + xOffset,
                                this.height / 2 - 75 + 32 * i - 4, 0);
                    }
                }
                if (x > this.width / 2 - 100 - 33 + 150 - j * 28 + xOffset
                        && x < this.width / 2 - 100 - 33 + 16 + 150 - j * 28 + xOffset
                        && y > this.height / 2 - 75 + 32 * i
                        && y < this.height / 2 - 75 + 32 * i + 16) {
                    guiGraphics.renderTooltip(font, material, x, y);
                }
                if (x > this.width / 2 - 100 - 33 + 150 + 20 + xOffset
                        && x < this.width / 2 - 100 - 33 + 32 + 150 + 20 + xOffset
                        && y > this.height / 2 - 75 + 32 * i
                        && y < this.height / 2 - 75 + 32 * i + 16) {
                    WeeklyStore.recipes.stream()
                            .filter(recipe -> recipe.isSameRecipe(recipeList, targetItemStack))
                            .findAny().ifPresent(weeklyStoreRecipe
                                    -> WeeklyStorePlayerData.clientPlayerChangedCount.keySet().stream().filter(s -> {
                                return WeeklyStoreRecipe.getRecipeByString(s)
                                        .isSameRecipe(weeklyStoreRecipe.getMaterialList(), targetItemStack);
                            }).findAny().ifPresent(key
                                    -> guiGraphics.renderTooltip(font,
                                    Te.s("限购: ", CustomStyle.styleOfGold,
                                    WeeklyStorePlayerData.clientPlayerChangedCount.getOrDefault(key, 0),
                                    CustomStyle.styleOfWorld, " / ", CustomStyle.styleOfStone,
                                    weeklyStoreRecipe.count, " - ", "每期"), x, y)));
                }
                if (material.getCount() > 9) {
                    guiGraphics.drawCenteredString(font, Component.literal("" + material.getCount()).withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100 - 33 + 150 - j * 28 + 22 + xOffset, this.height / 2 - 75 + 32 * i + 14, 0);
                } else {
                    guiGraphics.drawCenteredString(font, Component.literal("" + material.getCount()).withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100 - 33 + 150 - j * 28 + 19 + xOffset, this.height / 2 - 75 + 32 * i + 14, 0);
                }
            }
        } else {
            ItemStack chestStack = new ItemStack(Items.CHEST);
            guiGraphics.renderItem(chestStack,
                    this.width / 2 - 100 - 33 + 150 + xOffset, this.height / 2 - 75 + 32 * i);
            if (x > this.width / 2 - 100 - 33 + 150 + xOffset && x < this.width / 2 - 100 - 33 + 16 + 150 + xOffset
                    && y > this.height / 2 - 75 + 32 * i && y < this.height / 2 - 75 + 32 * i + 16) {
                List<Component> components = new ArrayList<>();
                components.add(Te.s("需要材料", ChatFormatting.GOLD));
                for (int i1 = 0; i1 < recipeList.size(); i1++) {
                    ItemStack stack = recipeList.get(i1);
                    components.add(Te.s(" " + (i1 + 1) + ".", ChatFormatting.AQUA, " ", stack,
                            " * " + stack.getCount(), ChatFormatting.AQUA));
                }
                guiGraphics.renderComponentTooltip(font, components, x, y);
            }
            for (int j = 1; j < 3; j++) {
                ItemStack material = recipeList.get((ClientUtils.clientPlayerTick / 30 + j) % recipeList.size());
                material.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                guiGraphics.renderItem(material,
                        this.width / 2 - 100 - 33 + 150 - j * 28 + xOffset, this.height / 2 - 75 + 32 * i);
                if (x > this.width / 2 - 100 - 33 + 150 - j * 28 + xOffset && x < this.width / 2 - 100 - 33 + 16 + 150 - j * 28 + xOffset
                        && y > this.height / 2 - 75 + 32 * i && y < this.height / 2 - 75 + 32 * i + 16) {
                    guiGraphics.renderTooltip(font, material, x, y);
                }
                if (material.getCount() > 9) {
                    guiGraphics.drawCenteredString(font, Component.literal("" + material.getCount()).withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100 - 33 + 150 - j * 28 + 23 + xOffset, this.height / 2 - 75 + 32 * i + 14, 0);
                } else {
                    guiGraphics.drawCenteredString(font, Component.literal("" + material.getCount()).withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100 - 33 + 150 - j * 28 + 20 + xOffset, this.height / 2 - 75 + 32 * i + 14, 0);
                }
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
