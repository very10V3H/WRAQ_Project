package fun.wraq.networking.misc.attack;

import fun.wraq.common.equip.WraqBow;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BowAttackRequestC2SPacket {

    public BowAttackRequestC2SPacket() {
    }

    public BowAttackRequestC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer == null) return;
            WraqBow.playShootAnimationAndHandleTrig(serverPlayer);
        });
        return true;
    }
}
