package fun.wraq.common.registry;

import fun.wraq.blocks.blocks.WorldSoulBlock;
import fun.wraq.blocks.blocks.brew.HBrewing;
import fun.wraq.blocks.blocks.forge.ForgingBlock;
import fun.wraq.blocks.blocks.furnace.Furnace;
import fun.wraq.blocks.blocks.inject.InjectBlock;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.ore.OreItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);

    public static final RegistryObject<Block> FORGING_BLOCK = registerBlock("forging_block",
            () -> new ForgingBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()), CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> BREWING_BLOCK = registerBlock("h_brewing_block",
            () -> new HBrewing(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()), CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> WORLDSOUL_BLOCK = registerBlock("worldsoul_block",
            () -> new WorldSoulBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()), CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> INJECT_BLOCK = registerBlock("inject_block",
            () -> new InjectBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()), CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> FURNACE = registerBlock("furnace",
            () -> new Furnace(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()), CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> WRAQ_ORE_1 = registerBlock("wraq_ore_1",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).destroyTime(3).sound(SoundType.STONE)),
            CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> WRAQ_ORE_2 = registerBlock("wraq_ore_2",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).destroyTime(6).sound(SoundType.STONE)),
            CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> WRAQ_ORE_3 = registerBlock("wraq_ore_3",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).destroyTime(12).sound(SoundType.STONE)),
            CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> WRAQ_ORE_4 = registerBlock("wraq_ore_4",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).destroyTime(24).sound(SoundType.STONE)),
            CreativeModeTabs.getDefaultTab());

    public static final RegistryObject<Block> MOONTAIN_ORE = registerBlock("moontain_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).destroyTime(12).sound(SoundType.STONE)),
            CreativeModeTabs.getDefaultTab());

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block,
                                                            CreativeModeTab tab) {
        OreItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

}
