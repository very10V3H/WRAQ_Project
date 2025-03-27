package fun.wraq.series.holy.ice;

import fun.wraq.common.util.Utils;
import fun.wraq.series.WraqItem;
import fun.wraq.series.holy.HolyChest;
import fun.wraq.series.holy.HolyPieceChest;
import fun.wraq.series.holy.ice.curio.IceHolyCrest;
import fun.wraq.series.holy.ice.curio.IceHolyRune;
import fun.wraq.series.holy.ice.curio.IceHolySceptre;
import fun.wraq.series.holy.ice.curio.IceHolySword;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class IceHolyItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> PIECE_0 = ITEMS.register("ice_holy_piece_0",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.COMMON), false, true));
    public static final RegistryObject<Item> PIECE_1 = ITEMS.register("ice_holy_piece_1",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.UNCOMMON), false, true));
    public static final RegistryObject<Item> PIECE_2 = ITEMS.register("ice_holy_piece_2",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE), false, true));
    public static final RegistryObject<Item> PIECE_3 = ITEMS.register("ice_holy_piece_3",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC), false, true));

    public static final RegistryObject<Item> SWORD_ATTACK_0 = ITEMS.register("ice_holy_sword_attack_0",
            () -> new IceHolySword(0, true));
    public static final RegistryObject<Item> SWORD_ATTACK_1 = ITEMS.register("ice_holy_sword_attack_1",
            () -> new IceHolySword(1, true));
    public static final RegistryObject<Item> SWORD_ATTACK_2 = ITEMS.register("ice_holy_sword_attack_2",
            () -> new IceHolySword(2, true));
    public static final RegistryObject<Item> SWORD_ATTACK_3 = ITEMS.register("ice_holy_sword_attack_3",
            () -> new IceHolySword(3, true));

    public static final RegistryObject<Item> SWORD_MANA_0 = ITEMS.register("ice_holy_sword_mana_0",
            () -> new IceHolySword(0, false));
    public static final RegistryObject<Item> SWORD_MANA_1 = ITEMS.register("ice_holy_sword_mana_1",
            () -> new IceHolySword(1, false));
    public static final RegistryObject<Item> SWORD_MANA_2 = ITEMS.register("ice_holy_sword_mana_2",
            () -> new IceHolySword(2, false));
    public static final RegistryObject<Item> SWORD_MANA_3 = ITEMS.register("ice_holy_sword_mana_3",
            () -> new IceHolySword(3, false));

    public static final RegistryObject<Item> SCEPTRE_0 = ITEMS.register("ice_holy_sceptre_0",
            () -> new IceHolySceptre(0));
    public static final RegistryObject<Item> SCEPTRE_1 = ITEMS.register("ice_holy_sceptre_1",
            () -> new IceHolySceptre(1));
    public static final RegistryObject<Item> SCEPTRE_2 = ITEMS.register("ice_holy_sceptre_2",
            () -> new IceHolySceptre(2));
    public static final RegistryObject<Item> SCEPTRE_3 = ITEMS.register("ice_holy_sceptre_3",
            () -> new IceHolySceptre(3));

    public static final RegistryObject<Item> RUNE_0 = ITEMS.register("ice_holy_rune_0",
            () -> new IceHolyRune(0));
    public static final RegistryObject<Item> RUNE_1 = ITEMS.register("ice_holy_rune_1",
            () -> new IceHolyRune(1));
    public static final RegistryObject<Item> RUNE_2 = ITEMS.register("ice_holy_rune_2",
            () -> new IceHolyRune(2));
    public static final RegistryObject<Item> RUNE_3 = ITEMS.register("ice_holy_rune_3",
            () -> new IceHolyRune(3));

    public static final RegistryObject<Item> CREST_0 = ITEMS.register("ice_holy_crest_0",
            () -> new IceHolyCrest(0));
    public static final RegistryObject<Item> CREST_1 = ITEMS.register("ice_holy_crest_1",
            () -> new IceHolyCrest(1));
    public static final RegistryObject<Item> CREST_2 = ITEMS.register("ice_holy_crest_2",
            () -> new IceHolyCrest(2));
    public static final RegistryObject<Item> CREST_3 = ITEMS.register("ice_holy_crest_3",
            () -> new IceHolyCrest(3));

    public static final RegistryObject<Item> CHEST = ITEMS.register("ice_holy_chest",
            () -> new HolyChest(new Item.Properties().rarity(Rarity.UNCOMMON), new ArrayList<>() {{
                this.addAll(List.of(
                        SWORD_ATTACK_0.get(), SWORD_MANA_0.get(), SCEPTRE_0.get(), RUNE_0.get(), CREST_0.get()
                ));
            }}, new ArrayList<>() {{
                this.addAll(List.of(
                        SWORD_ATTACK_1.get(), SWORD_MANA_1.get(), SCEPTRE_1.get(), RUNE_1.get(), CREST_1.get()
                ));
            }}, new ArrayList<>() {{
                this.addAll(List.of(
                        SWORD_ATTACK_2.get(), SWORD_MANA_2.get(), SCEPTRE_2.get(), RUNE_2.get(), CREST_2.get()
                ));
            }}, new ArrayList<>() {{
                this.addAll(List.of(
                        SWORD_ATTACK_3.get(), SWORD_MANA_3.get(), SCEPTRE_3.get(), RUNE_3.get(), CREST_3.get()
                ));
            }}));

    public static final RegistryObject<Item> PIECE_CHEST_0 = ITEMS.register("ice_holy_piece_chest_0",
            () -> new HolyPieceChest(new Item.Properties().rarity(Rarity.COMMON), new ArrayList<>() {{
                this.addAll(List.of(
                        SWORD_ATTACK_0.get(), SWORD_MANA_0.get(), SCEPTRE_0.get(), RUNE_0.get(), CREST_0.get()
                ));
            }}));
    public static final RegistryObject<Item> PIECE_CHEST_1 = ITEMS.register("ice_holy_piece_chest_1",
            () -> new HolyPieceChest(new Item.Properties().rarity(Rarity.COMMON), new ArrayList<>() {{
                this.addAll(List.of(
                        SWORD_ATTACK_1.get(), SWORD_MANA_1.get(), SCEPTRE_1.get(), RUNE_1.get(), CREST_1.get()
                ));
            }}));
    public static final RegistryObject<Item> PIECE_CHEST_2 = ITEMS.register("ice_holy_piece_chest_2",
            () -> new HolyPieceChest(new Item.Properties().rarity(Rarity.COMMON), new ArrayList<>() {{
                this.addAll(List.of(
                        SWORD_ATTACK_2.get(), SWORD_MANA_2.get(), SCEPTRE_2.get(), RUNE_2.get(), CREST_2.get()
                ));
            }}));
    public static final RegistryObject<Item> PIECE_CHEST_3 = ITEMS.register("ice_holy_piece_chest_3",
            () -> new HolyPieceChest(new Item.Properties().rarity(Rarity.COMMON), new ArrayList<>() {{
                this.addAll(List.of(
                        SWORD_ATTACK_3.get(), SWORD_MANA_3.get(), SCEPTRE_3.get(), RUNE_3.get(), CREST_3.get()
                ));
            }}));
}
