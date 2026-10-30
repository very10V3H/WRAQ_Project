package fun.wraq.render.gui.skills;

import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SkillPackets.SkillRequestC2SPacket;
import fun.wraq.networking.misc.SmartPhonePackets.MarketScreenC2SPacket;
import fun.wraq.networking.misc.TeamPackets.TeamScreenOpenRequestC2SPacket;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.guide.networking.GuideFinishC2SPacket;
import fun.wraq.process.system.achievement.AchievementScreen;
import fun.wraq.process.func.plan.networking.DailySupplyC2SPacket;
import fun.wraq.process.system.missions.netWorking.MissionScreenOpenC2SPacket;
import fun.wraq.process.system.tower.TowerScreen;
import fun.wraq.process.system.vp.VpStoreScreen;
import fun.wraq.render.gui.illustrate.Illustrate;
import fun.wraq.render.gui.mission.ReputationStore;
import fun.wraq.render.gui.trade.weekly.WeeklyStoreScreenC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

/**
 * AI-Generated, 2026-10-25
 * 身份卡功能入口，供独立 {@link IdCardGui} 和原版物品栏面板共用。
 * <p>
 * 这里只负责触发，不持有界面；所有服务端交互仍走原有 C2S 包。
 */
public final class IdCardActions {

    private IdCardActions() {
    }

    public static void requestData() {
        ModNetworking.sendToServer(new SkillRequestC2SPacket());
    }

    public static void openMastery(Screen parent) {
        MasteryScreen.tryOpen(parent);
    }

    public static void claimSupply() {
        ModNetworking.sendToServer(new DailySupplyC2SPacket());
    }

    public static void openResearch() {
        ModNetworking.sendToServer(new WeeklyStoreScreenC2SPacket());
    }

    public static void openAchievements() {
        Minecraft.getInstance().setScreen(new AchievementScreen(-1, 0));
    }

    public static void openMissions() {
        ModNetworking.sendToServer(new MissionScreenOpenC2SPacket(2));
    }

    public static void openMarket() {
        ModNetworking.sendToServer(new MarketScreenC2SPacket(0));
    }

    public static void openReputation() {
        Minecraft.getInstance().setScreen(new ReputationStore(true));
    }

    public static void openVpStore() {
        Minecraft.getInstance().setScreen(new VpStoreScreen());
    }

    public static void openIllustrate() {
        Minecraft.getInstance().setScreen(new Illustrate(true, 0));
        ModNetworking.sendToServer(new GuideFinishC2SPacket(Guide.StageV2.ILLUSTRATE));
    }

    public static void openTower() {
        Minecraft.getInstance().setScreen(new TowerScreen(0));
    }

    public static void openTeam() {
        ModNetworking.sendToServer(new TeamScreenOpenRequestC2SPacket());
    }
}
