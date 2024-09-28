package fun.wraq.render.gui.mission;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.reputation.ReputationBuyRequestC2SPacket;
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

@OnlyIn(Dist.CLIENT)
public class ReputationStore extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/reputation_store.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;

    public ReputationStore(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.reputation_store") : Component.translatable("menu.reputation_store0"));
        this.showPauseMenu = p_96308_;
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
            if (page < 100) page++;
        }).pos(this.width / 2 + 20 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12, 12).build());

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                ModNetworking.sendToServer(new ReputationBuyRequestC2SPacket(page * 10 + finalI));
            }).pos(this.width / 2 - 35, this.height / 2 - 75 + 32 * i).size(32, 16).build());

        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                ModNetworking.sendToServer(new ReputationBuyRequestC2SPacket(page * 10 + 5 + finalI));
            }).pos(this.width / 2 - 35 + 140, this.height / 2 - 75 + 32 * i).size(32, 16).build());

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

        if (Utils.ReputationStoreItemList.isEmpty()) Utils.setReputationStoreItemList();
        if (Utils.ReputationStorePrice.isEmpty()) Utils.setReputationStorePrice();
        for (int i = 0; i < 5; i++) {
            if (page * 10 + i < Utils.ReputationStoreItemList.size()) {
                ItemStack itemStack = Utils.ReputationStoreItemList.get(page * 10 + i).getDefaultInstance();

                guiGraphics.renderItem(itemStack,
                        this.width / 2 - 100 - 33, this.height / 2 - 73 + 32 * i);

                guiGraphics.drawCenteredString(font, itemStack.getDisplayName(),
                        this.width / 2 - 80, this.height / 2 - 80 + 32 * i, 0);

                guiGraphics.drawCenteredString(font, Component.literal(Utils.ReputationStorePrice.get(
                        itemStack.getItem()) + " 声望点数").withStyle(ChatFormatting.LIGHT_PURPLE), this.width / 2 - 80, this.height / 2 - 65 + 32 * i, 0);

                if (x > this.width / 2 - 100 - 33 && x < this.width / 2 - 100 - 33 + 16
                        && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                    guiGraphics.renderTooltip(font, itemStack, x, y);
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if (page * 10 + 5 + i < Utils.ReputationStoreItemList.size()) {
                ItemStack itemStack = Utils.ReputationStoreItemList.get(page * 10 + 5 + i).getDefaultInstance();

                guiGraphics.renderItem(itemStack,
                        this.width / 2 - 100 - 33 + 140, this.height / 2 - 73 + 32 * i);

                guiGraphics.drawCenteredString(font, itemStack.getDisplayName(),
                        this.width / 2 - 80 + 140, this.height / 2 - 80 + 32 * i, 0);

                guiGraphics.drawCenteredString(font, Component.literal(Utils.ReputationStorePrice.get(
                        itemStack.getItem()) + " 声望点数").withStyle(ChatFormatting.LIGHT_PURPLE), this.width / 2 - 80 + 140, this.height / 2 - 65 + 32 * i, 0);

                if (x > this.width / 2 - 100 - 33 + 140 && x < this.width / 2 - 100 - 33 + 16 + 140
                        && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                    guiGraphics.renderTooltip(font, itemStack, x, y);
                }
            }
        }
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("当前声望: ").withStyle(ChatFormatting.LIGHT_PURPLE), this.width / 2 + 80, this.height / 2 + 83, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + ClientUtils.ReputationNum).withStyle(ChatFormatting.YELLOW), this.width / 2 + 128, this.height / 2 + 83, 0);


        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1)).withStyle(ChatFormatting.YELLOW), this.width / 2 + 5, this.height / 2 - 22 + 105, 0);

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
