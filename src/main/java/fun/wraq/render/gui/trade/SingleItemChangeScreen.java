package fun.wraq.render.gui.trade;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
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
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SingleItemChangeScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/vp_store.png");
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;

    private final List<SingleItemChangeRecipe> recipeList;

    public SingleItemChangeScreen(List<SingleItemChangeRecipe> recipeList) {
        super(Component.translatable("menu.single_item_change"));
        this.recipeList = recipeList;
    }

    protected void init() {
        this.createMenu();
    }

    private void createMenu() {
        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) page--;
        }).pos(this.width / 2 - 39 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < (recipeList.size() - 1) / 10) page++;
        }).pos(this.width / 2 + 20 - 4, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12, 12).build());

        for (int i = 0; i < 5; i++) {
            if (i + page * 10 < recipeList.size()) {
                SingleItemChangeRecipe recipe = recipeList.get(i + page * 10);
                this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                    ModNetworking.sendToServer(new SingleItemChangeC2SPacket(recipe.needStack(), recipe.goods()));
                }).pos(this.width / 2 - 35, this.height / 2 - 83 + 32 * i).size(32, 16).build());
            }
        }

        for (int i = 0; i < 5; i++) {
            if (i + page * 10 + 5 < recipeList.size()) {
                SingleItemChangeRecipe recipe = recipeList.get(i + page * 10 + 5);
                this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                    ModNetworking.sendToServer(new SingleItemChangeC2SPacket(recipe.needStack(), recipe.goods()));
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
        int yOffset = 0;
        int xOffset = -16;
        for (int i = 0; i < 5; i++) {
            if (page * 10 + i < recipeList.size()) {
                SingleItemChangeRecipe recipe = recipeList.get(i + page * 10);
                ItemStack goods = recipe.goods();
                ItemStack needStack = recipe.needStack();
                guiGraphics.renderItem(goods, this.width / 2 - 100 - 17 + xOffset, this.height / 2 - 83 + 32 * i);
                guiGraphics.drawCenteredString(font, Component.literal("" + goods.getCount()).withStyle(ChatFormatting.WHITE),
                        this.width / 2 - 100 - 17 + xOffset + 20, this.height / 2 - 83 + 32 * i + 8, 0);
                guiGraphics.drawCenteredString(font, goods.getDisplayName(), this.width / 2 - 74, this.height / 2 - 86 + 32 * i, 0);
                Component component = Te.s(needStack.getDisplayName(), " * " + needStack.getCount(), ChatFormatting.AQUA);
                guiGraphics.drawCenteredString(font, component, this.width / 2 - 74, this.height / 2 - 71 + 32 * i, 0);
                if (x > this.width / 2 - 100 - 17 + xOffset && x < this.width / 2 - 100 - 17 + 16 + xOffset
                        && y > this.height / 2 - 83 + 32 * i && y < this.height / 2 - 83 + 32 * i + 16) {
                    guiGraphics.renderTooltip(font, goods, x, y);
                }

                if (x > this.width / 2 - 35 && x < this.width / 2
                        && y > this.height / 2 - 83 + 32 * i && y < this.height / 2 - 83 + 32 * i + 16) {
                    if (recipe.limitType().equals(SingleItemChangePurchaseLimit.Type.NULL)) {
                        guiGraphics.renderTooltip(font, Te.s("不限制购买次数", CustomStyle.styleOfStone), x, y);
                    } else {
                        guiGraphics.renderTooltip(font, Te.s("限购: ", CustomStyle.styleOfGold,
                                "" + SingleItemChangePurchaseLimit.clientDataMap.getOrDefault(recipe.getDataKey(), 0),
                                CustomStyle.styleOfWorld, " / ", CustomStyle.styleOfStone,
                                "" + recipe.limitTimes(),
                                " - ", SingleItemChangePurchaseLimit.TYPE_DESCRIPTION_MAP.get(recipe.limitType())), x, y);
                    }
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if (page * 10 + 5 + i < recipeList.size()) {
                SingleItemChangeRecipe recipe = recipeList.get(i + page * 10 + 5);
                ItemStack goods = recipe.goods();
                ItemStack needStack = recipe.needStack();
                guiGraphics.renderItem(goods,
                        this.width / 2 - 100 - 13 + 140 + xOffset, this.height / 2 - 83 + 32 * i);
                guiGraphics.drawCenteredString(font, Te.s(String.valueOf(goods.getCount())),
                        this.width / 2 - 100 - 17 + xOffset + 20 + 144, this.height / 2 - 83 + 32 * i + 8, 0);
                guiGraphics.drawCenteredString(font, goods.getDisplayName(),
                        this.width / 2 - 74 + 140, this.height / 2 - 86 + 32 * i, 0);
                Component component = Te.s(needStack.getDisplayName(), " * " + needStack.getCount(), ChatFormatting.AQUA);
                guiGraphics.drawCenteredString(font, component, this.width / 2 - 74 + 144, this.height / 2 - 71 + 32 * i, 0);
                if (x > this.width / 2 - 100 - 13 + 140 + xOffset && x < this.width / 2 - 100 - 13 + 16 + 140 + xOffset
                        && y > this.height / 2 - 83 + 32 * i && y < this.height / 2 - 83 + 32 * i + 16) {
                    guiGraphics.renderTooltip(font, goods, x, y);
                }

                if (x > this.width / 2 - 35 + 140 && x < this.width / 2 + 140
                        && y > this.height / 2 - 83 + 32 * i && y < this.height / 2 - 83 + 32 * i + 16) {
                    if (recipe.limitType().equals(SingleItemChangePurchaseLimit.Type.NULL)) {
                        guiGraphics.renderTooltip(font, Te.s("不限制购买次数", CustomStyle.styleOfStone), x, y);
                    } else {
                        guiGraphics.renderTooltip(font, Te.s("限购: ", CustomStyle.styleOfGold,
                                "" + SingleItemChangePurchaseLimit.clientDataMap.getOrDefault(recipe.getDataKey(), 0),
                                CustomStyle.styleOfWorld, " / ", CustomStyle.styleOfStone,
                                "" + recipe.limitTimes(),
                                " - ", SingleItemChangePurchaseLimit.TYPE_DESCRIPTION_MAP.get(recipe.limitType())), x, y);
                    }
                }
            }
        }

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("当前vb: ").withStyle(ChatFormatting.LIGHT_PURPLE),
                this.width / 2 + 80, this.height / 2 + 86, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + ClientUtils.VBNUM).withStyle(ChatFormatting.GOLD),
                this.width / 2 + 128, this.height / 2 + 86, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1)).withStyle(ChatFormatting.WHITE),
                this.width / 2, this.height / 2 - 20 + 105, 0);
        super.render(graphics, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
