package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class InfoInquireCommand implements Command<CommandSourceStack> {
    public static InfoInquireCommand instance = new InfoInquireCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String name = StringArgumentType.getString(context, "name");
        CompoundTag dataP = player.getPersistentData();
        if (!player.isCreative()) {
            Compute.sendFormatMSG(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                    Component.literal("此命令仅管理员可用。").withStyle(ChatFormatting.WHITE));
        } else {

            List<ServerPlayer> playerList = player.getServer().getPlayerList().getPlayers();
            AtomicBoolean flag = new AtomicBoolean(false);
            playerList.forEach(serverPlayer -> {
                if (serverPlayer.getName().getString().equals(name)) {
                    CompoundTag data = serverPlayer.getPersistentData();
                    player.sendSystemMessage(Component.literal("其当前基本属性如下:"));
                    player.sendSystemMessage(Component.literal("·物理攻击:").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.attackDamage(serverPlayer))).withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·攻击距离:").withStyle(CustomStyle.styleOfSea).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", PlayerAttributes.attackRangeUp(serverPlayer) + 3)).withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·魔法攻击:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.manaDamage(serverPlayer))).withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·护甲穿透:").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.defencePenetration(serverPlayer) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))).append(Component.literal("+[" + String.format("%.2f", PlayerAttributes.defencePenetration0(player)) + "]").withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·魔法穿透:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.manaPenetration(serverPlayer) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%").withStyle(ChatFormatting.WHITE))));
                    player.sendSystemMessage(Component.literal("·暴击几率:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.critRate(serverPlayer) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
                    player.sendSystemMessage(Component.literal("·暴击伤害:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.critDamage(serverPlayer) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
                    player.sendSystemMessage(Component.literal("·生命偷取:").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.healthSteal(serverPlayer) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
                    player.sendSystemMessage(Component.literal("·最大法力值:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.valueOf(data.getDouble("MAXMANA"))).withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·法力回复:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", 5 + PlayerAttributes.manaRecover(serverPlayer))).withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·移动速度:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.movementSpeedWithoutBattle(serverPlayer) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
                    player.sendSystemMessage(Component.literal("·最大生命值:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", PlayerAttributes.maxHealth(serverPlayer))).withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·生命回复:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", PlayerAttributes.healthRecover(serverPlayer))).withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·治疗强度:").withStyle(CustomStyle.styleOfHealth).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.0f", PlayerAttributes.getHealEffect(serverPlayer) * 100)).withStyle(ChatFormatting.WHITE)).append(Component.literal("%").withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·基础护甲:").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", PlayerAttributes.defence(serverPlayer))).withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·冷却缩减:").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.0f", PlayerAttributes.coolDownDecrease(serverPlayer) * 100)).withStyle(ChatFormatting.WHITE)).append(Component.literal("%").withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·经验加成:").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f", PlayerAttributes.expUp(serverPlayer) * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
                    player.sendSystemMessage(Component.literal("·任务完成次数:").withStyle(ChatFormatting.GOLD).append(Component.literal(String.valueOf(data.getInt("QuestCounts"))).withStyle(ChatFormatting.WHITE)));
                    player.sendSystemMessage(Component.literal("·VB余额:").withStyle(ChatFormatting.GOLD).append(Component.literal(String.format("%.2f", data.getDouble("VB"))).withStyle(ChatFormatting.WHITE)));
                    flag.set(true);
                }
            });
            if (!flag.get()) {
                player.sendSystemMessage(Component.literal("该玩家未在线。"));
            }

        }
        return 0;
    }
}
