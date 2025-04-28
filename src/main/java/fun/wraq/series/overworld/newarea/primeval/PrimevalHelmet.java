package fun.wraq.series.overworld.newarea.primeval;

import fun.wraq.common.equip.WraqArmor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PrimevalHelmet extends WraqArmor {

    public PrimevalHelmet(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public Style getMainStyle() {
        return null;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components =  new ArrayList<>();

        return components;
    }

    @Override
    public Component getSuffix() {
        return null;
    }
}
