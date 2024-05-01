package com.very.wraq.netWorking.reputationMission;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
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

public class ReputationMissionRequestC2SPacket {
    public ReputationMissionRequestC2SPacket()
    {

    }
    public ReputationMissionRequestC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( ()->{
            ServerPlayer serverPlayer = context.getSender();
            if (!Utils.playerReputationMissionContent.containsKey(serverPlayer.getName().getString())) {
                boolean allowToRequest = true;
                Calendar calendar = Calendar.getInstance();
                if (Utils.playerReputationMissionAllowRequestTime.containsKey(serverPlayer.getName().getString())) {
                    Calendar allowTime = null;
                    try {
                        allowTime = Compute.StringToCalendar( Utils.playerReputationMissionAllowRequestTime.get(serverPlayer.getName().getString()));
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
                        int Boundary;
                        if (serverPlayer.experienceLevel < 30) Boundary = 4;
                        else if (serverPlayer.experienceLevel < 50) Boundary = 11;
                        else if (serverPlayer.experienceLevel < 80) Boundary = 15;
                        else if (serverPlayer.experienceLevel < 120) Boundary = 19;
                        else Boundary = 23;
                        int ChooseItem = random.nextInt(0,Boundary + 1);
                        if (Utils.MissionItemList.isEmpty()) Utils.setMissionItemList();
                        ItemStack itemStack = Utils.MissionItemList.get(ChooseItem);
                        Utils.playerReputationMissionContent.put(serverPlayer.getName().getString(),itemStack);
                        Utils.playerReputationMissionContentNum.put(serverPlayer.getName().getString(),random.nextInt(64, (int) Math.max(64,serverPlayer.experienceLevel)));
                        Utils.playerReputationMissionStartTime.put(serverPlayer.getName().getString(),Compute.CalendarToString(Calendar.getInstance()));
                        ModNetworking.sendToClient(new ReputationMissionStartTimeS2CPacket(Utils.playerReputationMissionStartTime.get(serverPlayer.getName().getString())),serverPlayer);
                        ModNetworking.sendToClient(new ReputationMissionContentS2CPacket(
                                Utils.playerReputationMissionContent.get(serverPlayer.getName().getString()),
                                Utils.playerReputationMissionContentNum.get(serverPlayer.getName().getString())),serverPlayer);
                        ModNetworking.sendToClient(new SoundsS2CPacket(6),serverPlayer);
                        Compute.FormatMSGSend(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                                Component.literal("你成功接取了").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(" 悬赏任务 !").withStyle(ChatFormatting.GOLD)));

                    }
                    else {
                        Compute.FormatMSGSend(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                                Component.literal("悬赏任务需要达到20级才能开始接取。").withStyle(ChatFormatting.WHITE));
                    }
                }
                else {
                    Compute.FormatMSGSend(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                            Component.literal("悬赏任务惩罚尚未结束。").withStyle(ChatFormatting.WHITE));

                }
            }
            else {
                Compute.FormatMSGSend(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("你已经接取悬赏任务了！").withStyle(ChatFormatting.WHITE));
            }
        });
        return true;
    }
}
