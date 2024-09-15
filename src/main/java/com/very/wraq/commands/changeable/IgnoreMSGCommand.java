package com.very.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.StringUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class IgnoreMSGCommand implements Command<CommandSourceStack> {
    public static IgnoreMSGCommand instance = new IgnoreMSGCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        CompoundTag data = player.getPersistentData();
        String string = StringArgumentType.getString(context, "type");
        boolean flag = true;
        if (string.equalsIgnoreCase("Fight")) {
            String Type = StringUtils.IgnoreType.Fight;
            if (!data.contains(Type) || !data.getBoolean(Type)) {
                data.putBoolean(Type, true);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有战斗信息。").withStyle(ChatFormatting.WHITE));

            } else {
                data.putBoolean(Type, false);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收战斗信息。").withStyle(ChatFormatting.WHITE));
            }
            flag = false;
        }
        if (string.equalsIgnoreCase("Rune")) {
            String Type = StringUtils.IgnoreType.Rune;
            if (!data.contains(Type) || !data.getBoolean(Type)) {
                data.putBoolean(Type, true);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有符石信息。").withStyle(ChatFormatting.WHITE));
            } else {
                data.putBoolean(Type, false);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收符石信息。").withStyle(ChatFormatting.WHITE));
            }
            flag = false;
        }
        if (string.equalsIgnoreCase("Exp")) {
            String Type = StringUtils.IgnoreType.Exp;
            if (!data.contains(Type) || !data.getBoolean(Type)) {
                data.putBoolean(Type, true);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有经验信息。").withStyle(ChatFormatting.WHITE));

            } else {
                data.putBoolean(Type, false);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收经验信息。").withStyle(ChatFormatting.WHITE));
            }
            flag = false;
        }
        if (string.equalsIgnoreCase("QuickUse")) {
            String Type = StringUtils.IgnoreType.QuickUse;
            if (!data.contains(Type) || !data.getBoolean(Type)) {
                data.putBoolean(Type, true);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有快捷使用信息。").withStyle(ChatFormatting.WHITE));

            } else {
                data.putBoolean(Type, false);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收快捷使用信息。").withStyle(ChatFormatting.WHITE));
            }
            flag = false;
        }
        if (string.equalsIgnoreCase("ItemGet")) {
            String Type = StringUtils.IgnoreType.ItemGet;
            if (!data.contains(Type) || !data.getBoolean(Type)) {
                data.putBoolean(Type, true);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有物品获取信息。").withStyle(ChatFormatting.WHITE));

            } else {
                data.putBoolean(Type, false);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收物品获取信息。").withStyle(ChatFormatting.WHITE));
            }
            flag = false;
        }
        if (string.equalsIgnoreCase("VB")) {
            String Type = StringUtils.IgnoreType.VB;
            if (!data.contains(Type) || !data.getBoolean(Type)) {
                data.putBoolean(Type, true);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有VB信息。").withStyle(ChatFormatting.WHITE));

            } else {
                data.putBoolean(Type, false);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收VB信息。").withStyle(ChatFormatting.WHITE));
            }
            flag = false;
        }
        if (string.equalsIgnoreCase("Mana")) {
            String Type = StringUtils.IgnoreType.Mana;
            if (!data.contains(Type) || !data.getBoolean(Type)) {
                data.putBoolean(Type, true);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有法力值信息。").withStyle(ChatFormatting.WHITE));

            } else {
                data.putBoolean(Type, false);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收法力值信息。").withStyle(ChatFormatting.WHITE));
            }
            flag = false;
        }
        if (string.equalsIgnoreCase("Instance")) {
            String Type = StringUtils.IgnoreType.Instance;
            if (!data.contains(Type) || !data.getBoolean(Type)) {
                data.putBoolean(Type, true);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你已屏蔽所有副本信息。").withStyle(ChatFormatting.WHITE));

            } else {
                data.putBoolean(Type, false);
                Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                        Component.literal("你重新开始接收副本信息。").withStyle(ChatFormatting.WHITE));
            }
            flag = false;
        }
        if (flag) Compute.sendFormatMSG(player, Component.literal("屏蔽").withStyle(ChatFormatting.RED),
                Component.literal("当前支持的屏蔽类型为战斗(Fight)、符石(Rune)、经验(Exp)、快捷使用(QuickUse)、物品获取(ItemGet)、金钱(VB)、法力值(Mana)、副本(Instance)。").withStyle(ChatFormatting.WHITE));

        return 0;
    }
}
