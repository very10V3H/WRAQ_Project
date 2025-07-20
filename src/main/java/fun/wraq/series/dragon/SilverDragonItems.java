package fun.wraq.series.dragon;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.lottery.NewLotteries;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class SilverDragonItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> SILVER_DRAGON_WEAPON_PIECE
            = ITEMS.register("silver_dragon_weapon_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), true, true));

    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_SWORD
            = ITEMS.register("silver_dragon_blood_sword",
            () -> new SilverDragonBloodSword(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 0));
    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_BOW
            = ITEMS.register("silver_dragon_blood_bow",
            () -> new SilverDragonBloodBow(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 0));
    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_SCEPTRE
            = ITEMS.register("silver_dragon_blood_sceptre",
            () -> new SilverDragonBloodSceptre(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 0));

    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_SWORD_1
            = ITEMS.register("silver_dragon_blood_sword_1",
            () -> new SilverDragonBloodSword(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 1));
    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_BOW_1
            = ITEMS.register("silver_dragon_blood_bow_1",
            () -> new SilverDragonBloodBow(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 1));
    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_SCEPTRE_1
            = ITEMS.register("silver_dragon_blood_sceptre_1",
            () -> new SilverDragonBloodSceptre(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 1));

    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_SWORD_2
            = ITEMS.register("silver_dragon_blood_sword_2",
            () -> new SilverDragonBloodSword(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 2));
    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_BOW_2
            = ITEMS.register("silver_dragon_blood_bow_2",
            () -> new SilverDragonBloodBow(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 2));
    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_SCEPTRE_2
            = ITEMS.register("silver_dragon_blood_sceptre_2",
            () -> new SilverDragonBloodSceptre(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 2));

    public static final RegistryObject<Item> SILVER_DRAGON_SWORD_LOTTERY
            = ITEMS.register("silver_dragon_sword_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY),
                    new ArrayList<>() {{
                        List<NewLotteries.Loot> loots = List.of(
                                new NewLotteries.Loot(new ItemStack(SILVER_DRAGON_BLOOD_SWORD.get()), 0.005),
                                new NewLotteries.Loot(new ItemStack(SILVER_DRAGON_WEAPON_PIECE.get(), 2), 0.005),
                                new NewLotteries.Loot(new ItemStack(ModItems.WORLD_FORGE_STONE.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.FORGE_ENHANCE_3.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.EQUIP_PIECE_5.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.KILL_PAPER_LOOT.get(), 4), 0.2),
                                new NewLotteries.Loot(new ItemStack(ModItems.COMPLETE_GEM.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.REPUTATION_MEDAL.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.GOLD_COIN_BAG.get(), 4), 0.1)
                        );
                        addAll(loots);
                    }}));

    public static final RegistryObject<Item> SILVER_DRAGON_BOW_LOTTERY
            = ITEMS.register("silver_dragon_bow_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY),
                    new ArrayList<>() {{
                        List<NewLotteries.Loot> loots = List.of(
                                new NewLotteries.Loot(new ItemStack(SILVER_DRAGON_BLOOD_BOW.get()), 0.005),
                                new NewLotteries.Loot(new ItemStack(SILVER_DRAGON_WEAPON_PIECE.get(), 2), 0.005),
                                new NewLotteries.Loot(new ItemStack(ModItems.WORLD_FORGE_STONE.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.FORGE_ENHANCE_3.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.EQUIP_PIECE_5.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.KILL_PAPER_LOOT.get(), 4), 0.2),
                                new NewLotteries.Loot(new ItemStack(ModItems.COMPLETE_GEM.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.REPUTATION_MEDAL.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.GOLD_COIN_BAG.get(), 4), 0.1)
                        );
                        addAll(loots);
                    }}));

    public static final RegistryObject<Item> SILVER_DRAGON_SCEPTRE_LOTTERY
            = ITEMS.register("silver_dragon_sceptre_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY),
                    new ArrayList<>() {{
                        List<NewLotteries.Loot> loots = List.of(
                                new NewLotteries.Loot(new ItemStack(SILVER_DRAGON_BLOOD_SCEPTRE.get()), 0.005),
                                new NewLotteries.Loot(new ItemStack(SILVER_DRAGON_WEAPON_PIECE.get(), 2), 0.005),
                                new NewLotteries.Loot(new ItemStack(ModItems.WORLD_FORGE_STONE.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.FORGE_ENHANCE_3.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.EQUIP_PIECE_5.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.KILL_PAPER_LOOT.get(), 4), 0.2),
                                new NewLotteries.Loot(new ItemStack(ModItems.COMPLETE_GEM.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.REPUTATION_MEDAL.get()), 0.1),
                                new NewLotteries.Loot(new ItemStack(ModItems.GOLD_COIN_BAG.get(), 4), 0.1)
                        );
                        addAll(loots);
                    }}));
}
