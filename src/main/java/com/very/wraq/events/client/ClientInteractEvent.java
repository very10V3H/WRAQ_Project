package com.very.wraq.events.client;

import com.very.wraq.render.gui.villagerTrade.MyVillagerData;
import com.very.wraq.render.gui.villagerTrade.TradeList;
import com.very.wraq.common.Utils.StringUtils;
import net.minecraft.client.Minecraft;
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
            if (StringUtils.VillagerNameMap.containsValue(villager.getName()) || MyVillagerData.villagerNameMap.containsKey(villager.getName())) {
                event.setCanceled(true);
            }
        }
    }


}
