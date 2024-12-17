package fun.wraq.series.instance.series.warden;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber
public class AncientCityWardenSpawn {

    public static Set<Warden> set = new HashSet<>();

    @SubscribeEvent
    public static void onWardenSpawn(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Warden warden && !event.getLevel().isClientSide) {
            if (!set.contains(warden) && (!warden.hasCustomName() || MobSpawn.getMobOriginName(warden).equals("古城守卫"))) {
                MobSpawn.setMobCustomName(warden, Te.s("古城守卫", CustomStyle.styleOfWarden), 260);
                MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(warden), 260);
                MobSpawn.MobBaseAttributes.setMobBaseAttributes(warden, 5000, 360, 360, 0.4,
                        5, 0.6, 300, 25,
                        5000 * Math.pow(10, 4), 0.35);
                set.add(warden);
            }
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && Tick.get() > 19 && Tick.get() % 20 == 0
                && event.level.dimension().equals(Level.OVERWORLD)
                && event.phase.equals(TickEvent.Phase.START)) {
            set.removeIf(warden -> !warden.isAlive());
            set.forEach(warden -> {
                Compute.getNearEntity(warden, Player.class, 16)
                        .stream().map(e -> (Player) e)
                        .forEach(player -> {
                            Damage.manaDamageToPlayer(warden, player, 3500, 0.6, 300);
                            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100));
                            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
                            ParticleProvider.createLineEffectParticle(warden.level(), (int) player.distanceTo(warden) * 5,
                                    warden.getEyePosition(), player.getEyePosition(), CustomStyle.styleOfWarden);
                        });
            });
        }
    }
}
