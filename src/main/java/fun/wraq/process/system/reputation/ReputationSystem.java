package fun.wraq.process.system.reputation;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.StringUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.reputation.ReputationValueS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ReputationSystem {
    public static int getPlayerReputation(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getInt(StringUtils.Reputation);
    }

    public static boolean addOrCostReputation(Player player, int num) {
        CompoundTag data = player.getPersistentData();
        ChatFormatting chatFormatting = ChatFormatting.GREEN;
        if (num < 0) {
            if (getPlayerReputation(player) + num < 0) {
                Compute.sendFormatMSG(player, Component.literal("声望").withStyle(ChatFormatting.YELLOW),
                        Component.literal("当前声望不足。").withStyle(ChatFormatting.WHITE));
                return false;
            }
            chatFormatting = ChatFormatting.RED;
        }
        data.putInt(StringUtils.Reputation, data.getInt(StringUtils.Reputation) + num);
        Compute.sendFormatMSG(player, Component.literal("声望").withStyle(ChatFormatting.YELLOW),
                Component.literal("你的声望值:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("" + getPlayerReputation(player)).withStyle(ChatFormatting.YELLOW)).
                        append(Component.literal(" (" + num + ")").withStyle(chatFormatting)));
        ModNetworking.sendToClient(new ReputationValueS2CPacket(data.getInt(StringUtils.Reputation)), (ServerPlayer) player);
        return true;
    }

    public static void giveReputation(Player player, int reputation, Component type) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Reputation, data.getInt(StringUtils.Reputation) + reputation);
        data.putInt(StringUtils.ReputationCalculate, data.getInt(StringUtils.ReputationCalculate) + reputation);
        Compute.sendFormatMSG(player, Te.s("声望", ChatFormatting.YELLOW),
                Compute.getValueIncreaseMSG(String.valueOf(reputation), ChatFormatting.YELLOW,
                        String.valueOf(data.getInt(StringUtils.Reputation)), CustomStyle.styleOfStone, type));
    }

    public static void giveReputation(Player player, double reputation, Component type) {
        giveReputation(player, (int) reputation, type);
    }
}
