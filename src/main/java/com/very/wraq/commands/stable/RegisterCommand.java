package com.very.wraq.commands.stable;

import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class RegisterCommand implements Command<CommandSourceStack> {

    public static RegisterCommand instance = new RegisterCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final String string0 = StringArgumentType.getString(context,"passwd");
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        if(data.contains(StringUtils.Login.Password) && data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online)) {
            data.putString(StringUtils.Login.Password, string0);
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("密码已修改。").withStyle(ChatFormatting.WHITE)));
        }
        else {
            data.putString(StringUtils.Login.Password, string0);
            data.putString(StringUtils.Login.Status, StringUtils.Login.Online);
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("注册成功，再次输入该指令格式可以修改你的密码。").withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契欢迎你的到来！").withStyle(ChatFormatting.WHITE)));
        }
        return Command.SINGLE_SUCCESS;
    }
}
