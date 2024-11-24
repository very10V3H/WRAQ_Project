package fun.wraq.common.registry;

import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.Boss2.Boss2;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.entities.entities.MainBoss.MainBoss;
import fun.wraq.entities.entities.SakuraMob.SakuraMob;
import fun.wraq.entities.entities.Scarecrow.Scarecrow;
import fun.wraq.entities.entities.SoraSword.SoraSwordAir;
import fun.wraq.projectiles.firework.Firework;
import fun.wraq.projectiles.mana.*;
import fun.wraq.projectiles.mana.swordair.SwordAir;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Utils.MOD_ID);

    public static final RegistryObject<EntityType<MainBoss>> HETITY = ENTITY_TYPES.register("hboss",
            () -> EntityType.Builder.of(MainBoss::new, MobCategory.MONSTER).sized(3F, 3F).setTrackingRange(30)
                    .build(new ResourceLocation(Utils.MOD_ID, "hboss").toString()));

    public static final RegistryObject<EntityType<SakuraMob>> SakuraMob =
            ENTITY_TYPES.register("sakuramob",
                    () -> EntityType.Builder.of(SakuraMob::new, MobCategory.CREATURE)
                            .sized(0.75f, 1)
                            .build(new ResourceLocation(Utils.MOD_ID, "sakuramob").toString()));
    public static final RegistryObject<EntityType<Scarecrow>> Scarecrow =
            ENTITY_TYPES.register("scarecrow",
                    () -> EntityType.Builder.of(Scarecrow::new, MobCategory.CREATURE)
                            .sized(1, 2)
                            .build(new ResourceLocation(Utils.MOD_ID, "scarecrow").toString()));

    public static final RegistryObject<EntityType<ManaArrow>> NEW_ARROW_PLAIN =
            ENTITY_TYPES.register("new_arrow_plain",
                    () -> EntityType.Builder.<ManaArrow>of(ManaArrow::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .build(new ResourceLocation(Utils.MOD_ID, "new_arrow_plain").toString()));
    public static final RegistryObject<EntityType<ManaArrow>> NEW_ARROW =
            ENTITY_TYPES.register("new_arrow",
                    () -> EntityType.Builder.<ManaArrow>of(ManaArrow::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .build(new ResourceLocation(Utils.MOD_ID, "new_arrow").toString()));
    public static final RegistryObject<EntityType<ManaArrow>> NEW_ARROW_MAGMA =
            ENTITY_TYPES.register("new_arrow_magma",
                    () -> EntityType.Builder.<ManaArrow>of(ManaArrow::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .build(new ResourceLocation(Utils.MOD_ID, "new_arrow_magma").toString()));
    public static final RegistryObject<EntityType<ManaArrow>> NEW_ARROW_WORLD =
            ENTITY_TYPES.register("new_arrow_world",
                    () -> EntityType.Builder.<ManaArrow>of(ManaArrow::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .build(new ResourceLocation(Utils.MOD_ID, "new_arrow_world").toString()));
    public static final RegistryObject<EntityType<ManaArrow>> NEW_ARROW_NETHER =
            ENTITY_TYPES.register("new_arrow_nether",
                    () -> EntityType.Builder.<ManaArrow>of(ManaArrow::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .build(new ResourceLocation(Utils.MOD_ID, "new_arrow_nether").toString()));
    public static final RegistryObject<EntityType<Firework>> FIRE_WORK =
            ENTITY_TYPES.register("fire_work",
                    () -> EntityType.Builder.<Firework>of(Firework::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .build(new ResourceLocation(Utils.MOD_ID, "fire_work").toString()));
    public static final RegistryObject<EntityType<ManaArrow>> NEW_ARROW_SEA =
            ENTITY_TYPES.register("new_arrow_sea",
                    () -> EntityType.Builder.<ManaArrow>of(ManaArrow::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .build(new ResourceLocation(Utils.MOD_ID, "new_arrow_sea").toString()));

    public static final RegistryObject<EntityType<ManaArrow>> NEW_ARROW_SAKURA =
            ENTITY_TYPES.register("new_arrow_sakura",
                    () -> EntityType.Builder.<ManaArrow>of(ManaArrow::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .build(new ResourceLocation(Utils.MOD_ID, "new_arrow_sakura").toString()));

    public static final RegistryObject<EntityType<ManaArrow>> NEW_ARROW_SNOW =
            ENTITY_TYPES.register("new_arrow_snow",
                    () -> EntityType.Builder.<ManaArrow>of(ManaArrow::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .build(new ResourceLocation(Utils.MOD_ID, "new_arrow_snow").toString()));

    public static final RegistryObject<EntityType<Meteorite>> METEORITE =
            ENTITY_TYPES.register("meteorite",
                    () -> EntityType.Builder.<Meteorite>of(Meteorite::new, MobCategory.MISC)
                            .sized(0.75f, 0.75f)
                            .build(new ResourceLocation(Utils.MOD_ID, "meteorite").toString()));

    public static final RegistryObject<EntityType<Boss2>> Boss2 =
            ENTITY_TYPES.register("boss2",
                    () -> EntityType.Builder.of(Boss2::new, MobCategory.MONSTER)
                            .sized(0.75f, 2)
                            .build(new ResourceLocation(Utils.MOD_ID, "boss2").toString()));

    public static final RegistryObject<EntityType<SwordAir>> SWORD_AIR =
            ENTITY_TYPES.register("sword_air",
                    () -> EntityType.Builder.<SwordAir>of(SwordAir::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .build(new ResourceLocation(Utils.MOD_ID, "sword_air").toString()));

    public static final RegistryObject<EntityType<ShangMengLiSwordAir>> SHANGMENGLI_SWORD_AIR =
            ENTITY_TYPES.register("shangmengli_sword_air",
                    () -> EntityType.Builder.<ShangMengLiSwordAir>of(ShangMengLiSwordAir::new, MobCategory.MISC)
                            .sized(0.75f, 0.75f)
                            .build(new ResourceLocation(Utils.MOD_ID, "shangmengli_sword_air").toString()));

    public static final RegistryObject<EntityType<SoraSwordAir>> SORA_SWORD_AIR =
            ENTITY_TYPES.register("sora_sword_air",
                    () -> EntityType.Builder.<SoraSwordAir>of(SoraSwordAir::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .build(new ResourceLocation(Utils.MOD_ID, "sora_sword_air").toString()));

    public static final RegistryObject<EntityType<SoraSwordAir>> SORA_RED_SWORD_AIR =
            ENTITY_TYPES.register("sora_red_sword_air",
                    () -> EntityType.Builder.<SoraSwordAir>of(SoraSwordAir::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .build(new ResourceLocation(Utils.MOD_ID, "sora_red_sword_air").toString()));

    public static final RegistryObject<EntityType<BlazeSword>> BLAZE_SWORD =
            ENTITY_TYPES.register("blaze_sword",
                    () -> EntityType.Builder.<BlazeSword>of(BlazeSword::new, MobCategory.MISC)
                            .sized(0.75f, 0.75f)
                            .build(new ResourceLocation(Utils.MOD_ID, "blaze_sword").toString()));

    public static final RegistryObject<EntityType<Civil>> CIVIL =
            ENTITY_TYPES.register("civil",
                    () -> EntityType.Builder.<Civil>of(Civil::new, MobCategory.MISC)
                            .sized(0.75f, 1.83f)
                            .build(new ResourceLocation(Utils.MOD_ID, "civil").toString()));

}
