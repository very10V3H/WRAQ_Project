package fun.wraq.process.func.effect;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlindTickS2CPacket {
    private final int blindTick;

    public BlindTickS2CPacket(int blindTick) {
        this.blindTick = blindTick;
    }

    public BlindTickS2CPacket(FriendlyByteBuf buf) {
        this.blindTick = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.blindTick);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SpecialEffectOnPlayer.clientBlindTick = blindTick;
        });
        return true;
    }
}
