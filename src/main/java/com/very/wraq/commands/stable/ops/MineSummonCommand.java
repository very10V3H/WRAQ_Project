package com.very.wraq.commands.stable.ops;

import com.very.wraq.common.Compute;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MineSummonCommand implements Command<CommandSourceStack> {
    public static MineSummonCommand instance = new MineSummonCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
        if (player.isCreative()) Compute.purpleMineBlockPosInit(overWorld, true);
        if (!player.isCreative())
            Compute.formatMSGSend(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                    Component.literal("仅管理员可用。").withStyle(ChatFormatting.WHITE));

        return 0;
    }
}
