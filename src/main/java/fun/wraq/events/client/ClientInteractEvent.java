package fun.wraq.events.client;

import fun.wraq.common.util.StringUtils;
import fun.wraq.render.gui.villagerTrade.MyVillagerData;
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
            if (StringUtils.VillagerNameMap.containsValue(villager.getName())
                    || MyVillagerData.villagerNameMap.containsKey(villager.getName())) {
                event.setCanceled(true);
            }
        }
    }
}
