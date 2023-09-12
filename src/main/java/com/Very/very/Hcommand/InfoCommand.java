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
        if (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online)) {
            player.sendSystemMessage(Component.literal("你的当前基本属性如下:"));
            player.sendSystemMessage(Component.literal("·物理攻击:").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",Compute.PlayerAttackDamage(player))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·攻击距离:").withStyle(Utils.styleOfSea).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f",Compute.PlayerAttackRangeUp(player)+3)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·魔法攻击:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",Compute.PlayerManaDamage(player))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·护甲穿透:").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",Compute.PlayerBreakDefence(player)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))).append(Component.literal("+["+String.format("%.2f",Compute.PlayerBreakDefence0(player))+"]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·魔法穿透:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",Compute.PlayerManaBreakDefence(player)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE))));
            player.sendSystemMessage(Component.literal("·暴击几率:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",Compute.PlayerCriticalHitRate(player)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            player.sendSystemMessage(Component.literal("·暴击伤害:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",Compute.PlayerCriticalHitDamage(player)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            player.sendSystemMessage(Component.literal("·生命偷取:").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",Compute.PlayerHealSteal(player)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            player.sendSystemMessage(Component.literal("·最大法力值:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.valueOf(data.getInt("MAXMANA"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·法力回复:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f",5+Compute.PlayerManaReply(player))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·移动速度:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",Compute.PlayerSpeedImprove(player)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            player.sendSystemMessage(Component.literal("·最大生命值:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f",Compute.MaxHealImprove(player)+20)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·生命回复:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f",Compute.PlayerHealthRecover(player))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·治疗强度:").withStyle(Utils.styleOfHealth).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.0f",Compute.PlayerHealEffectUp(player)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).append(Component.literal("%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·基础护甲:").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f",Compute.PlayerDefence(player))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·冷却缩减:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.0f",Compute.PlayerCoolDownDecrease(player)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).append(Component.literal("%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·经验加成:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",Compute.ExpGetImprove(player)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            player.sendSystemMessage(Component.literal("·任务完成次数:").withStyle(ChatFormatting.GOLD).append(Component.literal(String.valueOf(data.getInt("QuestCounts"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            player.sendSystemMessage(Component.literal("·VB余额:").withStyle(ChatFormatting.GOLD).append(Component.literal(String.format("%.2f",data.getFloat("VB"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        }
        else {
            Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("请先登录。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));

        }
        return 0;
    }
}
