package com.very.wraq.process.system.missions.netWorking;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MissionScreenOpenS2CPacket {

    private final int type;

    public MissionScreenOpenS2CPacket(int type) {
        this.type = type;
    }

    public MissionScreenOpenS2CPacket(FriendlyByteBuf buf) {
        this.type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            ClientUtils.missionScreenFlag = type;
        });
        return true;
    }

}

