package com.very.wraq.networking.reputationMission;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.util.Calendar;
import java.util.function.Supplier;

public class ReputationMissionCancelRequestC2SPacket {
    public ReputationMissionCancelRequestC2SPacket() {

    }

    public ReputationMissionCancelRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            CompoundTag data = serverPlayer.getPersistentData();
            if (Utils.playerReputationMissionContent.containsKey(serverPlayer.getName().getString())) {
                Utils.playerReputationMissionContent.remove(serverPlayer.getName().getString());
                Utils.playerReputationMissionStartTime.remove(serverPlayer.getName().getString());
                if (!Utils.playerReputationMissionPunishLevel.containsKey(serverPlayer.getName().getString())) {
                    Utils.playerReputationMissionPunishLevel.put(serverPlayer.getName().getString(), 1);
                } else Utils.playerReputationMissionPunishLevel.put(serverPlayer.getName().getString(),
                        Math.min(9, Utils.playerReputationMissionPunishLevel.get(serverPlayer.getName().getString()) + 1));
                Compute.formatMSGSend(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("你取消了悬赏任务。").withStyle(ChatFormatting.WHITE));
                int punishMinutes = Utils.playerReputationMissionPunishTime[Utils.playerReputationMissionPunishLevel.get(serverPlayer.getName().getString())];
                Compute.formatMSGSend(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("作为取消任务的惩罚，你将在 ").withStyle(ChatFormatting.WHITE).
                                append(Component.literal(punishMinutes + "min ").withStyle(ChatFormatting.RED)).
                                append(Component.literal("后方可接取下一个悬赏任务。").withStyle(ChatFormatting.WHITE)));
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, punishMinutes);
                Utils.playerReputationMissionAllowRequestTime.put(serverPlayer.getName().getString(), Compute.CalendarToString(calendar));
                ModNetworking.sendToClient(new ReputationMissionAllowRequestTimeS2CPacket(Utils.playerReputationMissionAllowRequestTime.get(serverPlayer.getName().getString())), serverPlayer);
                ModNetworking.sendToClient(new ReputationMissionStartTimeS2CPacket(""), serverPlayer);
                ModNetworking.sendToClient(new ReputationMissionContentS2CPacket(Items.AIR.getDefaultInstance(), 0), serverPlayer);

            } else {
                Compute.formatMSGSend(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("当前没有悬赏任务可以取消。").withStyle(ChatFormatting.WHITE));

            }
        });
        return true;
    }
}
