package fun.wraq.process.system.tower;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.registry.ModItems;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TowerScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/tower.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page;

    public TowerScreen(int page) {
        super(Component.translatable("menu.teamscreen"));
        this.showPauseMenu = true;
        this.page = page;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
        }
    }

    private void createMenu() {
        int X = this.width / 2;
        int Y = this.height / 2;
        if (this.minecraft == null) return;

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) this.minecraft.setScreen(new TowerScreen(page - 1));
        }).pos(this.width / 2 - 39 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < Math.ceil((double) fun.wraq.process.system.tower.Tower.levelRequire.length / 5) - 1)
                this.minecraft.setScreen(new TowerScreen(page + 1));
        }).pos(this.width / 2 + 20 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X + 136, Y - 98).size(12, 12).build());

        for (int i = 0; i < 5; i++) {
            if (page * 5 + i < fun.wraq.process.system.tower.Tower.levelRequire.length) {
                int finalI = i;
                this.addRenderableWidget(Button.builder(Component.translatable("挑战").withStyle(ChatFormatting.AQUA), (p_280814_) -> {
                    ModNetworking.sendToServer(new TowerChallengeC2SPacket(page * 5 + finalI));
                }).pos(X + 108, Y - 76 + 32 * i).size(32, 20).build());
            }
        }

    }

    public void tick() {
        super.tick();
    }

    /*public static final String[] numToRoma = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};*/
    /*public static final int[] levelRequire = {100, 120, 140, 160, 180, 200};*/

    public void render(@NotNull GuiGraphics graphics, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int textureWidth = 300;
        int textureHeight = 200;
        int X = this.width / 2;
        int Y = this.height / 2;
        guiGraphics.blit(GUI_TEXTURE, X - 150, Y - 100,
                0, 0, 300, 200, textureWidth, textureHeight);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("「本源回廊挑战（测试阶段）」").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD), X + 5, Y - 96, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1)).withStyle(ChatFormatting.WHITE), X + 5, Y - 22 + 105, 0);

        for (int i = 0; i < 5; i++) {
            int index = page * 5 + i;
            if (index < fun.wraq.process.system.tower.Tower.levelRequire.length) {
                guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                append(Component.literal("本源回廊 - " + fun.wraq.process.system.tower.Tower.numToRoma[index]).withStyle(CustomStyle.styleOfWorld)).
                                append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                        X - 132, Y - 80 + i * 32, 0);

                guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                append(Component.literal("")).
                                append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                        X - 56, Y - 80 + i * 32, 0);

                guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                append(Component.literal("本期进度")).
                                append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                        X - 132, Y - 64 + i * 32, 0);

                for (int j = 0; j < 4; j++) {
                    if (Tower.clientTowerStatus != null && !Tower.clientTowerStatus.isEmpty()) {
                        int stage = Integer.parseInt(Tower.clientTowerStatus.substring(index, index + 1));
                        if (j < stage)
                            guiGraphics.renderItem(ModItems.WORLD_SOUL_5.get().getDefaultInstance(), X - 80 + j * 20, Y - 68 + i * 32);
                        else
                            guiGraphics.renderItem(ModItems.WORLDSOUL_HOLLOW.get().getDefaultInstance(), X - 80 + j * 20, Y - 68 + i * 32);
                    }
                    guiGraphics.renderItem(ModItems.WORLDSOUL_HOLLOW.get().getDefaultInstance(), X - 80 + j * 20, Y - 68 + i * 32);
                }

                guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                append(Component.literal("等级需求：Lv." + Tower.levelRequire[index])).
                                append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                        X, Y - 80 + 32 * i, 0);

/*                if (x > X + 68 && x < X + 100 && y > Y - 76 + 32 * i && y < Y - 56 + 32 * i) {
                    guiGraphics.renderTooltip(fontRenderer, Component.literal("1"), x, y);
                }


                if (x > X - 132 && x < X - 132 + 64 && y > Y - 80 + i * 32 && y < Y - 80 + i * 32 + 8) {
                    guiGraphics.renderTooltip(fontRenderer, Component.literal("1"), x, y);
                }*/
            }
        }
        super.render(graphics, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
