package com.very.wraq.process.system.spur.events;

import com.very.wraq.process.system.missions.series.labourDay.LabourDayMission;
import com.very.wraq.process.system.spur.Items.SpurItems;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CropSpur {

    public static void cropsInteract(net.minecraftforge.event.level.BlockEvent.BreakEvent event) throws IOException {
        if (!event.getPlayer().level().isClientSide && !event.getPlayer().isCreative()) {
            Player player = event.getPlayer();
            BlockPos blockPos = event.getPos();
            Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
            harvestCrops(player, blockPos, overWorld);
        }
    }

    public static void harvestCrops(Player player, BlockPos blockPos, Level overWorld) throws IOException {
        if (player.level().equals(overWorld)) {
            if (blockPos.getX() > 1265 && blockPos.getX() < 1614
                    && blockPos.getZ() > 1297 && blockPos.getZ() < 1545) return;
            BlockState blockState = overWorld.getBlockState(blockPos);
            if (blockState.getBlock() instanceof CropBlock cropBlock) {
                if (cropBlock.getAge(blockState) == cropBlock.getMaxAge()) {
                    if (cropsPickDrop.containsKey(blockState.getBlock())) {
                        Compute.itemStackGive(player, cropsPickDrop.get(blockState.getBlock()).getDefaultInstance());
                        cropsReward(player, blockState);

                        if (Compute.exHarvestItemGive(player, new ItemStack(cropsPickDrop.get(blockState.getBlock())), 1)) {
                            cropsReward(player, blockState);
                        }
                    }
                    BlockState blockState1 = cropBlock.getStateForAge(0);
                    overWorld.destroyBlock(blockPos, false);
                    overWorld.setBlockAndUpdate(blockPos, blockState1);
                }
            }
            if (blockState.is(Blocks.TORCHFLOWER)) {
                cropsReward(player, blockState);
                BlockState blockState1 = Blocks.TORCHFLOWER_CROP.defaultBlockState();
                overWorld.destroyBlock(blockPos, false);
                overWorld.setBlockAndUpdate(blockPos, blockState1);
                Compute.itemStackGive(player, Items.TORCHFLOWER.getDefaultInstance());

                if (Compute.exHarvestItemGive(player, new ItemStack(Items.TORCHFLOWER), 1)) {
                    cropsReward(player, blockState);
                }
            }
        }
    }

    @SubscribeEvent
    public static void sweetBerries(PlayerInteractEvent.RightClickBlock event) throws IOException {
        if (event.getSide().isServer()) {
            Player player = event.getEntity();
            BlockPos blockPos = event.getHitVec().getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);
            if (!player.isShiftKeyDown() && blockState.is(Blocks.SWEET_BERRY_BUSH) && (blockState.getValue(SweetBerryBushBlock.AGE) == SweetBerryBushBlock.MAX_AGE || blockState.getValue(SweetBerryBushBlock.AGE) == 2)) {
                cropsReward(player, blockState);
            }
        }
    }

    public static String cropPieceGetTimes = "cropPieceGetTimes";

    public static void cropsReward(Player player, BlockState blockState) throws IOException {
        CompoundTag data = player.getPersistentData();
        if (blockState.is(Blocks.WHEAT))
            data.putInt(StringUtils.Gardening.Wheat, data.getInt(StringUtils.Gardening.Wheat) + 1);
        if (blockState.is(Blocks.CARROTS))
            data.putInt(StringUtils.Gardening.Carrot, data.getInt(StringUtils.Gardening.Carrot) + 1);
        if (blockState.is(Blocks.POTATOES))
            data.putInt(StringUtils.Gardening.Potato, data.getInt(StringUtils.Gardening.Potato) + 1);
        if (blockState.is(Blocks.BEETROOTS))
            data.putInt(StringUtils.Gardening.Beet, data.getInt(StringUtils.Gardening.Beet) + 1);
        if (blockState.is(Blocks.TORCHFLOWER))
            data.putInt(StringUtils.Gardening.Torch, data.getInt(StringUtils.Gardening.Torch) + 1);
        if (blockState.is(Blocks.SWEET_BERRY_BUSH))
            data.putInt(StringUtils.Gardening.SweetBerries, data.getInt(StringUtils.Gardening.SweetBerries) + 1);

        Compute.playerGardeningExpAdd(player, 2);
        Compute.givePercentExpToPlayer(player, 0.0025, 0, Math.min(player.experienceLevel, 50));
        Utils.dayCropCount.put(player.getName().getString(), Utils.dayCropCount.getOrDefault(player.getName().getString(), 0) + 1);
        LabourDayMission.count(player, LabourDayMission.cropCounts);

        Random random = new Random();
        if (random.nextDouble() < 0.035) {
            data.putInt(cropPieceGetTimes, data.getInt(cropPieceGetTimes) + 1);
            Compute.itemStackGive(player, new ItemStack(SpurItems.cropPiece.get()));
        }
        if (Compute.exHarvestItemGive(player, new ItemStack(SpurItems.cropPiece.get()), 0.035)) {
            data.putInt(cropPieceGetTimes, data.getInt(cropPieceGetTimes) + 1);
        }
    }

    public static Map<Block, Item> cropsPickDrop = new HashMap<>() {{
        put(Blocks.WHEAT, Items.WHEAT);
        put(Blocks.CARROTS, Items.CARROT);
        put(Blocks.POTATOES, Items.POTATO);
        put(Blocks.BEETROOTS, Items.BEETROOT);
        put(Blocks.TORCHFLOWER, Items.TORCHFLOWER);
    }};

}
