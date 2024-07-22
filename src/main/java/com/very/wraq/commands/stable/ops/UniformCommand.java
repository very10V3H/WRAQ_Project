package com.very.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UniformCommand implements Command<CommandSourceStack> {
    public static UniformCommand instance = new UniformCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayer();
        List<ServerPlayer> playerList = serverPlayer.getServer().getPlayerList().getPlayers();
        serverPlayer.sendSystemMessage(Component.literal("start check.."));
        playerList.forEach(player -> {
            AtomicInteger num = new AtomicInteger();
            CuriosApi.getCuriosInventory(player).ifPresent(inventory -> {
                for (int i = 0; i < inventory.getEquippedCurios().getSlots(); i++) {
                    ItemStack curios = inventory.getEquippedCurios().getStackInSlot(i);
                    if (Utils.uniformList.contains(curios.getItem())) num.getAndIncrement();
                }
            });
            if (num.get() != 0) {
                serverPlayer.sendSystemMessage(Component.literal("").withStyle(ChatFormatting.WHITE).
                        append(player.getDisplayName()).
                        append(Component.literal(" - " + num.get()).withStyle(ChatFormatting.WHITE)));
            }
        });
        serverPlayer.sendSystemMessage(Component.literal("check finished!"));
        return 0;
    }
}
