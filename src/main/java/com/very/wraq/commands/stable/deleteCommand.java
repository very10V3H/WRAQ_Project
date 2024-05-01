package com.very.wraq.commands.stable;

import com.very.wraq.files.FileHandler;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
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

import java.io.IOException;

public class deleteCommand implements Command<CommandSourceStack> {
    public static deleteCommand instance = new deleteCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        int TickCount = player.getServer().getTickCount();
        if (Utils.deleteCommandSecurity.containsKey(player) && Utils.deleteCommandSecurity.get(player) > TickCount) {
            ItemStack handItem = player.getMainHandItem();
            Compute.FormatBroad(player.level(), Component.literal("销毁").withStyle(ChatFormatting.RED),
                    Component.literal("").append(player.getDisplayName()).
                            append(Component.literal("销毁了").withStyle(ChatFormatting.WHITE)).
                            append(handItem.getDisplayName()));
            try {
                FileHandler.WRAQLogWrite(player, StringUtils.LogsType.ItemDelete, handItem.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ItemStack itemStack = Items.AIR.getDefaultInstance();
            player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);

        } else {
            Utils.deleteCommandSecurity.put(player, TickCount + 60);
            Compute.FormatMSGSend(player, Component.literal("销毁").withStyle(ChatFormatting.RED),
                    Component.literal("请在3s内再次输入指令以进行销毁。").withStyle(ChatFormatting.WHITE));
        }

        return 0;
    }
}
