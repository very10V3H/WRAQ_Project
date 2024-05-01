package com.very.wraq.netWorking.dailyMission;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Random;
import java.util.function.Supplier;

public class DailyMissionRequestC2SPacket {
    public DailyMissionRequestC2SPacket()
    {

    }
    public DailyMissionRequestC2SPacket(FriendlyByteBuf buf)
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
            if (!Utils.playerDailyMissionContent.containsKey(serverPlayer.getName().getString())) {
                if (serverPlayer.experienceLevel >= 20) {
                    CompoundTag data = serverPlayer.getPersistentData();
                    Calendar allowToRequestTime;
                    try {
                        allowToRequestTime = Compute.StringToCalendar(data.getString(StringUtils.LastDailyMissionFinishedTime));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    allowToRequestTime.add(Calendar.HOUR_OF_DAY,22);
                    if (allowToRequestTime.before(Calendar.getInstance())) {
                        Random random = new Random();
                        int Boundary;
                        if (serverPlayer.experienceLevel < 30) Boundary = 4;
                        else if (serverPlayer.experienceLevel < 50) Boundary = 11;
                        else if (serverPlayer.experienceLevel < 70) Boundary = 15;
                        else Boundary = 18;
                        int ChooseItem = random.nextInt(0,Boundary + 1);
                        if (Utils.MissionItemList.isEmpty()) Utils.setMissionItemList();
                        ItemStack itemStack = Utils.MissionItemList.get(ChooseItem);
                        Utils.playerDailyMissionContent.put(serverPlayer.getName().getString(),itemStack);
                        Utils.playerDailyMissionContentNum.put(serverPlayer.getName().getString(),random.nextInt(40, (int) Math.max(41,serverPlayer.experienceLevel * 2.5)));
                        ModNetworking.sendToClient(new DailyMissionContentS2CPacket(
                                Utils.playerDailyMissionContent.get(serverPlayer.getName().getString()),
                                Utils.playerDailyMissionContentNum.get(serverPlayer.getName().getString())),serverPlayer);
                        ModNetworking.sendToClient(new SoundsS2CPacket(6),serverPlayer);
                        Compute.FormatMSGSend(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                                Component.literal("你成功接取了").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(" 每日任务 !").withStyle(CustomStyle.styleOfKaze)));
                    }
                    else {
                        Compute.FormatMSGSend(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                                Component.literal("还未到达可以接取每日任务的时间。").withStyle(ChatFormatting.WHITE));
                    }
                }
                else {
                    Compute.FormatMSGSend(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                            Component.literal("每日任务需要达到20级才能开始接取。").withStyle(ChatFormatting.WHITE));
                }
            }
            else {
                Compute.FormatMSGSend(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("你已经接取每日任务了！").withStyle(ChatFormatting.WHITE));

            }
        });
        return true;
    }
}
