package com.very.wraq.render.mobEffects;

import com.very.wraq.render.mobEffects.PotionEffect.MyEffect;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.util.Utils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Utils.MOD_ID);
    public static final RegistryObject<MobEffect> FORESTSWORD = MOB_EFFECTS.register("forestsword",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 16733525));
    public static final RegistryObject<MobEffect> FORESTARMOR = MOB_EFFECTS.register("forestarmor",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 0));
    public static final RegistryObject<MobEffect> PLAINBOW = MOB_EFFECTS.register("plainbow",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 0));
    public static final RegistryObject<MobEffect> PLAINARMOR = MOB_EFFECTS.register("plainarmor",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 5635925));
    public static final RegistryObject<MobEffect> LAKESWORD = MOB_EFFECTS.register("lakesword",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 5592575));
    public static final RegistryObject<MobEffect> LAKEARMOR = MOB_EFFECTS.register("lakearmor",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 0));
    public static final RegistryObject<MobEffect> VOLCANOSWORD = MOB_EFFECTS.register("volcanosword",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 16777045));
    public static final RegistryObject<MobEffect> VOLCANOARMOR = MOB_EFFECTS.register("volcanoarmor",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 0));
    public static final RegistryObject<MobEffect> VOLCANOBOW = MOB_EFFECTS.register("volcanobow",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 16777045));

    public static final RegistryObject<MobEffect> LIFEMANA = MOB_EFFECTS.register("lifemana",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 16733695));
    public static final RegistryObject<MobEffect> OBSIMANA = MOB_EFFECTS.register("obsimana",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 16733695));
    public static final RegistryObject<MobEffect> SEASWORD = MOB_EFFECTS.register("seasword",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 0));
    public static final RegistryObject<MobEffect> BLACKFORESTSWORD = MOB_EFFECTS.register("blackforestsword",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 0));


    public static final RegistryObject<MobEffect> ATTACKUP = MOB_EFFECTS.register("attackup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfVolcano.getColor().getValue()));
    public static final RegistryObject<MobEffect> BREAKDefenceUP = MOB_EFFECTS.register("breakdefenceup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfMine.getColor().getValue()));
    public static final RegistryObject<MobEffect> BREAKMANADefenceUP = MOB_EFFECTS.register("breakmanadefenceup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfMana.getColor().getValue()));
    public static final RegistryObject<MobEffect> COOLDOWNUP = MOB_EFFECTS.register("cooldownup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfWater.getColor().getValue()));
    public static final RegistryObject<MobEffect> CRITDAMAGEUP = MOB_EFFECTS.register("critdamageup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfLightning.getColor().getValue()));
    public static final RegistryObject<MobEffect> CRITRATEUP = MOB_EFFECTS.register("critrateup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfSakura.getColor().getValue()));
    public static final RegistryObject<MobEffect> DefenceUP = MOB_EFFECTS.register("defenceup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfMine.getColor().getValue()));
    public static final RegistryObject<MobEffect> HEALSTEALUP = MOB_EFFECTS.register("healstealup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfNether.getColor().getValue()));
    public static final RegistryObject<MobEffect> MANADAMAGEUP = MOB_EFFECTS.register("manadamageup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfMana.getColor().getValue()));
    public static final RegistryObject<MobEffect> MANADefenceUP = MOB_EFFECTS.register("manadefenceup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfMana.getColor().getValue()));
    public static final RegistryObject<MobEffect> MANAREPLYUP = MOB_EFFECTS.register("manareplyup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfMana.getColor().getValue()));
    public static final RegistryObject<MobEffect> SPEEDUP = MOB_EFFECTS.register("speedup",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfFlexible.getColor().getValue()));
    public static final RegistryObject<MobEffect> SKYARMOR = MOB_EFFECTS.register("skyarmor",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfSky.getColor().getValue()));
    public static final RegistryObject<MobEffect> LIGHTNINGARMOR = MOB_EFFECTS.register("lightningarmor",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, 11184810));
    public static final RegistryObject<MobEffect> HEALTHRECOVER = MOB_EFFECTS.register("healreply",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfLife.getColor().getValue()));
    public static final RegistryObject<MobEffect> WARM = MOB_EFFECTS.register("warm",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfPower.getColor().getValue()));

    public static final RegistryObject<MobEffect> DAMAGE_ENHANCE = MOB_EFFECTS.register("damage_enhance",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfPower.getColor().getValue()));
    public static final RegistryObject<MobEffect> ATTACK_DAMAGE_ENHANCE = MOB_EFFECTS.register("attack_damage_enhance",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfPower.getColor().getValue()));
    public static final RegistryObject<MobEffect> MANA_DAMAGE_ENHANCE = MOB_EFFECTS.register("mana_damage_enhance",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfMana.getColor().getValue()));
    public static final RegistryObject<MobEffect> GIANT = MOB_EFFECTS.register("giant",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfHealth.getColor().getValue()));
    public static final RegistryObject<MobEffect> STONE = MOB_EFFECTS.register("stone",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfStone.getColor().getValue()));
    public static final RegistryObject<MobEffect> EX_HARVEST = MOB_EFFECTS.register("ex_harvest",
            () -> new MyEffect(MobEffectCategory.BENEFICIAL, CustomStyle.styleOfGold.getColor().getValue()));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}
