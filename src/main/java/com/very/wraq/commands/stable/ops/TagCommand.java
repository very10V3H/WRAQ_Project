package com.very.wraq.commands.stable.ops;

import com.very.wraq.common.util.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class TagCommand implements Command<CommandSourceStack> {
    public static TagCommand instance = new TagCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String type = StringArgumentType.getString(context, "type");
        String key = StringArgumentType.getString(context, "key");
        String valueString = StringArgumentType.getString(context, "value");
        CompoundTag data = player.getMainHandItem().getOrCreateTagElement(Utils.MOD_ID);
        switch (type) {
            case "int" -> {
                int value = Integer.parseInt(valueString);
                data.putInt(key, value);
                player.sendSystemMessage(Component.literal("已添加:(" + key + "," + valueString + ")"));
            }
            case "double" -> {
                double value = Double.parseDouble(valueString);
                data.putDouble(key, value);
                player.sendSystemMessage(Component.literal("已添加:(" + key + "," + valueString + ")"));
            }
            case "string" -> {
                data.putString(key, valueString);
                player.sendSystemMessage(Component.literal("已添加:(" + key + "," + valueString + ")"));
            }
            case "remove" -> {
                data.remove(key);
                player.sendSystemMessage(Component.literal("已移除:(" + key + ")"));
            }
            default -> {
                player.sendSystemMessage(Component.literal("检查类型拼写"));
            }
        }
        return 0;
    }
}
