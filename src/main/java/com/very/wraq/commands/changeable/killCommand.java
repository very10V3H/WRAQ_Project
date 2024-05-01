package com.very.wraq.commands.changeable;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class killCommand implements Command<CommandSourceStack> {
    public static killCommand instance = new killCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();

        Component[] Name = {Component.literal("平原僵尸").withStyle(ChatFormatting.GREEN),
                Component.literal("森林骷髅").withStyle(ChatFormatting.DARK_GREEN),
                Component.literal("森林僵尸").withStyle(ChatFormatting.DARK_GREEN),
                Component.literal("湖泊守卫者").withStyle(ChatFormatting.BLUE),
                Component.literal("火山烈焰").withStyle(ChatFormatting.YELLOW),
                Component.literal("矿洞僵尸").withStyle(ChatFormatting.GRAY),
                Component.literal("矿洞骷髅").withStyle(ChatFormatting.GRAY),
                Component.literal("冰川流浪者").withStyle(ChatFormatting.AQUA),
                Component.literal("天空城的不速之客").withStyle(ChatFormatting.AQUA),
                Component.literal("森林唤魔者").withStyle(CustomStyle.styleOfMana),
                Component.literal("森林唤魔大师").withStyle(CustomStyle.styleOfMana),
                Component.literal("脆弱的灵魂").withStyle(CustomStyle.styleOfBlackForest),
                Component.literal("神殿守卫").withStyle(CustomStyle.styleOfSea),
                Component.literal("唤雷守卫").withStyle(CustomStyle.styleOfLightingIsland),
                Component.literal("下界凋零骷髅").withStyle(CustomStyle.styleOfNether),
                Component.literal("下界猪灵").withStyle(CustomStyle.styleOfNether),
                Component.literal("下界遗骸").withStyle(CustomStyle.styleOfNether),
                Component.literal("下界能量聚合体").withStyle(CustomStyle.styleOfNether),
                Component.literal("狂风").withStyle(CustomStyle.styleOfKaze),
                Component.literal("微光蜘蛛").withStyle(CustomStyle.styleOfSpider),
                Component.literal("终界银鱼").withStyle(CustomStyle.styleOfEnd),
                Component.literal("终界使者").withStyle(CustomStyle.styleOfEnd),
                Component.literal("樱灵").withStyle(CustomStyle.styleOfSakura),
                Component.literal("稻草人").withStyle(CustomStyle.styleOfWheat),
                Component.literal("紫晶矿工").withStyle(CustomStyle.styleOfPurpleIron),
                Component.literal("废旧船厂员工").withStyle(CustomStyle.styleOfShip),
                Component.literal("冰原猎手").withStyle(CustomStyle.styleOfIce),
                Component.literal("魔力衍生物-地魔").withStyle(CustomStyle.styleOfMana),
                Component.literal("魔力衍生物-血魔").withStyle(CustomStyle.styleOfBloodMana),

                Component.literal("莘岛史莱姆").withStyle(CustomStyle.styleOfHealth),
                Component.literal("烽火台哨卫").withStyle(CustomStyle.styleOfPower),
                Component.literal("古树魔能研究者").withStyle(CustomStyle.styleOfMana),
                Component.literal("熔岩湖溢出物").withStyle(CustomStyle.styleOfPower),
                Component.literal("梦灵").withStyle(CustomStyle.styleOfMoon1),
                Component.literal("生机元素").withStyle(CustomStyle.styleOfLife),
                Component.literal("碧水元素").withStyle(CustomStyle.styleOfWater),
                Component.literal("炽焰元素").withStyle(CustomStyle.styleOfFire),
                Component.literal("层岩元素").withStyle(CustomStyle.styleOfStone),
                Component.literal("凛冰元素").withStyle(CustomStyle.styleOfIce),
                Component.literal("怒雷元素").withStyle(CustomStyle.styleOfLightning),
                Component.literal("澄风元素").withStyle(CustomStyle.styleOfWind),
                Component.literal("寂域遗骸").withStyle(CustomStyle.styleOfEnd),
                Component.literal("寂域灵螨").withStyle(CustomStyle.styleOfEnd),
                Component.literal("终界征讨者遗骸").withStyle(CustomStyle.styleOfEnd),

                Component.literal("总击杀数").withStyle(ChatFormatting.AQUA),
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

                StringUtils.KillCount.Beacon,
                StringUtils.KillCount.Tree,
                StringUtils.KillCount.Blaze,
                StringUtils.KillCount.Star,
                StringUtils.MobName.LifeElement,
                StringUtils.MobName.WaterElement,
                StringUtils.MobName.FireElement,
                StringUtils.MobName.StoneElement,
                StringUtils.MobName.IceElement,
                StringUtils.MobName.LightningElement,
                StringUtils.MobName.WindElement,
                StringUtils.MobName.Shulker,
                StringUtils.MobName.EnderMite,
                StringUtils.MobName.EndStray,

                "KillCountTotal"
        };
        player.sendSystemMessage(Component.literal("击杀数统计:").withStyle(ChatFormatting.RED));
        for (int i = 0; i < Name.length; i++) {
            if (data.getInt(Count[i]) > 0) {
                player.sendSystemMessage(Component.literal("").withStyle(ChatFormatting.WHITE).
                        append(Name[i]).append(Component.literal(" " + data.getInt(Count[i]))));
            }
        }

        return 0;
    }
}
