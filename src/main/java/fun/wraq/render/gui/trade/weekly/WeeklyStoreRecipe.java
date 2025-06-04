package fun.wraq.render.gui.trade.weekly;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class WeeklyStoreRecipe {
    public ItemStack materialStack0 = Items.AIR.getDefaultInstance();
    public ItemStack materialStack1 = Items.AIR.getDefaultInstance();
    public ItemStack materialStack2 = Items.AIR.getDefaultInstance();
    public ItemStack product;
    public int count;

    public WeeklyStoreRecipe(ItemStack materialStack0, ItemStack materialStack1, ItemStack materialStack2,
                             ItemStack product, int count) {
        this.materialStack0 = materialStack0;
        this.materialStack1 = materialStack1;
        this.materialStack2 = materialStack2;
        this.product = product;
        this.count = count;
    }

    public WeeklyStoreRecipe(List<ItemStack> materialStacks, ItemStack product, int count) {
        if (materialStacks.size() == 1) {
            this.materialStack0 = materialStacks.get(0);
        } else if (materialStacks.size() == 2) {
            this.materialStack0 = materialStacks.get(0);
            this.materialStack1 = materialStacks.get(1);
        } else if (materialStacks.size() == 3) {
            this.materialStack0 = materialStacks.get(0);
            this.materialStack1 = materialStacks.get(1);
            this.materialStack2 = materialStacks.get(2);
        }
        this.product = product;
        this.count = count;
    }

    @Override
    public String toString() {
        return Compute.getItemStackString(this.materialStack0) + "#"
                + Compute.getItemStackString(this.materialStack1) + "#"
                + Compute.getItemStackString(this.materialStack2) + "#"
                + Compute.getItemStackString(this.product) + "#" + this.count;
    }

    public static WeeklyStoreRecipe getRecipeByString(String s) {
        String[] split = s.split("#");
        try {
            return new WeeklyStoreRecipe(Compute.getItemFromString(split[0]),
                    Compute.getItemFromString(split[1]), Compute.getItemFromString(split[2]),
                    Compute.getItemFromString(split[3]), Integer.parseInt(split[4]));
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSameRecipe(List<ItemStack> material, ItemStack product) {
        boolean sameProduct = product.is(this.product.getItem()) && product.getCount() == this.product.getCount();
        if (material.size() == 1) {
            return sameProduct && material.get(0).is(materialStack0.getItem())
                    && material.get(0).getCount() == materialStack0.getCount();
        } else if (material.size() == 2) {
            return sameProduct && material.get(0).is(materialStack0.getItem())
                    && material.get(0).getCount() == materialStack0.getCount()
                    && material.get(1).is(materialStack1.getItem())
                    && material.get(1).getCount() == materialStack1.getCount();
        } else if (material.size() == 3) {
            return sameProduct && material.get(0).is(materialStack0.getItem())
                    && material.get(0).getCount() == materialStack0.getCount()
                    && material.get(1).is(materialStack1.getItem())
                    && material.get(1).getCount() == materialStack1.getCount()
                    && material.get(2).is(materialStack2.getItem())
                    && material.get(2).getCount() == materialStack2.getCount();
        }
        return false;
    }

    public List<ItemStack> getMaterialList() {
        List<ItemStack> list = new ArrayList<>();
        if (!materialStack0.is(Items.AIR)) {
            list.add(materialStack0);
        }
        if (!materialStack1.is(Items.AIR)) {
            list.add(materialStack1);
        }
        if (!materialStack2.is(Items.AIR)) {
            list.add(materialStack2);
        }
        return list;
    }
}
