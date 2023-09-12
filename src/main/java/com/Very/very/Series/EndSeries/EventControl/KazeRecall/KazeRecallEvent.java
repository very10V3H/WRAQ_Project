package com.Very.very.Series.EndSeries.EventControl.KazeRecall;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
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

import java.util.Random;

@Mod.EventBusSubscriber
public class KazeRecallEvent {
    @SubscribeEvent
    public static void Kaze(TickEvent.LevelTickEvent event) {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "风之谷";
                Style style = Utils.styleOfKaze;
                double ZoneX = 35;
                double ZoneY = 68;
                double ZoneZ = 1418;
                double PlatformX = 0;
                double PlatformY = 158;
                double PlatformZ = 40;
                if (Utils.KazeRecall.RecallCount != -1) {
                    if (Utils.KazeRecall.KillCount == -1 && Utils.KazeRecall.RecallCount != 400 && Utils.KazeRecall.RecallCount != 380) Utils.KazeRecall.RecallCount--;
                    if (Utils.KazeRecall.RecallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始"+ZoneName+"的回忆").withStyle(style));
                        Utils.KazeRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.KazeRecall.RecallPlayer.getName().getString()+"开始回忆其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.KazeRecall.RecallCount == 540) {
                        Utils.KazeRecall.KillCount = 20;
                        Utils.KazeRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只狂风").withStyle(style));
                        Utils.KazeRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.KazeRecall.RecallCount--;
                    }
                    if (Utils.KazeRecall.KillCount == 0) {
                        Utils.KazeRecall.KillCount = -1;
                    }
                    if (Utils.KazeRecall.RecallCount == 500) {
                        Utils.KazeRecall.RecallPlayer.teleportTo(level1,PlatformX,PlatformY,PlatformZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.KazeRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.KazeRecall.RecallCount == 400) {
                        if (Utils.KazeRecallSkeleton == null || !Utils.KazeRecallSkeleton.isAlive()) {
                            if(Utils.KazeRecallSkeleton != null) Utils.KazeRecallSkeleton.remove(Entity.RemovalReason.KILLED);
                            Utils.KazeRecallSkeleton = new Skeleton(EntityType.SKELETON,level1);
                            Compute.SetMobCustomName(Utils.KazeRecallSkeleton,Moditems.ArmorKazeRecall.get(),Component.literal("模糊记忆中的狂风").withStyle(style));
                            Utils.KazeRecallSkeleton.setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorKazeRecall.get().getDefaultInstance());
                            Utils.KazeRecallSkeleton.setItemSlot(EquipmentSlot.CHEST , Moditems.ArmorKazeChest.get().getDefaultInstance());
                            Utils.KazeRecallSkeleton.setItemSlot(EquipmentSlot.LEGS , Moditems.ArmorKazeLeggings.get().getDefaultInstance());
                            Utils.KazeRecallSkeleton.setItemSlot(EquipmentSlot.FEET , Moditems.ArmorKazeBoots.get().getDefaultInstance());
                            Utils.KazeRecallSkeleton.setItemSlot(EquipmentSlot.MAINHAND , Moditems.KazeSword3.get().getDefaultInstance());
                            Utils.KazeRecallSkeleton.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.KazeRecallSkeleton.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.KazeRecallSkeleton.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.KazeRecallSkeleton.setHealth(Utils.KazeRecallSkeleton.getMaxHealth());
                            Utils.KazeRecallSkeleton.moveTo(PlatformX,PlatformY,PlatformZ);
                            level1.addFreshEntity(Utils.KazeRecallSkeleton);
                            Utils.KazeRecall.RecallCount--;
                        }
                    }
                    if (Utils.KazeRecall.RecallCount == 380) {
                        if (Utils.KazeRecall.RecallSuccess) {
                            Utils.KazeRecall.RecallCount --;
                        }
                    }
                    if (Utils.KazeRecall.RecallCount == 340) {
                        if (Utils.KazeRecall.RecallSuccess) {
                            CompoundTag data = Utils.KazeRecall.RecallPlayer.getPersistentData();
                            String RecallTimes = "KazeRecallTimes";
                            String RecallSuccessTimes = "KazeRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime,20)) {
                                data.putInt(RecallTimes,0);
                                data.putInt(RecallSuccessTimes,data.getInt(RecallSuccessTimes) + 1);
                                Utils.KazeRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.KazeRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("成功").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).
                                                append(Component.literal("回想起了在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的所经所厉。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("这是Ta第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.KazeRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.KazeRecallSoul.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.KazeRecall.RecallPlayer,itemStack);
                            }
                            else {
                                data.putInt(RecallTimes,data.getInt(RecallTimes) + 1);
                                Utils.KazeRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.KazeRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.KazeRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.KazeRune.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.KazeRecall.RecallPlayer,itemStack);
                            }
                        }
                    }
                    if (Utils.KazeRecall.RecallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.KazeRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.KazeRecall.Reset();
                    }
                }
                if (Utils.KazeRecallSkeleton != null && Utils.KazeRecallSkeleton.isAlive()) {
                    if (level1.getServer().getTickCount() % 200 == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("狂风即将袭来！").withStyle(style));
                        Utils.KazeRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (level1.getServer().getTickCount() % 200 == 40) {
                        if (Utils.KazeRecall.RecallPlayer.onGround()) {
                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                    new ClientboundSetEntityMotionPacket(Utils.KazeRecall.RecallPlayer.getId(),new Vec3(0,2,0));
                            Utils.KazeRecall.RecallPlayer.connection.send(clientboundSetEntityMotionPacket);
                        }
                    }
                    if (level1.getServer().getTickCount() % 200 == 60) {
                        if (!Utils.KazeRecall.RecallPlayer.onGround()) {
                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                    new ClientboundSetEntityMotionPacket(Utils.KazeRecall.RecallPlayer.getId(),new Vec3(0,-2,0));
                            Utils.KazeRecall.RecallPlayer.connection.send(clientboundSetEntityMotionPacket);
                            Utils.KazeRecall.RecallPlayer.setHealth(Utils.KazeRecall.RecallPlayer.getHealth() * 0.5f);
                        }
                    }
                }
                if (Utils.KazeRecall.RecallTimeLimit > 0) Utils.KazeRecall.RecallTimeLimit--;
                else {
                    if (Utils.KazeRecall.RecallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.KazeRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.KazeRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        Utils.KazeRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                        if (Utils.KazeRecallSkeleton != null) Utils.KazeRecallSkeleton.remove(Entity.RemovalReason.KILLED);
                        Utils.KazeRecall.Reset();
                    }
                }
                if (Utils.KazeRecall.RecallTimeLimit > 0 && Utils.KazeRecall.RecallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.KazeRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                            Component.literal(Utils.KazeRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    Utils.KazeRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                    if (Utils.KazeRecallSkeleton != null) Utils.KazeRecallSkeleton.remove(Entity.RemovalReason.KILLED);
                    Utils.KazeRecall.Reset();
                }
            }
        }
    }
}
