package fun.wraq.render.gui.trade.weekly;

import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WeeklyStoreScreenC2SPacket {

    public WeeklyStoreScreenC2SPacket() {
    }

    public WeeklyStoreScreenC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = supplier.get().getSender();
            if (player != null) {
                WeeklyStorePlayerData.sendDataToClient(player);
                ModNetworking.sendToClient(new ScreenSetS2CPacket(10), player);
            }
        });
        return true;
    }
}
