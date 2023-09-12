package com.Very.very.ValueAndTools.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
public enum ItemMaterial implements ArmorMaterial {
    VMaterialForArmor("vmaterial",0,0,0,0.0F,0.0F, SoundEvents.ARMOR_EQUIP_IRON),
    MaterialForArmor2("vmaterial",0,0,0,1.0F,1.0F,SoundEvents.ARMOR_EQUIP_IRON),
    MaterialForBoss("bossmaterial",0,0,0,1.0F,1.0F,SoundEvents.ARMOR_EQUIP_IRON),
    PlainMaterialBoots("plainmaterial",0,0,15,0.1F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    PlainMaterialLeggings("plainmaterial",0,0,15,0.1F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    PlainMaterialChest("plainmaterial",0,0,15,0.1F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    PlainMaterialHelmet("plainmaterial",0,0,15,0.1F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    ForestMaterialBoots("forestmaterial",0,0,15,0.2F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    ForestMaterialLeggings("forestmaterial",0,0,15,0.2F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    ForestMaterialChest("forestmaterial",0,0,15,0.2F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    ForestMaterialHelmet("forestmaterial",0,0,15,0.2F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    LakeMaterialBoots("lakematerial",0,0,15,0.3F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    LakeMaterialLeggings("lakematerial",0,0,15,0.3F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    LakeMaterialChest("lakematerial",0,0,15,0.3F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    LakeMaterialHelmet("lakematerial",0,0,15,0.3F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    VolcanoMaterialBoots("volcanomaterial",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    VolcanoMaterialLeggings("volcanomaterial",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    VolcanoMaterialChest("volcanomaterial",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    VolcanoMaterialHelmet("volcanomaterial",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    LifeManaBoots("lifemana",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    LifeManaLeggings("lifemana",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    LifeManaChest("lifemana",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    LifeManaHelmet("lifemana",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    ObsiManaBoots("obsimana",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    ObsiManaLeggings("obsimana",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    ObsiManaChest("obsimana",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    ObsiManaHelmet("obsimana",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    NetherAll("netherite",0,0,0,1,1,SoundEvents.ARMOR_EQUIP_NETHERITE),
    IslandMaterial("iron",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_IRON),
    SkyMaterrial("skymaterial",0,0,15,0.4F,0.0F,SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorPF("plainmaterial",0,0,15,0.2F,0,SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorSV("volcanomaterial",0,0,15,0.3F,0,SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorSL("lakematerial",0,0,15,0.3F,0,SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorKaze("kazematerial",0,0,15,0.3F,0,SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorS("smaterial",0,0,15,0.3F,0,SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorIS("ismaterial",0,0,15,0.3f,0,SoundEvents.ARMOR_EQUIP_LEATHER),
    ArmorIL("ilmaterial",0,0,15,0.3f,0,SoundEvents.ARMOR_EQUIP_IRON);


    private final int Durability;
    private final int Defense;
    private final int EnchantmenValue;
    private final float KnockbackResistance;
    private final float Toughness;
    private final String name;
    private final SoundEvent sound;
    private ItemMaterial(String name,int IMnum1,int IMnum2,int IMnum3,float IMnum4,float IMnum5,SoundEvent sound)
    {
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
