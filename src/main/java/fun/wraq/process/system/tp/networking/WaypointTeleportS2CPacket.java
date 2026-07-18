/** AI-Generated, 2026-07-18 */
package fun.wraq.process.system.tp.networking;

import fun.wraq.process.system.tp.WaypointTeleportClientData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 服务端 → 客户端：同步传送锚点的解锁状态与颜色
 */
public class WaypointTeleportS2CPacket {

    private final String[] names;
    private final boolean[] unlocked;
    private final int[] colors;

    public WaypointTeleportS2CPacket(String[] names, boolean[] unlocked, int[] colors) {
        this.names = names;
        this.unlocked = unlocked;
        this.colors = colors;
    }

    public WaypointTeleportS2CPacket(FriendlyByteBuf buf) {
        int count = buf.readInt();
        names = new String[count];
        unlocked = new boolean[count];
        colors = new int[count];
        for (int i = 0; i < count; i++) {
            names[i] = buf.readUtf();
            unlocked[i] = buf.readBoolean();
            colors[i] = buf.readInt();
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(names.length);
        for (int i = 0; i < names.length; i++) {
            buf.writeUtf(names[i]);
            buf.writeBoolean(unlocked[i]);
            buf.writeInt(colors[i]);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> WaypointTeleportClientData.setStatus(names, unlocked, colors));
        return true;
    }
}
