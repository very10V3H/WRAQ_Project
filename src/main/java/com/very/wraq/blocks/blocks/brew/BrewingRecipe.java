package com.very.wraq.blocks.blocks.brew;

import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrewingRecipe {
    public final ItemStack input;
    public final ItemStack material1;
    public final ItemStack material2;
    public final ItemStack output;

    public BrewingRecipe(ItemStack input, ItemStack material1, ItemStack material2, ItemStack output) {
        this.input = input;
        this.material1 = material1;
        this.material2 = material2;
        this.output = output;
    }

    public static List<BrewingRecipe> recipeList = new ArrayList<>();

    public static void setRecipeList() {
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.VolcanoSolidifiedSoul.get())
                , new ItemStack(ModItems.VolcanoSolidifiedSoul.get())
                , new ItemStack(ModItems.AttackUpPotion.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.VolcanoSolidifiedSoul.get())
                , new ItemStack(ModItems.ForestSolidifiedSoul.get())
                , new ItemStack(ModItems.DefencePenetrationUpPotion.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.SnowSolidifiedSoul.get())
                , new ItemStack(ModItems.VolcanoSolidifiedSoul.get())
                , new ItemStack(ModItems.CritRateUpPotion.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.VolcanoSolidifiedSoul.get())
                , new ItemStack(ModItems.SnowSolidifiedSoul.get())
                , new ItemStack(ModItems.CritDamageUpPotion.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.EvokerSolidifiedSoul.get())
                , new ItemStack(ModItems.VolcanoSolidifiedSoul.get())
                , new ItemStack(ModItems.ManaDamageUpPotion.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.EvokerSolidifiedSoul.get())
                , new ItemStack(ModItems.ForestSolidifiedSoul.get())
                , new ItemStack(ModItems.ManaPenetrationUpPotion.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.EvokerSolidifiedSoul.get())
                , new ItemStack(ModItems.PlainSolidifiedSoul.get())
                , new ItemStack(ModItems.ManaRecoverUpPotion.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.LakeSolidifiedSoul.get())
                , new ItemStack(ModItems.PlainSolidifiedSoul.get())
                , new ItemStack(ModItems.CooldownUpPotion.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.NetherSolidifiedSoul.get())
                , new ItemStack(ModItems.VolcanoSolidifiedSoul.get())
                , new ItemStack(ModItems.HealthStealUpPotion.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.ForestSolidifiedSoul.get())
                , new ItemStack(ModItems.EvokerSolidifiedSoul.get())
                , new ItemStack(ModItems.ManaDefenceUpPotion.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.ForestSolidifiedSoul.get())
                , new ItemStack(ModItems.ForestSolidifiedSoul.get())
                , new ItemStack(ModItems.DefenceUpPotion.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.SkySolidifiedSoul.get())
                , new ItemStack(ModItems.PlainSolidifiedSoul.get())
                , new ItemStack(ModItems.MovementSpeedUpPotion.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.PlainSolidifiedSoul.get())
                , new ItemStack(ModItems.PlainSolidifiedSoul.get())
                , new ItemStack(ModItems.HealthRecoverUpPotion.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.Pearl3.get())
                , new ItemStack(ModItems.Pearl3.get())
                , new ItemStack(ModItems.DamageEnhancePotion.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.Pearl4.get())
                , new ItemStack(ModItems.VolcanoSolidifiedSoul.get(), 16)
                , new ItemStack(ModItems.AttackDamageEnhancePotion.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.Pearl5.get())
                , new ItemStack(ModItems.EvokerSolidifiedSoul.get(), 16)
                , new ItemStack(ModItems.ManaDamageEnhancePotion.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.Pearl1.get())
                , new ItemStack(ModItems.PlainSolidifiedSoul.get(), 16)
                , new ItemStack(ModItems.GiantPotion.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.Pearl2.get())
                , new ItemStack(ModItems.ForestSolidifiedSoul.get(), 16)
                , new ItemStack(ModItems.StonePotion.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PurifiedWater.get())
                , new ItemStack(ModItems.Pearl6.get())
                , new ItemStack(ModItems.Boss2Piece.get(), 4)
                , new ItemStack(ModItems.ExHarvestPotion.get())));
    }

    public static ItemStack getOutput(ItemStack input, ItemStack material1, ItemStack material2) {
        if (recipeList.isEmpty()) setRecipeList();
        for (BrewingRecipe brewingRecipe : recipeList) {
            if (brewingRecipe.input.is(input.getItem()) && brewingRecipe.material1.is(material1.getItem())
                    && brewingRecipe.material2.is(material2.getItem()) && material1.getCount() >= brewingRecipe.material1.getCount()
                    && material2.getCount() >= brewingRecipe.material2.getCount())
                return brewingRecipe.output;
        }
        return null;
    }

    public static BrewingRecipe getRecipe(ItemStack input, ItemStack material1, ItemStack material2) {
        if (recipeList.isEmpty()) setRecipeList();
        for (BrewingRecipe brewingRecipe : recipeList) {
            if (brewingRecipe.input.is(input.getItem()) && brewingRecipe.material1.is(material1.getItem())
                    && brewingRecipe.material2.is(material2.getItem()) && material1.getCount() >= brewingRecipe.material1.getCount()
                    && material2.getCount() >= brewingRecipe.material2.getCount())
                return brewingRecipe;
        }
        return null;
    }

    public static List<Item> basicPotionList = new ArrayList<>();

    public static void setBasicPotionList() {
        basicPotionList.add(ModItems.AttackUpPotion.get());
        basicPotionList.add(ModItems.DefencePenetrationUpPotion.get());
        basicPotionList.add(ModItems.ManaPenetrationUpPotion.get());
        basicPotionList.add(ModItems.CooldownUpPotion.get());
        basicPotionList.add(ModItems.CritDamageUpPotion.get());

        basicPotionList.add(ModItems.CritRateUpPotion.get());
        basicPotionList.add(ModItems.DefenceUpPotion.get());
        basicPotionList.add(ModItems.HealthStealUpPotion.get());
        basicPotionList.add(ModItems.ManaDamageUpPotion.get());
        basicPotionList.add(ModItems.ManaDefenceUpPotion.get());

        basicPotionList.add(ModItems.ManaRecoverUpPotion.get());
        basicPotionList.add(ModItems.MovementSpeedUpPotion.get());
        basicPotionList.add(ModItems.HealthRecoverUpPotion.get());
    }

    public static Map<Item, Item> basicToConMap = new HashMap<>();
    public static Map<Item, Item> basicToLongMap = new HashMap<>();

    public static void setBasicToConMap() {
        basicToConMap.put(ModItems.AttackUpPotion.get(), ModItems.AttackUpConPotion.get());
        basicToConMap.put(ModItems.DefencePenetrationUpPotion.get(), ModItems.DefencePenetrationUpConPotion.get());
        basicToConMap.put(ModItems.ManaPenetrationUpPotion.get(), ModItems.ManaPenetrationUpConPotion.get());
        basicToConMap.put(ModItems.CooldownUpPotion.get(), ModItems.CooldownUpConPotion.get());
        basicToConMap.put(ModItems.CritDamageUpPotion.get(), ModItems.CritDamageUpConPotion.get());

        basicToConMap.put(ModItems.CritRateUpPotion.get(), ModItems.CritRateUpConPotion.get());
        basicToConMap.put(ModItems.DefenceUpPotion.get(), ModItems.DefenceUpConPotion.get());
        basicToConMap.put(ModItems.HealthStealUpPotion.get(), ModItems.HealthStealUpConPotion.get());
        basicToConMap.put(ModItems.ManaDamageUpPotion.get(), ModItems.ManaDamageUpConPotion.get());
        basicToConMap.put(ModItems.ManaDefenceUpPotion.get(), ModItems.ManaDefenceUpConPotion.get());

        basicToConMap.put(ModItems.ManaRecoverUpPotion.get(), ModItems.ManaRecoverUpConPotion.get());
        basicToConMap.put(ModItems.MovementSpeedUpPotion.get(), ModItems.MovementSpeedUpConPotion.get());
        basicToConMap.put(ModItems.HealthRecoverUpPotion.get(), ModItems.HealthRecoverUpConPotion.get());


    }

    public static void setBasicToLongMap() {
        basicToLongMap.put(ModItems.AttackUpPotion.get(), ModItems.AttackUpLongPotion.get());
        basicToLongMap.put(ModItems.DefencePenetrationUpPotion.get(), ModItems.DefencePenetrationUpLongPotion.get());
        basicToLongMap.put(ModItems.ManaPenetrationUpPotion.get(), ModItems.ManaPenetrationUpLongPotion.get());
        basicToLongMap.put(ModItems.CooldownUpPotion.get(), ModItems.CooldownUpLongPotion.get());
        basicToLongMap.put(ModItems.CritDamageUpPotion.get(), ModItems.CritDamageUpLongPotion.get());

        basicToLongMap.put(ModItems.CritRateUpPotion.get(), ModItems.CritRateUpLongPotion.get());
        basicToLongMap.put(ModItems.DefenceUpPotion.get(), ModItems.DefenceUpLongPotion.get());
        basicToLongMap.put(ModItems.HealthStealUpPotion.get(), ModItems.HealthStealUpLongPotion.get());
        basicToLongMap.put(ModItems.ManaDamageUpPotion.get(), ModItems.ManaDamageUpLongPotion.get());
        basicToLongMap.put(ModItems.ManaDefenceUpPotion.get(), ModItems.ManaDefenceUpLongPotion.get());

        basicToLongMap.put(ModItems.ManaRecoverUpPotion.get(), ModItems.ManaRecoverUpLongPotion.get());
        basicToLongMap.put(ModItems.MovementSpeedUpPotion.get(), ModItems.MovementSpeedUpLongPotion.get());
        basicToLongMap.put(ModItems.HealthRecoverUpPotion.get(), ModItems.HealthRecoverUpLongPotion.get());
    }

    public static Map<Item, Integer> outputNeedBrewingLevelMap = new HashMap<>();

    public static void setOutputNeedBrewingLevelMap() {
        outputNeedBrewingLevelMap.put(ModItems.DamageEnhancePotion.get(), 6);
        outputNeedBrewingLevelMap.put(ModItems.AttackDamageEnhancePotion.get(), 6);
        outputNeedBrewingLevelMap.put(ModItems.ManaDamageEnhancePotion.get(), 6);
        outputNeedBrewingLevelMap.put(ModItems.GiantPotion.get(), 6);
        outputNeedBrewingLevelMap.put(ModItems.StonePotion.get(), 6);
        outputNeedBrewingLevelMap.put(ModItems.ExHarvestPotion.get(), 6);
    }

    public static Item getLongPotionItem(Item originPotion) {
        String originName = originPotion.toString();
        int index = originName.indexOf("_potion");
        if (index == -1) return null;
        String longPotionName = originName.substring(0, index) + "_long_potion";
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(Utils.MOD_ID, longPotionName));
    }

    public static Item getConPotionItem(Item originPotion) {
        String originName = originPotion.toString();
        int index = originName.indexOf("_potion");
        if (index == -1) return null;
        String longPotionName = originName.substring(0, index) + "_con_potion";
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(Utils.MOD_ID, longPotionName));
    }
}
