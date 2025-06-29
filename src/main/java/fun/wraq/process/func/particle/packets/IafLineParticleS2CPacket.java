package fun.wraq.process.func.particle.packets;

import com.github.alexthe666.iceandfire.IceAndFire;
import fun.wraq.process.func.particle.ParticleProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class IafLineParticleS2CPacket {
    private final Vector3f endVec3;
    private final Vector3f startVec3;
    private final int num;
    private final String name;

    public IafLineParticleS2CPacket(Vector3f endVec3, Vector3f startVec3, int num, String name) {
        this.endVec3 = endVec3;
        this.startVec3 = startVec3;
        this.num = num;
        this.name = name;
    }

    public IafLineParticleS2CPacket(FriendlyByteBuf buf) {
        this.endVec3 = buf.readVector3f();
        this.startVec3 = buf.readVector3f();
        this.num = buf.readInt();
        this.name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(endVec3);
        buf.writeVector3f(startVec3);
        buf.writeInt(num);
        buf.writeUtf(name);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Vector3f vector3f = endVec3.sub(startVec3);
            Vec3 vec = new Vec3(vector3f.x, vector3f.y, vector3f.z);
            for (int i = 0; i < num; i++) {
                Vec3 pointVec = new Vec3(startVec3.x + vec.x * (i * 1.0 / num), startVec3.y + vec.y * (i * 1.0 / num),
                        startVec3.z + vec.z * (i * 1.0 / num));
                IceAndFire.PROXY.spawnParticle(ParticleProvider.iafParticlesMap.get(name), pointVec.x, pointVec.y
                        , pointVec.z, 0, 0, 0);
            }
        });
        return true;
    }
}
