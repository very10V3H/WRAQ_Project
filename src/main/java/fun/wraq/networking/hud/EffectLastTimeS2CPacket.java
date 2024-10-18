package fun.wraq.networking.hud;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.struct.HudIcon;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EffectLastTimeS2CPacket {
    private final int lastTime;
    private final String url;
    private final int level;
    private final boolean forever;

    public EffectLastTimeS2CPacket(String url, int counts) {
        this(url, counts, 0, false);
    }

    public EffectLastTimeS2CPacket(String url, int counts, int level, boolean forever) {
        this.url = url;
        this.lastTime = counts;
        this.level = level;
        this.forever = forever;
    }

    public EffectLastTimeS2CPacket(FriendlyByteBuf buf) {
        this.url = buf.readUtf();
        this.lastTime = buf.readInt();
        this.level = buf.readInt();
        this.forever = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.url);
        buf.writeInt(this.lastTime);
        buf.writeInt(this.level);
        buf.writeBoolean(this.forever);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.effectTimeLasts.removeIf(hudIcon -> hudIcon.url.equals(this.url));
            if (this.forever) {
                ClientUtils.effectTimeLasts.add(new HudIcon(url, lastTime, lastTime, level, true));
            }
            else {
                ClientUtils.effectTimeLasts.add(new HudIcon(url, lastTime, lastTime, level));
            }
        });
        return true;
    }
}
