package com.Very.very.Hcommand;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.SoundsPackets.SoundsS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ForgeCommand implements Command<CommandSourceStack> {
    public static ForgeCommand instance = new ForgeCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String second = StringArgumentType.getString(context, "level");
        int level = Integer.parseInt(second);
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        data.putInt(StringUtils.ForgeLevel,level);
        itemStack.resetHoverName();
        player.sendSystemMessage(Component.literal("手上的物品已附加" + level + "强化等级标签。"));
        return 0;
    }
}
