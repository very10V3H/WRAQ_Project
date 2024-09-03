package com.very.wraq.series.end.eventController.SnowRecall;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter2.dimension.ToEnd;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.Random;

@Mod.EventBusSubscriber
public class SnowRecallEvent {
    @SubscribeEvent
    public static void Lightning(TickEvent.LevelTickEvent event) throws IOException {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "冰川";
                Style style = CustomStyle.styleOfSnow;
                double ZoneX = -76;
                double ZoneY = 198;
                double ZoneZ = 1368;
                double PlatformX = -20;
                double PlatformY = 159;
                double PlatformZ = -20;
                if (Utils.snowRecall.recallCount != -1) {
                    if (Utils.snowRecall.recallPlayer == null) {
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(Utils.snowRecall.playerName).
                                        append(Component.literal("尝试回想其在").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        if (Utils.SnowRecallStray != null) Utils.SnowRecallStray.remove(Entity.RemovalReason.KILLED);

                        Utils.snowRecall.Reset();
                        return;
                    }
                    if (Utils.snowRecall.killCount == -1 && Utils.snowRecall.recallCount != 400 && Utils.snowRecall.recallCount != 380)
                        Utils.snowRecall.recallCount--;
                    if (Utils.snowRecall.recallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始" + ZoneName + "的回忆").withStyle(style));
                        Utils.snowRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.snowRecall.recallPlayer.getName().getString() + "开始回忆其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.snowRecall.recallCount == 540) {
                        Utils.snowRecall.killCount = 20;
                        Utils.snowRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只").withStyle(style));
                        Utils.snowRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.snowRecall.recallCount--;
                    }
                    if (Utils.snowRecall.killCount == 0) {
                        Utils.snowRecall.killCount = -1;
                    }
                    if (Utils.snowRecall.recallCount == 500) {
                        Utils.snowRecall.recallPlayer.teleportTo(level1, PlatformX, PlatformY, PlatformZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.snowRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.snowRecall.recallCount == 400) {
                        if (Utils.SnowRecallStray == null || !Utils.SnowRecallStray.isAlive()) {
                            if (Utils.SnowRecallStray != null)
                                Utils.SnowRecallStray.remove(Entity.RemovalReason.KILLED);
                            Utils.SnowRecallStray = new Stray(EntityType.STRAY, level1);
                            Compute.SetMobCustomName(Utils.SnowRecallStray, ModItems.ArmorSnowRecall.get(), Component.literal("模糊记忆中的冰川流浪者").withStyle(style));
                            Utils.SnowRecallStray.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorSnowRecall.get().getDefaultInstance());
                            Utils.SnowRecallStray.setItemSlot(EquipmentSlot.CHEST, ModItems.ArmorLZChest.get().getDefaultInstance());
                            Utils.SnowRecallStray.setItemSlot(EquipmentSlot.LEGS, ModItems.ArmorLZLeggings.get().getDefaultInstance());
                            Utils.SnowRecallStray.setItemSlot(EquipmentSlot.FEET, ModItems.ArmorLZBoots.get().getDefaultInstance());
                            Utils.SnowRecallStray.setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());
                            Utils.SnowRecallStray.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.SnowRecallStray.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(800.0D);
                            Utils.SnowRecallStray.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.SnowRecallStray.setHealth(Utils.SnowRecallStray.getMaxHealth());
                            Utils.SnowRecallStray.moveTo(PlatformX, PlatformY, PlatformZ);
                            Utils.SnowRecallStray.getPersistentData().putBoolean("Create", true);
                            level1.addFreshEntity(Utils.SnowRecallStray);
                            Utils.snowRecall.recallCount--;
                            Utils.snowRecall.mob = Utils.SnowRecallStray;
                        }
                    }
                    if (Utils.snowRecall.recallCount == 380) {
                        if (Utils.snowRecall.recallSuccess) {
                            Utils.snowRecall.recallCount--;
                        }
                    }
                    if (Utils.snowRecall.recallCount == 340) {
                        if (Utils.snowRecall.recallSuccess) {
                            CompoundTag data = Utils.snowRecall.recallPlayer.getPersistentData();
                            String RecallTimes = "SnowRecallTimes";
                            String RecallSuccessTimes = "SnowRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime, 20)) {
                                data.putInt(RecallTimes, 0);
                                data.putInt(RecallSuccessTimes, data.getInt(RecallSuccessTimes) + 1);
                                Utils.snowRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.snowRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
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
                                Compute.sendFormatMSG(Utils.snowRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.SnowRecallSoul.get().getDefaultInstance();
                                itemStack.setCount(1);
                                Compute.itemStackGive(Utils.snowRecall.recallPlayer, itemStack);
                                data.putInt(StringUtils.RecallEndRune3, 3);
                            } else {
                                data.putInt(RecallTimes, data.getInt(RecallTimes) + 1);
                                ToEnd.toEndSpawnPos(Utils.snowRecall.recallPlayer);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.snowRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.snowRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.SnowRune.get().getDefaultInstance();
                                itemStack.setCount(1);
                                Compute.itemStackGive(Utils.snowRecall.recallPlayer, itemStack);
                                data.putInt(StringUtils.RecallEndRune3, 3);
                            }
                        }
                    }
                    if (Utils.snowRecall.recallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.snowRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.snowRecall.Reset();
                        int X = -20;
                        int Y = 158;
                        int Z = -11;
                        for (int i = 0; i < 19; i++) {
                            for (int j = 0; j <= Math.min(i, 18 - i); j++) {
                                BlockPos blockPos = new BlockPos(X - j, Y, Z - i);
                                BlockPos blockPos1 = new BlockPos(X + j, Y, Z - i);
                                level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                                level.setBlockAndUpdate(blockPos1, Blocks.AIR.defaultBlockState());
                            }
                        }
                    }
                }
                if (Utils.SnowRecallStray != null && Utils.SnowRecallStray.isAlive()) {
                    if (Utils.SnowRecallStray.getPersistentData().getBoolean("Create") && Utils.SnowRecallStray.getHealth() <= Utils.SnowRecallStray.getMaxHealth() * 0.5f) {
                        int X = -20;
                        int Y = 158;
                        int Z = -11;
                        for (int i = 0; i < 19; i++) {
                            for (int j = 0; j <= Math.min(i, 18 - i); j++) {
                                BlockPos blockPos = new BlockPos(X - j, Y, Z - i);
                                BlockPos blockPos1 = new BlockPos(X + j, Y, Z - i);
                                level.setBlockAndUpdate(blockPos, Blocks.POWDER_SNOW.defaultBlockState());
                                level.setBlockAndUpdate(blockPos1, Blocks.POWDER_SNOW.defaultBlockState());
                            }
                        }
                        Utils.SnowRecallStray.getPersistentData().putBoolean("Create", false);
                    }
                    if (level1.getServer().getTickCount() % 200 == 0) {
                        Utils.snowRecall.recallPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
                        int X = Utils.snowRecall.recallPlayer.getBlockX();
                        int Y = Utils.snowRecall.recallPlayer.getBlockY() + 1;
                        int Z = Utils.snowRecall.recallPlayer.getBlockZ();
                        BlockPos blockPos = new BlockPos(X, Y, Z);
                        if (level1.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
                            level1.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                            level1.destroyBlock(blockPos, false);
                        }
                    }
                }
                if (Utils.snowRecall.recallTimeLimit > 0) Utils.snowRecall.recallTimeLimit--;
                else {
                    if (Utils.snowRecall.recallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.snowRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.snowRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        ToEnd.toEndSpawnPos(Utils.snowRecall.recallPlayer);
                        if (Utils.SnowRecallStray != null) Utils.SnowRecallStray.remove(Entity.RemovalReason.KILLED);
                        Utils.snowRecall.Reset();
                        int X = -20;
                        int Y = 158;
                        int Z = -11;
                        for (int i = 0; i < 19; i++) {
                            for (int j = 0; j <= Math.min(i, 18 - i); j++) {
                                BlockPos blockPos = new BlockPos(X - j, Y, Z - i);
                                BlockPos blockPos1 = new BlockPos(X + j, Y, Z - i);
                                level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                                level.setBlockAndUpdate(blockPos1, Blocks.AIR.defaultBlockState());
                            }
                        }
                    }
                }
                if (Utils.snowRecall.recallTimeLimit > 0 && Utils.snowRecall.recallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.snowRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                            Component.literal(Utils.snowRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                    ToEnd.toEndSpawnPos(Utils.snowRecall.recallPlayer);
                    if (Utils.SnowRecallStray != null) Utils.SnowRecallStray.remove(Entity.RemovalReason.KILLED);
                    Utils.snowRecall.Reset();
                    int X = -20;
                    int Y = 158;
                    int Z = -11;
                    for (int i = 0; i < 19; i++) {
                        for (int j = 0; j <= Math.min(i, 18 - i); j++) {
                            BlockPos blockPos = new BlockPos(X - j, Y, Z - i);
                            BlockPos blockPos1 = new BlockPos(X + j, Y, Z - i);
                            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                            level.setBlockAndUpdate(blockPos1, Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }
        }
    }
}
