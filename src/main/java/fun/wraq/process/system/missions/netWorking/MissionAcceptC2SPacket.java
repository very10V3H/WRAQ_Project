package fun.wraq.process.system.missions.netWorking;

import fun.wraq.common.Compute;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.missions.Mission;
import fun.wraq.process.system.missions.netWorking.MissionStatusS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class MissionAcceptC2SPacket {

    private final int serialNum;

    public MissionAcceptC2SPacket(int serialNum) {
        this.serialNum = serialNum;
    }

    public MissionAcceptC2SPacket(FriendlyByteBuf buf) {
        this.serialNum = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(serialNum);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            if (Objects.equals(Mission.getPlayerMissionStatus(player, serialNum), Mission.Status.AbleToAccepted)) {
                Mission.setPlayerMissionStatus(player, serialNum, Mission.Status.InProgress);
                Compute.sendFormatMSG(player, Component.literal("任务").withStyle(CustomStyle.styleOfFlexible),
                        Component.literal("成功接取: ").withStyle(ChatFormatting.GREEN).
                                append(Mission.missionsMap.get(serialNum).title));
            } else {
                Compute.sendFormatMSG(player, Component.literal("任务").withStyle(CustomStyle.styleOfFlexible),
                        Component.literal("似乎还不能接取这个任务").withStyle(ChatFormatting.WHITE));
            }

            ModNetworking.sendToClient(new MissionStatusS2CPacket(
                    Mission.getPlayerMissionStatusString(player)), player);

            ModNetworking.sendToClient(new MissionScreenOpenS2CPacket(2), player);
        });
        return true;
    }

}

