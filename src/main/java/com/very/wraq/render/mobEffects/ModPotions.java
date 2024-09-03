package com.very.wraq.render.mobEffects;

import com.very.wraq.common.util.Utils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {

    public static class Type {
        public static String AttackUp = "attackup_potion";
        public static String LongAttackUp = "long_attackup_potion";
        public static String ConAttackUp = "con_attackup_potion";

        public static String DefencePenetrationUp = "breakdefenceup_potion";
        public static String LongDefencePenetrationUp = "long_breakdefenceup_potion";
        public static String ConDefencePenetrationUp = "con_breakdefenceup_potion";

        public static String CritRateUp = "critrateup_potion";
        public static String LongCritRateUp = "long_critrateup_potion";
        public static String ConCritRateUp = "con_critrateup_potion";

        public static String CritDamageUp = "critdamageup_potion";
        public static String LongCritDamageUp = "long_critdamageup_potion";
        public static String ConCritDamageUp = "con_critdamageup_potion";

        public static String ManaDamageUp = "manadamageup_potion";
        public static String LongManaDamageUp = "long_manadamageup_potion";
        public static String ConManaDamageUp = "con_manadamageup_potion";

        public static String ManaPenetrationUp = "breakmanadefenceup_potion";
        public static String LongManaPenetrationUp = "long_breakmanadefenceup_potion";
        public static String ConManaPenetrationUp = "con_breakmanadefenceup_potion";

        public static String CoolDownUp = "cooldownup_potion";
        public static String LongCoolDownUp = "long_cooldownup_potion";
        public static String ConCoolDownUp = "con_cooldownup_potion";

        public static String HealthStealUp = "healstealup_potion";
        public static String LongHealthStealUp = "long_healstealup_potion";
        public static String ConHealthStealUp = "con_healstealup_potion";

        public static String ManaDefenceUp = "manadefenceup_potion";
        public static String LongManaDefenceUp = "long_manadefenceup_potion";
        public static String ConManaDefenceUp = "con_manadefenceup_potion";

        public static String DefenceUp = "defenceup_potion";
        public static String LongDefenceUp = "long_defenceup_potion";
        public static String ConDefenceUp = "con_defenceup_potion";

        public static String MovementSpeedUp = "speedup_potion";
        public static String LongMovementSpeedUp = "long_speedup_potion";
        public static String ConMovementSpeedUp = "con_speedup_potion";

        public static String HealthRecoverUp = "healreply_potion";
        public static String LongHealthRecoverUp = "long_healreply_potion";
        public static String ConHealthRecoverUp = "con_healreply_potion";

        public static String ManaRecoverUp = "manareplyup_potion";
        public static String LongManaRecoverUp = "long_manareplyup_potion";
        public static String ConManaRecoverUp = "con_manareplyup_potion";


    }

    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, Utils.MOD_ID);

    public static final RegistryObject<Potion> ATTACKUP_POTION = POTIONS.register("attackup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ATTACKUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_ATTACKUP_POTION = POTIONS.register("long_attackup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ATTACKUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_ATTACKUP_POTION = POTIONS.register("con_attackup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ATTACKUP.get(), 2400, 1)));
    //火山+火山
    public static final RegistryObject<Potion> BREAKDEFECEUP_POTION = POTIONS.register("breakdefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKDefenceUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_BREAKDEFECEUP_POTION = POTIONS.register("long_breakdefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKDefenceUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_BREAKDEFECEUP_POTION = POTIONS.register("con_breakdefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKDefenceUP.get(), 2400, 1)));
    //火山+森林
    public static final RegistryObject<Potion> CRTIRATEUP_POTION = POTIONS.register("critrateup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITRATEUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_CRTIRATEUP_POTION = POTIONS.register("long_critrateup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITRATEUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_CRTIRATEUP_POTION = POTIONS.register("con_critrateup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITRATEUP.get(), 2400, 1)));
    //冰川+火山
    public static final RegistryObject<Potion> CRITDAMAGEUP_POTION = POTIONS.register("critdamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITDAMAGEUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_CRITDAMAGEUP_POTION = POTIONS.register("long_critdamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITDAMAGEUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_CRITDAMAGEUP_POTION = POTIONS.register("con_critdamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CRITDAMAGEUP.get(), 2400, 1)));
    //火山+冰川

    public static final RegistryObject<Potion> MANADAMAGEUP_POTION = POTIONS.register("manadamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADAMAGEUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_MANADAMAGEUP_POTION = POTIONS.register("long_manadamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADAMAGEUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_MANADAMAGEUP_POTION = POTIONS.register("con_manadamageup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADAMAGEUP.get(), 2400, 1)));
    //唤魔+火山
    public static final RegistryObject<Potion> MANABREAKdefenceup_potion = POTIONS.register("breakmanadefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKMANADefenceUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_MANABREAKdefenceup_potion = POTIONS.register("long_breakmanadefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKMANADefenceUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_MANABREAKdefenceup_potion = POTIONS.register("con_breakmanadefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BREAKMANADefenceUP.get(), 2400, 1)));
    //唤魔+森林
    public static final RegistryObject<Potion> MANAREPLYUP_POTION = POTIONS.register("manareplyup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANAREPLYUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_MANAREPLYUP_POTION = POTIONS.register("long_manareplyup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANAREPLYUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_MANAREPLYUP_POTION = POTIONS.register("con_manareplyup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANAREPLYUP.get(), 2400, 1)));
    //唤魔+平原
    public static final RegistryObject<Potion> COOLDOWNUP_POTION = POTIONS.register("cooldownup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.COOLDOWNUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_COOLDOWNUP_POTION = POTIONS.register("long_cooldownup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.COOLDOWNUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_COOLDOWNUP_POTION = POTIONS.register("con_cooldownup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.COOLDOWNUP.get(), 2400, 1)));
    //湖泊+平原

    public static final RegistryObject<Potion> HEALSTEALUP_POTION = POTIONS.register("healstealup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALSTEALUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_HEALSTEALUP_POTION = POTIONS.register("long_healstealup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALSTEALUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_HEALSTEALUP_POTION = POTIONS.register("con_healstealup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALSTEALUP.get(), 2400, 1)));
    //下界+火山
    public static final RegistryObject<Potion> MANAdefenceup_potion = POTIONS.register("manadefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADefenceUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_MANAdefenceup_potion = POTIONS.register("long_manadefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADefenceUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_MANAdefenceup_potion = POTIONS.register("con_manadefenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANADefenceUP.get(), 2400, 1)));
    //森林+唤魔
    public static final RegistryObject<Potion> defenceup_potion = POTIONS.register("defenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DefenceUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_defenceup_potion = POTIONS.register("long_defenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DefenceUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_defenceup_potion = POTIONS.register("con_defenceup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DefenceUP.get(), 2400, 1)));
    //森林+森林
    public static final RegistryObject<Potion> SPEEDUP_POTION = POTIONS.register("speedup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SPEEDUP.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_SPEEDUP_POTION = POTIONS.register("long_speedup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SPEEDUP.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_SPEEDUP_POTION = POTIONS.register("con_speedup_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SPEEDUP.get(), 2400, 1)));
    //天空+平原
    public static final RegistryObject<Potion> HEALREPLY_POTION = POTIONS.register("healreply_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALTHRECOVER.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_HEALREPLY_POTION = POTIONS.register("long_healreply_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALTHRECOVER.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_HEALREPLY_POTION = POTIONS.register("con_healreply_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.HEALTHRECOVER.get(), 2400, 1)));

    public static final RegistryObject<Potion> DAMAGE_ENHANCE_POTION = POTIONS.register("damage_enhance_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DAMAGE_ENHANCE.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_DAMAGE_ENHANCE_POTION = POTIONS.register("long_damage_enhance_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DAMAGE_ENHANCE.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_DAMAGE_ENHANCE_POTION = POTIONS.register("con_damage_enhance_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.DAMAGE_ENHANCE.get(), 2400, 1)));

    public static final RegistryObject<Potion> ATTACK_DAMAGE_ENHANCE_POTION = POTIONS.register("attack_damage_enhance_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ATTACK_DAMAGE_ENHANCE.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_ATTACK_DAMAGE_ENHANCE_POTION = POTIONS.register("long_attack_damage_enhance_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ATTACK_DAMAGE_ENHANCE.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_ATTACK_DAMAGE_ENHANCE_POTION = POTIONS.register("con_attack_damage_enhance_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ATTACK_DAMAGE_ENHANCE.get(), 2400, 1)));

    public static final RegistryObject<Potion> MANA_DAMAGE_ENHANCE_POTION = POTIONS.register("mana_damage_enhance_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANA_DAMAGE_ENHANCE.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_MANA_DAMAGE_ENHANCE_POTION = POTIONS.register("long_mana_damage_enhance_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANA_DAMAGE_ENHANCE.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_MANA_DAMAGE_ENHANCE_POTION = POTIONS.register("con_mana_damage_enhance_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.MANA_DAMAGE_ENHANCE.get(), 2400, 1)));

    public static final RegistryObject<Potion> GIANT_POTION = POTIONS.register("giant_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.GIANT.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_GIANT_POTION = POTIONS.register("long_giant_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.GIANT.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_GIANT_POTION = POTIONS.register("con_giant_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.GIANT.get(), 2400, 1)));

    public static final RegistryObject<Potion> STONE_POTION = POTIONS.register("stone_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.STONE.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_STONE_POTION = POTIONS.register("long_stone_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.STONE.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_STONE_POTION = POTIONS.register("con_stone_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.STONE.get(), 2400, 1)));

    public static final RegistryObject<Potion> EX_HARVEST_POTION = POTIONS.register("ex_harvest_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.EX_HARVEST.get(), 4800, 0)));
    public static final RegistryObject<Potion> LONG_EX_HARVEST_POTION = POTIONS.register("long_ex_harvest_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.EX_HARVEST.get(), 9600, 0)));
    public static final RegistryObject<Potion> CON_EX_HARVEST_POTION = POTIONS.register("con_ex_harvest_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.EX_HARVEST.get(), 2400, 1)));

    //平原+平原
    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
