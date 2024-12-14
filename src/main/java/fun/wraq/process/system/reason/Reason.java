package fun.wraq.process.system.reason;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.PsValueS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Calendar;

public class Reason {

    public static int clientReasonValue = 0;

    public static final String REASON_KEY = "REASON";
    public static int getPlayerReasonValue(Player player) {
        return player.getPersistentData().getInt(REASON_KEY);
    }

    public static void setPlayerReasonValue(Player player, int value) {
        player.getPersistentData().putInt(REASON_KEY, value);
        sendReasonValuePacketToClient(player);
    }

    public static boolean addOrCostPlayerReasonValue(Player player, int value) {
        CompoundTag data = player.getPersistentData();
        int reasonValue = getPlayerReasonValue(player);
        if (reasonValue > 100 && value > 0) return false;
        if (reasonValue + value < 0) return false;
        reasonValue += value;
        if (value < 0) {
            data.putInt(REASON_KEY, reasonValue);
            sendReasonValuePacketToClient(player);
            sendFormatMSG(player, Te.s("失去了", ChatFormatting.RED, value + "理智", CustomStyle.styleOfFlexible,
                    "，当前拥有", getPlayerReasonValue(player) + "理智", CustomStyle.styleOfFlexible));
        } else {
            data.putInt(REASON_KEY, Math.min(reasonValue, 100));
            sendReasonValuePacketToClient(player);
            sendFormatMSG(player, Te.s("恢复了", ChatFormatting.GREEN, value + "理智", CustomStyle.styleOfFlexible,
                    "，当前拥有", getPlayerReasonValue(player) + "理智", CustomStyle.styleOfFlexible));
        }
        return true;
    }

    public static boolean addOrCostPlayerReasonValueIgnoreLimit(Player player, int value) {
        CompoundTag data = player.getPersistentData();
        int reasonValue = getPlayerReasonValue(player);
        if (reasonValue + value < 0) return false;
        reasonValue += value;
        data.putInt(REASON_KEY, reasonValue);
        sendReasonValuePacketToClient(player);
        if (value < 0) {
            sendFormatMSG(player, Te.s("失去了", ChatFormatting.RED, value + "理智", CustomStyle.styleOfFlexible,
                    "，当前拥有", getPlayerReasonValue(player) + "理智", CustomStyle.styleOfFlexible));
        } else {
            sendFormatMSG(player, Te.s("恢复了", ChatFormatting.GREEN, value + "理智", CustomStyle.styleOfFlexible,
                    "，当前拥有", getPlayerReasonValue(player) + "理智", CustomStyle.styleOfFlexible));
        }
        return true;
    }

    public static void sendReasonValuePacketToClient(Player player) {
        ModNetworking.sendToClient(new PsValueS2CPacket(getPlayerReasonValue(player)), (ServerPlayer) player);
    }

    public static void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("理智", CustomStyle.styleOfFlexible), content);
    }

    public static int serverLastReasonRecoverHour = -1;
    public static void serverTick() {
        if (serverLastReasonRecoverHour != Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            serverLastReasonRecoverHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            Tick.server.getPlayerList().getPlayers().forEach(serverPlayer -> {
                addOrCostPlayerReasonValue(serverPlayer, 5);
            });
        }
    }
}
