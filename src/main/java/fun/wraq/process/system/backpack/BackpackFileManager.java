package fun.wraq.process.system.backpack;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AI-Generated, 2026-07-12
 * 一玩家一独立 NBT 文件管理器。
 * <p>
 * 文件位置：<world>/data/vmd/backpacks/<UUID>.dat
 * 懒加载：首次访问某玩家时从磁盘读取，之后缓存。
 * 写回：通过 scheduleSave() 标记脏数据，在服务器 tick 或关闭时批量写入。
 */
public class BackpackFileManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackpackFileManager.class);

    private static final Map<UUID, BackpackData> CACHE = new ConcurrentHashMap<>();
    private static final Map<UUID, Boolean> DIRTY = new ConcurrentHashMap<>();

    private static Path BACKPACK_DIR = null;

    /** 初始化背包存储目录（在服务器启动时调用） */
    public static void init(MinecraftServer server) {
        Path worldPath = server.getWorldPath(LevelResource.ROOT);
        BACKPACK_DIR = worldPath.resolve("data/vmd/backpacks");
        try {
            Files.createDirectories(BACKPACK_DIR);
            LOGGER.info("Backpack storage initialized at: {}", BACKPACK_DIR);
        } catch (IOException e) {
            LOGGER.error("Failed to create backpack directory", e);
        }
    }

    private static Path fileFor(UUID uuid) {
        return BACKPACK_DIR.resolve(uuid + ".dat");
    }

    /** 获取玩家背包数据（懒加载） */
    public static BackpackData get(UUID uuid) {
        BackpackData data = CACHE.computeIfAbsent(uuid, key -> {
            Path file = fileFor(key);
            if (Files.exists(file)) {
                return loadFromDisk(file);
            }
            return new BackpackData();
        });
        // 迁移：缓存中的老存档不足 8 行（72 格）自动补齐
        if (data.getSlotCount() < 72) {
            int rowsToAdd = (72 - data.getSlotCount() + 8) / 9;
            for (int i = 0; i < rowsToAdd; i++) {
                data.expandRow();
            }
            markDirty(uuid);
        }
        return data;
    }

    /** 获取玩家背包数据，targetUuid 可不同于在线玩家——支持管理员查看离线玩家 */
    public static BackpackData getForPlayer(MinecraftServer server, UUID targetUuid) {
        return get(targetUuid);
    }

    private static BackpackData loadFromDisk(Path file) {
        try {
            CompoundTag tag = NbtIo.readCompressed(file.toFile());
            BackpackData data = new BackpackData();
            data.deserializeNBT(tag);
            return data;
        } catch (IOException e) {
            LOGGER.error("Failed to load backpack from {}", file, e);
            return new BackpackData();
        }
    }

    /** 标记某玩家背包为脏（需要写回磁盘） */
    public static void markDirty(UUID uuid) {
        DIRTY.put(uuid, true);
    }

    /** 立即写回单玩家背包 */
    public static void save(UUID uuid) {
        Path file = fileFor(uuid);
        try {
            Files.createDirectories(file.getParent());
            CompoundTag tag = get(uuid).serializeNBT();
            NbtIo.writeCompressed(tag, file.toFile());
            DIRTY.remove(uuid);
        } catch (IOException e) {
            LOGGER.error("Failed to save backpack for {}", uuid, e);
        }
    }

    /** 保存所有脏背包（服务器关闭时调用） */
    public static void saveAll() {
        DIRTY.keySet().forEach(BackpackFileManager::save);
        LOGGER.info("All backpacks saved.");
    }

    /** 从缓存中卸载某玩家（登出时调用） */
    public static void unload(UUID uuid) {
        if (DIRTY.containsKey(uuid)) {
            save(uuid);
        }
        CACHE.remove(uuid);
        DIRTY.remove(uuid);
    }

    /** 强制重载某玩家背包（从磁盘） */
    public static void reload(UUID uuid) {
        CACHE.remove(uuid);
        DIRTY.remove(uuid);
        get(uuid);
    }

    /** 服务器停止时清理 */
    public static void shutdown() {
        saveAll();
        CACHE.clear();
        DIRTY.clear();
    }
}
