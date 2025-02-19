package fun.wraq.networking.misc.PrefixPackets;

import fun.wraq.Items.Prefix.PrefixInfo;
import fun.wraq.commands.changeable.PrefixCommand;
import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class PrefixS2CPacket {
    private final List<PrefixInfoWithUUID> list;
    private final List<PlayerLocationInfo> locationInfos;

    public record PrefixInfoWithUUID(UUID uuid, PrefixInfo prefixInfo) {
    }

    public record PlayerLocationInfo(UUID uuid, int levelId, Vector3f pos) {}

    public PrefixS2CPacket(List<PrefixInfoWithUUID> list, List<PlayerLocationInfo> locationInfos) {
        this.list = list;
        this.locationInfos = locationInfos;
    }

    public PrefixS2CPacket(FriendlyByteBuf buf) {
        this.list = buf.readList((friendlyByteBuf -> {
            UUID uuid = buf.readUUID();
            String prefixName = buf.readUtf();
            String color = buf.readUtf();
            int level = buf.readInt();
            return new PrefixInfoWithUUID(uuid, new PrefixInfo(prefixName, color, level));
        }));
        this.locationInfos = buf.readList((friendlyByteBuf -> {
            UUID uuid = buf.readUUID();
            int levelId = buf.readInt();
            Vector3f pos = buf.readVector3f();
            return new PlayerLocationInfo(uuid, levelId, pos);
        }));
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(this.list, ((friendlyByteBuf, prefixInfoWithUUID) -> {
            UUID uuid = prefixInfoWithUUID.uuid;
            String prefixName = prefixInfoWithUUID.prefixInfo.getPrefix();
            String color = prefixInfoWithUUID.prefixInfo.getColor();
            int level = prefixInfoWithUUID.prefixInfo.getLevel();
            buf.writeUUID(uuid);
            buf.writeUtf(prefixName);
            buf.writeUtf(color);
            buf.writeInt(level);
        }));
        buf.writeCollection(this.locationInfos, ((friendlyByteBuf, playerLocationInfo) -> {
            UUID uuid = playerLocationInfo.uuid;
            int levelId = playerLocationInfo.levelId;
            Vector3f pos = playerLocationInfo.pos;
            buf.writeUUID(uuid);
            buf.writeInt(levelId);
            buf.writeVector3f(pos);
        }));
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            this.list.forEach(prefixInfoWithUUID -> {
                PrefixCommand.clientPrefixInfo.put(prefixInfoWithUUID.uuid, prefixInfoWithUUID.prefixInfo);
            });
            this.locationInfos.forEach(playerLocationInfo -> {
                ClientUtils.clientPlayerLevelIdMap.put(playerLocationInfo.uuid, playerLocationInfo.levelId);
                ClientUtils.clientPlayerLocationMap.put(playerLocationInfo.uuid, new Vec3(playerLocationInfo.pos));
            });
        });
        return true;
    }
}
