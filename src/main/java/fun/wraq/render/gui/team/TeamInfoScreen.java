package fun.wraq.render.gui.team;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.PlayerLeaveC2SPacket;
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

@OnlyIn(Dist.CLIENT)
public class TeamInfoScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/team_screen.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;

    public TeamInfoScreen(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.teamscreen") :
                Component.translatable("menu.teamscreen1"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
        }
    }

    private void createMenu() {

        this.addRenderableWidget(Button.builder(Component.translatable("退出队伍"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
            ModNetworking.sendToServer(new PlayerLeaveC2SPacket());
        }).pos(this.width / 2 - 110, this.height / 2 + 70).size(64, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("关闭"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 108, this.height / 2 - 92).size(32, 16).build());

        for (int i = 0; i < 4; i++) {
            this.addRenderableWidget(Button.builder(Component.translatable("查看信息"), (p_280814_) -> {
            }).pos(this.width / 2 + 80, this.height / 2 - 62 + 32 * i).size(64, 20).build());
        }

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (this.page < ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberNameList().size() / 4)
                this.page++;
        }).pos(this.width / 2 + 30, this.height / 2 + 70).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (this.page > 0) this.page--;
        }).pos(this.width / 2 - 30, this.height / 2 + 70).size(20, 20).build());


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
        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.valueOf(this.page + 1)).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK),
                this.width / 2 + 10, this.height / 2 + 74, 0);

        for (int i = 0; i < 4; i++) {
            if (ClientUtils.clientPlayerTeamMap.containsKey(mc.player.getName().getString())) {
                if (page * 4 + i < ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberNameList().size() && (ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberNameList().get(page * 4 + i) != null)) {
                    if (i == 0 && page == 0)
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("队长: ").
                                        withStyle(CustomStyle.styleOfWorld).
                                        append(ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberDisplayNameList().get(page * 4 + i)),
                                this.width / 2 - 28, this.height / 2 - 64 + i * 32, 0);
                    else {
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("成员: ")
                                        .withStyle(CustomStyle.styleOfSky).
                                        append(ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberDisplayNameList().get(page * 4 + i)),
                                this.width / 2 - 28, this.height / 2 - 64 + i * 32, 0);
                    }
                }
            }
        }
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.valueOf(this.page + 1))
                        .withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfWorld),
                this.width / 2 + 10, this.height / 2 + 74, 0);
        super.render(p_96310_, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
