package com.very.wraq.commands.stable.players;

import com.very.wraq.common.Compute;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class PayCommand implements Command<CommandSourceStack> {
    public static PayCommand instance = new PayCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "name");
        String s = StringArgumentType.getString(context, "num");
        double num = Double.parseDouble(s);
        if (num < 0) {
            Compute.sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                    Component.literal("需要一个正确的数值！").withStyle(ChatFormatting.WHITE));
            return 0;
        }
        for (GameProfile profile : gameProfile) {
            ServerPlayer target = player.getServer().getPlayerList().getPlayer(profile.getId());
            if (target == null) {
                Compute.sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                        Component.literal("玩家似乎不在线。。。").withStyle(ChatFormatting.WHITE));
                return 0;
            }
            if (Compute.CurrentVB(player) >= num) {

                Compute.sendFormatMSG(target, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                        Component.literal("你收到一笔来自 ").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 的转账!").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(" 金额: " + num).withStyle(ChatFormatting.GOLD)));

                Compute.sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                        Component.literal("成功向 ").withStyle(ChatFormatting.WHITE).
                                append(target.getDisplayName()).
                                append(Component.literal(" 转账!").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(" 金额: " + num).withStyle(ChatFormatting.GOLD)));

                Compute.VBExpenseAndMSGSend(player, num);
                Compute.VBIncomeAndMSGSend(target, num);

            } else {
                Compute.sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                        Component.literal("VB不足！").withStyle(ChatFormatting.WHITE));
            }
        }
        return 0;
    }
}
