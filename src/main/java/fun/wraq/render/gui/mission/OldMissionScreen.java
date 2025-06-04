package fun.wraq.render.gui.mission;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.dailyMission.DailyMissionFinishedRequestC2SPacket;
import fun.wraq.networking.dailyMission.DailyMissionRequestC2SPacket;
import fun.wraq.networking.reputationMission.ReputationMissionCancelRequestC2SPacket;
import fun.wraq.networking.reputationMission.ReputationMissionFinishedRequestC2SPacket;
import fun.wraq.networking.reputationMission.ReputationMissionRequestC2SPacket;
import fun.wraq.process.func.plan.networking.mission.PlanMissionCancelRequestC2SPacket;
import fun.wraq.process.func.plan.networking.mission.PlanMissionFinishedRequestC2SPacket;
import fun.wraq.process.func.plan.networking.mission.PlanMissionRequestC2SPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@OnlyIn(Dist.CLIENT)
public class OldMissionScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/mission_screen.png");
    public static final Minecraft mc = Minecraft.getInstance();

    public static ItemStack planMissionItem;
    public static int planMissionItemCount;
    public static String planMissionStartTime = "";
    public static String planMissionAllowRequestTime = "";

    public OldMissionScreen() {
        super(Component.translatable("menu.mission_screen"));
    }

    protected void init() {
        this.createMenu();
    }

    private void createMenu() {

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12, 12).build());

        this.addRenderableWidget(Button.builder(Component.translatable("接取"), (p_280814_) -> {
            ModNetworking.sendToServer(new DailyMissionRequestC2SPacket());
        }).pos(this.width / 2 - 115, this.height / 2 + 40).size(32, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("提交"), (p_280814_) -> {
            ModNetworking.sendToServer(new DailyMissionFinishedRequestC2SPacket());
        }).pos(this.width / 2 - 100, this.height / 2 + 64).size(32, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("接取"), (p_280814_) -> {
            ModNetworking.sendToServer(new ReputationMissionRequestC2SPacket());
        }).pos(this.width / 2 - 115 + 98, this.height / 2 + 40).size(32, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("提交"), (p_280814_) -> {
            ModNetworking.sendToServer(new ReputationMissionFinishedRequestC2SPacket());
        }).pos(this.width / 2 - 97 + 98, this.height / 2 + 64).size(32, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("取消"), (p_280814_) -> {
            ModNetworking.sendToServer(new ReputationMissionCancelRequestC2SPacket());
        }).pos(this.width / 2 - 133 + 98, this.height / 2 + 64).size(32, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("接取"), (p_280814_) -> {
            ModNetworking.sendToServer(new PlanMissionRequestC2SPacket());
        }).pos(this.width / 2 - 115 + 98 * 2, this.height / 2 + 40).size(32, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("提交"), (p_280814_) -> {
            ModNetworking.sendToServer(new PlanMissionFinishedRequestC2SPacket());
        }).pos(this.width / 2 - 97 + 98 * 2, this.height / 2 + 64).size(32, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("取消"), (p_280814_) -> {
            ModNetworking.sendToServer(new PlanMissionCancelRequestC2SPacket());
        }).pos(this.width / 2 - 133 + 98 * 2, this.height / 2 + 64).size(32, 16).build());
    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int textureWidth = 300;
        int textureHeight = 200;
        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100,
                0, 0, 300, 200, textureWidth, textureHeight);
        // 每日任务
        guiGraphics.renderItem(ModItems.DAILY_MISSION.get().getDefaultInstance(), this.width / 2 - 110, this.height / 2 - 80);
        guiGraphics.drawCenteredString(font, Component.literal("每日任务").withStyle(ChatFormatting.AQUA), this.width / 2 - 100, this.height / 2 - 60, 0);

        if (ClientUtils.DailyMissionItem == null || ClientUtils.DailyMissionItem.is(Items.AIR)) {
            guiGraphics.drawCenteredString(font, Component.literal("完成物品收集任务").withStyle(ChatFormatting.WHITE), this.width / 2 - 100, this.height / 2 - 40, 0);
            guiGraphics.drawCenteredString(font, Component.literal("获得").withStyle(ChatFormatting.WHITE), this.width / 2 - 100, this.height / 2 - 24, 0);
            guiGraphics.drawCenteredString(font, Component.literal("经验|宝石碎片奖励").withStyle(ChatFormatting.WHITE), this.width / 2 - 100, this.height / 2 - 8, 0);

            Calendar calendar;
            try {
                calendar = Compute.StringToCalendar(ClientUtils.lastDailyMissionFinishedTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            calendar.add(Calendar.HOUR_OF_DAY, 22);
            try {
                if (calendar.before(Compute.StringToCalendar(ClientUtils.serverTime))) {
                    guiGraphics.drawCenteredString(font, Component.literal("可接取").withStyle(ChatFormatting.WHITE), this.width / 2 - 100, this.height / 2 + 8, 0);
                } else {
                    Calendar time = Compute.StringToCalendar(ClientUtils.serverTime);
                    guiGraphics.drawCenteredString(font, Component.literal("任务冷却：")
                                    .withStyle(ChatFormatting.AQUA),
                            this.width / 2 - 100, this.height / 2 + 8, 0);
                    guiGraphics.drawCenteredString(font, Component.literal(Compute.getDifferenceFormatText(calendar, time))
                                    .withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100, this.height / 2 + 24, 0);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        } else {
            guiGraphics.drawCenteredString(font, Component.literal("进行中").withStyle(ChatFormatting.RED), this.width / 2 - 100, this.height / 2 - 40, 0);
            guiGraphics.renderItem(ClientUtils.DailyMissionItem, this.width / 2 - 110, this.height / 2 - 20);
            guiGraphics.drawCenteredString(font, Component.literal("").append(ClientUtils.DailyMissionItem.getDisplayName()).
                    append(Component.literal("x" + ClientUtils.DailyMissionItemCount).withStyle(ChatFormatting.WHITE)), this.width / 2 - 100, this.height / 2 + 8, 0);
        }

        // 悬赏任务
        guiGraphics.renderItem(ModItems.Boss2Piece.get().getDefaultInstance(), this.width / 2 - 110 + 98, this.height / 2 - 80);
        guiGraphics.drawCenteredString(font, Component.literal("悬赏任务").withStyle(ChatFormatting.GOLD), this.width / 2 - 100 + 98, this.height / 2 - 60, 0);

        int xOffsetRate = 1;

        if (ClientUtils.reputationMissionItem == null || ClientUtils.reputationMissionItem.is(Items.AIR)) {
            guiGraphics.drawCenteredString(font, Component.literal("完成物品收集任务").withStyle(ChatFormatting.WHITE), this.width / 2 - 100 + 98, this.height / 2 - 40, 0);
            guiGraphics.drawCenteredString(font, Component.literal("获得").withStyle(ChatFormatting.WHITE), this.width / 2 - 100 + 98, this.height / 2 - 24, 0);
            guiGraphics.drawCenteredString(font, Component.literal("经验|声望奖励").withStyle(ChatFormatting.WHITE), this.width / 2 - 100 + 98, this.height / 2 - 8, 0);

            Calendar currentTime = null;
            try {
                currentTime = Compute.StringToCalendar(ClientUtils.serverTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (!ClientUtils.ReputationMissionAllowRequestTime.isEmpty()) {
                Calendar allowTime = null;
                try {
                    allowTime = Compute.StringToCalendar(ClientUtils.ReputationMissionAllowRequestTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (currentTime.before(allowTime)) {
                    guiGraphics.drawCenteredString(font, Component.literal("任务冷却：")
                                    .withStyle(ChatFormatting.AQUA),
                            this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 8, 0);
                    guiGraphics.drawCenteredString(font, Component.literal(Compute.getDifferenceFormatText(allowTime, currentTime))
                                    .withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 24, 0);
                } else {
                    guiGraphics.drawCenteredString(font, Component.literal("可接取").withStyle(ChatFormatting.WHITE), this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 8, 0);
                }
            } else {
                guiGraphics.drawCenteredString(font, Component.literal("可接取").withStyle(ChatFormatting.WHITE), this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 8, 0);
            }

        } else {
            guiGraphics.drawCenteredString(font, Component.literal("进行中").withStyle(ChatFormatting.RED), this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 - 48, 0);
            guiGraphics.renderItem(ClientUtils.reputationMissionItem, this.width / 2 - 110 + 98 * xOffsetRate, this.height / 2 - 36);
            guiGraphics.drawCenteredString(font, Component.literal("").append(ClientUtils.reputationMissionItem.getDisplayName()).
                    append(Component.literal("x" + ClientUtils.reputationMissionItemCount).withStyle(ChatFormatting.WHITE)), this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 - 8, 0);

            Calendar currentTime = null;
            try {
                currentTime = Compute.StringToCalendar(ClientUtils.serverTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Calendar startTime = null;
            try {
                startTime = Compute.StringToCalendar(ClientUtils.ReputationMissionStartTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            long delta = currentTime.getTimeInMillis() - startTime.getTimeInMillis();
            long seconds = delta / 1000 % 60;
            long hours = delta / (1000 * 3600);
            long minute = delta / (1000 * 60) % 60;
            SimpleDateFormat tmpDate = new SimpleDateFormat("HH:mm:ss");
            Calendar deltaTime = Calendar.getInstance();
            deltaTime.set(Calendar.HOUR_OF_DAY, (int) hours);
            deltaTime.set(Calendar.MINUTE, (int) minute);
            deltaTime.set(Calendar.SECOND, (int) seconds);
            int tier = Math.max(0, 5 - (int) (delta / (1000 * 60) / 15));

            String[] level = {
                    "普通", "优秀", "精良", "史诗", "传说", "神话"
            };

            ChatFormatting[] chatFormattings = {
                    ChatFormatting.GRAY,
                    ChatFormatting.GREEN,
                    ChatFormatting.AQUA,
                    ChatFormatting.LIGHT_PURPLE,
                    ChatFormatting.GOLD,
                    ChatFormatting.RED,
            };


            guiGraphics.drawCenteredString(font, Component.literal("当前评级：")
                            .withStyle(ChatFormatting.AQUA).
                            append(Component.literal(level[tier]).withStyle(chatFormattings[tier])),
                    this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 8, 0);
            if (hours >= 24) {
                guiGraphics.drawCenteredString(font, Component.literal("用时: --:--:--")
                                .withStyle(ChatFormatting.WHITE),
                        this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 24, 0);
            } else {
                guiGraphics.drawCenteredString(font, Component.literal("用时:" + tmpDate.format(deltaTime.getTime()))
                                .withStyle(ChatFormatting.WHITE),
                        this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 24, 0);
            }
        }

        xOffsetRate = 2;

        // 以下为月卡/计划
        guiGraphics.renderItem(ModItems.COMPLETE_GEM.get().getDefaultInstance(), this.width / 2 - 110 + 98 * xOffsetRate, this.height / 2 - 80);
        guiGraphics.drawCenteredString(font, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE), this.width / 2 - 100 + 98 * xOffsetRate - 1, this.height / 2 - 60, 0);

        if (planMissionItem == null || planMissionItem.is(Items.AIR)) {
            guiGraphics.drawCenteredString(font, Component.literal("完成物品收集任务").withStyle(ChatFormatting.WHITE), this.width / 2 - 100 + 98 * xOffsetRate - 1, this.height / 2 - 40, 0);
            guiGraphics.drawCenteredString(font, Component.literal("获得").withStyle(ChatFormatting.WHITE), this.width / 2 - 100 + 98 * xOffsetRate - 1, this.height / 2 - 24, 0);
            guiGraphics.drawCenteredString(font, Component.literal("丰富奖励").withStyle(ChatFormatting.LIGHT_PURPLE), this.width / 2 - 100 + 98 * xOffsetRate - 1, this.height / 2 - 8, 0);

            Calendar currentTime = null;
            try {
                currentTime = Compute.StringToCalendar(ClientUtils.serverTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (!planMissionAllowRequestTime.isEmpty()) {
                Calendar allowTime = null;
                try {
                    allowTime = Compute.StringToCalendar(planMissionAllowRequestTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (currentTime.before(allowTime)) {
                    long delta = (allowTime.getTimeInMillis() - currentTime.getTimeInMillis());
                    long seconds = delta / 1000 % 60;
                    long hours = delta / (1000 * 3600);
                    long minute = delta / (1000 * 60) % 60;
                    SimpleDateFormat tmpDate = new SimpleDateFormat("HH:mm:ss");
                    Calendar deltaTime = Calendar.getInstance();
                    deltaTime.set(Calendar.HOUR_OF_DAY, (int) hours);
                    deltaTime.set(Calendar.MINUTE, (int) minute);
                    deltaTime.set(Calendar.SECOND, (int) seconds);

                    guiGraphics.drawCenteredString(font, Component.literal("任务冷却：")
                                    .withStyle(ChatFormatting.AQUA),
                            this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 8, 0);

                    guiGraphics.drawCenteredString(font, Component.literal(tmpDate.format(deltaTime.getTime()))
                                    .withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 24, 0);
                } else {
                    guiGraphics.drawCenteredString(font, Component.literal("可接取").withStyle(ChatFormatting.WHITE), this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 8, 0);
                }
            } else {
                guiGraphics.drawCenteredString(font, Component.literal("可接取").withStyle(ChatFormatting.WHITE), this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 8, 0);
            }

        } else {
            guiGraphics.drawCenteredString(font, Component.literal("进行中").withStyle(ChatFormatting.RED), this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 - 48, 0);
            guiGraphics.renderItem(planMissionItem, this.width / 2 - 110 + 98 * xOffsetRate, this.height / 2 - 36);
            guiGraphics.drawCenteredString(font, Component.literal("").append(planMissionItem.getDisplayName()).
                    append(Component.literal("x" + planMissionItemCount).withStyle(ChatFormatting.WHITE)), this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 - 8, 0);

            Calendar currentTime = null;
            try {
                currentTime = Compute.StringToCalendar(ClientUtils.serverTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Calendar startTime = null;
            try {
                startTime = Compute.StringToCalendar(planMissionStartTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            long delta = currentTime.getTimeInMillis() - startTime.getTimeInMillis();
            long seconds = delta / 1000 % 60;
            long hours = delta / (1000 * 3600);
            long minute = delta / (1000 * 60) % 60;
            SimpleDateFormat tmpDate = new SimpleDateFormat("HH:mm:ss");
            Calendar deltaTime = Calendar.getInstance();
            deltaTime.set(Calendar.HOUR_OF_DAY, (int) hours);
            deltaTime.set(Calendar.MINUTE, (int) minute);
            deltaTime.set(Calendar.SECOND, (int) seconds);
            int tier = Math.max(0, 5 - (int) (delta / (1000 * 60) / 15));

            String[] level = {
                    "普通", "优秀", "精良", "史诗", "传说", "神话"
            };

            ChatFormatting[] chatFormattings = {
                    ChatFormatting.GRAY,
                    ChatFormatting.GREEN,
                    ChatFormatting.AQUA,
                    ChatFormatting.LIGHT_PURPLE,
                    ChatFormatting.GOLD,
                    ChatFormatting.RED,
            };


            guiGraphics.drawCenteredString(font, Component.literal("当前评级：")
                            .withStyle(ChatFormatting.AQUA).
                            append(Component.literal(level[tier]).withStyle(chatFormattings[tier])),
                    this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 8, 0);
            if (hours >= 24) {
                guiGraphics.drawCenteredString(font, Component.literal("用时: --:--:--")
                                .withStyle(ChatFormatting.WHITE),
                        this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 24, 0);
            } else {
                guiGraphics.drawCenteredString(font, Component.literal("用时:" + tmpDate.format(deltaTime.getTime()))
                                .withStyle(ChatFormatting.WHITE),
                        this.width / 2 - 100 + 98 * xOffsetRate, this.height / 2 + 24, 0);
            }
        }
        super.render(p_96310_, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
