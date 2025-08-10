package fun.wraq.render.gui.trade.single;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.gui.WraqScreen;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SingleItemChangeScreen extends WraqScreen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/vp_store.png");
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;

    private final List<SingleItemChangeRecipe> recipeList;

    public SingleItemChangeScreen(List<SingleItemChangeRecipe> recipeList, int page) {
        super(Component.translatable("menu.single_item_change"));
        this.recipeList = recipeList;
        this.page = page;
    }

    protected void init() {
        this.createMenu();
    }

    private void createMenu() {
        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) {
                page--;
                mc.setScreen(new SingleItemChangeScreen(recipeList, page));
            }
        }).pos(this.width / 2 - 39 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < (recipeList.size() - 1) / 10) {
                page++;
                mc.setScreen(new SingleItemChangeScreen(recipeList, page));
            }
        }).pos(this.width / 2 + 20 - 4, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen(null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12, 12).build());

        for (int i = 0; i < 5; i++) {
            if (i + page * 10 < recipeList.size()) {
                int finalI = i;
                this.addRenderableWidget(Button.builder(Component.translatable("成交"), (p_280814_) -> {
                    SingleItemChangeRecipe recipe = recipeList.get(finalI + page * 10);
                    ModNetworking.sendToServer(new SingleItemChangeC2SPacket(
                            new ItemStack(recipe.needStack.getItem()), recipe.needStack.getCount(), recipe.goods));
                }).pos(this.width / 2 - 35, this.height / 2 - 83 + 32 * i).size(32, 16).build());
            }
            if (i + page * 10 + 5 < recipeList.size()) {
                int finalI = i;
                this.addRenderableWidget(Button.builder(Component.translatable("成交"), (p_280814_) -> {
                    SingleItemChangeRecipe recipe = recipeList.get(finalI + page * 10 + 5);
                    ModNetworking.sendToServer(new SingleItemChangeC2SPacket(
                            new ItemStack(recipe.needStack.getItem()), recipe.needStack.getCount(), recipe.goods));
                }).pos(this.width / 2 - 35 + 140, this.height / 2 - 83 + 32 * i).size(32, 16).build());
            }
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
        int textureWidth = 300;
        int textureHeight = 200;
        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100,
                0, 0, 300, 200, textureWidth, textureHeight);
        int xOffset = -16;
        for (int i = 0; i < 5; i++) {
            if (page * 10 + i < recipeList.size()) {
                SingleItemChangeRecipe recipe = recipeList.get(i + page * 10);
                ItemStack goods = recipe.goods;
                ItemStack needStack = recipe.needStack;
                renderRecipe(x, y, guiGraphics, goods, xOffset, i, needStack, recipe);
            }
            if (page * 10 + 5 + i < recipeList.size()) {
                SingleItemChangeRecipe recipe = recipeList.get(i + page * 10 + 5);
                ItemStack goods = recipe.goods;
                ItemStack needStack = recipe.needStack;
                renderRecipe(x, y, guiGraphics, goods, xOffset + 144, i, needStack, recipe);
            }
        }
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("当前vb: ")
                        .withStyle(ChatFormatting.WHITE),
                this.width / 2 + 80, this.height / 2 + 86, 0);
        guiGraphics.drawCenteredString(fontRenderer,
                Te.s(BasicAttributeDescription.getDecimal(ClientUtils.VBNUM, 2)),
                this.width / 2 + 128, this.height / 2 + 86, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1))
                        .withStyle(ChatFormatting.WHITE),
                this.width / 2, this.height / 2 - 20 + 105, 0);
        guiGraphics.drawCenteredString(fontRenderer,
                Te.s("共" + ((recipeList.size() - 1) / 10 + 1) + "页 " + recipeList.size() + "件物品"),
                this.width / 2 - 85, this.height / 2 + 80, 0);
        super.render(graphics, x, y, v);
    }

    private void renderRecipe(int x, int y, GuiGraphics guiGraphics, ItemStack goods, int xOffset, int i,
                              ItemStack needStack, SingleItemChangeRecipe recipe) {
        guiGraphics.renderItem(goods, this.width / 2 - 100 - 17 + xOffset,
                this.height / 2 - 83 + 32 * i);
        guiGraphics.drawCenteredString(font, Te.s(goods.getCount()),
                this.width / 2 - 100 - 17 + xOffset + 20,
                this.height / 2 - 83 + 32 * i + 8, 0);
        int count = 0;
        if (getMinecraft().player != null) {
            count = InventoryOperation.itemStackCount(getMinecraft().player, goods.getItem());
            if (count > 0) {
                guiGraphics.drawCenteredString(font, Te.s(count, CustomStyle.styleOfWorld),
                        this.width / 2 - 100 - 17 + xOffset,
                        this.height / 2 - 83 + 32 * i - 4, 0);
            }
        }
        guiGraphics.drawCenteredString(font, goods.getDisplayName(),
                this.width / 2 - 58 + xOffset, this.height / 2 - 86 + 32 * i, 0);
        Component component;
        if (recipe.vbSellOrBuy == null) {
            component = Te.s(needStack.getDisplayName(), " * " + needStack.getCount(), ChatFormatting.AQUA);
        } else {
            component = Te.s(recipe.vbSellOrBuy.price() + "VB ", ChatFormatting.GOLD,
                    (recipe.vbSellOrBuy.isSell() ? Te.s("", ChatFormatting.AQUA)
                            : Te.s("收购", ChatFormatting.GREEN)));
        }
        guiGraphics.drawCenteredString(font, component, this.width / 2 - 58 + xOffset,
                this.height / 2 - 71 + 32 * i, 0);
        if (x > this.width / 2 - 100 - 17 + xOffset && x < this.width / 2 - 100 - 17 + 16 + xOffset
                && y > this.height / 2 - 83 + 32 * i && y < this.height / 2 - 83 + 32 * i + 16) {
            guiGraphics.renderTooltip(font, goods, x, y);
        }
        if (x > this.width / 2 - 35 + xOffset && x < this.width / 2 + xOffset
                && y > this.height / 2 - 83 + 32 * i && y < this.height / 2 - 83 + 32 * i + 16) {
            if (recipe.limitType.equals(SingleItemChangePurchaseLimit.Type.NULL)) {
                guiGraphics.renderTooltip(font, Te.s("不限制购买次数", CustomStyle.styleOfStone), x, y);
            } else {
                guiGraphics.renderTooltip(font, Te.s("限购: ", CustomStyle.styleOfGold,
                        "" + SingleItemChangePurchaseLimit.clientDataMap.getOrDefault(recipe.getDataKey(), 0),
                        CustomStyle.styleOfWorld, " / ", CustomStyle.styleOfStone,
                        "" + recipe.limitTimes,
                        " - ", SingleItemChangePurchaseLimit.TYPE_DESCRIPTION_MAP.get(recipe.limitType)), x, y);
            }
        }
    }
}
