package fun.wraq.process.system.dialog;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustment;
import fun.wraq.process.system.missions.mainline.MainQuestDialogue;
import fun.wraq.process.system.npc.NpcIds;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI-Generated, 2026-10-17
 * 对话树注册表与动作分发。
 * <p>
 * 第一个样板：{@link MobKillEntrustment#VILLAGER_NAME}（联合研院秘书 - 贝尔）。
 * 旧实现 {@code MobKillEntrustment#onPlayerInteractWithVillager} 用聊天窗 + 5 条
 * {@code /vmd entrustment ...} 命令承载对话，且用 4 个分支手工判断状态；这里改为
 * 「按当前状态构建一页对话」，动作通过返回下一页或接管界面来表达。
 */
public final class DialogTrees {

    /** 样板 NPC：贝尔。 */
    public static final String ENTRUSTMENT_NPC = MobKillEntrustment.VILLAGER_NAME;

    public static final String ACTION_ACCEPT = "accept";
    public static final String ACTION_CANCEL = "cancel";
    public static final String ACTION_STORE = "openStore";
    public static final String ACTION_RANK = "enhanceRank";
    public static final String ACTION_DAILY = "queryDaily";
    public static final String ACTION_WEEKLY = "queryWeekly";
    public static final String ACTION_BACK = "back";

    private DialogTrees() {
    }

    /** dialogId → 对话树。注册制，新增 NPC 只需实现 {@link DialogTree} 并注册。 */
    private static final Map<String, DialogTree> TREES = new HashMap<>();

    static {
        DialogTree entrustment = new EntrustmentTree();
        register(NpcIds.ENTRUSTMENT, entrustment);
        // 兼容：旧调用方（例如 MobKillEntrustment）仍会把显示名当 id 传进来
        register(ENTRUSTMENT_NPC, entrustment);
        // 主线任务 NPC：共用一棵数据驱动对话树
        for (String mainQuestNpc : fun.wraq.process.system.missions.mainline.MainQuestData.NPC_IDS) {
            register(mainQuestNpc, MainQuestDialogue.INSTANCE);
        }
    }

    /** 注册一棵对话树。 */
    public static void register(String dialogId, DialogTree tree) {
        TREES.put(dialogId, tree);
    }

    /**
     * 打开对话前执行的自动行为。返回需要显示在首页顶部的提示（可为 null）。
     * <p>
     * 旧实现在「委托已完成」时会直接提交并结束交互，这里保留该语义，把结果作为提示写进第一页。
     */
    public static Component onOpen(Player player, String npcId) {
        DialogTree tree = TREES.get(npcId);
        return tree == null ? null : tree.onOpen(player, npcId);
    }

    /** 构建当前状态对应的一页对话。 */
    public static DialogPage build(Player player, String npcId, String sessionId, Component notice) {
        DialogTree tree = TREES.get(npcId);
        return tree == null ? buildUnknown(npcId, sessionId, notice) : tree.buildPage(player, npcId, sessionId, notice);
    }

    /**
     * 分发一次选项点击。返回下一页对话；返回 null 表示动作接管了界面（或主动结束）。
     */
    public static DialogPage handle(Player player, String npcId, String sessionId, String actionId) {
        DialogTree tree = TREES.get(npcId);
        return tree == null ? null : tree.handle(player, npcId, sessionId, actionId);
    }

    // ------------------------------------------------------------------
    // 贝尔：动作分发
    // ------------------------------------------------------------------

    private static DialogPage handleEntrustment(Player player, String npcId, String sessionId, String actionId) {
        switch (actionId) {
            case ACTION_ACCEPT -> {
                MobKillEntrustment.playerTryToAcceptFromNpc(player);
                return build(player, npcId, sessionId, null);
            }
            case ACTION_CANCEL -> {
                MobKillEntrustment.playerTryToCancelFromNpc(player);
                return build(player, npcId, sessionId, null);
            }
            case ACTION_STORE -> {
                DialogManager.close(player);
                MobKillEntrustment.openStore(player);
                return null;
            }
            case ACTION_RANK -> {
                RankData.onTryToEnhanceRank(player);
                return build(player, npcId, sessionId, null);
            }
            case ACTION_DAILY -> {
                return buildDaily(player, npcId, sessionId);
            }
            case ACTION_WEEKLY -> {
                return buildWeekly(player, npcId, sessionId);
            }
            case ACTION_BACK -> {
                return build(player, npcId, sessionId, null);
            }
            default -> {
                return null;
            }
        }
    }

    // ------------------------------------------------------------------
    // 贝尔：四态对话树（有委托未完成 / 冷却中 / 空闲）
    // ------------------------------------------------------------------

    private static DialogPage buildEntrustment(Player player, String dialogId, String sessionId, Component notice) {
        List<Component> lines = new ArrayList<>();
        List<DialogOption> options = new ArrayList<>();
        String name = player.getName().getString();
        MobKillEntrustment entrustment = MobKillEntrustment.playerCurrentEntrustmentMap.get(name);
        int nextAllowTick = MobKillEntrustment.playerNextAllowAcceptTickMap.getOrDefault(name, 0);

        if (notice != null) {
            lines.add(notice);
        }

        if (entrustment != null) {
            // 有进行中的委托（opened 时已完成的情况已被 onOpen 提交掉，走到这里一定是未完成）
            int progress = MobKillEntrustment.playerKillProcessMap.getOrDefault(name, 0);
            lines.add(Te.s("你手上还有一份没做完的委托。"));
            lines.add(Te.s("目标：击杀 ", entrustment.targetCount + " 只", ChatFormatting.RED, " ",
                    entrustment.mobName));
            lines.add(Te.s("进度：", progress + " / " + entrustment.targetCount,
                    progress >= entrustment.targetCount ? ChatFormatting.GREEN : ChatFormatting.YELLOW));
            options.add(option(ACTION_CANCEL, Te.s("「取消委托」", ChatFormatting.RED),
                    Te.s("取消后需要等待 ", "5min", ChatFormatting.AQUA, " 才能再次接取")));
            options.add(option(ACTION_STORE, Te.s("「打开委托商店」", CustomStyle.styleOfWorld),
                    Te.s("用委托债券兑换奖励")));
            options.add(option(ACTION_DAILY, Te.s("「查看每日进度」", ChatFormatting.LIGHT_PURPLE),
                    Te.s("查看每日完成次数与奖励档位")));
            options.add(option(ACTION_WEEKLY, Te.s("「查看每周进度」", ChatFormatting.LIGHT_PURPLE),
                    Te.s("查看每周完成次数与奖励档位")));
        } else if (nextAllowTick > Tick.get()) {
            // 冷却中：旧实现在这个状态只给一个「打开商店」命令
            lines.add(Te.s("暂时还不能接取新的委托。"));
            lines.add(Te.s("还需要等待 ",
                    MobKillEntrustment.getDeltaTimeFormatString(nextAllowTick, Tick.get()),
                    CustomStyle.styleOfWorld, "。"));
            options.add(option(ACTION_STORE, Te.s("「打开委托商店」", CustomStyle.styleOfWorld),
                    Te.s("用委托债券兑换奖励")));
            options.add(option(ACTION_DAILY, Te.s("「查看每日进度」", ChatFormatting.LIGHT_PURPLE),
                    Te.s("查看每日完成次数与奖励档位")));
            options.add(option(ACTION_WEEKLY, Te.s("「查看每周进度」", ChatFormatting.LIGHT_PURPLE),
                    Te.s("查看每周完成次数与奖励档位")));
        } else {
            // 空闲：旧实现在这个状态塞了 5 条命令消息
            lines.add(Te.s("近期如何，", name, "？"));
            lines.add(Te.s("我这边有一份狩猎委托，需要达到 ", "75 级", ChatFormatting.YELLOW, " 才能接取。"));
            options.add(option(ACTION_ACCEPT, Te.s("「接取委托」", ChatFormatting.GREEN),
                    Te.s("随机分配一个当前可接取的击杀委托")));
            options.add(option(ACTION_STORE, Te.s("「打开委托商店」", CustomStyle.styleOfWorld),
                    Te.s("用委托债券兑换奖励")));
            options.add(option(ACTION_RANK, Te.s("「提升 Rank」", ChatFormatting.GOLD),
                    Te.s("尝试使用 ", ModItems.SPECIAL_BOND.get().getDefaultInstance(), " 提升 Rank")));
            options.add(option(ACTION_DAILY, Te.s("「查看每日进度」", ChatFormatting.LIGHT_PURPLE),
                    Te.s("查看每日完成次数与奖励档位")));
            options.add(option(ACTION_WEEKLY, Te.s("「查看每周进度」", ChatFormatting.LIGHT_PURPLE),
                    Te.s("查看每周完成次数与奖励档位")));
        }

        return new DialogPage(dialogId, sessionId,
                Te.s("联合研院秘书 - 贝尔", CustomStyle.styleOfWorld), lines, options);
    }

    // ------------------------------------------------------------------
    // 进度页：把原来直接刷屏的 queryDaily/queryWeekly 变成一页可读的正文
    // ------------------------------------------------------------------

    private static DialogPage buildDaily(Player player, String npcId, String sessionId) {
        int current = MobKillEntrustment.getDailyFinishedTimes(player);
        int level = player.experienceLevel;
        List<Component> lines = new ArrayList<>();
        lines.add(Te.s("委托任务每日进度奖励", CustomStyle.styleOfWorld));
        lines.add(Te.s("今天已完成 ", current + " 次", CustomStyle.styleOfWorld));
        lines.add(Te.s(" ", MobKillEntrustment.getStatus(current, 5), "5 ", ChatFormatting.GREEN,
                " -> ", ModItems.TP_TICKET, " * 2", " + ", ModItems.WORLD_SOUL_5, " * 5",
                " + ", level * 50 + "VB", CustomStyle.styleOfGold));
        lines.add(Te.s(" ", MobKillEntrustment.getStatus(current, 10), "10", ChatFormatting.YELLOW,
                " -> ", ModItems.TP_TICKET, " * 2", " + ", ModItems.BOND, " * 1", " + ",
                ModItems.GOLDEN_BEANS, " * 2", " + ", level * 100 + "VB", CustomStyle.styleOfGold));
        lines.add(Te.s(" ", MobKillEntrustment.getStatus(current, 15), "15", ChatFormatting.LIGHT_PURPLE,
                " -> ", ModItems.TP_TICKET, " * 3", " + ", ModItems.WORLD_SOUL_5, " * 5",
                " + ", level * 150 + "VB", CustomStyle.styleOfGold));
        lines.add(Te.s(" ", MobKillEntrustment.getStatus(current, 20), "20", ChatFormatting.RED,
                " -> ", ModItems.TP_TICKET, " * 3", " + ", ModItems.JUNIOR_SUPPLY, " * 1",
                " + ", level * 200 + "VB", CustomStyle.styleOfGold));
        return new DialogPage(npcId, sessionId,
                Te.s("联合研院秘书 - 贝尔", CustomStyle.styleOfWorld), lines, progressOptions());
    }

    private static DialogPage buildWeekly(Player player, String npcId, String sessionId) {
        int current = MobKillEntrustment.getWeeklyFinishedTimes(player);
        List<Component> lines = new ArrayList<>();
        lines.add(Te.s("委托任务每周进度奖励", CustomStyle.styleOfWorld));
        lines.add(Te.s("本周已完成 ", current + " 次", CustomStyle.styleOfWorld));
        lines.add(Te.s(" ", MobKillEntrustment.getStatus(current, 20), "20 ", ChatFormatting.GREEN,
                " -> ", ModItems.SPECIAL_BOND, " * 1", " + ", ModItems.WORLD_SOUL_5, " * 10",
                " + ", ModItems.SUPPLY_BOX_TIER_0, " * 1"));
        lines.add(Te.s(" ", MobKillEntrustment.getStatus(current, 40), "40 ", ChatFormatting.AQUA,
                " -> ", ModItems.SPECIAL_BOND, " * 1", " + ", ModItems.WORLD_SOUL_5, " * 20",
                " + ", ModItems.ORE_SUPPLY, " * 1"));
        lines.add(Te.s(" ", MobKillEntrustment.getStatus(current, 60), "60 ", ChatFormatting.YELLOW,
                " -> ", ModItems.SPECIAL_BOND, " * 1", " + ", ModItems.WORLD_SOUL_5, " * 30",
                " + ", ModItems.JUNIOR_SUPPLY, " * 1"));
        lines.add(Te.s(" ", MobKillEntrustment.getStatus(current, 80), "80 ", ChatFormatting.LIGHT_PURPLE,
                " -> ", ModItems.SPECIAL_BOND, " * 1", " + ", ModItems.WORLD_SOUL_5, " * 40",
                " + ", ModItems.SENIOR_POTION_SUPPLY, " * 1"));
        lines.add(Te.s(" ", MobKillEntrustment.getStatus(current, 100), "100", ChatFormatting.RED,
                " -> ", ModItems.SPECIAL_BOND, " * 1", " + ", ModItems.WORLD_SOUL_5, " * 50",
                " + ", ModItems.SENIOR_SUPPLY, " * 1"));
        return new DialogPage(npcId, sessionId,
                Te.s("联合研院秘书 - 贝尔", CustomStyle.styleOfWorld), lines, progressOptions());
    }

    private static List<DialogOption> progressOptions() {
        List<DialogOption> options = new ArrayList<>();
        options.add(option(ACTION_BACK, Te.s("「返回」", ChatFormatting.AQUA), Te.s("回到对话首页")));
        options.add(option(ACTION_STORE, Te.s("「打开委托商店」", CustomStyle.styleOfWorld),
                Te.s("用委托债券兑换奖励")));
        return options;
    }

    private static DialogPage buildUnknown(String npcId, String sessionId, Component notice) {
        List<Component> lines = new ArrayList<>();
        lines.add(Te.s("（这个 NPC 还没有配置对话。）"));
        List<DialogOption> options = new ArrayList<>();
        options.add(option(ACTION_BACK, Te.s("「关闭」", ChatFormatting.GRAY), Te.s("结束对话")));
        return new DialogPage(npcId, sessionId, Te.s(npcId), lines, options);
    }

    private static DialogOption option(String actionId, Component label, Component hover) {
        return new DialogOption(actionId, label, hover);
    }

    // ------------------------------------------------------------------
    // 对话树接口与「贝尔」的实现
    // ------------------------------------------------------------------

    /**
     * 一棵对话树：一个 dialogId 对应一个实现。
     * <p>
     * 新增 NPC 时：实现本接口 → 在静态块里 {@code register(NpcIds.XXX, new XxxTree())}，
     * 不需要再往 {@code VillagerEvents} 里加 {@code switch} 分支。
     */
    public interface DialogTree {

        /** 打开对话前的自动行为，返回首页顶部的提示（可为 null）。 */
        Component onOpen(Player player, String dialogId);

        /** 构建当前状态对应的一页。 */
        DialogPage buildPage(Player player, String dialogId, String sessionId, Component notice);

        /** 分发一次选项点击；返回 null 表示动作接管了界面。 */
        DialogPage handle(Player player, String dialogId, String sessionId, String actionId);
    }

    /** 联合研院秘书 - 贝尔（委托任务）。 */
    private static final class EntrustmentTree implements DialogTree {

        @Override
        public Component onOpen(Player player, String dialogId) {
            String name = player.getName().getString();
            MobKillEntrustment entrustment = MobKillEntrustment.playerCurrentEntrustmentMap.get(name);
            if (entrustment == null) {
                return null;
            }
            int progress = MobKillEntrustment.playerKillProcessMap.getOrDefault(name, 0);
            if (progress >= entrustment.targetCount || player.isCreative()) {
                MobKillEntrustment.playerTryToSubmit(player);
                return Te.s("这份委托已经完成，报酬已经发放（明细见聊天栏）。", CustomStyle.styleOfWorld);
            }
            return null;
        }

        @Override
        public DialogPage buildPage(Player player, String dialogId, String sessionId, Component notice) {
            return buildEntrustment(player, dialogId, sessionId, notice);
        }

        @Override
        public DialogPage handle(Player player, String dialogId, String sessionId, String actionId) {
            return handleEntrustment(player, dialogId, sessionId, actionId);
        }
    }
}
