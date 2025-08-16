package fun.wraq.process.system.vp;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.vp.networking.VpValueS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class VpDataHandler {

    public static double clientVpValue = 0;

    public static final String VP_DATA_KEY = "VpValue";

    public static double getPlayerVp(Player player) {
        return player.getPersistentData().getDouble(VP_DATA_KEY);
    }

    public static void setPlayerVp(Player player, double value) {
        player.getPersistentData().putDouble(VP_DATA_KEY, value);
    }

    public static void rechargeVp(Player player, double num) {
        setPlayerVp(player, getPlayerVp(player) + num);
        Compute.sendFormatMSG(player, Te.s("vp", CustomStyle.styleOfWorld),
                Te.s("收到了 ", String.format("%.2f", num) + "vp", CustomStyle.styleOfWorld,
                        "，当前vp余额为:", String.format("%.2f", getPlayerVp(player)), CustomStyle.styleOfWorld));
        sendPlayerVpValueToClient(player);
    }

    public static void payVp(Player player, double num) {
        setPlayerVp(player, getPlayerVp(player) - num);
        Compute.sendFormatMSG(player, Te.s("vp", CustomStyle.styleOfWorld),
                Te.s("支出了 ", String.format("%.2f", num) + "vp", CustomStyle.styleOfWorld,
                        "，当前vp余额为:", String.format("%.2f", getPlayerVp(player)), CustomStyle.styleOfWorld));
        sendPlayerVpValueToClient(player);
    }

    public static void sendPlayerVpValueToClient(Player player) {
        ModNetworking.sendToClient(new VpValueS2CPacket(getPlayerVp(player)), (ServerPlayer) player);
    }
}
