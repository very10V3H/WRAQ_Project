package fun.wraq.process.system.xp;

import fun.wraq.common.Compute;
import fun.wraq.networking.ModNetworking;
import fun.wraq.render.hud.networking.ExpGetS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class MyExpSystem {
    public static int levelUpperLimit = 300;
    public static int expGetUpperLimit = 125;

    public static double getCurrentXpLevelUpNeedXpPoint(int xpLevel) {
        return Math.pow(Math.E, 3 + (xpLevel / 100d) * 7);
    }

    public static void giveExpToPlayer(Player player, double num, double expUp, boolean sendMSG, Component sourceType, int expLevel) {
        if (player.experienceLevel >= levelUpperLimit || expLevel - player.experienceLevel > 8) return;
        double expValue = num * (1 + expUp);

        CompoundTag data = player.getPersistentData();
        player.getPersistentData().putDouble("Xp", data.getDouble("Xp") + expValue);

        ModNetworking.sendToClient(new ExpGetS2CPacket(expValue), (ServerPlayer) player);
        if (sendMSG) {
            Compute.getValueIncreaseMSG(String.format("%.0f", expValue), ChatFormatting.LIGHT_PURPLE,
                    String.format("%.0f", data.getDouble("Xp")), ChatFormatting.GRAY, sourceType);
        }
    }

    public static void giveExpToPlayer(Player player, double num, double expUp, int expLevel) {
        giveExpToPlayer(player, num, expUp, false, null, expLevel);
    }

    public static void givePercentExpToPlayer(Player player, double num, double expUp, int expLevel,
                                              boolean sendMSG, Component sourceType) {
        if (player.experienceLevel >= levelUpperLimit) return;
        if (expLevel >= expGetUpperLimit) {
            num *= (double) expLevel / expGetUpperLimit;
            expLevel = expGetUpperLimit;
        }
        if (expLevel - player.experienceLevel > 8) expLevel = player.experienceLevel;
        CompoundTag data = player.getPersistentData();
        double expLevelXp = getCurrentXpLevelUpNeedXpPoint(expLevel);
        double xpBeforeUp = (expLevelXp * num);
        double xpUp = (expLevelXp * num) * expUp;
        double xp = xpBeforeUp + xpUp;
        if (data.contains("Xp")) {
            data.putDouble("Xp", data.getDouble("Xp") + xp);
        } else {
            data.putDouble("Xp", xp);
        }
        ModNetworking.sendToClient(new ExpGetS2CPacket(xp), (ServerPlayer) player);
        if (sendMSG) {
            Compute.getValueIncreaseMSG(String.format("%.0f", xp), ChatFormatting.LIGHT_PURPLE,
                    String.format("%.0f", data.getDouble("Xp")), ChatFormatting.GRAY, sourceType);
        }
    }

    public static void givePercentExpToPlayer(Player player, double num, double expUp, int expLevel) {
        givePercentExpToPlayer(player, num, expUp, expLevel, false, null);
    }

    public static void giveExpToPlayer(Player player, double num) {
        if (player.experienceLevel >= levelUpperLimit) return;
        CompoundTag data = player.getPersistentData();
        double LevelUpNeedXp = getCurrentXpLevelUpNeedXpPoint(player.experienceLevel);
        double XpUp = 0;
        if (data.contains("Xp")) data.putDouble("Xp", data.getDouble("Xp") + num);
        else data.putDouble("Xp", num);
        if (!data.contains("IgnoreExp") || (!data.getBoolean("IgnoreExp")))
            Compute.sendFormatMSG(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("经验值").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal(" + ").withStyle(ChatFormatting.DARK_PURPLE)).
                            append(Component.literal(String.format("%.1f", num)).withStyle(ChatFormatting.LIGHT_PURPLE)).
                            append(Component.literal(" + " + String.format("%.1f", XpUp)).withStyle(CustomStyle.styleOfLucky)).
                            append(Component.literal(String.format(" (%.1f/%.1f)", data.getDouble("Xp"), LevelUpNeedXp)).withStyle(ChatFormatting.GRAY)));
    }
}
