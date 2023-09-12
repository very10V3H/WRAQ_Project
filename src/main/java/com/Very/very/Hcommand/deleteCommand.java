package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class deleteCommand implements Command<CommandSourceStack> {
    public static deleteCommand instance = new deleteCommand();
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        if (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online)) {
            ItemStack handItem = player.getMainHandItem();
            Compute.FormatBroad(player.level(),Component.literal("销毁").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                    Component.literal("").append(player.getDisplayName()).
                            append(Component.literal("销毁了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(handItem.getDisplayName()));
            ItemStack itemStack = Items.AIR.getDefaultInstance();
            player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
        }
        else {
            Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("请先登录。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
