package fun.wraq.networking.misc.SmartPhonePackets;

import fun.wraq.render.gui.testAndHelper.OpenGui;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenSmartPhoneS2CPacket {
    public OpenSmartPhoneS2CPacket() {

    }

    public OpenSmartPhoneS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> OpenGui::new);
        });
        return true;
    }
}
