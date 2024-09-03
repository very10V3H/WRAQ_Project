package com.very.wraq.process.system.spur.events;

import com.very.wraq.process.system.missions.series.labourDay.LabourDayMission;
import com.very.wraq.process.system.spur.Items.SpurItems;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.struct.BlockAndResetTime;
import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class MineSpur {

    public static void mineEvent(net.minecraftforge.event.level.BlockEvent.BreakEvent event) throws IOException {
        Player player = event.getPlayer();
        BlockPos blockPos = event.getPos();
        BlockState blockState = event.getState();
        Level level = player.level();
        Level OverWorld = player.getServer().getLevel(Level.OVERWORLD);
        if (!player.isCreative() && blockState.getBlock().toString().length() > 12
                && blockState.getBlock().toString().startsWith("create", 6)) event.setCanceled(true);

        if (!player.isCreative()) {
            if (!level.isClientSide && level.equals(OverWorld)) {
                int tickCount = level.getServer().getTickCount();
                if (blockPos.getY() <= 11) {
                    if (Utils.canBeDigBlockList.contains(blockState.getBlock())) {
                        if (Utils.mineExpMap.containsKey(blockState.getBlock())) {
                            BlockAndResetTime blockAndResetTime = new BlockAndResetTime(blockState, blockPos, tickCount + 36000);
                            if (!Utils.posEvenBeenDigOrPlace.contains(blockPos)) {
                                Utils.worldMineList.add(blockAndResetTime);
                                Utils.posEvenBeenDigOrPlace.add(blockPos);
                            }
                            Compute.itemStackGive(player, Utils.mineDropMap.get(blockState.getBlock()).getDefaultInstance());
                            level.destroyBlock(blockPos, false);
                            mineReward(player, blockState);

                            Random random = new Random();
                            if (random.nextDouble() < Compute.playerExHarvest(player)) {
                                Compute.sendFormatMSG(player, Component.literal("额外产出").withStyle(ChatFormatting.GOLD),
                                        Component.literal("为你提供了额外产物！").withStyle(ChatFormatting.WHITE));
                                Compute.itemStackGive(player, Utils.mineDropMap.get(blockState.getBlock()).getDefaultInstance());
                                mineReward(player, blockState);
                            }
                        } else {
                            BlockAndResetTime blockAndResetTime = new BlockAndResetTime(blockState, blockPos, tickCount + 1200);
                            String name = player.getName().getString();
                            if (!Utils.noMineDigMap.containsKey(name))
                                Utils.noMineDigMap.put(name, new LinkedList<>());
                            if (!Utils.posEvenBeenDigOrPlace.contains(blockPos)) {
                                Utils.noMineDigMap.get(name).add(blockAndResetTime);
                                Utils.posEvenBeenDigOrPlace.add(blockPos);
                            }
                            if ((blockState.is(Blocks.STONE) || blockState.is(Blocks.COBBLESTONE)) && Compute.itemStackCount(player.getInventory(), Items.COBBLESTONE) < 32) {
                                player.addItem(Items.COBBLESTONE.getDefaultInstance());
                            }
                            if ((blockState.is(Blocks.DEEPSLATE) || blockState.is(Blocks.COBBLED_DEEPSLATE)) && Compute.itemStackCount(player.getInventory(), Items.COBBLED_DEEPSLATE) < 32) {
                                player.addItem(Items.COBBLED_DEEPSLATE.getDefaultInstance());
                            }
                            if (blockState.is(Blocks.ANDESITE)) player.addItem(Items.ANDESITE.getDefaultInstance());
                            if (blockState.is(Blocks.DIORITE)) player.addItem(Items.DIORITE.getDefaultInstance());
                            level.destroyBlock(blockPos, false);
                            if (Utils.noMineDigMap.get(name).size() > 80) {
                                BlockAndResetTime blockAndResetTime1 = Utils.noMineDigMap.get(name).poll();
                                level.setBlockAndUpdate(blockAndResetTime1.getBlockPos(), blockAndResetTime1.getBlockState());
                                Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime1.getBlockPos());
                            }
                        }
                    }
                }
            }
        }
    }

    public static String minePieceGetTimes = "minePieceGetTimes";

    public static void mineReward(Player player, BlockState blockState) throws IOException {
        double baseExpRate = Utils.mineExpMap.get(blockState.getBlock());
        Compute.playerMineExpAdd(player, (int) (baseExpRate * 100));
        baseExpRate *= (1 + Compute.playerMineLevel(player)) * 0.25;
        Compute.givePercentExpToPlayer(player, baseExpRate, 0, Math.min(player.experienceLevel, 50));
        ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.EXPERIENCE_ORB_PICKUP), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.5f, 1, 1);
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSoundPacket);
        Utils.dayMineCount.put(player.getName().getString(), Utils.dayMineCount.getOrDefault(player.getName().getString(), 0) + 1);
        LabourDayMission.count(player, LabourDayMission.mineCounts);

        Random random = new Random();
        if (random.nextDouble() < 0.08) {
            CompoundTag tag = player.getPersistentData();
            tag.putInt(minePieceGetTimes, tag.getInt(minePieceGetTimes) + 1);
            Compute.itemStackGive(player, new ItemStack(SpurItems.minePiece.get()));
        }
        if (Compute.exHarvestItemGive(player, new ItemStack(SpurItems.minePiece.get()), 0.08))  {
            CompoundTag tag = player.getPersistentData();
            tag.putInt(minePieceGetTimes, tag.getInt(minePieceGetTimes) + 1);
        }
    }
}
