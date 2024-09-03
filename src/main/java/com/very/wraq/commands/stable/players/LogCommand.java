package com.very.wraq.commands.stable.players;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.registry.ModItems;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class LogCommand implements Command<CommandSourceStack> {
    public static LogCommand instance = new LogCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        if (data.contains(StringUtils.Lop.Xp) && !data.contains(StringUtils.LogReward)) {
            Compute.itemStackGive(player, new ItemStack(ModItems.LogBag.get(), data.getInt(StringUtils.Lop.Xp) / 256));
            data.putBoolean(StringUtils.LogReward, true);
        } else {
            Compute.sendFormatMSG(player, Component.literal("补偿").withStyle(CustomStyle.styleOfField),
                    Component.literal(" 似乎已经领取过补偿了呢。").withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
