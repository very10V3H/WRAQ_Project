package com.very.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.commands.changeable.PrefixCommand;
import com.very.wraq.common.Compute;
import com.very.wraq.process.func.plan.PlanPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CustomPrefixCommand implements Command<CommandSourceStack> {
    public static CustomPrefixCommand instance = new CustomPrefixCommand();

    public static String customPrefixTimes = "customPrefixTimes";

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayer();
        CompoundTag data = serverPlayer.getPersistentData();
        String prefixString = StringArgumentType.getString(context, "prefix");
        String colorString = StringArgumentType.getString(context, "color");

        int tier = 0;
        try {
            tier = PlanPlayer.getPlayerTier(serverPlayer);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (tier == 0) {
            Compute.sendFormatMSG(serverPlayer, Component.literal("自定义称号").withStyle(ChatFormatting.GRAY),
                    Component.literal("当前无法使用自定义称号").withStyle(ChatFormatting.WHITE));
            return 0;
        } else if (tier != 3) {
            int times = data.getInt(customPrefixTimes);
            if (times <= 0) {
                Compute.sendFormatMSG(serverPlayer, Component.literal("自定义称号").withStyle(ChatFormatting.GRAY),
                        Component.literal("自定义称号的次数已经使用完了").withStyle(ChatFormatting.WHITE));
                return 0;
            }
        }

        if (prefixString.length() > 8) {
            Compute.sendFormatMSG(serverPlayer, Component.literal("自定义称号").withStyle(ChatFormatting.GRAY),
                    Component.literal("称号名称的最大长度为8").withStyle(ChatFormatting.WHITE));
            return 0;
        }

        // 使用预置颜色
        if (!colorString.startsWith("#")) {
            Map<String, String> colorStringMap = new HashMap<>() {{
                for (ChatFormatting value : ChatFormatting.values()) {
                    put(value.getName().toLowerCase(), String.valueOf(Style.EMPTY.applyFormat(value).getColor()));
                }
            }};
            if (colorStringMap.containsKey(colorString)) colorString = colorStringMap.get(colorString);
            else {
                Compute.sendFormatMSG(serverPlayer, Component.literal("自定义称号").withStyle(ChatFormatting.GRAY),
                        Component.literal("以下是一些可用的预置颜色").withStyle(ChatFormatting.WHITE));
                for (ChatFormatting value : ChatFormatting.values()) {
                    serverPlayer.sendSystemMessage(Component.literal(value.getName().toLowerCase()).withStyle(value));
                }
                return 0;
            }
        } else {
            if (TextColor.parseColor(colorString) == null) {
                Compute.sendFormatMSG(serverPlayer, Component.literal("自定义称号").withStyle(ChatFormatting.GRAY),
                        Component.literal("需要自定义称号颜色，请使用形如 #92ecfa 的RGB十六进制编码").withStyle(ChatFormatting.WHITE));
                return 0;
            }
        }

        data.putString(PrefixCommand.prefix, prefixString);
        data.putString(PrefixCommand.prefixColor, colorString);
        Compute.sendFormatMSG(serverPlayer, Component.literal("自定义称号").withStyle(ChatFormatting.GRAY),
                Component.literal(prefixString).withStyle(Style.EMPTY.withColor(TextColor.parseColor(colorString))).
                        append(Component.literal(" 已成功激活！").withStyle(ChatFormatting.WHITE)));
        if (tier != 3) {
            data.putInt(customPrefixTimes, data.getInt(customPrefixTimes) - 1);
            Compute.sendFormatMSG(serverPlayer, Component.literal("自定义称号").withStyle(ChatFormatting.GRAY),
                    Component.literal("剩余自定义称号可用次数：").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.valueOf(data.getInt(customPrefixTimes))).withStyle(ChatFormatting.AQUA)));
        }
        return 0;
    }

}
