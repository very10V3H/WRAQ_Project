package com.very.wraq.commands.stable.ops;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
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

public class PlayerTagCommand implements Command<CommandSourceStack> {
    public static PlayerTagCommand instance = new PlayerTagCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "player");
        String type = StringArgumentType.getString(context, "type");
        String key = StringArgumentType.getString(context, "key");
        String valueString = StringArgumentType.getString(context, "value");
        ServerPlayer target = null;
        for (GameProfile profile : gameProfile) {
            target = player.getServer().getPlayerList().getPlayer(profile.getId());
        }
        if (target == null) {
            player.sendSystemMessage(Component.literal("玩家似乎不在线"));
            return 0;
        }

        CompoundTag data = target.getPersistentData();
        switch (type) {
            case "int" -> {
                int value = Integer.parseInt(valueString);
                data.putInt(key, value);
                player.sendSystemMessage(Component.literal("已添加:(" + key + "," + valueString + ")至 ").withStyle(ChatFormatting.WHITE).
                        append(target.getDisplayName()));
            }
            case "double" -> {
                double value = Double.parseDouble(valueString);
                data.putDouble(key, value);
                player.sendSystemMessage(Component.literal("已添加:(" + key + "," + valueString + ")至 ").withStyle(ChatFormatting.WHITE).
                        append(target.getDisplayName()));
            }
            case "string" -> {
                data.putString(key, valueString);
                player.sendSystemMessage(Component.literal("已添加:(" + key + "," + valueString + ")至").withStyle(ChatFormatting.WHITE).
                        append(target.getDisplayName()));
            }
            case "remove" -> {
                data.remove(key);
                player.sendSystemMessage(Component.literal("已移除:(" + key + ")于").withStyle(ChatFormatting.WHITE).
                        append(target.getDisplayName()));
            }
            default -> {
                player.sendSystemMessage(Component.literal("检查类型拼写"));
            }

        }
        return 0;
    }
}
