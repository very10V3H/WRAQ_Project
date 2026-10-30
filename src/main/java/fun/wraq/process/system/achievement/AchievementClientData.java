package fun.wraq.process.system.achievement;

import net.minecraft.nbt.CompoundTag;

/**
 * AI-Generated, 2026-10-30
 * 客户端成就数据缓存。
 */
public final class AchievementClientData {

    public static CompoundTag data = new CompoundTag();

    private AchievementClientData() {
    }

    public static boolean isUnlocked(String id) {
        return data.getCompound("unlocked").getBoolean(id);
    }

    public static boolean isClaimed(String id) {
        return data.getCompound("claimed").getBoolean(id);
    }

    public static int progress(String id) {
        return data.getCompound("progress").getInt(id);
    }
}
