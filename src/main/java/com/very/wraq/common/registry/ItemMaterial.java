package com.very.wraq.common.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public enum ItemMaterial implements ArmorMaterial {
    BasicArmor1("vmaterial", 0, 0, 0, 0.0f, 0.0f, SoundEvents.ARMOR_EQUIP_IRON),
    BasicArmor2("vmaterial", 0, 0, 0, 1.0f, 1.0f, SoundEvents.ARMOR_EQUIP_IRON),
    MaterialForBoss("bossmaterial", 0, 0, 0, 1.0f, 1.0f, SoundEvents.ARMOR_EQUIP_IRON),
    PlainMaterialBoots("plainmaterial", 0, 0, 15, 0.1F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    PlainMaterialLeggings("plainmaterial", 0, 0, 15, 0.1F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    PlainMaterialChest("plainmaterial", 0, 0, 15, 0.1F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    PlainMaterialHelmet("plainmaterial", 0, 0, 15, 0.1F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    Forest("forestmaterial", 0, 0, 15, 0.2F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    LakeMaterialBoots("lakematerial", 0, 0, 15, 0.3F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    LakeMaterialLeggings("lakematerial", 0, 0, 15, 0.3F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    LakeMaterialChest("lakematerial", 0, 0, 15, 0.3F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    Lake("lakematerial", 0, 0, 15, 0.3F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    VolcanoMaterialBoots("volcanomaterial", 0, 0, 15, 0.4F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    VolcanoMaterialLeggings("volcanomaterial", 0, 0, 15, 0.4F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    VolcanoMaterialChest("volcanomaterial", 0, 0, 15, 0.4F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    VolcanoMaterialHelmet("volcanomaterial", 0, 0, 15, 0.4F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    LifeMana("lifemana", 0, 0, 15, 0.4F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    ObsiMana("obsimana", 0, 0, 15, 0.4F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    NetherAll("netherite", 0, 0, 0, 1, 1, SoundEvents.ARMOR_EQUIP_NETHERITE),
    IslandMaterial("iron", 0, 0, 15, 0.4F, 0.0f, SoundEvents.ARMOR_EQUIP_IRON),
    SkyMaterial("skymaterial", 0, 0, 15, 0.4F, 0.0f, SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorPF("plainmaterial", 0, 0, 15, 0.2F, 0, SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorSV("volcanomaterial", 0, 0, 15, 0.3F, 0, SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorSL("lakematerial", 0, 0, 15, 0.3F, 0, SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorKaze("kazematerial", 0, 0, 15, 0.3F, 0, SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorS("smaterial", 0, 0, 15, 0.3F, 0, SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorIS("ismaterial", 0, 0, 15, 0.3f, 0, SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorIL("ilmaterial", 0, 0, 15, 0.3f, 0, SoundEvents.ARMOR_EQUIP_IRON),
    Golden("gold", 0, 0, 15, 0.4F, 0.0f, SoundEvents.ARMOR_EQUIP_GOLD),
    PurpleIron("purpleiron", 0, 0, 15, 0.3f, 0, SoundEvents.ARMOR_EQUIP_IRON),
    ArmorLeather("leather", 0, 0, 15, 0.3f, 0, SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorIce("diamond", 0, 0, 15, 0.3f, 0, SoundEvents.ARMOR_EQUIP_DIAMOND),
    ArmorLifeE("life_e", 0, 0, 15, 0.3f, 0, SoundEvents.ARMOR_EQUIP_DIAMOND),
    ArmorObsiE("obsi_e", 0, 0, 15, 0.3f, 0, SoundEvents.ARMOR_EQUIP_DIAMOND),
    NetherMana("nether_mana", 0, 0, 15, 0.3f, 0, SoundEvents.ARMOR_EQUIP_DIAMOND),
    Spring("gold", 0, 0, 15, 0.3f, 0, SoundEvents.ARMOR_EQUIP_GOLD),
    BloodMana("blood", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_LEATHER),
    AIR("air", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_LEATHER),
    Devil("devil", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_GOLD),
    Moon("moon", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_DIAMOND),

    LifeElement("life_element", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_DIAMOND),
    WaterElement("water_element", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_DIAMOND),
    FireElement("fire_element", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_DIAMOND),
    StoneElement("stone_element", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_DIAMOND),
    IceElement("ice_element", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_DIAMOND),
    LightningElement("lightening_element", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_DIAMOND),
    WindElement("wind_element", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_DIAMOND),

    Castle("castle", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_NETHERITE),

    MOONTAIN("moontain", 0, 0, 15, 1, 1, SoundEvents.ARMOR_EQUIP_DIAMOND);

    private final int Durability;
    private final int Defense;
    private final int EnchantmenValue;
    private final float KnockbackResistance;
    private final float Toughness;
    private final String name;
    private final SoundEvent sound;

    private ItemMaterial(String name, int IMnum1, int IMnum2, int IMnum3, float IMnum4, float IMnum5, SoundEvent sound) {
        this.Durability = IMnum1;
        this.Defense = IMnum2;
        this.EnchantmenValue = IMnum3;
        this.KnockbackResistance = IMnum4;
        this.Toughness = IMnum5;
        this.name = name;
        this.sound = sound;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type p_266807_) {
        return this.Durability;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type p_267168_) {
        return this.Defense;
    }

    @Override
    public int getEnchantmentValue() {
        return this.EnchantmenValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.Toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.KnockbackResistance;
    }
}
