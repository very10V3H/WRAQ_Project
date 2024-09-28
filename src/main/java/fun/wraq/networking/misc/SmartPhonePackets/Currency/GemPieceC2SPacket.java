package fun.wraq.networking.misc.SmartPhonePackets.Currency;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class GemPieceC2SPacket {

    public GemPieceC2SPacket() {

    }

    public GemPieceC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            try {
                InventoryOperation.itemTrade(player, new ItemStack(ModItems.gemPiece.get(), 64), new ItemStack(ModItems.completeGem.get()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
