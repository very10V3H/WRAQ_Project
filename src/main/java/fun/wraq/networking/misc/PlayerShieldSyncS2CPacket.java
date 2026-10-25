/**
 * AI-Generated, 2026-10-18
 */
package fun.wraq.networking.misc;

import fun.wraq.render.hud.ClientShieldData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 实体护盾同步（服务端 → 周围玩家）。
 *
 * <p>玩家与怪物共用：护盾值变化时发送，护盾持续期间每 10 tick 刷新一次，护盾结束时发送 0。
 * 客户端按实体 id 存入 {@link ClientShieldData}，头顶血条的白色护盾段据此绘制。</p>
 */
public class PlayerShieldSyncS2CPacket {
    private final int entityId;
    private final double shield;

    public PlayerShieldSyncS2CPacket(int entityId, double shield) {
        this.entityId = entityId;
        this.shield = shield;
    }

    public PlayerShieldSyncS2CPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.shield = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeDouble(this.shield);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> ClientShieldData.setEntityShield(this.entityId, this.shield));
        return true;
    }
}
