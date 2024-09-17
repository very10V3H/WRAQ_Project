package com.very.wraq.events.mob.moonfloor;

import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class MoonFloorEntities {
    public static @NotNull EntityType<?> common1 = BornInChaosV1ModEntities.MR_PUMPKIN.get();
    public static @NotNull EntityType<?> common2 = BornInChaosV1ModEntities.SENOR_PUMPKIN.get();
    public static @NotNull EntityType<?> common3 = BornInChaosV1ModEntities.FELSTEED.get();

    public static @NotNull EntityType<?> boss1 = BornInChaosV1ModEntities.SIR_PUMPKINHEAD_WITHOUT_HORSE.get();
    public static @NotNull EntityType<?> boss2 = BornInChaosV1ModEntities.SIR_PUMPKINHEAD.get();
    public static @NotNull EntityType<?> boss3 = BornInChaosV1ModEntities.LORD_THE_HEADLESS.get();
}
