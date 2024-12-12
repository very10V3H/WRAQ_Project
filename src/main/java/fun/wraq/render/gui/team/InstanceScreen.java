package fun.wraq.render.gui.team;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.InstanceChooseC2SPacket;
import fun.wraq.process.system.teamInstance.NewTeamInstance;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHandler;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InstanceScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/team_screen.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private EditBox nameSearchBox;

    public InstanceScreen(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.teamscreen") :
                Component.translatable("menu.teamscreen6"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
            this.nameSearchBox = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 92, 200, 20,
                    Component.translatable("teamsearch.search"));
            this.addWidget(this.nameSearchBox);
        }
    }

    private void createMenu() {
        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (this.page < (NewTeamInstanceHandler.getInstances().size() - 1) / 4) this.page++;
        }).pos(this.width / 2 + 30, this.height / 2 + 70).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (this.page > 0) this.page--;
        }).pos(this.width / 2 - 30, this.height / 2 + 70).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("返回"), (p_280814_) -> {
            this.minecraft.setScreen(new TeamManageScreen(true));
        }).pos(this.width / 2 + 108, this.height / 2 - 92).size(32, 16).build());

        for (int i = 0; i < 4; i++) {
            int index = page * 4 + i;
            if (index < NewTeamInstanceHandler.getInstances().size()) {
                this.addRenderableWidget(Button.builder(Component.translatable("挑战").withStyle(ChatFormatting.RED), (p_280814_) -> {
                    ModNetworking.sendToServer(new InstanceChooseC2SPacket(index));
                }).pos(this.width / 2 + 108, this.height / 2 - 62 + 32 * i).size(32, 20).build());
            }
        }
    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int textureWidth = 300;
        int textureHeight = 200;
        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100,
                0, 0, 300, 200, textureWidth, textureHeight);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("搜索副本:")
                        .withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfRed),
                this.width / 2 - 128, this.height / 2 - 88, 0);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.valueOf(this.page + 1))
                        .withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfWorld),
                this.width / 2 + 10, this.height / 2 + 74, 0);

        for (int i = 0; i < 4; i++) {
            if (page * 4 + i < NewTeamInstanceHandler.getInstances().size()) {
                NewTeamInstance instance = NewTeamInstanceHandler.getInstances().get(page * 4 + i);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("副本: ")
                                .withStyle(CustomStyle.styleOfEnd).
                                append(instance.description),
                        this.width / 2 - 100, this.height / 2 - 64 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("人数需求: ")
                                .withStyle(ChatFormatting.AQUA).
                                append(String.valueOf(instance.minPlayerNum)),
                        this.width / 2 - 20, this.height / 2 - 64 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("地点: ")
                                .withStyle(CustomStyle.styleOfFlexible).
                                append(instance.regionDescription),
                        this.width / 2 - 100, this.height / 2 - 48 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("等级需求: ")
                                .withStyle(ChatFormatting.LIGHT_PURPLE).
                                append(Component.literal(String.valueOf(instance.levelRequire)).
                                        withStyle(Utils.levelStyleList.get(instance.levelRequire / 25))),
                        this.width / 2 - 20, this.height / 2 - 48 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Te.s("消耗理智: " + instance.reasonCost,
                                CustomStyle.styleOfFlexible),
                        this.width / 2 + 60, this.height / 2 - 64 + i * 32, 0);
            }
        }

        this.nameSearchBox.render(guiGraphics, x, y, v);
        super.render(p_96310_, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
