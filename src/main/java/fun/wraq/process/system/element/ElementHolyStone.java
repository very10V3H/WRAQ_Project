package fun.wraq.process.system.element;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public class ElementHolyStone extends WraqCurios {

    private final Style style;

    public ElementHolyStone(Properties properties, int type, Style style, Map<Item, Double> elementValueMap) {
        super(properties);
        switch (type) {
            case 0 -> Utils.attackDamage.put(this, 200d);
            case 1 -> Utils.swiftnessUp.put(this, 1.5);
            case 2 -> Utils.manaDamage.put(this, 400d);
        }
        elementValueMap.put(this, 0.7);
        Utils.levelRequire.put(this, 200);
        this.style = style;
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return style;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfElement();
    }
}
