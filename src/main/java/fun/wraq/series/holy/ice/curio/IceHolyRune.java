package fun.wraq.series.holy.ice.curio;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class IceHolyRune extends IceHolyCurio {
    public IceHolyRune(int tier) {
        super(tier);
        Utils.percentMaxHealthEnhance.put(this, new double[]{0.02, 0.04, 0.06, 0.1}[tier]);
        Utils.percentHealthRecover.put(this, new double[]{0.002, 0.004, 0.006, 0.01}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getFuncTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }
}
