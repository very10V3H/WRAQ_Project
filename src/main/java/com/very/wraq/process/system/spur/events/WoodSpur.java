package com.very.wraq.process.system.spur.events;

import com.very.wraq.process.system.missions.series.labourDay.LabourDayMission;
import com.very.wraq.process.system.spur.Items.SpurItems;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.BlockAndResetTime;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.util.Random;

public class WoodSpur {
    public static void woodEvent(net.minecraftforge.event.level.BlockEvent.BreakEvent event) throws IOException {
        if (!event.getPlayer().level().isClientSide && !event.getPlayer().isCreative()) {
            Player player = event.getPlayer();
            BlockPos blockPos = event.getPos();
            Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
            Level level = player.level();
            BlockState blockState = level.getBlockState(blockPos);
            if (level.equals(overWorld)) {
                Block block = blockState.getBlock();
                if (block.toString().contains("log") && !block.toString().contains("stripped")) {
                    boolean hasLeafOnRoof = false;
                    BlockPos blockPos1 = blockPos.above();
                    while (!level.getBlockState(blockPos1).is(Blocks.AIR)) {
                        Block block1 = level.getBlockState(blockPos1).getBlock();
                        if (block1.toString().contains("leaves")) {
                            hasLeafOnRoof = true;
                            break;
                        }
                        blockPos1 = blockPos1.above();
                        if (level.getBlockState(blockPos1).is(Blocks.AIR)) {
                            BlockPos tmp = new BlockPos(blockPos1.getX(), blockPos1.getY(), blockPos1.getZ());
                            for (int i = 0; i < 3; i++) {
                                tmp = tmp.above();
                                if (level.getBlockState(tmp).getBlock().toString().contains("leaves")) {
                                    hasLeafOnRoof = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (hasLeafOnRoof) {
                        do {
                            blockPos1 = blockPos1.below();
                        } while (level.getBlockState(blockPos1).getBlock().toString().contains("stripped"));

                        logReward(player);
                        Compute.itemStackGive(player, new ItemStack(blockState.getBlock().asItem(), 2));
                        Utils.worldWoodList.add(new BlockAndResetTime(blockState, blockPos1, level.getServer().getTickCount() + 36000));

                        level.setBlockAndUpdate(blockPos1, getStrippedLog(level.getBlockState(blockPos1).getBlock()).defaultBlockState());

                        Random random = new Random();
                        if (random.nextDouble() < Compute.playerExHarvest(player)) {
                            Compute.sendFormatMSG(player, Component.literal("额外产出").withStyle(ChatFormatting.GOLD),
                                    Component.literal("为你提供了额外产物！").withStyle(ChatFormatting.WHITE));
                            logReward(player);
                            Compute.itemStackGive(player, new ItemStack(blockState.getBlock().asItem(), 2));
                        }
                    }
                }
            }
        }
    }

    public static String logPieceGetTimes = "logPieceGetTimes";

    public static void logReward(Player player) throws IOException {
        CompoundTag data = player.getPersistentData();
        Compute.playerLopExpAdd(player, 2);
        Compute.givePercentExpToPlayer(player, 0.005, 0, Math.min(player.experienceLevel, 50));

        Utils.dayLopCount.put(player.getName().getString(), Utils.dayLopCount.getOrDefault(player.getName().getString(), 0) + 1);

        if (data.contains(StringUtils.Lop.Xp) && !data.contains(StringUtils.LogReward)) {
            Compute.itemStackGive(player, new ItemStack(ModItems.LogBag.get(), data.getInt(StringUtils.Lop.Xp) / 256));
            data.putBoolean(StringUtils.LogReward, true);
        }

        LabourDayMission.count(player, LabourDayMission.lopCounts);

        Random random = new Random();
        if (random.nextDouble() < 0.05) {
            data.putInt(logPieceGetTimes, data.getInt(logPieceGetTimes) + 1);
            Compute.itemStackGive(player, new ItemStack(SpurItems.logPiece.get()));
        }
        if (Compute.exHarvestItemGive(player, new ItemStack(SpurItems.logPiece.get()), 0.05)) {
            data.putInt(logPieceGetTimes, data.getInt(logPieceGetTimes) + 1);
        }
    }

    public static Block getStrippedLog(Block block) {
        String s = block.toString();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '{') {
                s = s.substring(i + 1, s.length() - 1);
                break;
            }
        }
        String namespace = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ':') {
                namespace = s.substring(0, i + 1) + "stripped_" + s.substring(i + 1);
                break;
            }
        }
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(namespace));
    }

}
