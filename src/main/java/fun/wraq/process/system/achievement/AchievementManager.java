package fun.wraq.process.system.achievement;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.PlayerDataUtil;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.achievement.networking.AchievementDataS2CPacket;
import fun.wraq.process.system.missions.mainline.MainQuestStage;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-30
 * 成就数据与触发入口。
 * <p>
 * 个人数据存放在玩家 PersistentData 的 AchievementData 下：
 * unlocked / progress / claimed / completedAt。成就是否达成由服务端判定。
 */
public final class AchievementManager {

    private static final String DATA_KEY = "AchievementData";
    private static final String UNLOCKED_KEY = "unlocked";
    private static final String PROGRESS_KEY = "progress";
    private static final String CLAIMED_KEY = "claimed";
    private static final String COMPLETED_AT_KEY = "completedAt";
    private static final String VERSION_KEY = "version";

    private AchievementManager() {
    }

    public static CompoundTag data(Player player) {
        CompoundTag data = PlayerDataUtil.getPlayerSpecificKeyCompoundTagData(player, DATA_KEY);
        if (!data.contains(VERSION_KEY)) {
            data.putInt(VERSION_KEY, 1);
        }
        section(data, UNLOCKED_KEY);
        section(data, PROGRESS_KEY);
        section(data, CLAIMED_KEY);
        section(data, COMPLETED_AT_KEY);
        return data;
    }

    private static CompoundTag section(CompoundTag data, String key) {
        if (!data.contains(key)) {
            data.put(key, new CompoundTag());
        }
        return data.getCompound(key);
    }

    public static boolean isUnlocked(Player player, String achievementId) {
        return section(data(player), UNLOCKED_KEY).getBoolean(achievementId);
    }

    public static boolean isClaimed(Player player, String achievementId) {
        return section(data(player), CLAIMED_KEY).getBoolean(achievementId);
    }

    public static int progress(Player player, String achievementId) {
        return section(data(player), PROGRESS_KEY).getInt(achievementId);
    }

    public static void sync(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ModNetworking.sendToClient(new AchievementDataS2CPacket(data(player).copy()), serverPlayer);
        }
    }

    public static void tick(Player player) {
        if (player.level().isClientSide || player.tickCount % 20 != 0) {
            return;
        }
        if (player.experienceLevel >= 100) {
            unlock(player, "level_100");
        }
        if (player.experienceLevel >= 135) {
            unlock(player, "level_135");
        }
        if (InventoryOperation.checkPlayerHasItem(player, ModItems.SKY_LINE.get(), 1)) {
            unlock(player, "collect_sky_line");
        }
        if (InventoryOperation.checkPlayerHasItem(player, ModItems.REASON.get(), 10)) {
            unlock(player, "collect_reason");
        }
    }

    public static void onMobKill(Player player, String mobName) {
        if (player == null || player.level().isClientSide) {
            return;
        }
        addProgress(player, "combat_first_blood", 1);
        addProgress(player, "combat_veteran", 1);
        if ("普莱尼".equals(mobName)) {
            addProgress(player, "combat_plain", 1);
        }
        if ("紫水晶巨蟹".equals(mobName)) {
            addProgress(player, "combat_purple_crab", 1);
        }
    }

    public static void onInstanceClear(Player player, String instanceName) {
        if (player == null || player.level().isClientSide) {
            return;
        }
        if ("普莱尼".equals(instanceName)) {
            addProgress(player, "instance_plain", 1);
            addProgress(player, "combat_plain", 1);
        }
        if ("紫水晶巨蟹".equals(instanceName)) {
            addProgress(player, "combat_purple_crab", 1);
        }
    }

    public static void onTowerClear(Player player, int towerIndex) {
        if (player == null || player.level().isClientSide) {
            return;
        }
        if (towerIndex == 0) {
            addProgress(player, "instance_tower_i", 1);
        }
    }

    public static void onMainQuestComplete(Player player, MainQuestStage.Stage stage) {
        if (player == null || player.level().isClientSide || stage == null) {
            return;
        }
        if ("main_01_depart_xusheng".equals(stage.id())) {
            addProgress(player, "explore_sky_arrival", 1);
        }
        if ("main_07_plaini".equals(stage.id())) {
            addProgress(player, "quest_main_07", 1);
        }
    }

    public static void onElementOpened(Player player) {
        if (player != null && !player.level().isClientSide) {
            addProgress(player, "quest_element", 1);
        }
    }

    public static void addProgress(Player player, String achievementId, int amount) {
        Achievement achievement = AchievementRegistry.byId(achievementId);
        if (achievement == null || player.level().isClientSide || isUnlocked(player, achievementId)) {
            return;
        }
        CompoundTag data = data(player);
        CompoundTag progress = section(data, PROGRESS_KEY);
        int current = Math.min(achievement.target(),
                progress.getInt(achievementId) + Math.max(1, amount));
        progress.putInt(achievementId, current);
        if (current >= achievement.target()) {
            unlock(player, achievementId);
        } else {
            sync(player);
        }
    }

    public static void unlock(Player player, String achievementId) {
        Achievement achievement = AchievementRegistry.byId(achievementId);
        if (achievement == null || player.level().isClientSide || isUnlocked(player, achievementId)) {
            return;
        }
        CompoundTag data = data(player);
        section(data, UNLOCKED_KEY).putBoolean(achievementId, true);
        section(data, PROGRESS_KEY).putInt(achievementId, achievement.target());
        section(data, COMPLETED_AT_KEY).putLong(achievementId, System.currentTimeMillis());
        sync(player);
        Compute.formatBroad(Te.s("成就", CustomStyle.styleOfGold),
                Te.s(player.getName(), " 达成了成就 ",
                        Te.s("「" + achievement.name() + "」", achievement.category().color())));
    }

    public static boolean claim(Player player, String achievementId) {
        Achievement achievement = AchievementRegistry.byId(achievementId);
        if (achievement == null || player.level().isClientSide
                || !isUnlocked(player, achievementId) || isClaimed(player, achievementId)) {
            return false;
        }
        for (Supplier<ItemStack> reward : achievement.rewards()) {
            ItemStack stack = reward.get();
            if (!stack.isEmpty()) {
                InventoryOperation.giveItemStackWithMSG(player, stack);
            }
        }
        section(data(player), CLAIMED_KEY).putBoolean(achievementId, true);
        sync(player);
        Compute.sendFormatMSG(player, Te.s("成就", CustomStyle.styleOfGold),
                Te.s("已领取成就奖励: ", achievement.name()));
        return true;
    }

    /** 供客户端界面读取成就列表。 */
    public static List<Achievement> all() {
        return AchievementRegistry.all();
    }
}
