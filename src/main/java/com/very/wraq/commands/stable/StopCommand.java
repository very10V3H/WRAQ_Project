package com.very.wraq.commands.stable;

import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

public class StopCommand implements Command<CommandSourceStack> {
    public static StopCommand instance = new StopCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Utils.ServerStopTick = 1200;
        return 0;
    }
}
