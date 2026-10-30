package fun.wraq.process.system.achievement.networking;

import fun.wraq.process.system.achievement.AchievementManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * AI-Generated, 2026-10-30
 * 玩家请求领取成就奖励。
 */
public class AchievementClaimC2SPacket {

    private final String achievementId;

    public AchievementClaimC2SPacket(String achievementId) {
        this.achievementId = achievementId;
    }

    public AchievementClaimC2SPacket(FriendlyByteBuf buf) {
        this.achievementId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(achievementId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                AchievementManager.claim(player, achievementId);
                AchievementManager.sync(player);
            }
        });
        return true;
    }
}
