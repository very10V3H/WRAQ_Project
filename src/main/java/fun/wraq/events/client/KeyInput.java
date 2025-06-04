package fun.wraq.events.client;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.KeyBoradInput;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.AnimationPackets.RollingRequestC2SPacket;
import fun.wraq.networking.misc.TeamPackets.TeamScreenOpenRequestC2SPacket;
import fun.wraq.networking.misc.USE.UseC2SPacket;
import fun.wraq.networking.unSorted.PlayerClickSpaceC2SPacket;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.guide.networking.GuideFinishC2SPacket;
import fun.wraq.process.system.element.ElementRoulette;
import fun.wraq.process.system.element.networking.CurrentSeasonC2SPacket;
import fun.wraq.process.system.element.piece.ElementPieceGui;
import fun.wraq.process.system.forge.ForgeScreen;
import fun.wraq.process.system.missions.MissionScreen;
import fun.wraq.process.system.missions.netWorking.MissionScreenOpenC2SPacket;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2Screen;
import fun.wraq.process.system.skill.skillv2.network.SkillV2PlayerTryToReleaseSkillC2SPacket;
import fun.wraq.process.system.smelt.SmeltProgressScreen;
import fun.wraq.process.system.smelt.SmeltRecipeScreen;
import fun.wraq.process.system.tower.TowerScreen;
import fun.wraq.process.system.vp.VpStoreScreen;
import fun.wraq.render.gui.illustrate.Illustrate;
import fun.wraq.render.gui.market.SmartPhone;
import fun.wraq.render.gui.mission.OldMissionScreen;
import fun.wraq.render.gui.mission.ReputationStore;
import fun.wraq.render.gui.skills.IdCardGui;
import fun.wraq.render.gui.skills.SkillTreeGui;
import fun.wraq.render.gui.team.*;
import fun.wraq.render.gui.trade.single.SingleItemChangeScreen;
import fun.wraq.render.gui.villagerTrade.TradeScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Calendar;

public class KeyInput {
    @Mod.EventBusSubscriber(modid = Utils.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (player == null) return;
            if (isModScreen(mc) && event.getKey() == 69 && event.getAction() == 1) {
                mc.popGuiLayer();
            }
            if (KeyBoradInput.SKILL_SCREEN.consumeClick()) {
                Minecraft.getInstance().setScreen(new SkillV2Screen(SkillV2.clientProfessionType));
            }
            if (KeyBoradInput.NEW_SKILL_1.consumeClick()) {
                if (ClientUtils.IsAdjustingPower) {
                    if (ClientUtils.PowerQueue.size() >= 4) {
                        ClientUtils.PowerQueue.poll();
                        ClientUtils.PowerQueue.add(1);
                    } else ClientUtils.PowerQueue.add(1);
                } else {
                    ModNetworking.sendToServer(new SkillV2PlayerTryToReleaseSkillC2SPacket(1));
                    SkillV2.clientLastReleaseTick = ClientUtils.serverTick;
                }
            }
            if (KeyBoradInput.NEW_SKILL_2.consumeClick()) {
                if (ClientUtils.IsAdjustingPower) {
                    if (ClientUtils.PowerQueue.size() >= 4) {
                        ClientUtils.PowerQueue.poll();
                        ClientUtils.PowerQueue.add(2);
                    } else ClientUtils.PowerQueue.add(2);
                } else {
                    ModNetworking.sendToServer(new SkillV2PlayerTryToReleaseSkillC2SPacket(2));
                    SkillV2.clientLastReleaseTick = ClientUtils.serverTick;
                }
            }
            if (KeyBoradInput.NEW_SKILL_3.consumeClick()) {
                if (ClientUtils.IsAdjustingPower) {
                    if (ClientUtils.PowerQueue.size() >= 4) {
                        ClientUtils.PowerQueue.poll();
                        ClientUtils.PowerQueue.add(3);
                    } else ClientUtils.PowerQueue.add(3);
                } else {
                    ModNetworking.sendToServer(new SkillV2PlayerTryToReleaseSkillC2SPacket(3));
                    SkillV2.clientLastReleaseTick = ClientUtils.serverTick;
                }
            }
            if (KeyBoradInput.NEW_SKILL_4.consumeClick()) {
                if (ClientUtils.IsAdjustingPower) {
                    if (ClientUtils.PowerQueue.size() >= 4) {
                        ClientUtils.PowerQueue.poll();
                        ClientUtils.PowerQueue.add(4);
                    } else ClientUtils.PowerQueue.add(4);
                } else {
                    ModNetworking.sendToServer(new SkillV2PlayerTryToReleaseSkillC2SPacket(4));
                    SkillV2.clientLastReleaseTick = ClientUtils.serverTick;
                }
            }
            if (KeyBoradInput.USE5.consumeClick()) {
                ModNetworking.sendToServer(new UseC2SPacket(7));
            }
            if (KeyBoradInput.USE6.consumeClick()) {
                ModNetworking.sendToServer(new UseC2SPacket(8));
            }
            if (KeyBoradInput.Rolling.consumeClick()) {
                ModNetworking.sendToServer(new RollingRequestC2SPacket());
            }

            if (KeyBoradInput.Mission.consumeClick()) {
                ModNetworking.sendToServer(new MissionScreenOpenC2SPacket(2));
            }

            if (KeyBoradInput.NavigateSet.consumeClick()) {
                if (ClientUtils.Mission) {
                    if (ClientUtils.NavigateIndex == -1) {
                        ClientUtils.NavigateIndex = ClientUtils.ListIndex;
                        player.sendSystemMessage(Component.literal("[系统]").withStyle(ChatFormatting.GRAY).
                                append(Component.literal("已启用目的地准星定点，请尝试移动准星直到无法看见红色粒子，位置即为目的地方向。[默认左Alt开启或关闭]").withStyle(ChatFormatting.WHITE)));
                    } else {
                        ClientUtils.NavigateIndex = -1;
                        player.sendSystemMessage(Component.literal("[系统]").withStyle(ChatFormatting.GRAY).
                                append(Component.literal("已关闭目的地准星定点。[默认左Alt开启或关闭]").withStyle(ChatFormatting.WHITE)));

                    }
                } else {
                    player.sendSystemMessage(Component.literal("[系统]").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("似乎没有任务坐标用于准星定点。").withStyle(ChatFormatting.WHITE)));

                }
            }

