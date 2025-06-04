package fun.wraq.blocks.blocks.brew;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
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
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.VOLCANO_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.VOLCANO_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.ATTACKUP_POTION.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.VOLCANO_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.FOREST_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.DEFENCE_PENETRATION_UP_POTION.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.SNOW_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.VOLCANO_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.CRIT_RATE_UP_POTION.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.VOLCANO_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.SNOW_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.CRIT_DAMAGE_UP_POTION.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.EVOKER_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.VOLCANO_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.MANA_DAMAGE_UP_POTION.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.EVOKER_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.FOREST_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.MANA_PENETRATION_UP_POTION.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.EVOKER_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.PLAIN_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.MANA_RECOVER_UP_POTION.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.LAKE_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.PLAIN_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.COOLDOWN_UP_POTION.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.NETHER_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.VOLCANO_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.HEALTH_STEAL_UP_POTION.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.FOREST_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.EVOKER_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.MANA_DEFENCE_UP_POTION.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.FOREST_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.FOREST_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.DEFENCE_UP_POTION.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.SKY_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.PLAIN_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.MOVEMENT_SPEED_UP_POTION.get())));
        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.PLAIN_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.PLAIN_SOLIDIFIED_SOUL.get())
                , new ItemStack(ModItems.HEALTH_RECOVER_UP_POTION.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.PEARL_3.get())
                , new ItemStack(ModItems.PEARL_3.get())
                , new ItemStack(ModItems.DAMAGE_ENHANCE_POTION.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.PEARL_4.get())
                , new ItemStack(ModItems.VOLCANO_SOLIDIFIED_SOUL.get(), 16)
                , new ItemStack(ModItems.ATTACK_DAMAGE_ENHANCE_POTION.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.PEARL_5.get())
                , new ItemStack(ModItems.EVOKER_SOLIDIFIED_SOUL.get(), 16)
                , new ItemStack(ModItems.MANA_DAMAGE_ENHANCE_POTION.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.PEARL_1.get())
                , new ItemStack(ModItems.PLAIN_SOLIDIFIED_SOUL.get(), 16)
                , new ItemStack(ModItems.GIANT_POTION.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.PEARL_2.get())
                , new ItemStack(ModItems.FOREST_SOLIDIFIED_SOUL.get(), 16)
                , new ItemStack(ModItems.STONE_POTION.get())));

        recipeList.add(new BrewingRecipe(new ItemStack(ModItems.PURIFIED_WATER.get())
                , new ItemStack(ModItems.PEARL_6.get())
                , new ItemStack(ModItems.Boss2Piece.get(), 4)
                , new ItemStack(ModItems.EX_HARVEST_POTION.get())));
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
        basicPotionList.add(ModItems.ATTACKUP_POTION.get());
        basicPotionList.add(ModItems.DEFENCE_PENETRATION_UP_POTION.get());
        basicPotionList.add(ModItems.MANA_PENETRATION_UP_POTION.get());
        basicPotionList.add(ModItems.COOLDOWN_UP_POTION.get());
        basicPotionList.add(ModItems.CRIT_DAMAGE_UP_POTION.get());

        basicPotionList.add(ModItems.CRIT_RATE_UP_POTION.get());
        basicPotionList.add(ModItems.DEFENCE_UP_POTION.get());
        basicPotionList.add(ModItems.HEALTH_STEAL_UP_POTION.get());
        basicPotionList.add(ModItems.MANA_DAMAGE_UP_POTION.get());
        basicPotionList.add(ModItems.MANA_DEFENCE_UP_POTION.get());

        basicPotionList.add(ModItems.MANA_RECOVER_UP_POTION.get());
        basicPotionList.add(ModItems.MOVEMENT_SPEED_UP_POTION.get());
        basicPotionList.add(ModItems.HEALTH_RECOVER_UP_POTION.get());
    }

    public static Map<Item, Item> basicToConMap = new HashMap<>();
    public static Map<Item, Item> basicToLongMap = new HashMap<>();

    public static void setBasicToConMap() {
        basicToConMap.put(ModItems.ATTACKUP_POTION.get(), ModItems.ATTACKUP_CON_POTION.get());
        basicToConMap.put(ModItems.DEFENCE_PENETRATION_UP_POTION.get(), ModItems.DEFENCE_PENETRATION_UP_CON_POTION.get());
        basicToConMap.put(ModItems.MANA_PENETRATION_UP_POTION.get(), ModItems.MANA_PENETRATION_UP_CON_POTION.get());
        basicToConMap.put(ModItems.COOLDOWN_UP_POTION.get(), ModItems.COOLDOWN_UP_CON_POTION.get());
        basicToConMap.put(ModItems.CRIT_DAMAGE_UP_POTION.get(), ModItems.CRIT_DAMAGE_UP_CON_POTION.get());

        basicToConMap.put(ModItems.CRIT_RATE_UP_POTION.get(), ModItems.CRIT_RATE_UP_CON_POTION.get());
        basicToConMap.put(ModItems.DEFENCE_UP_POTION.get(), ModItems.DEFENCE_UP_CON_POTION.get());
        basicToConMap.put(ModItems.HEALTH_STEAL_UP_POTION.get(), ModItems.HEALTH_STEAL_UP_CON_POTION.get());
        basicToConMap.put(ModItems.MANA_DAMAGE_UP_POTION.get(), ModItems.MANA_DAMAGE_UP_CON_POTION.get());
        basicToConMap.put(ModItems.MANA_DEFENCE_UP_POTION.get(), ModItems.MANA_DEFENCE_UP_CON_POTION.get());

        basicToConMap.put(ModItems.MANA_RECOVER_UP_POTION.get(), ModItems.MANA_RECOVER_UP_CON_POTION.get());
        basicToConMap.put(ModItems.MOVEMENT_SPEED_UP_POTION.get(), ModItems.MOVEMENT_SPEED_UP_CON_POTION.get());
        basicToConMap.put(ModItems.HEALTH_RECOVER_UP_POTION.get(), ModItems.HEALTH_RECOVER_UP_CON_POTION.get());


    }

    public static void setBasicToLongMap() {
        basicToLongMap.put(ModItems.ATTACKUP_POTION.get(), ModItems.ATTACKUP_LONG_POTION.get());
        basicToLongMap.put(ModItems.DEFENCE_PENETRATION_UP_POTION.get(), ModItems.DEFENCE_PENETRATION_UP_LONG_POTION.get());
        basicToLongMap.put(ModItems.MANA_PENETRATION_UP_POTION.get(), ModItems.MANA_PENETRATION_UP_LONG_POTION.get());
        basicToLongMap.put(ModItems.COOLDOWN_UP_POTION.get(), ModItems.COOLDOWN_UP_LONG_POTION.get());
        basicToLongMap.put(ModItems.CRIT_DAMAGE_UP_POTION.get(), ModItems.CRIT_DAMAGE_UP_LONG_POTION.get());

        basicToLongMap.put(ModItems.CRIT_RATE_UP_POTION.get(), ModItems.CRIT_RATE_UP_LONG_POTION.get());
        basicToLongMap.put(ModItems.DEFENCE_UP_POTION.get(), ModItems.DEFENCE_UP_LONG_POTION.get());
        basicToLongMap.put(ModItems.HEALTH_STEAL_UP_POTION.get(), ModItems.HEALTH_STEAL_UP_LONG_POTION.get());
        basicToLongMap.put(ModItems.MANA_DAMAGE_UP_POTION.get(), ModItems.MANA_DAMAGE_UP_LONG_POTION.get());
        basicToLongMap.put(ModItems.MANA_DEFENCE_UP_POTION.get(), ModItems.MANA_DEFENCE_UP_LONG_POTION.get());

        basicToLongMap.put(ModItems.MANA_RECOVER_UP_POTION.get(), ModItems.MANA_RECOVER_UP_LONG_POTION.get());
        basicToLongMap.put(ModItems.MOVEMENT_SPEED_UP_POTION.get(), ModItems.MOVEMENT_SPEED_UP_LONG_POTION.get());
        basicToLongMap.put(ModItems.HEALTH_RECOVER_UP_POTION.get(), ModItems.HEALTH_RECOVER_UP_LONG_POTION.get());
    }

    public static Map<Item, Integer> outputNeedBrewingLevelMap = new HashMap<>();

    public static void setOutputNeedBrewingLevelMap() {
        outputNeedBrewingLevelMap.put(ModItems.DAMAGE_ENHANCE_POTION.get(), 6);
        outputNeedBrewingLevelMap.put(ModItems.ATTACK_DAMAGE_ENHANCE_POTION.get(), 6);
        outputNeedBrewingLevelMap.put(ModItems.MANA_DAMAGE_ENHANCE_POTION.get(), 6);
        outputNeedBrewingLevelMap.put(ModItems.GIANT_POTION.get(), 6);
        outputNeedBrewingLevelMap.put(ModItems.STONE_POTION.get(), 6);
        outputNeedBrewingLevelMap.put(ModItems.EX_HARVEST_POTION.get(), 6);
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
