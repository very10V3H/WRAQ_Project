package com.very.wraq.networking.misc.ParticlePackets;

import com.very.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UtilsParticleS2CPacket {
    private final int random;
    private final boolean flag;

    public UtilsParticleS2CPacket(int random, boolean flag) {
        this.random = random;
        this.flag = flag;
    }

    public UtilsParticleS2CPacket(FriendlyByteBuf buf) {
        this.random = buf.readInt();
        this.flag = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.random);
        buf.writeBoolean(this.flag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.ParticleRandom = random;
            ClientUtils.ParticleFlag = flag;
        });
        return true;
    }
}
