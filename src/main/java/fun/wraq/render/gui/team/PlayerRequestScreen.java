package fun.wraq.render.gui.team;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.TeamConfirmC2SPacket;
import fun.wraq.render.gui.team.TeamManageScreen;
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

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PlayerRequestScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/teamscreen.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private EditBox nameSearchBox;
    private List<String> playerList;

    public PlayerRequestScreen(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.teamscreen") : Component.translatable("menu.teamscreen5"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
            this.nameSearchBox = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 92, 200, 20, Component.translatable("teamsearch.search"));
            this.addWidget(this.nameSearchBox);
            playerList = ClientUtils.TeamPlayerRequestList.keySet().stream().toList();
        }
    }

    private void createMenu() {

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (this.page < playerList.size() / 4) this.page++;
        }).pos(this.width / 2 + 30, this.height / 2 + 70).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (this.page > 0) this.page--;
        }).pos(this.width / 2 - 30, this.height / 2 + 70).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("返回"), (p_280814_) -> {
            this.minecraft.setScreen(new TeamManageScreen(true));
        }).pos(this.width / 2 + 108, this.height / 2 - 92).size(32, 16).build());

        for (int i = 0; i < 4; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("同意"), (p_280814_) -> {
                if (playerList.size() > page * 4 + finalI) {
                    ModNetworking.sendToServer(new TeamConfirmC2SPacket(playerList.get(page * 4 + finalI)));
                }
            }).pos(this.width / 2 + 108, this.height / 2 - 62 + 32 * i).size(32, 20).build());
        }

    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {

        List<String> playerList = ClientUtils.TeamPlayerRequestList.keySet().stream().toList();

        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("搜索玩家:").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK),
                this.width / 2 - 128, this.height / 2 - 88, 0);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.valueOf(this.page + 1)).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK),
                this.width / 2 + 10, this.height / 2 + 74, 0);

        for (int i = 0; i < 4; i++) {
            if (page * 4 + i < playerList.size()) {
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("申请人: ").withStyle(ChatFormatting.BLACK).
                                append(ClientUtils.TeamPlayerRequestList.get(playerList.get(page * 4 + i))),
                        this.width / 2 - 28, this.height / 2 - 64 + i * 32, 0);
            }
        }

        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);

        this.nameSearchBox.render(guiGraphics, x, y, v);
        super.render(p_96310_, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
