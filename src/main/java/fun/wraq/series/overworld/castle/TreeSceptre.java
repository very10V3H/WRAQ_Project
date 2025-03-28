package fun.wraq.series.overworld.castle;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class TreeSceptre extends WraqPassiveEquip {

    public TreeSceptre(Properties properties, int tier) {
        super(properties);
        Utils.manaDamage.put(this, new double[]{400, 600, 800, 1000}[tier]);
        Utils.maxMana.put(this, new double[]{25, 50, 75, 100}[tier]);
        Utils.manaDefence.put(this, new double[]{4, 6, 8, 10}[tier]);
        Utils.manaPenetration0.put(this, new double[]{2, 3, 4, 5}[tier]);
        Utils.levelRequire.put(this, 180);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfHealth;
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
        return Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE);
    }
}
