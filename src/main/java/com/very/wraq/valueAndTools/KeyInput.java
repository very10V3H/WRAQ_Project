package com.very.wraq.valueAndTools;

import com.very.wraq.customized.players.sceptre.liulixian_.LiulixianCurios2;
import com.very.wraq.Items.Drugs.KeyBoradInput;
import com.very.wraq.netWorking.customized.LiuLiXianActiveC2SPacket;
import com.very.wraq.netWorking.customized.LiuLiXianSceptreC2SPacket;
import com.very.wraq.netWorking.customized.YuanShiRenC2SPacket;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.AnimationPackets.RollingAnimationRequestC2SPacket;
import com.very.wraq.netWorking.misc.DrugsC2SPacket;
import com.very.wraq.netWorking.misc.Limit.LimitC2SPacket;
import com.very.wraq.netWorking.misc.TeamPackets.TeamScreenOpenRequestC2SPacket;
import com.very.wraq.netWorking.misc.USE.*;
import com.very.wraq.valueAndTools.Utils.ClientUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class KeyInput {
    @Mod.EventBusSubscriber(modid = Utils.MOD_ID , value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (ClientUtils.PacketsLimit <= 0) {
                ModNetworking.sendToServer(new LimitC2SPacket());
            }
            else {
                if (KeyBoradInput.DRUGS.consumeClick()) {
                    if (ClientUtils.IsHandlingPower) {
                        ClientUtils.IsAdjustingPower = !ClientUtils.IsAdjustingPower;
                    }
                    else {
                        ModNetworking.sendToServer(new DrugsC2SPacket());
                    }
                }

                if (KeyBoradInput.USE1.consumeClick()) {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(1);
                        }
                        else ClientUtils.PowerQueue.add(1);
                    }
                    else {
                        ModNetworking.sendToServer(new Use1C2SPacket());
                    }
                }
                if (KeyBoradInput.USE2.consumeClick()) {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(2);
                        }
                        else ClientUtils.PowerQueue.add(2);
                    }
                    else {
                        ModNetworking.sendToServer(new Use2C2SPacket());
                    }
                }
                if (KeyBoradInput.USE3.consumeClick()) {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(3);
                        }
                        else ClientUtils.PowerQueue.add(3);
                    }
                    else {
                        ModNetworking.sendToServer(new Use3C2SPacket());
                    }
                }
                if (KeyBoradInput.USE4.consumeClick()) {
                    if (ClientUtils.IsAdjustingPower) {
                        if (ClientUtils.PowerQueue.size() >= 4) {
                            ClientUtils.PowerQueue.poll();
                            ClientUtils.PowerQueue.add(4);
                        }
                        else ClientUtils.PowerQueue.add(4);
                    }
                    else {
                        ModNetworking.sendToServer(new Use4C2SPacket());
                    }
                }
                if (KeyBoradInput.USE5.consumeClick()) {
                    ModNetworking.sendToServer(new Use5C2SPacket());
                }
                if (KeyBoradInput.USE6.consumeClick()) {
                    ModNetworking.sendToServer(new Use6C2SPacket());
                }
                if (KeyBoradInput.Rolling.consumeClick()) {
                    Player player = Minecraft.getInstance().player;
                    if (!ClientUtils.PlayerIsManaAttacking(player) && !ClientUtils.PlayerIsUsing(player)
                            && !ClientUtils.PlayerIsBowAttacking(player) && !ClientUtils.PlayerIsAttacking(player))
                        ModNetworking.sendToServer(new RollingAnimationRequestC2SPacket(0));
                }

                if (KeyBoradInput.Mission.consumeClick()) {
                    ClientUtils.Mission = !ClientUtils.Mission;
                }

                if (KeyBoradInput.TeamGuiOpen.consumeClick()) {
                    ModNetworking.sendToServer(new TeamScreenOpenRequestC2SPacket());
                }

                if (KeyBoradInput.NavigateSet.consumeClick()) {
                    if (ClientUtils.Mission) {
                        if (ClientUtils.NavigateIndex == -1) {
                            ClientUtils.NavigateIndex = ClientUtils.ListIndex;
                            Minecraft.getInstance().player.sendSystemMessage(Component.literal("[系统]").withStyle(ChatFormatting.GRAY).
                                    append(Component.literal("已启用目的地准星定点，请尝试移动准星直到无法看见红色粒子，位置即为目的地方向。[默认左Alt开启或关闭]").withStyle(ChatFormatting.WHITE)));
                        }
                        else {
                            ClientUtils.NavigateIndex = -1;
                            Minecraft.getInstance().player.sendSystemMessage(Component.literal("[系统]").withStyle(ChatFormatting.GRAY).
                                    append(Component.literal("已关闭目的地准星定点。[默认左Alt开启或关闭]").withStyle(ChatFormatting.WHITE)));

                        }
                    }
                    else {
                        Minecraft.getInstance().player.sendSystemMessage(Component.literal("[系统]").withStyle(ChatFormatting.GRAY).
                                append(Component.literal("似乎没有任务坐标用于准星定点。").withStyle(ChatFormatting.WHITE)));

                    }
                }

                if (KeyBoradInput.SPACE.consumeClick()) {
                    if (LiulixianCurios2.flySpeed > 0) {
                        Player player = Minecraft.getInstance().player;
                        if (!player.isCreative()) {
                            if (!player.onGround()) {
                                player.getAbilities().flying = true;
                                player.getAbilities().setFlyingSpeed((float) LiulixianCurios2.flySpeed);
                            }
                            else {
                                player.getAbilities().flying = false;
                            }
                        }
                    }
                }

                if (KeyBoradInput.LiuLiXianKey.consumeClick()) {
                    ModNetworking.sendToServer(new LiuLiXianSceptreC2SPacket());
                }
                if (KeyBoradInput.LiuLiXianKey1.consumeClick()) {
                    ModNetworking.sendToServer(new LiuLiXianActiveC2SPacket());
                }
                if (KeyBoradInput.YSR.consumeClick()) {
                    ModNetworking.sendToServer(new YuanShiRenC2SPacket());
                }
            }
        }
    }
    @Mod.EventBusSubscriber(modid = Utils.MOD_ID , value = Dist.CLIENT , bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents
    {
        @SubscribeEvent
        public static void KeyBinding(RegisterKeyMappingsEvent event)
        {
            event.register(KeyBoradInput.DRUGS);
            event.register(KeyBoradInput.USE1);
            event.register(KeyBoradInput.USE2);
            event.register(KeyBoradInput.USE3);
            event.register(KeyBoradInput.USE4);
            event.register(KeyBoradInput.USE5);
            event.register(KeyBoradInput.USE6);
            event.register(KeyBoradInput.Rolling);
            event.register(KeyBoradInput.TeamGuiOpen);
            event.register(KeyBoradInput.Mission);
            event.register(KeyBoradInput.NavigateSet);
            event.register(KeyBoradInput.LiuLiXianKey);
            event.register(KeyBoradInput.LiuLiXianKey1);
        }
    }
}
