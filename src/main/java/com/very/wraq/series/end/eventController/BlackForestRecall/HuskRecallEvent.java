package com.very.wraq.series.end.eventController.BlackForestRecall;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.Utils;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.chapter2.HuskSpawnController;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ParticlePackets.BlackForestRecallParticleS2CPacket;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter2.dimension.ToEnd;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.Random;

@Mod.EventBusSubscriber
public class HuskRecallEvent {
    @SubscribeEvent
    public static void Kaze(TickEvent.LevelTickEvent event) throws IOException {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "沙岸";
                Style style = CustomStyle.styleOfHusk;
                double ZoneX = 808.5;
                double ZoneY = 67;
                double ZoneZ = 367.5;
                double PlatformX = 40;
                double PlatformY = 159;
                double PlatformZ = 0;

                if (Utils.huskRecall.recallCount != -1) {
                    if (Utils.huskRecall.recallPlayer == null) {
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(Utils.huskRecall.playerName).
                                        append(Component.literal("尝试回想其在").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        if (Utils.HuskRecallHusk != null) Utils.HuskRecallHusk.remove(Entity.RemovalReason.KILLED);
                        Utils.huskRecall.Reset();
                        return;
                    }

                    if (Utils.huskRecall.killCount == -1 && Utils.huskRecall.recallCount != 400 && Utils.huskRecall.recallCount != 380)
                        Utils.huskRecall.recallCount--;
                    if (Utils.huskRecall.recallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始" + ZoneName + "的回忆").withStyle(style));
                        Utils.huskRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.huskRecall.recallPlayer.getName().getString() + "开始回忆其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.huskRecall.recallCount == 540) {
                        Utils.huskRecall.killCount = 20;
                        Utils.huskRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只" + HuskSpawnController.mobName).withStyle(style));
                        Utils.huskRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.huskRecall.recallCount--;
                    }
                    if (Utils.huskRecall.killCount == 0) {
                        Utils.huskRecall.killCount = -1;
                    }
                    if (Utils.huskRecall.recallCount == 500) {
                        Utils.huskRecall.recallPlayer.teleportTo(level1, PlatformX, PlatformY, PlatformZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.huskRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.huskRecall.recallCount == 400) {
                        if (Utils.HuskRecallHusk == null || !Utils.HuskRecallHusk.isAlive()) {
                            if (Utils.HuskRecallHusk != null) Utils.HuskRecallHusk.remove(Entity.RemovalReason.KILLED);
                            Utils.HuskRecallHusk = new Husk(EntityType.HUSK, level1);
                            MobSpawn.setMobCustomName(Utils.HuskRecallHusk, ModItems.ArmorHuskRecall.get(), Component.literal("模糊记忆中的脆弱灵魂").withStyle(style));
                            Utils.HuskRecallHusk.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorHuskRecall.get().getDefaultInstance());
                            Utils.HuskRecallHusk.setItemSlot(EquipmentSlot.MAINHAND, ModItems.BlackForestSword4.get().getDefaultInstance());
                            Utils.HuskRecallHusk.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.HuskRecallHusk.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.HuskRecallHusk.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.HuskRecallHusk.setHealth(Utils.HuskRecallHusk.getMaxHealth());
                            Utils.HuskRecallHusk.getPersistentData().putBoolean("Half", false);
                            Utils.HuskRecallHusk.moveTo(PlatformX, PlatformY, PlatformZ);
                            level1.addFreshEntity(Utils.HuskRecallHusk);
                            Utils.huskRecall.recallCount--;
                            Utils.huskRecall.mob = Utils.HuskRecallHusk;
                        }
                    }
                    if (Utils.huskRecall.recallCount == 380) {
                        if (Utils.huskRecall.recallSuccess) {
                            Utils.huskRecall.recallCount--;
                        }
                    }
                    if (Utils.huskRecall.recallCount == 340) {
                        if (Utils.huskRecall.recallSuccess) {
                            CompoundTag data = Utils.huskRecall.recallPlayer.getPersistentData();
                            String RecallTimes = "HuskRecallTimes";
                            String RecallSuccessTimes = "HuskRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime, 20)) {
                                data.putInt(RecallTimes, 0);
                                data.putInt(RecallSuccessTimes, data.getInt(RecallSuccessTimes) + 1);
                                Utils.huskRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.huskRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("成功").withStyle(ChatFormatting.GREEN)).
                                                append(Component.literal("回想起了在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的所经所厉。").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("这是Ta第").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("次回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.huskRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("次回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.BlackForestRecallSoul.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.itemStackGive(Utils.huskRecall.recallPlayer, itemStack);
                            } else {
                                data.putInt(RecallTimes, data.getInt(RecallTimes) + 1);
                                ToEnd.toEndSpawnPos(Utils.huskRecall.recallPlayer);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.huskRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.huskRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.huskRune.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.itemStackGive(Utils.huskRecall.recallPlayer, itemStack);
                            }
                        }
                    }
                    if (Utils.huskRecall.recallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.huskRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.huskRecall.Reset();
                    }
                }

                if (Utils.HuskRecallHusk != null && Utils.HuskRecallHusk.isAlive()) { // AI
                    if (!Utils.HuskRecallHusk.getPersistentData().getBoolean("Half") && Utils.HuskRecallHusk.getHealth() <= Utils.HuskRecallHusk.getMaxHealth() * 0.25f) {
                        Utils.HuskRecallHusk.getPersistentData().putBoolean("Half", true);
                        Utils.HuskRecallHusk.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(400.0d);
                        Utils.HuskRecallHusk.heal(Utils.huskRecall.recallPlayer.getMaxHealth() * 10);
                        ModNetworking.sendToClient(new BlackForestRecallParticleS2CPacket(true), Utils.huskRecall.recallPlayer);
                    }
                }

                if (Utils.huskRecall.recallTimeLimit > 0) Utils.huskRecall.recallTimeLimit--;
                else {
                    if (Utils.huskRecall.recallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.huskRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.huskRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        ToEnd.toEndSpawnPos(Utils.huskRecall.recallPlayer);
                        if (Utils.HuskRecallHusk != null) Utils.HuskRecallHusk.remove(Entity.RemovalReason.KILLED);
                        Utils.huskRecall.Reset();
                    }
                }
                if (Utils.huskRecall.recallTimeLimit > 0 && Utils.huskRecall.recallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.huskRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                            Component.literal(Utils.huskRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                    ToEnd.toEndSpawnPos(Utils.huskRecall.recallPlayer);
                    if (Utils.HuskRecallHusk != null) Utils.HuskRecallHusk.remove(Entity.RemovalReason.KILLED);
                    Utils.huskRecall.Reset();
                }
            }
        }
    }
}
