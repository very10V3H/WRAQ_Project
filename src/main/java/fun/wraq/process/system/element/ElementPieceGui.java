package fun.wraq.process.system.element;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ElementPieceGui extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/element_roulette.png");

    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;

    public ElementPieceGui() {
        super(Component.translatable("menu.teamscreen"));
    }

    protected void init() {
        this.createMenu();
    }

    private void createMenu() {
        int X = this.width / 2;
        int Y = this.height / 2;
        if (this.minecraft == null) return;

        this.addRenderableWidget(Button.builder(Component.translatable("生机"), (p_280814_) -> {

        }).pos(X - 14, Y - 89).size(28, 28).build());
    }

    public void render(@NotNull GuiGraphics graphics, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int X = this.width / 2;
        int Y = this.height / 2;
        guiGraphics.blit(GUI_TEXTURE, X - 150, Y - 150, 0, 0,
                300, 200, 300, 200);
        for (int i = 0; i < 7 ; i++) {

        }
    }

    public List<Item> getItems(int index) {
        if (index < 7) {
            String element = Element.elementList.get(index);
            return List.of(
                    Element.getPiece0ItemMap().get(element),
                    Element.getPiece1ItemMap().get(element),
                    Element.getPiece2ItemMap().get(element),
                    ElementItems.weakPiece0.get()
            );
        } else {
            if (index == 7) {
                return List.of(
                        ModItems.RainbowPowder.get(),
                        ModItems.RainbowCrystal.get()
                );
            } else {
                return List.of(
                        ElementItems.weakPiece0.get()
                );
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
