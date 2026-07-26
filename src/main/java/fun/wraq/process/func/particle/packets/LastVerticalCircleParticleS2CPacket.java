package fun.wraq.process.func.particle.packets;

import fun.wraq.render.hud.main.ClientCircleRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class LastVerticalCircleParticleS2CPacket {

    private final Vector3f vector3f;
    private final double r;
    private final int num;
    private final int color;
    private final int lastTick;

    public LastVerticalCircleParticleS2CPacket(Vector3f vector3f, double r, int num, int color, int lastTick) {
        this.vector3f = vector3f;
        this.r = r;
        this.num = num;
        this.color = color;
        this.lastTick = lastTick;
    }

    public LastVerticalCircleParticleS2CPacket(FriendlyByteBuf buf) {
        this.vector3f = buf.readVector3f();
        this.r = buf.readDouble();
        this.num = buf.readInt();
        this.color = buf.readInt();
        this.lastTick = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(vector3f);
        buf.writeDouble(r);
        buf.writeInt(num);
        buf.writeInt(color);
        buf.writeInt(lastTick);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientCircleRenderer.addCircle(new Vec3(vector3f), r, color, lastTick);
        });
        return true;
    }
}
