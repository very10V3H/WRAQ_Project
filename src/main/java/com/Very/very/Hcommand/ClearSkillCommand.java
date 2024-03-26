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


public class ClearSkillCommand implements Command<CommandSourceStack> {
    public static ClearSkillCommand instance = new ClearSkillCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player.isCreative()) {
            Compute.ResetSkillAndAbility(player);
        }
        else {
            if (!player.isCreative()) {
                Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                        Component.literal("此命令仅管理员可用。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
        }
        return 0;
    }
}
