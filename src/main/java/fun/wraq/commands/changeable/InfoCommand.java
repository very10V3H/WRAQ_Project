package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
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
        player.sendSystemMessage(Component.literal("·物理攻击:").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.attackDamage(player))).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·攻击距离:").withStyle(CustomStyle.styleOfSea).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", PlayerAttributes.attackRangeUp(player) + 3)).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·魔法攻击:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.manaDamage(player))).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·护甲穿透:").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.defencePenetration(player) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))).append(Component.literal("+[" + String.format("%.2f", PlayerAttributes.defencePenetration0(player)) + "]").withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·魔法穿透:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.manaPenetration(player) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%").withStyle(ChatFormatting.WHITE))).append(Component.literal("+[" + String.format("%.2f", PlayerAttributes.manaPenetration0(player)) + "]").withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·暴击几率:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.critRate(player) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·暴击伤害:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.critDamage(player) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·生命偷取:").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.healthSteal(player) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·法术吸血:").withStyle(CustomStyle.styleOfBloodMana).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.manaHealthSteal(player) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·最大法力值:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.valueOf(data.getDouble("MAXMANA"))).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·法力回复:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", 5 + PlayerAttributes.manaRecover(player))).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·移动速度:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.movementSpeedWithoutBattle(player) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·额外迅捷:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.extraSwiftness(player))).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·闪避几率:").withStyle(CustomStyle.styleOfFlexible).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f%%", Compute.PlayerDodgeRate(player) * 100)).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·最大生命值:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", PlayerAttributes.maxHealth(player))).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·生命回复:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", PlayerAttributes.healthRecover(player))).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·治疗强度:").withStyle(CustomStyle.styleOfHealth).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.0f", PlayerAttributes.healEffectUp(player) * 100)).withStyle(ChatFormatting.WHITE)).append(Component.literal("%").withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·基础护甲:").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", PlayerAttributes.defence(player))).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·伤害削减:").withStyle(CustomStyle.styleOfStone).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.0f", PlayerAttributes.damageDirectDecrease(player))).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·冷却缩减:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.0f", PlayerAttributes.coolDownDecrease(player) * 100)).withStyle(ChatFormatting.WHITE)).append(Component.literal("%").withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Te.s("·挖掘速度:", ChatFormatting.GRAY, String.format("%.0f%%", PlayerAttributes.getMineSpeed(player) * 100)));
        player.sendSystemMessage(Component.literal("·经验加成:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.expUp(player) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
        player.sendSystemMessage(Component.literal("·额外产出:").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", Compute.playerExHarvest(player) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));

        player.sendSystemMessage(Component.literal(" - 你的归一化元素强度如下:"));
        player.sendSystemMessage(Component.literal("·归一化生机元素强度: ").withStyle(CustomStyle.styleOfLife).
                append(Component.literal(String.format("%.0f%%", ElementValue.PlayerLifeElementValue(player) * 100)).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·归一化碧水元素强度: ").withStyle(CustomStyle.styleOfWater).
                append(Component.literal(String.format("%.0f%%", ElementValue.PlayerWaterElementValue(player) * 100)).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·归一化炽焰元素强度: ").withStyle(CustomStyle.styleOfFire).
                append(Component.literal(String.format("%.0f%%", ElementValue.PlayerFireElementValue(player) * 100)).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·归一化层岩元素强度: ").withStyle(CustomStyle.styleOfStone).
                append(Component.literal(String.format("%.0f%%", ElementValue.PlayerStoneElementValue(player) * 100)).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·归一化凛冰元素强度: ").withStyle(CustomStyle.styleOfIce).
                append(Component.literal(String.format("%.0f%%", ElementValue.PlayerIceElementValue(player) * 100)).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·归一化怒雷元素强度: ").withStyle(CustomStyle.styleOfLightning).
                append(Component.literal(String.format("%.0f%%", ElementValue.PlayerLightningElementValue(player) * 100)).withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("·归一化澄风元素强度: ").withStyle(CustomStyle.styleOfWind).
                append(Component.literal(String.format("%.0f%%", ElementValue.PlayerWindElementValue(player) * 100)).withStyle(ChatFormatting.WHITE)));

        return 0;
    }
}
