package fun.wraq.blocks.blocks.furnace;

import fun.wraq.common.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FurnaceRecipe {
    private List<ItemStack> Material;
    private List<ItemStack> Fuel;
    private List<ItemStack> Product;

    public FurnaceRecipe(List<ItemStack> material, List<ItemStack> fuel, List<ItemStack> product) {
        this.Material = material;
        this.Fuel = fuel;
        this.Product = product;
    }

    public FurnaceRecipe(ItemStack material, ItemStack fuel, ItemStack product) {
        this.Material = new ArrayList<>() {{
            add(material);
        }};
        this.Fuel = new ArrayList<>() {{
            add(fuel);
        }};
        this.Product = new ArrayList<>() {{
            add(product);
        }};
    }

    public FurnaceRecipe(ItemStack material, List<ItemStack> fuel, ItemStack product) {
        this.Material = new ArrayList<>() {{
            add(material);
        }};
        this.Fuel = fuel;
        this.Product = new ArrayList<>() {{
            add(product);
        }};
    }

    public boolean HasRecipe(ItemStackHandler itemStackHandler) {
        List<ItemStack> TempProduct = new ArrayList<>();
        this.Product.forEach(itemStack -> {
            ItemStack itemStack1 = new ItemStack(itemStack.getItem(), itemStack.getCount());
            TempProduct.add(itemStack1);
        });

        TempProduct.forEach(itemStack -> {
            Item requireItem = itemStack.getItem();
            for (int i = 6; i < 8; i++) {
                ItemStack itemInSlot = itemStackHandler.getStackInSlot(i);
                if (itemInSlot.is(requireItem) || itemInSlot.is(Items.AIR)) {
                    if ((64 - itemInSlot.getCount()) >= itemStack.getCount()) itemStack.setCount(0);
                    else itemStack.setCount(itemStack.getCount() - (64 - itemInSlot.getCount()));
                }
            }
        });
        TempProduct.removeIf(itemStack -> itemStack.getCount() <= 0);
        boolean HasSlotToPlace = TempProduct.isEmpty();

        return isThisRecipe(itemStackHandler) && HasSlotToPlace;
    }

    public boolean isThisRecipe(ItemStackHandler itemStackHandler) {
        List<ItemStack> TempMaterials = new ArrayList<>();
        this.Material.forEach(itemStack -> {
            ItemStack itemStack1 = new ItemStack(itemStack.getItem(), itemStack.getCount());
            TempMaterials.add(itemStack1);
        });

        TempMaterials.forEach(itemStack -> {
            Item requireItem = itemStack.getItem();
            for (int i = 0; i < 4; i++) {
                ItemStack itemInSlot = itemStackHandler.getStackInSlot(i);
                if (itemInSlot.is(requireItem)) {
                    if (itemInSlot.getCount() >= itemStack.getCount()) {
                        itemStack.setCount(0);
                    } else {
                        itemStack.setCount(itemStack.getCount() - itemInSlot.getCount());
                    }
                }
            }
        });
        TempMaterials.removeIf(itemStack -> itemStack.getCount() <= 0);
        boolean HasMaterial = TempMaterials.isEmpty();

        List<ItemStack> TempFuels = new ArrayList<>();
        this.Fuel.forEach(itemStack -> {
            ItemStack itemStack1 = new ItemStack(itemStack.getItem(), itemStack.getCount());
            TempFuels.add(itemStack1);
        });
        TempFuels.forEach(itemStack -> {
            Item requireItem = itemStack.getItem();
            for (int i = 4; i < 6; i++) {
                ItemStack itemInSlot = itemStackHandler.getStackInSlot(i);
                if (itemInSlot.is(requireItem)) {
                    if (itemInSlot.getCount() >= itemStack.getCount()) {
                        itemStack.setCount(0);
                    } else {
                        itemStack.setCount(itemStack.getCount() - itemInSlot.getCount());
                    }
                }
            }
        });
        TempFuels.removeIf(itemStack -> itemStack.getCount() <= 0);
        boolean HasFuel = TempFuels.isEmpty();
        return HasMaterial && HasFuel;
    }

    public static boolean hasRecipe(ItemStackHandler itemStackHandler) {
        AtomicBoolean flag = new AtomicBoolean(false);
        furnaceRecipes.forEach(furnaceRecipe -> {
            if (furnaceRecipe.HasRecipe(itemStackHandler)) flag.set(true);
        });
        return flag.get();
    }

    public List<ItemStack> getProduct() {
        return this.Product;
    }

    public List<ItemStack> getMaterial() {
        return Material;
    }

    public List<ItemStack> getFuel() {
        return Fuel;
    }

    public static FurnaceRecipe getRecipe(ItemStackHandler itemStackHandler) {
        AtomicReference<FurnaceRecipe> furnaceRecipe = new AtomicReference<>();
        furnaceRecipes.forEach(furnaceRecipe1 -> {
            if (furnaceRecipe1.HasRecipe(itemStackHandler)) furnaceRecipe.set(furnaceRecipe1);
        });
        return furnaceRecipe.get();
    }

    public static void Cost(ItemStackHandler itemStackHandler) {
        FurnaceRecipe furnaceRecipe = getRecipe(itemStackHandler);
        List<ItemStack> TempMaterials = new ArrayList<>();
        furnaceRecipe.getMaterial().forEach(itemStack -> {
            ItemStack itemStack1 = new ItemStack(itemStack.getItem(), itemStack.getCount());
            TempMaterials.add(itemStack1);
        });
        TempMaterials.forEach(itemStack -> {
            Item requireItem = itemStack.getItem();
            for (int i = 0; i < 4; i++) {
                ItemStack itemInSlot = itemStackHandler.getStackInSlot(i);
                if (itemInSlot.is(requireItem)) {
                    if (itemInSlot.getCount() >= itemStack.getCount()) {
                        itemInSlot.setCount(itemInSlot.getCount() - itemStack.getCount());
                        itemStack.setCount(0);
                    } else {
                        itemStack.setCount(itemStack.getCount() - itemInSlot.getCount());
                        itemInSlot.setCount(0);
                    }
                }
            }
        });

        List<ItemStack> TempFuels = new ArrayList<>();
        furnaceRecipe.getFuel().forEach(itemStack -> {
            ItemStack itemStack1 = new ItemStack(itemStack.getItem(), itemStack.getCount());
            TempFuels.add(itemStack1);
        });
        TempFuels.forEach(itemStack -> {
            Item requireItem = itemStack.getItem();
            for (int i = 4; i < 6; i++) {
                ItemStack itemInSlot = itemStackHandler.getStackInSlot(i);
                if (itemInSlot.is(requireItem)) {
                    if (itemInSlot.getCount() >= itemStack.getCount()) {
                        itemInSlot.setCount(itemInSlot.getCount() - itemStack.getCount());
                        itemStack.setCount(0);
                    } else {
                        itemStack.setCount(itemStack.getCount() - itemInSlot.getCount());
                        itemInSlot.setCount(0);
                    }
                }
            }
        });

        List<ItemStack> TempProduct = new ArrayList<>();
        furnaceRecipe.getProduct().forEach(itemStack -> {
            ItemStack itemStack1 = new ItemStack(itemStack.getItem(), itemStack.getCount());
            TempProduct.add(itemStack1);
        });
        TempProduct.forEach(itemStack -> {
            Item requireItem = itemStack.getItem();
            for (int i = 6; i < 8; i++) {
                ItemStack itemInSlot = itemStackHandler.getStackInSlot(i);
                if (itemInSlot.is(requireItem) || itemInSlot.is(Items.AIR)) {
                    if ((64 - itemInSlot.getCount()) >= itemStack.getCount()) {
                        itemStackHandler.setStackInSlot(i, new ItemStack(requireItem, itemInSlot.getCount() + itemStack.getCount()));
                        itemStack.setCount(0);
                    } else {
                        itemStack.setCount(itemStack.getCount() - (64 - itemInSlot.getCount()));
                        itemInSlot.setCount(64);
                    }
                }
            }
        });
    }

    public static List<FurnaceRecipe> furnaceRecipes = new ArrayList<>() {{
        add(new FurnaceRecipe(new ItemStack(Items.OAK_LOG, 4),
                new ItemStack(Items.COAL, 1),
                new ItemStack(Items.CHARCOAL, 1)));
        add(new FurnaceRecipe(new ItemStack(Items.SPRUCE_LOG, 4),
                new ItemStack(Items.COAL, 1),
                new ItemStack(Items.CHARCOAL, 1)));
        add(new FurnaceRecipe(new ItemStack(Items.BIRCH_LOG, 4),
                new ItemStack(Items.COAL, 1),
                new ItemStack(Items.CHARCOAL, 1)));
        add(new FurnaceRecipe(new ItemStack(Items.JUNGLE_LOG, 4),
                new ItemStack(Items.COAL, 1),
                new ItemStack(Items.CHARCOAL, 1)));
        add(new FurnaceRecipe(new ItemStack(Items.ACACIA_LOG, 4),
                new ItemStack(Items.COAL, 1),
                new ItemStack(Items.CHARCOAL, 1)));
        add(new FurnaceRecipe(new ItemStack(Items.DARK_OAK_LOG, 4),
                new ItemStack(Items.COAL, 1),
                new ItemStack(Items.CHARCOAL, 1)));
        add(new FurnaceRecipe(new ItemStack(Items.MANGROVE_LOG, 4),
                new ItemStack(Items.COAL, 1),
                new ItemStack(Items.CHARCOAL, 1)));
        add(new FurnaceRecipe(new ItemStack(Items.CHERRY_LOG, 4),
                new ItemStack(Items.COAL, 1),
                new ItemStack(Items.CHARCOAL, 1)));

        add(new FurnaceRecipe(new ItemStack(Items.CHARCOAL, 4),
                new ArrayList<>() {{
                    add(new ItemStack(Items.COAL));
                    add(new ItemStack(ModItems.PLAIN_SOUL.get()));
                }},
                new ItemStack(ModItems.CRUDE_COAL.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(Items.CHARCOAL, 4),
                new ArrayList<>() {{
                    add(new ItemStack(Items.COAL));
                    add(new ItemStack(ModItems.FOREST_SOUL.get()));
                }},
                new ItemStack(ModItems.CRUDE_COAL.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(ModItems.CRUDE_COAL.get(), 4),
                new ArrayList<>() {{
                    add(new ItemStack(Items.COAL));
                    add(new ItemStack(ModItems.VOLCANO_SOUL.get()));
                }},
                new ItemStack(ModItems.HOT_COAL.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(ModItems.CRUDE_COAL.get(), 4),
                new ArrayList<>() {{
                    add(new ItemStack(Items.COAL));
                    add(new ItemStack(ModItems.LAKE_SOUL.get()));
                }},
                new ItemStack(ModItems.REFINING_COAL.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(ModItems.HOT_COAL.get(), 4),
                new ArrayList<>() {{
                    add(new ItemStack(Items.COAL));
                    add(new ItemStack(ModItems.MAGMA_SOUL.get()));
                }},
                new ItemStack(ModItems.BLAZE_COAL.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(Items.RAW_IRON, 4),
                new ItemStack(ModItems.CRUDE_COAL.get(), 1),
                new ItemStack(ModItems.CRUDE_IRON.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(ModItems.CRUDE_IRON.get(), 4),
                new ItemStack(ModItems.HOT_COAL.get(), 1),
                new ItemStack(ModItems.HOT_IRON.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(ModItems.HOT_IRON.get(), 4),
                new ItemStack(ModItems.REFINING_COAL.get(), 1),
                new ItemStack(ModItems.REFINING_IRON.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(Items.RAW_COPPER, 4),
                new ItemStack(ModItems.CRUDE_COAL.get(), 1),
                new ItemStack(ModItems.CRUDE_COPPER.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(ModItems.CRUDE_COPPER.get(), 4),
                new ItemStack(ModItems.HOT_COAL.get(), 1),
                new ItemStack(ModItems.HOT_COPPER.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(ModItems.HOT_COPPER.get(), 4),
                new ItemStack(ModItems.REFINING_COAL.get(), 1),
                new ItemStack(ModItems.REFINING_COPPER.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(Items.RAW_GOLD, 4),
                new ItemStack(ModItems.CRUDE_COAL.get(), 1),
                new ItemStack(ModItems.CRUDE_GOLD.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(ModItems.CRUDE_GOLD.get(), 4),
                new ItemStack(ModItems.BLAZE_COAL.get(), 1),
                new ItemStack(ModItems.BLAZE_GOLD.get(), 1)));

        add(new FurnaceRecipe(new ItemStack(ModItems.BLAZE_GOLD.get(), 4),
                new ItemStack(ModItems.REFINING_COAL.get(), 1),
                new ItemStack(ModItems.REFINING_GOLD.get(), 1)));
    }};
}
