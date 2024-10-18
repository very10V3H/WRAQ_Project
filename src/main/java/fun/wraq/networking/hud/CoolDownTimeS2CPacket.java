package fun.wraq.networking.hud;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.struct.HudIcon;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CoolDownTimeS2CPacket {

    private final int lastTime;
    private final String url;
    private final int level;

    public CoolDownTimeS2CPacket(String url, int counts) {
        this.url = url;
        this.lastTime = counts;
        this.level = 0;
    }

    public CoolDownTimeS2CPacket(FriendlyByteBuf buf) {
        this.url = buf.readUtf();
        this.lastTime = buf.readInt();
        this.level = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.url);
        buf.writeInt(this.lastTime);
        buf.writeInt(this.level);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.coolDownTimes.removeIf(hudIcon -> hudIcon.url.equals(url));
            ClientUtils.coolDownTimes.add(new HudIcon(url, lastTime, lastTime, level));
        });
        return true;
    }
}
