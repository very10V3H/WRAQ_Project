package fun.wraq.series.events._7shade;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class SevenShadePieceS2CPacket {
    private final List<Item> list;

    public SevenShadePieceS2CPacket(List<Item> list) {
        this.list = list;
    }

    public SevenShadePieceS2CPacket(FriendlyByteBuf buf) {
        this.list = buf.readList((friendlyByteBuf -> {
            return friendlyByteBuf.readItem().getItem();
        }));
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(this.list, ((friendlyByteBuf, item) -> {
            buf.writeItem(item.getDefaultInstance());
        }));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SevenShadePiece.clientActivePieces.clear();
            SevenShadePiece.clientActivePieces.addAll(list);
        });
        return true;
    }
}
