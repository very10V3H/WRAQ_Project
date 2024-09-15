package com.very.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.StringUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class BowCommand implements Command<CommandSourceStack> {
    public static BowCommand instance = new BowCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        if (data.contains(StringUtils.BowSlowDown))
            data.putBoolean(StringUtils.BowSlowDown, !data.getBoolean(StringUtils.BowSlowDown));
        else data.putBoolean(StringUtils.BowSlowDown, false);
        if (data.getBoolean(StringUtils.BowSlowDown)) {
            Compute.sendFormatMSG(player, Component.literal("弓").withStyle(ChatFormatting.GREEN),
                    Component.literal("你已打开弓箭的视角拉伸。").withStyle(ChatFormatting.WHITE));
        } else {
            Compute.sendFormatMSG(player, Component.literal("弓").withStyle(ChatFormatting.GREEN),
                    Component.literal("你已关闭弓箭的视角拉伸。").withStyle(ChatFormatting.WHITE));

        }
        return 0;
    }
}
