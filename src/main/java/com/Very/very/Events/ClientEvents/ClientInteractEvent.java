package com.Very.very.Events.ClientEvents;

import com.Very.very.Render.Gui.VillagerTrade.Trade;
import com.Very.very.Render.Gui.VillagerTrade.TradeList;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientInteractEvent {
    @SubscribeEvent
    public static void Trade(PlayerInteractEvent.EntityInteract event) {
        if (event.getSide().isClient() && event.getTarget() instanceof Villager villager && event.getEntity().equals(Minecraft.getInstance().player)) {
            if (TradeList.tradeContent.isEmpty() || TradeList.tradeRecipeMap.isEmpty()) TradeList.setTradeContent();
            if (StringUtils.VillagerNameMap.containsValue(villager.getName())) {
                event.setCanceled(true);
            }
        }
    }


}
