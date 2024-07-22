package com.very.wraq.common.registry;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum ItemTier implements Tier {
    VMaterial(0, 100, 1.0f, 0.0f, 15),
    MaterialForPickaxe0(10, 100, 2.0f, 0.0f, 15),
    MaterialForPickaxe1(10, 100, 3.0f, 0.0f, 15),
    MaterialForPickaxe2(10, 100, 4.0f, 0.0f, 15),
    MaterialForPickaxe3(10, 100, 5.0f, 0.0f, 15),
    Extraction(0, 64, 1.6F, 0.0f, 15),
    MaterialForPurplePickaxe0(10, 100, 8.0f, 0.0f, 15),
    MaterialForPurplePickaxe1(10, 100, 10.0f, 0.0f, 15),
    MaterialForPurplePickaxe2(10, 100, 12.0f, 0.0f, 15),
    MaterialForPurplePickaxe3(10, 100, 15.0f, 0.0f, 15),
    XiangLiPickaxe(10, 100, 15.0f, 0.0f, 15);


    private final int level;
    private final int uses;
    private final float AttackSpeed;
    private final float damage;
    private final int enchantmentValue;

    private ItemTier(int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_) {
        this.level = p_i48458_3_;
        this.uses = p_i48458_4_;
        this.AttackSpeed = p_i48458_5_;
        this.damage = p_i48458_6_;
        this.enchantmentValue = p_i48458_7_;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.AttackSpeed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.getRepairIngredient();
    }

}
