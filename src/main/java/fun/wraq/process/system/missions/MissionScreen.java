package fun.wraq.process.system.missions;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.missions.mission2.MissionV2;
import fun.wraq.process.system.missions.mission2.MissionV2Helper;
import fun.wraq.process.system.missions.mainline.MainQuestHelper;
import fun.wraq.process.system.missions.netWorking.MissionAcceptC2SPacket;
import fun.wraq.process.system.missions.netWorking.MissionSubmitC2SPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MissionScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/tower.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private final int page;
    private final int type; // 0 - 全部任务 1 - 未接受 2 - 进行中 3 - 已完成
    private final int category; // 0 - 全部分类 1 - 每日任务 2 - 主线任务 3 - 支线任务
    private List<MissionV2> missionList;

    public MissionScreen(int type, int page) {
        this(type, page, 0, false);
    }

    public MissionScreen(int type, int page, int category) {
        this(type, page, category, false);
    }

    public MissionScreen(int type, int page, int category, boolean autoLocateMainQuest) {
        super(Component.translatable("menu.teamscreen"));
        this.type = type;
        this.category = category;
        this.showPauseMenu = true;
        this.missionList = getFilteredMissions(type, category);
        this.page = autoLocateMainQuest ? locateMainQuestPage(this.missionList, page) : page;
    }

    /**
     * AI-Generated, 2026-10-18
     * 首次打开任务列表时，自动跳到当前主线阶段所在页（29 个阶段后避免玩家自己翻页）。
     * 翻页按钮复用旧构造器，不会再次触发自动定位。
     */
    private static int locateMainQuestPage(List<MissionV2> list, int page) {
        if (page != 0) {
            return page;
        }
        fun.wraq.process.system.missions.mainline.MainQuestStage.Stage current =
                MainQuestHelper.clientCurrent();
        if (current == null) {
            return page;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).name().equals(current.mission())) {
                return i / 5;
            }
        }
        return page;
    }

    private static CompoundTag getStatusData() {
        return MissionV2.clientMissionData.getCompound(MissionV2Helper.MISSION_V2_STATUS_KEY);
    }

    /**
     * AI-Generated, 2026-10-18
     * 将界面使用的分类序号（1~3）转换为任务分类，0 或非法值返回 null 表示不筛选.
     */
    private static MissionV2.Category getCategory(int category) {
        MissionV2.Category[] categories = MissionV2.Category.values();
        if (category <= 0 || category > categories.length) return null;
        return categories[category - 1];
    }

    private static boolean matchCategory(MissionV2 mission, int category) {
        MissionV2.Category target = getCategory(category);
        return target == null || mission.getCategory() == target;
    }

    private static boolean matchStatus(MissionV2 mission, int type, CompoundTag statusData) {
        if (type == 0) return true;
        String status = statusData.getString(mission.name());
        return switch (type) {
            case 1 -> status.equals(MissionV2.Status.NOT_ACCEPTED);
            case 2 -> status.equals(MissionV2.Status.IN_PROGRESS);
            case 3 -> status.equals(MissionV2.Status.FINISHED);
            default -> true;
        };
    }

    /**
     * AI-Generated, 2026-10-18
     * 同时按状态（type）与分类（category）筛选任务.
     */
    private static List<MissionV2> getFilteredMissions(int type, int category) {
        CompoundTag statusData = getStatusData();
        return Arrays.stream(MissionV2.values())
                .filter(missionV2 -> matchCategory(missionV2, category))
                .filter(missionV2 -> matchStatus(missionV2, type, statusData))
                // 主线未解锁（状态为空）时不进入任务列表
                .filter(missionV2 -> !MainQuestHelper.isMainMission(missionV2)
                        || !statusData.getString(missionV2.name()).isEmpty())
                .toList();
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
        }
    }

    private void createMenu() {
        CompoundTag statusData = getStatusData();
        int allCount = getFilteredMissions(0, category).size();
        int notAcceptedCount = getFilteredMissions(1, category).size();
        int inProgressCount = getFilteredMissions(2, category).size();
        int finishedCount = getFilteredMissions(3, category).size();
        int X = this.width / 2;
        int Y = this.height / 2;
        if (this.minecraft == null) return;
        this.addRenderableWidget(Button.builder(Te.s("全部任务(" + allCount + ")"), (p_280814_) -> {
            this.minecraft.setScreen(new MissionScreen(0, 0, category, true));
        }).pos(X + 150, Y - 98).size(48, 20).build());
        this.addRenderableWidget(Button.builder(Te.s("可接受(" + notAcceptedCount + ")"), (p_280814_) -> {
            this.minecraft.setScreen(new MissionScreen(1, 0, category, true));
        }).pos(X + 150, Y - 74).size(48, 20).build());
        this.addRenderableWidget(Button.builder(Te.s("进行中(" + inProgressCount + ")"), (p_280814_) -> {
            this.minecraft.setScreen(new MissionScreen(2, 0, category, true));
        }).pos(X + 150, Y - 50).size(48, 20).build());
        this.addRenderableWidget(Button.builder(Te.s("已完成(" + finishedCount + ")"), (p_280814_) -> {
            this.minecraft.setScreen(new MissionScreen(3, 0, category, true));
        }).pos(X + 150, Y - 26).size(48, 20).build());
        // 左侧任务分类筛选按钮：点击当前分类可取消筛选
        MissionV2.Category[] categories = MissionV2.Category.values();
        for (int i = 0; i < categories.length; i++) {
            int targetCategory = i + 1;
            MissionV2.Category categoryType = categories[i];
            int categoryCount = getFilteredMissions(type, targetCategory).size();
            Component label = Te.s(categoryType.displayName + "(" + categoryCount + ")");
            if (this.category == targetCategory) label = label.copy().withStyle(ChatFormatting.GOLD);
            this.addRenderableWidget(Button.builder(label, (p_280814_) -> {
                int newCategory = this.category == targetCategory ? 0 : targetCategory;
                this.minecraft.setScreen(new MissionScreen(this.type, 0, newCategory, true));
            }).pos(X - 206, Y - 74 + 24 * i).size(56, 20).build());
        }
        this.addRenderableWidget(Button.builder(Te.s("←"), (p_280814_) -> {
            if (page > 0) this.minecraft.setScreen(new MissionScreen(type, page - 1, category));
        }).pos(this.width / 2 - 39 + 5, this.height / 2 - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Te.s("→"), (p_280814_) -> {
            if (page < Math.ceil((double) missionList.size() / 5) - 1)
                this.minecraft.setScreen(new MissionScreen(type, page + 1, category));
        }).pos(this.width / 2 + 20 + 5, this.height / 2 - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Te.s("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X + 136, Y - 98).size(12, 12).build());
        for (int i = 0; i < 5; i++) {
            if (page * 5 + i < missionList.size()) {
                MissionV2 mission = missionList.get(page * 5 + i);
                // 主线只能通过 NPC 对话接取/交付，任务列表不提供按钮
                if (MainQuestHelper.isMainMission(mission)) {
                    continue;
                }
                if (statusData.getString(mission.name()).equals(MissionV2.Status.NOT_ACCEPTED)) {
                    this.addRenderableWidget(Button.builder(Component.translatable("接受")
                            .withStyle(ChatFormatting.GREEN), (p_280814_) -> {
                        ModNetworking.sendToServer(new MissionAcceptC2SPacket(mission.ordinal()));
                    }).pos(X + 108, Y - 76 + 32 * i).size(32, 20).build());
                } else if (statusData.getString(mission.name()).equals(MissionV2.Status.IN_PROGRESS)) {
                    this.addRenderableWidget(Button.builder(Component.translatable("提交")
                            .withStyle(ChatFormatting.YELLOW), (p_280814_) -> {
                        ModNetworking.sendToServer(new MissionSubmitC2SPacket(mission.ordinal()));
                    }).pos(X + 108, Y - 76 + 32 * i).size(32, 20).build());
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
        String screenTitle;
        switch (type) {
            case 0 -> screenTitle = "全部任务";
            case 1 -> screenTitle = "可接受的任务";
            case 2 -> screenTitle = "进行中的任务";
            default -> screenTitle = "已完成的任务";
        }
        MissionV2.Category selectedCategory = getCategory(category);
        if (selectedCategory != null) screenTitle = selectedCategory.displayName + " · " + screenTitle;
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("「" + screenTitle + "」")
                .withStyle(CustomStyle.styleOfFlexible), X + 5, Y - 96, 0);
        if (!missionList.isEmpty()) {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1))
                    .withStyle(ChatFormatting.WHITE), X + 5, Y - 22 + 105, 0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("暂无符合条件的任务")
                    .withStyle(ChatFormatting.GRAY), X + 5, Y - 40, 0);
        }
        guiGraphics.drawString(fontRenderer, Component.literal("光标移动至任务名可查看需求")
                .withStyle(ChatFormatting.WHITE), X - 144, Y - 108, 0);
        guiGraphics.drawString(fontRenderer, Component.literal("光标移动至提交按钮查看奖励")
                .withStyle(ChatFormatting.WHITE), X + 28, Y - 108, 0);
        CompoundTag statusData = MissionV2.clientMissionData.getCompound(MissionV2Helper.MISSION_V2_STATUS_KEY);
        for (int i = 0; i < 5; i++) {
            if (page * 5 + i < missionList.size()) {
                MissionV2 mission = missionList.get(page * 5 + i);
                String status = statusData.getString(mission.name());
                Component title = mission.title;
                if (mission.titleOperation != null && status.equals(MissionV2.Status.IN_PROGRESS)) {
                    try {
                        title = mission.titleOperation.operation(mission, MissionV2.clientMissionData);
                    } catch (CommandSyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
                guiGraphics.drawString(fontRenderer, Te.s("「", ChatFormatting.AQUA, title,
                                "」", ChatFormatting.AQUA),
                        X - 132, Y - 80 + i * 32, 0);
                // 描述文本：超过6字截断，悬停显示全文
                String descStr = mission.description.getString();
                if (descStr.length() > 6) {
                    Component truncatedDesc = Component.literal("「").withStyle(ChatFormatting.AQUA)
                            .append(Component.literal(descStr.substring(0, 6) + "…").withStyle(ChatFormatting.AQUA))
                            .append(Component.literal("」").withStyle(ChatFormatting.AQUA));
                    guiGraphics.drawString(fontRenderer, truncatedDesc, X - 20, Y - 80 + i * 32, 0);
                } else {
                    guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA)
                                    .append(mission.description)
                                    .append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                            X - 20, Y - 80 + i * 32, 0);
                }
                Component tipsComponent = mission.tips;
                if (mission.tipsOperation != null && status.equals(MissionV2.Status.IN_PROGRESS)) {
                    try {
                        tipsComponent = mission.tipsOperation.operation(mission, MissionV2.clientMissionData);
                    } catch (CommandSyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
                guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                append(tipsComponent).
                                append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                        X - 132, Y - 64 + i * 32, 0);
                if (status.isEmpty() || status.equals(MissionV2.Status.NOT_ACCEPTED)) {
                    guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                    append(mission.frontCondition).
                                    append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                            X + 44, Y - 80 + 32 * i, 0);
                }
                if (MainQuestHelper.isMainMission(mission)
                        && (status.equals(MissionV2.Status.NOT_ACCEPTED)
                        || status.equals(MissionV2.Status.IN_PROGRESS))) {
                    guiGraphics.drawCenteredString(fontRenderer, Te.s("对话", ChatFormatting.GRAY),
                            X + 124, Y - 70 + 32 * i, 0);
                }
                if (status.equals(MissionV2.Status.FINISHED)) {
                    guiGraphics.drawString(fontRenderer, Component.literal("「").withStyle(ChatFormatting.AQUA).
                                    append(Component.literal("已完成").withStyle(CustomStyle.styleOfFlexible)).
                                    append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                            X + 64, Y - 72 + 32 * i, 0);
                }
                if (status.equals(MissionV2.Status.IN_PROGRESS)) {
                    if (x > X + 68 && x < X + 100 && y > Y - 76 + 32 * i && y < Y - 56 + 32 * i) {
                        List<Component> components = new ArrayList<>();
                        components.add(Component.literal("「完成奖励」").withStyle(ChatFormatting.LIGHT_PURPLE));
                        components.addAll(mission.rewardDescription);
                        guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
                    }
                }
                if (x > X - 160 && x < X - 132 + 112 && y > Y - 86 + i * 32 && y < Y - 86 + i * 32 + 32) {
                    if (mission.detailOperation != null && status.equals(MissionV2.Status.IN_PROGRESS)) {
                        try {
                            guiGraphics.renderTooltip(fontRenderer,
                                    mission.detailOperation.operation(mission, MissionV2.clientMissionData), x, y);
                        } catch (CommandSyntaxException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                // 描述过长时，悬停显示全文
                if (descStr.length() > 6
                        && x > X - 20 && x < X + 80
                        && y > Y - 86 + i * 32 && y < Y - 86 + i * 32 + 32) {
                    guiGraphics.renderTooltip(fontRenderer,
                            Component.literal("「").withStyle(ChatFormatting.AQUA)
                                    .append(mission.description)
                                    .append(Component.literal("」").withStyle(ChatFormatting.AQUA)),
                            x, y);
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
