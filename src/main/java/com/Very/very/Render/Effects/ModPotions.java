package com.Very.very.Render.Effects;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, Utils.MOD_ID);
    public static final RegistryObject<Potion> ATTACKUP_POTION = POTIONS.register("attackup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ATTACKUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_ATTACKUP_POTION = POTIONS.register("long_attackup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ATTACKUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_ATTACKUP_POTION = POTIONS.register("con_attackup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ATTACKUP.get(),1800,1)));
    //火山+火山
    public static final RegistryObject<Potion> BREAKDEFECEUP_POTION = POTIONS.register("breakdefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKDEFENCEUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_BREAKDEFECEUP_POTION = POTIONS.register("long_breakdefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKDEFENCEUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_BREAKDEFECEUP_POTION = POTIONS.register("con_breakdefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKDEFENCEUP.get(),1800,1)));
    //火山+森林
    public static final RegistryObject<Potion> CRTIRATEUP_POTION = POTIONS.register("critrateup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITRATEUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_CRTIRATEUP_POTION = POTIONS.register("long_critrateup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITRATEUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_CRTIRATEUP_POTION = POTIONS.register("con_critrateup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITRATEUP.get(),1800,1)));
    //冰川+火山
    public static final RegistryObject<Potion> CRITDAMAGEUP_POTION = POTIONS.register("critdamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITDAMAGEUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_CRITDAMAGEUP_POTION = POTIONS.register("long_critdamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITDAMAGEUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_CRITDAMAGEUP_POTION = POTIONS.register("con_critdamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITDAMAGEUP.get(),1800,1)));
    //火山+冰川

    public static final RegistryObject<Potion> MANADAMAGEUP_POTION = POTIONS.register("manadamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADAMAGEUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_MANADAMAGEUP_POTION = POTIONS.register("long_manadamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADAMAGEUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_MANADAMAGEUP_POTION = POTIONS.register("con_manadamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADAMAGEUP.get(),1800,1)));
    //唤魔+火山
    public static final RegistryObject<Potion> MANABREAKDEFENCEUP_POTION = POTIONS.register("manabreakdefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKMANADEFENCEUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_MANABREAKDEFENCEUP_POTION = POTIONS.register("long_manabreakdefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKMANADEFENCEUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_MANABREAKDEFENCEUP_POTION = POTIONS.register("con_manabreakdefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKMANADEFENCEUP.get(),1800,1)));
    //唤魔+森林
    public static final RegistryObject<Potion> MANAREPLYUP_POTION = POTIONS.register("manareplyup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANAREPLYUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_MANAREPLYUP_POTION = POTIONS.register("long_manareplyup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANAREPLYUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_MANAREPLYUP_POTION = POTIONS.register("con_manareplyup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANAREPLYUP.get(),1800,1)));
    //唤魔+平原
    public static final RegistryObject<Potion> COOLDOWNUP_POTION = POTIONS.register("cooldownup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.COOLDOWNUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_COOLDOWNUP_POTION = POTIONS.register("long_cooldownup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.COOLDOWNUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_COOLDOWNUP_POTION = POTIONS.register("con_cooldownup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.COOLDOWNUP.get(),1800,1)));
    //湖泊+平原

    public static final RegistryObject<Potion> HEALSTEALUP_POTION = POTIONS.register("healstealup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALSTEALUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_HEALSTEALUP_POTION = POTIONS.register("long_healstealup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALSTEALUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_HEALSTEALUP_POTION = POTIONS.register("con_healstealup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALSTEALUP.get(),1800,1)));
    //下界+火山
    public static final RegistryObject<Potion> MANADEFENCEUP_POTION = POTIONS.register("manadefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADEFENCEUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_MANADEFENCEUP_POTION = POTIONS.register("long_manadefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADEFENCEUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_MANADEFENCEUP_POTION = POTIONS.register("con_manadefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADEFENCEUP.get(),1800,1)));
    //森林+唤魔
    public static final RegistryObject<Potion> DEFENCEUP_POTION = POTIONS.register("defenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DEFENCEUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_DEFENCEUP_POTION = POTIONS.register("long_defenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DEFENCEUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_DEFENCEUP_POTION = POTIONS.register("con_defenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DEFENCEUP.get(),1800,1)));
    //森林+森林
    public static final RegistryObject<Potion> SPEEDUP_POTION = POTIONS.register("speedup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SPEEDUP.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_SPEEDUP_POTION = POTIONS.register("long_speedup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SPEEDUP.get(),6000,0)));
    public static final RegistryObject<Potion> CON_SPEEDUP_POTION = POTIONS.register("con_speedup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SPEEDUP.get(),1800,1)));
    //天空+平原
    public static final RegistryObject<Potion> HEALREPLY_POTION = POTIONS.register("healreply_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALREPLY.get(),3000,0)));
    public static final RegistryObject<Potion> LONG_HEALREPLY_POTION = POTIONS.register("long_healreply_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALREPLY.get(),6000,0)));
    public static final RegistryObject<Potion> CON_HEALREPLY_POTION = POTIONS.register("con_healreply_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALREPLY.get(),1800,1)));
    //平原+平原
    public static void register(IEventBus eventBus)
    {
        POTIONS.register(eventBus);
    }
}
