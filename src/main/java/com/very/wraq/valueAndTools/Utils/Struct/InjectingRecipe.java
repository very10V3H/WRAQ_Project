package com.very.wraq.valueAndTools.Utils.Struct;

import net.minecraft.world.item.Item;

public class InjectingRecipe {
    private Item forgingNeededMaterial;
    private int count;
    private Item forgingGetItem;
    private int OriginalMaterialNeedCount;

    public InjectingRecipe(Item forgingNeededMaterial, int count, Item forgingGetItem) {
        this.forgingNeededMaterial = forgingNeededMaterial;
        this.count = count;
        this.forgingGetItem = forgingGetItem;
        this.OriginalMaterialNeedCount = 1;
    }

    public InjectingRecipe(int originalMaterialNeedCount, Item forgingNeededMaterial, int count, Item forgingGetItem) {
        this.forgingNeededMaterial = forgingNeededMaterial;
        this.count = count;
        this.forgingGetItem = forgingGetItem;
        this.OriginalMaterialNeedCount = originalMaterialNeedCount;
    }

    public Item getForgingGetItem() {
        return forgingGetItem;
    }

    public void setForgingGetItem(Item forgingGetItem) {
        this.forgingGetItem = forgingGetItem;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Item getForgingNeededMaterial() {
        return forgingNeededMaterial;
    }

    public void setForgingNeededMaterial(Item forgingNeededMaterial) {
        this.forgingNeededMaterial = forgingNeededMaterial;
    }

    public int getOriginalMaterialNeedCount() {
        return OriginalMaterialNeedCount;
    }

    public void setOriginalMaterialNeedCount(int originalMaterialNeedCount) {
        OriginalMaterialNeedCount = originalMaterialNeedCount;
    }
}
