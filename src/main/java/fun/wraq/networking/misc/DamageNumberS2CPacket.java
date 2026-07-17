package fun.wraq.networking.misc;

import fun.wraq.render.hud.main.DamageNumberRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** AI-Generated, 2026-05-10 */
public class DamageNumberS2CPacket {
    private final double x;
    private final double y;
    private final double z;
    private final Component component;
    private final int durationMs;

    public DamageNumberS2CPacket(Vec3 position, Component component, int durationMs) {
        this.x = position.x;
        this.y = position.y;
        this.z = position.z;
        this.component = component;
        this.durationMs = durationMs;
    }

    public DamageNumberS2CPacket(FriendlyByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.component = buf.readComponent();
        this.durationMs = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeComponent(component);
        buf.writeInt(durationMs);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DamageNumberRenderer.addDamageNumber(new Vec3(x, y, z), component, durationMs);
        });
        return true;
    }
}
