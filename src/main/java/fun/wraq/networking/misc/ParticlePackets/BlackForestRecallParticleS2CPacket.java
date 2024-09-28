package fun.wraq.networking.misc.ParticlePackets;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlackForestRecallParticleS2CPacket {
    private final boolean flag;

    public BlackForestRecallParticleS2CPacket(boolean flag) {
        this.flag = flag;
    }

    public BlackForestRecallParticleS2CPacket(FriendlyByteBuf buf) {
        this.flag = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.flag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.BlackForestParticle = true;
        });
        return true;
    }
}
