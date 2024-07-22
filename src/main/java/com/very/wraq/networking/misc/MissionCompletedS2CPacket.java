package com.very.wraq.networking.misc;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MissionCompletedS2CPacket {
    private final String Title;

    public MissionCompletedS2CPacket(String title) {
        Title = title;
    }

    public MissionCompletedS2CPacket(FriendlyByteBuf buf) {
        Title = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(Title);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.oldMissionList.removeIf(mission -> {
                return mission.getTitle().equals(Title);
            });
            ClientUtils.MissionIndex = 0;
            ClientUtils.MissionChange = false;
            ClientUtils.ListIndex = 0;
            ClientUtils.NavigateIndex = -1;

        });
        return true;
    }
}
