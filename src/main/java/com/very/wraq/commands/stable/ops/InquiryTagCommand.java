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

public class InquiryTagCommand implements Command<CommandSourceStack> {
    public static InquiryTagCommand instance = new InquiryTagCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "player");
        String type = StringArgumentType.getString(context, "type");
        String key = StringArgumentType.getString(context, "key");
        CompoundTag data = player.getPersistentData();
        ServerPlayer target = null;
        for (GameProfile profile : gameProfile) {
            target = player.getServer().getPlayerList().getPlayer(profile.getId());
        }
        if (target == null) {
            player.sendSystemMessage(Component.literal("玩家似乎不在线"));
            return 0;
        }
        switch (type) {
            case "int" -> {
                int value = data.getInt(key);
                player.sendSystemMessage(Component.literal("").withStyle(ChatFormatting.WHITE).
                        append(target.getDisplayName()).
                        append(Component.literal(": (" + key + "," + value + ")").withStyle(ChatFormatting.WHITE)));
            }
            case "double" -> {
                double value = data.getDouble(key);
                player.sendSystemMessage(Component.literal("").withStyle(ChatFormatting.WHITE).
                        append(target.getDisplayName()).
                        append(Component.literal(": (" + key + "," + value + ")").withStyle(ChatFormatting.WHITE)));
            }
            case "string" -> {
                String value = data.getString(key);
                player.sendSystemMessage(Component.literal("").withStyle(ChatFormatting.WHITE).
                        append(target.getDisplayName()).
                        append(Component.literal(": (" + key + "," + value + ")").withStyle(ChatFormatting.WHITE)));
            }
            default -> player.sendSystemMessage(Component.literal("检查类型拼写"));
        }
        return 0;
    }
}
