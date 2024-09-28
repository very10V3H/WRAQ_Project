package fun.wraq.process.system.element.networking;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.networking.CurrentSeasonAndResonanceTypeS2CPacket;
import fun.wraq.process.system.season.MySeason;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CurrentSeasonC2SPacket {

    public CurrentSeasonC2SPacket() {

    }

    public CurrentSeasonC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ModNetworking.sendToClient(
                    new CurrentSeasonAndResonanceTypeS2CPacket(MySeason.currentSeason,
                            Element.PlayerResonanceType.getOrDefault(context.getSender(), "")),
                    context.getSender());
        });
        return true;
    }
}
