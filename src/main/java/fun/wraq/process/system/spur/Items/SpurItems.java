package fun.wraq.process.system.spur.Items;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.spur.Items.crop.CropCharm;
import fun.wraq.process.system.spur.Items.crop.INaRiBow;
import fun.wraq.process.system.spur.Items.log.LogCharm;
import fun.wraq.process.system.spur.Items.mine.MineCharm;
import fun.wraq.process.system.spur.Items.mine.SilverDragonAssassinPickaxe;
import fun.wraq.process.system.spur.Items.sea.DeepSeaLegendary;
import fun.wraq.process.system.spur.Items.sea.SeaCharm;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SimpleFoiledItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class SpurItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> MINE_CHARM_0 = ITEMS.register("mine_charm0",
            () -> new MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 0));
    public static final RegistryObject<Item> MINE_CHARM_1 = ITEMS.register("mine_charm1",
            () -> new MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 1));
    public static final RegistryObject<Item> MINE_CHARM_2 = ITEMS.register("mine_charm2",
            () -> new MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 2));
    public static final RegistryObject<Item> MINE_CHARM_3 = ITEMS.register("mine_charm3",
            () -> new MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 3));
    public static final RegistryObject<Item> MINE_CHARM_4 = ITEMS.register("mine_charm4",
            () -> new MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 4));
    public static final RegistryObject<Item> MINE_CHARM_5 = ITEMS.register("mine_charm5",
            () -> new MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 5));
    public static final RegistryObject<Item> MINE_CHARM_6 = ITEMS.register("mine_charm6",
            () -> new MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 6));

    public static final RegistryObject<Item> SEA_CHARM_0 = ITEMS.register("sea_charm0",
            () -> new SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 0));
    public static final RegistryObject<Item> SEA_CHARM_1 = ITEMS.register("sea_charm1",
            () -> new SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 1));
    public static final RegistryObject<Item> SEA_CHARM_2 = ITEMS.register("sea_charm2",
            () -> new SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 2));
    public static final RegistryObject<Item> SEA_CHARM_3 = ITEMS.register("sea_charm3",
            () -> new SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 3));
    public static final RegistryObject<Item> SEA_CHARM_4 = ITEMS.register("sea_charm4",
            () -> new SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 4));
    public static final RegistryObject<Item> SEA_CHARM_5 = ITEMS.register("sea_charm5",
            () -> new SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 5));
    public static final RegistryObject<Item> SEA_CHARM_6 = ITEMS.register("sea_charm6",
            () -> new SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 6));

    public static final RegistryObject<Item> CROP_CHARM_0 = ITEMS.register("crop_charm0",
            () -> new CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 0));
    public static final RegistryObject<Item> CROP_CHARM_1 = ITEMS.register("crop_charm1",
            () -> new CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 1));
    public static final RegistryObject<Item> CROP_CHARM_2 = ITEMS.register("crop_charm2",
            () -> new CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 2));
    public static final RegistryObject<Item> CROP_CHARM_3 = ITEMS.register("crop_charm3",
            () -> new CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 3));
    public static final RegistryObject<Item> CROP_CHARM_4 = ITEMS.register("crop_charm4",
            () -> new CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 4));
    public static final RegistryObject<Item> CROP_CHARM_5 = ITEMS.register("crop_charm5",
            () -> new CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 5));
    public static final RegistryObject<Item> CROP_CHARM_6 = ITEMS.register("crop_charm6",
            () -> new CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 6));

    public static final RegistryObject<Item> LOG_CHARM_0 = ITEMS.register("log_charm0",
            () -> new LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 0));
    public static final RegistryObject<Item> LOG_CHARM_1 = ITEMS.register("log_charm1",
            () -> new LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 1));
    public static final RegistryObject<Item> LOG_CHARM_2 = ITEMS.register("log_charm2",
            () -> new LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 2));
    public static final RegistryObject<Item> LOG_CHARM_3 = ITEMS.register("log_charm3",
            () -> new LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 3));
    public static final RegistryObject<Item> LOG_CHARM_4 = ITEMS.register("log_charm4",
            () -> new LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 4));
    public static final RegistryObject<Item> LOG_CHARM_5 = ITEMS.register("log_charm5",
            () -> new LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 5));
    public static final RegistryObject<Item> LOG_CHARM_6 = ITEMS.register("log_charm6",
            () -> new LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 6));

    public static final RegistryObject<Item> MINE_PIECE = ITEMS.register("mine_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Mine), false, false, List.of(
                    Te.s("通过", "挖掘矿石", CustomStyle.styleOfStone, "概率获取")
            )));
    public static final RegistryObject<Item> MINE_PIECE_1 = ITEMS.register("mine_piece1",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.MineBold)));

    public static final RegistryObject<Item> SEA_PIECE = ITEMS.register("sea_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Sea), false, false, List.of(
                    Te.s("通过", "钓鱼", CustomStyle.styleOfSea, "概率获取")
            )));
    public static final RegistryObject<Item> SEA_PIECE_1 = ITEMS.register("sea_piece1",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.SeaBold)));

    public static final RegistryObject<Item> CROP_PIECE = ITEMS.register("crop_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Gold), false, false, List.of(
                    Te.s("通过", "采集农作物", CustomStyle.styleOfField, "概率获取")
            )));
    public static final RegistryObject<Item> CROP_PIECE_1 = ITEMS.register("crop_piece1",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> LOG_PIECE = ITEMS.register("log_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Husk), false, false, List.of(
                    Te.s("通过", "砍伐树木", CustomStyle.styleOfHusk, "概率获取")
            )));
    public static final RegistryObject<Item> LOG_PIECE_1 = ITEMS.register("log_piece1",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.HuskBold)));

    public static final RegistryObject<Item> SILVER_DRAGON_ASSASSIN_PICKAXE_E =
            ITEMS.register("silver_dragon_assassin_pickaxe_e", () -> new SilverDragonAssassinPickaxe(
                    new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_ITALIC_RARITY), 0));
    public static final RegistryObject<Item> SILVER_DRAGON_ASSASSIN_PICKAXE_D =
            ITEMS.register("silver_dragon_assassin_pickaxe_d", () -> new SilverDragonAssassinPickaxe(
                    new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_ITALIC_RARITY), 1));
    public static final RegistryObject<Item> SILVER_DRAGON_ASSASSIN_PICKAXE_C =
            ITEMS.register("silver_dragon_assassin_pickaxe_c", () -> new SilverDragonAssassinPickaxe(
                    new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_ITALIC_RARITY), 2));
    public static final RegistryObject<Item> SILVER_DRAGON_ASSASSIN_PICKAXE_B =
            ITEMS.register("silver_dragon_assassin_pickaxe_b", () -> new SilverDragonAssassinPickaxe(
                    new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_ITALIC_RARITY), 3));
    public static final RegistryObject<Item> SILVER_DRAGON_ASSASSIN_PICKAXE_A =
            ITEMS.register("silver_dragon_assassin_pickaxe_a", () -> new SilverDragonAssassinPickaxe(
                    new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_ITALIC_RARITY), 4));

    public static final RegistryObject<Item> INARI_BOW_E =
            ITEMS.register("inari_bow_e", () -> new INaRiBow(
                    new Item.Properties().rarity(CustomStyle.FieldItalic), 0));
    public static final RegistryObject<Item> INARI_BOW_D =
            ITEMS.register("inari_bow_d", () -> new INaRiBow(
                    new Item.Properties().rarity(CustomStyle.FieldItalic), 1));
    public static final RegistryObject<Item> INARI_BOW_C =
            ITEMS.register("inari_bow_c", () -> new INaRiBow(
                    new Item.Properties().rarity(CustomStyle.FieldItalic), 2));
    public static final RegistryObject<Item> INARI_BOW_B =
            ITEMS.register("inari_bow_b", () -> new INaRiBow(
                    new Item.Properties().rarity(CustomStyle.FieldItalic), 3));
    public static final RegistryObject<Item> INARI_BOW_A =
            ITEMS.register("inari_bow_a", () -> new INaRiBow(
                    new Item.Properties().rarity(CustomStyle.FieldItalic), 4));

    public static final RegistryObject<Item> DEEP_SEA_LEGENDARY_E =
            ITEMS.register("deep_sea_legendary_e", () -> new DeepSeaLegendary(
                    new Item.Properties().rarity(CustomStyle.SeaItalic), 0));
    public static final RegistryObject<Item> DEEP_SEA_LEGENDARY_D =
            ITEMS.register("deep_sea_legendary_d", () -> new DeepSeaLegendary(
                    new Item.Properties().rarity(CustomStyle.SeaItalic), 1));
    public static final RegistryObject<Item> DEEP_SEA_LEGENDARY_C =
            ITEMS.register("deep_sea_legendary_c", () -> new DeepSeaLegendary(
                    new Item.Properties().rarity(CustomStyle.SeaItalic), 2));
    public static final RegistryObject<Item> DEEP_SEA_LEGENDARY_B =
            ITEMS.register("deep_sea_legendary_b", () -> new DeepSeaLegendary(
                    new Item.Properties().rarity(CustomStyle.SeaItalic), 3));
    public static final RegistryObject<Item> DEEP_SEA_LEGENDARY_A =
            ITEMS.register("deep_sea_legendary_a", () -> new DeepSeaLegendary(
                    new Item.Properties().rarity(CustomStyle.SeaItalic), 4));
}
