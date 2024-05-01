package com.very.wraq.valueAndTools.registry;

import com.very.wraq.blocks.blocks.*;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);
    public static final RegistryObject<Block> Sliver_Block = BLOCKS.register("sliver_block",
            ()->new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> FIRST_BLOCK = registerBlock("forging_block",
            () -> new ForgingBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()), CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> HBREWING_BLOCK = registerBlock("h_brewing_block",
            () -> new HBrewing(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()), CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> WorldSoul_Block = registerBlock("worldsoul_block",
            () -> new WorldSoulBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()), CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> Inject_Block = registerBlock("inject_block",
            () -> new InjectBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()), CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> Furnace = registerBlock("furnace",
            () -> new Furnace(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()), CreativeModeTabs.getDefaultTab());


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

}
