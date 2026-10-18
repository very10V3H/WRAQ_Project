package fun.wraq.events.core;

import fun.wraq.entities.entities.npc.VmdNpc;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.unSorted.VillagerTradeScreenS2CPacket;
import fun.wraq.process.system.npc.NpcIdResolver;
import fun.wraq.process.system.npc.NpcIds;
import fun.wraq.process.system.npc.NpcInteractionRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 实体右键交互入口。
 * <p>
 * AI-Generated, 2026-10-17 改造：不再按实体类型（原版村民 / CustomNPCs 实体）分两套分支，
 * 也不再按「显示名 switch」路由。现在统一走
 * {@link NpcIdResolver#resolve(Entity)} → {@link NpcInteractionRegistry#handle}：
 * <ul>
 *     <li>新实体 {@link VmdNpc} 自带 dialogId；</li>
 *     <li>原版村民由 persistentData 里的 {@code vmd.npcId} 提供，
 *         没有补写时退回「显示名 → id」映射，因此旧世界的村民无需迁移。</li>
 * </ul>
 * 交易商人暂时仍按显示名回退（{@code TradeList.tradeContent} 以显示名为键，
 * 该系统的 id 化属于独立重构，不在本阶段范围）。
 */
@Mod.EventBusSubscriber
public class VillagerEvents {

    @SubscribeEvent
    public static void Trade(PlayerInteractEvent.EntityInteract event) {
        if (!event.getSide().isServer()) {
            return;
        }
        Player player = event.getEntity();
        Entity target = event.getTarget();

        // 1. 先按 dialogId 路由（VmdNpc 与原版村民共用同一条路径）
        String dialogId = NpcIdResolver.resolve(target);
        if (dialogId != null && NpcInteractionRegistry.handle(dialogId, player, target)) {
            event.setCanceled(true);
            return;
        }

        // 2. NPC 类实体：一律拦截原生交互（村民交易界面 / 手持物品被顺带使用）
        if (target instanceof Villager || target instanceof VmdNpc) {
            event.setCanceled(true);
            // 交易商人回退：仍按显示名匹配（范围边界，见类注释）
            String displayName = NpcIdResolver.displayNameOf(target);
            if (NpcIds.isMerchantName(displayName)) {
                ModNetworking.sendToClient(new VillagerTradeScreenS2CPacket(displayName), (ServerPlayer) player);
            }
            return;
        }

        // 3. 容器类载具：保持原有拦截
        if (target instanceof MinecartChest || target instanceof ChestBoat || target instanceof MinecartHopper) {
            event.setCanceled(true);
        }
    }
}
