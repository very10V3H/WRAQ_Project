package fun.wraq.networking.misc.EarthPower;

import fun.wraq.series.overworld.sakuraSeries.EarthMana.EarthPower;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EarthPowerC2SPacket {

    private final int type;

    public EarthPowerC2SPacket(int type) {
        this.type = type;
    }

    public EarthPowerC2SPacket(FriendlyByteBuf buf) {
        this.type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        context.enqueueWork(() -> {
            EarthPower.Active(serverPlayer, this.type);
        });
        return true;
    }
}
