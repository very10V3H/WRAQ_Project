package fun.wraq.networking.afk;

import fun.wraq.common.util.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 同步AFK数据到客户端
 * AI-Generated, 2026-05-17
 */
public class AfkDataS2CPacket {
    private final String selectedMobTypeId;
    private final long lastHarvestTime;
    private final long serverTick;
    private final List<Map<String, String>> mobTypeList;

    public AfkDataS2CPacket(String selectedMobTypeId, long lastHarvestTime,
                             long serverTick, List<Map<String, String>> mobTypeList) {
        this.selectedMobTypeId = selectedMobTypeId;
        this.lastHarvestTime = lastHarvestTime;
        this.serverTick = serverTick;
        this.mobTypeList = mobTypeList;
    }

    public AfkDataS2CPacket(FriendlyByteBuf buf) {
        this.selectedMobTypeId = buf.readUtf();
        this.lastHarvestTime = buf.readLong();
        this.serverTick = buf.readLong();
        int size = buf.readInt();
        this.mobTypeList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Map<String, String> entry = new HashMap<>();
            entry.put("id", buf.readUtf());
            entry.put("name", buf.readUtf());
            entry.put("category", buf.readUtf());
            mobTypeList.add(entry);
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(selectedMobTypeId);
        buf.writeLong(lastHarvestTime);
        buf.writeLong(serverTick);
        buf.writeInt(mobTypeList.size());
        for (Map<String, String> entry : mobTypeList) {
            buf.writeUtf(entry.getOrDefault("id", ""));
            buf.writeUtf(entry.getOrDefault("name", ""));
            buf.writeUtf(entry.getOrDefault("category", ""));
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.afkSelectedMobTypeId = this.selectedMobTypeId;
            ClientUtils.afkLastHarvestTime = this.lastHarvestTime;
            ClientUtils.afkServerTick = this.serverTick;
            ClientUtils.afkMobTypeList = this.mobTypeList;
            ClientUtils.afkScreenOpenFlag = true;
        });
        return true;
    }
}
