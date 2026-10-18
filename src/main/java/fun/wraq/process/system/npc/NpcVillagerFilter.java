/** AI-Generated, 2026-10-17 */
package fun.wraq.process.system.npc;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 原版村民过滤器：村民一旦进入世界且位置命中 {@code config/vmd_npcs.json}，
 * 就地取消其加入并确保该位置是 VMD NPC。
 * <p>
 * 为什么必须这么做（踩过的坑）：
 * <ol>
 *     <li>1.20 的实体数据是<b>懒加载</b>的，只在区块进入 ENTITY_TICKING（玩家附近 / forceload）时才读盘。
 *         服务端启动完成（{@code ServerStartedEvent}）时还没有玩家，{@code level.getAllEntities()}
 *         里根本看不到远处的村民 → 启动期「村民转换」恒为 0，只会盲目新建 NPC，导致村民与 NPC 重合。</li>
 *     <li>放到「村民加入世界」这一刻处理，就和加载时机无关了：玩家走到哪，哪里的村民被拦下并替换，
 *         不需要 forceload、不需要重启，也不需要猜加载顺序。</li>
 * </ol>
 */
@Mod.EventBusSubscriber
public class NpcVillagerFilter {

    @SubscribeEvent
    public static void onVillagerJoin(EntityJoinLevelEvent event) {
        Level level = event.getLevel();
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!(event.getEntity() instanceof Villager villager)) {
            // NPC 自身加载时也做一次主动去重（同一位置同名只保留一只）。
            // 如果本次加入的正是被判定为重复的那只，连同事件一起取消，避免它短暂进入世界。
            if (event.getEntity() instanceof fun.wraq.entities.entities.npc.VmdNpc npc) {
                NpcRegistry.dedupAround(serverLevel, npc);
                if (npc.isRemoved()) {
                    event.setCanceled(true);
                }
            }
            return;
        }
        NpcRegistry.NpcDef def = NpcRegistry.findDefAt(serverLevel, villager.position());
        if (def == null) {
            // 不是配置里的 NPC 位置：普通村民，放行
            return;
        }
        // 命中配置位置：拦下村民并确保 NPC 就位（继承村民的名字/朝向）
        event.setCanceled(true);
        villager.discard();
        NpcRegistry.ensureNpc(serverLevel, def, villager);
    }
}
