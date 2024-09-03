package com.very.wraq.common.util.struct;

import net.minecraft.world.item.Item;

public class InjectingRecipe {
    private Item forgingNeededMaterial;
    private int materialCount;
    private Item forgingGetItem;
    private int originalMaterialNeedCount;

    public InjectingRecipe(Item forgingNeededMaterial, int materialCount, Item forgingGetItem) {
        this.forgingNeededMaterial = forgingNeededMaterial;
        this.materialCount = materialCount;
        this.forgingGetItem = forgingGetItem;
        this.originalMaterialNeedCount = 1;
    }

    public InjectingRecipe(int originalMaterialNeedCount, Item forgingNeededMaterial, int materialCount, Item forgingGetItem) {
        this.forgingNeededMaterial = forgingNeededMaterial;
        this.materialCount = materialCount;
        this.forgingGetItem = forgingGetItem;
        this.originalMaterialNeedCount = originalMaterialNeedCount;
    }

    public Item getForgingGetItem() {
        return forgingGetItem;
    }

    public void setForgingGetItem(Item forgingGetItem) {
        this.forgingGetItem = forgingGetItem;
    }

    public void setMaterialCount(int materialCount) {
        this.materialCount = materialCount;
    }

    public int getMaterialCount() {
        return materialCount;
    }

    public Item getForgingNeededMaterial() {
        return forgingNeededMaterial;
    }

    public void setForgingNeededMaterial(Item forgingNeededMaterial) {
        this.forgingNeededMaterial = forgingNeededMaterial;
    }

    public int getOriginalMaterialNeedCount() {
        return originalMaterialNeedCount;
    }

    public void setOriginalMaterialNeedCount(int originalMaterialNeedCount) {
        this.originalMaterialNeedCount = originalMaterialNeedCount;
    }
}
