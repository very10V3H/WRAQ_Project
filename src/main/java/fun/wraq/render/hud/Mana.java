package fun.wraq.render.hud;

import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.ManaSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class Mana {

    private static double maxMana;
    private static double currentMana;

    public static void setMaxMana(double mana) {
        Mana.maxMana = mana;
    }

    public static double getMaxMana() {
        return maxMana;
    }

    public static void setCurrentMana(double Value) {
        Mana.currentMana = Value;
    }

    public static double getCurrentMana() {
        return currentMana;
    }

    public static void updateManaStatus(Player player) {
        CompoundTag data = player.getPersistentData();
        double MaxMana = data.getDouble("MAXMANA");
        ModNetworking.sendToClient(new ManaSyncS2CPacket(MaxMana, data.getDouble("MANA")), (ServerPlayer) player);
    }

    public static void addOrCostPlayerMana(Player player, double value) {
        CompoundTag data = player.getPersistentData();
        if (value > 0) data.putDouble("MANA", Math.min(data.getDouble("MANA") + value, data.getDouble("MAXMANA")));
        else data.putDouble("MANA", Math.max(data.getDouble("MANA") + value, 0));
        updateManaStatus(player);
    }

    public static double getPlayerCurrentManaNum(Player player) {
        return player.getPersistentData().getDouble("MANA");
    }

    public static double getPlayerMaxManaNum(Player player) {
        return player.getPersistentData().getDouble("MAXMANA");
    }



}
