package com.very.wraq.process.system.spur.events;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.BlockAndResetTime;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.process.system.missions.series.labourDay.LabourDayMission;
import com.very.wraq.process.system.spur.Items.SpurItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedList;
import java.util.Random;

public class MineSpur {

    public static void mineEvent(net.minecraftforge.event.level.BlockEvent.BreakEvent event) {
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
                Block block = blockState.getBlock();
                if (blockPos.getY() <= 11 || Utils.mineRewardMap.containsKey(block)) {
                    if (Utils.canBeDigBlockList.contains(block)) {
                        if (Utils.mineRewardMap.containsKey(block)) {
                            BlockAndResetTime blockAndResetTime = new BlockAndResetTime(blockState, blockPos, tickCount + 36000);
                            if (!Utils.posEvenBeenDigOrPlace.contains(blockPos)) {
                                Utils.worldMineList.add(blockAndResetTime);
                                Utils.posEvenBeenDigOrPlace.add(blockPos);
                            }
                            ItemAndRate.summonItemEntity(Utils.mineRewardMap.get(block).item().getDefaultInstance(),
                                    blockPos.getCenter(), level);

                            level.destroyBlock(blockPos, false);
                            mineReward(player, blockState, blockPos);

                            Random random = new Random();
                            if (random.nextDouble() < Compute.playerExHarvest(player)) {
                                Compute.sendFormatMSG(player, Component.literal("额外产出").withStyle(ChatFormatting.GOLD),
                                        Component.literal("为你提供了额外产物！").withStyle(ChatFormatting.WHITE));
                                ItemAndRate.summonItemEntity(Utils.mineRewardMap.get(block).item().getDefaultInstance(),
                                        blockPos.getCenter(), level);
                                mineReward(player, blockState, blockPos);
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
                            if ((blockState.is(Blocks.STONE) || blockState.is(Blocks.COBBLESTONE)) && InventoryOperation.itemStackCount(player.getInventory(), Items.COBBLESTONE) < 32) {
                                player.addItem(Items.COBBLESTONE.getDefaultInstance());
                            }
                            if ((blockState.is(Blocks.DEEPSLATE) || blockState.is(Blocks.COBBLED_DEEPSLATE)) && InventoryOperation.itemStackCount(player.getInventory(), Items.COBBLED_DEEPSLATE) < 32) {
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

    public static String fromMineReward = "fromMineReward";

    public static void mineReward(Player player, BlockState blockState, BlockPos blockPos) {
        double rate = Utils.mineRewardMap.get(blockState.getBlock()).exp();
        addPlayerMineExp(player, (int) (rate * 100));
        rate *= (1 + getPlayerMineLevel(player)) * 0.25;
        ItemAndRate.dropOrbs(Math.min(player.experienceLevel, 50), rate, player.level(), blockPos.getCenter(), fromMineReward);

        ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.EXPERIENCE_ORB_PICKUP), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.5f, 1, 1);
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSoundPacket);
        Utils.dayMineCount.put(player.getName().getString(), Utils.dayMineCount.getOrDefault(player.getName().getString(), 0) + 1);
        LabourDayMission.count(player, LabourDayMission.mineCounts);

        Random random = new Random();
        if (random.nextDouble() < 0.08) {
            CompoundTag tag = player.getPersistentData();
            tag.putInt(minePieceGetTimes, tag.getInt(minePieceGetTimes) + 1);
            InventoryOperation.itemStackGive(player, new ItemStack(SpurItems.minePiece.get()));
        }
        if (Compute.exHarvestItemGive(player, new ItemStack(SpurItems.minePiece.get()), 0.08))  {
            CompoundTag tag = player.getPersistentData();
            tag.putInt(minePieceGetTimes, tag.getInt(minePieceGetTimes) + 1);
        }
    }

    public static int getPlayerMineLevel(Player player) {
        CompoundTag data = player.getPersistentData();
        int MineXp = data.getInt(StringUtils.Mine.Exp);
        if (MineXp <= 100) return 1;
        else if (MineXp <= 1000) return 2;
        else if (MineXp <= 5000) return 3;
        else if (MineXp <= 20000) return 4;
        else if (MineXp <= 100000) return 5;
        return 0;
    }

    public static void addPlayerMineExp(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Mine.Exp, data.getInt(StringUtils.Mine.Exp) + Num);
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket = new ClientboundSetActionBarTextPacket(Component.literal("采矿经验").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" + ").withStyle(ChatFormatting.DARK_GREEN)).
                append(Component.literal("" + Num).withStyle(ChatFormatting.GREEN)).
                append(Component.literal(" (" + data.getInt(StringUtils.Mine.Exp) + ")").withStyle(ChatFormatting.GRAY)));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);
    }
}
