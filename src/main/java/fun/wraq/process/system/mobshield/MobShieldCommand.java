/**
 * AI-Generated, 2026-10-25
 */
package fun.wraq.process.system.mobshield;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.MobShield;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.Locale;

/**
 * {@code /vmd mobshield <value> [seconds]} 测试命令。
 *
 * <p>给准星指向的怪物施加一层护盾（默认 10 秒），用于验证：
 * 护盾吸收伤害、持续时间、头顶血条上的白色护盾段。</p>
 */
public final class MobShieldCommand {

    private static final double PICK_DISTANCE = 32.0D;

    private MobShieldCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Utils.MOD_ID)
                .then(Commands.literal("mobshield")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("value", DoubleArgumentType.doubleArg(1.0D))
                                .executes(context -> apply(context, 10))
                                .then(Commands.argument("seconds", IntegerArgumentType.integer(1, 600))
                                        .executes(context -> apply(context,
                                                IntegerArgumentType.getInteger(context, "seconds")))))));
    }

    private static int apply(CommandContext<CommandSourceStack> context, int seconds)
            throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();
        double value = DoubleArgumentType.getDouble(context, "value");
        Mob mob = findTargetMob(player);
        if (mob == null) {
            source.sendFailure(Te.s("准星没有指向任何怪物。"));
            return 0;
        }
        MobShield.provideShield(mob, Tick.s(seconds), value);
        String amount = String.format(Locale.ROOT, "%.0f", value);
        source.sendSuccess(() -> Te.s("[护盾] 已给 ", mob.getDisplayName(),
                " 施加 ", amount, " 点护盾，持续 ", String.valueOf(seconds), " 秒。"), true);
        return 1;
    }

    /** 准星射线命中的最近怪物。 */
    private static Mob findTargetMob(ServerPlayer player) {
        Vec3 eye = player.getEyePosition();
        Vec3 end = eye.add(player.getLookAngle().scale(PICK_DISTANCE));
        AABB searchBox = new AABB(eye, end).inflate(1.0D);
        return player.level().getEntitiesOfClass(Mob.class, searchBox,
                        mob -> mob.getBoundingBox().inflate(0.3D).clip(eye, end).isPresent())
                .stream()
                .min(Comparator.comparingDouble(mob -> mob.distanceToSqr(player)))
                .orElse(null);
    }
}
