package com.very.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.attributeValues.DamageInfluence;
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
                append(Component.literal(String.format("%.2f%%", DamageInfluence.PlayerCommonDamageUpOrDown(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·最终伤害提升: ").withStyle(ChatFormatting.RED).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.PlayerFinalDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·物理伤害提升: ").withStyle(CustomStyle.styleOfPower).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.PlayerAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·魔法伤害提升: ").withStyle(CustomStyle.styleOfMana).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.PlayerManaDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·近战攻击增幅: ").withStyle(CustomStyle.styleOfVolcano).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.PlayerNormalSwordAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·箭矢攻击增幅: ").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.PlayerNormalBowAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·法球攻击增幅: ").withStyle(CustomStyle.styleOfMana).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.PlayerNormalManaAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal(" "));
        player.sendSystemMessage(Component.literal("·VB余额:").withStyle(ChatFormatting.GOLD).append(Component.literal(String.format("%.2f", data.getDouble("VB"))).withStyle(ChatFormatting.WHITE)));
        return 0;
    }
}
