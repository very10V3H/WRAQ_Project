package fun.wraq.render.hud.networking;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class AttributeDataS2CPacket {

    private final List<Double> attributes;
    private final int id;

    public AttributeDataS2CPacket(List<Double> attributes, int id) {
        this.attributes = attributes;
        this.id = id;
    }

    public AttributeDataS2CPacket(FriendlyByteBuf buf) {
        this.attributes = buf.readList((friendlyByteBuf) -> {
            return buf.readDouble();
        });
        this.id = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(this.attributes, ((friendlyByteBuf, value) -> {
            buf.writeDouble(value);
        }));
        buf.writeInt(this.id);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.playerAttributeList = attributes;
            ClientUtils.playerId = id;
        });
        return true;
    }
}
