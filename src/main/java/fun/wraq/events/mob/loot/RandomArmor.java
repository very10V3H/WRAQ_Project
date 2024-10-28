package fun.wraq.events.mob.loot;

import fun.wraq.common.equip.WraqArmor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class RandomArmor extends WraqArmor implements RandomLootEquip {

    private final Style style;
    private final Component suffix;
    private final List<RandomAttributeValue> randomAttributeValues;
    private final int levelRequire;

    public RandomArmor(ArmorMaterial armorMaterial, Type type, Properties properties, Style style,
                       Component suffix, List<RandomAttributeValue> randomAttributeValues, int levelRequire) {
        super(armorMaterial, type, properties);
        this.style = style;
        this.suffix = suffix;
        this.randomAttributeValues = randomAttributeValues;
        this.levelRequire = levelRequire;
    }

    @Override
    public List<RandomAttributeValue> getRandomAttributeValues() {
        return randomAttributeValues;
    }

    @Override
    public int levelRequire() {
        return levelRequire;
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
}
