package fun.wraq.process.system.reason;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.PsValueS2CPacket;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.system.endlessinstance.instance.ManaPlainTemple;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.text.ParseException;
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

    public static int getPlayerReasonUpperLimit(Player player) {
        int rankSerial = RankData.getRankSerial(player);
        if (rankSerial == 0) {
            return 100;
        } else if (rankSerial <= 2) { // 13C 13B
            return 105;
        } else if (rankSerial <= 3) { // 13A
            return 110;
        } else if (rankSerial <= 5) { // 14C 14B
            return 115;
        } else if (rankSerial <= 6) { // 14A
            return 120 + rankSerial - 6;
        } else {
            return 120 + (rankSerial - 6) * 5;
        }
    }

    public static int getPlayerReasonRecoverPerHour(Player player) throws ParseException {
        int planTier = PlanPlayer.getPlayerTier(player);
        if (planTier <= 0) return 5;
        if (planTier == 1) return 10;
        else if (planTier == 2) return 20;
        else if (planTier == 3) return 30;
        return 5;
    }

    public static boolean addOrCostPlayerReasonValue(Player player, int value) {
        CompoundTag data = player.getPersistentData();
        int reasonValue = getPlayerReasonValue(player);
        int maxReasonValue = getPlayerReasonUpperLimit(player);
        if (reasonValue > maxReasonValue && value > 0) return false;
        if (reasonValue + value < 0) return false;
        reasonValue += value;
        if (value < 0) {
            data.putInt(REASON_KEY, reasonValue);
            sendReasonValuePacketToClient(player);
            sendFormatMSG(player, Te.s("失去了", ChatFormatting.RED, value + "理智", CustomStyle.styleOfFlexible,
                    "，当前拥有", getPlayerReasonValue(player) + "理智", CustomStyle.styleOfFlexible));
        } else {
            data.putInt(REASON_KEY, Math.min(reasonValue, maxReasonValue));
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
                try {
                    addOrCostPlayerReasonValue(serverPlayer, getPlayerReasonRecoverPerHour(serverPlayer));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public static void tip(Player player) {
        if (player.tickCount != 0 && player.tickCount % Tick.min(60) == 0 && getPlayerReasonValue(player) == 100) {
            sendFormatMSG(player, Te.s("今天的", "理智", CustomStyle.styleOfFlexible, "似乎还没有使用呢。"));
            if (player.experienceLevel < 230) {
                sendFormatMSG(player, Te.s("考虑一下前往", "无尽熵增 - ", ManaPlainTemple.getInstance().name,
                        "完成挑战获取", "大量经验值", ChatFormatting.LIGHT_PURPLE, "吧!"));
            }
        }
    }
}
