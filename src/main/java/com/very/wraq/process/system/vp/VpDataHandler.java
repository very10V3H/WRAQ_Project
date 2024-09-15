package com.very.wraq.process.system.vp;

import com.very.wraq.common.Compute;
import com.very.wraq.files.dataBases.DataBase;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.process.system.vp.networking.VpValueS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VpDataHandler {

    public static double clientVpValue = 0;

    public static Map<String, Double> playerVpData = new HashMap<>();

    public record ReviseData(String name, double value) {
    }

    public static List<ReviseData> reviseDataList = new ArrayList<>();
    public static String tableName = "vpdata";

    public static void firstRead() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        String sql = "select * from vpdata";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String vpString = resultSet.getString("vp");
            double vp = 0;
            if (vpString != null) vp = Double.parseDouble(vpString);
            playerVpData.put(name.toLowerCase(), vp);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }

    public static void normalRead() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        String sql = "select * from vpdata";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String updateString = resultSet.getString("updateValue");
            double update = 0;
            if (updateString != null) update = Double.parseDouble(updateString);
            playerVpData.put(name.toLowerCase(), playerVpData.getOrDefault(name.toLowerCase(), 0d) + update);
            if (update != 0) {
                reviseDataList.add(new ReviseData(name, update));
            }
        }
        resultSet.close();

        playerVpData.forEach((name, vp) -> {
            try {
                DataBase.put(statement, name.toLowerCase(), "vp", vp.toString(), tableName);
                DataBase.put(statement, name.toLowerCase(), "updateValue", "0", tableName);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        statement.close();
        connection.close();
    }

    public static void write() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        playerVpData.forEach((name, vp) -> {
            try {
                DataBase.put(statement, name.toLowerCase(), "vp", String.valueOf(vp), tableName);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        statement.close();
        connection.close();
    }

    public static double getPlayerVp(String playerName) {
        return playerVpData.getOrDefault(playerName.toLowerCase(), 0d);
    }

    public static void reviseDataMSGSend(MinecraftServer server) {
        if (!reviseDataList.isEmpty()) {
            PlayerList playerList = server.getPlayerList();
            reviseDataList.forEach(reviseData -> {
                Player player = playerList.getPlayerByName(reviseData.name);
                if (player != null) {
                    Compute.sendFormatMSG(player, Component.literal("vp").withStyle(ChatFormatting.AQUA),
                            Component.literal("你收到了 ").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(String.format("%.2f", reviseData.value) + "vp").withStyle(ChatFormatting.AQUA)));
                    sendPlayerVpValue(player);
                }
            });
            reviseDataList.clear();
        }
    }

    public static void sendPlayerVpValue(Player player) {
        ModNetworking.sendToClient(new VpValueS2CPacket(playerVpData.getOrDefault(player.getName().getString().toLowerCase(), 0d)), (ServerPlayer) player);
    }
}
