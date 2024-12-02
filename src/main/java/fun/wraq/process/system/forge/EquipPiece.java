package fun.wraq.process.system.forge;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.series.WraqItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EquipPiece extends WraqItem implements Decomposable {

    private final int tier;
    public EquipPiece(Properties properties, int tier) {
        super(properties, true, tier > 2);
        this.tier = tier;
    }

    @Override
    public ItemStack getProduct() {
        if (tier > 0) {
            return new ItemStack(ForgeEquipUtils.getEquipPiece(tier - 1), 4);
        }
        return Items.AIR.getDefaultInstance();
    }
}
