package fun.wraq.common.util.struct;

import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.ShieldSyncS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
            return shieldValue;
        }
        ModNetworking.sendToClient(new ShieldSyncS2CPacket(0, 0), (ServerPlayer) player);
        return 0;
    }

    public static double clearPlayerShield(Player player) {
        double shieldValue = computePlayerShield(player);
        Shield.decreasePlayerShield(player, shieldValue);
        return shieldValue;
    }
}
