package fun.wraq.process.func.plan.networking;

import fun.wraq.process.func.plan.DailySupply;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DailySupplyS2CPacket {

    private final int status;

    public DailySupplyS2CPacket(int status) {
        this.status = status;
    }

    public DailySupplyS2CPacket(FriendlyByteBuf buf) {
        this.status = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(status);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DailySupply.clientSupplyStatus = status;
        });
        return true;
    }
}
