/** AI-Generated, 2026-07-19 */
package fun.wraq.process.system.worldtext;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.worldtext.networking.WorldTextS2CPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端世界文字数据管理器。
 * 由 {@code Compute.summonArmorStand} / {@code Compute.removeNearArmorStand} 驱动，
 * 记录当前所有应显示的世界文字条目（位置 + 文本），
 * 并在每个 ServerTick 中将有变更的维度同步给该维度内的所有玩家。
 */
public class WorldTextDataManager {

    public record Entry(Vec3 pos, Component text) {
    }

    private static final Map<ResourceKey<Level>, List<Entry>> ENTRIES = new HashMap<>();
    private static final Set<ResourceKey<Level>> DIRTY = ConcurrentHashMap.newKeySet();

    public static void addEntry(ServerLevel level, Vec3 pos, Component text) {
        ResourceKey<Level> dim = level.dimension();
        List<Entry> list = ENTRIES.computeIfAbsent(dim, k -> new ArrayList<>());
        int existingIdx = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).pos().equals(pos)) {
                existingIdx = i;
                break;
            }
        }
        if (existingIdx >= 0) {
            list.set(existingIdx, new Entry(pos, text));
        } else {
            list.add(new Entry(pos, text));
        }
        DIRTY.add(dim);
    }

    public static void removeEntriesInRadius(ServerLevel level, Vec3 pos, double radius) {
        ResourceKey<Level> dim = level.dimension();
        List<Entry> list = ENTRIES.get(dim);
        if (list == null || list.isEmpty()) return;
        double radiusSq = radius * radius;
        list.removeIf(entry -> entry.pos().distanceToSqr(pos) < radiusSq);
        DIRTY.add(dim);
    }

    /**
     * 每个 ServerTick 调用一次，将脏维度的条目快照同步给该维度所有在线玩家。
     */
    public static void tick(MinecraftServer server) {
        if (DIRTY.isEmpty()) return;
        List<ServerPlayer> allPlayers = server.getPlayerList().getPlayers();

        for (ResourceKey<Level> dim : DIRTY) {
            List<Entry> entries = ENTRIES.getOrDefault(dim, Collections.emptyList());
            if (entries.isEmpty()) continue;

            // 构建快照（防御性拷贝，防止迭代中被修改）
            List<Entry> snapshot = List.copyOf(entries);
            WorldTextS2CPacket packet = new WorldTextS2CPacket(dim, snapshot);

            // 仅发送给处于该维度的玩家
            for (ServerPlayer player : allPlayers) {
                if (player.level().dimension().equals(dim)) {
                    ModNetworking.sendToClient(packet, player);
                }
            }
        }

        DIRTY.clear();
    }

    /**
     * 服务器关闭时清理。
     */
    public static void clear() {
        ENTRIES.clear();
        DIRTY.clear();
    }
}
