package fun.wraq.render.hud;

import fun.wraq.common.Compute;
import fun.wraq.common.impl.oncostmana.OnCostManaEquip;
import fun.wraq.common.util.StringUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.ManaSyncS2CPacket;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.skill.ManaSkillTree;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.sakura.EarthMana.EarthBook;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
        double currentValue = getPlayerCurrentManaNum(player);
        if (value > 0) {
            data.putDouble("MANA",
                    Math.min(currentValue + value, getPlayerMaxManaNum(player)));
            ManaSkillTree.skill14OnPlayerManaRecover(player, Math.min(getPlayerMaxManaNum(player) - currentValue, value));
        }
        else {
            double manaCostRate = 1;
            manaCostRate += EarthBook.getManaCostRate(player);
            value *= manaCostRate;
            PowerLogic.playerLastTimeReleasePowerManaCost.put(player, value);
            if (SuitCount.getEarthManaSuitCount(player) > 0) {
                if (ManaSkillTree.getManaSkillTier(player, 13) == 0) {
                    Compute.playerHeal(player, value * SuitCount.getEarthManaSuitCount(player));
                }
            }
            OnCostManaEquip.costMana(player, value);
            data.putDouble("MANA", Math.max(currentValue + value, 0));
        }
        updateManaStatus(player);
    }

    public static double getPlayerCurrentManaNum(Player player) {
        return player.getPersistentData().getDouble("MANA");
    }

    public static double getPlayerMaxManaNum(Player player) {
        return player.getPersistentData().getDouble("MAXMANA");
    }

    public static double getPlayerLostMana(Player player) {
        return getPlayerMaxManaNum(player) - getPlayerCurrentManaNum(player);
    }

    public static boolean playerManaCost(Player player, double manaCost) {
        if (getPlayerCurrentManaNum(player) < manaCost) {
            CompoundTag data = player.getPersistentData();
            if (!data.getBoolean(StringUtils.IgnoreType.Mana)) {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("魔力").withStyle(CustomStyle.styleOfMana)).
                        append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal("魔力不足。").withStyle(ChatFormatting.WHITE)));
            }
            return false;
        } else {
            addOrCostPlayerMana(player, -manaCost);
        }
        return true;
    }

    public static boolean playerManaCost(Player player, double manaCost, boolean IsMana) {
        if (getPlayerCurrentManaNum(player) < manaCost) {
            CompoundTag data = player.getPersistentData();
            if (!data.getBoolean(StringUtils.IgnoreType.Mana)) {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("魔力").withStyle(CustomStyle.styleOfMana)).
                        append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal("魔力不足。").withStyle(ChatFormatting.WHITE)));
            }
            return false;
        } else {
            addOrCostPlayerMana(player, -manaCost);
        }
        return true;
    }
}
