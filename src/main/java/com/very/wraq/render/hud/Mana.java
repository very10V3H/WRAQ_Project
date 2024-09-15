package com.very.wraq.render.hud;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ManaSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

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
        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (Compute.getManaSkillLevel(data, 10) > 0 && Utils.sceptreTag.containsKey(mainhand))
            MaxMana *= (1 - Compute.getManaSkillLevel(data, 10) * 0.1);
        ModNetworking.sendToClient(new ManaSyncS2CPacket(MaxMana, data.getDouble("MANA")), (ServerPlayer) player);
    }

    public static void addOrCostPlayerMana(Player player, double Mana) {
        CompoundTag data = player.getPersistentData();
        if (Mana > 0) data.putDouble("MANA", Math.min(data.getDouble("MANA") + Mana, data.getDouble("MAXMANA")));
        else data.putDouble("MANA", Math.max(data.getDouble("MANA") + Mana, 0));
        updateManaStatus(player);
    }

    public static double getPlayerCurrentManaNum(Player player) {
        return player.getPersistentData().getDouble("MANA");
    }

    public static double getPlayerMaxManaNum(Player player) {
        return player.getPersistentData().getDouble("MAXMANA");
    }



}
