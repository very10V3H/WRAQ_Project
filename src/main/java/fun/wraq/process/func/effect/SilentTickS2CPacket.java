package fun.wraq.process.func.effect;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SilentTickS2CPacket {
    private final int silentTick;

    public SilentTickS2CPacket(int silentTick) {
        this.silentTick = silentTick;
    }

    public SilentTickS2CPacket(FriendlyByteBuf buf) {
        this.silentTick = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.silentTick);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SpecialEffectOnPlayer.clientSilentTick = silentTick;
        });
        return true;
    }
}
