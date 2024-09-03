package com.very.wraq.commands.stable.players;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class DingDingCommand implements Command<CommandSourceStack> {
    public static DingDingCommand instance = new DingDingCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "name");
        for (GameProfile profile : gameProfile) {
            ServerPlayer target = player.getServer().getPlayerList().getPlayer(profile.getId());
            if (target == null) {
                Compute.sendFormatMSG(player, Component.literal("VB").withStyle(ChatFormatting.GOLD),
                        Component.literal("玩家似乎不在线。。。").withStyle(ChatFormatting.WHITE));
                return 0;
            }
            if (Utils.DingDingCoolDown.containsKey(player) && Utils.DingDingCoolDown.get(player) > player.getServer().getTickCount()) {
                Compute.sendFormatMSG(player, Component.literal("增强叮!").withStyle(ChatFormatting.AQUA),
                        Component.literal("一分钟内只能发送一次增强叮叮叮喔！").withStyle(ChatFormatting.WHITE));
            } else {
                CompoundTag data = target.getPersistentData();
                Utils.PlayerAFKMap.put(target, true);
                data.putDouble("XRot", player.getXRot());
                data.putDouble("YRot", player.getYRot());
                if (!player.isCreative()) Utils.DingDingCoolDown.put(player, player.getServer().getTickCount() + 1200);
                Compute.sendFormatMSG(player, Component.literal("增强叮!").withStyle(ChatFormatting.AQUA),
                        Component.literal("你向 ").withStyle(ChatFormatting.WHITE).
                                append(target.getDisplayName()).
                                append(Component.literal(" 发送了一个增强叮!").withStyle(ChatFormatting.WHITE)));
            }
        }

        return 0;
    }


}
