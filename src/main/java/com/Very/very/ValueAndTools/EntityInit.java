package com.Very.very.ValueAndTools;

import com.Very.very.Entity.SakuraMob;
import com.Very.very.Projectile.Mana.NewArrow;
import com.Very.very.Projectile.Mana.NewArrowLife;
import com.Very.very.Projectile.Mana.NewArrowPlain;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Entity.HEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Utils.MOD_ID);

    public static final RegistryObject<EntityType<HEntity>> HETITY = ENTITY_TYPES.register("hboss",
            () -> EntityType.Builder.of(HEntity::new , MobCategory.MONSTER).sized(0.8F,3.0F).setTrackingRange(30)
                    .build(new ResourceLocation(Utils.MOD_ID, "hboss").toString()));
    public static final RegistryObject<EntityType<NewArrow>> NEW_ARROW = ENTITY_TYPES.register("new_arrow",
            () -> EntityType.Builder.<NewArrow>of(NewArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
                    .build(new ResourceLocation(Utils.MOD_ID,"new_arrow").toString())
    );
    public static final RegistryObject<EntityType<NewArrowPlain>> NEW_ARROW_PLAIN = ENTITY_TYPES.register("new_arrow_plain",
            () -> EntityType.Builder.<NewArrowPlain>of(NewArrowPlain::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
                    .build(new ResourceLocation(Utils.MOD_ID,"new_arrow_plain").toString())
    );
    public static final RegistryObject<EntityType<NewArrowLife>> NEW_ARROW_LIFE = ENTITY_TYPES.register("new_arrow_life",
            () -> EntityType.Builder.<NewArrowLife>of(NewArrowLife::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
                    .build(new ResourceLocation(Utils.MOD_ID,"new_arrow_plain").toString())
    );
    public static final RegistryObject<EntityType<SakuraMob>> SakuraMob =
            ENTITY_TYPES.register("sakuramob",
                    () -> EntityType.Builder.of(SakuraMob::new, MobCategory.CREATURE)
                                    .sized(0.5f,1)
                                    .build(new ResourceLocation(Utils.MOD_ID,"sakuramob").toString()));

}
