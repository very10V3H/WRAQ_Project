/** AI-Generated, 2026-10-25 */
package fun.wraq.process.system.mastery;

import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 玩家精通数据的轻量快照缓存。
 *
 * <p>服务端从 PersistentData 读取并缓存；客户端使用 {@link MasteryClientData}，
 * 通过 revision 判断是否需要重算。属性计算不要在每次攻击时重新读 NBT。</p>
 */
public final class MasteryBonusCache {

    public record Snapshot(MasteryStats stats, int[][] allocation) {
        public Snapshot {
            allocation = MasteryAllocation.copy(allocation);
        }

        public static Snapshot of(int[][] allocation) {
            return new Snapshot(MasteryStats.of(allocation), allocation);
        }

        public int points(MasteryTree tree, int row, int node) {
            return allocation[tree.ordinal()][MasteryAllocation.index(row, node)];
        }
    }

    private static final Snapshot EMPTY =
            new Snapshot(MasteryStats.EMPTY, MasteryAllocation.empty());
    private static final Map<Player, Snapshot> SERVER_CACHE = new WeakHashMap<>();

    private static volatile int clientRevision = -1;
    private static volatile Snapshot clientSnapshot = EMPTY;

    private MasteryBonusCache() {
    }

    public static Snapshot get(Player player) {
        if (player.level().isClientSide) {
            if (clientRevision != MasteryClientData.revision) {
                clientRevision = MasteryClientData.revision;
                clientSnapshot = Snapshot.of(MasteryClientData.allocation);
            }
            return clientSnapshot;
        }
        return SERVER_CACHE.computeIfAbsent(player,
                p -> Snapshot.of(MasteryData.read(p.getPersistentData())));
    }

    public static MasteryStats stats(Player player) {
        return get(player).stats();
    }

    public static void invalidate(Player player) {
        SERVER_CACHE.remove(player);
    }
}
