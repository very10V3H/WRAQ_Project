package fun.wraq.series.holy.ice.curio;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class IceHolySword extends IceHolyCurio {
    public IceHolySword(int tier, boolean isAttack) {
        super(tier);
        if (isAttack) {
            Utils.attackDamageEnhance.put(this, new double[]{0.02, 0.04, 0.06, 0.1}[tier]);
        } else {
            Utils.manaDamageEnhance.put(this, new double[]{0.02, 0.04, 0.06, 0.1}[tier]);
        }
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
