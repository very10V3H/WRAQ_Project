package fun.wraq.process.system.element.networking;

import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.season.MySeason;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CurrentSeasonAndResonanceTypeS2CPacket {

    private final String season;
    private final String resonanceType;

    public CurrentSeasonAndResonanceTypeS2CPacket(String season, String resonanceType) {
        this.season = season;
        this.resonanceType = resonanceType;
    }

    public CurrentSeasonAndResonanceTypeS2CPacket(FriendlyByteBuf buf) {
        this.season = buf.readUtf();
        this.resonanceType = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(season);
        buf.writeUtf(resonanceType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MySeason.clientSeason = season;
            Element.clientResonanceType = resonanceType;
        });
        return true;
    }
}
