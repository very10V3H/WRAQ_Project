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
import net.minecraft.world.entity.player.Player;

public class prefixChooseCommand implements Command<CommandSourceStack> {
    public static prefixChooseCommand instance = new prefixChooseCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        if (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online)) {
            String string = StringArgumentType.getString(context,"num");
            int ChooseCount = Integer.parseInt(string);
            int Count = 0;
            boolean flag = true;
            if (data.contains("KillCountOfPlainZombie") && data.getInt("KillCountOfPlainZombie") > 2000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","平原统治者");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("平原统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
                    flag = false;
                }
            }
            if (data.contains("KillCountOfForestSkeleton") && data.contains("KillCountOfForestZombie")
                    && (data.getInt("KillCountOfForestSkeleton")+data.getInt("KillCountOfForestZombie")) > 2000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","森林统治者");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("森林统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN)));
                    flag = false;
                }
            }
            if (data.contains("KillCountOfLakeDrowned") && data.getInt("KillCountOfLakeDrowned") > 2000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","湖泊统治者");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("湖泊统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
                    flag = false;
                }
            }
            if (data.contains("KillCountOfVolcanoBlazw") && data.getInt("KillCountOfVolcanoBlazw") > 2000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","火山统治者");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("火山统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)));
                    flag = false;
                }
            }
            if (data.contains("KillCountOfSnowStray") && data.getInt("KillCountOfSnowStray") > 2000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","冰川统治者");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("冰川统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                    flag = false;
                }
            }
            if (data.contains("KillCountOfVex") && data.getInt("KillCountOfVex") > 2000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","天空统治者");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("天空统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                    flag = false;
                }
            }
            if (data.contains("KillCountOfEvoker") && data.getInt("KillCountOfEvoker") > 2000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","唤魔森林统治者");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("唤魔森林统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
                    flag = false;
                }
            }
            if (data.contains("KillCountOfGuardian") && data.getInt("KillCountOfGuardian") > 2000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","神殿统治者");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("神殿统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)));
                    flag = false;
                }
            }
            if (data.contains("KillCountOfLZ") && data.getInt("KillCountOfLZ") > 2000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","唤雷岛统治者");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("唤雷岛统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland)));
                    flag = false;
                }
            }
            if (data.contains("KillCountOfBFHusk") && data.getInt("KillCountOfLZ") > 2000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","黑色森林统治者");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("黑色森林统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBlackForest)));
                    flag = false;
                }
            }
            if (data.contains("securityGet") && data.getFloat("securityGet") > 100000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","股市巴菲特");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("股市巴菲特").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
                    flag = false;
                }
            }
            if (data.contains("securityGet") && data.getFloat("securityGet") < 100000) {
                Count++;
                if (ChooseCount == Count) {
                    data.putString("Prefix","股市墨菲特");
                    Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                            Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("股市墨菲特").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GRAY)));
                    flag = false;
                }
            }
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix",Utils.BrewingLevelName[data.getInt("BrewingLevel")].getString());
                Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Utils.BrewingLevelName[data.getInt("BrewingLevel")]));
                flag = false;
            }
            if (flag) {
                Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("似乎还没有达到解锁此称号的条件。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
            else {
                Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("需要重新进入世界才能激活称号。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
        }
        else {
            Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("请先登录。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));

        }
        return 0;
    }
}
