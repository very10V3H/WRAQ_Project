package fun.wraq.process.system.achievement.networking;

import fun.wraq.process.system.achievement.AchievementClientData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-30
 * 玩家成就数据全量同步。
 */
public class AchievementDataS2CPacket {

    private final CompoundTag data;

    public AchievementDataS2CPacket(CompoundTag data) {
        this.data = data;
    }

    public AchievementDataS2CPacket(FriendlyByteBuf buf) {
        this.data = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(data);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> AchievementClientData.data = data);
        return true;
    }

    public CompoundTag data() {
        return data;
    }
}
