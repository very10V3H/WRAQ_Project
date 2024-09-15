package com.very.wraq.process.func.plan.networking.mission;

import com.very.wraq.common.Compute;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.reputationMission.PlanMissionInfoS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.util.Calendar;
import java.util.function.Supplier;

public class PlanMissionCancelRequestC2SPacket {
    public PlanMissionCancelRequestC2SPacket() {

    }

    public PlanMissionCancelRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            String name = serverPlayer.getName().getString();
            if (PlanMission.planMissionContentMap.containsKey(name)) {
                PlanMission.planMissionContentMap.remove(name);
                PlanMission.planMissionStartTimeMap.remove(name);

                Compute.sendFormatMSG(serverPlayer, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你取消了月卡任务。").withStyle(ChatFormatting.WHITE));

                int punishMinutes = 5;
                Compute.sendFormatMSG(serverPlayer, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("作为取消月卡任务的惩罚，你将在 ").withStyle(ChatFormatting.WHITE).
                                append(Component.literal(punishMinutes + "min ").withStyle(ChatFormatting.RED)).
                                append(Component.literal("后方可接取下一个悬赏任务。").withStyle(ChatFormatting.WHITE)));

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, punishMinutes);
                PlanMission.planMissionAllowRequestTimeMap.put(name, Compute.CalendarToString(calendar));
                ModNetworking.sendToClient(new PlanMissionInfoS2CPacket(Items.AIR.getDefaultInstance(), 0, "", PlanMission.planMissionAllowRequestTimeMap.get(name)), serverPlayer);

            } else {
                Compute.sendFormatMSG(serverPlayer, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("当前没有月卡任务可以取消。").withStyle(ChatFormatting.WHITE));

            }
        });
        return true;
    }
}
