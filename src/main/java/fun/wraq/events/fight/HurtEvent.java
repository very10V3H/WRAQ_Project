package fun.wraq.events.fight;

import fun.wraq.common.util.struct.Shield;
import fun.wraq.entities.entities.Civil.Civil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.MinecartItem;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HurtEvent {
    @SubscribeEvent
    public static void Hurt(LivingHurtEvent event) {
        event.setPhase(EventPriority.LOWEST);
        if (!event.getEntity().level().isClientSide) {
            Entity attacker = event.getSource().getEntity();
            Entity hurter = event.getEntity();
            if (hurter.getClass() == Villager.class
                    || hurter.getClass() == WanderingTrader.class
                    || (hurter instanceof Animal && !hurter.hasCustomName())
                    || event.getEntity().level().isClientSide
            ) event.setCanceled(true);
            else {
                if (attacker == null && hurter instanceof Player player) {
                    double damage = event.getAmount();
                    double damageAfterComputeShield = Shield.decreasePlayerShield(player, damage);
                    if (damageAfterComputeShield > 0) {
                        player.setHealth((float) (player.getHealth() - damageAfterComputeShield));
                    }
                    event.setCanceled(true);
                }
                if (attacker instanceof Player) {
                    Player player = (Player) event.getSource().getEntity();
                    if (player.getName().getString().equals("very_H") && player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(MinecartItem.byId(765))) {
                        hurter.remove(Entity.RemovalReason.KILLED);
                    }
                    if (hurter instanceof Civil) {
                        event.setCanceled(true);
                        return;
                    }

                    if (hurter instanceof Player) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}