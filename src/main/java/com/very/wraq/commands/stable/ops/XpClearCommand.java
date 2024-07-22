package com.very.wraq.commands.stable.ops;

import com.very.wraq.common.Compute;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class XpClearCommand implements Command<CommandSourceStack> {
    public static XpClearCommand instance = new XpClearCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        if (player.isCreative()) {
            player.experienceLevel = 0;
            data.putInt("Xp", 0);
        } else {
            Compute.formatMSGSend(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                    Component.literal("此命令仅管理员可用。").withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
