package fun.wraq.render.hud.networking;

import fun.wraq.render.hud.main.ItemAndExpGetHud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ExpGetResetS2CPacket {

    public ExpGetResetS2CPacket() {
    }

    public ExpGetResetS2CPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ItemAndExpGetHud.getExp = 0;
            ItemAndExpGetHud.lastFreshTick = 0;
        });
        return true;
    }
}
