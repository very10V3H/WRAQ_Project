package fun.wraq.render.hud.networking;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.render.hud.main.ItemAndExpGetHud;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ExpGetS2CPacket {

    private final double exp;

    public ExpGetS2CPacket(double exp) {
        this.exp = exp;
    }

    public ExpGetS2CPacket(FriendlyByteBuf buf) {
        this.exp = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.exp);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ItemAndExpGetHud.getExp = exp;
            ItemAndExpGetHud.lastFreshTick = ClientUtils.clientPlayerTick;
        });
        return true;
    }
}
