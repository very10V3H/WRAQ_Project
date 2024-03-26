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
                Component.literal("森林唤魔大师").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana),
                Component.literal("脆弱的灵魂").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBlackForest),
                Component.literal("神殿守卫").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                Component.literal("唤雷守卫").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland),
                Component.literal("下界凋零骷髅").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                Component.literal("下界猪灵").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                Component.literal("下界遗骸").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                Component.literal("下界能量聚合体").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                Component.literal("狂风").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfKaze),
                Component.literal("微光蜘蛛").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                Component.literal("终界银鱼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                Component.literal("终界使者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                Component.literal("樱灵").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSakura),
                Component.literal("稻草人").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWheat),
                Component.literal("紫晶矿工").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron),
                Component.literal("废旧船厂员工").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfShip),
                Component.literal("冰原猎手").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfIce),
                Component.literal("魔力衍生物-地魔").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana),
                Component.literal("魔力衍生物-血魔").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBloodMana),
                Component.literal("莘岛史莱姆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfHealth),
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
                "KillCountOfEvokerMaster",
                "KillCountOfBFHusk",
                "KillCountOfGuardian",
                "KillCountOfLZ",
                "KillCountOfWitherSkeleton",
                "KillCountOfZombiePigLin",
                "KillCountOfNetherSkeleton",
                "KillCountOfNetherMagma",
                "KillCountOfKaze",
                "KillCountOfSpider",
                "KillCountOfSilverfish",
                "KillCountOfEnderMan",
                StringUtils.KillCount.SakuraMob,
                StringUtils.KillCount.Scarecrow,
                StringUtils.KillCount.MineWorker,
                StringUtils.KillCount.ShipWorker,
                StringUtils.KillCount.IceHunter,
                StringUtils.KillCount.EarthMana,
                StringUtils.KillCount.BloodMana,
                StringUtils.KillCount.Slime,
                "KillCountTotal"
        };
        player.sendSystemMessage(Component.literal("击杀数统计:").withStyle(ChatFormatting.RED));
        for (int i = 0; i < Name.length; i++) {
            if (data.getInt(Count[i]) > 0) {
                player.sendSystemMessage(Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                        append(Name[i]).append(Component.literal(" " + data.getInt(Count[i]))));
            }
        }

        return 0;
    }
}
