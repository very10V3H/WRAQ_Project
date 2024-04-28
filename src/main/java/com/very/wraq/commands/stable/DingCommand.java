package com.very.wraq.commands.stable;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.DingS2CPacket;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class DingCommand implements Command<CommandSourceStack> {
    public static DingCommand instance = new DingCommand();
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context,"name");
        for (GameProfile profile : gameProfile) {
            ServerPlayer target = player.getServer().getPlayerList().getPlayer(profile.getId());
            if (target == null) {
                Compute.FormatMSGSend(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                        Component.literal("玩家似乎不在线。。。").withStyle(ChatFormatting.WHITE));
                return 0;
            }
            if (Utils.DingCoolDown.containsKey(player) && Utils.DingCoolDown.get(player) > player.getServer().getTickCount()) {
                Compute.FormatMSGSend(player,Component.literal("叮!").withStyle(ChatFormatting.AQUA),
                        Component.literal("一分钟内只能发送一次叮叮叮喔！").withStyle(ChatFormatting.WHITE));
            }
            else {
                ModNetworking.sendToClient(new DingS2CPacket(5),target);
                Compute.FormatMSGSend(player,Component.literal("叮!").withStyle(ChatFormatting.AQUA),
                        Component.literal("你向").withStyle(ChatFormatting.WHITE).
                                append(target.getDisplayName()).
                                append(Component.literal("发送了一个叮叮叮！").withStyle(ChatFormatting.WHITE)));
                Compute.FormatMSGSend(target,Component.literal("叮!").withStyle(ChatFormatting.AQUA),
                        Component.literal("你受到了来自").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal("发送的叮叮叮！").withStyle(ChatFormatting.WHITE)));
                Compute.FormatBroad(player.level(),Component.literal("叮!").withStyle(ChatFormatting.AQUA),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal("向").withStyle(ChatFormatting.WHITE)).
                                append(target.getDisplayName()).
                                append(Component.literal("发送了一个 叮~").withStyle(ChatFormatting.WHITE)));
                ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                        new ClientboundSetTitleTextPacket(Component.literal("叮叮叮!").withStyle(ChatFormatting.AQUA));
                target.connection.send(clientboundSetTitleTextPacket);
                if (!player.isCreative()) Utils.DingCoolDown.put(player,player.getServer().getTickCount() + 1200);
            }
        }
        return 0;
    }


}
