package fun.wraq.process.system.backpack.audit;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AI-Generated, 2026-10-18
 * 精妙背包「按内容 UUID」的独占锁：同一时刻只允许一方打开同一背包。
 *
 * <p>背景：精妙背包的内容存在世界的 {@code BackpackStorage} 里，但每个
 * {@code BackpackWrapper} 都会在自己内存里持有一份 {@code InventoryHandler}，
 * 任何一次改动都会把整份背包序列化写回共享 NBT（last write wins）。
 * 因此只要保证「同一 UUID 同时只有一个菜单」，就不存在并发覆写——
 * 一致性由「单写入者」构造保证，不需要双写同步。
 *
 * <p>锁只保存在内存中（服务器重启即清空），避免留下「死锁存档」。
 * 释放时机：GUI 关闭、玩家登出、以及每 20 tick 的兜底校验。
 */
public final class BackpackLockManager {

    /**
     * AI-Generated, 2026-10-18
     *
     * @param contentsUuid 精妙背包内容 UUID
     * @param holder       持有者
     * @param holderName   持有者名字（用于提示）
     * @param menuId       绑定的容器窗口 id（释放时校验，避免误释放）
     * @param since        加锁时的游戏时间（tick）
     */
    public record Lock(UUID contentsUuid, UUID holder, String holderName, int menuId, long since) {
    }

    private static final Map<UUID, Lock> LOCKS = new ConcurrentHashMap<>();

    private BackpackLockManager() {
    }

    public static Lock get(UUID contentsUuid) {
        return LOCKS.get(contentsUuid);
    }

    public static boolean isLockedByOther(UUID contentsUuid, UUID player) {
        Lock lock = LOCKS.get(contentsUuid);
        if (lock == null || lock.holder().equals(player)) {
            return false;
        }
        // 惰性校验：持有者已经关掉界面时立刻视为无锁，避免 20 tick 校验间隔内的误拦截
        if (!isStillHeld(lock)) {
            LOCKS.remove(contentsUuid, lock);
            return false;
        }
        return true;
    }

    /** 锁是否仍然有效（持有者在线且正打开着绑定的那个菜单） */
    private static boolean isStillHeld(Lock lock) {
        if (lock.menuId() < 0) {
            // 预占（命令已下发、界面尚未打开），由 tick 超时兜底
            return true;
        }
        MinecraftServer server = net.minecraftforge.server.ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return true;
        }
        ServerPlayer holder = server.getPlayerList().getPlayer(lock.holder());
        return holder != null && holder.containerMenu != null
                && holder.containerMenu.containerId == lock.menuId();
    }

    /**
     * 尝试获取锁。
     *
     * @return null 表示获取成功；否则返回挡路的锁
     */
    public static Lock tryAcquire(UUID contentsUuid, ServerPlayer player, int menuId) {
        Lock existing = LOCKS.get(contentsUuid);
        if (existing != null && !existing.holder().equals(player.getUUID())) {
            return existing;
        }
        LOCKS.put(contentsUuid, new Lock(contentsUuid, player.getUUID(),
                player.getName().getString(), menuId, player.level().getGameTime()));
        return null;
    }

    /** 仅当 (持有者, 窗口 id) 都对得上时才释放 */
    public static void release(UUID contentsUuid, UUID player, int menuId) {
        Lock lock = LOCKS.get(contentsUuid);
        if (lock != null && lock.holder().equals(player) && lock.menuId() == menuId) {
            LOCKS.remove(contentsUuid, lock);
        }
    }

    /** 玩家登出/离开时释放其持有的全部锁 */
    public static void releaseAll(UUID player) {
        for (Map.Entry<UUID, Lock> entry : LOCKS.entrySet()) {
            if (entry.getValue().holder().equals(player)) {
                LOCKS.remove(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 每 20 tick 调用：持有者离线、已经换过菜单、或预占超时未真正打开界面，就释放（兜底，防呆锁）。
     */
    public static void validate(MinecraftServer server, long gameTime) {
        if (server == null || LOCKS.isEmpty()) {
            return;
        }
        for (Map.Entry<UUID, Lock> entry : LOCKS.entrySet()) {
            Lock lock = entry.getValue();
            ServerPlayer holder = server.getPlayerList().getPlayer(lock.holder());
            boolean release;
            if (holder == null) {
                release = true;
            } else if (lock.menuId() < 0) {
                // 命令已下发、界面还没打开（等客户端回执）：超时释放，避免死锁
                release = gameTime - lock.since() > 100;
            } else {
                release = holder.containerMenu == null
                        || holder.containerMenu.containerId != lock.menuId();
            }
            if (release) {
                LOCKS.remove(entry.getKey(), lock);
            }
        }
    }

    /** 服务器关闭/整服清理 */
    public static void clear() {
        LOCKS.clear();
    }

    /** 给玩家看的持锁描述，如「Steve（12 秒前）」 */
    public static String describe(Lock lock, long gameTime) {
        long seconds = Math.max(0, (gameTime - lock.since()) / 20);
        return lock.holderName() + "（" + seconds + " 秒前）";
    }
}
