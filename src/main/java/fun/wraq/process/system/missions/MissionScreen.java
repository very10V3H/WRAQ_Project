package fun.wraq.process.system.missions;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.missions.netWorking.MissionAcceptC2SPacket;
import fun.wraq.process.system.missions.netWorking.MissionCancelC2SPacket;
import fun.wraq.process.system.missions.netWorking.MissionSubmitC2SPacket;
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
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MissionScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/tower.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private final int page;
    private final int type; // 0 - 全部任务 1 - 可接受 2 - 进行中 3 - 已完成
    private List<fun.wraq.process.system.missions.Mission> missionList;

    public MissionScreen(int type, int page) {
        super(Component.translatable("menu.teamscreen"));
        this.type = type;
        this.showPauseMenu = true;
        switch (type) {
            case 0 -> missionList = fun.wraq.process.system.missions.Mission.missionList;
            case 1 -> missionList = fun.wraq.process.system.missions.MissionClient.getMissionList(fun.wraq.process.system.missions.Mission.Status.ableToAccepted);
            case 2 -> missionList = fun.wraq.process.system.missions.MissionClient.getMissionList(fun.wraq.process.system.missions.Mission.Status.inProgress);
            case 3 -> missionList = fun.wraq.process.system.missions.MissionClient.getMissionList(fun.wraq.process.system.missions.Mission.Status.done);
        }
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
        this.addRenderableWidget(Button.builder(Component.translatable("全部任务"), (p_280814_) -> {
            this.minecraft.setScreen(new MissionScreen(0, 0));
        }).pos(X + 150, Y - 50).size(48, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("可接受"), (p_280814_) -> {
            this.minecraft.setScreen(new MissionScreen(1, 0));
        }).pos(X + 150, Y - 98).size(48, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("进行中"), (p_280814_) -> {
            this.minecraft.setScreen(new MissionScreen(2, 0));
        }).pos(X + 150, Y - 74).size(48, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("已完成"), (p_280814_) -> {
            this.minecraft.setScreen(new MissionScreen(3, 0));
        }).pos(X + 150, Y - 26).size(48, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) this.minecraft.setScreen(new MissionScreen(type, page - 1));
        }).pos(this.width / 2 - 39 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < Math.ceil((double) missionList.size() / 5) - 1)
                this.minecraft.setScreen(new MissionScreen(type, page + 1));
        }).pos(this.width / 2 + 20 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X + 136, Y - 98).size(12, 12).build());

        for (int i = 0; i < 5; i++) {
            if (page * 5 + i < missionList.size()) {
                fun.wraq.process.system.missions.Mission mission = missionList.get(page * 5 + i);
                if (fun.wraq.process.system.missions.MissionClient.getMissionStatus(mission.serialNum) == fun.wraq.process.system.missions.Mission.Status.ableToAccepted) {
                    this.addRenderableWidget(Button.builder(Component.translatable("接受").withStyle(ChatFormatting.GREEN), (p_280814_) -> {
                        ModNetworking.sendToServer(new MissionAcceptC2SPacket(mission.serialNum));
                    }).pos(X + 108, Y - 76 + 32 * i).size(32, 20).build());
                } else if (fun.wraq.process.system.missions.MissionClient.getMissionStatus(mission.serialNum) == fun.wraq.process.system.missions.Mission.Status.inProgress) {
                    this.addRenderableWidget(Button.builder(Component.translatable("放弃").withStyle(ChatFormatting.RED), (p_280814_) -> {
                        ModNetworking.sendToServer(new MissionCancelC2SPacket(mission.serialNum));
                    }).pos(X + 108, Y - 76 + 32 * i).size(32, 20).build());

                    this.addRenderableWidget(Button.builder(Component.translatable("提交").withStyle(ChatFormatting.YELLOW), (p_280814_) -> {
                        ModNetworking.sendToServer(new MissionSubmitC2SPacket(mission.serialNum));
                    }).pos(X + 68, Y - 76 + 32 * i).size(32, 20).build());
                }
            }
        }

    }

    public void tick() {
        super.tick();
    }

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
        guiGraphics.blit(GUI_TEXTURE, X - 150, Y - 100, 0, 0,
                300, 200, textureWidth, textureHeight);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("「任务列表」").withStyle(ChatFormatting.LIGHT_PURPLE), X + 5, Y - 96, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1)).withStyle(ChatFormatting.WHITE), X + 5, Y - 22 + 105, 0);
        guiGraphics.drawString(fontRenderer, Component.literal("光标移动至任务名可查看需求").withStyle(ChatFormatting.WHITE), X - 144, Y - 108, 0);
        guiGraphics.drawString(fontRenderer, Component.literal("光标移动至提交按钮查看奖励").withStyle(ChatFormatting.WHITE), X + 28, Y - 108, 0);

        for (int i = 0; i < 5; i++) {
            if (page * 5 + i < missionList.size()) {
                fun.wraq.process.system.missions.Mission mission = missionList.get(page * 5 + i);

                guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                append(mission.title).
                                append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                        X - 132, Y - 80 + i * 32, 0);

                guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                append(mission.description).
                                append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                        X - 56, Y - 80 + i * 32, 0);

                guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                append(mission.description1).
                                append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                        X - 132, Y - 64 + i * 32, 0);

                if (fun.wraq.process.system.missions.MissionClient.getMissionStatus(mission.serialNum) == fun.wraq.process.system.missions.Mission.Status.unableToAccept) {
                    guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                    append(mission.frontLoadedDescription).
                                    append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                            X + 40, Y - 80 + 32 * i, 0);
                }

                if (fun.wraq.process.system.missions.MissionClient.getMissionStatus(mission.serialNum) == fun.wraq.process.system.missions.Mission.Status.done) {
                    guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                    append(Component.literal("已完成").withStyle(CustomStyle.styleOfFlexible)).
                                    append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                            X + 40, Y - 80 + 32 * i, 0);
                }

                if (MissionClient.getMissionStatus(mission.serialNum) == fun.wraq.process.system.missions.Mission.Status.inProgress) {
                    if (x > X + 68 && x < X + 100 && y > Y - 76 + 32 * i && y < Y - 56 + 32 * i) {
                        if (fun.wraq.process.system.missions.Mission.rewardContent.isEmpty()) fun.wraq.process.system.missions.Mission.setRewardContent();
                        if (fun.wraq.process.system.missions.Mission.rewardContent.containsKey(mission.serialNum)) {
                            List<Component> components = new ArrayList<>();
                            components.add(Component.literal("「完成奖励」").withStyle(ChatFormatting.LIGHT_PURPLE));
                            for (int j = 0; j < fun.wraq.process.system.missions.Mission.rewardContent.get(mission.serialNum).size(); j++) {
                                ItemStack stack = fun.wraq.process.system.missions.Mission.rewardContent.get(mission.serialNum).get(j);
                                components.add(Component.literal("").withStyle(ChatFormatting.AQUA).
                                        append(stack.getDisplayName()).
                                        append(Component.literal(" * ").withStyle(ChatFormatting.AQUA)).
                                        append(Component.literal("" + stack.getCount()).withStyle(ChatFormatting.AQUA)));
                            }
                            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
                        }
                    }
                }

                if (fun.wraq.process.system.missions.Mission.detailContent.isEmpty()) fun.wraq.process.system.missions.Mission.setDetailContent();
                if (fun.wraq.process.system.missions.Mission.detailContent.containsKey(mission.serialNum)) {
                    if (x > X - 132 && x < X - 132 + 64 && y > Y - 80 + i * 32 && y < Y - 80 + i * 32 + 8) {
                        guiGraphics.renderComponentTooltip(fontRenderer, Mission.detailContent.get(mission.serialNum), x, y);
                    }
                }
            }
        }
        super.render(graphics, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
