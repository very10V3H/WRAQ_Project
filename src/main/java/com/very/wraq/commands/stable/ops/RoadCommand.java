package com.very.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraftforge.common.IPlantable;

import java.util.*;

public class RoadCommand implements Command<CommandSourceStack> {
    public static RoadCommand instance = new RoadCommand();

    public static WeakHashMap<Player, Integer> map = new WeakHashMap<>();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String s = StringArgumentType.getString(context, "type");
        int type = Integer.parseInt(s);
        Map<Integer, String> typeMap = new HashMap<>() {{
            put(0, "石制路径");
            put(1, "森林路径");
            put(2, "黑石路径");
            put(3, "平原草径");
        }};
        map.put(player, type);
        if (!typeMap.containsKey(type)) {
            player.sendSystemMessage(Component.literal("支持类型为："));
            typeMap.forEach((k, v) -> {
                player.sendSystemMessage(Component.literal(v));
            });
            return 0;
        }
        if (typeMap.containsKey(type)) {
            player.sendSystemMessage(Component.literal("开始铺设路径，类型为：" + typeMap.get(type)));
        } else {
            player.sendSystemMessage(Component.literal("停止铺设路径"));
        }
        return 0;
    }

    public static Queue<BlockPos> changedPos = new ArrayDeque<>();

    public static void tick(Player player) {
        if (map.containsKey(player)) {
            int type = map.get(player);
            Level level = player.level();
            BlockPos blockPos = new BlockPos(player.getBlockX(), (int) Math.round(player.getY() - 1), player.getBlockZ());
            Random r = new Random();
            BlockState[] path = null;
            BlockState[] deco = null;
            switch (type) {
                case 0 -> {
                    path = new BlockState[]{Blocks.ANDESITE.defaultBlockState(),
                            Blocks.STONE.defaultBlockState(),
                            Blocks.STONE_BRICKS.defaultBlockState(),
                            Blocks.SMOOTH_STONE.defaultBlockState(),
                            Blocks.COBBLESTONE.defaultBlockState()
                    };
                    deco = new BlockState[]{

                    };
                }
                case 1 -> {
                    path = new BlockState[]{Blocks.COBBLESTONE.defaultBlockState(),
                            Blocks.MOSSY_COBBLESTONE.defaultBlockState(),
                            Blocks.PODZOL.defaultBlockState(),
                            Blocks.GRASS_BLOCK.defaultBlockState(),
                            Blocks.DIRT_PATH.defaultBlockState()
                    };
                    deco = new BlockState[]{Blocks.WHITE_TULIP.defaultBlockState(),
                            Blocks.AZURE_BLUET.defaultBlockState(),
                            Blocks.ORANGE_TULIP.defaultBlockState(),
                            Blocks.OXEYE_DAISY.defaultBlockState(),
                            Blocks.DANDELION.defaultBlockState(),
                            Blocks.POPPY.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                    };
                }
                case 2 -> {
                    path = new BlockState[]{Blocks.COBBLED_DEEPSLATE.defaultBlockState(),
                            Blocks.DEEPSLATE.defaultBlockState(),
                            Blocks.DEEPSLATE_BRICKS.defaultBlockState(),
                            Blocks.POLISHED_DEEPSLATE.defaultBlockState(),
                            Blocks.BLACKSTONE.defaultBlockState(),
                            Blocks.POLISHED_BLACKSTONE.defaultBlockState()
                    };
                    deco = new BlockState[]{Blocks.TORCHFLOWER.defaultBlockState(),
                            Blocks.FERN.defaultBlockState(),
                            Blocks.FERN.defaultBlockState(),
                            Blocks.FERN.defaultBlockState(),
                            Blocks.FERN.defaultBlockState(),
                            Blocks.FERN.defaultBlockState(),
                            Blocks.FERN.defaultBlockState(),
                    };
                }
                case 3 -> {
                    path = new BlockState[]{Blocks.MOSS_BLOCK.defaultBlockState(),
                            Blocks.GRASS_BLOCK.defaultBlockState(),
                            Blocks.DIRT_PATH.defaultBlockState(),
                            Blocks.GRAVEL.defaultBlockState(),
                            Blocks.GRASS_BLOCK.defaultBlockState(),
                    };
                    deco = new BlockState[]{Blocks.WHITE_TULIP.defaultBlockState(),
                            Blocks.AZURE_BLUET.defaultBlockState(),
                            Blocks.ORANGE_TULIP.defaultBlockState(),
                            Blocks.OXEYE_DAISY.defaultBlockState(),
                            Blocks.MOSS_CARPET.defaultBlockState(),
                            Blocks.DANDELION.defaultBlockState(),
                            Blocks.POPPY.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                            Blocks.TALL_GRASS.defaultBlockState(),
                    };
                }
            }
            if (path == null) return;
            if (changedPos.contains(blockPos) || level.getBlockState(blockPos).getBlock().equals(Blocks.AIR)
                    || level.getBlockState(blockPos).getBlock() instanceof IPlantable) return;
            double decoDensity = 0.4;
            for (int i = -4; i < 5; i++) {
                for (int j = -4; j < 5; j++) {
                    BlockPos changeBlockPos = new BlockPos(blockPos.getX() + i, blockPos.getY(), blockPos.getZ() + j);
                    if (i >= -2 && i <= 2 && j >= -2 && j <= 2) {
                        if (level.getBlockState(changeBlockPos.above()).getBlock() instanceof IPlantable) {
                            level.setBlockAndUpdate(changeBlockPos.above(), Blocks.AIR.defaultBlockState());
                        }
                        level.setBlockAndUpdate(changeBlockPos, path[r.nextInt(path.length)]);
                        changedPos.add(changeBlockPos);
                    } else {
                        if (level.getBlockState(changeBlockPos.above()).getBlock().equals(Blocks.AIR)
                                && !level.getBlockState(changeBlockPos).getBlock().equals(Blocks.AIR)
                                && !changedPos.contains(changeBlockPos)) {
                            if (r.nextDouble() < decoDensity) {
                                BlockState blockState = deco[r.nextInt(deco.length)];
                                if (blockState.getBlock().equals(Blocks.STONE_BUTTON)) {
                                    blockState = blockState.setValue(ButtonBlock.FACING, Direction.NORTH).setValue(ButtonBlock.POWERED,
                                            Boolean.FALSE).setValue(ButtonBlock.FACE, AttachFace.FLOOR);
                                }
                                if (blockState.getBlock().equals(Blocks.TALL_GRASS)) {
                                    DoublePlantBlock.placeAt(player.level(), blockState, changeBlockPos.above(), 2);
                                } else level.setBlockAndUpdate(changeBlockPos.above(), blockState);
                            }
                        }
                    }
                }
            }
            while (changedPos.size() > 1000) changedPos.remove();
        }
    }
}
