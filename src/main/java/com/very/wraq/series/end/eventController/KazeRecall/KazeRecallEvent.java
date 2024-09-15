package com.very.wraq.series.end.eventController.KazeRecall;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.Utils;
import com.very.wraq.events.mob.chapter2.WindSkeletonSpawnController;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter2.dimension.ToEnd;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.Random;

@Mod.EventBusSubscriber
public class KazeRecallEvent {
    @SubscribeEvent
    public static void Kaze(TickEvent.LevelTickEvent event) throws IOException {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "怀德半岛";
                Style style = CustomStyle.styleOfKaze;
                double ZoneX = 644;
                double ZoneY = 70;
                double ZoneZ = 412;
                double PlatformX = 0;
                double PlatformY = 159;
                double PlatformZ = 40;
                if (Utils.kazeRecall.recallCount != -1) {
                    if (Utils.kazeRecall.recallPlayer == null) {
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(Utils.kazeRecall.playerName).
                                        append(Component.literal("尝试回想其在").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        if (Utils.KazeRecallSkeleton != null)
                            Utils.KazeRecallSkeleton.remove(Entity.RemovalReason.KILLED);

                        Utils.kazeRecall.Reset();
                        return;
                    }
                    if (Utils.kazeRecall.killCount == -1 && Utils.kazeRecall.recallCount != 400 && Utils.kazeRecall.recallCount != 380)
                        Utils.kazeRecall.recallCount--;
                    if (Utils.kazeRecall.recallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始" + ZoneName + "的回忆").withStyle(style));
                        Utils.kazeRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.kazeRecall.recallPlayer.getName().getString() + "开始回忆其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.kazeRecall.recallCount == 540) {
                        Utils.kazeRecall.killCount = 20;
                        Utils.kazeRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只" + WindSkeletonSpawnController.mobName).withStyle(style));
                        Utils.kazeRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.kazeRecall.recallCount--;
                    }
                    if (Utils.kazeRecall.killCount == 0) {
                        Utils.kazeRecall.killCount = -1;
                    }
                    if (Utils.kazeRecall.recallCount == 500) {
                        Utils.kazeRecall.recallPlayer.teleportTo(level1, PlatformX, PlatformY, PlatformZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.kazeRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.kazeRecall.recallCount == 400) {
                        if (Utils.KazeRecallSkeleton == null || !Utils.KazeRecallSkeleton.isAlive()) {
                            if (Utils.KazeRecallSkeleton != null)
                                Utils.KazeRecallSkeleton.remove(Entity.RemovalReason.KILLED);
                            Utils.KazeRecallSkeleton = new Skeleton(EntityType.SKELETON, level1);
                            Compute.setMobCustomName(Utils.KazeRecallSkeleton, ModItems.ArmorKazeRecall.get(), Component.literal("模糊记忆中的狂风").withStyle(style));
                            Utils.KazeRecallSkeleton.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorKazeRecall.get().getDefaultInstance());
                            Utils.KazeRecallSkeleton.setItemSlot(EquipmentSlot.CHEST, ModItems.ArmorKazeChest.get().getDefaultInstance());
                            Utils.KazeRecallSkeleton.setItemSlot(EquipmentSlot.LEGS, ModItems.ArmorKazeLeggings.get().getDefaultInstance());
                            Utils.KazeRecallSkeleton.setItemSlot(EquipmentSlot.FEET, ModItems.ArmorKazeBoots.get().getDefaultInstance());
                            Utils.KazeRecallSkeleton.setItemSlot(EquipmentSlot.MAINHAND, ModItems.KazeSword3.get().getDefaultInstance());
                            Utils.KazeRecallSkeleton.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.KazeRecallSkeleton.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.KazeRecallSkeleton.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.KazeRecallSkeleton.setHealth(Utils.KazeRecallSkeleton.getMaxHealth());
                            Utils.KazeRecallSkeleton.moveTo(PlatformX, PlatformY, PlatformZ);
                            level1.addFreshEntity(Utils.KazeRecallSkeleton);
                            Utils.kazeRecall.recallCount--;
                            Utils.kazeRecall.mob = Utils.KazeRecallSkeleton;
                        }
                    }
                    if (Utils.kazeRecall.recallCount == 380) {
                        if (Utils.kazeRecall.recallSuccess) {
                            Utils.kazeRecall.recallCount--;
                        }
                    }
                    if (Utils.kazeRecall.recallCount == 340) {
                        if (Utils.kazeRecall.recallSuccess) {
                            CompoundTag data = Utils.kazeRecall.recallPlayer.getPersistentData();
                            String RecallTimes = "KazeRecallTimes";
                            String RecallSuccessTimes = "KazeRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime, 20)) {
                                data.putInt(RecallTimes, 0);
                                data.putInt(RecallSuccessTimes, data.getInt(RecallSuccessTimes) + 1);
                                Utils.kazeRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.kazeRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("成功").withStyle(ChatFormatting.GREEN)).
                                                append(Component.literal("回想起了在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的所经所厉。").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("这是Ta第").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.kazeRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.KazeRecallSoul.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.itemStackGive(Utils.kazeRecall.recallPlayer, itemStack);
                            } else {
                                data.putInt(RecallTimes, data.getInt(RecallTimes) + 1);
                                ToEnd.toEndSpawnPos(Utils.kazeRecall.recallPlayer);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.kazeRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.kazeRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.KazeRune.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.itemStackGive(Utils.kazeRecall.recallPlayer, itemStack);
                            }
                        }
                    }
                    if (Utils.kazeRecall.recallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.kazeRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.kazeRecall.Reset();
                    }
                }
                if (Utils.KazeRecallSkeleton != null && Utils.KazeRecallSkeleton.isAlive()) {
                    if (level1.getServer().getTickCount() % 200 == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("狂风即将袭来！").withStyle(style));
                        Utils.kazeRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (level1.getServer().getTickCount() % 200 == 40) {
                        if (Utils.kazeRecall.recallPlayer.onGround()) {
                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                    new ClientboundSetEntityMotionPacket(Utils.kazeRecall.recallPlayer.getId(), new Vec3(0, 2, 0));
                            Utils.kazeRecall.recallPlayer.connection.send(clientboundSetEntityMotionPacket);
                        }
                    }
                    if (level1.getServer().getTickCount() % 200 == 60) {
                        if (!Utils.kazeRecall.recallPlayer.onGround()) {
                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                    new ClientboundSetEntityMotionPacket(Utils.kazeRecall.recallPlayer.getId(), new Vec3(0, -2, 0));
                            Utils.kazeRecall.recallPlayer.connection.send(clientboundSetEntityMotionPacket);
                            Utils.kazeRecall.recallPlayer.setHealth(Utils.kazeRecall.recallPlayer.getHealth() * 0.5f);
                        }
                    }
                }
                if (Utils.kazeRecall.recallTimeLimit > 0) Utils.kazeRecall.recallTimeLimit--;
                else {
                    if (Utils.kazeRecall.recallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.kazeRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.kazeRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        ToEnd.toEndSpawnPos(Utils.kazeRecall.recallPlayer);
                        if (Utils.KazeRecallSkeleton != null)
                            Utils.KazeRecallSkeleton.remove(Entity.RemovalReason.KILLED);
                        Utils.kazeRecall.Reset();
                    }
                }
                if (Utils.kazeRecall.recallTimeLimit > 0 && Utils.kazeRecall.recallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.kazeRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                            Component.literal(Utils.kazeRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                    ToEnd.toEndSpawnPos(Utils.kazeRecall.recallPlayer);
                    if (Utils.KazeRecallSkeleton != null) Utils.KazeRecallSkeleton.remove(Entity.RemovalReason.KILLED);
                    Utils.kazeRecall.Reset();
                }
            }
        }
    }
}
