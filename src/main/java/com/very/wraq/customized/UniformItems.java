package com.very.wraq.customized;

import com.very.wraq.common.Utils.Utils;
import com.very.wraq.customized.uniform.attack.AttackCurios0;
import com.very.wraq.customized.uniform.attack.AttackCurios1;
import com.very.wraq.customized.uniform.attack.AttackCurios2;
import com.very.wraq.customized.uniform.attack.AttackCuriosYxwg;
import com.very.wraq.customized.uniform.bow.BowCurios0;
import com.very.wraq.customized.uniform.bow.BowCurios1;
import com.very.wraq.customized.uniform.bow.BowCurios2;
import com.very.wraq.customized.uniform.bow.BowCuriosYxwg;
import com.very.wraq.customized.uniform.element.*;
import com.very.wraq.customized.uniform.mana.ManaCurios0;
import com.very.wraq.customized.uniform.mana.ManaCurios1;
import com.very.wraq.customized.uniform.mana.ManaCurios2;
import com.very.wraq.customized.uniform.mana.ManaCuriosYxwg;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SimpleFoiledItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UniformItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> AttackCurios0 = ITEMS.register("attack_curios_0",
            () -> new AttackCurios0(new Item.Properties().rarity(CustomStyle.MagmaBold)));

    public static final RegistryObject<Item> BowCurios0 = ITEMS.register("bow_curios_0",
            () -> new BowCurios0(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> ManaCurios0 = ITEMS.register("mana_curios_0",
            () -> new ManaCurios0(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> AttackCurios1 = ITEMS.register("attack_curios_1",
            () -> new AttackCurios1(new Item.Properties().rarity(CustomStyle.MagmaBold)));

    public static final RegistryObject<Item> BowCurios1 = ITEMS.register("bow_curios_1",
            () -> new BowCurios1(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> ManaCurios1 = ITEMS.register("mana_curios_1",
            () -> new ManaCurios1(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> AttackCurios2 = ITEMS.register("attack_curios_2",
            () -> new AttackCurios2(new Item.Properties().rarity(CustomStyle.MagmaBold)));

    public static final RegistryObject<Item> BowCurios2 = ITEMS.register("bow_curios_2",
            () -> new BowCurios2(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> ManaCurios2 = ITEMS.register("mana_curios_2",
            () -> new ManaCurios2(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> LifeCurios0 = ITEMS.register("life_curios_0",
            () -> new LifeCurios0(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> WaterCurios0 = ITEMS.register("water_curios_0",
            () -> new WaterCurios0(new Item.Properties().rarity(CustomStyle.WaterBold)));

    public static final RegistryObject<Item> FireCurios0 = ITEMS.register("fire_curios_0",
            () -> new FireCurios0(new Item.Properties().rarity(CustomStyle.FireBold)));

    public static final RegistryObject<Item> StoneCurios0 = ITEMS.register("stone_curios_0",
            () -> new StoneCurios0(new Item.Properties().rarity(CustomStyle.StoneBold)));

    public static final RegistryObject<Item> IceCurios0 = ITEMS.register("ice_curios_0",
            () -> new IceCurios0(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> WindCurios0 = ITEMS.register("wind_curios_0",
            () -> new WindCurios0(new Item.Properties().rarity(CustomStyle.WindBold)));

    public static final RegistryObject<Item> LightningCurios0 = ITEMS.register("lightning_curios_0",
            () -> new LightningCurios0(new Item.Properties().rarity(CustomStyle.LightningBold)));

    public static final RegistryObject<Item> ATTACK_CURIOS_YXWG = ITEMS.register("attack_curios_yxwg",
            () -> new AttackCuriosYxwg(new Item.Properties().stacksTo(1).rarity(CustomStyle.MagmaBold)));

    public static final RegistryObject<Item> BOW_CURIOS_YXWG = ITEMS.register("bow_curios_yxwg",
            () -> new BowCuriosYxwg(new Item.Properties().stacksTo(1).rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> MANA_CURIOS_YXWG = ITEMS.register("mana_curios_yxwg",
            () -> new ManaCuriosYxwg(new Item.Properties().stacksTo(1).rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> uniformPiece = ITEMS.register("uniform_piece",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.WorldBold)));
}
