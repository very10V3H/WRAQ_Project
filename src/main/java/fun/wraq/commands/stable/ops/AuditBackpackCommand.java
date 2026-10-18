package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.system.backpack.audit.AuditBackpackProvider;
import fun.wraq.process.system.backpack.audit.BackpackLockManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

/**
 * AI-Generated, 2026-10-18
 * {@code /vmd auditBackpack <uuid>}：管理员按内容 UUID 打开精妙背包原版界面。
 *
 * <p>不需要发放任何背包物品；打开期间对该 UUID 加独占锁，
 * 玩家尝试打开同一个背包会被拦下，从而保证「同一时刻只有一个写入者」。
 * 权限等级 2（OP）。
 */
public class AuditBackpackCommand implements Command<CommandSourceStack> {

    public static final AuditBackpackCommand instance = new AuditBackpackCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Te.s("仅玩家可执行该命令。"));
            return 0;
        }
        UUID contentsUuid = context.getArgument("uuid", UUID.class);

        // 先占锁再打开：避免「命令已下发、界面打开前」被其他玩家抢开的竞态。
        // 真正的窗口 id 会在 PlayerContainerEvent.Open 里绑定。
        BackpackLockManager.Lock conflict = BackpackLockManager.tryAcquire(contentsUuid, player, -1);
        if (conflict != null) {
            Compute.sendFormatMSG(player, Te.s("背包审计", ChatFormatting.AQUA),
                    Te.s("该背包正被 ", ChatFormatting.WHITE,
                            BackpackLockManager.describe(conflict, player.level().getGameTime()),
                            " 打开，请稍后再试。"));
            return 0;
        }

        AuditBackpackProvider.open(player, contentsUuid);
        return 1;
    }
}
