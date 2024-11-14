package fun.wraq.events.mob.loot;

import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class RandomSword extends WraqSword implements RandomLootEquip, UsageOrGetWayDescriptionItem {

    private final Style style;
    private final Component suffix;
    private final List<RandomAttributeValue> randomAttributeValues;
    private final int levelRequire;

    public RandomSword(Properties properties, Style style, Component suffix,
                       List<RandomAttributeValue> randomAttributeValues, int levelRequire) {
        super(properties);
        this.style = style;
        this.suffix = suffix;
        this.randomAttributeValues = randomAttributeValues;
        this.levelRequire = levelRequire;
    }

    @Override
    public Style getMainStyle() {
        return style;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return suffix;
    }

    public List<RandomAttributeValue> getRandomAttributeValues() {
        return randomAttributeValues;
    }

    @Override
    public int levelRequire() {
        return levelRequire;
    }

    @Override
    public List<Component> getWayDescription() {
        return List.of(
                Te.s(" 可在锻造台分解为相应品质锻造碎片", CustomStyle.styleOfStone)
        );
    }
}
