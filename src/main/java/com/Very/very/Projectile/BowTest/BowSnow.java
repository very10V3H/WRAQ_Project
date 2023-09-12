package com.Very.very.Projectile.BowTest;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BowSnow extends BowItem {

    public BowSnow(Properties p_40660_) {
        super(p_40660_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
        return super.use(p_40672_, p_40673_, p_40674_);
    }
}
