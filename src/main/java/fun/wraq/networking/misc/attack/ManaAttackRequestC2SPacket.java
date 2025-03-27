package fun.wraq.networking.misc.attack;

import fun.wraq.common.equip.WraqSceptre;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaAttackRequestC2SPacket {

    public ManaAttackRequestC2SPacket() {
    }

    public ManaAttackRequestC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer == null) return;
            WraqSceptre.playShootAnimationAndHandleTrig(serverPlayer);
        });
        return true;
    }
}
