package com.very.wraq.commands.stable.ops;

import com.very.wraq.events.core.LevelEvents;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class GiantCommand implements Command<CommandSourceStack> {
    public static GiantCommand instance = new GiantCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        LevelEvents.SummonGiant(player.level());
        return 0;
    }
}
