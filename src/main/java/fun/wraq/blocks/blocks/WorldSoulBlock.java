package fun.wraq.blocks.blocks;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class WorldSoulBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public WorldSoulBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE =
            Block.box(0, 0, 0, 16, 16, 16);

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public static void onRightClickSoulBlock(BlockState blockState, BlockPos blockPos,
                                             ItemStack itemStack, Player player) {
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
                ItemStack worldSoul1 = ModItems.WORLD_SOUL_1.get().getDefaultInstance();
                int transformSuccessNum = 0;
                int originalItemNum = itemStack.getCount();
                for (int i = 0; i < itemStack.getCount(); i++) {
                    Random random = new Random();
                    if (random.nextDouble(1) < (Utils.WorldEntropyIncreaseSpeed + mediumNum.get())) {
                        transformSuccessNum++;
                    }
                }
                Compute.sendFormatMSG(player, Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                        Component.literal("你在解析成功概率为").withStyle(ChatFormatting.WHITE).
                                append(Component.literal(String.format("%.2f%%", (Utils.WorldEntropyIncreaseSpeed + mediumNum.get()) * 100)).withStyle(CustomStyle.styleOfWorld))
                                .append(Component.literal("的介质中,用" + originalItemNum + "个").withStyle(ChatFormatting.WHITE))
                                .append(itemStack.getDisplayName())
                                .append(Component.literal("成功解析出了").withStyle(ChatFormatting.WHITE))
                                .append(Component.literal(String.valueOf(transformSuccessNum)).withStyle(CustomStyle.styleOfWorld))
                                .append(Component.literal("个").withStyle(ChatFormatting.WHITE))
                                .append(worldSoul1.getDisplayName()));
                worldSoul1.setCount(transformSuccessNum);
                Compute.playerItemUseWithRecord(player, itemStack.getCount());
                ItemAndRate.send(player, worldSoul1);
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
                    InventoryOperation.giveItemStack(player, NextTireSoul.getDefaultInstance());
                }
            }
        }
    }
}
