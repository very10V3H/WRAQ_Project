package fun.wraq.events.mob;

import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.projectiles.mana.ManaArrow;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * AI-Generated, 2026-10-26
 * 怪物释放法球的公共工具。
 * <p>
 * 调用方只需要给出怪物、目标、是否有前摇、速度、基础伤害、颜色与法术穿透，
 * 工具会负责创建 {@link ManaArrow}、设置颜色/伤害/穿透，并按需处理前摇：
 * 前摇期间怪物停止移动、面向目标并播放一次基础攻击动作，前摇结束后再发射法球。
 */
@Mod.EventBusSubscriber
public final class MobSkillUtil {

    /** 有前摇时的默认前摇时长（tick）。 */
    public static final int DEFAULT_WINDUP_TICKS = 10;

    private static final int DEFAULT_COLOR = 0xFFAA88FF;
    private static final float MIN_SPEED = 0.05F;
    private static final Map<UUID, PendingCast> PENDING_CASTS = new HashMap<>();

    private MobSkillUtil() {
    }

    /**
     * 向指定玩家发射一颗怪物法球。
     *
     * @param caster              施法怪物
     * @param target              目标玩家
     * @param hasWindup           是否有前摇；有前摇时本方法返回 null，法球会在前摇结束时发射
     * @param speed               法球速度
     * @param baseDamage          法球基础伤害（计算护甲/法术抗性之前）
     * @param style               法球颜色，取 {@link Style#getColor()}；为空时使用默认紫色
     * @param manaPenetration     百分比法术穿透
     * @param manaPenetration0    固定法术穿透
     * @return 立即发射时返回法球实体；有前摇时返回 null
     */
    public static ManaArrow shootManaProjectile(Mob caster, Player target, boolean hasWindup,
                                                float speed, double baseDamage, Style style,
                                                double manaPenetration, double manaPenetration0) {
        if (!canCast(caster, target)) {
            return null;
        }
        if (hasWindup) {
            startWindup(caster, target, speed, baseDamage, style, manaPenetration, manaPenetration0);
            return null;
        }
        float[] rotation = rotationTo(caster, target);
        return fire(caster, rotation[0], rotation[1], speed, baseDamage, style,
                manaPenetration, manaPenetration0);
    }

    /**
     * 按固定朝向发射一颗怪物法球（用于 MoonInstance 的环形弹幕等场景）。
     */
    public static ManaArrow shootManaProjectile(Mob caster, float yaw, float pitch,
                                                float speed, double baseDamage, Style style,
                                                double manaPenetration, double manaPenetration0) {
        if (!canCast(caster, null)) {
            return null;
        }
        return fire(caster, yaw, pitch, speed, baseDamage, style,
                manaPenetration, manaPenetration0);
    }

    private static boolean canCast(Mob caster, Player target) {
        return caster != null
                && caster.isAlive()
                && !caster.isRemoved()
                && !caster.level().isClientSide
                && (target == null || (target.isAlive() && !target.isRemoved()));
    }

    private static void startWindup(Mob caster, Player target, float speed, double baseDamage,
                                    Style style, double manaPenetration, double manaPenetration0) {
        PendingCast existing = PENDING_CASTS.get(caster.getUUID());
        boolean previousNoAi = existing != null ? existing.previousNoAi() : caster.isNoAi();
        PENDING_CASTS.put(caster.getUUID(), new PendingCast(caster, target.getUUID(),
                Tick.get() + DEFAULT_WINDUP_TICKS, speed, baseDamage, style,
                manaPenetration, manaPenetration0, previousNoAi));
        // 前摇期间彻底停住 AI，避免只清速度后又被寻路/移动控制推着走。
        caster.setNoAi(true);
        freeze(caster, target);
        caster.swing(InteractionHand.MAIN_HAND);
    }

    private static void restoreAi(Mob caster, PendingCast cast) {
        if (caster.isAlive() && !caster.isRemoved()) {
            caster.setNoAi(cast.previousNoAi());
        }
    }

    private static void freeze(Mob caster, Player target) {
        caster.getNavigation().stop();
        caster.setDeltaMovement(0, 0, 0);
        caster.getLookControl().setLookAt(target, 30, 30);
    }

    private static ManaArrow fire(Mob caster, float yaw, float pitch,
                                  float speed, double baseDamage, Style style,
                                  double manaPenetration, double manaPenetration0) {
        ManaArrow arrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), caster, caster.level(), 1);
        arrow.setStyle(colorOf(style));
        arrow.setMobDamage(baseDamage, manaPenetration, manaPenetration0);
        arrow.shootFromRotation(caster, pitch, yaw, 0.0F, Math.max(MIN_SPEED, speed), 1.0F);
        arrow.setNoGravity(true);
        caster.level().addFreshEntity(arrow);
        return arrow;
    }

    private static float[] rotationTo(Mob caster, Player target) {
        double dx = target.getX() - caster.getX();
        double dy = (target.getEyeY() + target.getY()) / 2 - caster.getEyeY();
        double dz = target.getZ() - caster.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90.0F;
        float pitch = (float) (-Math.toDegrees(Math.atan2(dy, horizontal)));
        return new float[]{yaw, pitch};
    }

    private static int colorOf(Style style) {
        return style != null && style.getColor() != null
                ? style.getColor().getValue() : DEFAULT_COLOR;
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Mob caster) || caster.level().isClientSide) {
            return;
        }
        PendingCast cast = PENDING_CASTS.get(caster.getUUID());
        if (cast == null) {
            return;
        }
        if (!caster.isAlive() || caster.isRemoved()) {
            PENDING_CASTS.remove(caster.getUUID());
            return;
        }
        Player target = caster.level().getPlayerByUUID(cast.targetId());
        if (target == null || !target.isAlive() || target.isRemoved()) {
            PENDING_CASTS.remove(caster.getUUID());
            restoreAi(caster, cast);
            return;
        }
        freeze(caster, target);
        if (Tick.get() < cast.releaseTick()) {
            return;
        }
        PENDING_CASTS.remove(caster.getUUID());
        restoreAi(caster, cast);
        float[] rotation = rotationTo(caster, target);
        fire(caster, rotation[0], rotation[1], cast.speed(), cast.baseDamage(),
                cast.style(), cast.manaPenetration(), cast.manaPenetration0());
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Mob mob) {
            PendingCast cast = PENDING_CASTS.remove(mob.getUUID());
            if (cast != null) {
                restoreAi(mob, cast);
            }
        }
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        // 在世界保存前解除前摇 NoAI，避免服务器正常关闭时把怪物永久冻结状态写进存档。
        PENDING_CASTS.values().forEach(cast -> restoreAi(cast.caster(), cast));
        PENDING_CASTS.clear();
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        PENDING_CASTS.clear();
    }

    private record PendingCast(Mob caster, UUID targetId, long releaseTick, float speed, double baseDamage,
                               Style style, double manaPenetration, double manaPenetration0,
                               boolean previousNoAi) {
    }
}
