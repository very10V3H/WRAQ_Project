package fun.wraq.networking.reputationMission;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
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
    public ReputationMissionRequestC2SPacket() {

    }

    public ReputationMissionRequestC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();

            if (!Utils.playerReputationMissionContent.containsKey(serverPlayer.getName().getString())) {
                boolean allowToRequest = true;
                Calendar calendar = Calendar.getInstance();
                if (Utils.playerReputationMissionAllowRequestTime.containsKey(serverPlayer.getName().getString())) {
                    Calendar allowTime;
                    try {
                        allowTime = Compute.StringToCalendar(Utils.playerReputationMissionAllowRequestTime.get(serverPlayer.getName().getString()));
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
                        int boundary = Utils.getMissionItemBoundary(serverPlayer.experienceLevel);
                        int chooseIndex = random.nextInt(0, boundary + 1);
                        if (Utils.missionItemList.isEmpty()) Utils.setMissionItemList();
                        ItemStack chooseItemStack = Utils.missionItemList.get(chooseIndex).getDefaultInstance();
                        ItemStack itemStack = chooseItemStack.copy();
                        Utils.playerReputationMissionContent.put(serverPlayer.getName().getString(), itemStack);
                        int count = random.nextInt(64, Math.max(65, serverPlayer.experienceLevel));
                        if (itemStack.is(ModItems.SPIDER_SOUL.get())) count = (int) (count * 0.1);
                        Utils.playerReputationMissionContentNum.put(serverPlayer.getName().getString(), count);
                        Utils.playerReputationMissionStartTime.put(serverPlayer.getName().getString(), Compute.CalendarToString(Calendar.getInstance()));
                        ModNetworking.sendToClient(new ReputationMissionStartTimeS2CPacket(Utils.playerReputationMissionStartTime.get(serverPlayer.getName().getString())), serverPlayer);
                        ModNetworking.sendToClient(new ReputationMissionContentS2CPacket(
                                Utils.playerReputationMissionContent.get(serverPlayer.getName().getString()),
                                Utils.playerReputationMissionContentNum.get(serverPlayer.getName().getString())), serverPlayer);
                        ModNetworking.sendToClient(new SoundsS2CPacket(6), serverPlayer);
                        Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                                Component.literal("你成功接取了").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(" 悬赏任务 !").withStyle(ChatFormatting.GOLD)));
                    } else {
                        Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                                Component.literal("悬赏任务需要达到20级才能开始接取。").withStyle(ChatFormatting.WHITE));
                    }
                } else {
                    Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                            Component.literal("悬赏任务惩罚尚未结束。").withStyle(ChatFormatting.WHITE));
                }
            } else {
                Compute.sendFormatMSG(serverPlayer, Component.literal("任务").withStyle(CustomStyle.styleOfKaze),
                        Component.literal("你已经接取悬赏任务了！").withStyle(ChatFormatting.WHITE));
            }
        });
        return true;
    }
}
