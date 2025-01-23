package fun.wraq.process.system.entrustment.mob;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;
import java.util.List;

public class MobKillEntrustmentHud {
    public static Component mobName;
    public static int targetCount;
    public static int currentCount;
    public static int startServerTick;
    public static int reputationExpiredMin;
    public static int reputation;
    public static int dailyFinishedTimes;
    public static int weeklyFinishedTimes;
    public static int totalFinishedTimes;
    public static int averageTick;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    public static final IGuiOverlay ENTRUSTMENT_HUD = ((gui, poseStack, partialTick, width, height) -> {
        if (mc.player.experienceLevel < 75) return;
        int x = width / 2;
        int y = height / 2;
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        if (Guide.clientDisplay && (mc.screen == null || mc.screen instanceof ChatScreen)) {
            List<Component> components = new ArrayList<>();
            if (mobName.getString().isEmpty()) {
                components.add(Te.s("委托任务概况", CustomStyle.styleOfWorld));
                components.add(Te.s("当前", "委托声望: ", CustomStyle.styleOfWorld, reputation,
                        String.format(" (+%.0f%%)", MobKillEntrustment.getExRateOfReputation(reputation) * 100), getTierStyle(reputation)));
                if (reputation > 0) {
                    if (reputationExpiredMin == 0) {
                        components.add(Te.s("每", "1min", CustomStyle.styleOfStone, " -1", "委托声望", CustomStyle.styleOfWorld));
                    } else {
                        components.add(Te.s("衰减剩余时间: ", reputationExpiredMin + "min", CustomStyle.styleOfStone));
                    }
                }
                components.add(Te.s("今日已完成次数: ", dailyFinishedTimes, CustomStyle.styleOfWorld));
                components.add(Te.s("本周已完成次数: ", weeklyFinishedTimes, CustomStyle.styleOfMoon1));
                components.add(Te.s("总完成次数: ", totalFinishedTimes, ChatFormatting.YELLOW));
                components.add(Te.s("平均用时: ", MobKillEntrustment.getDeltaTimeFormatString(averageTick, 0), CustomStyle.styleOfSky));
            } else {
                components.add(Te.s("委托任务", CustomStyle.styleOfWorld));
                components.add(Te.s("击杀 ", mobName));
                if (currentCount >= targetCount) {
                    components.add(Te.s("当前进度: ", "已完成", ChatFormatting.GREEN));
                } else {
                    components.add(Te.s("当前进度: ", currentCount, " / ", targetCount, ChatFormatting.AQUA));
                }
                int deltaTick = ClientUtils.serverTick - startServerTick;
                if (PlanPlayer.clientPlanTier > 0) {
                    boolean flag = false;
                    for (MobKillEntrustment.TimeAndTier timeAndTier : MobKillEntrustment.planTimeAndTiers) {
                        if (deltaTick <= Tick.min(timeAndTier.minutes())) {
                            components.add(Te.s("在",timeAndTier.minutes()  + "min", ChatFormatting.AQUA, "内提交"));
                            components.add(Te.s("可获得", timeAndTier.component(), "评级"));
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        components.add(Te.s("完成必定获得", "B+", ChatFormatting.LIGHT_PURPLE, "评级"));
                    }
                } else {
                    for (MobKillEntrustment.TimeAndTier timeAndTier : MobKillEntrustment.timeAndTiers) {
                        if (deltaTick <= Tick.min(timeAndTier.minutes())) {
                            components.add(Te.s("在", timeAndTier.minutes()  + "min", ChatFormatting.AQUA, "内提交"));
                            components.add(Te.s("可获得", timeAndTier.component(), "评级"));
                            break;
                        }
                    }
                }
                components.add(Te.s("已进行 ",
                        MobKillEntrustment.getDeltaTimeFormatString(ClientUtils.serverTick, startServerTick), ChatFormatting.AQUA));
                components.add(Te.s("当前", "委托声望: ", CustomStyle.styleOfWorld, reputation,
                        String.format(" (+%.0f%%)", MobKillEntrustment.getExRateOfReputation(reputation) * 100), getTierStyle(reputation)));
                components.add(Component.literal("").withStyle(ChatFormatting.WHITE));
                components.add(Te.s("按下", "[Tab]", ChatFormatting.AQUA));
                components.add(Te.s("以打开/关闭此栏"));
                components.add(Te.s("你也可以前往"));
                components.add(Te.s("按键绑定", CustomStyle.styleOfStone, "修改按键"));
            }
            guiGraphics.renderComponentTooltip(fontRenderer, components, x * 2, (int) (y / 1.2));
        }
    });

    public static ChatFormatting getTierStyle(int reputation) {
        if (reputation <= 5) {
            return ChatFormatting.GREEN;
        } else if (reputation < 10) {
            return ChatFormatting.YELLOW;
        } else return ChatFormatting.RED;
    }
}