            if (KeyBoradInput.GUIDE.consumeClick()) {
                if (Calendar.getInstance().getTimeInMillis() - ClientUtils.tabSwitchLastTime > 250) {
                    Guide.clientDisplay = !Guide.clientDisplay;
                    if (Guide.clientDisplay) {
                        Compute.sendFormatMSG(player, Te.s("系统", ChatFormatting.AQUA),
                                Te.s("已开启", ChatFormatting.GREEN, "附加界面"));
                    } else {
                        Compute.sendFormatMSG(player, Te.s("系统", ChatFormatting.AQUA),
                                Te.s("已关闭", ChatFormatting.RED, "附加界面"));
                    }
                }
                ClientUtils.tabSwitchLastTime = Calendar.getInstance().getTimeInMillis();
            }

            if (KeyBoradInput.ElementRoulette.consumeClick()) {
                ModNetworking.sendToServer(new CurrentSeasonC2SPacket());
                Minecraft.getInstance().setScreen(new ElementRoulette());
                ModNetworking.sendToServer(new GuideFinishC2SPacket(Guide.StageV2.ELEMENT_ROULETTE));
            }

            if (KeyBoradInput.SPACE.consumeClick()) {
                // 适用于低重力环境跳跃
                if (Compute.inLowGravityEnvironment(player)) {
                    ModNetworking.sendToServer(new PlayerClickSpaceC2SPacket());
                }
            }

            if (KeyBoradInput.TEAM.consumeClick()) {
                ModNetworking.sendToServer(new TeamScreenOpenRequestC2SPacket());
            }
        }
    }

    private static boolean isModScreen(Minecraft mc) {
        Screen screen = mc.screen;
        return screen instanceof Illustrate
                || screen instanceof SmartPhone || screen instanceof OldMissionScreen
                || screen instanceof ReputationStore || screen instanceof IdCardGui
                || screen instanceof SkillTreeGui || screen instanceof InstanceScreen
                || screen instanceof PlayerRequestScreen || screen instanceof PlayerSearchScreen
                || screen instanceof TeamInfoScreen || screen instanceof TeamInviteScreen
                || screen instanceof TeamManageScreen || screen instanceof TeamSearchScreen
                || screen instanceof TradeScreen || screen instanceof MissionScreen
                || screen instanceof ForgeScreen || screen instanceof TowerScreen
                || screen instanceof ElementRoulette || screen instanceof VpStoreScreen
                || screen instanceof SmeltRecipeScreen || screen instanceof SmeltProgressScreen
                || screen instanceof SingleItemChangeScreen || screen instanceof SkillV2Screen
                || screen instanceof ElementPieceGui;
    }

    @Mod.EventBusSubscriber(modid = Utils.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void KeyBinding(RegisterKeyMappingsEvent event) {
            event.register(KeyBoradInput.SKILL_SCREEN);
            event.register(KeyBoradInput.NEW_SKILL_1);
            event.register(KeyBoradInput.NEW_SKILL_2);
            event.register(KeyBoradInput.NEW_SKILL_3);
            event.register(KeyBoradInput.NEW_SKILL_4);
            event.register(KeyBoradInput.USE5);
            event.register(KeyBoradInput.USE6);
            event.register(KeyBoradInput.Rolling);
            event.register(KeyBoradInput.Mission);
            event.register(KeyBoradInput.NavigateSet);
            event.register(KeyBoradInput.GUIDE);
            event.register(KeyBoradInput.ElementRoulette);
            event.register(KeyBoradInput.TEAM);
        }
    }
}
