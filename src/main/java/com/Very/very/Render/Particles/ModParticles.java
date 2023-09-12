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
    public static final RegistryObject<SimpleParticleType> BREAKDEFENCE_MANA =
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
    public static final RegistryObject<SimpleParticleType> KAZE =
            PARTICLE_TYPES.register("kaze", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LAKE =
            PARTICLE_TYPES.register("lake", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LIGHTNINGISLAND =
            PARTICLE_TYPES.register("lightningisland", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> MANAFOREST =
            PARTICLE_TYPES.register("manaforest", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> NETHER =
            PARTICLE_TYPES.register("nether", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> PLAIN =
            PARTICLE_TYPES.register("plain", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SEA =
            PARTICLE_TYPES.register("sea", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SKY =
            PARTICLE_TYPES.register("sky", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SNOW =
            PARTICLE_TYPES.register("snow", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPIDER =
            PARTICLE_TYPES.register("spider", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> VOLCANO =
            PARTICLE_TYPES.register("volcano", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LONG_VOLCANO =
            PARTICLE_TYPES.register("long_volcano", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ENTROPY =
            PARTICLE_TYPES.register("entropy", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
