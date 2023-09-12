package com.Very.very.Render.Effects;

import com.Very.very.Render.Effects.MainStory_I.Forest.ForestArmorEffect;
import com.Very.very.Render.Effects.MainStory_I.Forest.ForestSwordEffect;
import com.Very.very.Render.Effects.MainStory_I.Lake.LakeArmorEffect;
import com.Very.very.Render.Effects.MainStory_I.Lake.LakeSwordEffect;
import com.Very.very.Render.Effects.MainStory_I.Plain.PlainArmorEffect;
import com.Very.very.Render.Effects.MainStory_I.Plain.PlainBowEffect;
import com.Very.very.Render.Effects.MainStory_I.Volcano.VolcanoArmorEffect;
import com.Very.very.Render.Effects.MainStory_I.Volcano.VolcanoBowEffect;
import com.Very.very.Render.Effects.MainStory_I.Volcano.VolcanoSwordEffect;
import com.Very.very.Render.Effects.MainStory_II.*;
import com.Very.very.Render.Effects.PotionEffect.*;
import com.Very.very.ValueAndTools.Utils.Utils;
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
            () -> new ForestSwordEffect(MobEffectCategory.BENEFICIAL,16733525));
    public static final RegistryObject<MobEffect> FORESTARMOR = MOB_EFFECTS.register("forestarmor",
            () -> new ForestArmorEffect(MobEffectCategory.BENEFICIAL,0));
    public static final RegistryObject<MobEffect> PLAINBOW = MOB_EFFECTS.register("plainbow",
            () -> new PlainBowEffect(MobEffectCategory.BENEFICIAL,0));
    public static final RegistryObject<MobEffect> PLAINARMOR = MOB_EFFECTS.register("plainarmor",
            () -> new PlainArmorEffect(MobEffectCategory.BENEFICIAL,5635925));
    public static final RegistryObject<MobEffect> LAKESWORD = MOB_EFFECTS.register("lakesword",
            () -> new LakeSwordEffect(MobEffectCategory.BENEFICIAL,5592575));
    public static final RegistryObject<MobEffect> LAKEARMOR = MOB_EFFECTS.register("lakearmor",
            () -> new LakeArmorEffect(MobEffectCategory.BENEFICIAL,0));
    public static final RegistryObject<MobEffect> VOLCANOSWORD = MOB_EFFECTS.register("volcanosword",
            () -> new VolcanoSwordEffect(MobEffectCategory.BENEFICIAL,16777045));
    public static final RegistryObject<MobEffect> VOLCANOARMOR = MOB_EFFECTS.register("volcanoarmor",
            () -> new VolcanoArmorEffect(MobEffectCategory.BENEFICIAL,0));
    public static final RegistryObject<MobEffect> VOLCANOBOW = MOB_EFFECTS.register("volcanobow",
            () -> new VolcanoBowEffect(MobEffectCategory.BENEFICIAL,16777045));

    public static final RegistryObject<MobEffect> LIFEMANA = MOB_EFFECTS.register("lifemana",
            () -> new LifeManaArmorEffect(MobEffectCategory.BENEFICIAL,16733695));
    public static final RegistryObject<MobEffect> OBSIMANA = MOB_EFFECTS.register("obsimana",
            () -> new ObsiManaArmorEffect(MobEffectCategory.BENEFICIAL,16733695));
    public static final RegistryObject<MobEffect> SEASWORD = MOB_EFFECTS.register("seasword",
            () -> new SeaSwordEffect(MobEffectCategory.BENEFICIAL,0));
    public static final RegistryObject<MobEffect> BLACKFORESTSWORD = MOB_EFFECTS.register("blackforestsword",
            () -> new BlackForestSwordEffect(MobEffectCategory.BENEFICIAL,0));


    public static final RegistryObject<MobEffect> ATTACKUP = MOB_EFFECTS.register("attackup",
            () -> new AttackUpEffect(MobEffectCategory.BENEFICIAL,16777045));
    public static final RegistryObject<MobEffect> BREAKDEFENCEUP = MOB_EFFECTS.register("breakdefenceup",
            () -> new BreakDefenceUpEffect(MobEffectCategory.BENEFICIAL,11184810));
    public static final RegistryObject<MobEffect> BREAKMANADEFENCEUP = MOB_EFFECTS.register("breakmanadefenceup",
            () -> new BreakManaDefenceUpEffect(MobEffectCategory.BENEFICIAL,16733695));
    public static final RegistryObject<MobEffect> COOLDOWNUP = MOB_EFFECTS.register("cooldownup",
            () -> new AttackUpEffect(MobEffectCategory.BENEFICIAL,5636095));
    public static final RegistryObject<MobEffect> CRITDAMAGEUP = MOB_EFFECTS.register("critdamageup",
            () -> new CritDamageUpEffect(MobEffectCategory.BENEFICIAL,5592575));
    public static final RegistryObject<MobEffect> CRITRATEUP = MOB_EFFECTS.register("critrateup",
            () -> new CritRateUpEffect(MobEffectCategory.BENEFICIAL,16733695));
    public static final RegistryObject<MobEffect> DEFENCEUP = MOB_EFFECTS.register("defenceup",
            () -> new DefenceUpEffect(MobEffectCategory.BENEFICIAL,11184810));
    public static final RegistryObject<MobEffect> HEALSTEALUP = MOB_EFFECTS.register("healstealup",
            () -> new HealStealUpEffect(MobEffectCategory.BENEFICIAL,16733525));
    public static final RegistryObject<MobEffect> MANADAMAGEUP = MOB_EFFECTS.register("manadamageup",
            () -> new ManaDamageUpEffect(MobEffectCategory.BENEFICIAL,16733695));
    public static final RegistryObject<MobEffect> MANADEFENCEUP = MOB_EFFECTS.register("manadefenceup",
            () -> new ManaDefenceUpEffect(MobEffectCategory.BENEFICIAL,11184810));
    public static final RegistryObject<MobEffect> MANAREPLYUP = MOB_EFFECTS.register("manareplyup",
            () -> new ManaReplyUpEffect(MobEffectCategory.BENEFICIAL,16733695));
    public static final RegistryObject<MobEffect> SPEEDUP = MOB_EFFECTS.register("speedup",
            () -> new SpeedUpEffect(MobEffectCategory.BENEFICIAL,5635925));
    public static final RegistryObject<MobEffect> SKYARMOR = MOB_EFFECTS.register("skyarmor",
            () -> new SkyArmorEffect(MobEffectCategory.BENEFICIAL,5636095));
    public static final RegistryObject<MobEffect> LIGHTNINGARMOR = MOB_EFFECTS.register("lightningarmor",
            () -> new LightningIslandArmorEffect(MobEffectCategory.BENEFICIAL,11184810));
    public static final RegistryObject<MobEffect> HEALREPLY = MOB_EFFECTS.register("healreply",
            () -> new HealReplyEffect(MobEffectCategory.BENEFICIAL,16733525));

    public static void register(IEventBus eventBus)
    {
        MOB_EFFECTS.register(eventBus);
    }
}
