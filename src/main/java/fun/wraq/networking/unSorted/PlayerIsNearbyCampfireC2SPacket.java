package fun.wraq.networking.unSorted;

import fun.wraq.render.hud.ColdData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerIsNearbyCampfireC2SPacket {
    public PlayerIsNearbyCampfireC2SPacket() {

    }

    public PlayerIsNearbyCampfireC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            ColdData.addPlayerColdValue(serverPlayer, -2);
        });
        return true;
    }
}
