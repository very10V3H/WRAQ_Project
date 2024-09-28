package fun.wraq.render.hud;

import fun.wraq.common.util.StringUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.unSorted.SwiftSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import static java.lang.Math.min;

public class SwiftData {

    private static double MaxSwift;
    private static double CurrentSwift;

    public static void setMaxSwift(double mana) {
        SwiftData.MaxSwift = mana;
    }

    public static double getMaxSwift() {
        return MaxSwift;
    }

    public static void setCurrentSwift(double Value) {
        SwiftData.CurrentSwift = Value;
    }

    public static double getCurrentSwift() {
        return CurrentSwift;
    }

    public static void updateSwiftStatus(Player player) {
        CompoundTag data = player.getPersistentData();
        ModNetworking.sendToClient(new SwiftSyncS2CPacket(100, data.getDouble(StringUtils.Swift)), (ServerPlayer) player);
    }

    public static boolean changePlayerSwift(Player player, double Num) {
        CompoundTag data = player.getPersistentData();
        double CurrentSwift = data.getDouble(StringUtils.Swift);
        double MaxSwift = data.getDouble(StringUtils.MaxSwift);
        if (CurrentSwift + Num < 0) return false;
        else {
            data.putDouble(StringUtils.Swift, min(MaxSwift, CurrentSwift + Num));
            updateSwiftStatus(player);
            return true;
        }
    }

}
