package fun.wraq.series.holy.ice.curio;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class IceHolyCrest extends IceHolyCurio {
    public IceHolyCrest(int tier) {
        super(tier);
        Utils.defencePenetration0.put(this, new double[]{10, 20, 30, 50}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }
}
