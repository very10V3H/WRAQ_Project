package com.very.wraq.commands.stable.players;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class SpeedCommand implements Command<CommandSourceStack> {
    public static SpeedCommand instance = new SpeedCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        String s = StringArgumentType.getString(context, "rate");
        int rate = Integer.parseInt(s);
        if (rate >= 0 && rate <= 100) {
            data.putInt(StringUtils.MovementSpeedRate, rate);
            Compute.sendFormatMSG(player, Component.literal("移速调整").withStyle(CustomStyle.styleOfFlexible),
                    Component.literal("已将移动速度加成的效果调整为: ").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.format("%d%%", rate)).withStyle(CustomStyle.styleOfFlexible)));
        } else {
            Compute.sendFormatMSG(player, Component.literal("移速调整").withStyle(CustomStyle.styleOfFlexible),
                    Component.literal("移速调整的参数范围为: ").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("0 ~ 100").withStyle(CustomStyle.styleOfFlexible)).
                            append(Component.literal("（整数）").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("，对应").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("0% ~ 100%效能").withStyle(CustomStyle.styleOfFlexible)));
        }
        return 0;
    }
}
