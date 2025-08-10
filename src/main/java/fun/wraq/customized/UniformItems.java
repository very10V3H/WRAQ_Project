package fun.wraq.customized;

import fun.wraq.common.util.Utils;
import fun.wraq.customized.uniform.attack.enhanced.AttackEnhancedCurio0;
import fun.wraq.customized.uniform.attack.normal.*;
import fun.wraq.customized.uniform.bow.enhanced.BowEnhancedCurio;
import fun.wraq.customized.uniform.bow.normal.*;
import fun.wraq.customized.uniform.element.*;
import fun.wraq.customized.uniform.mana.enhanced.ManaEnhancedCurio;
import fun.wraq.customized.uniform.mana.normal.*;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.labourDay.LabourDayUniformCurio;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SimpleFoiledItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UniformItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> ATTACK_CURIOS_0 = ITEMS.register("attack_curios_0",
            () -> new AttackCurios0(new Item.Properties().rarity(CustomStyle.MagmaBold)));
    public static final RegistryObject<Item> BOW_CURIOS_0 = ITEMS.register("bow_curios_0",
            () -> new BowCurios0(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> MANA_CURIOS_0 = ITEMS.register("mana_curios_0",
            () -> new ManaCurios0(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> ATTACK_CURIOS_1 = ITEMS.register("attack_curios_1",
            () -> new AttackCurios1(new Item.Properties().rarity(CustomStyle.MagmaBold)));
    public static final RegistryObject<Item> BOW_CURIOS_1 = ITEMS.register("bow_curios_1",
            () -> new BowCurios1(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> MANA_CURIOS_1 = ITEMS.register("mana_curios_1",
            () -> new ManaCurios1(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> ATTACK_CURIOS_2 = ITEMS.register("attack_curios_2",
            () -> new AttackCurios2(new Item.Properties().rarity(CustomStyle.MagmaBold)));
    public static final RegistryObject<Item> BOW_CURIOS_2 = ITEMS.register("bow_curios_2",
            () -> new BowCurios2(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> MANA_CURIOS_2 = ITEMS.register("mana_curios_2",
            () -> new ManaCurios2(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> LIFE_CURIOS_0 = ITEMS.register("life_curios_0",
            () -> new LifeCurios0(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> WATER_CURIOS_0 = ITEMS.register("water_curios_0",
            () -> new WaterCurios0(new Item.Properties().rarity(CustomStyle.WaterBold)));
    public static final RegistryObject<Item> FIRE_CURIOS_0 = ITEMS.register("fire_curios_0",
            () -> new FireCurios0(new Item.Properties().rarity(CustomStyle.FireBold)));
    public static final RegistryObject<Item> STONE_CURIOS_0 = ITEMS.register("stone_curios_0",
            () -> new StoneCurios0(new Item.Properties().rarity(CustomStyle.StoneBold)));
    public static final RegistryObject<Item> ICE_CURIOS_0 = ITEMS.register("ice_curios_0",
            () -> new IceCurios0(new Item.Properties().rarity(CustomStyle.IceBold)));
    public static final RegistryObject<Item> WIND_CURIOS_0 = ITEMS.register("wind_curios_0",
            () -> new WindCurios0(new Item.Properties().rarity(CustomStyle.WindBold)));
    public static final RegistryObject<Item> LIGHTNING_CURIOS_0 = ITEMS.register("lightning_curios_0",
            () -> new LightningCurios0(new Item.Properties().rarity(CustomStyle.LightningBold)));

    public static final RegistryObject<Item> ATTACK_CURIOS_YXWG = ITEMS.register("attack_curios_yxwg",
            () -> new AttackCuriosYxwgCurios(new Item.Properties().stacksTo(1).rarity(CustomStyle.MagmaBold)));
    public static final RegistryObject<Item> BOW_CURIOS_YXWG = ITEMS.register("bow_curios_yxwg",
            () -> new BowCuriosYxwg(new Item.Properties().stacksTo(1).rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> MANA_CURIOS_YXWG = ITEMS.register("mana_curios_yxwg",
            () -> new ManaCuriosYxwg(new Item.Properties().stacksTo(1).rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> ATTACK_CURIO_LX = ITEMS.register("attack_curio_lx",
            () -> new AttackCurioLX(new Item.Properties().stacksTo(1).rarity(CustomStyle.MagmaBold)));
    public static final RegistryObject<Item> BOW_CURIO_LEI_YAN = ITEMS.register("bow_curio_lei_yan",
            () -> new BowCurioLeiYan(new Item.Properties().stacksTo(1).rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> MANA_CURIO_TABOO = ITEMS.register("mana_curio_taboo",
            () -> new ManaCurioTaboo(new Item.Properties().stacksTo(1).rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> UNIFORM_PIECE = ITEMS.register("uniform_piece",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> ATTACK_CURIOS_3 = ITEMS.register("attack_curios_3",
            () -> new AttackCurios3(new Item.Properties().rarity(CustomStyle.MagmaBold)));
    public static final RegistryObject<Item> BOW_CURIOS_3 = ITEMS.register("bow_curios_3",
            () -> new BowCurios3(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> MANA_CURIOS_3 = ITEMS.register("mana_curios_3",
            () -> new ManaCurios3(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> ATTACK_CURIOS_4 = ITEMS.register("attack_curios_4",
            () -> new AttackCurios4(new Item.Properties().rarity(CustomStyle.MagmaBold)));
    public static final RegistryObject<Item> BOW_CURIOS_4 = ITEMS.register("bow_curios_4",
            () -> new BowCurios4(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> MANA_CURIOS_4 = ITEMS.register("mana_curios_4",
            () -> new ManaCurios4(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> ATTACK_CURIOS_5 = ITEMS.register("attack_curios_5",
            () -> new AttackCurios5(new Item.Properties().rarity(CustomStyle.MagmaBold)));
    public static final RegistryObject<Item> BOW_CURIOS_5 = ITEMS.register("bow_curios_5",
            () -> new BowCurios5(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> MANA_CURIOS_5 = ITEMS.register("mana_curios_5",
            () -> new ManaCurios5(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> ATTACK_CURIOS_6 = ITEMS.register("attack_curios_6",
            () -> new AttackCurios6(new Item.Properties().rarity(CustomStyle.MagmaBold)));
    public static final RegistryObject<Item> BOW_CURIOS_6 = ITEMS.register("bow_curios_6",
            () -> new BowCurios6(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> MANA_CURIOS_6 = ITEMS.register("mana_curios_6",
            () -> new ManaCurios6(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> ATTACK_CURIOS_7 = ITEMS.register("attack_curios_7",
            () -> new AttackCurios7(new Item.Properties().rarity(CustomStyle.MagmaBold)));
    public static final RegistryObject<Item> BOW_CURIOS_7 = ITEMS.register("bow_curios_7",
            () -> new BowCurios7(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> MANA_CURIOS_7 = ITEMS.register("mana_curios_7",
            () -> new ManaCurios7(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> ATTACK_ENHANCED_CURIOS_0 = ITEMS.register("attack_enhanced_curio_0",
            () -> new AttackEnhancedCurio0(new Item.Properties().rarity(CustomStyle.MagmaBold)));
    public static final RegistryObject<Item> BOW_ENHANCED_CURIOS_0 = ITEMS.register("bow_enhanced_curio_0",
            () -> new BowEnhancedCurio(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> MANA_ENHANCED_CURIOS_0 = ITEMS.register("mana_enhanced_curio_0",
            () -> new ManaEnhancedCurio(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> LABOUR_DAY_UNIFORM_CURIO
            = ITEMS.register("labour_day_uniform_curio",
            () -> new LabourDayUniformCurio(new Item.Properties().rarity(CustomStyle.GoldBold)));
}
