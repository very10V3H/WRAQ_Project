package fun.wraq.series.instance.series.lava.comsumable;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class DeleteMaxHealth extends ComsumableItem {
    public DeleteMaxHealth(Properties properties) {
        super(properties, List.of(
                Te.s("")
        ));
    }

    @Override
    public void active(Player player) {
        Compute.getPlayerVisionConicalMobs(player, 30).forEach(mob -> {
            mob.setHealth(mob.getHealth() * 0.9f);
            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(mob.getMaxHealth() * 0.9);
            ParticleProvider.createLineParticle(player.level(), (int) mob.distanceTo(player) * 5,
                    player.getEyePosition(), mob.getEyePosition(), ParticleTypes.FLAME);
        });
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
