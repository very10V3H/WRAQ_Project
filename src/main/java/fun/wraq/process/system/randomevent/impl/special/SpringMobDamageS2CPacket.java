package fun.wraq.process.system.randomevent.impl.special;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpringMobDamageS2CPacket {

    private final double damage;
    private final int expiredTick;

    public SpringMobDamageS2CPacket(double damage, int expiredTick) {
        this.damage = damage;
        this.expiredTick = expiredTick;
    }

    public SpringMobDamageS2CPacket(FriendlyByteBuf buf) {
        this.damage = buf.readDouble();
        this.expiredTick = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(damage);
        buf.writeInt(expiredTick);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SpringMobEvent.clientDamage = damage;
            SpringMobEvent.clientDamageDisplayExpiredTick = expiredTick;
        });
        return true;
    }
}
