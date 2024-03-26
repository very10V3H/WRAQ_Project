package com.Very.very.Hcommand;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.DingS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class DingCommand implements Command<CommandSourceStack> {
    public static DingCommand instance = new DingCommand();
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String name = StringArgumentType.getString(context, "name");
        if (player.getServer().getPlayerList().getPlayerByName(name) != null) {
            if (Utils.DingCoolDown.containsKey(player) && Utils.DingCoolDown.get(player) > player.getServer().getTickCount()) {
                Compute.FormatMSGSend(player,Component.literal("叮!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                        Component.literal("一分钟内只能发送一次叮叮叮喔！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
            else {
                ServerPlayer serverPlayer = player.getServer().getPlayerList().getPlayerByName(name);
                ModNetworking.sendToClient(new DingS2CPacket(5),serverPlayer);
                Compute.FormatMSGSend(player,Component.literal("叮!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                        Component.literal("你向").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(serverPlayer.getDisplayName()).
                                append(Component.literal("发送了一个叮叮叮！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                Compute.FormatMSGSend(serverPlayer,Component.literal("叮!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                        Component.literal("你受到了来自").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal("发送的叮叮叮！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                Compute.FormatBroad(player.level(),Component.literal("叮!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                        Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal("向").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                append(serverPlayer.getDisplayName()).
                                append(Component.literal("发送了一个 叮~").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                        new ClientboundSetTitleTextPacket(Component.literal("叮叮叮!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                if (!player.isCreative()) Utils.DingCoolDown.put(player,player.getServer().getTickCount() + 1200);
            }
        }
        else {
            Compute.FormatMSGSend(player,Component.literal("叮!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("玩家似乎没有在线。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }


}
