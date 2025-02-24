
package fun.wraq.series.overworld.divine.equip.passive;

import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GhastlySword extends WraqPassiveEquip {

    public GhastlySword(Properties properties) {
        super(properties);
        Utils.percentAttackDamageEnhance.put(this, 0.03);
        Utils.percentDefenceEnhance.put(this, 0.03);
        Utils.defencePenetration.put(this, 0.03);
        Utils.percentMaxHealthEnhance.put(this, 0.03);
        Utils.levelRequire.put(this, 230);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.GHASTLY_STYLE;
    }

    @Override
    public List<Component> getAdditionDescriptions() {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfGhastly();
    }

    @Override
    public Component getType() {
        return Component.literal("长剑").withStyle(CustomStyle.styleOfPower);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
