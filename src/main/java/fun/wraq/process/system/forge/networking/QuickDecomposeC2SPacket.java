package fun.wraq.process.system.forge.networking;

import fun.wraq.events.mob.loot.RandomLootEquip;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class QuickDecomposeC2SPacket {

    public QuickDecomposeC2SPacket() {
    }

    public QuickDecomposeC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null) {
                try {
                    RandomLootEquip.quickDecompose(serverPlayer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return true;
    }
}
