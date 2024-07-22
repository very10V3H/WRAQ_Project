package com.very.wraq.events.mob.loot;

import com.very.wraq.projectiles.WraqSceptre;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class RandomSceptre extends WraqSceptre implements RandomLootEquip {

    private final Style style;
    private final Component suffix;
    private final List<RandomAttributeValue> randomAttributeValues;
    private final int levelRequire;

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

    public RandomSceptre(Properties properties, Style style, Component suffix,
                         List<RandomAttributeValue> randomAttributeValues, int levelRequire) {
        super(properties);
        this.style = style;
        this.suffix = suffix;
        this.randomAttributeValues = randomAttributeValues;
        this.levelRequire = levelRequire;
    }

    @Override
    public int levelRequire() {
        return levelRequire;
    }
}
