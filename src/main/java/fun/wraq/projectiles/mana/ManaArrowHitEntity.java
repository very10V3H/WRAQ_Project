package fun.wraq.projectiles.mana;

import net.minecraft.world.entity.Entity;

public interface ManaArrowHitEntity {
    void onHit(ManaArrow manaArrow, Entity entity);
}
