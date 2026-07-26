package fun.wraq.series.overworld.c1;

import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.series.WraqItem;
import fun.wraq.series.overworld.chapter1.mine.MineArmor;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NewC1Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> GRAY_SLIME_BALL = ITEMS.register("gray_slime_ball",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> ARMOR_PIECE = ITEMS.register("armor_piece",
            () -> new WraqItem(new Item.Properties()));

    public static final RegistryObject<Item> MINE_HELMET = ModItems.ITEMS.register("mine_helmet",
            () -> new MineArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> MINE_CHEST = ModItems.ITEMS.register("mine_chest",
            () -> new MineArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> MINE_LEGGINGS = ModItems.ITEMS.register("mine_leggings",
            () -> new MineArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> MINE_BOOTS = ModItems.ITEMS.register("mine_boots",
            () -> new MineArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.BOOTS));
}
