package fun.wraq.series.overworld.castle;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BeaconBow extends WraqPassiveEquip {

    private static final double[] ExAttackDamage = {
            200, 300, 400, 500
    };

    private static final double[] CritDamage = {
            0.02, 0.03, 0.04, 0.05
    };

    private static final double[] Swiftness = {
            0.25, 0.4, 0.55, 0.7
    };


    public BeaconBow(Properties p_40524_, int tier) {
        super(p_40524_);
        Utils.attackDamage.put(this, ExAttackDamage[tier]);
        Utils.critDamage.put(this, CritDamage[tier]);
        Utils.swiftnessUp.put(this, Swiftness[tier]);
        Utils.levelRequire.put(this, 180);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPower;
    }

    @Override
    public List<Component> getAdditionDescriptions() {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfCastle();
    }

    @Override
    public Component getType() {
        return Component.literal("长弓").withStyle(CustomStyle.styleOfFlexible);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
