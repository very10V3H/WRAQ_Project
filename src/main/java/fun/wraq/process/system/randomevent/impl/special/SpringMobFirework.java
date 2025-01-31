package fun.wraq.process.system.randomevent.impl.special;

import fun.wraq.common.Compute;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.series.events.spring2024.FireworkGun;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class SpringMobFirework extends FireworkRocketEntity {

    public SpringMobFirework(EntityType<? extends FireworkRocketEntity> p_37027_, Level p_37028_) {
        super(p_37027_, p_37028_);
    }

    private Entity spawner;

    public SpringMobFirework(Entity spawner, Vec3 pos) {
        super(spawner.level(), pos.x, pos.y, pos.z, FireworkGun.getFireworkItemStack());
        this.spawner = spawner;
    }

    @Override
    protected void onHitEntity(EntityHitResult p_37071_) {
        if (!level().isClientSide && this.spawner instanceof Mob mob) {
            onHit(mob);
        }
        super.onHitEntity(p_37071_);
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37069_) {
        if (!level().isClientSide && this.spawner instanceof Mob mob) {
            onHit(mob);
        }
        super.onHitBlock(p_37069_);
    }

    private void onHit(Mob springMob) {
        Compute.getNearEntity(springMob, Player.class, 6)
                .stream().map(entity -> (Player) entity)
                .forEach(player -> {
                    Damage.causeTrueDamageToPlayer(springMob, player, player.getMaxHealth() * 0.5);
                });
    }
}
