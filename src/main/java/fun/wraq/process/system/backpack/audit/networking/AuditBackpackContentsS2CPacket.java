package fun.wraq.process.system.backpack.audit.networking;

import fun.wraq.networking.ModNetworking;
import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackStorage;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-18
 * S2C：把某个精妙背包（按内容 UUID）的完整 NBT 同步给管理员客户端。
 *
 * <p>为什么要自己同步：精妙背包的物品/升级都在世界的 {@code BackpackStorage} 里，
 * 客户端只有 {@code clientStorageCopy} 这一份副本，且 SB 只在特定时机（悬浮预览）才拉取。
 * 管理员打开的「虚拟背包」不在物品栏里，不会触发那条路径，所以这里主动把内容送过去，
 * 客户端收到后回执 {@link AuditBackpackReadyC2SPacket}，服务端才真正打开界面——
 * 保证客户端构造容器时已经能读到完整内容（升级页签、排序、设置都正确）。
 */
public class AuditBackpackContentsS2CPacket {

    private final UUID contentsUuid;
    private final CompoundTag contents;
    private final int requestId;

    public AuditBackpackContentsS2CPacket(UUID contentsUuid, CompoundTag contents, int requestId) {
        this.contentsUuid = contentsUuid;
        this.contents = contents;
        this.requestId = requestId;
    }

    public AuditBackpackContentsS2CPacket(FriendlyByteBuf buf) {
        this.contentsUuid = buf.readUUID();
        this.contents = buf.readNbt();
        this.requestId = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(contentsUuid);
        buf.writeNbt(contents);
        buf.writeInt(requestId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            CompoundTag synced = Objects.requireNonNullElseGet(contents, CompoundTag::new);
            BackpackStorage.get().setBackpackContents(contentsUuid, synced);
            // 便于排查「客户端没有升级数据导致堆叠升级不生效」：打开界面前先确认内容与升级都已到达
            LogUtils.getLogger().info("[VMD-背包审计] 客户端同步背包 {}：物品 {} 件 / 升级 {} 个 / 标签 {}",
                    contentsUuid,
                    synced.getCompound("inventory").getList("Items", Tag.TAG_COMPOUND).size(),
                    synced.getCompound("upgradeInventory").getList("Items", Tag.TAG_COMPOUND).size(),
                    synced.getAllKeys());
            ModNetworking.sendToServer(new AuditBackpackReadyC2SPacket(requestId));
        });
        return true;
    }
}
