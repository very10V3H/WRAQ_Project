package fun.wraq.process.system.missions.side;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.missions.mainline.MainQuestData;
import fun.wraq.process.system.missions.mainline.MainQuestHelper;
import fun.wraq.process.system.missions.mission2.MissionV2;
import fun.wraq.series.overworld.chapter1.plain.LifeSceptre;
import fun.wraq.series.overworld.chapter1.plain.PlainBow;
import fun.wraq.series.overworld.chapter1.plain.PlainSword;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * AI-Generated, 2026-10-26
 * 支线「锻造师」系列任务。
 * <p>
 * 「锻造师 I」在主线「平原清剿」完成后解锁；玩家成功锻造任意平原武器后
 * 自动计入完成条件，并通过任务系统自动提交、发放奖励。
 * 「锻造师 II」在 I 完成后解锁；把任意装备强化到 +2 即完成。
 */
public final class ForgeMasterQuest {

    private static final String FORGED_PLAIN_WEAPON_KEY = "SideForgeI_ForgedPlainWeapon";
    private static final String ENHANCED_TO_LEVEL_2_KEY = "SideForgeII_EnhancedToLevel2";

    private ForgeMasterQuest() {
    }

    /** 完成主线「平原清剿」后才允许接取锻造师 I。 */
    public static boolean canAccept(Player player) {
        return MainQuestHelper.status(player, MainQuestData.MAIN_05)
                .equals(MissionV2.Status.FINISHED);
    }

    /** 完成锻造师 I 后才允许接取锻造师 II。 */
    public static boolean canAcceptII(Player player) {
        return MissionV2.SIDE_FORGE_I.getPlayerStatus(player).equals(MissionV2.Status.FINISHED);
    }

    /** 解锁尚未初始化的锻造师支线。 */
    public static void refreshUnlock(Player player) {
        if (canAccept(player)
                && MissionV2.SIDE_FORGE_I.getPlayerStatus(player).isEmpty()) {
            MissionV2.SIDE_FORGE_I.setPlayerStatus(player, MissionV2.Status.NOT_ACCEPTED);
            MissionV2.sendDataToClient(player);
            MissionV2.sendMSG(player, Component.literal("新支线已解锁: ")
                    .append(MissionV2.SIDE_FORGE_I.title));
        }
        if (MissionV2.SIDE_FORGE_I.getPlayerStatus(player).equals(MissionV2.Status.FINISHED)
                && MissionV2.SIDE_FORGE_II.getPlayerStatus(player).isEmpty()) {
            MissionV2.SIDE_FORGE_II.setPlayerStatus(player, MissionV2.Status.NOT_ACCEPTED);
            MissionV2.sendDataToClient(player);
            MissionV2.sendMSG(player, Component.literal("新支线已解锁: ")
                    .append(MissionV2.SIDE_FORGE_II.title));
        }
    }

    /** 锻造成功时调用；只有进行中的锻造师 I 会记录进度。 */
    public static void onForge(Player player, Item forgedItem) {
        if (!isPlainWeapon(forgedItem)) {
            return;
        }
        if (!MissionV2.SIDE_FORGE_I.getPlayerStatus(player).equals(MissionV2.Status.IN_PROGRESS)) {
            return;
        }
        player.getPersistentData().putBoolean(FORGED_PLAIN_WEAPON_KEY, true);
        // 锻造完成的当 tick 直接尝试提交，避免等待任务轮询的 1s 延迟。
        MissionV2.autoSubmit(player);
        refreshUnlock(player);
    }

    /** 强化成功且强化等级首次达到 2 级时调用。 */
    public static void onEnhance(Player player) {
        if (!MissionV2.SIDE_FORGE_II.getPlayerStatus(player).equals(MissionV2.Status.IN_PROGRESS)) {
            return;
        }
        player.getPersistentData().putBoolean(ENHANCED_TO_LEVEL_2_KEY, true);
        MissionV2.autoSubmit(player);
    }

    public static boolean hasEnhancedToLevel2(Player player) {
        return player.getPersistentData().getBoolean(ENHANCED_TO_LEVEL_2_KEY);
    }

    public static boolean hasForgedPlainWeapon(Player player) {
        return player.getPersistentData().getBoolean(FORGED_PLAIN_WEAPON_KEY);
    }

    public static void claimReward(Player player) {
        InventoryOperation.giveItemStackWithMSG(player, ModItems.DIAMOND_HAMMER.get(), 5);
        InventoryOperation.giveItemStackWithMSG(player, ModItems.FORGING_STONE_0.get(), 16);
        player.getPersistentData().remove(FORGED_PLAIN_WEAPON_KEY);
    }

    public static List<Component> rewardDescription() {
        return ComponentUtils.getItemStackDescriptionList(List.of(
                new ItemStack(ModItems.DIAMOND_HAMMER.get(), 5),
                new ItemStack(ModItems.FORGING_STONE_0.get(), 16)
        ));
    }

    public static void claimRewardII(Player player) {
        player.getPersistentData().remove(ENHANCED_TO_LEVEL_2_KEY);
    }

    public static List<Component> rewardDescriptionII() {
        return List.of();
    }

    private static boolean isPlainWeapon(Item item) {
        return item instanceof PlainSword
                || item instanceof PlainBow
                || item instanceof LifeSceptre;
    }
}
