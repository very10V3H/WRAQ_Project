/** AI-Generated, 2026-07-19 */
package fun.wraq.process.system.worldtext.networking;

import fun.wraq.process.system.worldtext.WorldTextDataManager;
import fun.wraq.render.hud.main.WorldTextRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 服务端 → 客户端：同步某维度所有世界文字条目。
 * 客户端收到后替换本地缓存，由 {@link WorldTextRenderer} 每帧渲染。
 */
public class WorldTextS2CPacket {

    private final ResourceKey<Level> dimension;
    private final List<WorldTextDataManager.Entry> entries;

    public WorldTextS2CPacket(ResourceKey<Level> dimension, List<WorldTextDataManager.Entry> entries) {
        this.dimension = dimension;
        this.entries = entries;
    }

    public WorldTextS2CPacket(FriendlyByteBuf buf) {
        this.dimension = ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation());
        int size = buf.readVarInt();
        this.entries = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            Component text = buf.readComponent();
            this.entries.add(new WorldTextDataManager.Entry(new net.minecraft.world.phys.Vec3(x, y, z), text));
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(dimension.location());
        buf.writeVarInt(entries.size());
        for (WorldTextDataManager.Entry entry : entries) {
            buf.writeDouble(entry.pos().x);
            buf.writeDouble(entry.pos().y);
            buf.writeDouble(entry.pos().z);
            buf.writeComponent(entry.text());
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> WorldTextRenderer.updateEntries(dimension, entries));
        return true;
    }
}
