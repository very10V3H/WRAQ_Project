/**
 * AI-Generated, 2026-10-25
 */
package fun.wraq.events.mob;

import fun.wraq.common.util.struct.MobShield;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 怪物护盾处理器。
 *
 * <p>负责两件事：</p>
 * <ul>
 *     <li>每 tick 递减 {@link MobShield} 的持续时间，到期自动移除并同步给客户端；</li>
 *     <li>怪物死亡时清空护盾，避免残留数据与客户端白条。</li>
 * </ul>
 */
@Mod.EventBusSubscriber
public class MobShieldHandler {

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) return;
        if (mob.level().isClientSide) return;
        if (!MobShield.hasShield(mob)) return;
        // 已被移除 / 已死亡的怪物不再计时，直接清掉护盾
        if (mob.isRemoved() || !mob.isAlive()) {
            MobShield.clearMobShield(mob);
            return;
        }
        MobShield.tickMobShield(mob);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) return;
        if (mob.level().isClientSide) return;
        MobShield.clearMobShield(mob);
    }
}
