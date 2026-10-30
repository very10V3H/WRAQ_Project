package fun.wraq.process.system.achievement;

import fun.wraq.common.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-30
 * 成就定义表。奖励在玩家手动领取时发放。
 */
public final class AchievementRegistry {

    private static final List<Achievement> ALL = List.of(
            new Achievement("explore_sky_arrival", AchievementCategory.EXPLORATION,
                    "初抵天空城", "抵达天空城，开始联合研院的考察。",
                    "完成主线阶段「出发：旭升岛轻轨」。", 1,
                    rewards(() -> new ItemStack(ModItems.COPPER_COIN.get(), 64))),
            new Achievement("combat_first_blood", AchievementCategory.COMBAT,
                    "初次交锋", "击败第一只怪物。",
                    "击败任意怪物 1 只。", 1,
                    rewards(() -> new ItemStack(ModItems.COPPER_COIN.get(), 32))),
            new Achievement("combat_veteran", AchievementCategory.COMBAT,
                    "百战余生", "在维瑞阿契大陆上积累战斗经验。",
                    "累计击败 100 只怪物。", 100,
                    rewards(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 5))),
            new Achievement("combat_plain", AchievementCategory.COMBAT,
                    "普莱尼的终结", "击败盘踞在平原村的普莱尼。",
                    "击败普莱尼。", 1,
                    rewards(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 10))),
            new Achievement("combat_purple_crab", AchievementCategory.COMBAT,
                    "紫晶克星", "击败紫水晶巨蟹。",
                    "击败紫水晶巨蟹。", 1,
                    rewards(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 10))),
            new Achievement("collect_sky_line", AchievementCategory.COLLECTION,
                    "天空科技", "获得副手 Sky-Line。",
                    "获得 Sky-Line。", 1,
                    rewards(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 5))),
            new Achievement("collect_reason", AchievementCategory.COLLECTION,
                    "理智储备", "随身携带一定数量的理智。",
                    "背包中拥有 10 个理智。", 10,
                    rewards(() -> new ItemStack(ModItems.COPPER_COIN.get(), 64))),
            new Achievement("instance_plain", AchievementCategory.INSTANCE,
                    "初入副本", "首次通关普莱尼副本。",
                    "通关普莱尼副本。", 1,
                    rewards(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 5))),
            new Achievement("instance_tower_i", AchievementCategory.INSTANCE,
                    "本源回廊-I", "完成本源回廊第 I 层的挑战。",
                    "完成本源回廊-I。", 1,
                    rewards(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 10))),
            new Achievement("quest_main_07", AchievementCategory.QUEST,
                    "雨林村的谢意", "完成雨林村的普莱尼主线。",
                    "完成主线阶段「击败普莱尼」。", 1,
                    rewards(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 10))),
            new Achievement("quest_element", AchievementCategory.QUEST,
                    "元素学入门", "完成元素学第一课。",
                    "完成支线「元素学I」。", 1,
                    rewards(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 5))),
            new Achievement("level_100", AchievementCategory.OTHER,
                    "百级考察员", "达到 100 级。",
                    "等级达到 100 级。", 1,
                    rewards(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 10))),
            new Achievement("level_135", AchievementCategory.OTHER,
                    "元素之友", "达到 135 级，解锁元素轮盘。",
                    "等级达到 135 级。", 1,
                    rewards(() -> new ItemStack(ModItems.WORLD_SOUL_5.get(), 15)))
    );

    private static final Map<String, Achievement> BY_ID = new LinkedHashMap<>();

    static {
        for (Achievement achievement : ALL) {
            BY_ID.put(achievement.id(), achievement);
        }
    }

    private AchievementRegistry() {
    }

    @SafeVarargs
    private static List<Supplier<ItemStack>> rewards(Supplier<ItemStack>... suppliers) {
        return List.of(suppliers);
    }

    public static List<Achievement> all() {
        return ALL;
    }

    public static Achievement byId(String id) {
        return BY_ID.get(id);
    }
}
