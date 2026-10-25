package fun.wraq.common.util.struct;

import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.PlayerShieldSyncS2CPacket;
import fun.wraq.networking.misc.ShieldSyncS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.Math.ceil;

public class Shield {
    private int tick;
    private double value;

    public Shield(int tick, double value) {
        this.tick = tick;
        this.value = value;
    }

    public int getTick() {
        return tick;
    }

    public double getValue() {
        return value;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static double decreasePlayerShield(Player player, double value) {
        double TmpNum = value;
        List<Shield> shieldQueue = Utils.playerShieldQueue.get(player.getName().getString());
        if (shieldQueue != null && !shieldQueue.isEmpty()) {
            for (Shield shield : shieldQueue) {
                if (shield.getValue() > TmpNum) {
                    shield.setValue(shield.getValue() - TmpNum);
                    computePlayerShield(player);
                    return 0;
                } else {
                    TmpNum -= shield.getValue();
                    shield.setTick(0);
                    shield.setValue(0);
                }
            }
            computePlayerShield(player);
            return TmpNum;
        }
        return value;
    }

    public static void providePlayerShield(Player player, int tick, double value) {
        value *= 1 + fun.wraq.process.system.mastery.MasteryBonusCache.stats(player)
                .value(fun.wraq.process.system.mastery.MasteryStat.SHIELD_POWER)
                + fun.wraq.process.system.mastery.MasteryBonusCache.stats(player)
                .value(fun.wraq.process.system.mastery.MasteryStat.EQUIP_PASSIVE);
        Shield shield = new Shield(tick, value);
        List<Shield> shieldQueue = Utils.playerShieldQueue.get(player.getName().getString());
        if (shieldQueue == null) {
            shieldQueue = new ArrayList<>();
            shieldQueue.add(shield);
            Utils.playerShieldQueue.put(player.getName().getString(), shieldQueue);
        } else {
            shieldQueue.add(shield);
        }
        computePlayerShield(player);
    }

    public static double computePlayerShield(Player player) {
        List<Shield> shieldQueue = Utils.playerShieldQueue.get(player.getName().getString());
        if (shieldQueue != null && !shieldQueue.isEmpty()) {
            Iterator<Shield> iterator0 = shieldQueue.iterator();
            double shieldValue = 0;
            while (iterator0.hasNext()) {
                Shield shield = iterator0.next();
                if (shield.getTick() > 0) shieldValue += shield.getValue();
                if (shield.getTick() == 0 || shield.getValue() == 0) iterator0.remove();
            }
            double tmpShieldNum = 9.0 * (shieldValue / player.getMaxHealth());
            ModNetworking.sendToClient(new ShieldSyncS2CPacket((int) ceil(tmpShieldNum),
                    (int) ceil(shieldValue)), (ServerPlayer) player);
            broadcastShield(player, shieldValue);
            return shieldValue;
        }
        ModNetworking.sendToClient(new ShieldSyncS2CPacket(0, 0), (ServerPlayer) player);
        broadcastShield(player, 0);
        return 0;
    }

    /** 上一次广播给周围玩家的护盾值 / tick（按实体 id），用于避免每 tick 重复发包。 */
    private static final Map<Integer, Double> lastBroadcastShield = new HashMap<>();
    private static final Map<Integer, Integer> lastBroadcastTick = new HashMap<>();

    /**
     * 把实体（玩家 / 怪物）的护盾值同步给追踪该实体的客户端（头顶血条的白色护盾段使用）。
     * 护盾值变化时立即发送；持续期间每 10 tick 刷新一次，保证客户端数据不过期。
     */
    public static void broadcastShield(Entity entity, double shieldValue) {
        if (entity.level().isClientSide) {
            return;
        }
        int entityId = entity.getId();
        Double last = lastBroadcastShield.get(entityId);
        Integer lastTick = lastBroadcastTick.get(entityId);
        boolean changed = last == null || Math.abs(last - shieldValue) > 0.01;
        boolean refresh = lastTick == null || entity.tickCount - lastTick >= 10;
        if (!changed && !refresh) {
            return;
        }
        lastBroadcastShield.put(entityId, shieldValue);
        lastBroadcastTick.put(entityId, entity.tickCount);
        ModNetworking.sendToClientsTrackingEntity(new PlayerShieldSyncS2CPacket(entityId, shieldValue), entity);
    }

    public static double clearPlayerShield(Player player) {
        double shieldValue = computePlayerShield(player);
        Shield.decreasePlayerShield(player, shieldValue);
        return shieldValue;
    }
}
