package fun.wraq.process.system.randomevent.impl.special;

import fun.wraq.process.func.damage.Damage;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class SpringMobFireBall extends LargeFireball {

    public SpringMobFireBall(EntityType<? extends LargeFireball> p_37199_, Level p_37200_) {
        super(p_37199_, p_37200_);
    }

    public SpringMobFireBall(Level p_181151_, Mob mob,
                             double p_181153_, double p_181154_, double p_181155_, int p_181156_) {
        super(p_181151_, mob, p_181153_, p_181154_, p_181155_, p_181156_);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (!level().isClientSide && getOwner() != null) {
            onHit((Mob) getOwner());
        }
        super.onHitEntity(entityHitResult);
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        if (!level().isClientSide && getOwner() != null) {
            onHit((Mob) getOwner());
        }
        super.onHitBlock(hitResult);
    }

    private void onHit(Mob springMob) {
        List<Player> playerList = this.level().getEntitiesOfClass(Player.class,
                AABB.ofSize(this.position(), 6, 6, 6));
        playerList.forEach(player -> {
            Damage.causeTrueDamageToPlayer(springMob, player, player.getMaxHealth() * 0.2);
        });
    }
}
