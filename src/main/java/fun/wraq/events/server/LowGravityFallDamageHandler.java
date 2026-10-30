package fun.wraq.events.server;

import fun.wraq.common.Compute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * AI-Generated, 2026-10-30
 * 低重力区域：取消玩家的摔落伤害，避免在该区域落地时受到原版摔落伤害。
 */
@Mod.EventBusSubscriber
public final class LowGravityFallDamageHandler {

    private LowGravityFallDamageHandler() {
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player
                && !player.level().isClientSide
                && Compute.inLowGravityEnvironment(player)) {
            event.setCanceled(true);
            player.fallDistance = 0;
        }
    }
}
