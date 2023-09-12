package com.Very.very.Projectile.BowTest;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;

public class Mybow extends BowItem {
    private float BaseDamage = 10.0F;
    public Mybow(Properties p_40660_) {
        super(p_40660_);
        Utils.BaseDamage.put(this,this.BaseDamage);
        Utils.MainHandTag.put(this,1f);
    }
    @Override
    public AbstractArrow customArrow(AbstractArrow arrow) {
        return super.customArrow(arrow);
    }

}
