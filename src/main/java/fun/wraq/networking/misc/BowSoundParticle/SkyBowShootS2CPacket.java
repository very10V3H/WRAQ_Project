package fun.wraq.networking.misc.BowSoundParticle;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkyBowShootS2CPacket {
    private final boolean flag;

    public SkyBowShootS2CPacket(boolean flag) {
        this.flag = flag;
    }

    public SkyBowShootS2CPacket(FriendlyByteBuf buf) {
        this.flag = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.flag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.UseOfSkyBow = flag;
        });
        return true;
    }
}
