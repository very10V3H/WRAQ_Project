package com.Very.very.Hcommand;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.DingS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class DingDingCommand implements Command<CommandSourceStack> {
    public static DingDingCommand instance = new DingDingCommand();
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String name = StringArgumentType.getString(context, "name");
        if (player.getServer().getPlayerList().getPlayerByName(name) != null) {
            if (Utils.DingDingCoolDown.containsKey(player) && Utils.DingDingCoolDown.get(player) > player.getServer().getTickCount()) {
                Compute.FormatMSGSend(player,Component.literal("增强叮!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                        Component.literal("一分钟内只能发送一次增强叮叮叮喔！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
            else {
                ServerPlayer serverPlayer = player.getServer().getPlayerList().getPlayerByName(name);
                CompoundTag data = serverPlayer.getPersistentData();
                Utils.PlayerAFKMap.put(serverPlayer,true);
                data.putDouble("XRot", player.getXRot());
                data.putDouble("YRot", player.getYRot());
                if(!player.isCreative()) Utils.DingDingCoolDown.put(player,player.getServer().getTickCount() + 1200);
                Compute.FormatMSGSend(player,Component.literal("增强叮!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                        Component.literal("你向 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                append(serverPlayer.getDisplayName()).
                                append(Component.literal(" 发送了一个增强叮!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
        }
        else {
            Compute.FormatMSGSend(player,Component.literal("叮!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("玩家似乎没有在线。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }


}
