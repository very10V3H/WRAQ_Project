package com.very.wraq.commands.stable;

import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class DebugCommand implements Command<CommandSourceStack> {
    public static DebugCommand instance = new DebugCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        data.putBoolean(StringUtils.Debug, !data.getBoolean(StringUtils.Debug));
        if (data.getBoolean(StringUtils.Debug)) {
            Compute.FormatMSGSend(player, Component.literal("调试").withStyle(CustomStyle.styleOfEnd),
                    Component.literal("你已开启伤害构成显示。").withStyle(ChatFormatting.WHITE));
        }
        else {
            Compute.FormatMSGSend(player, Component.literal("调试").withStyle(CustomStyle.styleOfEnd),
                    Component.literal("你已关闭伤害构成显示。").withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
