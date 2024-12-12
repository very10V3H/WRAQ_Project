package fun.wraq.process.system.randomevent.impl.urgent;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.randomevent.RandomAdditionalRewardEvent;
import fun.wraq.process.system.randomevent.RandomEvent;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.specialevents.springFes.FireWorkGun;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class UrgentEvent extends RandomEvent {

    private Queue<Player> rankQueue = new ArrayDeque<>();
    public UrgentEvent(ResourceKey<Level> dimension, Vec3 pos,
                       List<Component> beginAnnouncement, List<Component> finishAnnouncement,
                       MinecraftServer server, List<ItemAndRate> rewardList,
                       RandomAdditionalRewardEvent randomAdditionalRewardEvent) {
        super(dimension, pos, List.of(), beginAnnouncement, finishAnnouncement, finishAnnouncement, server, rewardList,
                randomAdditionalRewardEvent);
    }

    @Override
    protected void beginAction() {
        level().setBlockAndUpdate(new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), Blocks.CHEST.defaultBlockState());
    }

    private Style getRankStyle(int rank) {
        Style style;
        switch (rank) {
            case 1 -> style = CustomStyle.styleOfRed;
            case 2 -> style = CustomStyle.styleOfGold;
            case 3 -> style = CustomStyle.styleOfWorld;
            default -> style = CustomStyle.styleOfStone;
        }
        return style;
    }

    @Override
    protected void tick() {
        if (Tick.get() % 40 == 0) {
            if (!Compute.getNearEntity(level(), pos, Player.class, 64).isEmpty()) {
                FireWorkGun.summonFireWork(level(), pos.add(0, 2, 0));
            }
        }
        Compute.getNearEntity(level(), pos, Player.class, 6).forEach(p -> {
            Player player = (Player) p;
            if (!players.contains(player)) {
                players.add((Player) p);
                // 奖励
                int rank = players.size();
                // 根据rank提供一些奖励
                broad(Te.s(player.getDisplayName(), "在", "比拼谁跑得更快", CustomStyle.styleOfWorld,
                        "活动中，获得了第", String.valueOf(rank), getRankStyle(rank), "名!"));
                rankQueue.add(player);
            }
        });
    }

    @Override
    protected boolean finishCondition() {
        return beginTick + 5400 < Tick.get();
    }

    @Override
    protected void finishAction() {
        // 结束奖励 普遍奖励
        List<ItemStack> reward = List.of(
                new ItemStack(ModItems.GoldCoinBag.get(), 2),
                new ItemStack(ModItems.gemPiece.get(), 12),
                new ItemStack(ModItems.RevelationBook.get(), 5)
        );
        players.forEach(player -> {
            reward.forEach(stack -> {
                InventoryOperation.itemStackGive(player, new ItemStack(stack.getItem(), stack.getCount()));
            });
            Compute.playerReputationAddOrCost(player, 20);
        });
        broad(Te.s("比拼谁跑得更快活动结束了，下面宣布前几名的成绩单!"));
        int rankCount = 1;
        while (rankQueue.peek() != null && rankCount <= 5) {
            Player player = rankQueue.poll();
            broad(Te.s("第", String.valueOf(rankCount), getRankStyle(rankCount), "名:",
                    player.getDisplayName()));
            ++rankCount;
        }
    }

    @Override
    public void reset() {
        rankQueue.clear();
        level().destroyBlock(new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), false);
    }

    @Override
    protected String getDataKey() {
        return "URGENT_EVENT";
    }
}
