package fun.wraq.process.func.particle.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class LineEffectParticleS2CPacket {
    private final Vector3f endVec3;
    private final Vector3f startVec3;
    private final int num;
    private final int color;

    public LineEffectParticleS2CPacket(Vector3f endVec3, Vector3f startVec3, int num, int color) {
        this.endVec3 = endVec3;
        this.startVec3 = startVec3;
        this.num = num;
        this.color = color;
    }

    public LineEffectParticleS2CPacket(FriendlyByteBuf buf) {
        this.endVec3 = buf.readVector3f();
        this.startVec3 = buf.readVector3f();
        this.num = buf.readInt();
        this.color = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(endVec3);
        buf.writeVector3f(startVec3);
        buf.writeInt(num);
        buf.writeInt(color);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Vector3f vector3f = endVec3.sub(startVec3);
            Vec3 vec = new Vec3(vector3f.x, vector3f.y, vector3f.z);
            double d0 = (double)(color >> 16 & 255) / 255.0;
            double d1 = (double)(color >> 8 & 255) / 255.0;
            double d2 = (double)(color >> 0 & 255) / 255.0;
            for (int i = 0; i < num; i++) {
                Vec3 pointVec = new Vec3(startVec3.x + vec.x * (i * 1.0 / num), startVec3.y + vec.y * (i * 1.0 / num),
                        startVec3.z + vec.z * (i * 1.0 / num));
                Minecraft.getInstance().level.addParticle(ParticleTypes.AMBIENT_ENTITY_EFFECT, pointVec.x, pointVec.y
                        , pointVec.z, d0, d1, d2);
            }
        });
        return true;
    }
}
