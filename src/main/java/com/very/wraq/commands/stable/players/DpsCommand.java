package com.very.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.common.Compute;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.WeakHashMap;

public class DpsCommand implements Command<CommandSourceStack> {
    public static DpsCommand instance = new DpsCommand();

    public static WeakHashMap<Player, Integer> playerDpsStartTickMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> playerDpsTickMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> playerDpsSecondMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Double> playerDamageCount = new WeakHashMap<>();

    public static void Tick(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (playerDpsStartTickMap.containsKey(player) && playerDpsStartTickMap.get(player) >= TickCount) {
            if ((playerDpsStartTickMap.get(player) - TickCount) % 20 == 0) {
                ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                        new ClientboundSetTitleTextPacket(Component.literal("Dps计算将在" + ((playerDpsStartTickMap.get(player) - TickCount) / 20) + "s内开始"));
                if (playerDpsStartTickMap.get(player) == TickCount) {
                    clientboundSetTitleTextPacket = new ClientboundSetTitleTextPacket(Component.literal("Dps计算已开始").withStyle(ChatFormatting.WHITE));
                    ModNetworking.sendToClient(new SoundsS2CPacket(6), (ServerPlayer) player);
                }
                ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket);
            }
        }
        if (playerDpsTickMap.containsKey(player) && playerDpsTickMap.get(player) == TickCount) {
            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                    new ClientboundSetTitleTextPacket(Component.literal("Dps计算已结束！"));
            ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket);

            double damageCount = playerDamageCount.get(player);

            Compute.sendFormatMSG(player, Component.literal("Dps").withStyle(ChatFormatting.RED),
                    Component.literal("在" + playerDpsSecondMap.get(player) + "s内的Dps值如下:").withStyle(ChatFormatting.WHITE));
            player.sendSystemMessage(Component.literal("  1.在持续时间内的总伤害: ").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(FormatDamageCount(damageCount))));
            player.sendSystemMessage(Component.literal("  2.Dps(damage per second)每秒造成的伤害: ").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(FormatDamageCount(damageCount / playerDpsSecondMap.get(player)))));


            ModNetworking.sendToClient(new SoundsS2CPacket(6), (ServerPlayer) player);
        }
    }

    public static String FormatDamageCount(double damageCount) {
        String formatDamage = String.format("%.2f", damageCount);
        if (damageCount > 10000) formatDamage = String.format("%.2fw", damageCount / 10000);
        if (damageCount > Math.pow(10, 8)) formatDamage = String.format("%.2fe", damageCount / Math.pow(10, 8));
        return formatDamage;
    }

    public static void CalculateDamage(Player player, double damage) {
        int TickCount = player.getServer().getTickCount();
        if (playerDpsStartTickMap.containsKey(player) && playerDpsStartTickMap.get(player) < TickCount
                && playerDpsTickMap.containsKey(player) && playerDpsTickMap.get(player) > TickCount) {
            playerDamageCount.put(player, playerDamageCount.getOrDefault(player, 0d) + damage);
        }
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        int TickCount = player.getServer().getTickCount();
        String second = StringArgumentType.getString(context, "second");
        int Second = Integer.parseInt(second);
        playerDpsStartTickMap.put(player, TickCount + 60);
        playerDpsTickMap.put(player, TickCount + (3 + Second) * 20);
        playerDpsSecondMap.put(player, Second);
        playerDamageCount.put(player, 0d);
        return 0;
    }
}
