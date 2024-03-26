package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class LoginCommand implements Command<CommandSourceStack> {
    public static LoginCommand instance = new LoginCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String string = StringArgumentType.getString(context,"passwd");
        Player player = context.getSource().getPlayer();
        ServerPlayer serverPlayer = (ServerPlayer) player;
        CompoundTag data = player.getPersistentData();
        if(data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online))
        {
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("你已经在线了！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            return 0;
        }
        if(data.contains(StringUtils.Login.Password))
        {
            if(data.getString(StringUtils.Login.Password).equals(string))
            {
                data.putString(StringUtils.Login.Status,StringUtils.Login.Online);
                Utils.IpLoginMap.put(serverPlayer.getName().getString(),serverPlayer.getIpAddress());
                Utils.IpArrayList.add(serverPlayer.getIpAddress());
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("登录成功！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
            else player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("密码错误！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        }
        else  player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("请先注册喔！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));;
        return 0;
    }
}
