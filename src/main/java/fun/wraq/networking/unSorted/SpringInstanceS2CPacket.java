package fun.wraq.networking.unSorted;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpringInstanceS2CPacket {
    public SpringInstanceS2CPacket() {

    }

    public SpringInstanceS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.SpringInstanceAttackCount = 40;
        });
        return true;
    }
}
