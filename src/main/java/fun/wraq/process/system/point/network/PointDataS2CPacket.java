package fun.wraq.process.system.point.network;

import fun.wraq.process.system.point.Point;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class PointDataS2CPacket {

    private final List<Integer> pointValues;

    public PointDataS2CPacket(List<Integer> pointValues) {
        this.pointValues = pointValues;
    }

    public PointDataS2CPacket(FriendlyByteBuf buf) {
        this.pointValues = buf.readList((friendlyByteBuf) -> {
            return buf.readInt();
        });
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(this.pointValues, ((friendlyByteBuf, value) -> {
            buf.writeInt(value);
        }));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Point.clientData = pointValues;
        });
        return true;
    }
}
