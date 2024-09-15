package com.very.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.common.util.StringUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class XpCommand implements Command<CommandSourceStack> {
    public static XpCommand instance = new XpCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String level = StringArgumentType.getString(context, "level");
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.ExpLevel, Integer.parseInt(level));
        data.putDouble("Xp", 0);
        return 0;
    }


}
