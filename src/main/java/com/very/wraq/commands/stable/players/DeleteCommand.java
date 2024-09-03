package com.very.wraq.commands.stable.players;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DeleteCommand implements Command<CommandSourceStack> {
    public static DeleteCommand instance = new DeleteCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        int TickCount = player.getServer().getTickCount();
        String name = player.getName().getString();
        if (Utils.deleteCommandSecurity.containsKey(name) && Utils.deleteCommandSecurity.get(name) > TickCount) {
            ItemStack handItem = player.getMainHandItem();
            Compute.formatBroad(player.level(), Component.literal("销毁").withStyle(ChatFormatting.RED),
                    Component.literal("").append(player.getDisplayName()).
                            append(Component.literal("销毁了").withStyle(ChatFormatting.WHITE)).
                            append(handItem.getDisplayName()));
            ItemStack itemStack = Items.AIR.getDefaultInstance();
            player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);

        } else {
            Utils.deleteCommandSecurity.put(name, TickCount + 60);
            Compute.sendFormatMSG(player, Component.literal("销毁").withStyle(ChatFormatting.RED),
                    Component.literal("请在3s内再次输入指令以进行销毁。").withStyle(ChatFormatting.WHITE));
        }

        return 0;
    }
}
