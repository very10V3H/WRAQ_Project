package fun.wraq.series.overworld.divine.equip.passive;

import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class GhastlySceptre extends WraqPassiveEquip {

    public GhastlySceptre(Properties properties) {
        super(properties);
        Utils.percentManaDamageEnhance.put(this, 0.03);
        Utils.maxMana.put(this, 200d);
        Utils.manaPenetration.put(this, 0.06);
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
        return Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE);
    }
}
