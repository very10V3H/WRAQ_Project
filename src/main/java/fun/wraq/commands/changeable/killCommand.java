package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.events.mob.MobSpawn;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class killCommand implements Command<CommandSourceStack> {
    public static killCommand instance = new killCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String playerName = player.getName().getString();
        if (MobSpawn.totalKillCount.containsKey(playerName)) {
            AtomicInteger total = new AtomicInteger();
            Compute.sendFormatMSG(player, Component.literal("击杀数").withStyle(ChatFormatting.RED),
                    Component.literal("击杀数详情如下所示").withStyle(ChatFormatting.WHITE));
            Map<String, Integer> killCount = MobSpawn.totalKillCount.get(playerName);
            killCount.forEach((k, v) -> {
                player.sendSystemMessage(Component.literal(" " + k + " : ").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("" + v).withStyle(ChatFormatting.AQUA)));
                total.addAndGet(v);
            });
            player.sendSystemMessage(Component.literal(" " + "总击杀数" + " : ").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("" + total).withStyle(ChatFormatting.AQUA)));
        } else {
            Compute.sendFormatMSG(player, Component.literal("击杀数").withStyle(ChatFormatting.RED),
                    Component.literal("没有查询到相关信息，或许可以等一会再试试").withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
