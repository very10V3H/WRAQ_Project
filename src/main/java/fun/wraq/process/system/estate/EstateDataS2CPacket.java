package fun.wraq.process.system.estate;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EstateDataS2CPacket {

    private final int estateSerialNum;
    private final int realEstateSerialNum;

    public EstateDataS2CPacket(int estateSerialNum, int realEstateSerialNum) {
        this.estateSerialNum = estateSerialNum;
        this.realEstateSerialNum = realEstateSerialNum;
    }

    public EstateDataS2CPacket(FriendlyByteBuf buf) {
        this.estateSerialNum = buf.readInt();
        this.realEstateSerialNum = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(estateSerialNum);
        buf.writeInt(realEstateSerialNum);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            EstateUtil.clientEstateSerialNum = estateSerialNum;
            EstateUtil.clientRealEstateSerialNum = realEstateSerialNum;
        });
        return true;
    }
}
