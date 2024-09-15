package com.very.wraq.blocks.entity;

import com.very.wraq.common.registry.ModBlocks;
import com.very.wraq.common.util.Utils;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Utils.MOD_ID);

    public static final RegistryObject<BlockEntityType<ForgingBlockEntity>> FIRST_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("forging_block", () ->
                    BlockEntityType.Builder.of(ForgingBlockEntity::new,
                            ModBlocks.FORGING_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<HBrewingEntity>> HBrewing_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("h_brewing_block", () ->
                    BlockEntityType.Builder.of(HBrewingEntity::new,
                            ModBlocks.BREWING_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<InjectBlockEntity>> Inject_Block_Entity =
            BLOCK_ENTITIES.register("inject_block", () ->
                    BlockEntityType.Builder.of(InjectBlockEntity::new,
                            ModBlocks.INJECT_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<FurnaceEntity>> Furnace =
            BLOCK_ENTITIES.register("furnace", () ->
                    BlockEntityType.Builder.of(FurnaceEntity::new,
                            ModBlocks.Furnace.get()).build(null));

    public static void Register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }


}
