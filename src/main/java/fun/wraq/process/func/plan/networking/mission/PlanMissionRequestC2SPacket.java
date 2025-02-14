package fun.wraq.process.func.plan.networking.mission;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.networking.reputationMission.PlanMissionInfoS2CPacket;
import fun.wraq.process.func.plan.PlanPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Random;
import java.util.function.Supplier;

public class PlanMissionRequestC2SPacket {
    public PlanMissionRequestC2SPacket() {

    }

    public PlanMissionRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();

            if (!fun.wraq.process.func.plan.networking.mission.PlanMission.planMissionContentMap.containsKey(serverPlayer.getName().getString())) {
                boolean allowToRequest = PlanPlayer.getPlayerTier(serverPlayer) != 0;
                if (!allowToRequest) {
                    Compute.sendFormatMSG(serverPlayer, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("当前没有接取月卡任务的权限。").withStyle(ChatFormatting.WHITE));
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                if (fun.wraq.process.func.plan.networking.mission.PlanMission.planMissionAllowRequestTimeMap.containsKey(serverPlayer.getName().getString())) {
                    Calendar allowTime;
                    try {
                        allowTime = Compute.StringToCalendar(fun.wraq.process.func.plan.networking.mission.PlanMission.planMissionAllowRequestTimeMap.get(serverPlayer.getName().getString()));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    if (calendar.before(allowTime)) {
                        allowToRequest = false;
                    }
                }
                if (allowToRequest) {
                    if (serverPlayer.experienceLevel >= 20) {
                        Random random = new Random();
                        String name = serverPlayer.getName().getString();
                        int boundary = Utils.getMissionItemBoundary(serverPlayer.experienceLevel);
                        int chooseIndex = random.nextInt(0, boundary + 1);
                        if (Utils.missionItemList.isEmpty()) Utils.setMissionItemList();
                        ItemStack chooseItemStack = Utils.missionItemList.get(chooseIndex).getDefaultInstance();
                        ItemStack itemStack = chooseItemStack.copy();

                        fun.wraq.process.func.plan.networking.mission.PlanMission.planMissionContentMap.put(name, itemStack);
                        int count = random.nextInt(64, Math.max(65, serverPlayer.experienceLevel)) / 2;
                        if (itemStack.is(ModItems.SpiderSoul.get())) count = (int) (count * 0.1);
                        fun.wraq.process.func.plan.networking.mission.PlanMission.planMissionContentCountMap.put(name, count);

                        fun.wraq.process.func.plan.networking.mission.PlanMission.planMissionStartTimeMap.put(name, Compute.CalendarToString(Calendar.getInstance()));
                        ModNetworking.sendToClient(new PlanMissionInfoS2CPacket(fun.wraq.process.func.plan.networking.mission.PlanMission.planMissionContentMap.get(name),
                                fun.wraq.process.func.plan.networking.mission.PlanMission.planMissionContentCountMap.get(name),
                                fun.wraq.process.func.plan.networking.mission.PlanMission.planMissionStartTimeMap.get(name),
                                PlanMission.planMissionAllowRequestTimeMap.getOrDefault(name, "")), serverPlayer);

                        ModNetworking.sendToClient(new SoundsS2CPacket(6), serverPlayer);
                        Compute.sendFormatMSG(serverPlayer, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                                Component.literal("你成功接取了").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(" 月卡任务 !").withStyle(ChatFormatting.LIGHT_PURPLE)));
                    } else {
                        Compute.sendFormatMSG(serverPlayer, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                                Component.literal("悬赏任务需要达到20级才能开始接取。").withStyle(ChatFormatting.WHITE));
                    }
                } else {
                    Compute.sendFormatMSG(serverPlayer, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("悬赏任务惩罚尚未结束。").withStyle(ChatFormatting.WHITE));
                }
            } else {
                Compute.sendFormatMSG(serverPlayer, Component.literal("月卡任务").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你已经接取悬赏任务了！").withStyle(ChatFormatting.WHITE));
            }
        });
        return true;
    }
}
