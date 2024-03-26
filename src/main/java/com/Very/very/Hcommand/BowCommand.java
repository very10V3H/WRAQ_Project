package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
        if (data.contains(StringUtils.BowSlowDown)) data.putBoolean(StringUtils.BowSlowDown,!data.getBoolean(StringUtils.BowSlowDown));
        else data.putBoolean(StringUtils.BowSlowDown,false);
        if (data.getBoolean(StringUtils.BowSlowDown)) {
            Compute.FormatMSGSend(player,Component.literal("弓").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                    Component.literal("你已打开弓箭的视角拉伸。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        }
        else {
            Compute.FormatMSGSend(player,Component.literal("弓").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                    Component.literal("你已关闭弓箭的视角拉伸。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));

        }
        return 0;
    }
}
