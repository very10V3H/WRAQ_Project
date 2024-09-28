package fun.wraq.networking.dailyMission;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.dailyMission.DailyMissionContentS2CPacket;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
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
    public DailyMissionRequestC2SPacket() {

    }

    public DailyMissionRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
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

                    allowToRequestTime.add(Calendar.HOUR_OF_DAY, 22);
                    if (allowToRequestTime.before(Calendar.getInstance())) {
                        Random random = new Random();
                        int boundary = Utils.getMissionItemBoundary(serverPlayer.experienceLevel);
                        int chooseIndex = random.nextInt(0, boundary + 1);
                        if (Utils.missionItemList.isEmpty()) Utils.setMissionItemList();
                        ItemStack chooseItemStack = Utils.missionItemList.get(chooseIndex).getDefaultInstance();
                        ItemStack itemStack = chooseItemStack.copy();
                        Utils.playerDailyMissionContent.put(serverPlayer.getName().getString(), itemStack);
                        int count = random.nextInt(64, Math.max(65, serverPlayer.experienceLevel));
                        if (itemStack.is(ModItems.SpiderSoul.get())) count = (int) (count * 0.1);
                        Utils.playerDailyMissionContentNum.put(serverPlayer.getName().getString(), count);
                        ModNetworking.sendToClient(new DailyMissionContentS2CPacket(
                                Utils.playerDailyMissionContent.get(serverPlayer.getName().getString()),
                                Utils.playerDailyMissionContentNum.get(serverPlayer.getName().getString())), serverPlayer);
                        ModNetworking.sendToClient(new SoundsS2CPacket(6), serverPlayer);
                        Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                                Component.literal("你成功接取了").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(" 每日任务 !").withStyle(CustomStyle.styleOfKaze)));
                    } else {
                        Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                                Component.literal("还未到达可以接取每日任务的时间。").withStyle(ChatFormatting.WHITE));
                    }
                } else {
                    Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                            Component.literal("每日任务需要达到20级才能开始接取。").withStyle(ChatFormatting.WHITE));
                }
            } else {
                Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("你已经接取每日任务了！").withStyle(ChatFormatting.WHITE));

            }
        });
        return true;
    }
}
