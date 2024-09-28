package fun.wraq.networking.misc.PrefixPackets;

import fun.wraq.Items.Prefix.PrefixInfo;
import fun.wraq.commands.changeable.PrefixCommand;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class PrefixS2CPacket {
    private final List<PrefixInfoWithUUID> list;

    public record PrefixInfoWithUUID(UUID uuid, PrefixInfo prefixInfo) {
    }

    public PrefixS2CPacket(List<PrefixInfoWithUUID> list) {
        this.list = list;
    }

    public PrefixS2CPacket(FriendlyByteBuf buf) {
        this.list = buf.readList((friendlyByteBuf -> {
            UUID uuid = buf.readUUID();
            String prefixName = buf.readUtf();
            String color = buf.readUtf();
            int level = buf.readInt();
            return new PrefixInfoWithUUID(uuid, new PrefixInfo(prefixName, color, level));
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
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            this.list.forEach(prefixInfoWithUUID -> {
                PrefixCommand.clientPrefixInfo.put(prefixInfoWithUUID.uuid, prefixInfoWithUUID.prefixInfo);
            });
        });
        return true;
    }
}
