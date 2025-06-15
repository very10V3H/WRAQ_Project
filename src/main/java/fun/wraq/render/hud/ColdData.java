package fun.wraq.render.hud;

import fun.wraq.common.util.StringUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.unSorted.ColdSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ColdData {

    private static double maxCold;
    private static double currentCold;

    public static void setMaxCold(double mana) {
        ColdData.maxCold = mana;
    }

    public static double getMaxCold() {
        return maxCold;
    }

    public static void setCurrentCold(double Value) {
        ColdData.currentCold = Value;
    }

    public static double getCurrentCold() {
        return currentCold;
    }

    public static void addPlayerColdValue(Player player, double value) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(StringUtils.MaxCold) || !data.contains(StringUtils.CurrentCold)) {
            data.putDouble(StringUtils.MaxCold, 100);
            data.putDouble(StringUtils.CurrentCold, 0);
        } else {
            double maxCold = data.getDouble(StringUtils.MaxCold);
            double currentCold = data.getDouble(StringUtils.CurrentCold);
            if (value > 0) {
                data.putDouble(StringUtils.CurrentCold, Math.min(100, currentCold + value));
            } else {
                data.putDouble(StringUtils.CurrentCold, Math.max(0, currentCold + value));
            }
        }
        updatePlayerColdNumStatus(player);
    }

    public static void updatePlayerColdNumStatus(Player player) {
        CompoundTag data = player.getPersistentData();
        ModNetworking.sendToClient(new ColdSyncS2CPacket(data.getDouble(StringUtils.MaxCold), data.getDouble(StringUtils.CurrentCold)), (ServerPlayer) player);
    }

    public static double getPlayerCurrentColdValue(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getDouble(StringUtils.CurrentCold);
    }

    public static double getPlayerMaxColdValue(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getDouble(StringUtils.MaxCold);
    }
}
