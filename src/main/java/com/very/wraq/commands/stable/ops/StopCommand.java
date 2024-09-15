package com.very.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.common.util.Utils;
import net.minecraft.commands.CommandSourceStack;

public class StopCommand implements Command<CommandSourceStack> {
    public static StopCommand instance = new StopCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Utils.ServerStopTick = 1200;
        return 0;
    }
}
