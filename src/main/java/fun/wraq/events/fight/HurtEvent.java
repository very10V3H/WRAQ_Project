package fun.wraq.events.fight;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.process.func.damage.Damage;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
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
            Entity Attacker = event.getSource().getEntity();
            Entity Hurter = event.getEntity();
            if (Hurter.getClass() == Villager.class ||
                    Hurter.getClass() == WanderingTrader.class ||
                    Hurter instanceof Animal || event.getEntity().level().isClientSide
            ) event.setCanceled(true);
            else {
                if (Attacker == null && Hurter instanceof Player player) {
                    double damage = event.getAmount();
                    double damageAfterComputeShield = Shield.decreasePlayerShield(player, damage);
                    if (damageAfterComputeShield > 0) {
                        player.setHealth((float) (player.getHealth() - damageAfterComputeShield));
                    }
                    event.setCanceled(true);
                }
                if (Attacker instanceof Player) {
                    Player player = (Player) event.getSource().getEntity();
                    if (player.getName().getString().equals("very_H") && player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(MinecartItem.byId(765))) {
                        Hurter.remove(Entity.RemovalReason.KILLED);
                    }
                    if (Hurter instanceof Civil) {
                        event.setCanceled(true);
                        return;
                    }

                    if (Hurter instanceof Player hurter) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    public static void BlazeReflectDamage(Mob monster, Player player) {
        if (monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorBlaze.get())
                || monster.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorBlaze.get())) {
            Damage.manaDamageToPlayer(monster, player, player.getMaxHealth() * 0.02f);
        }
    }
}