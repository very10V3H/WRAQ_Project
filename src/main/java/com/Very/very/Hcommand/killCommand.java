package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.ListIterator;

public class killCommand implements Command<CommandSourceStack> {
    public static killCommand instance = new killCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        if (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online)) {
            Component[] Name = {Component.literal("平原僵尸").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                    Component.literal("森林骷髅").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN),
                    Component.literal("森林僵尸").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN),
                    Component.literal("湖泊守卫者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE),
                    Component.literal("火山烈焰").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW),
                    Component.literal("矿洞僵尸").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                    Component.literal("矿洞骷髅").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                    Component.literal("冰川流浪者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("天空城的不速之客").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("森林唤魔者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana),
                    Component.literal("脆弱的灵魂").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBlackForest),
                    Component.literal("神殿守卫").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                    Component.literal("唤雷守卫").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland),
                    Component.literal("下界凋零骷髅").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                    Component.literal("下界猪灵").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                    Component.literal("下界遗骸").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                    Component.literal("下界能量聚合体").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                    Component.literal("总击杀数").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
            };
            String[] Count = {
                    "KillCountOfPlainZombie",
                    "KillCountOfForestSkeleton",
                    "KillCountOfForestZombie",
                    "KillCountOfLakeDrowned",
                    "KillCountOfVolcanoBlazw",
                    "KillCountOfMineZombie",
                    "KillCountOfMineSkeleton",
                    "KillCountOfSnowStray",
                    "KillCountOfVex",
                    "KillCountOfEvoker",
                    "KillCountOfBFHusk",
                    "KillCountOfGuardian",
                    "KillCountOfLZ",
                    "KillCountOfWitherSkeleton",
                    "KillCountOfZombiePigLin",
                    "KillCountOfNetherSkeleton",
                    "KillCountOfNetherMagma",
                    "KillCountTotal"
            };
            player.sendSystemMessage(Component.literal("击杀数统计:").withStyle(ChatFormatting.RED));
            for(int i = 0; i < 18; i++)
            {
                if(i == 17){
                    int num = 0;
                    for(int j = 0; j < 17; j++){
                        num += data.getInt(Count[j]);
                    }
                    player.sendSystemMessage(Component.literal("").
                            append(Name[i]).
                            append(Component.literal(":"+num)));
                }
                else{
                    player.sendSystemMessage(Component.literal("").
                            append(Name[i]).
                            append(Component.literal(":"+data.getInt(Count[i]))));
                }
            }
        }
        else {
            Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("请先登录。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));

        }
        return 0;
    }
}
