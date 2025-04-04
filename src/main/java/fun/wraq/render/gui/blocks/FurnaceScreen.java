package fun.wraq.render.gui.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.Limit.ScreenCloseC2SPacket;
import fun.wraq.process.system.smelt.SmeltDataRequestC2SPacket;
import fun.wraq.process.system.smelt.SmeltRecipeScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FurnaceScreen extends AbstractContainerScreen<fun.wraq.render.gui.blocks.FurnaceMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Utils.MOD_ID, "textures/gui/furnace.png");

    private final fun.wraq.render.gui.blocks.FurnaceMenu menu;

    public FurnaceScreen(FurnaceMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.menu = menu;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(Button.builder(Component.translatable("托管炼造"), (p_280814_) -> {
            Minecraft.getInstance().setScreen(new SmeltRecipeScreen());
            ModNetworking.sendToServer(new SmeltDataRequestC2SPacket());
        }).pos(this.width / 2 + 90, this.height / 2 - 82).size(48, 16).build());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float PartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 77, y + 25, 176, 0, menu.getScaledProgress(), 8);
        }
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {

        Minecraft mc = Minecraft.getInstance();

        int x = width / 2;
        int y = height / 2;

        guiGraphics.drawString(mc.font, Component.literal("使用托管炼造以炼造新配方").withStyle(ChatFormatting.AQUA),
                x - 86, y - 92, 0);

        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void onClose() {
        super.onClose();
        ModNetworking.sendToServer(new ScreenCloseC2SPacket());
    }
}
