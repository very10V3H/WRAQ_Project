package fun.wraq.process.system.element.piece;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementItems;
import fun.wraq.process.system.element.RainbowPowder;
import fun.wraq.process.system.element.networking.ElementPieceC2SPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ElementPieceGui extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/element_piece_gui.png");

    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;

    public ElementPieceGui() {
        super(Component.translatable("menu.element_piece_gui"));
    }

    protected void init() {
        this.createMenu();
    }

    private void createMenu() {
        int X = this.width / 2;
        int Y = this.height / 2;
        if (this.minecraft == null) return;
    }

    public void render(@NotNull GuiGraphics graphics, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int X = this.width / 2;
        int Y = this.height / 2;
        guiGraphics.blit(GUI_TEXTURE, X - 150, Y - 100, 0, 0,
                300, 200, 300, 200);
        guiGraphics.drawCenteredString(fontRenderer, Te.s("「", CustomStyle.styleOfGold,
                        RainbowPowder.rainbowName("量子元素终端"), "」", CustomStyle.styleOfGold),
                X + 5, Y - 96, 0);
        for (int i = 0; i < 9; i++) {
            List<Item> items = ElementPieceRecipe.getItems(i);
            for (int j = 0; j < items.size(); j++) {
                Item item = items.get(j);
                guiGraphics.renderItem(new ItemStack(item),
                        this.width / 2 - 128 + i * 30, this.height / 2 - 73 + 32 * j);
                if (x > this.width / 2 - 128 + i * 30 && x < this.width / 2 - 128 + 16 + i * 30
                        && y > this.height / 2 - 73 + 32 * j && y < this.height / 2 - 73 + 32 * j + 16) {
                    List<Component> components = new ArrayList<>();
                    components.add(Te.s(item));
                    components.addAll(getTooltip(i, j));
                    guiGraphics.renderComponentTooltip(font, components, x, y);
                }
                guiGraphics.drawCenteredString(fontRenderer,
                        Te.s(Compute.getSimplifiedNumberDescription(ElementPieceData.getCountClient(item))),
                        this.width / 2 - 120 + i * 30, this.height / 2 - 55 + 32 * j, 0);
            }
        }
    }

    @Override
    public boolean mouseClicked(double x, double y, int index) {
        int X = this.width / 2;
        int Y = this.height / 2;
        for (int i = 0; i < 9; i++) {
            List<Item> items = ElementPieceRecipe.getItems(i);
            for (int j = 0; j < items.size(); j++) {
                if (x > X - 128 + i * 30 && x < X - 128 + 16 + i * 30
                        && y > Y - 73 + 32 * j && y < Y - 73 + 32 * j + 16) {
                    if (index == 0) {
                        ModNetworking.sendToServer(new ElementPieceC2SPacket("get", i, j));
                    } else {
                        ModNetworking.sendToServer(new ElementPieceC2SPacket("compose", i, j));
                    }
                }
            }
        }
        return super.mouseClicked(x, y, index);
    }

    public List<Component> getTooltip(int index0, int index1) {
        List<Component> components = new ArrayList<>();
        Item rainbowPowder = ModItems.RainbowPowder.get();
        Item rainbowCrystal = ModItems.RainbowCrystal.get();
        Item weakPiece0 = ElementItems.weakPiece0.get();
        if (index0 < 7) {
            String elementType = Element.elementList.get(index0);
            Item piece0Item = Element.getPiece0Items().get(index0);
            Item piece1Item = Element.getPiece1ItemMap().get(elementType);
            Item piece2Item = Element.getPiece2ItemMap().get(elementType);
            if (index1 == 0) {
                components.add(Te.s("左键:", ChatFormatting.AQUA, "取出1个", piece0Item));
                components.add(Te.s("右键:", ChatFormatting.GREEN, "合成64个", piece0Item));
                components.add(Te.s(" ┣合成需求:", CustomStyle.styleOfKaze));
                components.add(Te.s(" ┗", weakPiece0, " * 64", " + ", piece0Item, " * 1"));
            } else if (index1 == 1) {
                components.add(Te.s("左键:", ChatFormatting.AQUA, "取出1个", piece1Item));
                components.add(Te.s("右键:", ChatFormatting.GREEN, "合成1个", piece1Item));
                components.add(Te.s(" ┣合成需求:", CustomStyle.styleOfKaze));
                components.add(Te.s(" ┗", piece0Item, " * 64", " + ", rainbowPowder, " * 1"));
            } else if (index1 == 2) {
                components.add(Te.s("左键:", ChatFormatting.AQUA, "取出1个", piece2Item));
                components.add(Te.s("右键:", ChatFormatting.GREEN, "合成1个", piece2Item));
                components.add(Te.s(" ┣合成需求:", CustomStyle.styleOfKaze));
                components.add(Te.s(" ┗", piece1Item, " * 64", " + ", rainbowCrystal, " * 1"));
            } else if (index1 == 3) {
                components.add(Te.s("左键:", ChatFormatting.AQUA, "取出64个", weakPiece0));
                components.add(Te.s("右键:", ChatFormatting.GREEN, "合成64个", weakPiece0));
                components.add(Te.s(" ┣合成需求:", CustomStyle.styleOfKaze));
                components.add(Te.s(" ┗", piece0Item, " * 64", " + ", rainbowPowder, " * 1"));
            }
        } else if (index0 == 7) {
            if (index1 == 0) {
                components.add(Te.s("左键:", ChatFormatting.AQUA, "取出1个", rainbowPowder));
                components.add(Te.s("右键:", ChatFormatting.GREEN, "合成1个", rainbowPowder));
                components.add(Te.s(" ┣合成需求:", CustomStyle.styleOfKaze));
                components.add(Te.s(" ┗[所有微型元素碎片]", CustomStyle.styleOfSky, " * 7"));
            } else {
                components.add(Te.s("左键:", ChatFormatting.AQUA, "取出1个", rainbowCrystal));
                components.add(Te.s("右键:", ChatFormatting.GREEN, "合成1个", rainbowCrystal));
                components.add(Te.s(" ┣合成需求:", CustomStyle.styleOfKaze));
                components.add(Te.s(" ┗", rainbowPowder, " * 49", " + ", ModItems.COMPLETE_GEM, " * 7"));
            }
        } else {
            components.add(Te.s("左键:", ChatFormatting.AQUA, "取出1个", weakPiece0));
        }
        return components;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
