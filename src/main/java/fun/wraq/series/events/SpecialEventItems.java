package fun.wraq.series.events;

import fun.wraq.commands.changeable.PrefixPaperItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.lottery.NewLotteries;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.events._7shade.SevenShadePiece;
import fun.wraq.series.events.dragonboat.DragonDiamond;
import fun.wraq.series.events.dragonboat.MeatZongZi;
import fun.wraq.series.events.dragonboat.SweetZongZi;
import fun.wraq.series.events.labourDay.LabourDayIronHoe;
import fun.wraq.series.events.labourDay.LabourDayIronPickaxe;
import fun.wraq.series.events.labourDay.LabourDayOldCoin;
import fun.wraq.series.events.midautumn.MidAutumnBow;
import fun.wraq.series.events.midautumn.MidAutumnSceptre;
import fun.wraq.series.events.midautumn.MidAutumnSword;
import fun.wraq.series.events.midautumn.MoonFeather;
import fun.wraq.series.events.qingMing.QingMingAttackRing;
import fun.wraq.series.events.qingMing.QingMingCommonRing;
import fun.wraq.series.events.qingMing.QingMingDefenceRing;
import fun.wraq.series.events.qingMing.QingTuan;
import fun.wraq.series.events.spring2024.FireCracker;
import fun.wraq.series.events.spring2024.FireworkGun;
import fun.wraq.series.events.spring2024.Spring2024Scale;
import fun.wraq.series.events.spring2025.curios.*;
import fun.wraq.series.events.summer2024.SummerCuriosOrEquip2024;
import fun.wraq.series.events.train.TrainSouvenirs;
import fun.wraq.series.events.year2024.Souvenirs2024;
import fun.wraq.series.gems.WraqGem;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class SpecialEventItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> SUMMER_VOUCHER = ITEMS.register("summer_voucher", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.WaterBold)));

    public static final RegistryObject<Item> SUMMER_CURIOS0 = ITEMS.register("summer24_curios0", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 0));

    public static final RegistryObject<Item> SUMMER_CURIOS1 = ITEMS.register("summer24_curios1", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 1));

    public static final RegistryObject<Item> SUMMER_CURIOS2 = ITEMS.register("summer24_curios2", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 2));

    public static final RegistryObject<Item> SUMMER_CURIOS3 = ITEMS.register("summer24_curios3", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 3));

    public static final RegistryObject<Item> SUMMER_CURIOS4 = ITEMS.register("summer24_curios4", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 4));

    public static final RegistryObject<Item> SUMMER_CURIOS5 = ITEMS.register("summer24_curios5", () ->
            new SummerCuriosOrEquip2024(new Item.Properties().rarity(CustomStyle.WaterBold), 5));

    public static final RegistryObject<Item> MID_AUTUMN_SOUL = ITEMS.register("mid_autumn_soul", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.MoonBold), false, true));

    public static final RegistryObject<Item> MOON_FEATHER_0 = ITEMS.register("moon_feather_0", () ->
            new MoonFeather(new Item.Properties().rarity(CustomStyle.MoonBold), 0));

    public static final RegistryObject<Item> MOON_FEATHER_1 = ITEMS.register("moon_feather_1", () ->
            new MoonFeather(new Item.Properties().rarity(CustomStyle.MoonBold), 1));

    public static final RegistryObject<Item> MOON_FEATHER_2 = ITEMS.register("moon_feather_2", () ->
            new MoonFeather(new Item.Properties().rarity(CustomStyle.MoonBold), 2));

    public static final RegistryObject<Item> MOON_FEATHER_3 = ITEMS.register("moon_feather_3", () ->
            new MoonFeather(new Item.Properties().rarity(CustomStyle.MoonBold), 3));

    public static final RegistryObject<Item> MID_AUTUMN_SWORD = ITEMS.register("mid_autumn_sword", () ->
            new MidAutumnSword(new Item.Properties().rarity(CustomStyle.MoonBold), 0.6));

    public static final RegistryObject<Item> MID_AUTUMN_BOW = ITEMS.register("mid_autumn_bow", () ->
            new MidAutumnBow(new Item.Properties().rarity(CustomStyle.MoonBold), 0.6));

    public static final RegistryObject<Item> MID_AUTUMN_SCEPTRE = ITEMS.register("mid_autumn_sceptre", () ->
            new MidAutumnSceptre(new Item.Properties().rarity(CustomStyle.MoonBold), 1.2));

    public static final RegistryObject<Item> MID_AUTUMN_PREFIX = ITEMS.register("mid_autumn_prefix", () ->
            new PrefixPaperItem(new Item.Properties().rarity(CustomStyle.MoonBold),
                    "midAutumnPrefix", "月荧风清", CustomStyle.styleOfMoon));

    public static final RegistryObject<Item> TRAIN_SOUVENIRS = ITEMS.register("train_souvenirs", () ->
            new TrainSouvenirs(new Item.Properties().rarity(CustomStyle.FieldBold)));

    public static final RegistryObject<Item> SOUVENIRS_2024 = ITEMS.register("souvenirs_2024", () ->
            new Souvenirs2024(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> RING = ITEMS.register("spring_ring0",
            () -> new Spring2025Ring(new Item.Properties().rarity(CustomStyle.SpringBold), 0));

    public static final RegistryObject<Item> RING_1 = ITEMS.register("spring_ring1",
            () -> new Spring2025Ring(new Item.Properties().rarity(CustomStyle.SpringBold), 1));

    public static final RegistryObject<Item> RING_2 = ITEMS.register("spring_ring2",
            () -> new Spring2025Ring(new Item.Properties().rarity(CustomStyle.SpringBold), 2));

    public static final RegistryObject<Item> RING_3 = ITEMS.register("spring_ring3",
            () -> new Spring2025Ring(new Item.Properties().rarity(CustomStyle.SpringBold), 3));

    public static final RegistryObject<Item> RING_4 = ITEMS.register("spring_ring4",
            () -> new Spring2025Ring(new Item.Properties().rarity(CustomStyle.SpringBold), 4));

    public static final RegistryObject<Item> HAND = ITEMS.register("spring_hand0",
            () -> new Spring2025Hand(new Item.Properties().rarity(CustomStyle.SpringBold), 0));

    public static final RegistryObject<Item> HAND_1 = ITEMS.register("spring_hand1",
            () -> new Spring2025Hand(new Item.Properties().rarity(CustomStyle.SpringBold), 1));

    public static final RegistryObject<Item> HAND_2 = ITEMS.register("spring_hand2",
            () -> new Spring2025Hand(new Item.Properties().rarity(CustomStyle.SpringBold), 2));

    public static final RegistryObject<Item> HAND_3 = ITEMS.register("spring_hand3",
            () -> new Spring2025Hand(new Item.Properties().rarity(CustomStyle.SpringBold), 3));

    public static final RegistryObject<Item> HAND_4 = ITEMS.register("spring_hand4",
            () -> new Spring2025Hand(new Item.Properties().rarity(CustomStyle.SpringBold), 4));

    public static final RegistryObject<Item> BELT = ITEMS.register("spring_belt0",
            () -> new Spring2025Belt(new Item.Properties().rarity(CustomStyle.SpringBold), 0));

    public static final RegistryObject<Item> BELT_1 = ITEMS.register("spring_belt1",
            () -> new Spring2025Belt(new Item.Properties().rarity(CustomStyle.SpringBold), 1));

    public static final RegistryObject<Item> BELT_2 = ITEMS.register("spring_belt2",
            () -> new Spring2025Belt(new Item.Properties().rarity(CustomStyle.SpringBold), 2));

    public static final RegistryObject<Item> BELT_3 = ITEMS.register("spring_belt3",
            () -> new Spring2025Belt(new Item.Properties().rarity(CustomStyle.SpringBold), 3));

    public static final RegistryObject<Item> BELT_4 = ITEMS.register("spring_belt4",
            () -> new Spring2025Belt(new Item.Properties().rarity(CustomStyle.SpringBold), 4));

    public static final RegistryObject<Item> NECKLACE = ITEMS.register("spring_necklace0",
            () -> new Spring2025Necklace(new Item.Properties().rarity(CustomStyle.SpringBold), 0));

    public static final RegistryObject<Item> NECKLACE_1 = ITEMS.register("spring_necklace1",
            () -> new Spring2025Necklace(new Item.Properties().rarity(CustomStyle.SpringBold), 1));

    public static final RegistryObject<Item> NECKLACE_2 = ITEMS.register("spring_necklace2",
            () -> new Spring2025Necklace(new Item.Properties().rarity(CustomStyle.SpringBold), 2));

    public static final RegistryObject<Item> NECKLACE_3 = ITEMS.register("spring_necklace3",
            () -> new Spring2025Necklace(new Item.Properties().rarity(CustomStyle.SpringBold), 3));

    public static final RegistryObject<Item> NECKLACE_4 = ITEMS.register("spring_necklace4",
            () -> new Spring2025Necklace(new Item.Properties().rarity(CustomStyle.SpringBold), 4));

    public static final RegistryObject<Item> MONEY = ITEMS.register("spring_money",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> SPRING_GOLD_COIN = ITEMS.register("spring_gold_coin",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> SPRING_GOLD_INGOT = ITEMS.register("spring_gold_ingot",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> RED_ENVELOPE = ITEMS.register("red_envelope",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.SpringBold), new ArrayList<>() {{
                add(new NewLotteries.Loot(new ItemStack(SPRING_GOLD_COIN.get()), 0.8));
                add(new NewLotteries.Loot(new ItemStack(ModItems.JUNIOR_SUPPLY.get()), 0.1));
                add(new NewLotteries.Loot(new ItemStack(MONEY.get()), 0.1));
            }}));

    public static final RegistryObject<Item> SpringHeart = ITEMS.register("spring_heart",
            () -> new Item(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> SPRING_SOUL = ITEMS.register("spring_soul",
            () -> new Item(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> BIG_RED_ENVELOPE = ITEMS.register("big_red_envelope",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.SpringBold), new ArrayList<>() {{
                add(new NewLotteries.Loot(new ItemStack(MONEY.get()), 0.6));
                add(new NewLotteries.Loot(new ItemStack(ModItems.WORLD_SOUL_5.get(), 8), 0.2));
                add(new NewLotteries.Loot(new ItemStack(ModItems.REPUTATION_MEDAL.get()), 0.1));
                add(new NewLotteries.Loot(new ItemStack(RED_ENVELOPE.get()), 0.1));
            }}));

    public static final RegistryObject<Item> FIRE_CRACKER = ITEMS.register("fire_cracker",
            () -> new FireCracker(new Item.Properties().rarity(CustomStyle.Spring)));

    public static final RegistryObject<Item> SCALE_PIECE = ITEMS.register("spring_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Spring)));

    public static final RegistryObject<Item> SCALE_0 = ITEMS.register("spring_scale0",
            () -> new Spring2024Scale(new Item.Properties().rarity(CustomStyle.SpringBold), 0));

    public static final RegistryObject<Item> SCALE_1 = ITEMS.register("spring_scale1",
            () -> new Spring2024Scale(new Item.Properties().rarity(CustomStyle.SpringBold), 1));

    public static final RegistryObject<Item> SCALE_2 = ITEMS.register("spring_scale2",
            () -> new Spring2024Scale(new Item.Properties().rarity(CustomStyle.SpringBold), 2));

    public static final RegistryObject<Item> SCALE_3 = ITEMS.register("spring_scale3",
            () -> new Spring2024Scale(new Item.Properties().rarity(CustomStyle.SpringBold), 3));

    public static final RegistryObject<Item> FIRE_WORK_GUN = ITEMS.register("fire_work_gun",
            () -> new FireworkGun(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> SCALE_2025_0 = ITEMS.register("spring_scale_2025_0",
            () -> new Spring2025Scale(new Item.Properties().rarity(CustomStyle.SpringBold), 0));

    public static final RegistryObject<Item> SCALE_2025_1 = ITEMS.register("spring_scale_2025_1",
            () -> new Spring2025Scale(new Item.Properties().rarity(CustomStyle.SpringBold), 1));

    public static final RegistryObject<Item> SCALE_2025_2 = ITEMS.register("spring_scale_2025_2",
            () -> new Spring2025Scale(new Item.Properties().rarity(CustomStyle.SpringBold), 2));

    public static final RegistryObject<Item> SCALE_2025_3 = ITEMS.register("spring_scale_2025_3",
            () -> new Spring2025Scale(new Item.Properties().rarity(CustomStyle.SpringBold), 3));

    public static final RegistryObject<Item> QING_TUAN = ITEMS.register("qing_tuan",
            () -> new QingTuan(new Item.Properties().rarity(CustomStyle.ForestBold)));

    public static final RegistryObject<Item> QING_MING_PREFIX_PAPER_1 = ITEMS.register("qing_ming_prefix_paper_1",
            () -> new PrefixPaperItem(new Item.Properties().rarity(CustomStyle.LifeBold),
                    QingTuan.PREFIX_1, "雨纷纷", CustomStyle.styleOfLife));
    public static final RegistryObject<Item> QING_MING_PREFIX_PAPER_2 = ITEMS.register("qing_ming_prefix_paper_2",
            () -> new PrefixPaperItem(new Item.Properties().rarity(CustomStyle.LifeBold),
                    QingTuan.PREFIX_2, "欲断魂", CustomStyle.styleOfLife));

    public static final RegistryObject<Item> QING_MING_COMMON_RING = ITEMS.register("qing_ming_common_ring",
            () -> new QingMingCommonRing(new Item.Properties().rarity(CustomStyle.LifeBold).stacksTo(1)));
    public static final RegistryObject<Item> QING_MING_ATTACK_RING = ITEMS.register("qing_ming_attack_ring",
            () -> new QingMingAttackRing(new Item.Properties().rarity(CustomStyle.LifeBold).stacksTo(1)));
    public static final RegistryObject<Item> QING_MING_DEFENCE_RING = ITEMS.register("qing_ming_defence_ring",
            () -> new QingMingDefenceRing(new Item.Properties().rarity(CustomStyle.LifeBold).stacksTo(1)));

    public static final RegistryObject<Item> QING_MING_FORGE_PAPER = ITEMS.register("qing_ming_forge_paper",
            () -> new ForgePaper(new Item.Properties().rarity(CustomStyle.LifeBold),
                    "QingMingForgePaper", QingTuan.getQingMingSuffix(),
                    Te.s("「清符+1」", CustomStyle.styleOfLife)));

    public static final RegistryObject<Item> QING_MING_REBORN_CHEST = ITEMS.register("qing_ming_reborn_chest",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.LifeBold), new ArrayList<>() {{
                add(new NewLotteries.Loot(new ItemStack(QING_MING_COMMON_RING.get()), 0.025));
                add(new NewLotteries.Loot(new ItemStack(QING_MING_PREFIX_PAPER_1.get()), 0.025));
                add(new NewLotteries.Loot(new ItemStack(QING_TUAN.get(), 4), 0.05));
                add(new NewLotteries.Loot(new ItemStack(ModItems.WORLD_SOUL_5.get(), 5), 0.9));
            }}, true, List.of(
                    Te.s(" 在", "清明活动", CustomStyle.styleOfLife, "期间", "(4.2-4.7)", ChatFormatting.AQUA),
                    Te.s(" 每日第", "5/10/15/20", CustomStyle.styleOfLife, "次完成",
                            "委托任务", ChatFormatting.AQUA, "可获得", "2个此物品")
            )));

    public static final RegistryObject<Item> QING_MING_QING_TUAN_CHEST = ITEMS.register("qing_ming_qing_tuan_chest",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.LifeBold), new ArrayList<>() {{
                add(new NewLotteries.Loot(new ItemStack(QING_MING_FORGE_PAPER.get()), 0.01));
                add(new NewLotteries.Loot(new ItemStack(QING_MING_ATTACK_RING.get()), 0.0125));
                add(new NewLotteries.Loot(new ItemStack(QING_MING_DEFENCE_RING.get()), 0.0125));
                add(new NewLotteries.Loot(new ItemStack(QING_MING_PREFIX_PAPER_2.get()), 0.015));
                add(new NewLotteries.Loot(new ItemStack(QING_TUAN.get(), 2), 0.95));
            }}, false, List.of(
                    Te.s(" 在", "清明活动", CustomStyle.styleOfLife, "期间", "(4.2-4.7)", ChatFormatting.AQUA),
                    Te.s(" 完成", "委托任务", ChatFormatting.AQUA, "即可获得", "1个此物品")
            )));

    public static final RegistryObject<Item> OLD_SILVER_COIN = ITEMS.register("old_silver_coin",
            () -> new LabourDayOldCoin(new Item.Properties().rarity(CustomStyle.Mine)));

    public static final RegistryObject<Item> OLD_GOLD_COIN = ITEMS.register("old_gold_coin",
            () -> new LabourDayOldCoin(new Item.Properties().rarity(CustomStyle.Gold)));

    public static final RegistryObject<Item> LABOUR_DAY_FORGE_PAPER = ITEMS.register("labour_day_forge_paper",
            () -> new ForgePaper(new Item.Properties().rarity(CustomStyle.GoldBold),
                    "LabourDayForgePaper", LabourDayOldCoin.getLabourDaySuffix(),
                    Te.s("「劳动+1」", ChatFormatting.GOLD)));

    public static final RegistryObject<Item> LABOUR_DAY_IRON_HOE = ITEMS.register("labour_day_iron_hoe",
            () -> new LabourDayIronHoe(new Item.Properties().rarity(CustomStyle.GoldBold)));
    public static final RegistryObject<Item> LABOUR_DAY_IRON_PICKAXE = ITEMS.register("labour_day_iron_pickaxe",
            () -> new LabourDayIronPickaxe(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> LABOUR_DAY_PREFIX = ITEMS.register("labour_day_prefix",
            () -> new PrefixPaperItem(new Item.Properties().rarity(CustomStyle.GoldBold),
                    "labourDay", "无产阶级", CustomStyle.styleOfGold));

    public static final RegistryObject<Item> LABOUR_DAY_GEM = ITEMS.register("labour_day_gem",
            () -> new WraqGem(new Item.Properties().rarity(CustomStyle.GoldBold),
                    List.of(
                            new WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.05),
                            new WraqGem.AttributeMapValue(Utils.percentManaDamageEnhance, 0.05)
                    ), CustomStyle.styleOfGold, Te.s("这是最后的斗争", CustomStyle.styleOfGold),
                    LabourDayOldCoin.getLabourDaySuffix()));

    public static final RegistryObject<Item> LABOUR_DAY_LOTTERY = ITEMS.register("labour_day_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.GoldBold), new ArrayList<>() {{
                add(new NewLotteries.Loot(new ItemStack(SpecialEventItems.LABOUR_DAY_FORGE_PAPER.get()), 0.02));
                add(new NewLotteries.Loot(new ItemStack(SpecialEventItems.LABOUR_DAY_GEM.get()), 0.02));
                add(new NewLotteries.Loot(new ItemStack(SpecialEventItems.LABOUR_DAY_PREFIX.get()), 0.02));
                add(new NewLotteries.Loot(new ItemStack(SpecialEventItems.OLD_GOLD_COIN.get(), 2), 0.04));
                add(new NewLotteries.Loot(new ItemStack(SpecialEventItems.OLD_GOLD_COIN.get(), 1), 0.1));
                add(new NewLotteries.Loot(new ItemStack(ModItems.WORLD_SOUL_5.get(), 4), 0.8));
            }}));

    public static final RegistryObject<Item> ZONG_LEAF = ITEMS.register("zong_leaf",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.DRAGON_BOAT_FES_RARITY)));
    public static final RegistryObject<Item> SWEET_ZONG_ZI = ITEMS.register("sweet_zong_zi",
            () -> new SweetZongZi(new Item.Properties().rarity(CustomStyle.DRAGON_BOAT_FES_RARITY)));
    public static final RegistryObject<Item> MEAT_ZONG_ZI = ITEMS.register("meat_zong_zi",
            () -> new MeatZongZi(new Item.Properties().rarity(CustomStyle.DRAGON_BOAT_FES_RARITY)));

    public static final RegistryObject<Item> GOLDEN_ZONG_ZI_CONDIMENT = ITEMS.register("golden_zong_zi_condiment",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GoldBold)));
    public static final RegistryObject<Item> GOLDEN_ZONG_LEAF = ITEMS.register("golden_zong_leaf",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GoldBold)));
    public static final RegistryObject<Item> GOLDEN_ZONG_ZI = ITEMS.register("golden_zong_zi",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> DRAGON_BOAT_FES_PREFIX = ITEMS.register("dragon_boat_fes_prefix",
            () -> new PrefixPaperItem(new Item.Properties().rarity(CustomStyle.DRAGON_BOAT_FES_RARITY),
                    "dragonBoatFes", "粽子大王", CustomStyle.DRAGON_BOAT_FES_STYLE));

    public static final RegistryObject<Item> SEVEN_SHADE_PIECE_RICE
            = ITEMS.register("seven_shade_piece_rice",
            () -> new SevenShadePiece(new Item.Properties().rarity(CustomStyle.WorldBold),
                    List.of(
                            new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, 0.05),
                            new WraqGem.AttributeMapValue(Utils.healthRecover, -50)
                    )));

    public static final RegistryObject<Item> SEVEN_SHADE_PIECE_GOLDEN_LEAF
            = ITEMS.register("seven_shade_piece_golden_leaf",
            () -> new SevenShadePiece(new Item.Properties().rarity(CustomStyle.WorldBold),
                    List.of(
                            new WraqGem.AttributeMapValue(Utils.percentAttackDamageEnhance, 0.02),
                            new WraqGem.AttributeMapValue(Utils.percentManaDamageEnhance, 0.02),
                            new WraqGem.AttributeMapValue(Utils.manaRecover, 20),
                            new WraqGem.AttributeMapValue(Utils.percentMaxHealthEnhance, -0.04)
                    )));

    public static final RegistryObject<Item> SEVEN_SHADE_PIECE_DRAGON_BOAT
            = ITEMS.register("seven_shade_piece_dragon_boat",
            () -> new SevenShadePiece(new Item.Properties().rarity(CustomStyle.WorldBold),
                    List.of(
                            new WraqGem.AttributeMapValue(Utils.movementSpeedCommon, 0.1),
                            new WraqGem.AttributeMapValue(Utils.coolDownDecrease, 0.1),
                            new WraqGem.AttributeMapValue(Utils.defence, -20),
                            new WraqGem.AttributeMapValue(Utils.manaDefence, -20)
                    )));

    public static final RegistryObject<Item> DRAGON_BOAT_FES_FORGE_PAPER
            = ITEMS.register("dragon_boat_fes_forge_paper",
            () -> new ForgePaper(new Item.Properties().rarity(CustomStyle.DRAGON_BOAT_FES_RARITY),
                    "DragonBoatFes", LabourDayOldCoin.getLabourDaySuffix(),
                    Te.s("「粽符+1」", CustomStyle.DRAGON_BOAT_FES_STYLE)));

    public static final RegistryObject<Item> DRAGON_DIAMOND = ITEMS.register("dragon_diamond",
            () -> new DragonDiamond(new Item.Properties().rarity(CustomStyle.WorldBold)));
}
