/** AI-Generated, 2026-10-17 */
package fun.wraq.process.system.npc;

import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.process.system.bank.Bank;
import fun.wraq.process.system.cooking.CookingVillager;
import fun.wraq.process.system.dialog.DialogManager;
import fun.wraq.process.system.missions.mainline.MainQuestVisibility;
import fun.wraq.process.system.profession.pet.allay.AllayPetPlayerData;
import fun.wraq.process.system.profession.smith.SmithPlayerData;
import fun.wraq.render.gui.trade.weekly.WeeklyStorePlayerData;
import fun.wraq.series.crystal.OriginStone;
import fun.wraq.series.events.midautumn.MidAutumnStoreRecipe;
import fun.wraq.series.events.summer2025.Summer2025StoreRecipe;
import fun.wraq.series.overworld.cold.sc5.dragon.IceDragonTpUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 * NPC 交互注册表：dialogId → 交互行为。
 * <p>
 * 改造前这些行为散落在 {@code VillagerEvents} 的一个 {@code switch} 里，
 * 以「显示名字符串」为键——改文案即断链，且无法给同名 NPC 配置不同行为。
 * 现在键是 {@link NpcIds} 里的稳定标识，显示名只负责显示。
 * <p>
 * 返回值语义：{@code true} 表示已经接手本次交互，调用方应取消该交互事件
 * （防止手里的物品被顺带使用）；{@code false} 表示只做了副作用、不拦截。
 */
public final class NpcInteractionRegistry {

    /** dialogId → (player, npc)。 */
    private static final Map<String, BiPredicate<Player, Entity>> HANDLERS = new HashMap<>();

    private NpcInteractionRegistry() {
    }

    static {
        // ---- 对话型 ----
        register(NpcIds.ENTRUSTMENT, (player, npc) -> {
            DialogManager.open(player, npc, NpcIds.ENTRUSTMENT);
            return true;
        });
        // ---- 直接打开界面 / 业务动作 ----
        register(NpcIds.BANK_CLERK, (player, npc) -> {
            Bank.onPlayerInteractWithVillager(player);
            return true;
        });
        register(NpcIds.ALLAY_SCHOLAR, (player, npc) -> {
            AllayPetPlayerData.onPlayerInteractWithVillager(player);
            return true;
        });
        register(NpcIds.SMITH_SCHOLAR, (player, npc) -> {
            SmithPlayerData.onPlayerInteractWithVillager(player);
            return true;
        });
        register(NpcIds.NEGENTROPY_SCHOLAR, (player, npc) -> {
            sendScreen(player, 6);
            // 与改造前一致：这里不取消交互事件
            return false;
        });
        register(NpcIds.LAOBA, (player, npc) -> {
            CookingVillager.onPlayerInteractWithVillager(player);
            return true;
        });
        register(NpcIds.ZONGZI_KING, (player, npc) -> {
            sendScreen(player, 9);
            return true;
        });
        register(NpcIds.WEEKLY_STORE, (player, npc) -> {
            WeeklyStorePlayerData.sendDataToClient(player);
            sendScreen(player, 10);
            return true;
        });
        register(NpcIds.ORIGIN_STONE, (player, npc) -> {
            OriginStone.onPlayerRightClick(player);
            return true;
        });
        register(NpcIds.ICE_DRAGON, (player, npc) -> {
            IceDragonTpUtil.onInteractWithVillager(player);
            return true;
        });
        register(NpcIds.SUMMER_2025_STORE, (player, npc) -> {
            sendScreen(player, 12);
            return true;
        });
        register(NpcIds.MID_AUTUMN_STORE, (player, npc) -> {
            sendScreen(player, 13);
            return true;
        });
        // ---- 主线任务 NPC：打开主线对话树 ----
        for (String mainQuestNpc : fun.wraq.process.system.missions.mainline.MainQuestData.NPC_IDS) {
            register(mainQuestNpc, (player, npc) -> {
                DialogManager.open(player, npc, mainQuestNpc);
                return true;
            });
        }
    }

    public static void register(String dialogId, BiPredicate<Player, Entity> handler) {
        HANDLERS.put(dialogId, handler);
    }

    /**
     * 处理一次 NPC 交互。
     *
     * @return {@code true} 表示已接手，调用方应取消交互事件
     */
    public static boolean handle(String dialogId, Player player, Entity npc) {
        // 主线阶段未到的 NPC 不响应交互（客户端被改包也无效）
        if (!MainQuestVisibility.isVisibleFor(player, dialogId)) {
            return true;
        }
        BiPredicate<Player, Entity> handler = HANDLERS.get(dialogId);
        return handler != null && handler.test(player, npc);
    }

    public static boolean isRegistered(String dialogId) {
        return HANDLERS.containsKey(dialogId);
    }

    private static void sendScreen(Player player, int id) {
        if (player instanceof ServerPlayer serverPlayer) {
            ModNetworking.sendToClient(new ScreenSetS2CPacket(id), serverPlayer);
        }
    }
}
