package fun.wraq.render.gui.market;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.files.MarketItemInfo;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SmartPhonePackets.BuyCheckC2SPacket;
import fun.wraq.networking.misc.SmartPhonePackets.MarketDataC2SPacket;
import fun.wraq.networking.misc.SmartPhonePackets.OffShellC2SPacket;
import fun.wraq.networking.misc.SmartPhonePackets.RequestGetC2SPacket;
import fun.wraq.process.system.market.MarketInfo;
import fun.wraq.render.toolTip.CustomStyle;
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
    private int sortByType = -1; // -1 全部 0 - VB 1 - 金豆
    private static List<MarketItemInfo> tempItemInfos = new ArrayList<>();

    public static int tempPage = 0;
    public static int tempSortByPrice = -1;
    public static int tempSortByType = 0;
    public static String tempSearchString = "";

    public MarketScreen(boolean p_96308_, int page, int sortByPrice, int sortByType) {
        super(p_96308_ ? Component.translatable("menu.vmd2") : Component.translatable("menu.vmd3"));
        this.showPauseMenu = p_96308_;
        this.pageNum = page;
        this.sortByPrice = sortByPrice;
        this.sortByType = sortByType;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createPauseMenu();
            this.nameSearchBox = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 124, 200, 20,
                    Component.translatable("teamsearch.search"));
            this.nameSearchBox.setValue(tempSearchString);
            this.addWidget(this.nameSearchBox);
        }
    }

    private void createPauseMenu() {
        if (tempItemInfos.isEmpty()) {
            tempItemInfos.addAll(ClientUtils.marketInfo);
        }

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (pageNum > 0) pageNum--;
        }).pos(this.width / 2 - 39, this.height / 2 - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (pageNum < (tempItemInfos.size() - 1) / 5) pageNum++;
        }).pos(this.width / 2 + 20, this.height / 2 - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("收取"), (p_280814_) -> {
            ModNetworking.sendToServer(new RequestGetC2SPacket());
            mc.setScreen(new MarketScreen(true, pageNum, sortByPrice, sortByType));
            ModNetworking.sendToServer(new MarketDataC2SPacket());
        }).pos(this.width / 2 - 75, this.height / 2 - 20 + 97).size(32, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("刷新"), (p_280814_) -> {
            ModNetworking.sendToServer(new MarketDataC2SPacket());
        }).pos(this.width / 2 - 120, this.height / 2 - 20 + 97).size(32, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("默认"), (p_280814_) -> {
            sortByPrice = -1;
        }).pos(this.width / 2 - 186, this.height / 2 - 106 + 24).size(32, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("升序"), (p_280814_) -> {
            sortByPrice = 0;
        }).pos(this.width / 2 - 186, this.height / 2 - 106 + 24 * 2).size(32, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("降序"), (p_280814_) -> {
            sortByPrice = 1;
        }).pos(this.width / 2 - 186, this.height / 2 - 106 + 24 * 3).size(32, 20).build());

        this.addRenderableWidget(Button.builder(Te.s("全部", CustomStyle.styleOfGold), (p_280814_) -> {
            sortByType = -1;
        }).pos(this.width / 2 - 186, this.height / 2 - 95 + 24 * 4).size(32, 20).build());
        this.addRenderableWidget(Button.builder(Te.s("VB", CustomStyle.styleOfGold), (p_280814_) -> {
            sortByType = 0;
        }).pos(this.width / 2 - 186, this.height / 2 - 95 + 24 * 5).size(32, 20).build());
        this.addRenderableWidget(Button.builder(Te.s("金豆", CustomStyle.styleOfGold), (p_280814_) -> {
            sortByType = 1;
        }).pos(this.width / 2 - 186, this.height / 2 - 95 + 24 * 6).size(32, 20).build());

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                if (tempItemInfos.size() > pageNum * 5 + finalI) {
                    MarketItemInfo marketItemInfo = tempItemInfos.get(pageNum * 5 + finalI);
                    if (marketItemInfo != null) {
                        ModNetworking.sendToServer(new BuyCheckC2SPacket(marketItemInfo.playerName,
                                marketItemInfo.itemStack, marketItemInfo.price, marketItemInfo.type));
                        tempPage = pageNum;
                        tempSortByPrice = sortByPrice;
                        tempSortByType = sortByType;
                        mc.setScreen(new MarketScreen(true, pageNum, sortByPrice, sortByType));
                        ModNetworking.sendToServer(new MarketDataC2SPacket());
                    }
                }
            }).pos(this.width / 2 + 100, this.height / 2 - 84 + 32 * (i)).size(40, 20).build());
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("下架"), (p_280814_) -> {
                if (tempItemInfos.size() > pageNum * 5 + finalI) {
                    MarketItemInfo marketItemInfo = tempItemInfos.get(pageNum * 5 + finalI);
                    if (marketItemInfo != null) {
                        ModNetworking.sendToServer(new OffShellC2SPacket(marketItemInfo.playerName,
                                marketItemInfo.itemStack, marketItemInfo.price, marketItemInfo.type));
                        tempPage = pageNum;
                        tempSortByPrice = sortByPrice;
                        tempSortByType = sortByType;
                        mc.setScreen(new MarketScreen(true, pageNum, sortByPrice, sortByType));
                        ModNetworking.sendToServer(new MarketDataC2SPacket());
                    }
                }
            }).pos(this.width / 2 + 50, this.height / 2 - 84 + 32 * (i)).size(40, 20).build());
        }
    }

    public void tick() {
        super.tick();
        String targetValue = this.nameSearchBox.getValue();
        tempSearchString = targetValue;

        tempItemInfos.clear();
        tempItemInfos.addAll(ClientUtils.marketInfo);

        if (sortByType != -1) {
            tempItemInfos.removeIf(info -> info.type != sortByType);
        }
        if (sortByPrice != -1) {
            if (sortByPrice == 0) {
                tempItemInfos.sort(((o1, o2) -> {
                    return (int) (o1.price - o2.price);
                }));
            } else {
                tempItemInfos.sort(((o1, o2) -> {
                    return (int) (o2.price - o1.price);
                }));
            }
        }

        if (!targetValue.isEmpty()) {
            List<MarketItemInfo> matchedListPriority0 = new ArrayList<>();
            List<MarketItemInfo> matchedListPriority1 = new ArrayList<>();
            List<MarketItemInfo> otherList = new ArrayList<>();
            tempItemInfos.forEach(marketItemInfo -> {
                ItemStack itemStack = marketItemInfo.itemStack;
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
            tempItemInfos.clear();
            tempItemInfos = list;
        }
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100,
                0, 0, 300, 200, 300, 200);
        this.renderables.forEach(renderable -> {
            if (renderable instanceof Button button) {
                String content = button.getMessage().getString();
                if (content.contains("下架") || content.contains("购买")) {
                    for (int i = 0; i < 5; i++) {
                        if (button.getY() == this.height / 2 - 84 + 32 * i) {
                            if (pageNum * 5 + i < tempItemInfos.size()) {
                                if (content.contains("购买")) {
                                    renderable.render(guiGraphics, x, y, v);
                                } else {
                                    MarketItemInfo marketItemInfo = tempItemInfos.get(pageNum * 5 + i);
                                    if (marketItemInfo != null
                                            && (marketItemInfo.playerName.equals(mc.player.getName().getString())
                                            || mc.player.isCreative())) {
                                        renderable.render(guiGraphics, x, y, v);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    renderable.render(guiGraphics, x, y, v);
                }
            } else renderable.render(guiGraphics, x, y, v);
        });

        for (int i = 0; i < 5; i++) {
            if (pageNum * 5 + i >= tempItemInfos.size()) break;
            MarketItemInfo info = tempItemInfos.get(pageNum * 5 + i);
            if (info == null) break;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            ItemStack itemStack = info.itemStack;
            if (itemStack != null) {
                guiGraphics.renderItem(itemStack, this.width / 2 - 150 + 33, this.height / 2 - 100 + 17 + i * 32);
                if (x > this.width / 2 - 150 + 33 && x < this.width / 2 - 150 + 33 + 16
                        && y > this.height / 2 - 100 + 17 + i * 32 && y < this.height / 2 - 100 + 17 + i * 32 + 16) {
                    guiGraphics.renderTooltip(font, itemStack, x, y);
                }
                guiGraphics.drawCenteredString(fontRenderer, Te.s(itemStack.getDisplayName()),
                        this.width / 2 - 150 + 100, this.height / 2 - 100 + 17 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Te.s("价格:", info.price,
                                MarketInfo.getTypeDescription(info.type)),
                        this.width / 2 - 150 + 100, this.height / 2 - 100 + 27 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Te.s(info.getItemStackCount()),
                        this.width / 2 - 150 + 55, this.height / 2 - 100 + 27 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Te.s("卖家:", info.playerName),
                        this.width / 2 + 18, this.height / 2 - 100 + 22 + i * 32, 0);
            }
        }
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("当前VB余额:").withStyle(ChatFormatting.WHITE),
                this.width / 2 + 75, this.height / 2 + 86, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.2f", ClientUtils.VBNUM)).withStyle(ChatFormatting.WHITE),
                this.width / 2 + 125, this.height / 2 + 86, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (pageNum + 1)).withStyle(ChatFormatting.WHITE),
                this.width / 2, this.height / 2 - 20 + 105, 0);
        guiGraphics.drawCenteredString(fontRenderer, Te.s("搜索物品:", CustomStyle.styleOfWorld),
                this.width / 2 - 122, this.height / 2 - 118, 0);
        guiGraphics.drawCenteredString(fontRenderer, Te.s("按价格:", CustomStyle.styleOfGold),
                this.width / 2 - 170, this.height / 2 - 94, 0);

        guiGraphics.drawCenteredString(fontRenderer, Te.s("按类型:", CustomStyle.styleOfGold),
                this.width / 2 - 170, this.height / 2 - 10, 0);

        guiGraphics.drawCenteredString(fontRenderer,
                Te.s("使用/vmd sell [价格] [类型:0 = VB, 1 = 金豆] 来出售手上的物品", CustomStyle.styleOfWorld),
                this.width / 2, this.height / 2 + 105, 0);

        this.nameSearchBox.render(guiGraphics, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
