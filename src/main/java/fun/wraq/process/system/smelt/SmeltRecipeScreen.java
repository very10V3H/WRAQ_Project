package fun.wraq.process.system.smelt;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
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

import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SmeltRecipeScreen extends Screen {

    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/smelt_gui.png");
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;

    public SmeltRecipeScreen() {
        super(Component.translatable("menu.reputation_store0"));
    }

    protected void init() {
        this.createMenu();
    }

    private final List<SmeltRecipe> list = Arrays.stream(SmeltRecipe.values()).toList();

    private void createMenu() {

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) page--;
        }).pos(this.width / 2 - 39 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < (list.size() - 1) / 10) page++;
        }).pos(this.width / 2 + 20 - 4, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12, 12).build());

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("炼造"), (p_280814_) -> {
                ModNetworking.sendToServer(new SmeltRequestC2SPacket(finalI + page * 10));
            }).pos(this.width / 2 - 35, this.height / 2 - 83 + 32 * i).size(32, 16).build());
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("炼造"), (p_280814_) -> {
                ModNetworking.sendToServer(new SmeltRequestC2SPacket(finalI + 5 + page * 10));
            }).pos(this.width / 2 - 35 + 140, this.height / 2 - 83 + 32 * i).size(32, 16).build());
        }

        this.addRenderableWidget(Button.builder(Component.translatable("进程"), (p_280814_) -> {
            Minecraft.getInstance().setScreen(new SmeltProgressScreen());
        }).pos(this.width / 2 + 70, this.height / 2 + 77).size(32, 20).build());
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

        int yOffset = 0;
        int xOffset = -16;
        int tick = ClientUtils.clientPlayerTick;
        for (int i = 0; i < 5; i++) {
            if (page * 10 + i < list.size()) {
                displaySingleRecipe(list, graphics, i, tick, xOffset, 0, x, y);
            }
            if (page * 10 + 5 + i < list.size()) {
                displaySingleRecipe(list, graphics, i + 5, tick, xOffset + 140, -160, x, y);
            }
        }

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1)).withStyle(ChatFormatting.WHITE), this.width / 2, this.height / 2 - 20 + 105, 0);

        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);
        super.render(graphics, x, y, v);
    }

    private void displaySingleRecipe(List<fun.wraq.process.system.smelt.SmeltRecipe> list, GuiGraphics guiGraphics, int i, int tick, int xOffset, int yOffset, int x, int y) {
        SmeltRecipe smeltRecipe = list.get(page * 10 + i);
        int currentProductDisplayIndex = tick / 30 % smeltRecipe.productList.size();
        ItemStack currentDisplayProduct = smeltRecipe.productList.get(currentProductDisplayIndex);

        // 物品图标与数量
        guiGraphics.renderItem(currentDisplayProduct, this.width / 2 - 117 + xOffset, this.height / 2 - 83 + 32 * i + yOffset);
        if (currentDisplayProduct.getCount() > 1) {
            guiGraphics.drawCenteredString(font, Component.literal("" + currentDisplayProduct.getCount())
                            .withStyle(ChatFormatting.WHITE),
                    this.width / 2 - 117 + xOffset + 20, this.height / 2 - 75 + 32 * i + yOffset, 0);
        }

        // 配方名
        guiGraphics.drawCenteredString(font, smeltRecipe.name, this.width / 2 - 58 + xOffset, this.height / 2 - 86 + 32 * i + yOffset, 0);

        // 需要材料
        ItemStack firstMaterial = smeltRecipe.needMaterialList.get(0);
        Component needMaterialDescription;
        if (smeltRecipe.needMaterialList.size() > 1) {
            needMaterialDescription = Te.m("").append(firstMaterial.getDisplayName())
                    .append(Te.m("等")).append(Te.m("" + smeltRecipe.needMaterialList.size())).append(Te.m(".."));
        } else {
            needMaterialDescription = Te.m("").append(firstMaterial.getDisplayName())
                    .append(Te.m(" * ")).append(Te.m("" + firstMaterial.getCount()));
        }
        guiGraphics.drawCenteredString(font, needMaterialDescription, this.width / 2 - 58 + xOffset, this.height / 2 - 71 + 32 * i + yOffset, 0);

        // 当指针移动至图表处时显示的ToolTip
        if (x > this.width / 2 - 117 + xOffset && x < this.width / 2 - 117 + 16 + xOffset
                && y > this.height / 2 - 83 + 32 * i + yOffset && y < this.height / 2 - 83 + 32 * i + 16 + yOffset) {
            guiGraphics.renderTooltip(font, currentDisplayProduct, x, y);
        }

        // 鼠标移动至需要材料时显示的ToolTip
        if (x > this.width / 2 - 117 + xOffset + 16 && x < this.width / 2 - 117 + 90 + xOffset
                && y > this.height / 2 - 83 + 32 * i - 4 + yOffset && y < this.height / 2 - 83 + 32 * i + 16 + 4 + yOffset) {
            guiGraphics.renderComponentTooltip(fontRenderer, smeltRecipe.getDescription(), x, y);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
