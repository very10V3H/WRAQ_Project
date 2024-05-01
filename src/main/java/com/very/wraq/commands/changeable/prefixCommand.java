package com.very.wraq.commands.changeable;

import com.very.wraq.files.dataBases.DataBase;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.sql.SQLException;

public class prefixCommand implements Command<CommandSourceStack> {
    public static prefixCommand instance = new prefixCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        int Count = 0;
        Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.GOLD),
                Component.literal("可用称号如下:").withStyle(ChatFormatting.WHITE));
        Count ++;
        player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                append(Component.literal("初来乍到").withStyle(ChatFormatting.GRAY)));

        if (data.contains("KillCountOfPlainZombie") && data.getInt("KillCountOfPlainZombie") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("平原统治者").withStyle(ChatFormatting.GREEN)));
        }
        if (data.contains("KillCountOfForestSkeleton") && data.contains("KillCountOfForestZombie")
                && (data.getInt("KillCountOfForestSkeleton")+data.getInt("KillCountOfForestZombie")) > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("森林统治者").withStyle(ChatFormatting.DARK_GREEN)));
        }
        if (data.contains("KillCountOfLakeDrowned") && data.getInt("KillCountOfLakeDrowned") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("湖泊统治者").withStyle(ChatFormatting.BLUE)));
        }
        if (data.contains("KillCountOfVolcanoBlazw") && data.getInt("KillCountOfVolcanoBlazw") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("火山统治者").withStyle(ChatFormatting.YELLOW)));
        }
        if (data.contains("KillCountOfSnowStray") && data.getInt("KillCountOfSnowStray") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("冰川统治者").withStyle(ChatFormatting.AQUA)));
        }
        if (data.contains("KillCountOfVex") && data.getInt("KillCountOfVex") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("天空统治者").withStyle(ChatFormatting.AQUA)));
        }
        if (data.contains("KillCountOfEvoker") && data.getInt("KillCountOfEvoker") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("唤魔森林统治者").withStyle(ChatFormatting.LIGHT_PURPLE)));
        }
        if (data.contains("KillCountOfGuardian") && data.getInt("KillCountOfGuardian") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("神殿统治者").withStyle(CustomStyle.styleOfSea)));
        }
        if (data.contains("KillCountOfLZ") && data.getInt("KillCountOfLZ") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("唤雷岛统治者").withStyle(CustomStyle.styleOfLightingIsland)));
        }
        if (data.contains("KillCountOfBFHusk") && data.getInt("KillCountOfBFHusk") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("黑色森林统治者").withStyle(CustomStyle.styleOfBlackForest)));
        }
        if (data.contains("KillCountOfKaze") && data.getInt("KillCountOfKaze") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("风之谷统治者").withStyle(CustomStyle.styleOfKaze)));
        }
        if (data.contains("KillCountOfSpider") && data.getInt("KillCountOfSpider") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("微光森林统治者").withStyle(CustomStyle.styleOfSpider)));
        }
        if (data.contains("KillCountOfEnderMan") && data.getInt("KillCountOfEnderMan") > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("终界统治者").withStyle(CustomStyle.styleOfEnd)));
        }
        if (data.contains(StringUtils.KillCount.SakuraMob) && data.getInt(StringUtils.KillCount.SakuraMob) > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("绯樱树林统治者").withStyle(CustomStyle.styleOfSakura)));
        }
        if (data.contains(StringUtils.KillCount.Scarecrow) && data.getInt(StringUtils.KillCount.Scarecrow) > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("樱岛稻田统治者").withStyle(CustomStyle.styleOfWheat)));
        }
        if (data.contains(StringUtils.KillCount.MineWorker) && data.getInt(StringUtils.KillCount.MineWorker) > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("紫晶矿洞统治者").withStyle(CustomStyle.styleOfPurpleIron)));
        }
        if (data.contains(StringUtils.KillCount.IceHunter) && data.getInt(StringUtils.KillCount.IceHunter) > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("冰原统治者").withStyle(CustomStyle.styleOfIce)));
        }
        if (data.contains(StringUtils.KillCount.ShipWorker) && data.getInt(StringUtils.KillCount.ShipWorker) > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("废旧船厂统治者").withStyle(CustomStyle.styleOfShip)));
        }
        if (data.contains(StringUtils.KillCount.EarthMana) && data.getInt(StringUtils.KillCount.EarthMana) > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("旧世地蕴封印者").withStyle(CustomStyle.styleOfMana)));
        }
        if (data.contains(StringUtils.KillCount.BloodMana) && data.getInt(StringUtils.KillCount.BloodMana) > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("旧世腥月封印者").withStyle(CustomStyle.styleOfBloodMana)));
        }
        if (data.contains(StringUtils.KillCount.Slime) && data.getInt(StringUtils.KillCount.Slime) > 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("史莱姆的好伙伴(?)").withStyle(CustomStyle.styleOfHealth)));
        }
        if (data.contains("securityGet") && data.getDouble("securityGet") > 100000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("股市巴菲特").withStyle(ChatFormatting.GOLD)));
        }
        if (data.contains("securityGet") && data.getDouble("securityGet") < 100000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("股市墨菲特").withStyle(ChatFormatting.DARK_GRAY)));
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 20 && data.getInt(StringUtils.FishCount) < 50) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("见习渔夫").withStyle(ChatFormatting.DARK_GRAY)));
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 50 && data.getInt(StringUtils.FishCount) < 100) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("入门渔夫").withStyle(ChatFormatting.GRAY)));
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 100 && data.getInt(StringUtils.FishCount) < 200) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("中阶渔夫").withStyle(ChatFormatting.YELLOW)));
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 200 && data.getInt(StringUtils.FishCount) < 500) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("高阶渔夫").withStyle(ChatFormatting.BLUE)));
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 500 && data.getInt(StringUtils.FishCount) < 1000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("经常空军的钓鱼佬").withStyle(ChatFormatting.GOLD)));
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 1000 && data.getInt(StringUtils.FishCount) < 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("偶尔空军的钓鱼佬").withStyle(ChatFormatting.RED)));
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 2000) {
            Count++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("永不空军的钓鱼佬").withStyle(ChatFormatting.LIGHT_PURPLE)));
        }
        if (Compute.playerMineLevel(player) == 1) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("见习矿工").withStyle(ChatFormatting.DARK_GRAY)));
        }
        if (Compute.playerMineLevel(player) == 2) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("入门矿工").withStyle(ChatFormatting.GRAY)));
        }
        if (Compute.playerMineLevel(player) == 3) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("职业矿工").withStyle(ChatFormatting.GOLD)));
        }
        if (Compute.playerMineLevel(player) == 4) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("悲催苦力矿工").withStyle(ChatFormatting.GREEN)));
        }
        if (Compute.playerMineLevel(player) == 5) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("一只挖矿的帕鲁").withStyle(ChatFormatting.AQUA)));
        }
        if (Compute.playerLopLevel(player) == 1) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("见习伐木工").withStyle(ChatFormatting.DARK_GRAY)));
        }
        if (Compute.playerLopLevel(player) == 2) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("入门伐木工").withStyle(ChatFormatting.GRAY)));
        }
        if (Compute.playerLopLevel(player) == 3) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("职业伐木工").withStyle(ChatFormatting.GOLD)));
        }
        if (Compute.playerLopLevel(player) == 4) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("光头强").withStyle(ChatFormatting.GREEN)));
        }
        if (Compute.playerLopLevel(player) == 5) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("一只砍树的帕鲁").withStyle(ChatFormatting.AQUA)));
        }
        if (Compute.playerGardeningLevel(player) == 1) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("见习农夫").withStyle(ChatFormatting.DARK_GRAY)));
        }
        if (Compute.playerGardeningLevel(player) == 2) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("入门农夫").withStyle(ChatFormatting.GRAY)));
        }
        if (Compute.playerGardeningLevel(player) == 3) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("职业农夫").withStyle(ChatFormatting.GOLD)));
        }
        if (Compute.playerGardeningLevel(player) == 4) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("农耕大师").withStyle(ChatFormatting.GREEN)));
        }
        if (Compute.playerGardeningLevel(player) == 5) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("一只种田的帕鲁").withStyle(ChatFormatting.AQUA)));
        }
        if (data.contains(StringUtils.DragonPrefix)) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("龙行龘龘").withStyle(CustomStyle.styleOfSpring)));
        }
        if (data.contains(StringUtils.XiangLiPrefix)) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("理塘王").withStyle(CustomStyle.styleOfField)));
        }
        if (data.contains(StringUtils.LotteryPrefix)) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("赌神").withStyle(ChatFormatting.GOLD)));
        }
        if (data.contains(StringUtils.QingMingPrefix)) {
            Count ++;
            player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("雨纷纷").withStyle(CustomStyle.styleOfHealth)));
        }
        try {
            if (DataBase.get(player, StringUtils.LabourDayPrefix).equals(StringUtils.Exist)) {
                Count ++;
                player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("无产阶级").withStyle(ChatFormatting.GOLD)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Count ++;
        player.sendSystemMessage(Component.literal(Count+".").withStyle(ChatFormatting.WHITE).
                append(Utils.BrewingLevelName[data.getInt("BrewingLevel")]));
        Compute.FormatMSGSend(player,Component.literal("称号").withStyle(ChatFormatting.GOLD),
                Component.literal("使用/vmd prefix [编号]来激活称号").withStyle(ChatFormatting.WHITE));
        return 0;
    }
}
