package fun.wraq.process.system.spur.Items;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
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

    public static final RegistryObject<Item> mineCharm0 = ITEMS.register("mine_charm0",
            () -> new fun.wraq.process.system.spur.Items.MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 0));

    public static final RegistryObject<Item> mineCharm1 = ITEMS.register("mine_charm1",
            () -> new fun.wraq.process.system.spur.Items.MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 1));

    public static final RegistryObject<Item> mineCharm2 = ITEMS.register("mine_charm2",
            () -> new fun.wraq.process.system.spur.Items.MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 2));

    public static final RegistryObject<Item> mineCharm3 = ITEMS.register("mine_charm3",
            () -> new fun.wraq.process.system.spur.Items.MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 3));

    public static final RegistryObject<Item> mineCharm4 = ITEMS.register("mine_charm4",
            () -> new fun.wraq.process.system.spur.Items.MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 4));

    public static final RegistryObject<Item> mineCharm5 = ITEMS.register("mine_charm5",
            () -> new fun.wraq.process.system.spur.Items.MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 5));

    public static final RegistryObject<Item> mineCharm6 = ITEMS.register("mine_charm6",
            () -> new MineCharm(new Item.Properties().rarity(CustomStyle.MineBold), 6));

    public static final RegistryObject<Item> seaCharm0 = ITEMS.register("sea_charm0",
            () -> new fun.wraq.process.system.spur.Items.SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 0));

    public static final RegistryObject<Item> seaCharm1 = ITEMS.register("sea_charm1",
            () -> new fun.wraq.process.system.spur.Items.SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 1));

    public static final RegistryObject<Item> seaCharm2 = ITEMS.register("sea_charm2",
            () -> new fun.wraq.process.system.spur.Items.SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 2));

    public static final RegistryObject<Item> seaCharm3 = ITEMS.register("sea_charm3",
            () -> new fun.wraq.process.system.spur.Items.SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 3));

    public static final RegistryObject<Item> seaCharm4 = ITEMS.register("sea_charm4",
            () -> new fun.wraq.process.system.spur.Items.SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 4));

    public static final RegistryObject<Item> seaCharm5 = ITEMS.register("sea_charm5",
            () -> new fun.wraq.process.system.spur.Items.SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 5));

    public static final RegistryObject<Item> seaCharm6 = ITEMS.register("sea_charm6",
            () -> new SeaCharm(new Item.Properties().rarity(CustomStyle.SeaBold), 6));

    public static final RegistryObject<Item> cropCharm0 = ITEMS.register("crop_charm0",
            () -> new fun.wraq.process.system.spur.Items.CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 0));

    public static final RegistryObject<Item> cropCharm1 = ITEMS.register("crop_charm1",
            () -> new fun.wraq.process.system.spur.Items.CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 1));

    public static final RegistryObject<Item> cropCharm2 = ITEMS.register("crop_charm2",
            () -> new fun.wraq.process.system.spur.Items.CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 2));

    public static final RegistryObject<Item> cropCharm3 = ITEMS.register("crop_charm3",
            () -> new fun.wraq.process.system.spur.Items.CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 3));

    public static final RegistryObject<Item> cropCharm4 = ITEMS.register("crop_charm4",
            () -> new fun.wraq.process.system.spur.Items.CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 4));

    public static final RegistryObject<Item> cropCharm5 = ITEMS.register("crop_charm5",
            () -> new fun.wraq.process.system.spur.Items.CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 5));

    public static final RegistryObject<Item> cropCharm6 = ITEMS.register("crop_charm6",
            () -> new CropCharm(new Item.Properties().rarity(CustomStyle.GoldBold), 6));

    public static final RegistryObject<Item> logCharm0 = ITEMS.register("log_charm0",
            () -> new fun.wraq.process.system.spur.Items.LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 0));

    public static final RegistryObject<Item> logCharm1 = ITEMS.register("log_charm1",
            () -> new fun.wraq.process.system.spur.Items.LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 1));

    public static final RegistryObject<Item> logCharm2 = ITEMS.register("log_charm2",
            () -> new fun.wraq.process.system.spur.Items.LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 2));

    public static final RegistryObject<Item> logCharm3 = ITEMS.register("log_charm3",
            () -> new fun.wraq.process.system.spur.Items.LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 3));

    public static final RegistryObject<Item> logCharm4 = ITEMS.register("log_charm4",
            () -> new fun.wraq.process.system.spur.Items.LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 4));

    public static final RegistryObject<Item> logCharm5 = ITEMS.register("log_charm5",
            () -> new fun.wraq.process.system.spur.Items.LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 5));

    public static final RegistryObject<Item> logCharm6 = ITEMS.register("log_charm6",
            () -> new LogCharm(new Item.Properties().rarity(CustomStyle.HuskBold), 6));

    public static final RegistryObject<Item> minePiece = ITEMS.register("mine_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Mine), false, false, List.of(
                    Te.s("通过", "挖掘矿石", CustomStyle.styleOfStone, "概率获取")
            )));

    public static final RegistryObject<Item> minePiece1 = ITEMS.register("mine_piece1",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.MineBold)));

    public static final RegistryObject<Item> seaPiece = ITEMS.register("sea_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Sea), false, false, List.of(
                    Te.s("通过", "钓鱼", CustomStyle.styleOfSea, "概率获取")
            )));

    public static final RegistryObject<Item> seaPiece1 = ITEMS.register("sea_piece1",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.SeaBold)));

    public static final RegistryObject<Item> cropPiece = ITEMS.register("crop_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Gold), false, false, List.of(
                    Te.s("通过", "采集农作物", CustomStyle.styleOfField, "概率获取")
            )));

    public static final RegistryObject<Item> cropPiece1 = ITEMS.register("crop_piece1",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> logPiece = ITEMS.register("log_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Husk), false, false, List.of(
                    Te.s("通过", "砍伐树木", CustomStyle.styleOfHusk, "概率获取")
            )));

    public static final RegistryObject<Item> logPiece1 = ITEMS.register("log_piece1",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.HuskBold)));
}
