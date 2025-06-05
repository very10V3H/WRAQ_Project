package fun.wraq.items.dev.rail;

import com.simibubi.create.content.decoration.girder.GirderBlock;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.networking.ModNetworking;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber
public class RailwayPillarSetTool extends WraqItem {

    public RailwayPillarSetTool(Properties properties) {
        super(properties, false, false);
    }

    public static int mode = 0;
    public static int delta = 0;

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        if (!level.isClientSide) {
            switch (mode) {
                case 0 -> stack.setHoverName(Te.s(stack.getItem().getDefaultInstance().getHoverName(),
                        " - 标准模式", " 落差: ", delta, ChatFormatting.AQUA));
                case 1 -> stack.setHoverName(Te.s(stack.getItem().getDefaultInstance().getHoverName(), " - 仅传送标准模式"));
                case 2 -> stack.setHoverName(Te.s(stack.getItem().getDefaultInstance().getHoverName(),
                        " - 标准模式(轨上)", " 落差: ", delta, ChatFormatting.AQUA));
                case 3 -> stack.setHoverName(Te.s(stack.getItem().getDefaultInstance().getHoverName(),
                        " - 斜铺模式", " 落差: ", delta, ChatFormatting.AQUA));
                case 4 -> stack.setHoverName(Te.s(stack.getItem().getDefaultInstance().getHoverName(), " - 仅传送斜铺模式"));
                case 5 -> stack.setHoverName(Te.s(stack.getItem().getDefaultInstance().getHoverName(), " - 撤销模式"));
                case 6 -> stack.setHoverName(Te.s(stack.getItem().getDefaultInstance().getHoverName(), " - 清理模式"));
                case 7 ->
                        stack.setHoverName(Te.s(stack.getItem().getDefaultInstance().getHoverName(), " - 切弯模式 - 90°"));
                case 8 ->
                        stack.setHoverName(Te.s(stack.getItem().getDefaultInstance().getHoverName(), " - 设置落差模式"));
            }
        }
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }

    @SubscribeEvent
    public static void leftClick(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getSide().isClient() && event.getHand().equals(InteractionHand.MAIN_HAND)
                && event.getItemStack().is(ModItems.RAILWAY_PILLAR_SET_TOOL.get())) {
            ModNetworking.sendToServer(new RailwayPillarSetToolModeC2SPacket());
        }
    }

    @SuppressWarnings("ALL")
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (player.isCreative() && !level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {

            String create = "create";
            String railways = "railways";
            Block metal_girder = ForgeRegistries.BLOCKS
                    .getValue(new ResourceLocation(create, "metal_girder"));
            Block industrialIronBlock = ForgeRegistries.BLOCKS
                    .getValue(new ResourceLocation(create, "industrial_iron_block"));
            Block conductorVent = ForgeRegistries.BLOCKS
                    .getValue(new ResourceLocation(railways, "conductor_vent"));
            Block andesiteBars = ForgeRegistries.BLOCKS
                    .getValue(new ResourceLocation(create, "andesite_bars"));

            MySound.soundToPlayer(player, SoundEvents.AMETHYST_BLOCK_PLACE);
            Set<Block> blockSet = Set.of(metal_girder, industrialIronBlock, conductorVent, andesiteBars,
                    Blocks.POLISHED_ANDESITE);
            switch (mode) {
                case 0 -> {
                    infos.clear();
                    BlockPos blockPos = player.getOnPos().below();
                    while (isIgnoredBlock(level.getBlockState(blockPos.below()).getBlock())) {
                        blockPos = blockPos.below();
                    }
                    setDefaultPillars(level, player, blockPos);
                    if (Math.abs(Math.abs(player.getYRot()) - 90) > 45) {
                        setBlock(level, player.getOnPos().below().east(4), industrialIronBlock);
                        setBlock(level, player.getOnPos().below().west(4), industrialIronBlock);
                        blockPos = player.getOnPos().below().east(3);
                        for (int i = 0; i <= 6; i++) {
                            BlockState blockState = metal_girder.defaultBlockState();
                            if (i == 3) {
                                blockState = blockState.setValue(BlockStateProperties.AXIS, Direction.Axis.Y)
                                        .setValue(GirderBlock.TOP, true)
                                        .setValue(GirderBlock.BOTTOM, true)
                                        .setValue(GirderBlock.X, true);
                            } else {
                                blockState = blockState.setValue(GirderBlock.X, true);
                            }
                            setBlock(level, blockPos.west(i), blockState);
                        }
                    } else {
                        setBlock(level, player.getOnPos().below().north(4), industrialIronBlock);
                        setBlock(level, player.getOnPos().below().south(4), industrialIronBlock);
                        blockPos = player.getOnPos().below().north(3);
                        for (int i = 0; i <= 6; i++) {
                            BlockState blockState = metal_girder.defaultBlockState();
                            if (i == 3) {
                                blockState = blockState.setValue(BlockStateProperties.AXIS, Direction.Axis.Y)
                                        .setValue(GirderBlock.TOP, true)
                                        .setValue(GirderBlock.BOTTOM, true)
                                        .setValue(GirderBlock.Z, true);
                            } else {
                                blockState = blockState.setValue(GirderBlock.Z, true);
                            }
                            setBlock(level, blockPos.south(i), blockState);
                        }
                    }
                    if (Math.abs(player.getYRot() - 90) < 30) { // 西
                        player.teleportTo(player.getX() - 10, player.getY() + delta, player.getZ());
                    } else if (Math.abs(player.getYRot() - 0) < 30) { // 南
                        player.teleportTo(player.getX(), player.getY() + delta, player.getZ() + 10);
                    } else if (Math.abs(player.getYRot() + 90) < 30) { // 东
                        player.teleportTo(player.getX() + 10, player.getY() + delta, player.getZ());
                    } else { // 北
                        player.teleportTo(player.getX(), player.getY() + delta, player.getZ() - 10);
                    }
                }
                case 1 -> {
                    if (Math.abs(player.getYRot() - 90) < 30) {
                        player.teleportTo(player.getX() - 10, player.getY() + delta, player.getZ());
                    } else if (Math.abs(player.getYRot() - 0) < 30) {
                        player.teleportTo(player.getX(), player.getY() + delta, player.getZ() + 10);
                    } else if (Math.abs(player.getYRot() + 90) < 30) {
                        player.teleportTo(player.getX() + 10, player.getY() + delta, player.getZ());
                    } else {
                        player.teleportTo(player.getX(), player.getY() + delta, player.getZ() - 10);
                    }
                }
                case 2 -> {
                    infos.clear();
                    BlockPos blockPos = player.getOnPos().below();
                    if (Math.abs(player.getYRot() - 90) < 30) { // 西
                        blockPos = blockPos.south(4);
                    } else if (Math.abs(player.getYRot() - 0) < 30) { // 南
                        blockPos = blockPos.east(4);
                    } else if (Math.abs(player.getYRot() + 90) < 30) { // 东
                        blockPos = blockPos.north(4);
                    } else { // 北
                        blockPos = blockPos.west(4);
                    }
                    BlockPos bottomPos = new BlockPos(blockPos);
                    while (isIgnoredBlock(level.getBlockState(bottomPos.below()).getBlock())) {
                        bottomPos = bottomPos.below();
                    }
                    setDefaultPillars(level, player, bottomPos);
                    if (Math.abs(Math.abs(player.getYRot()) - 90) > 45) {
                        setBlock(level, blockPos.east(4), industrialIronBlock);
                        setBlock(level, blockPos.west(4), industrialIronBlock);
                        blockPos = blockPos.east(3);
                        for (int i = 0; i <= 6; i++) {
                            BlockState blockState = metal_girder.defaultBlockState();
                            if (i == 3) {
                                blockState = blockState.setValue(BlockStateProperties.AXIS, Direction.Axis.Y)
                                        .setValue(GirderBlock.TOP, true)
                                        .setValue(GirderBlock.BOTTOM, true)
                                        .setValue(GirderBlock.X, true);
                            } else {
                                blockState = blockState.setValue(GirderBlock.X, true);
                            }
                            setBlock(level, blockPos.west(i), blockState);
                        }
                    } else {
                        setBlock(level, blockPos.north(4), industrialIronBlock);
                        setBlock(level, blockPos.south(4), industrialIronBlock);
                        blockPos = blockPos.north(3);
                        for (int i = 0; i <= 6; i++) {
                            BlockState blockState = metal_girder.defaultBlockState();
                            if (i == 3) {
                                blockState = blockState.setValue(BlockStateProperties.AXIS, Direction.Axis.Y)
                                        .setValue(GirderBlock.TOP, true)
                                        .setValue(GirderBlock.BOTTOM, true)
                                        .setValue(GirderBlock.Z, true);
                            } else {
                                blockState = blockState.setValue(GirderBlock.Z, true);
                            }
                            setBlock(level, blockPos.south(i), blockState);
                        }
                    }
                    if (Math.abs(player.getYRot() - 90) < 30) { // 西
                        player.teleportTo(player.getX() - 10, player.getY() + delta, player.getZ());
                    } else if (Math.abs(player.getYRot() - 0) < 30) { // 南
                        player.teleportTo(player.getX(), player.getY() + delta, player.getZ() + 10);
                    } else if (Math.abs(player.getYRot() + 90) < 30) { // 东
                        player.teleportTo(player.getX() + 10, player.getY() + delta, player.getZ());
                    } else { // 北
                        player.teleportTo(player.getX(), player.getY() + delta, player.getZ() - 10);
                    }
                }
                case 3 -> {
                    infos.clear();
                    BlockPos bottomPos = player.getOnPos().below();
                    while (isIgnoredBlock(level.getBlockState(bottomPos.below()).getBlock())) {
                        bottomPos = bottomPos.below();
                    }
                    BlockPos blockPos = new BlockPos(bottomPos);
                    setDefaultPillars(level, player, blockPos);
                    if (Math.abs(player.getYRot() + 45) < 30 || Math.abs(player.getYRot() - 135) < 30) {
                        blockPos = new BlockPos(bottomPos);
                        blockPos = blockPos.south().west();
                        setDefaultPillars(level, player, blockPos);
                        blockPos = player.getOnPos().below().south(3).west(3);
                        BlockPos barPos1 = blockPos.east();
                        BlockState blockState1 = andesiteBars.defaultBlockState()
                                .setValue(BlockStateProperties.NORTH, true)
                                .setValue(BlockStateProperties.WEST, true);
                        BlockPos barPos2 = blockPos.north();
                        BlockState blockState2 = andesiteBars.defaultBlockState()
                                .setValue(BlockStateProperties.EAST, true)
                                .setValue(BlockStateProperties.SOUTH, true);
                        for (int i = 0; i < 6; i++) {
                            setBlock(level, blockPos.north(i).east(i), industrialIronBlock);
                            if (i < 5) {
                                setBlock(level, barPos1.north(i).east(i), blockState1);
                                setBlock(level, barPos2.north(i).east(i), blockState2);
                            }
                        }
                    } else {
                        blockPos = new BlockPos(bottomPos);
                        blockPos = blockPos.south().east();
                        setDefaultPillars(level, player, blockPos);
                        blockPos = player.getOnPos().below().south(3).east(3);
                        BlockPos barPos1 = blockPos.west();
                        BlockState blockState1 = andesiteBars.defaultBlockState()
                                .setValue(BlockStateProperties.NORTH, true)
                                .setValue(BlockStateProperties.EAST, true);
                        BlockPos barPos2 = blockPos.north();
                        BlockState blockState2 = andesiteBars.defaultBlockState()
                                .setValue(BlockStateProperties.WEST, true)
                                .setValue(BlockStateProperties.SOUTH, true);
                        for (int i = 0; i < 6; i++) {
                            setBlock(level, blockPos.north(i).west(i), industrialIronBlock);
                            if (i < 5) {
                                setBlock(level, barPos1.north(i).west(i), blockState1);
                                setBlock(level, barPos2.north(i).west(i), blockState2);
                            }
                        }
                    }

                    if (Math.abs(player.getYRot() + 45) < 30) {
                        player.teleportTo(player.getX() + 6, player.getY() + delta, player.getZ() + 6);
                    } else if (Math.abs(player.getYRot() - 45) < 30) {
                        player.teleportTo(player.getX() - 6, player.getY() + delta, player.getZ() + 6);
                    } else if (Math.abs(player.getYRot() - 135) < 30) {
                        player.teleportTo(player.getX() - 6, player.getY() + delta, player.getZ() - 6);
                    } else {
                        player.teleportTo(player.getX() + 6, player.getY() + delta, player.getZ() - 6);
                    }
                }
                case 4 -> {
                    if (Math.abs(player.getYRot() + 45) < 30) {
                        player.teleportTo(player.getX() + 6, player.getY() + delta, player.getZ() + 6);
                    } else if (Math.abs(player.getYRot() - 45) < 30) {
                        player.teleportTo(player.getX() - 6, player.getY() + delta, player.getZ() + 6);
                    } else if (Math.abs(player.getYRot() - 135) < 30) {
                        player.teleportTo(player.getX() - 6, player.getY() + delta, player.getZ() - 6);
                    } else {
                        player.teleportTo(player.getX() + 6, player.getY() + delta, player.getZ() - 6);
                    }
                }
                case 5 -> {
                    infos.forEach(info -> {
                        level.setBlockAndUpdate(info.blockPos, info.blockState);
                    });
                    player.sendSystemMessage(Te.s("撤销了", infos.size(), "个方块"));
                    infos.clear();
                }
                case 6 -> {
                    int count = 0;
                    for (int i = player.getBlockX() - 10; i <= player.getBlockX() + 10; i++) {
                        for (int j = 0; j <= player.getBlockY(); j++) {
                            for (int k = player.getBlockZ() - 10; k <= player.getBlockZ() + 10; k++) {
                                BlockPos blockPos = new BlockPos(i, j, k);
                                if (blockSet.contains(level.getBlockState(blockPos).getBlock())) {
                                    level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                                    ++count;
                                }
                            }
                        }
                    }
                    player.sendSystemMessage(Te.s("清理了" + count + "个方块"));
                }
                case 7 -> {
                    if (Math.abs(player.getYRot() - 135) < 30) {
                        player.teleportTo(player.getX() - 12, player.getY(), player.getZ() - 12);
                    } else if (Math.abs(player.getYRot() + 135) < 30) {
                        player.teleportTo(player.getX() + 12, player.getY(), player.getZ() - 12);
                    } else if (Math.abs(player.getYRot() + 45) < 30) {
                        player.teleportTo(player.getX() + 12, player.getY(), player.getZ() + 12);
                    } else {
                        player.teleportTo(player.getX() - 12, player.getY(), player.getZ() + 12);
                    }
                    MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
                }
                case 8 -> {
                    if (player.isShiftKeyDown()) {
                        --delta;
                    } else {
                        ++delta;
                    }
                    player.sendSystemMessage(Te.s("落差: ", delta, ChatFormatting.AQUA));
                }
            }
        }
        return super.use(level, player, interactionHand);
    }

    private boolean isIgnoredBlock(Block block) {
        Set<Block> blockList = Set.of(
                Blocks.AIR,
                Blocks.WATER,
                Blocks.SNOW
        );
        return blockList.contains(block) || block instanceof IPlantable
                || block instanceof LeavesBlock || block instanceof RotatedPillarBlock;
    }

    private record OnRailwayPillarSetBlockInfo(BlockPos blockPos, BlockState blockState) {
    }

    private static List<OnRailwayPillarSetBlockInfo> infos = new ArrayList<>();

    private void setBlock(Level level, BlockPos pos, Block block) {
        infos.add(new OnRailwayPillarSetBlockInfo(pos, level.getBlockState(pos)));
        level.setBlockAndUpdate(pos, block.defaultBlockState());
    }

    private void setBlock(Level level, BlockPos pos, BlockState blockState) {
        infos.add(new OnRailwayPillarSetBlockInfo(pos, level.getBlockState(pos)));
        level.setBlockAndUpdate(pos, blockState);
    }

    private void setDefaultPillars(Level level, Player player, BlockPos blockPos) {
        String create = "create";
        String railways = "railways";
        Block metal_girder = ForgeRegistries.BLOCKS
                .getValue(new ResourceLocation(create, "metal_girder"));
        Block industrialIronBlock = ForgeRegistries.BLOCKS
                .getValue(new ResourceLocation(create, "industrial_iron_block"));
        Block conductorVent = ForgeRegistries.BLOCKS
                .getValue(new ResourceLocation(railways, "conductor_vent"));
        Block andesiteBars = ForgeRegistries.BLOCKS
                .getValue(new ResourceLocation(create, "andesite_bars"));
        for (int i = 0; i < player.getBlockY() - blockPos.getY(); i++) {
            setBlock(level, blockPos.above(i), metal_girder);
        }

        setBlock(level, blockPos, industrialIronBlock);
        setBlock(level, blockPos.above(1), conductorVent);
        setBlock(level, blockPos.above(2), conductorVent);
        setBlock(level, blockPos.above(3), industrialIronBlock);

        eightVectors.forEach(pair -> {
            BlockState blockState = andesiteBars.defaultBlockState();
            if (pair.equals(NORTH)) {
                blockState = blockState.setValue(BlockStateProperties.SOUTH, true);
            } else if (pair.equals(EAST)) {
                blockState = blockState.setValue(BlockStateProperties.WEST, true);
            } else if (pair.equals(SOUTH)) {
                blockState = blockState.setValue(BlockStateProperties.NORTH, true);
            } else if (pair.equals(WEST)) {
                blockState = blockState.setValue(BlockStateProperties.EAST, true);
            }
            BlockPos pos = blockPos.north(pair.getLeft()).east(pair.getRight());
            if (!level.getBlockState(pos).is(industrialIronBlock)) {
                if (level.getBlockState(pos.north()).is(andesiteBars)
                        || level.getBlockState(pos.north()).is(industrialIronBlock)) {
                    blockState = blockState.setValue(BlockStateProperties.NORTH, true);
                }
                if (level.getBlockState(pos.east()).is(andesiteBars)
                        || level.getBlockState(pos.east()).is(industrialIronBlock)) {
                    blockState = blockState.setValue(BlockStateProperties.EAST, true);
                }
                if (level.getBlockState(pos.south()).is(andesiteBars)
                        || level.getBlockState(pos.south()).is(industrialIronBlock)) {
                    blockState = blockState.setValue(BlockStateProperties.SOUTH, true);
                }
                if (level.getBlockState(pos.west()).is(andesiteBars)
                        || level.getBlockState(pos.west()).is(industrialIronBlock)) {
                    blockState = blockState.setValue(BlockStateProperties.WEST, true);
                }
                setBlock(level, pos, blockState);
                setBlock(level, pos.below(), Blocks.POLISHED_ANDESITE);
            }
        });
    }

    private static final Pair<Integer, Integer> NORTH = Pair.of(1, 0);
    private static final Pair<Integer, Integer> SOUTH = Pair.of(-1, 0);
    private static final Pair<Integer, Integer> EAST = Pair.of(0, 1);
    private static final Pair<Integer, Integer> WEST = Pair.of(0, -1);
    private static final Pair<Integer, Integer> NORTHEAST = Pair.of(1, 1);
    private static final Pair<Integer, Integer> NORTHWEST = Pair.of(1, -1);
    private static final Pair<Integer, Integer> SOUTHEAST = Pair.of(-1, 1);
    private static final Pair<Integer, Integer> SOUTHWEST = Pair.of(-1, -1);

    private final List<Pair<Integer, Integer>> eightVectors = List.of(
            NORTH,
            NORTHEAST,
            EAST,
            SOUTHEAST,
            SOUTH,
            SOUTHWEST,
            WEST,
            NORTHWEST
    );
}
