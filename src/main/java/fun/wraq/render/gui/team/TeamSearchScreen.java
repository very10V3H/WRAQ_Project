package fun.wraq.render.gui.team;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.ClientPlayerTeam;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.PlayerRequestC2SPacket;
import fun.wraq.networking.misc.TeamPackets.TeamCreateC2SPacket;
import fun.wraq.render.gui.team.TeamInviteScreen;
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
public class TeamSearchScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/teamscreen.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private EditBox nameSearchBox;
    private List<ClientPlayerTeam> clientPlayerTeamList;

    public TeamSearchScreen(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.teamscreen") : Component.translatable("menu.teamscreen1"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
            this.nameSearchBox = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 92, 200, 20, Component.translatable("teamsearch.search"));
            this.addWidget(this.nameSearchBox);
            ClientUtils.setClientPlayerTeamList();
        }
    }

    private void createMenu() {
        this.addRenderableWidget(Button.builder(Component.translatable("创建队伍"), (p_280814_) -> {
            ModNetworking.sendToServer(new TeamCreateC2SPacket());
            this.minecraft.setScreen(new TeamManageScreen(true));
        }).pos(this.width / 2 - 120, this.height / 2 + 70).size(64, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("邀请列表"), (p_280814_) -> {
            this.minecraft.setScreen(new TeamInviteScreen(true));
        }).pos(this.width / 2 + 80, this.height / 2 + 70).size(64, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (this.page < ClientUtils.clientPlayerTeamList.size() / 4) this.page++;
        }).pos(this.width / 2 + 30, this.height / 2 + 70).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (this.page > 0) this.page--;
        }).pos(this.width / 2 - 30, this.height / 2 + 70).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("关闭"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 108, this.height / 2 - 92).size(32, 16).build());

        for (int i = 0; i < 4; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("申请加入"), (p_280814_) -> {
                if (ClientUtils.clientPlayerTeamList.size() > this.page * 4 + finalI) {
                    ClientPlayerTeam clientPlayerTeam = ClientUtils.clientPlayerTeamList.get(page * 4 + finalI);

                    if (clientPlayerTeam.getLeaderName() != null) {
                        if (!clientPlayerTeam.getLeaderName().equals(mc.player.getName().getString()))
                            ModNetworking.sendToServer(new PlayerRequestC2SPacket(clientPlayerTeam.getLeaderName()));
                        else mc.player.sendSystemMessage(Component.literal("你已经在该队伍内了。"));
                    }

                }
            }).pos(this.width / 2 + 80, this.height / 2 - 62 + 32 * i).size(64, 20).build());
        }


    }

    public void tick() {
        super.tick();
        ClientUtils.setClientPlayerTeamList();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {

        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("搜索队伍:").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK),
                this.width / 2 - 128, this.height / 2 - 88, 0);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.valueOf(this.page + 1)).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK),
                this.width / 2 + 10, this.height / 2 + 74, 0);

        for (int i = 0; i < 4; i++) {
            if (page * 4 + i < ClientUtils.clientPlayerTeamList.size()) {
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("队伍名: ").withStyle(ChatFormatting.BLACK).
                                append(Component.literal(ClientUtils.clientPlayerTeamList.get(page * 4 + i).getTeamName()).withStyle(ChatFormatting.BLACK)),
                        this.width / 2 - 28, this.height / 2 - 64 + i * 32, 0);

                guiGraphics.drawCenteredString(fontRenderer, Component.literal("队长: ").withStyle(ChatFormatting.BLACK).
                                append(ClientUtils.clientPlayerTeamList.get(page * 4 + i).getLeaderDisplayName()),
                        this.width / 2 - 28, this.height / 2 - 48 + i * 32, 0);

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
