package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.attribute.DamageInfluence;
import fun.wraq.render.toolTip.CustomStyle;
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
                append(Component.literal(String.format("%.2f%%", DamageInfluence.getPlayerCommonDamageUpOrDown(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·最终伤害提升: ").withStyle(ChatFormatting.RED).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.getPlayerFinalDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·物理伤害提升: ").withStyle(CustomStyle.styleOfPower).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.getPlayerAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·魔法伤害提升: ").withStyle(CustomStyle.styleOfMana).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.getPlayerManaDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·近战攻击增幅: ").withStyle(CustomStyle.styleOfVolcano).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.getPlayerNormalSwordAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·箭矢攻击增幅: ").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.getPlayerNormalBowAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal("·法球攻击增幅: ").withStyle(CustomStyle.styleOfMana).
                append(Component.literal(String.format("%.2f%%", DamageInfluence.getPlayerNormalManaAttackDamageEnhance(player) * 100)).withStyle(ChatFormatting.WHITE)));

        player.sendSystemMessage(Component.literal(" "));
        player.sendSystemMessage(Component.literal("·VB余额:").withStyle(ChatFormatting.GOLD).append(Component.literal(String.format("%.2f", data.getDouble("VB"))).withStyle(ChatFormatting.WHITE)));
        return 0;
    }
}
