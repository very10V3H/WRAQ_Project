/** AI-Generated, 2026-10-17 */
package fun.wraq.events.mob;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.util.HealUtil;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 怪物生命回复处理器。
 * <p>
 * 每秒（20 tick）为存活的、由本模组注册过基础属性的怪物回复生命值，回复量由两部分组成：
 * <ul>
 *   <li>固定生命回复：{@link MobAttributes#healthRecover(Mob)}</li>
 *   <li>百分比生命回复：{@link MobAttributes#percentHealthRecover(Mob)} * 最大生命值</li>
 * </ul>
 * 通过 {@link fun.wraq.events.mob.MobSpawn.MobBaseAttributes#setMobBaseAttributes} 注册的怪物
 * 默认每秒回复 1% 最大生命值，可在 {@link MobAttributes} 中单独调整。
 * </p>
 */
@Mod.EventBusSubscriber
public class MobHealthRecoverHandler {

    /** 生命回复间隔（tick），20 tick = 1 秒. */
    public static final int RECOVER_INTERVAL_TICK = 20;

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) return;
        if (mob.level().isClientSide) return;
        if (mob.tickCount % RECOVER_INTERVAL_TICK != 0) return;
        if (!mob.isAlive() || mob.isDeadOrDying()) return;
        // 仅对本模组注册过的怪物生效，避免影响原版生物
        if (!MobSpawn.MobBaseAttributes.isRegisteredMob(mob)) return;
        double recoverValue = MobAttributes.healthRecover(mob)
                + mob.getMaxHealth() * MobAttributes.percentHealthRecover(mob);
        if (recoverValue <= 0) return;
        HealUtil.mobHeal(mob, recoverValue);
    }
}
