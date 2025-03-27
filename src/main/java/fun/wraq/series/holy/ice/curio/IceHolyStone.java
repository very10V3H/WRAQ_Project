package fun.wraq.series.holy.ice.curio;

import fun.wraq.common.util.ComponentUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class IceHolyStone extends IceHolyCurio {
    public IceHolyStone(int tier) {
        super(tier);
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
