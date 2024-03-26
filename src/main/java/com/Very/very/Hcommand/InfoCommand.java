package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.Very.very.ValueAndTools.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class InfoCommand implements Command<CommandSourceStack> {
    public static InfoCommand instance = new InfoCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        player.sendSystemMessage(Component.literal(" - 你的当前基本属性如下:"));
        player.sendSystemMessage(Component.literal("·物理攻击:").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerAttributes.PlayerAttackDamage(player))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·攻击距离:").withStyle(Utils.styleOfSea).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", Compute.PlayerAttributes.PlayerAttackRangeUp(player) + 3)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·魔法攻击:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerAttributes.PlayerManaDamage(player))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·护甲穿透:").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerAttributes.PlayerDefencePenetration(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))).append(Component.literal("+[" + String.format("%.2f", Compute.PlayerAttributes.PlayerDefencePenetration0(player)) + "]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·魔法穿透:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerAttributes.PlayerManaPenetration(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE))).append(Component.literal("+[" + String.format("%.2f", Compute.PlayerAttributes.PlayerManaPenetration0(player)) + "]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·暴击几率:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerAttributes.PlayerCritRate(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·暴击伤害:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerAttributes.PlayerCritDamage(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·生命偷取:").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerAttributes.PlayerHealthSteal(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·法术吸血:").withStyle(Utils.styleOfBloodMana).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerManaHealSteal(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·最大法力值:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.valueOf(data.getInt("MAXMANA"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·法力回复:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", 5 + Compute.PlayerAttributes.PlayerManaRecover(player))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·移动速度:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerAttributes.PlayerMovement(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·额外迅捷:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerAttributes.PlayerExtraSwiftness(player))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·闪避几率:").withStyle(Utils.styleOfFlexible).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f%%", Compute.PlayerDodgeRate(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·最大生命值:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", Compute.PlayerAttributes.PlayerMaxHealth(player) + 200)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·生命回复:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", Compute.PlayerAttributes.PlayerHealthRecover(player))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·治疗强度:").withStyle(Utils.styleOfHealth).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.0f", Compute.PlayerAttributes.PlayerHealEffectUp(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).append(Component.literal("%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·基础护甲:").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", Compute.PlayerAttributes.PlayerDefence(player))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·冷却缩减:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.0f", Compute.PlayerAttributes.PlayerCoolDownDecrease(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).append(Component.literal("%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·经验加成:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerExpUp(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·幸运加成:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.PlayerLuckyUp(player) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal(" "));
        player.sendSystemMessage(Component.literal(" - 你的伤害提升属性如下:"));

        player.sendSystemMessage(Component.literal("·普通伤害提升: ").withStyle(Utils.styleOfMoon).
                append(Component.literal(String.format("%.2f%%",Compute.PlayerCommonDamageUpOrDown(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·最终伤害提升: ").withStyle(ChatFormatting.RED).
                append(Component.literal(String.format("%.2f%%",Compute.PlayerFinalDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·物理伤害提升: ").withStyle(Utils.styleOfPower).
                append(Component.literal(String.format("%.2f%%",Compute.PlayerAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·魔法伤害提升: ").withStyle(Utils.styleOfMana).
                append(Component.literal(String.format("%.2f%%",Compute.PlayerManaDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·近战攻击增幅: ").withStyle(Utils.styleOfVolcano).
                append(Component.literal(String.format("%.2f%%",Compute.PlayerNormalSwordAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·箭矢攻击增幅: ").withStyle(Utils.styleOfFlexible).
                append(Component.literal(String.format("%.2f%%",Compute.PlayerNormalBowAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·法球攻击增幅: ").withStyle(Utils.styleOfMana).
                append(Component.literal(String.format("%.2f%%",Compute.PlayerNormalManaAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));


        player.sendSystemMessage(Component.literal(" - 你的副本挑战情况如下:"));
        player.sendSystemMessage(Component.literal("·普莱尼: ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Plain))));
        player.sendSystemMessage(Component.literal("·四元方块: ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Main1Boss))));
        player.sendSystemMessage(Component.literal("·下界征讨: ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Nether))));
        player.sendSystemMessage(Component.literal("·唤雷岛: ").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Lightning))));
        player.sendSystemMessage(Component.literal("·突见忍: ").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSakura).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.SakuraBoss))));
        player.sendSystemMessage(Component.literal("·冰霜骑士: ").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfIce).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.IceKnight))));
        player.sendSystemMessage(Component.literal("·旧世复生魔王: ").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBloodMana).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Devil))));
        player.sendSystemMessage(Component.literal("·尘月宫: ").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMoon).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Moon))));
        player.sendSystemMessage(Component.literal("·新世禁法魔物: ").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBloodMana).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.Taboo))));
        player.sendSystemMessage(Component.literal("·暗黑城堡1层: ").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfCastle).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.BlackCastle1))));
        player.sendSystemMessage(Component.literal("·暗黑城堡2层: ").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfCastleCrystal).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.BlackCastle2))));
        player.sendSystemMessage(Component.literal("·紫晶骑士: ").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPurpleIron).
                append(Component.literal("" + data.getInt(StringUtils.InstanceTimes.PurpleKnight))));
        player.sendSystemMessage(Component.literal(" "));
        player.sendSystemMessage(Component.literal("·VB余额:").withStyle(ChatFormatting.GOLD).append(Component.literal(String.format("%.2f", data.getDouble("VB"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        return 0;
    }
}
