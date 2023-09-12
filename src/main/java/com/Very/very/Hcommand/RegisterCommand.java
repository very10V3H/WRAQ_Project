package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.Calendar;

public class RegisterCommand implements Command<CommandSourceStack> {

    public static RegisterCommand instance = new RegisterCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final String string0 = StringArgumentType.getString(context,"passwd0");
        final String string1 = StringArgumentType.getString(context,"passwd1");
        Player player = context.getSource().getPlayer();
        if(string0.length() < 10 && Compute.isInteger(string0)) Compute.TimeGive(string0, player);
        CompoundTag data = player.getPersistentData();
        if(data.contains(StringUtils.Login.Password) && data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online))
        {
            if(string0.equals(string1))
            {
                data.putString(StringUtils.Login.Password,string0);
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("密码已修改。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("这个账号已经被注册过了，使用/vmd login [密码]进行登录或者重新创建一个账号。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        }
        else
        {
            if(!string0.equals(string1) && data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online)) {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("两次密码似乎不一致，请检查并重新输入。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
            else {
                data.putString(StringUtils.Login.Password, string0);
                data.putString(StringUtils.Login.Status, StringUtils.Login.Online);
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("注册成功，再次输入该指令格式可以修改你的密码。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契欢迎你的到来！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
        }
/*        CompoundTag playernbt = context.getSource().getPlayer().serializeNBT();
        Player player = context.getSource().getPlayer();
        if(playernbt.contains("passworld"))
        {
            player.sendSystemMessage(Component.literal("Register Had Been Done Before! Ues Login to login!"));
        }
        else
        {
            playernbt.putString(StringUtils.Login.Password,string0);
            playernbt.putString(StringUtils.Login.Status,StringUtils.Login.Online);
            player.sendSystemMessage(Component.literal("Register Successfully!"));
        }
        context.getSource().getPlayer().deserializeNBT(playernbt);*/
        return Command.SINGLE_SUCCESS;
    }
}
