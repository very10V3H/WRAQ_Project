package fun.wraq.process.system.achievement;

import net.minecraft.ChatFormatting;

/**
 * AI-Generated, 2026-10-30
 * 成就分类。
 */
public enum AchievementCategory {

    EXPLORATION("探索"),
    COMBAT("战斗"),
    COLLECTION("收集"),
    INSTANCE("副本"),
    QUEST("任务"),
    OTHER("其他");

    public final String displayName;

    AchievementCategory(String displayName) {
        this.displayName = displayName;
    }

    public ChatFormatting color() {
        return switch (this) {
            case EXPLORATION -> ChatFormatting.AQUA;
            case COMBAT -> ChatFormatting.RED;
            case COLLECTION -> ChatFormatting.GREEN;
            case INSTANCE -> ChatFormatting.GOLD;
            case QUEST -> ChatFormatting.LIGHT_PURPLE;
            case OTHER -> ChatFormatting.GRAY;
        };
    }
}
