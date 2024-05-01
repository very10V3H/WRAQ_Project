package com.very.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.attributeValues.DamageEnhances;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Info1Command implements Command<CommandSourceStack> {
    public static Info1Command instance = new Info1Command();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        player.sendSystemMessage(Component.literal(" - 你的伤害提升属性如下:"));

        player.sendSystemMessage(Component.literal("·普通伤害提升: ").withStyle(CustomStyle.styleOfMoon).
                append(Component.literal(String.format("%.2f%%",DamageEnhances.PlayerCommonDamageUpOrDown(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·最终伤害提升: ").withStyle(ChatFormatting.RED).
                append(Component.literal(String.format("%.2f%%",DamageEnhances.PlayerFinalDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·物理伤害提升: ").withStyle(CustomStyle.styleOfPower).
                append(Component.literal(String.format("%.2f%%",DamageEnhances.PlayerAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·魔法伤害提升: ").withStyle(CustomStyle.styleOfMana).
                append(Component.literal(String.format("%.2f%%", DamageEnhances.PlayerManaDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·近战攻击增幅: ").withStyle(CustomStyle.styleOfVolcano).
                append(Component.literal(String.format("%.2f%%",DamageEnhances.PlayerNormalSwordAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·箭矢攻击增幅: ").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal(String.format("%.2f%%",DamageEnhances.PlayerNormalBowAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·法球攻击增幅: ").withStyle(CustomStyle.styleOfMana).
                append(Component.literal(String.format("%.2f%%",DamageEnhances.PlayerNormalManaAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));


        player.sendSystemMessage(Component.literal(" - 你的副本挑战情况如下:"));
        player.sendSystemMessage(Component.literal("·普莱尼: ").withStyle(ChatFormatting.GREEN).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Plain))));
        player.sendSystemMessage(Component.literal("·四元方块: ").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Main1Boss))));
        player.sendSystemMessage(Component.literal("·下界征讨: ").withStyle(ChatFormatting.RED).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Nether))));
        player.sendSystemMessage(Component.literal("·唤雷岛: ").withStyle(CustomStyle.styleOfLightingIsland).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Lightning))));
        player.sendSystemMessage(Component.literal("·突见忍: ").withStyle(CustomStyle.styleOfSakura).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.SakuraBoss))));
        player.sendSystemMessage(Component.literal("·冰霜骑士: ").withStyle(CustomStyle.styleOfIce).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.IceKnight))));
        player.sendSystemMessage(Component.literal("·旧世复生魔王: ").withStyle(CustomStyle.styleOfBloodMana).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Devil))));
        player.sendSystemMessage(Component.literal("·尘月宫: ").withStyle(CustomStyle.styleOfMoon).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Moon))));
        player.sendSystemMessage(Component.literal("·新世禁法魔物: ").withStyle(CustomStyle.styleOfBloodMana).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Taboo))));
        player.sendSystemMessage(Component.literal("·暗黑城堡1层: ").withStyle(CustomStyle.styleOfCastle).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.BlackCastle1))));
        player.sendSystemMessage(Component.literal("·暗黑城堡2层: ").withStyle(CustomStyle.styleOfCastleCrystal).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.BlackCastle2))));
        player.sendSystemMessage(Component.literal("·紫晶骑士: ").withStyle(CustomStyle.styleOfPurpleIron).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.PurpleKnight))));
        player.sendSystemMessage(Component.literal(" "));
        player.sendSystemMessage(Component.literal("·VB余额:").withStyle(ChatFormatting.GOLD).append(Component.literal(String.format("%.2f", data.getDouble("VB"))).withStyle(ChatFormatting.WHITE)));
        return 0;
    }
}
