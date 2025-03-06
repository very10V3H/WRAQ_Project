package fun.wraq.process.system.element.networking;

import fun.wraq.process.system.element.piece.ElementPieceData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ElementPieceS2CPacket {

    private final CompoundTag pieceData;

    public ElementPieceS2CPacket(CompoundTag pieceData) {
        this.pieceData = pieceData;
    }

    public ElementPieceS2CPacket(FriendlyByteBuf buf) {
        this.pieceData = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(pieceData);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ElementPieceData.clientData = pieceData;
        });
        return true;
    }
}
