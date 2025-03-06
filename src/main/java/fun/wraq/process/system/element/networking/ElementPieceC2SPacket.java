package fun.wraq.process.system.element.networking;

import fun.wraq.process.system.element.piece.ElementPieceData;
import fun.wraq.process.system.element.piece.ElementPieceRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ElementPieceC2SPacket {

    private final String type;
    private final int index0;
    private final int index1;

    public ElementPieceC2SPacket(String type, int index0, int index1) {
        this.type = type;
        this.index0 = index0;
        this.index1 = index1;
    }

    public ElementPieceC2SPacket(FriendlyByteBuf buf) {
        this.type = buf.readUtf();
        this.index0 = buf.readInt();
        this.index1 = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(type);
        buf.writeInt(index0);
        buf.writeInt(index1);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                switch (type) {
                    case "get" -> ElementPieceRecipe.tryToGet(player, index0, index1);
                    case "compose" -> ElementPieceRecipe.tryToCompose(player, index0, index1);
                    case "convert" -> ElementPieceData.convert(player);
                }
            }
        });
        return true;
    }
}
