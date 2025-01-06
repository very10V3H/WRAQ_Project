package fun.wraq.series.instance.series.lava.comsumable;

import fun.wraq.common.Compute;
import fun.wraq.process.func.particle.ParticleProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class VertigoRangeTarget extends ComsumableItem {

    public VertigoRangeTarget(Properties properties, List<Component> description) {
        super(properties, description);
    }

    @Override
    public void active(Player player) {
        Compute.getPlayerVisionConicalMobs(player, 30).forEach(mob -> {
            ParticleProvider.createBreakBlockParticle(mob, Blocks.LAVA_CAULDRON);
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 100, false, false, false));
        });
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
