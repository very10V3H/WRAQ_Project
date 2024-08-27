package com.very.wraq.common;

import com.very.wraq.Items.Drugs.KeyBoradInput;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.AnimationPackets.RollingAnimationRequestC2SPacket;
import com.very.wraq.networking.misc.DrugsC2SPacket;
import com.very.wraq.networking.misc.Limit.LimitC2SPacket;
import com.very.wraq.networking.misc.USE.*;
import com.very.wraq.process.func.guide.GuideHud;
import com.very.wraq.process.func.guide.networking.GuideFinishC2SPacket;
import com.very.wraq.process.system.element.ElementRoulette;
import com.very.wraq.process.system.element.networking.CurrentSeasonC2SPacket;
import com.very.wraq.process.system.forge.ForgeScreen;
import com.very.wraq.process.system.missions.MissionScreen;
import com.very.wraq.process.system.missions.netWorking.MissionScreenOpenC2SPacket;
import com.very.wraq.process.system.tower.TowerScreen;
import com.very.wraq.process.system.vp.VpStoreScreen;
import com.very.wraq.render.gui.illustrate.Illustrate;
import com.very.wraq.render.gui.market.SmartPhone;
import com.very.wraq.render.gui.mission.OldMissionScreen;
import com.very.wraq.render.gui.mission.ReputationStore;
import com.very.wraq.render.gui.skills.IdCardGui;
import com.very.wraq.render.gui.skills.SkillTreeGui;
import com.very.wraq.render.gui.team.*;
import com.very.wraq.render.gui.villagerTrade.TradeScreen;
import com.very.wraq.common.Utils.ClientUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class KeyInput {
    @Mod.EventBusSubscriber(modid = Utils.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft mc = Minecraft.getInstance();
            if (isModScreen(mc) && event.getKey() == 69 && event.getAction() == 1) mc.popGuiLayer();
            if (ClientUtils.PacketsLimit <= 0) {
                ModNetworking.sendToServer(new LimitC2SPacket());
            } else {
                if (KeyBoradInput.DRUGS.consumeClick()) {
                    if (ClientUtils.IsHandlingPower) {
                        ClientUtils.IsAdjustingPower = !ClientUtils.IsAdjustingPower;
                    } else {
                        ModNetworking.sendToServer(new DrugsC2SPacket());
                    }
                }

                if (KeyBoradInput.USE1.consumeClick()) {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(1);
                        } else ClientUtils.PowerQueue.add(1);
                    } else {
                        ModNetworking.sendToServer(new UseC2SPacket(3));
                    }
                }
                if (KeyBoradInput.USE2.consumeClick()) {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(2);
                        } else ClientUtils.PowerQueue.add(2);
                    } else {
                        ModNetworking.sendToServer(new UseC2SPacket(4));
                    }
                }
                if (KeyBoradInput.USE3.consumeClick()) {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(3);
                        } else ClientUtils.PowerQueue.add(3);
                    } else {
                        ModNetworking.sendToServer(new UseC2SPacket(5));
                    }
                }
                if (KeyBoradInput.USE4.consumeClick()) {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(4);
                        } else ClientUtils.PowerQueue.add(4);
                    } else {
                        ModNetworking.sendToServer(new UseC2SPacket(6));
                    }
                }
                if (KeyBoradInput.USE5.consumeClick()) {
                    ModNetworking.sendToServer(new UseC2SPacket(7));
                }
                if (KeyBoradInput.USE6.consumeClick()) {
                    ModNetworking.sendToServer(new UseC2SPacket(8));
                }
                if (KeyBoradInput.Rolling.consumeClick()) {
                    Player player = Minecraft.getInstance().player;
                    if (!ClientUtils.PlayerIsManaAttacking(player) && !ClientUtils.PlayerIsUsing(player)
                            && !ClientUtils.PlayerIsBowAttacking(player) && !ClientUtils.PlayerIsAttacking(player))
                        ModNetworking.sendToServer(new RollingAnimationRequestC2SPacket(0));
                }

                if (KeyBoradInput.Mission.consumeClick()) {
                    ModNetworking.sendToServer(new MissionScreenOpenC2SPacket(2));
                }

                if (KeyBoradInput.NavigateSet.consumeClick()) {
                    if (ClientUtils.Mission) {
                        if (ClientUtils.NavigateIndex == -1) {
                            ClientUtils.NavigateIndex = ClientUtils.ListIndex;
                            Minecraft.getInstance().player.sendSystemMessage(Component.literal("[系统]").withStyle(ChatFormatting.GRAY).
                                    append(Component.literal("已启用目的地准星定点，请尝试移动准星直到无法看见红色粒子，位置即为目的地方向。[默认左Alt开启或关闭]").withStyle(ChatFormatting.WHITE)));
                        } else {
                            ClientUtils.NavigateIndex = -1;
                            Minecraft.getInstance().player.sendSystemMessage(Component.literal("[系统]").withStyle(ChatFormatting.GRAY).
                                    append(Component.literal("已关闭目的地准星定点。[默认左Alt开启或关闭]").withStyle(ChatFormatting.WHITE)));

                        }
                    } else {
                        Minecraft.getInstance().player.sendSystemMessage(Component.literal("[系统]").withStyle(ChatFormatting.GRAY).
                                append(Component.literal("似乎没有任务坐标用于准星定点。").withStyle(ChatFormatting.WHITE)));

                    }
                }

                if (KeyBoradInput.GUIDE.consumeClick()) {
                    GuideHud.display = !GuideHud.display;
                }

                if (KeyBoradInput.ElementRoulette.consumeClick()) {
                    ModNetworking.sendToServer(new CurrentSeasonC2SPacket());
                    Minecraft.getInstance().setScreen(new ElementRoulette());
                    ModNetworking.sendToServer(new GuideFinishC2SPacket(6));
                }
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
                || screen instanceof ElementRoulette || screen instanceof VpStoreScreen;
    }

    @Mod.EventBusSubscriber(modid = Utils.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void KeyBinding(RegisterKeyMappingsEvent event) {
            event.register(KeyBoradInput.DRUGS);
            event.register(KeyBoradInput.USE1);
            event.register(KeyBoradInput.USE2);
            event.register(KeyBoradInput.USE3);
            event.register(KeyBoradInput.USE4);
            event.register(KeyBoradInput.USE5);
            event.register(KeyBoradInput.USE6);
            event.register(KeyBoradInput.Rolling);
            event.register(KeyBoradInput.Mission);
            event.register(KeyBoradInput.NavigateSet);
            event.register(KeyBoradInput.GUIDE);
            event.register(KeyBoradInput.ElementRoulette);
        }
    }
}
