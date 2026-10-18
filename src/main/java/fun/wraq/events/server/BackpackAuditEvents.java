package fun.wraq.events.server;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.system.backpack.audit.AuditBackpackProvider;
import fun.wraq.process.system.backpack.audit.BackpackLockManager;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;

import java.util.UUID;

/**
 * AI-Generated, 2026-10-18
 * 精妙背包独占锁的事件层。
 *
 * <p>三层防护：
 * <ol>
 *     <li>预防：右键手持的背包时若已被别人占用，直接取消交互（不会打开 GUI，
 *     也不会写 SB 的访问日志）；</li>
 *     <li>兜底：{@link PlayerContainerEvent.Open} 里发现同一 UUID 已被别人占用，
 *     立刻 {@code closeContainer()}（同 tick 关闭，客户端无可交互窗口）；</li>
 *     <li>释放：关闭菜单、玩家登出、每 20 tick 校验。</li>
 * </ol>
 *
 * <p>Forge 会在 {@code ServerPlayer#openMenu} 与 {@code NetworkHooks#openScreen}
 * 中同步 post 这两个容器事件，因此玩家的右键路径和我们的管理员命令路径都能覆盖。
 */
@Mod.EventBusSubscriber
public class BackpackAuditEvents {

    @SubscribeEvent
    public static void onContainerOpen(PlayerContainerEvent.Open event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        UUID contentsUuid = AuditBackpackProvider.contentsUuidOf(event.getContainer(), player);
        if (contentsUuid == null) {
            return;
        }
        int menuId = event.getContainer().containerId;
        BackpackLockManager.Lock conflict = BackpackLockManager.tryAcquire(contentsUuid, player, menuId);
        if (conflict == null) {
            return;
        }
        // 通知客户端关掉界面（服务端 closeContainer 本身不会发关闭包），再真正关闭容器
        player.connection.send(new net.minecraft.network.protocol.game.ClientboundContainerClosePacket(menuId));
        player.closeContainer();
        Compute.sendFormatMSG(player, Te.s("背包审计", ChatFormatting.AQUA),
                Te.s("该背包正被 ", ChatFormatting.WHITE,
                        BackpackLockManager.describe(conflict, player.level().getGameTime()),
                        " 打开，请稍后再试。"));
    }

    @SubscribeEvent
    public static void onContainerClose(PlayerContainerEvent.Close event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        UUID contentsUuid = AuditBackpackProvider.contentsUuidOf(event.getContainer(), player);
        if (contentsUuid == null) {
            return;
        }
        BackpackLockManager.release(contentsUuid, player.getUUID(), event.getContainer().containerId);
    }

    @SubscribeEvent
    public static void onLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        BackpackLockManager.releaseAll(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        if (event.getServer() == null || event.getServer().getTickCount() % 20 != 0) {
            return;
        }
        long gameTime = event.getServer().overworld().getGameTime();
        AuditBackpackProvider.tick(gameTime);
        BackpackLockManager.validate(event.getServer(), gameTime);
    }

    /** 预防层：右键使用被占用的背包物品时不打开 GUI */
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        cancelIfLocked(event, event.getItemStack(), event.getLevel() != null && event.getLevel().isClientSide);
    }

    /** 预防层：右键放置被占用的背包方块时也拦下 */
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        cancelIfLocked(event, event.getItemStack(), event.getLevel() != null && event.getLevel().isClientSide);
    }

    private static void cancelIfLocked(PlayerInteractEvent event, ItemStack stack, boolean clientSide) {
        if (clientSide || !(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (!(stack.getItem() instanceof BackpackItem)) {
            return;
        }
        UUID contentsUuid = new BackpackWrapper(stack).getContentsUuid().orElse(null);
        if (contentsUuid == null || !BackpackLockManager.isLockedByOther(contentsUuid, player.getUUID())) {
            return;
        }
        BackpackLockManager.Lock lock = BackpackLockManager.get(contentsUuid);
        event.setCanceled(true);
        event.setCancellationResult(net.minecraft.world.InteractionResult.FAIL);
        Compute.sendFormatMSG(player, Te.s("背包审计", ChatFormatting.AQUA),
                Te.s("该背包正被 ", ChatFormatting.WHITE,
                        BackpackLockManager.describe(lock, player.level().getGameTime()),
                        " 打开，请稍后再试。"));
    }
}
