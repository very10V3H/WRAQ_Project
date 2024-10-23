package fun.wraq.events.core;

import com.mojang.logging.LogUtils;
import fun.wraq.blocks.blocks.WorldSoulBlock;
import fun.wraq.blocks.entity.ForgingBlockEntity;
import fun.wraq.blocks.entity.FurnaceEntity;
import fun.wraq.blocks.entity.HBrewingEntity;
import fun.wraq.blocks.entity.InjectBlockEntity;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModBlocks;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.BlockAndResetTime;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.Limit.CheckBlockLimitS2CPacket;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.networking.unSorted.PlayerCallBack;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.bonuschest.BonusChestInfo;
import fun.wraq.process.system.bonuschest.BonusChestPlayerData;
import fun.wraq.process.system.forge.ForgeHammer;
import fun.wraq.process.system.spur.events.CropSpur;
import fun.wraq.process.system.spur.events.MineSpur;
import fun.wraq.process.system.spur.events.WoodSpur;
import fun.wraq.render.toolTip.CustomStyle;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber
public class BlockEvent {
    @SubscribeEvent
    public static void setMineSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        double mineSpeed = PlayerAttributes.getMineSpeed(player);
        event.setNewSpeed((float) (1 + mineSpeed));
    }

    @SubscribeEvent
    public static void skyTowerTeleport(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = player.level();
        BlockPos blockPos = event.getHitVec().getBlockPos();
        if (!player.isCreative() && level.getBlockState(blockPos).getBlock().toString().contains("lamp"))
            event.setCanceled(true);
        if (!event.getEntity().level().isClientSide) {
            if (blockPos.equals(new BlockPos(945, 267, 45))) {
                player.teleportTo(941, 282, -6);
            }
        }
    }

    @SubscribeEvent
    public static void worldSoulTransform(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            BlockPos blockPos = event.getHitVec().getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);

            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            AtomicReference<Double> mediumNum = new AtomicReference<>(0.125);

            if (blockState.getBlock() instanceof WorldSoulBlock) {

                if (Utils.SoulList.isEmpty()) Utils.SoulListInit();
                if (Utils.SoulList.contains(itemStack.getItem())) {
                    AtomicBoolean flag = new AtomicBoolean(true);
                    Utils.WorldEntropyPos.forEach(worldEntropy -> {
                        if (worldEntropy.getVec3().equals(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()))) {
                            mediumNum.set(worldEntropy.getMediumNum());
                            flag.set(false);
                        }
                    });
                    if (flag.get()) return;

                    ItemStack worldSoul1 = ModItems.WorldSoul1.get().getDefaultInstance();
                    int TransformSuccessNum = 0;
                    int OriginalItemNum = itemStack.getCount();
                    for (int i = 0; i < itemStack.getCount(); i++) {
                        Random random = new Random();
                        if (random.nextDouble(1) < (Utils.WorldEntropyIncreaseSpeed + mediumNum.get()))
                            TransformSuccessNum++;
                    }
                    Compute.sendFormatMSG(player, Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("你在解析成功概率为").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(String.format("%.2f%%", (Utils.WorldEntropyIncreaseSpeed + mediumNum.get()) * 100)).withStyle(CustomStyle.styleOfWorld))
                                    .append(Component.literal("的介质中,用" + OriginalItemNum + "个").withStyle(ChatFormatting.WHITE))
                                    .append(itemStack.getDisplayName())
                                    .append(Component.literal("成功解析出了").withStyle(ChatFormatting.WHITE))
                                    .append(Component.literal(String.valueOf(TransformSuccessNum)).withStyle(CustomStyle.styleOfWorld))
                                    .append(Component.literal("个").withStyle(ChatFormatting.WHITE))
                                    .append(worldSoul1.getDisplayName()));

                    worldSoul1.setCount(TransformSuccessNum);
                    Compute.playerItemUseWithRecord(player, itemStack.getCount());
                    InventoryOperation.itemStackGive(player, worldSoul1);
                } else {
                    if (Utils.WorldSoulMap.isEmpty()) Utils.WorldSoulMapInit();
                    if (itemStack.getCount() == 64 && Utils.WorldSoulMap.containsKey(itemStack.getItem())) {
                        Item NextTireSoul = Utils.WorldSoulMap.get(itemStack.getItem());
                        Compute.sendFormatMSG(player, Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                                Component.literal("你将").withStyle(ChatFormatting.WHITE).
                                        append(itemStack.getDisplayName()).
                                        append(Component.literal("转换成为").withStyle(ChatFormatting.WHITE)).
                                        append(NextTireSoul.getDefaultInstance().getDisplayName()));
                        Compute.playerItemUseWithRecord(player, 64);
                        InventoryOperation.itemStackGive(player, NextTireSoul.getDefaultInstance());
                    }
                }
            }
        }
    }

    public static boolean BlockLimitContainBlockPos(BlockPos blockPos) {
        boolean contains = false;
        PlayerCallBack removing = null;
        for (PlayerCallBack playerCallBack : Utils.playerCallBackList) {
            if (playerCallBack.getBlockPos().equals(blockPos)) {
                if (playerCallBack.getPlayer() == null || playerCallBack.getPlayer().position().distanceTo(playerCallBack.getBlockPos().getCenter()) > 8) {
                    contains = false;
                    removing = playerCallBack;
                } else {
                    ModNetworking.sendToClient(new CheckBlockLimitS2CPacket(), (ServerPlayer) playerCallBack.getPlayer());
                    contains = true;
                }
            }
        }
        Utils.playerCallBackList.remove(removing);
        return contains;
    }

    @SubscribeEvent
    public static void RightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            BlockPos blockPos = event.getHitVec().getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);
            if (BlockLimitContainBlockPos(blockPos) && !player.isCreative()) {
                Compute.sendFormatMSG(player, Component.literal("方块").withStyle(ChatFormatting.GREEN),
                        Component.literal("这个方块正在被使用。"));
                event.setCanceled(true);
            } else {
                boolean flag = false;
                if (blockState.getBlock().equals(ModBlocks.FORGING_BLOCK.get())) {
                    if (player.getMainHandItem().getItem() instanceof ForgeHammer) {
                        ModNetworking.sendToClient(new ScreenSetS2CPacket(4), (ServerPlayer) player);
                        MySound.soundToPlayer(player, SoundEvents.ANVIL_LAND);
                        event.setCanceled(true);
                    } else {
                        ForgingBlockEntity forgingBlockEntity = (ForgingBlockEntity) player.level().getBlockEntity(blockPos);
                        forgingBlockEntity.clear();
                        flag = true;
                    }
                }
                if (blockState.getBlock().equals(ModBlocks.BREWING_BLOCK.get())) {
                    HBrewingEntity hBrewingEntity = (HBrewingEntity) player.level().getBlockEntity(blockPos);
                    hBrewingEntity.clear();
                    flag = true;
                }
                if (blockState.getBlock().equals(ModBlocks.INJECT_BLOCK.get())) {
                    InjectBlockEntity injectBlockEntity = (InjectBlockEntity) player.level().getBlockEntity(blockPos);
                    injectBlockEntity.clear();
                    flag = true;
                }
                if (blockState.getBlock().equals(ModBlocks.Furnace.get())) {
                    FurnaceEntity furnaceEntity = (FurnaceEntity) player.level().getBlockEntity(blockPos);
                    furnaceEntity.clear();
                    flag = true;
                }
                if (flag) {
                    PlayerCallBack playerCallBack = new PlayerCallBack(blockPos, player, 3);
                    Utils.playerCallBackList.add(playerCallBack);
                    Utils.whoIsUsingBlock.put(blockPos, player.getName().getString());
                }
            }
            ItemStack Sword = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (!blockState.is(Blocks.CHEST) && (Sword.is(ModItems.ForestSword0.get()) || Sword.is(ModItems.ForestSword1.get()) ||
                    Sword.is(ModItems.ForestSword2.get()) || Sword.is(ModItems.ForestSword3.get()))) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void PreventRightClick(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        BlockPos blockPos = event.getHitVec().getBlockPos();
        BlockState blockState = player.level().getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (!player.isCreative()) {
            if (event.getItemStack().getItem() instanceof BoatItem) event.setCanceled(true);
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
                add(Blocks.CRAFTING_TABLE);
                add(Blocks.FURNACE);
                add(Blocks.BLAST_FURNACE);
                add(Blocks.HOPPER);
                add(Blocks.SMOKER);
                add(Blocks.ENCHANTING_TABLE);
                add(Blocks.DISPENSER);
                add(Blocks.DROPPER);
                add(Blocks.BARREL);
            }};
            if (set.contains(block) || block instanceof ShulkerBoxBlock
                    || block instanceof AnvilBlock){
                event.setCanceled(true);
            }
            if (blockState.getBlock().toString().length() > 12
                    && blockState.getBlock().toString().startsWith("create", 6)
                    && !blockState.getBlock().toString().contains("seat")) {
                event.setCanceled(true);
            }
            if (player.level().getBlockEntity(blockPos) instanceof Container
                    && event.getSide().isServer() && BonusChestInfo.getBonusChestInfo(blockPos) == null) {
                event.setCanceled(true);
            }
            if (block instanceof ChestBlock && event.getSide().isServer() && BonusChestInfo.getBonusChestInfo(blockPos) != null) {
                if (player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.AIR)) {
                    if (Utils.playerIsUsingBlockBlockPosMap.containsValue(blockPos)) {
                        Compute.sendFormatMSG(player, Component.literal("奖励箱").withStyle(ChatFormatting.LIGHT_PURPLE),
                                Component.literal("有其他玩家正在获取这个奖励箱的内容，请稍等片刻。").withStyle(ChatFormatting.WHITE));
                        event.setCanceled(true);
                    } else {
                        BonusChestPlayerData.onPlayerSuccessOpenBonusChest(player, blockPos, event);
                    }
                } else {
                    event.setCanceled(true);
                    Compute.sendFormatMSG(player, Component.literal("奖励箱").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("为确保物品能够正确被拾取，请空手右击箱子打开。").withStyle(ChatFormatting.WHITE));
                }
            }
        }
    }

    @SubscribeEvent
    public static void PlayerContainerCloseCheck(PlayerContainerEvent.Close event) {
        String playerName = event.getEntity().getName().getString();
        Utils.playerIsUsingBlockBlockPosMap.remove(playerName);
    }

    @SubscribeEvent
    public static void Dig(net.minecraftforge.event.level.BlockEvent.BreakEvent event) {
        if (!event.getPlayer().isCreative() && !event.getState().is(Blocks.FIRE)) event.setCanceled(true);
        MineSpur.mineEvent(event);
        WoodSpur.woodEvent(event);
        CropSpur.cropsInteract(event);
    }

    @SubscribeEvent
    public static void BlockPlaceEvent(net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide && !player.isCreative()) {
            Level level = player.level();
            Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
            BlockState blockState = event.getPlacedBlock();
            BlockPos blockPos = event.getBlockSnapshot().getPos();
            BlockState targetState = event.getBlockSnapshot().getReplacedBlock();
            int tickCount = level.getServer().getTickCount();
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
            int tick = event.player.getServer().getTickCount();
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
    }

    @SubscribeEvent
    public static void mineResetEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            int tick = event.level.getServer().getTickCount();
            Level overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (event.level.equals(overWorld) && tick % 400 == 15 && Utils.worldMineList.peek() != null) {
                Queue<BlockAndResetTime> queue = Utils.worldMineList;
                while (queue.peek() != null && queue.peek().getResetTick() < tick) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(), blockAndResetTime.getBlockState());
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
                }

            }
        }
    }

    @SubscribeEvent
    public static void WoodReset(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            int tick = event.level.getServer().getTickCount();
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
