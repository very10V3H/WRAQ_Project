package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class prefixCommand implements Command<CommandSourceStack> {
    public static prefixCommand instance = new prefixCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        int Count = 0;
        Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                Component.literal("可用称号如下:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        if (data.contains("KillCountOfPlainZombie") && data.getInt("KillCountOfPlainZombie") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("平原统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
        }
        if (data.contains("KillCountOfForestSkeleton") && data.contains("KillCountOfForestZombie")
                && (data.getInt("KillCountOfForestSkeleton")+data.getInt("KillCountOfForestZombie")) > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("森林统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN)));
        }
        if (data.contains("KillCountOfLakeDrowned") && data.getInt("KillCountOfLakeDrowned") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("湖泊统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
        }
        if (data.contains("KillCountOfVolcanoBlazw") && data.getInt("KillCountOfVolcanoBlazw") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("火山统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)));
        }
        if (data.contains("KillCountOfSnowStray") && data.getInt("KillCountOfSnowStray") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("冰川统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
        }
        if (data.contains("KillCountOfVex") && data.getInt("KillCountOfVex") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("天空统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
        }
        if (data.contains("KillCountOfEvoker") && data.getInt("KillCountOfEvoker") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("唤魔森林统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
        }
        if (data.contains("KillCountOfGuardian") && data.getInt("KillCountOfGuardian") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("神殿统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)));
        }
        if (data.contains("KillCountOfLZ") && data.getInt("KillCountOfLZ") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("唤雷岛统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland)));
        }
        if (data.contains("KillCountOfBFHusk") && data.getInt("KillCountOfBFHusk") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("黑色森林统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBlackForest)));
        }
        if (data.contains("securityGet") && data.getFloat("securityGet") > 100000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("股市巴菲特").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
        }
        if (data.contains("securityGet") && data.getFloat("securityGet") < 100000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("股市墨菲特").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GRAY)));
        }
        Count ++;
        player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Utils.BrewingLevelName[data.getInt("BrewingLevel")]));
        Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                Component.literal("使用/vmd prefix [编号]来激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        return 0;
    }
}
