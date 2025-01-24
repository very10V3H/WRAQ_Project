package fun.wraq.common.util.struct;

import net.minecraft.world.item.Item;

public class InjectingRecipe {
    public Item material;
    public int materialCount;
    public Item product;
    public int originalMaterialNeedCount;

    public InjectingRecipe(Item material, int materialCount, Item product) {
        this.material = material;
        this.materialCount = materialCount;
        this.product = product;
        this.originalMaterialNeedCount = 1;
    }

    public InjectingRecipe(int originalMaterialNeedCount, Item material, int materialCount, Item product) {
        this.material = material;
        this.materialCount = materialCount;
        this.product = product;
        this.originalMaterialNeedCount = originalMaterialNeedCount;
    }

    public Item getProduct() {
        return product;
    }

    public void setProduct(Item product) {
        this.product = product;
    }

    public void setMaterialCount(int materialCount) {
        this.materialCount = materialCount;
    }

    public int getMaterialCount() {
        return materialCount;
    }

    public Item getMaterial() {
        return material;
    }

    public void setMaterial(Item material) {
        this.material = material;
    }

    public int getSourceItemCount() {
        return originalMaterialNeedCount;
    }

    public void setOriginalMaterialNeedCount(int originalMaterialNeedCount) {
        this.originalMaterialNeedCount = originalMaterialNeedCount;
    }
}
