package com.very.wraq.render.mobEffects.MainStory_I.Lake;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class LakeSwordEffect extends MobEffect {
    public LakeSwordEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
