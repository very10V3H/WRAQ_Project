package fun.wraq.process.system.dialog;

import fun.wraq.common.fast.Tick;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.dialog.DialogOpenS2CPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * AI-Generated, 2026-10-17
 * 服务端对话会话管理器，取代「聊天窗 ClickEvent(RUN_COMMAND) + 命令分发」的旧实现。
 * <p>
 * 与旧实现的本质差别：
 * <ul>
 *     <li>不需要为每个选项注册命令，业务动作直接从对话进入，命令不再是 RPC 通道；</li>
 *     <li>会话绑定「玩家 + NPC + 页 + 时间窗 + 距离」，命令方案无法表达的上下文在这里是一等公民，
 *         因此不需要再用 static Map 去猜「玩家现在处于哪一轮」；</li>
 *     <li>点击时用「重建当前页」做动作白名单校验，客户端无法越过状态机（例如冷却中直接发 accept）。</li>
 * </ul>
 * 旧命令入口保持可用（见 {@code MobKillEntrustmentOperationCommand}），便于灰度与回滚。
 */
public final class DialogManager {

    /** 会话有效期：2 分钟。超时后任何点击都被丢弃，必须重新交互开启。 */
    private static final int SESSION_TTL_TICK = Tick.s(120);

    /** 两次点击的最小间隔：防止按钮被高频点击刷包。 */
    private static final int MIN_SELECT_INTERVAL_TICK = 2;

    /** 与 NPC 的最大允许距离（8 格，平方值）：替代旧实现在每个命令分支里各自查附近实体。 */
    private static final double MAX_DISTANCE_SQR = 64;

    /**
     * @param actions 当前这一页真实下发过的动作集合，点击时用它做白名单校验；
     *                而不是用「重建首页」判断，否则在进度页点击「返回」之类的次级页动作会被误拒。
     */
    private record Session(String npcId, String sessionId, Vec3 npcPos, int openedTick, int lastSelectTick,
                           Set<String> actions, int entityId) {
    }

    private static final Map<UUID, Session> SESSIONS = new HashMap<>();
    private static int sessionCounter = 0;

    private DialogManager() {
    }

    /**
     * 打开某个 NPC 的对话。会先执行「进入时的自动行为」（例如委托已完成则自动提交），
     * 再用最新状态构建第一页。
     *
     * @param player 交互的玩家
     * @param npc    被交互的实体，用于后续点击时的距离校验
     * @param npcId  对话树标识
     */
    public static void open(Player player, Entity npc, String npcId) {
        if (player.level().isClientSide) {
            return;
        }
        String sessionId = Integer.toString(++sessionCounter);
        int now = Tick.get();
        Component notice = DialogTrees.onOpen(player, npcId);
        DialogPage page = DialogTrees.build(player, npcId, sessionId, notice);
        if (page == null) {
            return;
        }
        int entityId = npc.getId();
        SESSIONS.put(player.getUUID(),
                new Session(npcId, sessionId, npc.position(), now, now, actionsOf(page), entityId));
        send(player, page.withEntityId(entityId));
    }

    /**
     * 处理一次选项点击。校验顺序：会话存在 → 会话匹配 → 未过期 → 非高频 → 距离合法 → 动作在当前页白名单内。
     */
    public static void select(Player player, String npcId, String sessionId, String actionId) {
        if (player.level().isClientSide) {
            return;
        }
        Session session = SESSIONS.get(player.getUUID());
        if (session == null || !session.npcId().equals(npcId) || !session.sessionId().equals(sessionId)) {
            return;
        }
        int now = Tick.get();
        if (now - session.openedTick() > SESSION_TTL_TICK) {
            SESSIONS.remove(player.getUUID());
            return;
        }
        if (now - session.lastSelectTick() < MIN_SELECT_INTERVAL_TICK) {
            return;
        }
        if (player.distanceToSqr(session.npcPos()) > MAX_DISTANCE_SQR) {
            SESSIONS.remove(player.getUUID());
            return;
        }
        if (!session.actions().contains(actionId)) {
            return;
        }
        DialogPage next = DialogTrees.handle(player, npcId, sessionId, actionId);
        if (next == null) {
            return;
        }
        SESSIONS.put(player.getUUID(), new Session(npcId, sessionId, session.npcPos(), session.openedTick(),
                now, actionsOf(next), session.entityId()));
        send(player, next.withEntityId(session.entityId()));
    }

    /** 结束会话（例如动作接管了界面，打开商店）。 */
    public static void close(Player player) {
        SESSIONS.remove(player.getUUID());
    }

    private static Set<String> actionsOf(DialogPage page) {
        Set<String> actions = new HashSet<>();
        for (DialogOption option : page.options()) {
            actions.add(option.actionId());
        }
        return actions;
    }

    private static void send(Player player, DialogPage page) {
        if (page == null) {
            return;
        }
        ModNetworking.sendToClient(new DialogOpenS2CPacket(page), player);
    }
}
