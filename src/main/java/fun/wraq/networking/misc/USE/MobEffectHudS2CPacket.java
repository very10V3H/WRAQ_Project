package fun.wraq.networking.misc.USE;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MobEffectHudS2CPacket {
    private final int id;
    private final String url;
    private final String tag;
    private final int lastTime;
    private final int level;
    private final boolean forever;

    public MobEffectHudS2CPacket(int id, String url, String tag, int counts, int level, boolean forever) {
        this.id = id;
        this.url = url;
        this.tag = tag;
        this.lastTime = counts;
        this.level = level;
        this.forever = forever;
    }

    public MobEffectHudS2CPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.url = buf.readUtf();
        this.tag = buf.readUtf();
        this.lastTime = buf.readInt();
        this.level = buf.readInt();
        this.forever = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeUtf(this.url);
        buf.writeUtf(this.tag);
        buf.writeInt(this.lastTime);
        buf.writeInt(this.level);
        buf.writeBoolean(this.forever);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (ClientUtils.clientLevel != null) {
                Entity entity = ClientUtils.clientLevel.getEntity(id);
                if (entity instanceof Mob mob) {
                    if (!ClientUtils.clientMobEffectMap.containsKey(mob)) ClientUtils.clientMobEffectMap.put(mob, new ArrayList<>());
                    List<ClientUtils.Effect> list = ClientUtils.clientMobEffectMap.get(mob);
                    list.removeIf(e -> e.tag().equals(tag));
                    list.add(new ClientUtils.Effect(url, tag, ClientUtils.clientPlayerTick, lastTime, level, forever));
                }
            }
        });
        return true;
    }
}
