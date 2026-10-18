package fun.wraq.process.system.translevel;

import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 跨维度传送：维度标签注册表与玩家在该维度上次位置的持久化存取。
 * 新增维度时只需在标签映射中加入一行。
 * AI-Generated, 2026-08-23
 */
public class TransLevelDimension {

    public static final ResourceKey<Level> SURVIVAL_WORLD =
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Utils.MOD_ID, "survival_world"));

    /** 维度标签 → 维度 key，按插入顺序展示，便于命令补全。 */
    private static final Map<String, ResourceKey<Level>> LABEL_TO_DIMENSION = new LinkedHashMap<>();

    static {
        LABEL_TO_DIMENSION.put("survival", SURVIVAL_WORLD);
        LABEL_TO_DIMENSION.put("overworld", Level.OVERWORLD);
    }

    /** 根据标签查询维度 key，大小写不敏感；未命中返回 null。 */
    public static ResourceKey<Level> get(String label) {
        if (label == null) return null;
        return LABEL_TO_DIMENSION.get(label.toLowerCase());
    }

    public static Map<String, ResourceKey<Level>> labels() {
        return LABEL_TO_DIMENSION;
    }

    /**
     * 记录玩家在某个维度的当前位置。存储于玩家持久化 NBT：
     * getPersistentData().getCompound(StringUtils.TransLevel)
     *   -> 按维度 location 字符串分键 -> {x, y, z, yaw, pitch}
     */
    public static void recordPosition(Player player, ResourceKey<Level> dimension,
                                      Vec3 pos, float yaw, float pitch) {
        CompoundTag data = player.getPersistentData();
        CompoundTag transLevel = data.getCompound(StringUtils.TransLevel);
        CompoundTag dimTag = new CompoundTag();
        dimTag.putDouble("x", pos.x);
        dimTag.putDouble("y", pos.y);
        dimTag.putDouble("z", pos.z);
        dimTag.putFloat("yaw", yaw);
        dimTag.putFloat("pitch", pitch);
        transLevel.put(dimension.location().toString(), dimTag);
        data.put(StringUtils.TransLevel, transLevel);
    }

    /** 玩家在目标维度的上次位置；无记录返回 null。 */
    public static Vec3 getLastPosition(Player player, ResourceKey<Level> dimension) {
        CompoundTag transLevel = player.getPersistentData().getCompound(StringUtils.TransLevel);
        CompoundTag dimTag = transLevel.getCompound(dimension.location().toString());
        if (dimTag.isEmpty()) return null;
        return new Vec3(dimTag.getDouble("x"), dimTag.getDouble("y"), dimTag.getDouble("z"));
    }

    public static float getLastYaw(Player player, ResourceKey<Level> dimension) {
        return player.getPersistentData().getCompound(StringUtils.TransLevel)
                .getCompound(dimension.location().toString()).getFloat("yaw");
    }

    public static float getLastPitch(Player player, ResourceKey<Level> dimension) {
        return player.getPersistentData().getCompound(StringUtils.TransLevel)
                .getCompound(dimension.location().toString()).getFloat("pitch");
    }
}
