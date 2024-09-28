package fun.wraq.render.hud;

import fun.wraq.common.util.StringUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.unSorted.ColdSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ColdData {

    private static double MaxCold;
    private static double CurrentCold;

    public static void setMaxCold(double mana) {
        ColdData.MaxCold = mana;
    }

    public static double getMaxCold() {
        return MaxCold;
    }

    public static void setCurrentCold(double Value) {
        ColdData.CurrentCold = Value;
    }

    public static double getCurrentCold() {
        return CurrentCold;
    }

    public static void PlayerColdNumAddOrCost(Player player, double Num) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(StringUtils.MaxCold) || !data.contains(StringUtils.CurrentCold)) {
            data.putDouble(StringUtils.MaxCold, 100);
            data.putDouble(StringUtils.CurrentCold, 0);
        } else {
            double MaxCold = data.getDouble(StringUtils.MaxCold);
            double CurrentCold = data.getDouble(StringUtils.CurrentCold);
            if (Num > 0) data.putDouble(StringUtils.CurrentCold, Math.min(100, CurrentCold + Num));
            else data.putDouble(StringUtils.CurrentCold, Math.max(0, CurrentCold + Num));
        }
        PlayerColdNumStatusUpdate(player);
    }

    public static void PlayerColdNumStatusUpdate(Player player) {
        CompoundTag data = player.getPersistentData();
        ModNetworking.sendToClient(new ColdSyncS2CPacket(data.getDouble(StringUtils.MaxCold), data.getDouble(StringUtils.CurrentCold)), (ServerPlayer) player);
    }

    public static double PlayerCurrentColdNum(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getDouble(StringUtils.CurrentCold);
    }

    public static double PlayerMaxColdNum(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getDouble(StringUtils.MaxCold);
    }

    public static double PlayerColdEffect(Player player) {
        if (PlayerCurrentColdNum(player) > 90) return -0.5;
        return 0;
    }
}
