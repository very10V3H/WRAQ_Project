package fun.wraq.render.gui.team;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.TeamDeleteC2SPacket;
import fun.wraq.networking.misc.TeamPackets.TeamNameConfirmC2SPacket;
import fun.wraq.networking.misc.TeamPackets.TeamRemovePlayerC2SPacket;
import fun.wraq.render.gui.team.InstanceScreen;
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
public class TeamManageScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/teamscreen.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private EditBox nameBox;

    public TeamManageScreen(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.teamscreen") : Component.translatable("menu.teamscreen3"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
            this.nameBox = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 92, 200, 20, Component.translatable("teamsearch.search"));
            this.addWidget(this.nameBox);
            if (ClientUtils.clientPlayerTeamMap.containsKey(mc.player.getName().getString()))
                this.nameBox.setValue(ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getTeamName());
            else this.nameBox.setValue(mc.player.getName().getString() + "的队伍");
        }
    }

    private void createMenu() {

        this.addRenderableWidget(Button.builder(Component.translatable("解散队伍"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
            ModNetworking.sendToServer(new TeamDeleteC2SPacket());
        }).pos(this.width / 2 - 140, this.height / 2 + 70).size(48, 20).build());


        this.addRenderableWidget(Button.builder(Component.translatable("副本挑战"), (p_280814_) -> {
            this.minecraft.setScreen(new InstanceScreen(true));
        }).pos(this.width / 2 - 85, this.height / 2 + 70).size(48, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("邀请玩家"), (p_280814_) -> {
            this.minecraft.setScreen(new PlayerSearchScreen(true));
        }).pos(this.width / 2 + 35, this.height / 2 + 70).size(48, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("申请列表"), (p_280814_) -> {
            this.minecraft.setScreen(new PlayerRequestScreen(true));
        }).pos(this.width / 2 + 90, this.height / 2 + 70).size(48, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("确认"), (p_280814_) -> {
            ModNetworking.sendToServer(new TeamNameConfirmC2SPacket(this.nameBox.getValue()));
        }).pos(this.width / 2 + 108, this.height / 2 - 92).size(32, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (this.page < ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberNameList().size() / 4)
                this.page++;
        }).pos(this.width / 2 + 10, this.height / 2 + 70).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (this.page > 0) this.page--;
        }).pos(this.width / 2 - 30, this.height / 2 + 70).size(20, 20).build());

        for (int i = 1; i < 4; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("请出队伍"), (p_280814_) -> {
                if (ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberNameList().size() > page * 4 + finalI)
                    ModNetworking.sendToServer(new TeamRemovePlayerC2SPacket(ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberNameList().get(page * 4 + finalI)));
            }).pos(this.width / 2 + 80, this.height / 2 - 62 + 32 * i).size(64, 20).build());
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

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("队伍名:").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK),
                this.width / 2 - 128, this.height / 2 - 88, 0);

        for (int i = 0; i < 4; i++) {
            if (ClientUtils.clientPlayerTeamMap.containsKey(mc.player.getName().getString())) {
                if (page * 4 + i < ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberNameList().size()
                        && (ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberNameList().get(page * 4 + i) != null)) {
                    if (i == 0 && page == 0)
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("队长: ").withStyle(ChatFormatting.BLACK).
                                        append(ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberDisplayNameList().get(page * 4 + i)),
                                this.width / 2 - 28, this.height / 2 - 64 + i * 32, 0);
                    else {
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("成员: ").withStyle(ChatFormatting.BLACK).
                                        append(ClientUtils.clientPlayerTeamMap.get(mc.player.getName().getString()).getMemberDisplayNameList().get(page * 4 + i)),
                                this.width / 2 - 28, this.height / 2 - 64 + i * 32, 0);
                    }
                }
            }
        }


        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.valueOf(this.page + 1)).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD),
                this.width / 2, this.height / 2 + 74, 0);

        this.nameBox.render(guiGraphics, x, y, v);
        super.render(p_96310_, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
