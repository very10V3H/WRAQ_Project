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
        String string = StringArgumentType.getString(context, "num");
        int ChooseCount = Integer.parseInt(string);
        int Count = 0;
        boolean flag = true;
        Count++;
        if (ChooseCount == Count) {
            data.putString("Prefix", "初来乍到");
            Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                    Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                            append(Component.literal("初来乍到").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            flag = false;
        }
        if (data.contains("KillCountOfPlainZombie") && data.getInt("KillCountOfPlainZombie") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "平原统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("平原统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfForestSkeleton") && data.contains("KillCountOfForestZombie")
                && (data.getInt("KillCountOfForestSkeleton") + data.getInt("KillCountOfForestZombie")) > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "森林统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("森林统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfLakeDrowned") && data.getInt("KillCountOfLakeDrowned") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "湖泊统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("湖泊统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfVolcanoBlazw") && data.getInt("KillCountOfVolcanoBlazw") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "火山统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("火山统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfSnowStray") && data.getInt("KillCountOfSnowStray") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "冰川统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("冰川统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfVex") && data.getInt("KillCountOfVex") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "天空统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("天空统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfEvoker") && data.getInt("KillCountOfEvoker") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "唤魔森林统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("唤魔森林统治者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfGuardian") && data.getInt("KillCountOfGuardian") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "神殿统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("神殿统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfLZ") && data.getInt("KillCountOfLZ") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "唤雷岛统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("唤雷岛统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfBFHusk") && data.getInt("KillCountOfLZ") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "黑色森林统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("黑色森林统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBlackForest)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfKaze") && data.getInt("KillCountOfKaze") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "风之谷统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("风之谷统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfKaze)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfSpider") && data.getInt("KillCountOfSpider") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "微光森林统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("微光森林统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider)));
                flag = false;
            }
        }
        if (data.contains("KillCountOfEnderMan") && data.getInt("KillCountOfEnderMan") > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "终界统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("终界统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.KillCount.SakuraMob) && data.getInt(StringUtils.KillCount.SakuraMob) > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "绯樱树林统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("绯樱树林统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSakura)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.KillCount.Scarecrow) && data.getInt(StringUtils.KillCount.Scarecrow) > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "樱岛稻田统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("樱岛稻田统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWheat)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.KillCount.MineWorker) && data.getInt(StringUtils.KillCount.MineWorker) > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "紫晶矿洞统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("紫晶矿洞统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.KillCount.IceHunter) && data.getInt(StringUtils.KillCount.IceHunter) > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "冰原统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("冰原统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfIce)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.KillCount.ShipWorker) && data.getInt(StringUtils.KillCount.ShipWorker) > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "废旧船厂统治者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("废旧船厂统治者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfShip)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.KillCount.EarthMana) && data.getInt(StringUtils.KillCount.EarthMana) > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "旧世地蕴封印者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("旧世地蕴封印者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.KillCount.BloodMana) && data.getInt(StringUtils.KillCount.BloodMana) > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "旧世腥月封印者");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("旧世腥月封印者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBloodMana)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.KillCount.Slime) && data.getInt(StringUtils.KillCount.Slime) > 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "史莱姆的好伙伴(?)");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("史莱姆的好伙伴(?)").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfHealth)));
                flag = false;
            }
        }
        if (data.contains("securityGet") && data.getDouble("securityGet") > 100000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "股市巴菲特");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("股市巴菲特").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
                flag = false;
            }
        }
        if (data.contains("securityGet") && data.getDouble("securityGet") < 100000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "股市墨菲特");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("股市墨菲特").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GRAY)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 20 && data.getInt(StringUtils.FishCount) < 50) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "见习渔夫");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("见习渔夫").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GRAY)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 50 && data.getInt(StringUtils.FishCount) < 100) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "入门渔夫");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("入门渔夫").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 100 && data.getInt(StringUtils.FishCount) < 200) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "中阶渔夫");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("中阶渔夫").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 200 && data.getInt(StringUtils.FishCount) < 500) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "高阶渔夫");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("高阶渔夫").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 500 && data.getInt(StringUtils.FishCount) < 1000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "经常空军的钓鱼佬");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("经常空军的钓鱼佬").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 1000 && data.getInt(StringUtils.FishCount) < 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "偶尔空军的钓鱼佬");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("偶尔空军的钓鱼佬").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.FishCount) && data.getInt(StringUtils.FishCount) >= 2000) {
            Count++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "永不空军的钓鱼佬");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("永不空军的钓鱼佬").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
                flag = false;
            }
        }
        if (Compute.playerMineLevel(player) == 1) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "见习矿工");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("见习矿工").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GRAY)));
                flag = false;
            }
        }
        if (Compute.playerMineLevel(player) == 2) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "入门矿工");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("入门矿工").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                flag = false;
            }
        }
        if (Compute.playerMineLevel(player) == 3) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "职业矿工");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("职业矿工").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
                flag = false;
            }
        }
        if (Compute.playerMineLevel(player) == 4) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "悲催苦力矿工");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("悲催苦力矿工").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
                flag = false;
            }
        }
        if (Compute.playerMineLevel(player) == 5) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "一只挖矿的帕鲁");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("一只挖矿的帕鲁").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                flag = false;
            }
        }
        if (Compute.playerLopLevel(player) == 1) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "见习伐木工");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("见习伐木工").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GRAY)));
                flag = false;
            }
        }
        if (Compute.playerLopLevel(player) == 2) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "入门伐木工");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("入门伐木工").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                flag = false;
            }
        }
        if (Compute.playerLopLevel(player) == 3) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "职业伐木工");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("职业伐木工").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
                flag = false;
            }
        }
        if (Compute.playerLopLevel(player) == 4) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "光头强");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("光头强").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
                flag = false;
            }
        }
        if (Compute.playerLopLevel(player) == 5) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "一只砍树的帕鲁");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("一只砍树的帕鲁").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                flag = false;
            }
        }
        if (Compute.playerGardeningLevel(player) == 1) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "见习农夫");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("见习农夫").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GRAY)));
                flag = false;
            }
        }
        if (Compute.playerGardeningLevel(player) == 2) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "入门农夫");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("入门农夫").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                flag = false;
            }
        }
        if (Compute.playerGardeningLevel(player) == 3) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "职业农夫");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("职业农夫").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
                flag = false;
            }
        }
        if (Compute.playerGardeningLevel(player) == 4) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "农耕大师");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("农耕大师").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
                flag = false;
            }
        }
        if (Compute.playerGardeningLevel(player) == 5) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "一只种田的帕鲁");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("一只种田的帕鲁").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.DragonPrefix)) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "龙行龘龘");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("龙行龘龘").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpring)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.XiangLiPrefix)) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "理塘王");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("理塘王").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfField)));
                flag = false;
            }
        }
        if (data.contains(StringUtils.LotteryPrefix)) {
            Count ++;
            if (ChooseCount == Count) {
                data.putString("Prefix", "赌神");
                Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                        Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(Component.literal("赌徒").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
                flag = false;
            }
        }
        Count++;
        if (ChooseCount == Count) {
            data.putString("Prefix", Utils.BrewingLevelName[data.getInt("BrewingLevel")].getString());
            Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                    Component.literal("已激活称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                            append(Utils.BrewingLevelName[data.getInt("BrewingLevel")]));
            flag = false;
        }
        if (flag) {
            Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                    Component.literal("似乎还没有达到解锁此称号的条件。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        } else {
            Compute.FormatMSGSend(player, Component.literal("称号").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),
                    Component.literal("称号需等待5s左右方可生效。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
