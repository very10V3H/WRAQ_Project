package fun.wraq.events.mob;

import fun.wraq.common.util.items.ItemAndRate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * AI-Generated, 2026-10-25
 * 关闭怪物死亡时的经验球掉落。
 * <p>
 * 两条来源都处理：
 * <ul>
 *     <li>原版 {@link LivingExperienceDropEvent}：置 droppedExperience = 0，阻止 ExperienceOrbEntity 生成；</li>
 *     <li>VMD 自己创建的实体经验球：带 {@link MobSpawn#fromMobSpawnTag} /
 *         {@link ItemAndRate#fromMobExpDropTag} 的 {@link ExperienceOrb} 在加入世界时直接取消。</li>
 * </ul>
 * 挖矿系统使用的 {@code fromMineReward} 经验球不受影响；动物/村民的原版经验也保留。
 */
@Mod.EventBusSubscriber
public final class MobExperienceDropHandler {

    private MobExperienceDropHandler() {
    }

    @SubscribeEvent
    public static void onLivingExperienceDrop(LivingExperienceDropEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }
        if (mob.level().isClientSide) {
            return;
        }
        if (!(mob instanceof Enemy) && !MobSpawn.MobBaseAttributes.isRegisteredMob(mob)) {
            return;
        }
        // LivingExperienceDropEvent 没有 cancel 语义，置 0 即可跳过原版 ExperienceOrbEntity 生成。
        event.setDroppedExperience(0);
    }

    /**
     * 拦截 VMD 自己生成的怪物经验球。标签在 {@code ItemAndRate.dropOrbs} 中、
     * 加入世界之前写入，所以 EntityJoinLevelEvent 能读到。
     */
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof ExperienceOrb orb)) {
            return;
        }
        if (orb.level().isClientSide) {
            return;
        }
        CompoundTag data = orb.getPersistentData();
        if (data.contains(MobSpawn.fromMobSpawnTag)
                || data.contains(ItemAndRate.fromMobExpDropTag)) {
            event.setCanceled(true);
        }
    }
}
