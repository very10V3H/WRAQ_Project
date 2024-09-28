package fun.wraq.networking.misc.ParticlePackets.ElementParticle;

import fun.wraq.process.system.element.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClearElementParticleS2CPacket {
    private final int id;

    public ClearElementParticleS2CPacket(int Id) {
        this.id = Id;
    }

    public ClearElementParticleS2CPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.id);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().level.getEntity(id);
            if (entity != null) {
                Element.ClientRemoveEntityParticle(entity);
            }
        });
        return true;
    }
}
