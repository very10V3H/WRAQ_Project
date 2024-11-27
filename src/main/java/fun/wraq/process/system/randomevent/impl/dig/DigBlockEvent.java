package fun.wraq.process.system.randomevent.impl.dig;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.system.randomevent.RandomEvent;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IPlantable;

import java.util.*;

public class DigBlockEvent extends RandomEvent {

    public Set<BlockPos> blockPosSet = new HashSet<>();
    private final List<Block> blockList;
    private Map<Player, Integer> scoreMap = new HashMap<>();
    private Set<BlockPos> brokenPosSet = new HashSet<>();
    public DigBlockEvent(ResourceKey<Level> dimension, Vec3 pos, List<Component> beginAnnouncement,
                         List<Component> finishAnnouncement, List<Component> overTimeAnnouncement,
                         MinecraftServer server, List<Block> blockList) {
        super(dimension, pos, beginAnnouncement, finishAnnouncement, overTimeAnnouncement, server);
        this.blockList = blockList;
    }

    @Override
    protected void beginAction() {
        Random random = new Random();
        double density = 0.15;
        for (int x = (int) pos.x - 16 ; x < (int) pos.x + 16 ; x ++) {
            for (int y = (int) pos.y - 3 ; y < (int) pos.y + 3 ; y ++) {
                for (int z = (int) pos.z - 16 ; z < (int) pos.z + 16 ; z ++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    BlockState blockState = level.getBlockState(blockPos);
                    Block block = blockState.getBlock();
                    Block belowBlock = level.getBlockState(blockPos.below()).getBlock();
                    Block aboveBlock = level.getBlockState(blockPos.above()).getBlock();
                    if (aboveBlock.equals(Blocks.AIR) && block.equals(Blocks.AIR) && !belowBlock.equals(Blocks.AIR)
                            && !(belowBlock instanceof BonemealableBlock || belowBlock instanceof IPlantable)) {
                        if (random.nextDouble() < density) {
                            blockPosSet.add(blockPos);
                        }
                    }
                }
            }
        }
        blockPosSet.forEach(pos -> {
            BlockState blockState = blockList.get(random.nextInt(blockList.size())).defaultBlockState();
            level.setBlockAndUpdate(pos, blockState);
        });
    }

    @Override
    protected void tick() {
        if (isCarryingOut && Tick.get() % 200 == 0) {
            Set<BlockPos> copyBlockPosSet = new HashSet<>(blockPosSet);
            copyBlockPosSet.removeAll(brokenPosSet);
            players.forEach(player -> {
                sendFormatMSG(player, Te.s("还有", String.valueOf(copyBlockPosSet.size()), "处的方块没有被破坏! 分别位于:"));
                List<BlockPos> copyBlockPosList = new ArrayList<>(copyBlockPosSet);
                for (int i = 0; i < Math.min(5, copyBlockPosList.size()); i++) {
                    BlockPos blockPos = copyBlockPosList.get(i);
                    sendFormatMSG(player, Te.s("(" + blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ() + ")"));
                }
                if (copyBlockPosList.size() > 5) {
                    sendFormatMSG(player, Te.s("..."));
                }
            });
        }
    }

    @Override
    protected boolean finishCondition() {
        return brokenPosSet.equals(blockPosSet);
    }

    @Override
    protected void finishAction() {
        broad(Te.s("天外来物", CustomStyle.styleOfSunIsland, "已被清理完毕!"));
        List<Map.Entry<Player, Integer>> rankList = new ArrayList<>(scoreMap.entrySet().stream().toList());
        rankList.sort(new Comparator<Map.Entry<Player, Integer>>() {
            @Override
            public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        for (int i = 0; i < Math.min(rankList.size(), 5); i++) {
            Map.Entry<Player, Integer> entry = rankList.get(i);
            broad(Te.s(entry.getKey().getDisplayName(), "清理了",
                    String.valueOf(entry.getValue()), CustomStyle.styleOfSunIsland, "个方块"));
        }
    }

    @Override
    public void reset() {
        scoreMap.clear();
        brokenPosSet.clear();
        blockPosSet.forEach(pos -> {
            level.destroyBlock(pos, false);
        });
        blockPosSet.clear();
    }

    @Override
    protected String getDataKey() {
        return "DIG_BLOCK_EVENT";
    }

    public void onBreakBlock(Player player, BlockPos pos) {
        if (player.level().equals(this.level) && blockPosSet.contains(pos)) {
            players.add(player);
            brokenPosSet.add(pos);
            int score = scoreMap.getOrDefault(player, 0) + 1;
            scoreMap.put(player, score);
            Compute.sendActionBarMSG(player, Te.s("目前拥有", score + "分", CustomStyle.styleOfFlexible));
            level.destroyBlock(pos, false);
        }
    }
}
