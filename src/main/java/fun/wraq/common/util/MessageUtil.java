/** AI-Generated, 2026-05-10 */
package fun.wraq.common.util;

import com.mojang.logging.LogUtils;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.networking.ModNetworking;
import fun.wraq.render.gui.ScreenInfoS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public class MessageUtil {

    public static void formatBroad(Level level, Component type, Component content) {
        formatBroad(type, content);
    }

    public static void formatBroad(Component content) {
        formatBroad(Te.s("维瑞阿契", ChatFormatting.AQUA), content);
    }

    public static void formatBroad(Component type, Component content) {
        List<ServerPlayer> playerList = Tick.server.getPlayerList().getPlayers();
        for (ServerPlayer player : playerList) {
            CompoundTag data = player.getPersistentData();
            if (type.getString().equals("副本") || type.getString().equals("黄金屋")) {
                if (!data.getBoolean(StringUtils.IgnoreType.Instance) && Utils.playerTeamMap.containsKey(player)) {
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(type).append("] ").withStyle(ChatFormatting.GRAY).
                            append(content));
                }
            } else {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(type).append("] ").withStyle(ChatFormatting.GRAY).
                        append(content));

            }
        }
    }

    public static void sendFormatMSG(Player player, Component type, Component content) {
        if (player != null)
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(type).append("] ").withStyle(ChatFormatting.GRAY).
                    append(content));
    }

    public static void sendBlankLine(Player player, int lines) {
        for (int i = 0; i < lines; i++) {
            player.sendSystemMessage(Component.literal(""));
        }
    }

    public static void msgSendToPlayer(Player player, Component content, int blank) {
        String blankString = " ".repeat(blank);
        player.sendSystemMessage(Component.literal(blankString).
                append(content));
    }

    public static MutableComponent getFormatMSG(Component type, Component content) {
        return Component.literal("[").withStyle(ChatFormatting.GRAY)
                .append(type).append("] ").withStyle(ChatFormatting.GRAY)
                .append(content);
    }

    public static void broad(Level level, Component component) {
        PlayerList list = level.getServer().getPlayerList();
        List<ServerPlayer> list1 = list.getPlayers();
        for (Player player : list1) {
            player.sendSystemMessage(component);
        }
    }

    public static void broad(Component component, int blank) {
        PlayerList list = Tick.server.getPlayerList();
        List<ServerPlayer> list1 = list.getPlayers();
        String blankString = " ".repeat(blank);
        for (Player player : list1) {
            player.sendSystemMessage(Component.literal(blankString).append(component));
        }
    }

    public static void sendErrorTips(Player player, Component content) {
        sendFormatMSG(player, Te.s("错误", ChatFormatting.RED), content);
    }

    public static void sendCommandOpMSG(Player player, String content) {
        if (player == null) {
            LogUtils.getLogger().info(content);
        } else {
            player.sendSystemMessage(Te.s(content));
        }
    }

    public static void sendInfoToScreen(Player player, Component info) {
        ModNetworking.sendToClient(new ScreenInfoS2CPacket(info), player);
    }
}
