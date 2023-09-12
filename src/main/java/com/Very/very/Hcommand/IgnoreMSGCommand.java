package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class IgnoreMSGCommand implements Command<CommandSourceStack> {
    public static IgnoreMSGCommand instance = new IgnoreMSGCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        String string = StringArgumentType.getString(context,"type");
        if (string.equals("Fight")) {
            if(!data.contains("IgnoreFight") || !data.getBoolean("IgnoreFight")) {
                data.putBoolean("IgnoreFight",true);
                Compute.FormatMSGSend(player,Component.literal("屏蔽").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有战斗信息。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));

            }
            else {
                data.putBoolean("IgnoreFight",false);
                Compute.FormatMSGSend(player,Component.literal("屏蔽").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收战斗信息。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
        }
        else if (string.equals("Rune")) {
            if(!data.contains("IgnoreRune") || !data.getBoolean("IgnoreRune")) {
                data.putBoolean("IgnoreRune",true);
                Compute.FormatMSGSend(player,Component.literal("屏蔽").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有符石信息。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
            else {
                data.putBoolean("IgnoreRune",false);
                Compute.FormatMSGSend(player,Component.literal("屏蔽").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收符石信息。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
        }
        else if (string.equals("Exp")) {
            if(!data.contains("IgnoreExp") || !data.getBoolean("IgnoreExp")) {
                data.putBoolean("IgnoreExp",true);
                Compute.FormatMSGSend(player,Component.literal("屏蔽").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有经验信息。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));

            }
            else {
                data.putBoolean("IgnoreExp",false);
                Compute.FormatMSGSend(player,Component.literal("屏蔽").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收经验信息。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
        }
        else if (string.equals("QuickUse")) {
            if(!data.contains("IgnoreUse") || !data.getBoolean("IgnoreUse")) {
                data.putBoolean("IgnoreUse",true);
                Compute.FormatMSGSend(player,Component.literal("屏蔽").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有快捷使用信息。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));

            }
            else {
                data.putBoolean("IgnoreUse",false);
                Compute.FormatMSGSend(player,Component.literal("屏蔽").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收快捷使用信息。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
        }
        else {
            Compute.FormatMSGSend(player,Component.literal("屏蔽").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                    Component.literal("当前支持的屏蔽类型为战斗(Fight)、符石(Rune)、经验(Exp)、快捷使用(QuickUse)。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));

        }
        return 0;
    }
}
