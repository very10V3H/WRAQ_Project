package fun.wraq.render.gui.villagerTrade;

import club.someoneice.cofe_delight.init.ItemInit;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.cooking.CookingItems;
import fun.wraq.process.system.cooking.CookingValue;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.comsumable.ComsumableItems;
import fun.wraq.series.crystal.CrystalItem;
import fun.wraq.series.crystal.CrystalItems;
import fun.wraq.series.crystal.OriginStone;
import fun.wraq.series.events.summer2025.Summer2025StoreRecipe;
import fun.wraq.series.overworld.chapter7.C7Items;
import fun.wraq.series.overworld.cold.SuperColdItems;
import fun.wraq.series.overworld.cold.sc5.dragon.IceDragonTpUtil;
import net.brdle.collectorsreap.common.item.CRItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class TradeListNew {

    public ItemStack product;
    public List<ItemStack> material;

    public TradeListNew(ItemStack product, List<ItemStack> material) {
        this.product = product;
        this.material = material;
    }

    public static void setVillagerData(String displayName, String codeName, Style style,
                                       VillagerType villagerType, VillagerProfession profession,
                                       List<TradeListNew> list) {
        List<ItemStack> stackList = new ArrayList<>();
        list.forEach(trade -> {
            stackList.add(trade.product);
            TradeList.tradeRecipeMap.put(trade.product, trade.material);
        });
        MyVillagerData.setMyVillagerData(displayName, codeName, style, villagerType, profession, stackList);
    }

    public static void init() {
        cookingCrop();
        cookingFruit();
        cookingMushroom();
        cookingMilkAndEgg();
        cookingMeat();
        cookingSeafood();
        cookingSpecial();
        cookingTools();
        cooking();
        dragonBoat();
        weeklyStore();
        OriginStone.setVillagerData();
        colorLossCrystal();
        mapleKnife();
        firCrystal();
        blizzard();
        coldIron();
        outpostEnergy();
        iceDragon();
        IceDragonTpUtil.setVillagerData();
        Summer2025StoreRecipe.setVillagerTradeRecipe();
    }

    public static List<ItemStack> getCoinList(int vb) {
        List<ItemStack> stacks = new ArrayList<>();
        if (vb / 144 > 0) {
            stacks.add(new ItemStack(ModItems.GOLD_COIN.get(), vb / 144));
        }
        if (vb % 144 / 12 > 0) {
            stacks.add(new ItemStack(ModItems.SILVER_COIN.get(), vb % 144 / 12));
        }
        if (vb % 12 > 0) {
            stacks.add(new ItemStack(ModItems.COPPER_COIN.get(), vb % 12));
        }
        return stacks;
    }

    public static void cookingCrop() {
        List<ItemStack> stacks = CookingItems.getCropSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("作物(制品)商人", "cookingCrop",
                CustomStyle.styleOfField, VillagerType.PLAINS, VillagerProfession.FARMER, stacks);
        for (ItemStack stack : stacks) {
            TradeList.tradeRecipeMap.put(stack, getCoinList(CookingValue.getIngredientValue(stack.getItem()) * 16));
        }
    }

    public static void cookingFruit() {
        List<ItemStack> stacks = CookingItems.getFruitSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("水果(制品)商人", "cookingFruit",
                CustomStyle.styleOfLife, VillagerType.JUNGLE, VillagerProfession.FARMER, stacks);
        for (ItemStack stack : stacks) {
            int value = CookingValue.getIngredientValue(stack.getItem());
            if (value == 0) {
                if (stack.is(Items.MELON)) {
                    value = 36 * 9;
                } else if (stack.is(CRItems.POMEGRANATE.get())) {
                    value = 36 * 4;
                }
            }
            TradeList.tradeRecipeMap.put(stack, getCoinList(value * 16));
        }
    }

    public static void cookingMushroom() {
        List<ItemStack> stacks = CookingItems.getMushroomSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("菌类商人", "cookingMushroom",
                CustomStyle.MUSHROOM_STYLE, VillagerType.JUNGLE, VillagerProfession.MASON, stacks);
        for (ItemStack stack : stacks) {
            TradeList.tradeRecipeMap.put(stack, getCoinList(CookingValue.getIngredientValue(stack.getItem()) * 16));
        }
    }

    public static void cookingMilkAndEgg() {
        List<ItemStack> stacks = CookingItems.getMilkAndEggSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("蛋奶商人", "cookingMilkAndEgg",
                Style.EMPTY.applyFormat(ChatFormatting.WHITE), VillagerType.JUNGLE, VillagerProfession.BUTCHER, stacks);
        for (ItemStack stack : stacks) {
            TradeList.tradeRecipeMap.put(stack, getCoinList(CookingValue.getIngredientValue(stack.getItem()) * 16));
        }
    }

    public static void cookingMeat() {
        List<ItemStack> stacks = CookingItems.getMeatSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("肉类商人", "cookingMeat",
                CustomStyle.styleOfLife, VillagerType.PLAINS, VillagerProfession.BUTCHER, stacks);
        for (ItemStack stack : stacks) {
            TradeList.tradeRecipeMap.put(stack, getCoinList(CookingValue.getIngredientValue(stack.getItem()) * 16));
        }
    }

    public static void cookingSeafood() {
        List<ItemStack> stacks = CookingItems.getSeafoodSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("水产商人", "cookingSeafood",
                CustomStyle.styleOfWater, VillagerType.SWAMP, VillagerProfession.FISHERMAN, stacks);
        for (ItemStack stack : stacks) {
            int value = CookingValue.getIngredientValue(stack.getItem());
            if (value == 0) {
                if (stack.is(CRItems.TIGER_PRAWN.get())) {
                    value = 144;
                }
            }
            TradeList.tradeRecipeMap.put(stack, getCoinList(value * 16));
        }
    }

    public static void cookingSpecial() {
        List<ItemStack> stacks = CookingItems.getSpecialSellingList()
                .stream().map(item -> new ItemStack(item, 16))
                .toList();
        MyVillagerData.setMyVillagerData("特殊商人", "cookingSpecial",
                CustomStyle.styleOfFantasy, VillagerType.TAIGA, VillagerProfession.LIBRARIAN, stacks);
        for (ItemStack stack : stacks) {
            TradeList.tradeRecipeMap.put(stack, getCoinList(CookingValue.getIngredientValue(stack.getItem()) * 16));
        }
    }

    public static void cookingTools() {
        ItemStack ironKnife = new ItemStack(vectorwing.farmersdelight.common.registry.ModItems.IRON_KNIFE.get());
        ItemStack goldenKnife = new ItemStack(vectorwing.farmersdelight.common.registry.ModItems.GOLDEN_KNIFE.get());
        ItemStack glassBottle = new ItemStack(Items.GLASS_BOTTLE, 16);
        ItemStack bowl = new ItemStack(Items.BOWL, 16);
        ItemStack clever = new ItemStack(ItemInit.Clever.get(), 16);
        ItemStack cup = new ItemStack(ItemInit.CUP.get(), 16);
        ItemStack mug = new ItemStack(ItemInit.MUG.get(), 16);
        ItemStack cauldron = new ItemStack(Items.CAULDRON, 16);
        ItemStack bucket = new ItemStack(Items.BUCKET, 16);
        ItemStack stick = new ItemStack(Items.STICK, 64);
        List<ItemStack> stacks = List.of(
                ironKnife, goldenKnife,
                glassBottle, bowl, clever, cup, mug,
                cauldron, bucket
        );
        MyVillagerData.setMyVillagerData("餐具商人", "cookingTools",
                CustomStyle.MUSHROOM_STYLE, VillagerType.PLAINS, VillagerProfession.TOOLSMITH, stacks);
        TradeList.tradeRecipeMap.put(ironKnife, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 16));
        }});
        TradeList.tradeRecipeMap.put(goldenKnife, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 999));
        }});
        TradeList.tradeRecipeMap.put(glassBottle, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
        }});
        TradeList.tradeRecipeMap.put(bowl, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
        }});
        TradeList.tradeRecipeMap.put(clever, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
        }});
        TradeList.tradeRecipeMap.put(cup, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
        }});
        TradeList.tradeRecipeMap.put(mug, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 1));
        }});
        TradeList.tradeRecipeMap.put(cauldron, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 16));
        }});
        TradeList.tradeRecipeMap.put(bucket, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 16));
        }});
        TradeList.tradeRecipeMap.put(stick, new ArrayList<>() {{
            add(new ItemStack(ModItems.GOLD_COIN.get(), 4));
        }});
    }

    public static void cooking() {
        MyVillagerData.setMyVillagerData("联合研院大吃货 - 老八",
                "cooking", CustomStyle.MUSHROOM_STYLE, VillagerType.SNOW,
                VillagerProfession.LIBRARIAN, List.of());
    }

    public static void dragonBoat() {
        MyVillagerData.setMyVillagerData("粽子大王",
                "dragonBoat", CustomStyle.DRAGON_BOAT_FES_STYLE, VillagerType.JUNGLE,
                VillagerProfession.BUTCHER, List.of());
    }

    public static final String WEEKLY_STORE_VILLAGER_NAME = "联合研院采购";

    public static void weeklyStore() {
        MyVillagerData.setMyVillagerData(WEEKLY_STORE_VILLAGER_NAME,
                "weeklyStore", CustomStyle.styleOfWorld, VillagerType.SAVANNA,
                VillagerProfession.LIBRARIAN, List.of());
    }

    public static List<ItemStack> getWool(CrystalItem crystalItem) {
        List<ItemStack> woolStacks = new ArrayList<>();
        switch (crystalItem.typeTier) {
            default -> {
                woolStacks.add(new ItemStack(SuperColdItems.GREEN_WOOL_POCKET.get(),
                        (int) Math.pow(2, crystalItem.tier)));
            }
            case 1 -> {
                woolStacks.add(new ItemStack(SuperColdItems.BLUE_WOOL_POCKET.get(),
                        (int) Math.pow(2, crystalItem.tier)));
            }
            case 2 -> {
                woolStacks.add(new ItemStack(SuperColdItems.YELLOW_WOOL_POCKET.get(),
                        (int) Math.pow(2, crystalItem.tier)));
            }
            case 3 -> {
                woolStacks.add(new ItemStack(SuperColdItems.RED_WOOL_POCKET.get(),
                        (int) Math.pow(2, crystalItem.tier)));
            }
            case 4 -> {
                woolStacks.add(new ItemStack(SuperColdItems.PURPLE_WOOL_POCKET.get(),
                        (int) Math.pow(2, crystalItem.tier)));
            }
        }
        return woolStacks;
    }

    public static void colorLossCrystal() {
        List<ItemStack> stacks = new ArrayList<>();
        CrystalItem.list.forEach(crystalItem -> {
            ItemStack colorLossCrystal = new ItemStack(CrystalItems.COLOR_LOSS_CRYSTAL.get(), crystalItem.getRate());
            stacks.add(colorLossCrystal);
            TradeList.tradeRecipeMap.put(colorLossCrystal, List.of(new ItemStack(crystalItem)));
            ItemStack colorLossCrystalCopyStack = colorLossCrystal.copy();
            ItemStack crystalStack = new ItemStack(crystalItem);
            List<ItemStack> needStacks = new ArrayList<>();
            needStacks.add(colorLossCrystalCopyStack);
            needStacks.addAll(getWool(crystalItem));
            stacks.add(crystalStack);
            TradeList.tradeRecipeMap.put(crystalStack, needStacks);
        });
        MyVillagerData.setMyVillagerData("失色宝石商人", "colorLossCrystal",
                CustomStyle.styleOfIce, VillagerType.SNOW, VillagerProfession.CARTOGRAPHER, stacks);
    }

    public static void mapleKnife() {
        ItemStack snowRune = new ItemStack(ModItems.SNOW_RUNE.get());
        ItemStack coldRune = new ItemStack(SuperColdItems.COLD_RUNE.get());
        ItemStack mapleRune = new ItemStack(SuperColdItems.MAPLE_RUNE.get());
        ItemStack mapleRuneByStar = new ItemStack(SuperColdItems.MAPLE_RUNE.get());
        ItemStack mapleKnife0 = new ItemStack(SuperColdItems.MAPLE_KNIFE_0.get());
        ItemStack mapleKnife1 = new ItemStack(SuperColdItems.MAPLE_KNIFE_1.get());
        ItemStack mapleKnife2 = new ItemStack(SuperColdItems.MAPLE_KNIFE_2.get());
        ItemStack mapleKnife3 = new ItemStack(SuperColdItems.MAPLE_KNIFE_3.get());
        ItemStack coldCrystal = new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get());
        List<ItemStack> stacks = List.of(
                snowRune, coldRune, mapleRune, mapleRuneByStar,
                mapleKnife0, mapleKnife1, mapleKnife2, mapleKnife3, coldCrystal
        );
        MyVillagerData.setMyVillagerData("枫林护林员", "mapleKnife",
                CustomStyle.styleOfIce, VillagerType.SNOW, VillagerProfession.WEAPONSMITH, stacks);
        TradeList.tradeRecipeMap.put(snowRune,
                List.of(new ItemStack(ModItems.SNOW_SOUL.get(), 64),
                        new ItemStack(ModItems.GOLD_COIN.get(), 3),
                        new ItemStack(ModItems.GEM_PIECE.get(), 1)));
        TradeList.tradeRecipeMap.put(coldRune,
                List.of(new ItemStack(ModItems.SNOW_RUNE.get(), 64),
                        new ItemStack(ModItems.ICE_ELEMENT_PIECE_2.get(), 1),
                        new ItemStack(ModItems.WORLD_SOUL_3.get(), 1)));
        TradeList.tradeRecipeMap.put(mapleRune,
                List.of(new ItemStack(SuperColdItems.MAPLE_SOUL.get(), 100),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 1),
                        new ItemStack(ModItems.REPUTATION_MEDAL.get(), 1),
                        new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 1)));
        TradeList.tradeRecipeMap.put(mapleRuneByStar,
                List.of(new ItemStack(SuperColdItems.MAPLE_SOUL.get(), 100),
                        new ItemStack(ModItems.WORLD_SOUL_5.get(), 20)));
        TradeList.tradeRecipeMap.put(mapleKnife0,
                List.of(new ItemStack(SuperColdItems.MAPLE_RUNE.get(), 10)));
        TradeList.tradeRecipeMap.put(mapleKnife1,
                List.of(new ItemStack(SuperColdItems.MAPLE_KNIFE_0.get(), 1),
                        new ItemStack(SuperColdItems.MAPLE_RUNE.get(), 15)));
        TradeList.tradeRecipeMap.put(mapleKnife2,
                List.of(new ItemStack(SuperColdItems.MAPLE_KNIFE_1.get(), 1),
                        new ItemStack(SuperColdItems.MAPLE_RUNE.get(), 30)));
        TradeList.tradeRecipeMap.put(mapleKnife3,
                List.of(new ItemStack(SuperColdItems.MAPLE_KNIFE_2.get(), 1),
                        new ItemStack(SuperColdItems.MAPLE_RUNE.get(), 50),
                        new ItemStack(SuperColdItems.COLD_RUNE.get(), 8)));
        TradeList.tradeRecipeMap.put(coldCrystal,
                List.of(new ItemStack(SuperColdItems.MAPLE_RUNE.get(), 1),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_C.get(), 1)));
    }

    public static void firCrystal() {
        ItemStack snowRune = new ItemStack(ModItems.SNOW_RUNE.get());
        ItemStack coldRune = new ItemStack(SuperColdItems.COLD_RUNE.get());
        ItemStack firRune = new ItemStack(SuperColdItems.FIR_RUNE.get());
        ItemStack firRuneByStar = new ItemStack(SuperColdItems.FIR_RUNE.get());
        ItemStack firCrystal0 = new ItemStack(SuperColdItems.FIR_CRYSTAL_0.get());
        ItemStack firCrystal1 = new ItemStack(SuperColdItems.FIR_CRYSTAL_1.get());
        ItemStack firCrystal2 = new ItemStack(SuperColdItems.FIR_CRYSTAL_2.get());
        ItemStack firCrystal3 = new ItemStack(SuperColdItems.FIR_CRYSTAL_3.get());
        ItemStack coldCrystal = new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get());
        List<ItemStack> stacks = List.of(
                snowRune, coldRune, firRune, firRuneByStar,
                firCrystal0, firCrystal1, firCrystal2, firCrystal3,
                coldCrystal
        );
        MyVillagerData.setMyVillagerData("冷杉林护林员", "firCrystal",
                CustomStyle.styleOfIce, VillagerType.SNOW, VillagerProfession.WEAPONSMITH, stacks);
        TradeList.tradeRecipeMap.put(snowRune,
                List.of(new ItemStack(ModItems.SNOW_SOUL.get(), 64),
                        new ItemStack(ModItems.GOLD_COIN.get(), 3),
                        new ItemStack(ModItems.GEM_PIECE.get(), 1)));
        TradeList.tradeRecipeMap.put(coldRune,
                List.of(new ItemStack(ModItems.SNOW_RUNE.get(), 64),
                        new ItemStack(ModItems.ICE_ELEMENT_PIECE_2.get(), 1),
                        new ItemStack(ModItems.WORLD_SOUL_3.get(), 1)));
        TradeList.tradeRecipeMap.put(firRune,
                List.of(new ItemStack(SuperColdItems.FIR_SOUL.get(), 100),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 1),
                        new ItemStack(ModItems.REPUTATION_MEDAL.get(), 1),
                        new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 1)));
        TradeList.tradeRecipeMap.put(firRuneByStar,
                List.of(new ItemStack(SuperColdItems.FIR_SOUL.get(), 100),
                        new ItemStack(ModItems.WORLD_SOUL_5.get(), 20)));
        TradeList.tradeRecipeMap.put(firCrystal0,
                List.of(new ItemStack(SuperColdItems.FIR_RUNE.get(), 10)));
        TradeList.tradeRecipeMap.put(firCrystal1,
                List.of(new ItemStack(SuperColdItems.FIR_CRYSTAL_0.get(), 1),
                        new ItemStack(SuperColdItems.FIR_RUNE.get(), 15)));
        TradeList.tradeRecipeMap.put(firCrystal2,
                List.of(new ItemStack(SuperColdItems.FIR_CRYSTAL_1.get(), 1),
                        new ItemStack(SuperColdItems.FIR_RUNE.get(), 30)));
        TradeList.tradeRecipeMap.put(firCrystal3,
                List.of(new ItemStack(SuperColdItems.FIR_CRYSTAL_2.get(), 1),
                        new ItemStack(SuperColdItems.FIR_RUNE.get(), 50),
                        new ItemStack(SuperColdItems.COLD_RUNE.get(), 8)));
        TradeList.tradeRecipeMap.put(coldCrystal,
                List.of(new ItemStack(SuperColdItems.FIR_RUNE.get(), 1),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_C.get(), 1)));
    }

    public static void blizzard() {
        ItemStack snowRune = new ItemStack(ModItems.SNOW_RUNE.get());
        ItemStack coldRune = new ItemStack(SuperColdItems.COLD_RUNE.get());
        ItemStack blizzardRune = new ItemStack(SuperColdItems.BLIZZARD_RUNE.get());
        ItemStack blizzardRuneByStar = new ItemStack(SuperColdItems.BLIZZARD_RUNE.get());
        ItemStack blizzardBoots0 = new ItemStack(SuperColdItems.BLIZZARD_BOOTS_0.get());
        ItemStack blizzardBoots1 = new ItemStack(SuperColdItems.BLIZZARD_BOOTS_1.get());
        ItemStack blizzardBoots2 = new ItemStack(SuperColdItems.BLIZZARD_BOOTS_2.get());
        ItemStack coldCrystal = new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get());
        List<ItemStack> stacks = List.of(
                snowRune, coldRune, blizzardRune, blizzardRuneByStar,
                blizzardBoots0, blizzardBoots1, blizzardBoots2,
                coldCrystal
        );
        MyVillagerData.setMyVillagerData("吹雪", "blizzard",
                CustomStyle.styleOfIce, VillagerType.SNOW, VillagerProfession.ARMORER, stacks);
        TradeList.tradeRecipeMap.put(snowRune,
                List.of(new ItemStack(ModItems.SNOW_SOUL.get(), 64),
                        new ItemStack(ModItems.GOLD_COIN.get(), 3),
                        new ItemStack(ModItems.GEM_PIECE.get(), 1)));
        TradeList.tradeRecipeMap.put(coldRune,
                List.of(new ItemStack(ModItems.SNOW_RUNE.get(), 64),
                        new ItemStack(ModItems.ICE_ELEMENT_PIECE_2.get(), 1),
                        new ItemStack(ModItems.WORLD_SOUL_3.get(), 1)));
        TradeList.tradeRecipeMap.put(blizzardRune,
                List.of(new ItemStack(SuperColdItems.BLIZZARD_SOUL.get(), 100),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 1),
                        new ItemStack(ModItems.REPUTATION_MEDAL.get(), 1),
                        new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 1)));
        TradeList.tradeRecipeMap.put(blizzardRuneByStar,
                List.of(new ItemStack(SuperColdItems.BLIZZARD_SOUL.get(), 100),
                        new ItemStack(ModItems.WORLD_SOUL_5.get(), 20)));
        TradeList.tradeRecipeMap.put(blizzardBoots0,
                List.of(new ItemStack(SuperColdItems.BLIZZARD_RUNE.get(), 30),
                        new ItemStack(SuperColdItems.COLD_RUNE.get(), 2)));
        TradeList.tradeRecipeMap.put(blizzardBoots1,
                List.of(new ItemStack(SuperColdItems.BLIZZARD_BOOTS_0.get(), 1),
                        new ItemStack(SuperColdItems.BLIZZARD_RUNE.get(), 35),
                        new ItemStack(SuperColdItems.COLD_RUNE.get(), 2)));
        TradeList.tradeRecipeMap.put(blizzardBoots2,
                List.of(new ItemStack(SuperColdItems.BLIZZARD_BOOTS_1.get(), 1),
                        new ItemStack(SuperColdItems.BLIZZARD_RUNE.get(), 45),
                        new ItemStack(SuperColdItems.COLD_RUNE.get(), 3)));
        TradeList.tradeRecipeMap.put(coldCrystal,
                List.of(new ItemStack(SuperColdItems.BLIZZARD_RUNE.get(), 1),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_C.get(), 1)));
    }

    public static void coldIron() {
        ItemStack snowRune = new ItemStack(ModItems.SNOW_RUNE.get());
        ItemStack coldRune = new ItemStack(SuperColdItems.COLD_RUNE.get());
        ItemStack coldIronRune = new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get());
        ItemStack coldIronRuneByStar = new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get());
        ItemStack coldIronHelmet0 = new ItemStack(SuperColdItems.COLD_IRON_HELMET_0.get());
        ItemStack coldIronHelmet1 = new ItemStack(SuperColdItems.COLD_IRON_HELMET_1.get());
        ItemStack coldIronHelmet2 = new ItemStack(SuperColdItems.COLD_IRON_HELMET_2.get());
        ItemStack coldIronChest0 = new ItemStack(SuperColdItems.COLD_IRON_CHEST_0.get());
        ItemStack coldIronChest1 = new ItemStack(SuperColdItems.COLD_IRON_CHEST_1.get());
        ItemStack coldIronChest2 = new ItemStack(SuperColdItems.COLD_IRON_CHEST_2.get());
        ItemStack coldIronLeggings0 = new ItemStack(SuperColdItems.COLD_IRON_LEGGINGS_0.get());
        ItemStack coldIronLeggings1 = new ItemStack(SuperColdItems.COLD_IRON_LEGGINGS_1.get());
        ItemStack coldIronLeggings2 = new ItemStack(SuperColdItems.COLD_IRON_LEGGINGS_2.get());
        ItemStack coldCrystal = new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get());
        List<ItemStack> stacks = List.of(
                snowRune, coldRune, coldIronRune, coldIronRuneByStar,
                coldIronHelmet0, coldIronHelmet1, coldIronHelmet2,
                coldIronChest0, coldIronChest1, coldIronChest2,
                coldIronLeggings0, coldIronLeggings1, coldIronLeggings2,
                coldCrystal
        );
        MyVillagerData.setMyVillagerData("寒铁", "coldIron",
                CustomStyle.styleOfIce, VillagerType.SNOW, VillagerProfession.ARMORER, stacks);
        TradeList.tradeRecipeMap.put(snowRune,
                List.of(new ItemStack(ModItems.SNOW_SOUL.get(), 64),
                        new ItemStack(ModItems.GOLD_COIN.get(), 3),
                        new ItemStack(ModItems.GEM_PIECE.get(), 1)));
        TradeList.tradeRecipeMap.put(coldRune,
                List.of(new ItemStack(ModItems.SNOW_RUNE.get(), 64),
                        new ItemStack(ModItems.ICE_ELEMENT_PIECE_2.get(), 1),
                        new ItemStack(ModItems.WORLD_SOUL_3.get(), 1)));
        TradeList.tradeRecipeMap.put(coldIronRune,
                List.of(new ItemStack(SuperColdItems.COLD_IRON_GOLEM_SOUL.get(), 100),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 1),
                        new ItemStack(ModItems.REPUTATION_MEDAL.get(), 1),
                        new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 1)));
        TradeList.tradeRecipeMap.put(coldIronRuneByStar,
                List.of(new ItemStack(SuperColdItems.COLD_IRON_GOLEM_SOUL.get(), 100),
                        new ItemStack(ModItems.WORLD_SOUL_5.get(), 20)));
        TradeList.tradeRecipeMap.put(coldIronHelmet0,
                List.of(new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get(), 20)));
        TradeList.tradeRecipeMap.put(coldIronHelmet1,
                List.of(new ItemStack(SuperColdItems.COLD_IRON_HELMET_0.get(), 1),
                        new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get(), 30)));
        TradeList.tradeRecipeMap.put(coldIronHelmet2,
                List.of(new ItemStack(SuperColdItems.COLD_IRON_HELMET_1.get(), 1),
                        new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get(), 55),
                        new ItemStack(SuperColdItems.COLD_RUNE.get(), 8)));
        TradeList.tradeRecipeMap.put(coldIronChest0,
                List.of(new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get(), 20)));
        TradeList.tradeRecipeMap.put(coldIronChest1,
                List.of(new ItemStack(SuperColdItems.COLD_IRON_CHEST_0.get(), 1),
                        new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get(), 30)));
        TradeList.tradeRecipeMap.put(coldIronChest2,
                List.of(new ItemStack(SuperColdItems.COLD_IRON_CHEST_1.get(), 1),
                        new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get(), 55),
                        new ItemStack(SuperColdItems.COLD_RUNE.get(), 8)));
        TradeList.tradeRecipeMap.put(coldIronLeggings0,
                List.of(new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get(), 20)));
        TradeList.tradeRecipeMap.put(coldIronLeggings1,
                List.of(new ItemStack(SuperColdItems.COLD_IRON_LEGGINGS_0.get(), 1),
                        new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get(), 30)));
        TradeList.tradeRecipeMap.put(coldIronLeggings2,
                List.of(new ItemStack(SuperColdItems.COLD_IRON_LEGGINGS_1.get(), 1),
                        new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get(), 55),
                        new ItemStack(SuperColdItems.COLD_RUNE.get(), 8)));
        TradeList.tradeRecipeMap.put(coldCrystal,
                List.of(new ItemStack(SuperColdItems.COLD_RUNE.get(), 1),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_C.get(), 1)));
    }

    public static void outpostEnergy() {
        ItemStack magmaSoul = new ItemStack(ModItems.MAGMA_SOUL.get());
        ItemStack heatInjection0 = new ItemStack(ComsumableItems.HEAT_INJECTION_0.get());
        ItemStack heatInjection1 = new ItemStack(ComsumableItems.HEAT_INJECTION_1.get());
        ItemStack heatInjection2 = new ItemStack(ComsumableItems.HEAT_INJECTION_2.get());
        ItemStack heatInjection3 = new ItemStack(ComsumableItems.HEAT_INJECTION_3.get());
        ItemStack heatInjection0RoseCoin = new ItemStack(ComsumableItems.HEAT_INJECTION_0.get());
        ItemStack heatInjection1RoseCoin = new ItemStack(ComsumableItems.HEAT_INJECTION_1.get());
        ItemStack heatInjection2RoseCoin = new ItemStack(ComsumableItems.HEAT_INJECTION_2.get());
        ItemStack heatInjection3RoseCoin = new ItemStack(ComsumableItems.HEAT_INJECTION_3.get());
        ItemStack heatDevice0 = new ItemStack(ComsumableItems.HEAT_DEVICE_0.get());
        ItemStack heatDevice1 = new ItemStack(ComsumableItems.HEAT_DEVICE_1.get());
        ItemStack heatDevice2 = new ItemStack(ComsumableItems.HEAT_DEVICE_2.get());
        ItemStack heatDevice0RoseCoin = new ItemStack(ComsumableItems.HEAT_DEVICE_0.get());
        ItemStack heatDevice1RoseCoin = new ItemStack(ComsumableItems.HEAT_DEVICE_1.get());
        ItemStack heatDevice2RoseCoin = new ItemStack(ComsumableItems.HEAT_DEVICE_2.get());
        List<ItemStack> stacks = List.of(
                magmaSoul,
                heatInjection0RoseCoin, heatInjection1RoseCoin, heatInjection2RoseCoin, heatInjection3RoseCoin,
                heatInjection0, heatInjection1, heatInjection2, heatInjection3,
                heatDevice0RoseCoin, heatDevice1RoseCoin, heatDevice2RoseCoin,
                heatDevice0, heatDevice1, heatDevice2
        );
        MyVillagerData.setMyVillagerData("极寒前哨所能源学者", "outpostEnergy",
                CustomStyle.BUNKER_STYLE, VillagerType.SAVANNA, VillagerProfession.TOOLSMITH, stacks);
        TradeList.tradeRecipeMap.put(magmaSoul,
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 1)));
        TradeList.tradeRecipeMap.put(heatInjection0RoseCoin,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 40)));
        TradeList.tradeRecipeMap.put(heatInjection1RoseCoin,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 60)));
        TradeList.tradeRecipeMap.put(heatInjection2RoseCoin,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 80)));
        TradeList.tradeRecipeMap.put(heatInjection3RoseCoin,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 100)));
        TradeList.tradeRecipeMap.put(heatDevice0RoseCoin,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 30)));
        TradeList.tradeRecipeMap.put(heatDevice1RoseCoin,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 40)));
        TradeList.tradeRecipeMap.put(heatDevice2RoseCoin,
                List.of(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 50)));

        TradeList.tradeRecipeMap.put(heatInjection0,
                List.of(new ItemStack(ModItems.WORLD_SOUL_5.get(), 40)));
        TradeList.tradeRecipeMap.put(heatInjection1,
                List.of(new ItemStack(ModItems.WORLD_SOUL_5.get(), 60)));
        TradeList.tradeRecipeMap.put(heatInjection2,
                List.of(new ItemStack(ModItems.WORLD_SOUL_5.get(), 80)));
        TradeList.tradeRecipeMap.put(heatInjection3,
                List.of(new ItemStack(ModItems.WORLD_SOUL_5.get(), 100)));
        TradeList.tradeRecipeMap.put(heatDevice0,
                List.of(new ItemStack(ModItems.WORLD_SOUL_5.get(), 30)));
        TradeList.tradeRecipeMap.put(heatDevice1,
                List.of(new ItemStack(ModItems.WORLD_SOUL_5.get(), 40)));
        TradeList.tradeRecipeMap.put(heatDevice2,
                List.of(new ItemStack(ModItems.WORLD_SOUL_5.get(), 50)));
    }

    public static void iceDragon() {
        ItemStack vdWeaponCore0 = new ItemStack(SuperColdItems.VD_WEAPON_CORE.get());
        ItemStack vdWeaponCore1 = new ItemStack(SuperColdItems.VD_WEAPON_CORE.get());
        ItemStack vdWeaponCore2 = new ItemStack(SuperColdItems.VD_WEAPON_CORE.get());
        ItemStack dragonSword1 = new ItemStack(SuperColdItems.DRAGON_SWORD_1.get());
        ItemStack dragonSword2 = new ItemStack(SuperColdItems.DRAGON_SWORD_2.get());
        ItemStack dragonBow1 = new ItemStack(SuperColdItems.DRAGON_BOW_1.get());
        ItemStack dragonBow2 = new ItemStack(SuperColdItems.DRAGON_BOW_2.get());
        ItemStack dragonSceptre1 = new ItemStack(SuperColdItems.DRAGON_SCEPTRE_1.get());
        ItemStack dragonSceptre2 = new ItemStack(SuperColdItems.DRAGON_SCEPTRE_2.get());
        ItemStack dragonBreathGem1 = new ItemStack(SuperColdItems.DRAGON_BREATH_GEM_1.get());
        ItemStack dragonBreathGem2 = new ItemStack(SuperColdItems.DRAGON_BREATH_GEM_2.get());
        ItemStack windGem1 = new ItemStack(SuperColdItems.WIND_GEM_1.get());
        ItemStack windGem2 = new ItemStack(SuperColdItems.WIND_GEM_2.get());
        ItemStack glacierGem1 = new ItemStack(SuperColdItems.GLACIER_GEM_1.get());
        ItemStack glacierGem2 = new ItemStack(SuperColdItems.GLACIER_GEM_2.get());
        ItemStack mapleRune = new ItemStack(SuperColdItems.MAPLE_RUNE.get());
        ItemStack firRune = new ItemStack(SuperColdItems.FIR_RUNE.get());
        ItemStack coldIronRune = new ItemStack(SuperColdItems.COLD_IRON_GOLEM_RUNE.get());
        ItemStack snowGolemRune = new ItemStack(SuperColdItems.BLIZZARD_RUNE.get());
        ItemStack blueCrystal = new ItemStack(CrystalItems.BLUE_CRYSTAL_C.get());
        List<ItemStack> stacks = List.of(
                vdWeaponCore0, vdWeaponCore1, vdWeaponCore2,
                dragonSword1, dragonSword2,
                dragonBow1, dragonBow2,
                dragonSceptre1, dragonSceptre2,
                dragonBreathGem1, dragonBreathGem2,
                windGem1, windGem2,
                glacierGem1, glacierGem2,
                mapleRune, firRune, coldIronRune, snowGolemRune,
                blueCrystal
        );
        MyVillagerData.setMyVillagerData("极寒龙之研究者", "iceDragon",
                CustomStyle.styleOfIce, VillagerType.SNOW, VillagerProfession.LIBRARIAN, stacks);
        TradeList.tradeRecipeMap.put(vdWeaponCore0,
                List.of(new ItemStack(C7Items.VD_SWORD.get())));
        TradeList.tradeRecipeMap.put(vdWeaponCore1,
                List.of(new ItemStack(C7Items.VD_BOW.get())));
        TradeList.tradeRecipeMap.put(vdWeaponCore2,
                List.of(new ItemStack(C7Items.VD_SCEPTRE.get())));
        TradeList.tradeRecipeMap.put(dragonSword1,
                List.of(new ItemStack(SuperColdItems.DRAGON_SWORD_0.get(), 1),
                        new ItemStack(SuperColdItems.WEAPON_CORE.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 10),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_PP.get(), 5),
                        new ItemStack(ModItems.ICE_ELEMENT_PIECE_2.get(), 8)));
        TradeList.tradeRecipeMap.put(dragonSword2,
                List.of(new ItemStack(SuperColdItems.DRAGON_SWORD_1.get(), 1),
                        new ItemStack(SuperColdItems.VD_WEAPON_CORE.get()),
                        new ItemStack(SuperColdItems.WEAPON_CORE.get(), 3),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 30),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_PP.get(), 10),
                        new ItemStack(ModItems.ICE_ELEMENT_PIECE_2.get(), 16)));
        TradeList.tradeRecipeMap.put(dragonBow1,
                List.of(new ItemStack(SuperColdItems.DRAGON_BOW_0.get(), 1),
                        new ItemStack(SuperColdItems.WEAPON_CORE.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 10),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_PP.get(), 5),
                        new ItemStack(ModItems.ICE_ELEMENT_PIECE_2.get(), 8)));
        TradeList.tradeRecipeMap.put(dragonBow2,
                List.of(new ItemStack(SuperColdItems.DRAGON_BOW_1.get(), 1),
                        new ItemStack(SuperColdItems.VD_WEAPON_CORE.get()),
                        new ItemStack(SuperColdItems.WEAPON_CORE.get(), 3),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 30),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_PP.get(), 10),
                        new ItemStack(ModItems.ICE_ELEMENT_PIECE_2.get(), 16)));
        TradeList.tradeRecipeMap.put(dragonSceptre1,
                List.of(new ItemStack(SuperColdItems.DRAGON_SCEPTRE_0.get(), 1),
                        new ItemStack(SuperColdItems.WEAPON_CORE.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 10),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_PP.get(), 5),
                        new ItemStack(ModItems.ICE_ELEMENT_PIECE_2.get(), 8)));
        TradeList.tradeRecipeMap.put(dragonSceptre2,
                List.of(new ItemStack(SuperColdItems.DRAGON_SCEPTRE_1.get(), 1),
                        new ItemStack(SuperColdItems.VD_WEAPON_CORE.get()),
                        new ItemStack(SuperColdItems.WEAPON_CORE.get(), 3),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 30),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_PP.get(), 10),
                        new ItemStack(ModItems.ICE_ELEMENT_PIECE_2.get(), 16)));
        TradeList.tradeRecipeMap.put(dragonBreathGem1,
                List.of(new ItemStack(SuperColdItems.DRAGON_BREATH_GEM_0.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_GEM_PIECE.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 5),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_P.get()),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 30)));
        TradeList.tradeRecipeMap.put(dragonBreathGem2,
                List.of(new ItemStack(SuperColdItems.DRAGON_BREATH_GEM_1.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_GEM_PIECE.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 15),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_P.get(), 3),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 90)));
        TradeList.tradeRecipeMap.put(windGem1,
                List.of(new ItemStack(SuperColdItems.WIND_GEM_0.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_GEM_PIECE.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 5),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_P.get()),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 30)));
        TradeList.tradeRecipeMap.put(windGem2,
                List.of(new ItemStack(SuperColdItems.WIND_GEM_1.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_GEM_PIECE.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 15),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_P.get(), 3),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 90)));
        TradeList.tradeRecipeMap.put(glacierGem1,
                List.of(new ItemStack(SuperColdItems.GLACIER_GEM_0.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_GEM_PIECE.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 5),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_P.get()),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 30)));
        TradeList.tradeRecipeMap.put(glacierGem2,
                List.of(new ItemStack(SuperColdItems.GLACIER_GEM_1.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_GEM_PIECE.get(), 1),
                        new ItemStack(SuperColdItems.SUPER_COLD_CRYSTAL.get(), 15),
                        new ItemStack(CrystalItems.BLUE_CRYSTAL_P.get(), 3),
                        new ItemStack(ModItems.COMPLETE_GEM.get(), 90)));
        TradeList.tradeRecipeMap.put(mapleRune,
                List.of(new ItemStack(SuperColdItems.SUPER_COLD_STONE.get(), 1)));
        TradeList.tradeRecipeMap.put(firRune,
                List.of(new ItemStack(SuperColdItems.SUPER_COLD_STONE.get(), 1)));
        TradeList.tradeRecipeMap.put(coldIronRune,
                List.of(new ItemStack(SuperColdItems.SUPER_COLD_STONE.get(), 1)));
        TradeList.tradeRecipeMap.put(snowGolemRune,
                List.of(new ItemStack(SuperColdItems.SUPER_COLD_STONE.get(), 1)));
        TradeList.tradeRecipeMap.put(blueCrystal,
                List.of(new ItemStack(SuperColdItems.SUPER_COLD_DRAGON_LOTTERY.get())));
    }
}