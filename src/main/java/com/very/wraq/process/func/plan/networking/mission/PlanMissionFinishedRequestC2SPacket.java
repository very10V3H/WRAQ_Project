package com.very.wraq.process.func.plan.networking.mission;

import com.very.wraq.common.registry.MySound;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.reputationMission.PlanMissionInfoS2CPacket;
import com.very.wraq.common.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.text.ParseException;
import java.util.Calendar;
import java.util.function.Supplier;

public class PlanMissionFinishedRequestC2SPacket {
    public PlanMissionFinishedRequestC2SPacket() {

    }

    public PlanMissionFinishedRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();

            if (!PlanMission.planMissionContentMap.containsKey(serverPlayer.getName().getString())) {
                Compute.sendFormatMSG(serverPlayer, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("当前没有月卡任务可以提交。").withStyle(ChatFormatting.WHITE));
                return;
            }

            ItemStack itemStack = PlanMission.planMissionContentMap.get(serverPlayer.getName().getString());
            int count = PlanMission.planMissionContentCountMap.get(serverPlayer.getName().getString());

            Inventory inventory = serverPlayer.getInventory();
            if (Compute.ItemStackCheck(inventory, itemStack.getItem(), count)) {
                Compute.itemStackRemove(inventory, itemStack.getItem(), count);
                Compute.sendFormatMSG(serverPlayer, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你完成了月卡任务！").withStyle(ChatFormatting.WHITE));

                Calendar currentTime = Calendar.getInstance();
                Calendar missionStartTime;
                String name = serverPlayer.getName().getString();
                try {
                    missionStartTime = Compute.StringToCalendar(PlanMission.planMissionStartTimeMap.get(name));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                long timeDelta = currentTime.getTimeInMillis() - missionStartTime.getTimeInMillis();
                long minuteDelta = timeDelta / (1000 * 60);
                if (minuteDelta >= 75) minuteDelta = 75;
                int tier = 5 - (int) (minuteDelta / 15);
                Compute.givePercentExpToPlayer(serverPlayer, 0.04 * tier, 0, serverPlayer.experienceLevel);
                Compute.playerReputationAddOrCost(serverPlayer, tier * (serverPlayer.experienceLevel / 20));
                if (serverPlayer.experienceLevel == 220) Compute.playerReputationAddOrCost(serverPlayer, tier);

                PlanMission.planMissionContentMap.remove(name);
                ModNetworking.sendToClient(new PlanMissionInfoS2CPacket(Items.AIR.getDefaultInstance(), 0,
                        PlanMission.planMissionStartTimeMap.getOrDefault(name, ""),
                        PlanMission.planMissionAllowRequestTimeMap.getOrDefault(name, "")), serverPlayer);
                PlanMission.planMissionStartTimeMap.remove(name);

                String[] level = {
                        "普通", "优秀", "精良", "史诗", "传说", "神话"
                };

                ChatFormatting[] chatFormattings = {
                        ChatFormatting.GRAY,
                        ChatFormatting.GREEN,
                        ChatFormatting.AQUA,
                        ChatFormatting.LIGHT_PURPLE,
                        ChatFormatting.GOLD,
                        ChatFormatting.RED,
                };

                Compute.formatBroad(serverPlayer.level(), Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(serverPlayer.getDisplayName()).
                                append(Component.literal("在 ").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(minuteDelta + "min").withStyle(chatFormattings[tier])).
                                append(Component.literal(" 内完成了月卡任务，获得了: ").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(level[tier]).withStyle(chatFormattings[tier])).
                                append(Component.literal(" 评级！").withStyle(ChatFormatting.WHITE)));

                PlanMission.planMissionAllowRequestTimeMap.remove(serverPlayer.getName().getString());

                MySound.SoundToAll(serverPlayer, SoundEvents.PLAYER_LEVELUP);
            } else {
                Compute.sendFormatMSG(serverPlayer, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("暂未达成任务要求。").withStyle(ChatFormatting.WHITE));
            }
        });
        return true;
    }
}
