package fun.wraq.process.system.achievement;

import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-30
 * 单个成就定义。成就本身不进入玩家存档，玩家存档只记录进度/解锁/领取状态。
 */
public record Achievement(String id, AchievementCategory category, String name, String description,
                          String acquisition, int target, List<Supplier<ItemStack>> rewards) {
}
