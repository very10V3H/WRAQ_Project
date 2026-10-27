package fun.wraq.events.mob.instance;

import fun.wraq.common.fast.Tick;
import fun.wraq.networking.ModNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * AI-Generated, 2026-10-27
 * 玩家被 NoTeamInstance 的领主或其同副本怪物击杀时，
 * 将副本机制说明发送到客户端，由客户端在死亡界面左下角显示。
 * <p>
 * 主要入口是 {@code Damage#playerDeadModule}；同时保留死亡状态 tick 兜底，
 * 避免某些自定义死亡流程既不触发 LivingDeathEvent 也没有经过统一死亡模块。
 */
@Mod.EventBusSubscriber
public final class InstanceDeathHintHandler {

    private static final int DEDUPE_TICKS = 20 * 10;
    private static final Map<UUID, Integer> lastHintTick = new HashMap<>();
    private static final java.util.Set<UUID> checkedThisDeath = new java.util.HashSet<>();

    private InstanceDeathHintHandler() {
    }

    public static void sendHint(ServerPlayer player, NoTeamInstance instance) {
        if (player == null || instance == null) {
            return;
        }
        List<Component> introduction = instance.getIntroduction();
        if (introduction == null || introduction.isEmpty()) {
            return;
        }
        int now = Tick.get();
        UUID uuid = player.getUUID();
        if (lastHintTick.getOrDefault(uuid, Integer.MIN_VALUE) + DEDUPE_TICKS > now) {
            return;
        }
        lastHintTick.put(uuid, now);
        ModNetworking.sendToClient(
                new InstanceMechanicHintS2CPacket(instance.name, introduction), player);
    }

    /** 兜底：没有经过 Damage#playerDeadModule 的死亡路径。 */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !event.side.isServer()
                || !(event.player instanceof ServerPlayer player)) {
            return;
        }
        UUID uuid = player.getUUID();
        if (!player.isDeadOrDying()) {
            checkedThisDeath.remove(uuid);
            return;
        }
        if (checkedThisDeath.contains(uuid)) {
            return;
        }
        checkedThisDeath.add(uuid);
        sendHint(player, NoTeamInstanceModule.getLastInstance(player));
    }
}
