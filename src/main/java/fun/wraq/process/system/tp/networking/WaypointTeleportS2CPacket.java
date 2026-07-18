/** AI-Generated, 2026-07-18 */
package fun.wraq.process.system.tp.networking;

import fun.wraq.process.system.tp.WaypointTeleportClientData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 服务端 → 客户端：同步传送锚点的解锁状态
 * 发送所有传送锚点的名称与是否已解锁，客户端用于渲染
 */
public class WaypointTeleportS2CPacket {

    private final String[] names;
    private final boolean[] unlocked;

    public WaypointTeleportS2CPacket(String[] names, boolean[] unlocked) {
        this.names = names;
        this.unlocked = unlocked;
    }

    public WaypointTeleportS2CPacket(FriendlyByteBuf buf) {
        int count = buf.readInt();
        names = new String[count];
        unlocked = new boolean[count];
        for (int i = 0; i < count; i++) {
            names[i] = buf.readUtf();
            unlocked[i] = buf.readBoolean();
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(names.length);
        for (int i = 0; i < names.length; i++) {
            buf.writeUtf(names[i]);
            buf.writeBoolean(unlocked[i]);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> WaypointTeleportClientData.setStatus(names, unlocked));
        return true;
    }
}
