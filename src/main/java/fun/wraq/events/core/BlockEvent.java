package fun.wraq.events.core;

import com.mojang.logging.LogUtils;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import fun.wraq.blocks.blocks.WorldSoulBlock;
import fun.wraq.blocks.entity.Droppable;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModBlocks;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.BlockAndResetTime;
import fun.wraq.events.mob.instance.instances.element.WardenInstance;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.Limit.CheckBlockLimitS2CPacket;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.networking.unSorted.BlockLimit;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.bonuschest.BonusChestInfo;
import fun.wraq.process.system.bonuschest.BonusChestPlayerData;
import fun.wraq.process.system.cooking.CookingItems;
import fun.wraq.process.system.estate.EstateUtil;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.forge.ForgeHammer;
import fun.wraq.process.system.profession.smith.SmithHammer;
import fun.wraq.process.system.randomevent.RandomEventsHandler;
import fun.wraq.process.system.smelt.Smelt;
import fun.wraq.process.system.spur.events.CropSpur;
import fun.wraq.process.system.spur.events.MineSpur;
import fun.wraq.process.system.spur.events.WoodSpur;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotMenu;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Mod.EventBusSubscriber
public class BlockEvent {
    @SubscribeEvent
    public static void setMineSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        double mineSpeed = PlayerAttributes.getMineSpeedEnhanceRate(player);
        event.setNewSpeed((float) (1 + mineSpeed));
    }

    @SubscribeEvent
    public static void lampAndSignCanceller(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = player.level();
        BlockPos blockPos = event.getHitVec().getBlockPos();
        Block block = level.getBlockState(blockPos).getBlock();
        if (!player.isCreative() &&
                (block.toString().contains("lamp"))) {
            event.setCanceled(true);
        }
        if (!player.isCreative() && block instanceof SignBlock) {
            if (!EstateUtil.canEditSingBlock(player, blockPos)) {
                event.setCanceled(true);
            }
        }
        // 房产右键判断
        if (!level.isClientSide && event.getHand().equals(InteractionHand.MAIN_HAND)) {
            EstateUtil.onPlayerRightClickInfoSignBlock(player, blockPos);
            EstateUtil.onPlayerRightClickDoor(player, blockPos);
        }
        if (!player.isCreative() && level.isClientSide && block instanceof DoorBlock
                && block.toString().contains("biomesoplenty")) {
            event.setCanceled(!EstateUtil.clientPlayerCanOpenDoor(blockPos));
        }
        CookingItems.onRightClickBlock(player, blockPos);
    }

    @SubscribeEvent
    public static void worldSoulTransform(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            BlockPos blockPos = event.getHitVec().getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            WorldSoulBlock.onRightClickSoulBlock(blockState, blockPos, itemStack, player);
        }
    }

    public static boolean checkBlockLimit(BlockPos blockPos, Player player) {
        boolean contains = false;
        BlockLimit removing = null;
        for (BlockLimit blockLimit : Utils.blockLimitList) {
            if (blockLimit.getBlockPos().equals(blockPos)) {
                if (blockLimit.getPlayer().equals(player)) {
                    return false;
                }
                if (blockLimit.getPlayer() == null
                        || blockLimit.getPlayer().position().distanceTo(blockLimit.getBlockPos().getCenter()) > 8) {
                    contains = false;
                    removing = blockLimit;
                } else {
                    ModNetworking.sendToClient(new CheckBlockLimitS2CPacket(), (ServerPlayer) blockLimit.getPlayer());
                    contains = true;
                }
            }
        }
        beforeRemoveBlockLimit(removing);
        Utils.blockLimitList.remove(removing);
        return contains;
    }

    public static boolean checkBlockLimitIsThisPlayer(BlockPos blockPos, Player player) {
        return Utils.blockLimitList.stream().anyMatch(blockLimit ->
                blockLimit.getBlockPos().equals(blockPos) && blockLimit.getPlayer().equals(player));
    }

    public static void beforeRemoveBlockLimit(BlockLimit removingBlockLimit) {
        if (removingBlockLimit != null && removingBlockLimit.getPlayer() != null) {
            Player player = removingBlockLimit.getPlayer();
            BlockEntity blockEntity = removingBlockLimit.getPlayer().level()
                    .getBlockEntity(removingBlockLimit.getBlockPos());
            if (blockEntity instanceof Droppable droppable) {
                droppable.drops(player);
            }
        }
    }

    public static void removePlayerLimit(Player player) {
        for (BlockLimit blockLimit : Utils.blockLimitList) {
            if (blockLimit.getPlayer().getName().getString().equals(player.getName().getString())) {
                BlockEntity blockEntity = player.level().getBlockEntity(blockLimit.getBlockPos());
                if (blockEntity instanceof Droppable droppable) {
                    droppable.drops(player);
                }
                Utils.blockLimitList.remove(blockLimit);
                break;
            }
        }
    }

    @SubscribeEvent
    public static void RightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            BlockPos blockPos = event.getHitVec().getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);
            BlockEntity blockEntity = player.level().getBlockEntity(blockPos);
            if (!checkBlockLimitIsThisPlayer(blockPos, player)) {
                if (checkBlockLimit(blockPos, player) && !player.isCreative()) {
                    Compute.sendFormatMSG(player, Component.literal("方块").withStyle(ChatFormatting.GREEN),
                            Component.literal("这个方块正在被使用。"));
                    event.setCanceled(true);
                } else {
                    if (blockState.getBlock().equals(ModBlocks.FURNACE.get())) {
                        ModNetworking.sendToClient(new ScreenSetS2CPacket(5), (ServerPlayer) player);
                        Smelt.sendDataToClient((ServerPlayer) player);
                        event.setCanceled(true);
                    } else {
                        Item item = player.getMainHandItem().getItem();
                        if (blockState.getBlock().equals(ModBlocks.FORGING_BLOCK.get())
                                && (item instanceof ForgeHammer || item instanceof SmithHammer)
                                && !ForgeEquipUtils.getPlayerInZoneItemList(player).isEmpty()) {
                            ModNetworking.sendToClient(new ScreenSetS2CPacket(4), (ServerPlayer) player);
                            MySound.soundToPlayer(player, SoundEvents.ANVIL_LAND);
                            event.setCanceled(true);
                        } else {
                            if (blockEntity instanceof Droppable) {
                                BlockLimit blockLimit = new BlockLimit(blockPos, player, 3);
                                Utils.blockLimitList.add(blockLimit);
                                Utils.whoIsUsingBlock.put(blockPos, player);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void disallowToRightClick(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        BlockPos blockPos = event.getHitVec().getBlockPos();
        BlockState blockState = player.level().getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (!player.isCreative()) {
            if (event.getItemStack().getItem() instanceof BoatItem) {
                event.setCanceled(true);
            }
            Set<Block> set = new HashSet<>() {{
                add(Blocks.ACACIA_TRAPDOOR);
                add(Blocks.BIRCH_TRAPDOOR);
                add(Blocks.IRON_TRAPDOOR);
                add(Blocks.OAK_TRAPDOOR);
                add(Blocks.CRIMSON_TRAPDOOR);
                add(Blocks.DARK_OAK_TRAPDOOR);
                add(Blocks.JUNGLE_TRAPDOOR);
                add(Blocks.MANGROVE_TRAPDOOR);
                add(Blocks.SPRUCE_TRAPDOOR);
                add(Blocks.WARPED_TRAPDOOR);
                add(Blocks.FURNACE);
                add(Blocks.BLAST_FURNACE);
                add(Blocks.HOPPER);
                add(Blocks.SMOKER);
                add(Blocks.ENCHANTING_TABLE);
                add(Blocks.DISPENSER);
                add(Blocks.DROPPER);
                add(Blocks.BARREL);
                add(AquamiraeBlocks.LUMINESCENT_BUBBLE.get());
            }};
            if (set.contains(block) || block instanceof ShulkerBoxBlock
                    || block instanceof AnvilBlock || block instanceof StonecutterBlock) {
                event.setCanceled(true);
            }
            if (blockState.getBlock().toString().length() > 12
                    && blockState.getBlock().toString().startsWith("create", 6)
                    && !blockState.getBlock().toString().contains("seat")) {
                event.setCanceled(true);
            }
            if (block.toString().contains("blue_seat")) {
                event.setCanceled(true);
            }
            if (block.getName().toString().contains("workshop")) return;
            if (player.level().getBlockEntity(blockPos) instanceof Container
                    && event.getSide().isServer() && BonusChestInfo.getBonusChestInfo(blockPos) == null) {
                event.setCanceled(true);
            }
        }

        if (block instanceof ChestBlock && event.getSide().isServer() && BonusChestInfo.getBonusChestInfo(blockPos) != null) {
            if (Utils.playerIsUsingBlockBlockPosMap.containsValue(blockPos)) {
                Compute.sendFormatMSG(player, Component.literal("奖励箱").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("有其他玩家正在获取这个奖励箱的内容，请稍等片刻。").withStyle(ChatFormatting.WHITE));
                event.setCanceled(true);
            } else {
                BonusChestPlayerData.onPlayerSuccessOpenBonusChest(player, blockPos, event);
            }
        }
    }

    @SubscribeEvent
    public static void recordPlayerOpenContainer(PlayerContainerEvent.Open event) {
        Player player = event.getEntity();
        String playerName = player.getName().getString();
        if (event.getContainer() instanceof CookingPotMenu) {
            LogUtils.getLogger().info("容器 {} 在 {} 打开了 {}", playerName, player.position(), event.getContainer());
        }
    }

    @SubscribeEvent
    public static void PlayerContainerCloseCheck(PlayerContainerEvent.Close event) {
        Player player = event.getEntity();
        String playerName = player.getName().getString();
        Utils.playerIsUsingBlockBlockPosMap.remove(playerName);
        ChestBlockEntity chestBlockEntity = BonusChestPlayerData.openBonusChestMap.getOrDefault(player, null);
        if (chestBlockEntity != null) {
            for (int i = 0; i < 27; i++) {
                if (!chestBlockEntity.getItem(i).is(Items.AIR)) {
                    InventoryOperation.giveItemStack(player, chestBlockEntity.getItem(i));
                }
            }
            BonusChestPlayerData.openBonusChestMap.remove(player);
        }
    }

    @SubscribeEvent
    public static void Dig(net.minecraftforge.event.level.BlockEvent.BreakEvent event) {
        if (!event.getPlayer().isCreative() && !event.getState().is(Blocks.FIRE)) event.setCanceled(true);
        RandomEventsHandler.onBreakBlock(event.getPlayer(), event.getPos());
        MineSpur.mineEvent(event);
        WoodSpur.woodEvent(event);
        CropSpur.cropsInteract(event);
        WardenInstance.onBreakBlockEvent(event);
    }

    @SubscribeEvent
    public static void BlockPlaceEvent(net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide && !player.isCreative()) {
            Level level = player.level();
            Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
            BlockState blockState = event.getPlacedBlock();
            BlockPos blockPos = event.getBlockSnapshot().getPos();
            BlockState targetState = event.getBlockSnapshot().getReplacedBlock();
            int tickCount = Tick.get();
            String name = player.getName().getString();
            if (level.equals(overWorld) && blockPos.getY() <= 11
                    && (blockState.is(Blocks.COBBLESTONE) || blockState.is(Blocks.COBBLED_DEEPSLATE))
                    && !targetState.getFluidState().is(Fluids.WATER) && !targetState.getFluidState().is(Fluids.LAVA)) {
                BlockAndResetTime blockAndResetTime = new BlockAndResetTime(blockState, blockPos, tickCount + 1200);
                if (!Utils.blockPlaceMap.containsKey(name)) Utils.blockPlaceMap.put(name, new LinkedList<>());
                if (!Utils.posEvenBeenDigOrPlace.contains(blockPos)) {
                    Utils.blockPlaceMap.get(name).add(blockAndResetTime);
                    Utils.posEvenBeenDigOrPlace.add(blockPos);
                }
                if (Utils.blockPlaceMap.get(name).size() > 40) {
                    BlockAndResetTime blockAndResetTime1 = Utils.blockPlaceMap.get(name).poll();
                    if (blockAndResetTime1.getBlockState().is(Blocks.COBBLESTONE))
                        player.addItem(Items.COBBLESTONE.getDefaultInstance());
                    if (blockAndResetTime1.getBlockState().is(Blocks.DEEPSLATE))
                        player.addItem(Items.COBBLED_DEEPSLATE.getDefaultInstance());
                    overWorld.destroyBlock(blockAndResetTime1.getBlockPos(), false);
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime1.getBlockPos());

                }
            } else event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void BlockResetEvent(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            int tick = Tick.get();
            Player player = event.player;
            Level overWorld = event.player.getServer().getLevel(Level.OVERWORLD);
            String name = player.getName().getString();
            if (tick % 20 == 0 && Utils.noMineDigMap.containsKey(name) && Utils.noMineDigMap.get(name).peek() != null) {
                Queue<BlockAndResetTime> queue = Utils.noMineDigMap.get(name);
                while (queue.peek() != null && queue.peek().getResetTick() < tick) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(), blockAndResetTime.getBlockState());
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
                }
            }
            if (tick % 20 == 10 && Utils.blockPlaceMap.containsKey(name) && Utils.blockPlaceMap.get(name).peek() != null) {
                Queue<BlockAndResetTime> queue = Utils.blockPlaceMap.get(name);
                while (queue.peek() != null && queue.peek().getResetTick() < tick) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    if (blockAndResetTime.getBlockState().is(Blocks.COBBLESTONE))
                        player.addItem(Items.COBBLESTONE.getDefaultInstance());
                    if (blockAndResetTime.getBlockState().is(Blocks.DEEPSLATE))
                        player.addItem(Items.COBBLED_DEEPSLATE.getDefaultInstance());
                    overWorld.destroyBlock(blockAndResetTime.getBlockPos(), false);
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
                }
            }
        }
    }

    public static void PlayerLogOut(Player player) {
        Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
        String name = player.getName().getString();
        if (Utils.noMineDigMap.containsKey(name) && Utils.noMineDigMap.get(name).peek() != null) {
            Queue<BlockAndResetTime> queue = Utils.noMineDigMap.get(name);
            while (queue.peek() != null) {
                BlockAndResetTime blockAndResetTime = queue.poll();
                overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(), blockAndResetTime.getBlockState());
                Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
            }
        }
        if (Utils.blockPlaceMap.containsKey(name) && Utils.blockPlaceMap.get(name).peek() != null) {
            Queue<BlockAndResetTime> queue = Utils.blockPlaceMap.get(name);
            while (queue.peek() != null) {
                BlockAndResetTime blockAndResetTime = queue.poll();
                if (blockAndResetTime.getBlockState().is(Blocks.COBBLESTONE))
                    player.addItem(Items.COBBLESTONE.getDefaultInstance());
                if (blockAndResetTime.getBlockState().is(Blocks.DEEPSLATE))
                    player.addItem(Items.COBBLED_DEEPSLATE.getDefaultInstance());
                overWorld.destroyBlock(blockAndResetTime.getBlockPos(), false);
                Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
            }
        }
    }

    public static void netherMineReset(Level level) {
        LogUtils.getLogger().info("Resetting nether mine.");
        if (Utils.netherMineList.peek() != null) {
            Queue<BlockAndResetTime> queue = Utils.netherMineList;
            while (queue.peek() != null) {
                BlockAndResetTime blockAndResetTime = queue.poll();
                level.setBlockAndUpdate(blockAndResetTime.getBlockPos(), blockAndResetTime.getBlockState());
                Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
            }
        }
        LogUtils.getLogger().info("done.");
    }

    public static void mineAndWoodReset(Level level) {
        LogUtils.getLogger().info("Resetting Mine And Wood.");
        Level overWorld = level.getServer().getLevel(Level.OVERWORLD);
        if (Utils.worldMineList.peek() != null) {
            Queue<BlockAndResetTime> queue = Utils.worldMineList;
            while (queue.peek() != null) {
                BlockAndResetTime blockAndResetTime = queue.poll();
                overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(), blockAndResetTime.getBlockState());
                Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
            }
        }

        while (Utils.worldWoodList.peek() != null) {
            BlockAndResetTime blockAndResetTime = Utils.worldWoodList.poll();
            overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(), blockAndResetTime.getBlockState());
        }

        Utils.noMineDigMap.keySet().forEach(name -> {
            if (Utils.noMineDigMap.containsKey(name) && Utils.noMineDigMap.get(name).peek() != null) {
                Queue<BlockAndResetTime> queue = Utils.noMineDigMap.get(name);
                while (queue.peek() != null) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(), blockAndResetTime.getBlockState());
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
                }
            }
            if (Utils.blockPlaceMap.containsKey(name) && Utils.blockPlaceMap.get(name).peek() != null) {
                Queue<BlockAndResetTime> queue = Utils.blockPlaceMap.get(name);
                while (queue.peek() != null) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    if (overWorld.getServer().getPlayerList().getPlayerByName(name) != null) {
                        ServerPlayer serverPlayer = overWorld.getServer().getPlayerList().getPlayerByName(name);
                        if (blockAndResetTime.getBlockState().is(Blocks.COBBLESTONE))
                            serverPlayer.addItem(Items.COBBLESTONE.getDefaultInstance());
                        if (blockAndResetTime.getBlockState().is(Blocks.DEEPSLATE))
                            serverPlayer.addItem(Items.COBBLED_DEEPSLATE.getDefaultInstance());
                    }
                    overWorld.destroyBlock(blockAndResetTime.getBlockPos(), false);
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
                }
            }
        });
        LogUtils.getLogger().info("done.");
    }

    @SubscribeEvent
    public static void mineResetEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            int tick = Tick.get();
            Level overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (event.level.equals(overWorld) && tick % 400 == 15 && Utils.worldMineList.peek() != null) {
                Queue<BlockAndResetTime> queue = Utils.worldMineList;
                while (queue.peek() != null && queue.peek().getResetTick() < tick) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(), blockAndResetTime.getBlockState());
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
                }
            }
            if (event.level.dimension().equals(Level.NETHER) && tick % 400 == 16) {
                Queue<BlockAndResetTime> queue = Utils.netherMineList;
                while (queue.peek() != null && queue.peek().getResetTick() < tick) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    event.level.setBlockAndUpdate(blockAndResetTime.getBlockPos(), blockAndResetTime.getBlockState());
                }
            }
        }
    }

    @SubscribeEvent
    public static void WoodReset(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            int tick = Tick.get();
            Level overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (event.level.equals(overWorld) && tick % 400 == 5) {
                if (Utils.worldWoodList.peek() != null) {
                    while (Utils.worldWoodList.peek() != null && Utils.worldWoodList.peek().getResetTick() < tick) {
                        BlockAndResetTime blockAndResetTime = Utils.worldWoodList.poll();
                        overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(), blockAndResetTime.getBlockState());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void BoatBanEvent(PlayerInteractEvent.RightClickItem event) {
        Vec3 vec3 = event.getEntity().pick(4, 0, false).getLocation();
        if (!event.getLevel().getBlockState(new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z)).is(Blocks.WATER)
                && event.getItemStack().getItem() instanceof BoatItem) event.setCanceled(true);
    }
}
