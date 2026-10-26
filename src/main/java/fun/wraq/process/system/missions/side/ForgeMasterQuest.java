package fun.wraq.process.system.missions.side;

import fun.wraq.common.fast.Te;
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
 * 「锻造师 I」在主线「平原清剿」完成后解锁；玩家成功锻造任意平原武器后完成。
 * 「锻造师 II」在 I 完成后解锁；把任意装备强化到 +2 即完成，奖励森林意志。
 * 「锻造师 III」在 II 完成后解锁；锻造森林粉碎者、森林长弓或生机权杖-X，
 * 完成后返还对应的平原/生机基础武器。
 */
public final class ForgeMasterQuest {

    private static final String FORGED_PLAIN_WEAPON_KEY = "SideForgeI_ForgedPlainWeapon";
    private static final String ENHANCED_TO_LEVEL_2_KEY = "SideForgeII_EnhancedToLevel2";
    private static final String FORGED_FORGE_III_KEY = "SideForgeIII_ForgedWeaponType";

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

    /** 完成锻造师 II 后才允许接取锻造师 III。 */
    public static boolean canAcceptIII(Player player) {
        return MissionV2.SIDE_FORGE_II.getPlayerStatus(player).equals(MissionV2.Status.FINISHED);
    }

    /** 解锁尚未初始化的锻造师支线。 */
    public static void refreshUnlock(Player player) {
        if (canAccept(player)
                && MissionV2.SIDE_FORGE_I.getPlayerStatus(player).isEmpty()) {
            MissionV2.SIDE_FORGE_I.setPlayerStatus(player, MissionV2.Status.NOT_ACCEPTED);
            MissionV2.sendDataToClient(player);
            MissionV2.sendMsgForUnlock(player, MissionV2.SIDE_FORGE_I);
        }
        if (MissionV2.SIDE_FORGE_I.getPlayerStatus(player).equals(MissionV2.Status.FINISHED)
                && MissionV2.SIDE_FORGE_II.getPlayerStatus(player).isEmpty()) {
            MissionV2.SIDE_FORGE_II.setPlayerStatus(player, MissionV2.Status.NOT_ACCEPTED);
            MissionV2.sendDataToClient(player);
            MissionV2.sendMsgForUnlock(player, MissionV2.SIDE_FORGE_II);
        }
        if (MissionV2.SIDE_FORGE_II.getPlayerStatus(player).equals(MissionV2.Status.FINISHED)
                && MissionV2.SIDE_FORGE_III.getPlayerStatus(player).isEmpty()) {
            MissionV2.SIDE_FORGE_III.setPlayerStatus(player, MissionV2.Status.NOT_ACCEPTED);
            MissionV2.sendDataToClient(player);
            MissionV2.sendMsgForUnlock(player, MissionV2.SIDE_FORGE_III);
        }
    }

    /** 锻造成功时调用；同时处理锻造师 I 和锻造师 III。 */
    public static void onForge(Player player, Item forgedItem) {
        if (isPlainWeapon(forgedItem)
                && MissionV2.SIDE_FORGE_I.getPlayerStatus(player).equals(MissionV2.Status.IN_PROGRESS)) {
            player.getPersistentData().putBoolean(FORGED_PLAIN_WEAPON_KEY, true);
            // 锻造完成的当 tick 直接尝试提交，避免等待任务轮询的 1s 延迟。
            MissionV2.autoSubmit(player);
            refreshUnlock(player);
        }
        onCraftedForIII(player, forgedItem);
    }

    /** 灌注完成时调用；用于生机权杖-X 等灌注产物。 */
    public static void onInjected(Player player, Item product) {
        onCraftedForIII(player, product);
    }

    private static void onCraftedForIII(Player player, Item item) {
        if (!MissionV2.SIDE_FORGE_III.getPlayerStatus(player).equals(MissionV2.Status.IN_PROGRESS)) {
            return;
        }
        String type = null;
        if (item == ModItems.FOREST_SWORD_0.get() || item == ModItems.FOREST_SWORD_4.get()) {
            type = "sword";
        } else if (item == ModItems.FOREST_BOW_0.get()) {
            type = "bow";
        } else if (item == ModItems.LIFE_SCEPTRE_X.get()) {
            type = "sceptre";
        }
        if (type == null) {
            return;
        }
        player.getPersistentData().putString(FORGED_FORGE_III_KEY, type);
        MissionV2.autoSubmit(player);
    }

    /** 强化成功且强化等级首次达到 2 级时调用。 */
    public static void onEnhance(Player player) {
        if (!MissionV2.SIDE_FORGE_II.getPlayerStatus(player).equals(MissionV2.Status.IN_PROGRESS)) {
            return;
        }
        player.getPersistentData().putBoolean(ENHANCED_TO_LEVEL_2_KEY, true);
        MissionV2.autoSubmit(player);
        refreshUnlock(player);
    }

    public static boolean hasEnhancedToLevel2(Player player) {
        return player.getPersistentData().getBoolean(ENHANCED_TO_LEVEL_2_KEY);
    }

    public static boolean hasForgedPlainWeapon(Player player) {
        return player.getPersistentData().getBoolean(FORGED_PLAIN_WEAPON_KEY);
    }

    public static boolean hasForgedIII(Player player) {
        return !player.getPersistentData().getString(FORGED_FORGE_III_KEY).isEmpty();
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
        InventoryOperation.giveItemStackWithMSG(player, ModItems.FOREST_RUNE.get(), 2);
        player.getPersistentData().remove(ENHANCED_TO_LEVEL_2_KEY);
    }

    public static List<Component> rewardDescriptionII() {
        return ComponentUtils.getItemStackDescriptionList(List.of(
                new ItemStack(ModItems.FOREST_RUNE.get(), 2)
        ));
    }

    public static void claimRewardIII(Player player) {
        String type = player.getPersistentData().getString(FORGED_FORGE_III_KEY);
        Item reward = switch (type) {
            case "sword" -> ModItems.PLAIN_SWORD_0.get();
            case "bow" -> ModItems.PLAIN_BOW_0.get();
            case "sceptre" -> ModItems.LIFE_SCEPTRE_0.get();
            default -> null;
        };
        if (reward != null) {
            InventoryOperation.giveItemStackWithMSG(player, reward, 1);
        }
        player.getPersistentData().remove(FORGED_FORGE_III_KEY);
    }

    public static List<Component> rewardDescriptionIII() {
        return List.of(
                Te.s("森林粉碎者 → ", new ItemStack(ModItems.PLAIN_SWORD_0.get())),
                Te.s("森林长弓 → ", new ItemStack(ModItems.PLAIN_BOW_0.get())),
                Te.s("生机权杖-X → ", new ItemStack(ModItems.LIFE_SCEPTRE_0.get()))
        );
    }

    private static boolean isPlainWeapon(Item item) {
        return item instanceof PlainSword
                || item instanceof PlainBow
                || item instanceof LifeSceptre;
    }
}
