package fun.wraq.process.system.randomevent.impl.special;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpringMobDamageS2CPacket {

    private final double damage;
    private final int expiredTick;
    private final int luckNumber;

    public SpringMobDamageS2CPacket(double damage, int expiredTick, int luckNumber) {
        this.damage = damage;
        this.expiredTick = expiredTick;
        this.luckNumber = luckNumber;
    }

    public SpringMobDamageS2CPacket(FriendlyByteBuf buf) {
        this.damage = buf.readDouble();
        this.expiredTick = buf.readInt();
        this.luckNumber = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(damage);
        buf.writeInt(expiredTick);
        buf.writeInt(luckNumber);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SpringMobEvent.clientDamage = damage;
            SpringMobEvent.clientDamageDisplayExpiredTick = expiredTick;
            SpringMobEvent.clientLuckyNumber = luckNumber;
        });
        return true;
    }
}
