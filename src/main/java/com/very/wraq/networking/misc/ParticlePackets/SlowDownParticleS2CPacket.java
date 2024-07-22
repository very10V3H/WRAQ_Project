package com.very.wraq.networking.misc.ParticlePackets;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SlowDownParticleS2CPacket {
    private final int Id;
    private final int Time;

    public SlowDownParticleS2CPacket(int Id, int Time) {
        this.Id = Id;
        this.Time = Time;
    }

    public SlowDownParticleS2CPacket(FriendlyByteBuf buf) {
        this.Id = buf.readInt();
        this.Time = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.Id);
        buf.writeInt(this.Time);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.SlowDownParticle.put(Id, Time);
        });
        return true;
    }
}
