package fun.wraq.series;

import fun.wraq.series.MaterialItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FoiledMaterialItem extends MaterialItem {

    public FoiledMaterialItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack itemStack) {
        return true;
    }
}
