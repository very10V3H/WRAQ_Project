package fun.wraq.process.system.wayPoints.networking;

import fun.wraq.process.system.wayPoints.MyWayPoint;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class SpecificWayPointAddS2CPacket {

    private final Vector3f position;
    private final String name;
    private final int color;
    private final int type;

    public SpecificWayPointAddS2CPacket(Vector3f position, String name, int color, int type) {
        this.position = position;
        this.name = name;
        this.color = color;
        this.type = type;
    }

    public SpecificWayPointAddS2CPacket(FriendlyByteBuf buf) {
        this.position = buf.readVector3f();
        this.name = buf.readUtf();
        this.color = buf.readInt();
        this.type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVector3f(position);
        buf.writeUtf(name);
        buf.writeInt(color);
        buf.writeInt(type);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MyWayPoint myWayPoint = new MyWayPoint(new Vec3(position.x, position.y, position.z), name, color, type);
            MyWayPoint.addWaypoint(myWayPoint);
        });
        return true;
    }
}
