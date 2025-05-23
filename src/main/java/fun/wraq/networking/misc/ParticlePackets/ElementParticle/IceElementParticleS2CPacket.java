package fun.wraq.networking.misc.ParticlePackets.ElementParticle;

import fun.wraq.process.system.element.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class IceElementParticleS2CPacket {
    private final int id;
    private final int time;

    public IceElementParticleS2CPacket(int Id, int Time) {
        this.id = Id;
        this.time = Time;
    }

    public IceElementParticleS2CPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.time = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.id);
        buf.writeInt(this.time);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().level.getEntity(id);
            if (entity != null) {
                Element.ClientRemoveEntityParticle(entity);
                Element.iceElementParticle.put(entity, time);
            }
        });
        return true;
    }
}
