package com.Very.very.Render.Particles;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Utils.MOD_ID);

    public static final RegistryObject<SimpleParticleType> FIRST_PARTICLE =
            PARTICLE_TYPES.register("first_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BREAKDefence_MANA =
            PARTICLE_TYPES.register("breakdefencemana", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> DAMAGE_MANA =
            PARTICLE_TYPES.register("damagemana", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> EFFECT_MANA =
            PARTICLE_TYPES.register("effectmana", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GATHER_MANA =
            PARTICLE_TYPES.register("gathermana", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> KAZE_MANA =
            PARTICLE_TYPES.register("kazemana", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LIGHTNING_MANA =
            PARTICLE_TYPES.register("lightningmana", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> RANGE_MANA =
            PARTICLE_TYPES.register("rangemana", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SNOW_MANA =
            PARTICLE_TYPES.register("snowmana", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> END_PARTICLE =
            PARTICLE_TYPES.register("endparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BLACKFOREST =
            PARTICLE_TYPES.register("blackforest", () -> new SimpleParticleType(true));


    public static final RegistryObject<SimpleParticleType> BLACKFOREST_RECALL =
            PARTICLE_TYPES.register("blackforestrecall", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FOREST =
            PARTICLE_TYPES.register("forest", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_FOREST =
            PARTICLE_TYPES.register("long_forest", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> KAZE =
            PARTICLE_TYPES.register("kaze", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_KAZE =
            PARTICLE_TYPES.register("long_kaze", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> LAKE =
            PARTICLE_TYPES.register("lake", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_LAKE =
            PARTICLE_TYPES.register("long_lake", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> LIGHTNINGISLAND =
            PARTICLE_TYPES.register("lightningisland", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_LIGHTNINGISLAND =
            PARTICLE_TYPES.register("long_lightningisland", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> MANAFOREST =
            PARTICLE_TYPES.register("manaforest", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_MANAFOREST =
            PARTICLE_TYPES.register("long_manaforest", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> NETHER =
            PARTICLE_TYPES.register("nether", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> PLAIN =
            PARTICLE_TYPES.register("plain", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_PLAIN =
            PARTICLE_TYPES.register("long_plain", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SEA =
            PARTICLE_TYPES.register("sea", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_SEA =
            PARTICLE_TYPES.register("long_sea", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SKY =
            PARTICLE_TYPES.register("sky", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_SKY =
            PARTICLE_TYPES.register("long_sky", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SNOW =
            PARTICLE_TYPES.register("snow", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_SNOW =
            PARTICLE_TYPES.register("long_snow", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SPIDER =
            PARTICLE_TYPES.register("spider", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_SPIDER =
            PARTICLE_TYPES.register("long_spider", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> VOLCANO =
            PARTICLE_TYPES.register("volcano", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_VOLCANO =
            PARTICLE_TYPES.register("long_volcano", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> ENTROPY =
            PARTICLE_TYPES.register("entropy", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> LONG_ENTROPY =
            PARTICLE_TYPES.register("long_entropy", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> BLADE0 =
            PARTICLE_TYPES.register("blade0", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> BLADE1 =
            PARTICLE_TYPES.register("blade1", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> TEST_CIRCLE =
            PARTICLE_TYPES.register("test_circle", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> BREAK_Defence =
            PARTICLE_TYPES.register("break_defence", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> MANA_BREAK_Defence =
            PARTICLE_TYPES.register("mana_break_defence", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SLOW_DOWN =
            PARTICLE_TYPES.register("slow_down", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> DAMAGE_DECREASE =
            PARTICLE_TYPES.register("damage_decrease", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> WORLD =
            PARTICLE_TYPES.register("world", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> VOLCANO_TP =
            PARTICLE_TYPES.register("volcano_tp", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SPRING =
            PARTICLE_TYPES.register("spring", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> LONG_SPRING =
            PARTICLE_TYPES.register("long_spring", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> RED_SPELL =
            PARTICLE_TYPES.register("red_spell", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> LONG_RED_SPELL =
            PARTICLE_TYPES.register("long_red_spell", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> WHITE_SPELL =
            PARTICLE_TYPES.register("white_spell", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> YSR =
            PARTICLE_TYPES.register("ysr", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> YSR1 =
            PARTICLE_TYPES.register("ysr1", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> LiuliSpell =
            PARTICLE_TYPES.register("liuli_spell", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> BIG =
            PARTICLE_TYPES.register("big", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> PurpleIronBig =
            PARTICLE_TYPES.register("purple_iron_big", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> PurpleIronOneTick =
            PARTICLE_TYPES.register("purple_iron_one_tick", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
